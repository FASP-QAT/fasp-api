/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.jwt.JwtTokenUtil;
import cc.altius.FASP.jwt.resource.JwtTokenResponse;
import cc.altius.FASP.model.BusinessFunction;
import cc.altius.FASP.model.CanCreateRole;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EmailUser;
import cc.altius.FASP.model.ForgotPasswordToken;
import cc.altius.FASP.model.Password;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Role;
import cc.altius.FASP.model.User;
import cc.altius.FASP.security.CustomUserDetailsService;
import cc.altius.FASP.service.UserService;
import cc.altius.utils.PassPhrase;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io", "chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop"})
public class UserRestController {
    
    private final Logger auditLogger = LoggerFactory.getLogger(UserRestController.class);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private UserService userService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${session.expiry.time}")
    private int sessionExpiryTime;
    @Value("${jwt.http.request.header}")
    private String tokenHeader;
    
    @GetMapping(value = "/role")
    public ResponseEntity getRoleList() {
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
            return new ResponseEntity(roleList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Role", e);
            return new ResponseEntity(new ResponseCode("static.message.role.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping(value = "/role")
    public ResponseEntity addNewRole(@RequestBody Role role, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int row = this.userService.addRole(role, curUser);
            if (row > 0) {
                auditLogger.error(role + " added successfully");
                return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
            } else {
                auditLogger.error("Could not add " + role + " 0 rows updated");
                return new ResponseEntity(new ResponseCode("static.message.role.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            auditLogger.error("Could no add " + role, e);
            return new ResponseEntity(new ResponseCode("static.message.role.alreadyExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            auditLogger.error("Could not add " + role, e);
            return new ResponseEntity(new ResponseCode("static.message.role.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping(value = "/role")
    public ResponseEntity editRole(@RequestBody Role role, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int row = this.userService.updateRole(role, curUser);
            if (row > 0) {
                auditLogger.error(role + " updated successfully");
                return new ResponseEntity(new ResponseCode("static.message.role.updatedSuccess"), HttpStatus.OK);
            } else {
                auditLogger.error("Could not updated " + role + " 0 rows updated");
                return new ResponseEntity(new ResponseCode("static.message.role.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            auditLogger.error("Error while trying to Add Role", e);
            return new ResponseEntity(new ResponseCode("static.message.role.alreadyExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            auditLogger.error("Error while trying to Add Role", e);
            return new ResponseEntity(new ResponseCode("static.message.role.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/businessFunction")
    public ResponseEntity getBusinessFunctionList() {
        try {
            return new ResponseEntity(this.userService.getBusinessFunctionList(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Could not get BF list", e);
            return new ResponseEntity(new ResponseCode("static.message.role.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/user")
    public ResponseEntity getUserList() {
        try {
            List<User> userList = this.userService.getUserList();
            for (User user : userList) {
                String[] roleId = new String[user.getRoleList().size()];
                int i = 0;
                for (Role b : user.getRoleList()) {
                    roleId[i] = b.getRoleId();
                    i++;
                }
                user.setRoles(roleId);
                i = 0;
            }
            return new ResponseEntity(userList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Could not get User list", e);
            return new ResponseEntity(new ResponseCode("static.message.user.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/user/realmId/{realmId}")
    public ResponseEntity getUserList(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.userService.getUserListForRealm(realmId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Could not get User list for RealmId=" + realmId, e);
            return new ResponseEntity(new ResponseCode("static.message.user.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Could not get User list for RealmId=" + realmId, e);
            return new ResponseEntity(new ResponseCode("static.message.user.listFailed"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Could not get User list for RealmId=" + realmId, e);
            return new ResponseEntity(new ResponseCode("static.message.user.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/user/{userId}")
    public ResponseEntity getUserByUserId(@PathVariable int userId) {
        try {
            return new ResponseEntity(this.userService.getUserByUserId(userId), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error(("Could not get User list for UserId=" + userId));
            return new ResponseEntity(new ResponseCode("static.message.user.listFailed"), HttpStatus.UNAUTHORIZED);
        } catch (EmptyResultDataAccessException e) {
            logger.error(("Could not get User list for UserId=" + userId));
            return new ResponseEntity(new ResponseCode("static.message.user.notFound"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(("Could not get User list for UserId=" + userId));
            return new ResponseEntity(new ResponseCode("static.message.user.notFound"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping(value = "/user")
    public ResponseEntity addUser(@RequestBody User user, Authentication authentication, HttpServletRequest request) {
        CustomUserDetails curUser = (CustomUserDetails) authentication.getPrincipal();
        auditLogger.info("Adding new User " + user.toString(), request.getRemoteAddr(), curUser.getUsername());
        try {
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
                        auditLogger.info("Could not generate a Token for the new user");
                        return new ResponseEntity(new ResponseCode("static.message.user.tokenFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
                    } else {
                        auditLogger.info("User has been created and credentials link sent on email");
                        return new ResponseEntity(new ResponseCode("static.message.user.addSuccess"), HttpStatus.OK);
                    }
                } else {
                    auditLogger.info("Failed to add the User");
                    return new ResponseEntity(new ResponseCode("static.message.user.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                auditLogger.info("Failed to add the User beacuse the Username already exists");
                return new ResponseEntity(new ResponseCode("static.message.user.alreadyExists"), HttpStatus.PRECONDITION_FAILED);
            }
        } catch (DuplicateKeyException e) {
            auditLogger.error("Error", e);
            auditLogger.info("Failed to add the User");
            return new ResponseEntity(new ResponseCode("static.message.user.alreadyExists"), HttpStatus.PRECONDITION_FAILED);
        } catch (Exception e) {
            auditLogger.error("Error", e);
            auditLogger.info("Failed to add the User");
            return new ResponseEntity(new ResponseCode("static.message.user.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping(value = "/user")
    public ResponseEntity editUser(@RequestBody User user, Authentication authentication, HttpServletRequest request) {
        CustomUserDetails curUser = (CustomUserDetails) authentication.getPrincipal();
        auditLogger.info("Going to update User " + user.toString(), request.getRemoteAddr(), curUser.getUsername());
        try {
            int row = this.userService.updateUser(user, curUser.getUserId());
            if (row > 0) {
                auditLogger.info("User updated successfully");
                return new ResponseEntity(new ResponseCode("static.message.user.updateSuccess"), HttpStatus.OK);
            } else {
                auditLogger.info("User could not be updated");
                return new ResponseEntity(new ResponseCode("static.message.user.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            auditLogger.info("User could not be updated, Username already exists");
            return new ResponseEntity(new ResponseCode("static.message.user.alreadyExists"), HttpStatus.PRECONDITION_FAILED);
        } catch (Exception e) {
            auditLogger.info("User could not be updated", e);
            return new ResponseEntity(new ResponseCode("static.message.user.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping(value = "/unlockAccount/{userId}/{emailId}")
    public ResponseEntity unlockAccount(@PathVariable int userId, @PathVariable String emailId, Authentication authentication, HttpServletRequest request) throws UnsupportedEncodingException {
        CustomUserDetails curUser = (CustomUserDetails) authentication.getPrincipal();
        auditLogger.info("Going to unlock account for userId: " + userId + " emailId:" + emailId, request.getRemoteAddr(), curUser.getUsername());
        try {
            User user = this.userService.getUserByUserId(userId);
            if (!user.getEmailId().equals(emailId)) {
                auditLogger.info("Incorrect emailId or UserId");
                return new ResponseEntity(new ResponseCode("static.message.login.incorrectEmailUser"), HttpStatus.UNAUTHORIZED);
            }
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String password = PassPhrase.getPassword();
            String hashPass = encoder.encode(password);
            int row = this.userService.unlockAccount(userId, hashPass);
            if (row > 0) {
                String token = this.userService.generateTokenForUsername(user.getUsername(), 1);
                if (token == null || token.isEmpty()) {
                    auditLogger.info("User could not be unlocked as Token could not be generated");
                    return new ResponseEntity(new ResponseCode("static.message.user.tokenNotGenerated"), HttpStatus.INTERNAL_SERVER_ERROR);
                } else {
                    auditLogger.info("User unlocked and email sent with credentials link");
                    return new ResponseEntity(new ResponseCode("static.message.user.accountUnlocked"), HttpStatus.OK);
                }
            } else {
                auditLogger.info("User could not be unlocked");
                return new ResponseEntity(new ResponseCode("static.message.user.couldNotBeUnlocked"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            auditLogger.info("User could not be unlocked", e);
            return new ResponseEntity(new ResponseCode("static.message.user.couldNotBeUnlocked"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping(value = "/updateExpiredPassword")
    public ResponseEntity updateExpiredPassword(@RequestBody Password password) {
        try {
            if (password.getOldPassword().equals(password.getNewPassword())) {
                return new ResponseEntity(new ResponseCode("static.message.user.passwordSame"), HttpStatus.PRECONDITION_FAILED);
            }
            if (!this.userService.confirmPassword(password.getUsername(), password.getOldPassword().trim())) {
                return new ResponseEntity(new ResponseCode("static.message.user.incorrectPassword"), HttpStatus.UNAUTHORIZED);
            } else {
                final CustomUserDetails userDetails = this.customUserDetailsService.loadUserByUsername(password.getUsername());
                PasswordEncoder encoder = new BCryptPasswordEncoder();
                String hashPass = encoder.encode(password.getNewPassword());
                password.setNewPassword(hashPass);
                int row = this.userService.updatePassword(userDetails.getUserId(), password.getNewPassword(), 90);
                if (row > 0) {
                    userDetails.setSessionExpiresOn(sessionExpiryTime);
                    final String token = jwtTokenUtil.generateToken(userDetails);
                    return ResponseEntity.ok(new JwtTokenResponse(token));
                } else {
                    return new ResponseEntity(new ResponseCode("static.message.user.failedPasswordUpdate"), HttpStatus.PRECONDITION_FAILED);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity(new ResponseCode("static.message.user.failedPasswordUpdate"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping(value = "/changePassword")
    public ResponseEntity changePassword(@RequestBody Password password) {
        try {
            User user = this.userService.getUserByUserId(password.getUserId());
            if (!this.userService.confirmPassword(user.getUsername(), password.getOldPassword().trim())) {
                return new ResponseEntity(new ResponseCode("static.message.user.incorrectPassword"), HttpStatus.UNAUTHORIZED);
            } else {
                PasswordEncoder encoder = new BCryptPasswordEncoder();
                String hashPass = encoder.encode(password.getNewPassword());
                password.setNewPassword(hashPass);
                int row = this.userService.updatePassword(password.getUserId(), password.getNewPassword(), 90);
                if (row > 0) {
                    Map<String, String> params = new HashMap<>();
                    params.put("hashPass", hashPass);
                    return new ResponseEntity(params, HttpStatus.OK);
                } else {
                    return new ResponseEntity(new ResponseCode("static.message.user.failedPasswordUpdate"), HttpStatus.PRECONDITION_FAILED);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity(new ResponseCode("static.message.user.failedPasswordUpdate"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/forgotPassword/{username}")
    public ResponseEntity forgotPassword(@PathVariable String username, HttpServletRequest request) {
        auditLogger.info("Forgot password action triggered for Username:" + username, request.getRemoteAddr());
        try {
            CustomUserDetails customUser = this.userService.getCustomUserByUsername(username);
            if (customUser != null) {
                if (customUser.isActive()) {
                    String token = this.userService.generateTokenForUsername(username, 1);
                    if (token == null || token.isEmpty()) {
                        auditLogger.info("Could not process request as Token could not be generated");
                        return new ResponseEntity(new ResponseCode("static.message.user.tokenFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
                    } else {
                        auditLogger.info("Forgot password request processed for Username: " + username + " email with password reset link sent");
                        Map<String, String> params = new HashMap<>();
                        params.put("token", token);
                        return new ResponseEntity(params, HttpStatus.OK);
                    }
                } else {
                    auditLogger.info("User is disabled Username: " + username);
                    return new ResponseEntity(new ResponseCode("static.message.user.disabled"), HttpStatus.UNAUTHORIZED);
                }
            } else {
                auditLogger.info("User does not exists with this Username " + username);
                return new ResponseEntity(new ResponseCode("static.message.user.notExist"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            auditLogger.info("Error while generating Token for forgot password", e);
            return new ResponseEntity(new ResponseCode("static.message.user.forgotPasswordTokenFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/confirmForgotPasswordToken")
    public ResponseEntity confirmForgotPasswordToken(@RequestBody EmailUser user, HttpServletRequest request) {
        try {
            ForgotPasswordToken fpt = this.userService.getForgotPasswordToken(user.getUsername(), user.getToken());
            auditLogger.info("Confirm forgot password has been triggered for Username:" + user.getUsername(), request.getRemoteAddr());
            if (fpt.isValidForTriggering()) {
                this.userService.updateTriggeredDateForForgotPasswordToken(user.getUsername(), user.getToken());
                auditLogger.info("Token is valid and reset can proceed");
                return new ResponseEntity(HttpStatus.OK);
            } else {
                this.userService.updateCompletionDateForForgotPasswordToken(user.getUsername(), user.getToken());
                auditLogger.info("Token is not valid or has expired");
                return new ResponseEntity(new ResponseCode(fpt.inValidReasonForTriggering()), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            auditLogger.info("Could not confirm Token", e);
            return new ResponseEntity(new ResponseCode("static.message.user.forgotPasswordTokenError"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/updatePassword")
    public ResponseEntity updatePaassword(@RequestBody EmailUser user, HttpServletRequest request) {
        try {
            auditLogger.info("Update password triggered for Username: " + user.getUsername(), request.getRemoteAddr());
            ForgotPasswordToken fpt = this.userService.getForgotPasswordToken(user.getUsername(), user.getToken());
            if (fpt.isValidForCompletion()) {
                // Go ahead and reset the password
                CustomUserDetails curUser = this.userService.getCustomUserByUsername(user.getUsername());
                BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
                if (bcrypt.matches(user.getPassword(), curUser.getPassword())) {
                    auditLogger.info("Failed to reset the password because New password is same as current password");
                    return new ResponseEntity(new ResponseCode("static.message.user.passwordSame"), HttpStatus.PRECONDITION_FAILED);
                } else {
                    this.userService.updatePassword(user.getUsername(), user.getToken(), user.getHashPassword(), 90);
                    auditLogger.info("Password has now been updated successfully for Username: " + user.getUsername());
                    return new ResponseEntity(new ResponseCode("static.message.user.passwordSuccess"), HttpStatus.OK);
                }
            } else {
                auditLogger.info("Failed to reset the password invlaid Token");
                return new ResponseEntity(new ResponseCode(fpt.inValidReasonForCompletion()), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            auditLogger.info("Could not update password", e);
            return new ResponseEntity(new ResponseCode("static.message.user.forgotPasswordTokenError"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/logout")
    public ResponseEntity logout(Authentication authentication, HttpServletRequest request) {
        CustomUserDetails curUser = (CustomUserDetails) authentication.getPrincipal();
        try {
            auditLogger.info("Received a Logout request for Username: " + curUser.getUsername(), request.getRemoteAddr());
            final String requestTokenHeader = request.getHeader(this.tokenHeader);
            String jwtToken;
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7);
                this.userService.addTokenToLogout(jwtToken);
                auditLogger.info("Successfully logged out Username: " + curUser.getUsername());
                return new ResponseEntity(HttpStatus.OK);
            } else {
                auditLogger.info("Could not logout Invalid Token Username: " + curUser.getUsername());
                return new ResponseEntity(new ResponseCode("static.message.user.logoutFailed"), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            auditLogger.info("Error while trying to logout Username: " + curUser.getUsername(), e);
            return new ResponseEntity(new ResponseCode("static.message.user.logoutFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/checkIfUserExists")
    public ResponseEntity checkIfUserExists(HttpServletRequest request) throws UnsupportedEncodingException {
        try {
            String username = request.getHeader("username");
            String password = request.getHeader("password");
            if (username == null || password == null) {
                return new ResponseEntity("static.message.user.usernameOrPassword", HttpStatus.NOT_ACCEPTABLE);
            }
            Map<String, Object> responseMap = this.userService.checkIfUserExists(username, password);
            CustomUserDetails customUserDetails = (CustomUserDetails) responseMap.get("customUserDetails");
            if (customUserDetails != null) {
                return new ResponseEntity(customUserDetails, HttpStatus.OK);
            } else {
                return new ResponseEntity("static.message.user.notFound", HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            logger.error("Error", e);
            return new ResponseEntity("static.message.user.listFailed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
