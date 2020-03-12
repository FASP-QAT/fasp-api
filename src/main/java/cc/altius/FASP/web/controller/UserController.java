/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.jwt.JwtTokenUtil;
import cc.altius.FASP.jwt.resource.JwtTokenResponse;
import cc.altius.FASP.model.BusinessFunction;
import cc.altius.FASP.model.CanCreateRole;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EmailUser;
import cc.altius.FASP.model.ForgotPasswordToken;
import cc.altius.FASP.model.Password;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.model.Role;
import cc.altius.FASP.model.User;
import cc.altius.FASP.security.CustomUserDetailsService;
import cc.altius.FASP.service.EmailService;
import cc.altius.utils.PassPhrase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import cc.altius.FASP.service.UserService;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io", "chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop"})
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${session.expiry.time}")
    private int sessionExpiryTime;
    @Value("${jwt.http.request.header}")
    private String tokenHeader;

    @GetMapping(value = "/getRoleList")
    public String getRoleList() {
        String json = null;
        try {
            List<Role> roleList = this.userService.getRoleList();
            for (Role role : roleList) {
                String[] businessFunctionId = new String[role.getBusinessFunctionList().size()];
                int i = 0;
                for (BusinessFunction b : role.getBusinessFunctionList()) {
                    businessFunctionId[i] = b.getBusinessFunctionId();
                    i++;
                }
                role.setBusinessFunctions(businessFunctionId);
                i = 0;
                String[] canCreateRoleId = new String[role.getCanCreateRoles().size()];
                i = 0;
                for (CanCreateRole c : role.getCanCreateRoles()) {
                    canCreateRoleId[i] = c.getRoleId();
                    i++;
                }
                role.setCanCreateRole(canCreateRoleId);
            }
            Gson gson = new Gson();
            Type typeList = new TypeToken<List>() {
            }.getType();
            json = gson.toJson(roleList, typeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    @GetMapping(value = "/getBusinessFunctionList")
    public String getBusinessFunctionList() {
        String json = null;
        try {
            List<BusinessFunction> businessFunctionList = this.userService.getBusinessFunctionList();
            Gson gson = new Gson();
            Type typeList = new TypeToken<List>() {
            }.getType();
            json = gson.toJson(businessFunctionList, typeList);
        } catch (Exception e) {
        }
        return json;
    }

    @PutMapping(value = "/addNewUser")
    public ResponseEntity addNewUser(@RequestBody User user, Authentication authentication) throws UnsupportedEncodingException {
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            CustomUserDetails curUser = (CustomUserDetails) authentication.getPrincipal();
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String password = PassPhrase.getPassword();
            String hashPass = encoder.encode(password);
            user.setPassword(hashPass);
            String msg = this.userService.checkIfUserExistsByEmailIdAndPhoneNumber(user, 1);
            if (msg.isEmpty()) {
                int userId = this.userService.addNewUser(user, curUser.getUserId());
                if (userId > 0) {
                    String token = this.userService.generateTokenForUsername(user.getUsername(), 2);
                    if (token == null || token.isEmpty()) {
                        responseFormat.setStatus("failed");
                        responseFormat.setMessage("Exception Occured. Please try again");
                        return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
                    } else {
                        responseFormat.setStatus("Success");
                        responseFormat.setMessage("User created successfully and credentials sent on email.");
                        return new ResponseEntity(responseFormat, HttpStatus.OK);
                    }

                } else {
                    responseFormat.setStatus("failed");
                    responseFormat.setMessage("Exception Occured. Please try again");
                    return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                responseFormat.setStatus("failed");
                responseFormat.setMessage(msg);
                return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            responseFormat.setStatus("failed");
            responseFormat.setMessage("User already exists.");
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getUserList")
    public String getUserList() throws UnsupportedEncodingException {
        String json = null;
        try {
            List<User> userList = this.userService.getUserList();
            for (User user : userList) {
                String[] roleId = new String[user.getRoles().size()];
                int i = 0;
                for (Role b : user.getRoles()) {
                    roleId[i] = b.getRoleId();
                    i++;
                }
                user.setRoleList(roleId);
                i = 0;
            }

            Gson gson = new Gson();
            Type typeList = new TypeToken<List>() {
            }.getType();
            json = gson.toJson(userList, typeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    @GetMapping(value = "/getUserByUserId/{userId}")
    public User getUserByUserId(@PathVariable int userId) throws UnsupportedEncodingException {
        try {
            User user = this.userService.getUserByUserId(userId);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(value = "/editUser")
    public ResponseEntity editUser(@RequestBody User user, Authentication authentication) throws UnsupportedEncodingException {
        Map<String, Object> responseMap = null;
        ResponseFormat responseFormat = new ResponseFormat();
        CustomUserDetails curUser = (CustomUserDetails) authentication.getPrincipal();
        try {
            int row = this.userService.updateUser(user, curUser.getUserId());
            if (row > 0) {
                responseFormat.setStatus("Success");
                responseFormat.setMessage("User updated successfully.");
                return new ResponseEntity(responseFormat, HttpStatus.OK);
            } else {
                responseFormat.setStatus("failed");
                responseFormat.setMessage("Failed to update the user");
                return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            responseFormat.setStatus("failed");
            responseFormat.setMessage("User already exists.");
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/unlockAccount/{userId}/{emailId}")
    public ResponseEntity unlockAccount(@PathVariable int userId, @PathVariable String emailId) throws UnsupportedEncodingException {
        Map<String, Object> responseMap = null;
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            User user = this.userService.getUserByUserId(userId);
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String password = PassPhrase.getPassword();
            String hashPass = encoder.encode(password);
            int row = this.userService.unlockAccount(userId, hashPass);
            if (row > 0) {
                String token = this.userService.generateTokenForUsername(user.getUsername(), 1);
                if (token == null || token.isEmpty()) {
                    responseFormat.setStatus("failed");
                    responseFormat.setMessage("Exception Occured. Please try again");
                    return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
                } else {
                    responseFormat.setStatus("Success");
                    responseFormat.setMessage("Account unlocked successfully and new password is sent on the registered email id.");
                    return new ResponseEntity(responseFormat, HttpStatus.OK);
                }
            } else {
                responseFormat.setStatus("failed");
                responseFormat.setMessage("Exception Occured. Please try again");
                return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/updateExpiredPassword")
    public ResponseEntity updateExpiredPassword(@RequestBody Password password) throws UnsupportedEncodingException {
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            Gson g = new Gson();
            if (!this.userService.confirmPassword(password.getUsername(), password.getOldPassword().trim())) {
                responseFormat.setStatus("Failed");
                responseFormat.setMessage("Old password is incorrect.");
                return new ResponseEntity(responseFormat, HttpStatus.UNAUTHORIZED);
            } else {
                final CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(password.getUsername());
                PasswordEncoder encoder = new BCryptPasswordEncoder();
                String hashPass = encoder.encode(password.getNewPassword());
                password.setNewPassword(hashPass);
                int row = this.userService.updatePassword(userDetails.getUserId(), password.getNewPassword(), 90);
                if (row > 0) {
                    userDetails.setSessionExpiresOn(sessionExpiryTime);
                    final String token = jwtTokenUtil.generateToken(userDetails);
                    return ResponseEntity.ok(new JwtTokenResponse(token));
                } else {
                    responseFormat.setStatus("failed");
                    responseFormat.setMessage("Exception occured. Please try again");
                    return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/changePassword")
    public ResponseEntity changePassword(@RequestBody Password password) throws UnsupportedEncodingException {
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            Gson g = new Gson();
            User user = this.userService.getUserByUserId(password.getUserId());
            if (!this.userService.confirmPassword(user.getUsername(), password.getOldPassword().trim())) {
                responseFormat.setStatus("Failed");
                responseFormat.setMessage("Old password is incorrect.");
                return new ResponseEntity(responseFormat, HttpStatus.UNAUTHORIZED);
            } else {
                PasswordEncoder encoder = new BCryptPasswordEncoder();
                String hashPass = encoder.encode(password.getNewPassword());
                password.setNewPassword(hashPass);
                int row = this.userService.updatePassword(password.getUserId(), password.getNewPassword(), 90);
                if (row > 0) {
                    responseFormat.setStatus("Success");
                    responseFormat.setMessage("Password updated successfully!");
                    responseFormat.setData(hashPass);
                    return new ResponseEntity(responseFormat, HttpStatus.OK);
                } else {
                    responseFormat.setStatus("failed");
                    responseFormat.setMessage("Exception occured. Please try again");
                    return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/forgotPassword/{username}")
    public ResponseFormat forgotPassword(@PathVariable String username) throws UnsupportedEncodingException {
        Map<String, Object> responseMap = null;
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            CustomUserDetails customUser = this.userService.getCustomUserByUsername(username);
            if (customUser != null) {
                if (customUser.isActive()) {
                    String token = this.userService.generateTokenForUsername(username, 1);
                    if (token == null || token.isEmpty()) {
                        return new ResponseFormat("Failed", "Cound not generate Token");
                    } else {
                        return new ResponseFormat("Success", "Email with password reset link sent", token);
                    }
                } else {
                    logger.error("User is disabled---" + username);
                    return new ResponseFormat("Failed", "User is disabled");
                }
            } else {
                logger.error("User does not exists with this username---" + username);
                return new ResponseFormat("Failed", "User does not exists with this username.");
            }
        } catch (Exception e) {
            logger.error("Error while generating Token for forgot password", e);
            return new ResponseFormat("Failed", "Cound not generate Token");
        }
    }

    @PostMapping("/confirmForgotPasswordToken")
    public ResponseFormat confirmForgotPasswordToken(@RequestBody EmailUser user) {
        try {
            ForgotPasswordToken fpt = this.userService.getForgotPasswordToken(user.getUsername(), user.getToken());
            logger.error("token---" + user.getToken());
            logger.error("token response---" + fpt.isValidForTriggering());
            if (fpt.isValidForTriggering()) {
                logger.error("Inside if---");
                this.userService.updateTriggeredDateForForgotPasswordToken(user.getUsername(), user.getToken());
                return new ResponseFormat("Success", "");
            } else {
                logger.error("Inside else---");
                this.userService.updateCompletionDateForForgotPasswordToken(user.getUsername(), user.getToken());
                return new ResponseFormat("Failed", fpt.inValidReasonForTriggering());
            }
        } catch (Exception e) {
            logger.error("Error while generating Token for forgot password", e);
            return new ResponseFormat("Failed", "Could not validate token");
        }
    }

    @PostMapping("/updatePassword")
    public ResponseFormat updatePaassword(@RequestBody EmailUser user) {
        try {
            ForgotPasswordToken fpt = this.userService.getForgotPasswordToken(user.getUsername(), user.getToken());
            if (fpt.isValidForCompletion()) {
                // Go ahead and reset the password
                CustomUserDetails curUser = this.userService.getCustomUserByUsername(user.getUsername());
                BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
                if (bcrypt.matches(user.getPassword(), curUser.getPassword())) {
                    return new ResponseFormat("Failed", "New password is same as current password.");
                } else {
                    this.userService.updatePassword(user.getUsername(), user.getToken(), user.getHashPassword(), 90);
                    return new ResponseFormat("Success", "Password updated successfully!!!");
                }
            } else {
                return new ResponseFormat("Failed", fpt.inValidReasonForCompletion());
            }
        } catch (Exception e) {
            logger.error("Error while generating Token for forgot password", e);
            return new ResponseFormat("Failed", "Cound not update password");
        }
    }

    @PutMapping(value = "/addNewRole")
    public ResponseEntity addNewRole(@RequestBody(required = true) String json) throws UnsupportedEncodingException {
        Map<String, Object> responseMap = null;
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            Gson g = new Gson();
            Role role = g.fromJson(json, Role.class);
            int row = this.userService.addRole(role);
            if (row > 0) {
                responseFormat.setStatus("Success");
                responseFormat.setMessage("Role created successfully.");
                return new ResponseEntity(responseFormat, HttpStatus.OK);
            } else {
                responseFormat.setStatus("failed");
                responseFormat.setMessage("Exception Occured. Please try again");
                return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Role already exists");
            return new ResponseEntity(responseFormat, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            e.printStackTrace();
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/editRole")
    public ResponseEntity editRole(@RequestBody(required = true) String json) throws UnsupportedEncodingException {
        Map<String, Object> responseMap = null;
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            Gson g = new Gson();
            Role role = g.fromJson(json, Role.class);
            int row = this.userService.updateRole(role);
            if (row > 0) {
                responseFormat.setStatus("Success");
                responseFormat.setMessage("Role updated successfully.");
                return new ResponseEntity(responseFormat, HttpStatus.OK);
            } else {
                responseFormat.setStatus("failed");
                responseFormat.setMessage("Exception Occured. Please try again");
                return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Role already exists");
            return new ResponseEntity(responseFormat, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            e.printStackTrace();
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/logout")
    public ResponseFormat logout(HttpServletRequest request) {
        try {
            final String requestTokenHeader = request.getHeader(this.tokenHeader);
            String jwtToken;
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7);
                this.userService.addTokenToLogout(jwtToken);
                return new ResponseFormat("Successfully logged out");
            } else {
                return new ResponseFormat("Failed", "Could not logout - Invalid token");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseFormat("Failed", "Exception Occured :" + e.getClass());
        }
    }
}
