/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.jwt.resource;

import cc.altius.FASP.jwt.JwtTokenUtil;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.rest.controller.UserRestController;
import cc.altius.FASP.security.CustomUserDetailsService;
import cc.altius.FASP.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(
    name = "JWT Authentication",
    description = "JWT Authentication for theFASP API"
)
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

    private final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Operation(summary = "Authenticate user", description = "Get JWT token for authentication")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully authenticated"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials"),
        @ApiResponse(responseCode = "406", description = "Password expired"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @RequestMapping(value = "${jwt.get.token.uri}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest, HttpServletRequest request) throws AuthenticationException {
        try {
            logger.info("Received a JWT Token request for Username: " + authenticationRequest.getUsername(), request.getRemoteAddr());
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            logger.info("JWT Token generated successfully for Username: " + authenticationRequest.getUsername());
            this.userService.resetFailedAttemptsByUsername(authenticationRequest.getUsername());
            this.userService.updateSuncExpiresOn(authenticationRequest.getUsername());
            logger.info("Langage changed flag---: " + authenticationRequest.isLanguageChanged());
//            System.out.println("Langage changed flag---: " + authenticationRequest.isLanguageChanged());
            if (authenticationRequest.isLanguageChanged()) {
                this.userService.updateUserLanguageByEmailId(authenticationRequest.getUsername(), authenticationRequest.getLanguageCode());
            }
        } catch (BadCredentialsException e) {
//            System.out.println("-------1--------------");
            this.userService.updateFailedAttemptsByUserId(authenticationRequest.getUsername());
            logger.info("JWT Token generation failed because of BadCredentials for Username: " + authenticationRequest.getUsername());
            return new ResponseEntity(new ResponseCode("static.message.login.invalidCredentials"), HttpStatus.UNAUTHORIZED);
        } catch (DisabledException | AccountExpiredException e) {
//            System.out.println("-------2--------------");
            logger.info("JWT Token generation failed because user is Disabled for Username: " + authenticationRequest.getUsername());
            return new ResponseEntity(new ResponseCode("static.message.login.disabled"), HttpStatus.UNAUTHORIZED);
        } catch (LockedException e) {
//            System.out.println("-------3--------------");
            logger.info("JWT Token generation failed because user is Locked for Username: " + authenticationRequest.getUsername());
            return new ResponseEntity(new ResponseCode("static.message.login.locked"), HttpStatus.UNAUTHORIZED);
        } catch (CredentialsExpiredException e) {
//            System.out.println("-------4--------------");
            logger.info("JWT Token generation failed because Password has expired for Username: " + authenticationRequest.getUsername());
            return new ResponseEntity(new ResponseCode("static.message.login.passwordExpired"), HttpStatus.NOT_ACCEPTABLE);
        } catch (UsernameNotFoundException e) {
//            System.out.println("-------5--------------");
            logger.info("JWT Token generation failed because User not found for Username: " + authenticationRequest.getUsername());
            return new ResponseEntity(new ResponseCode("static.message.login.noUser"), HttpStatus.UNAUTHORIZED);
        }
        final CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        userDetails.setSessionExpiresOn(sessionExpiryTime);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtTokenResponse(token));
    }

    @RequestMapping(value = "${jwt.refresh.token.uri}", method = RequestMethod.GET)
    @Operation(summary = "Refresh JWT token", description = "Refresh JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully refreshed"),
        @ApiResponse(responseCode = "401", description = "Invalid token")
    })
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);
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
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return new ResponseEntity(new ResponseCode("static.message.login.unauthorized"), HttpStatus.UNAUTHORIZED);
    }

    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
