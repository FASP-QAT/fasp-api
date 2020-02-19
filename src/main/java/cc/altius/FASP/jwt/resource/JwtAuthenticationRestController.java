/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.jwt.resource;

import cc.altius.FASP.jwt.JwtTokenUtil;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.security.CustomUserDetailsService;
import cc.altius.FASP.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:4202", "http://192.168.43.113:4202"})

public class JwtAuthenticationRestController {

    @Value("${jwt.http.request.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserService userService;

    @Value("${session.expiry.time}")
    private int sessionExpiryTime;

    @RequestMapping(value = "${jwt.get.token.uri}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest) throws AuthenticationException {
        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            this.userService.resetFailedAttemptsByUsername(authenticationRequest.getUsername());
        } catch (BadCredentialsException e) {
            this.userService.updateFailedAttemptsByUserId(authenticationRequest.getUsername());
            throw new AuthenticationException("Invalid credentials", e);
        } catch (DisabledException | AccountExpiredException e) {
            throw new AuthenticationException("User is disabled", e);
        } catch (LockedException e) {
            throw new AuthenticationException("User account is locked", e);
        } catch (CredentialsExpiredException e) {
            throw new AuthenticationException("Password expired", e);
        } catch (UsernameNotFoundException e) {
            throw new AuthenticationException("User not found", e);
        }
        final CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        userDetails.setSessionExpiresOn(sessionExpiryTime);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtTokenResponse(token));
    }

    @RequestMapping(value = "${jwt.refresh.token.uri}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);
        ResponseFormat responseFormat = new ResponseFormat();
//        String username = jwtTokenUtil.getUsernameFromToken(token);
//        CustomUserDetails user = (CustomUserDetails) customUserDetailsService.loadUserByUsername("anchal.c@altius.cc");
        try {
            if (jwtTokenUtil.canTokenBeRefreshed(token)) {
                return ResponseEntity.ok(new JwtTokenResponse(authToken));
            } else {
                String refreshedToken = jwtTokenUtil.refreshToken(token);
                return ResponseEntity.ok(new JwtTokenResponse(refreshedToken));
            }
        } catch (ExpiredJwtException e) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtTokenResponse(refreshedToken));
        } catch (Exception e) {
            System.out.println("in main exception");
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Error occured");
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
