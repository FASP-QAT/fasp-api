/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.jwt.JwtTokenUtil;
import cc.altius.FASP.jwt.resource.JwtTokenResponse;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EmailUser;
import cc.altius.FASP.model.ForgotPasswordToken;
import cc.altius.FASP.model.LanguageUser;
import cc.altius.FASP.model.Password;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Role;
import cc.altius.FASP.model.User;
import cc.altius.FASP.security.CustomUserDetailsService;
import cc.altius.FASP.service.UserService;
import cc.altius.utils.PassPhrase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.HashMap;
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
@RequestMapping("/api/user")
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

    /**
     * API used to get the userDetails for current user
     *
     * @param auth
     * @return returns User object
     */
    @Operation(description = "API used to get the userDetails for current user", summary = "Get userDetails for current user", tags = ("user"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the User")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of User")
    @GetMapping(value = "/userDetails")
    public ResponseEntity getUserDetails(Authentication auth) {
        CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
        try {
            return new ResponseEntity(this.userService.getUserByUserId(curUser.getUserId(), curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get User details", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the Role list for current user
     *
     * @param auth
     * @return returns the Role list
     */
    @Operation(description = "API used to get the Role list for current user", summary = "Get Role list for current user", tags = ("user"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Role list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Role list")
    @GetMapping(value = "/role")
    public ResponseEntity getRoleList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.userService.getRoleList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Role", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the Role object for a specific roleId
     *
     * @param roleId roleId that you want the Role Object for
     * @return returns the Role object for a specific roleId
     */
    @Operation(description = "API used to get the Role object for a specific roleId", summary = "Get Role object for a specific roleId", tags = ("user"))
    @Parameters(
            @Parameter(name = "roleId", description = "roleId that you want the Role Object for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Role object")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Role")
    @GetMapping(value = "/role/{roleId}")
    public ResponseEntity getRoleById(@PathVariable("roleId") String roleId) {
        try {
            return new ResponseEntity(this.userService.getRoleById(roleId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Role for roleId", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to add a Role
     *
     * @param role role object that you want to add
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to add a Role", summary = "Add Role", tags = ("user"))
    @Parameters(
            @Parameter(name = "role", description = "role object that you want to add"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the Role object supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping(value = "/role")
    public ResponseEntity addNewRole(@RequestBody Role role, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            int row = this.userService.addRole(role, curUser);
            if (row > 0) {
                auditLogger.error(role + " added successfully");
                return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
            } else {
                auditLogger.error("Could not add " + role + " 0 rows updated");
                return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            auditLogger.error("Could no add " + role, e);
            return new ResponseEntity(new ResponseCode("static.message.alreadyExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            auditLogger.error("Could not add " + role, e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to update a Role
     *
     * @param role role object that you want to update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to update a Role", summary = "Update Role", tags = ("user"))
    @Parameters(
            @Parameter(name = "role", description = "role object that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the Role object supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PutMapping(value = "/role")
    public ResponseEntity editRole(@RequestBody Role role, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            int row = this.userService.updateRole(role, curUser);
            if (row > 0) {
                auditLogger.error(role + " updated successfully");
                return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
            } else {
                auditLogger.error("Could not updated " + role + " 0 rows updated");
                return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            auditLogger.error("Error while trying to Add Role", e);
            return new ResponseEntity(new ResponseCode("static.message.alreadyExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            auditLogger.error("Error while trying to Add Role", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the BusinessFunction list
     *
     * @return returns the BusinessFunction list
     */
    @Operation(description = "API used to get the BusinessFunction list", summary = "Get BusinessFunction list", tags = ("user"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the BusinessFunction list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of BusinessFunction list")
    @GetMapping(value = "/businessFunction")
    public ResponseEntity getBusinessFunctionList() {
        try {
            return new ResponseEntity(this.userService.getBusinessFunctionList(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Could not get BF list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the User list
     *
     * @param auth
     * @return returns the User list
     */
    @Operation(description = "API used to get the User list", summary = "Get User list", tags = ("user"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the User list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of User list")
    @GetMapping(value = "/")
    public ResponseEntity getUserList(Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.userService.getUserList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Could not get User list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the User list for specified realmId
     *
     * @param realmId realmId that you want the User list for
     * @param auth
     *
     * @return returns the User list for specified realmId
     */
    @Operation(description = "API used to get the User list for specified realmId", summary = "Get the User list for specified realmId", tags = ("user"))
    @Parameters(
            @Parameter(name = "realmId", description = "RealmId that you want the User list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the User list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the some of the underlying data does not match.")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access to get the User list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of User list")
    @GetMapping(value = "/realmId/{realmId}")
    public ResponseEntity getUserList(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.userService.getUserListForRealm(realmId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Could not get User list for RealmId=" + realmId, e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Could not get User list for RealmId=" + realmId, e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Could not get User list for RealmId=" + realmId, e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the User object for specified userId
     *
     * @param userId userId that you want the User object for
     * @param auth
     *
     * @return returns the User object for specified userId
     */
    @Operation(description = "API used to get the User object for specified userId", summary = "Get the User object for specified userId", tags = ("user"))
    @Parameters(
            @Parameter(name = "realmId", description = "UserId that you want the User object for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the User object")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the some of the underlying data does not match.")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access to get the User list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of User object")
    @GetMapping(value = "/{userId}")
    public ResponseEntity getUserByUserId(@PathVariable int userId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            auditLogger.info("userId " + userId);
            return new ResponseEntity(this.userService.getUserByUserId(userId, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error(("Could not get User list for UserId=" + userId));
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (EmptyResultDataAccessException e) {
            logger.error(("Could not get User list for UserId=" + userId));
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(("Could not get User list for UserId=" + userId));
            auditLogger.info("Could not get User list for UserId=" + e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to add a User
     *
     * @param user user object that you want to add
     * @param authentication
     * @param request
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to add a user", summary = "Add User", tags = ("user"))
    @Parameters(
            @Parameter(name = "user", description = "user object that you want to add"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "412", description = "Returns a HttpStatus.PRECONDITION_FAILED if the Username or email id already exists")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping(value = "/")
    public ResponseEntity addUser(@RequestBody User user, Authentication authentication, HttpServletRequest request) {
        CustomUserDetails curUser = (CustomUserDetails) authentication.getPrincipal();
        auditLogger.info("Adding new User " + user.toString(), request.getRemoteAddr(), curUser.getUsername());
        try {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String password = PassPhrase.getPassword();
            String hashPass = encoder.encode(password);
            user.setPassword(hashPass);
            String msg = this.userService.checkIfUserExistsByEmailId(user, 1);
            if (msg.isEmpty()) {
                int userId = this.userService.addNewUser(user, curUser.getUserId());
                if (userId > 0) {
                    String token = this.userService.generateTokenForEmailId(user.getEmailId(), 2);
                    if (token == null || token.isEmpty()) {
                        auditLogger.info("Could not generate a Token for the new user");
                        return new ResponseEntity(new ResponseCode("static.message.tokenNotGenerated"), HttpStatus.INTERNAL_SERVER_ERROR);
                    } else {
                        auditLogger.info("User has been created and credentials link sent on email");
                        return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
                    }
                } else {
                    auditLogger.info("Failed to add the User");
                    return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                auditLogger.info("Failed to add the User beacuse the Username or email id already exists");
                return new ResponseEntity(new ResponseCode(msg), HttpStatus.PRECONDITION_FAILED);
            }
        } catch (Exception e) {
            auditLogger.error("Error", e);
            auditLogger.info("Failed to add the User");
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to update a User
     *
     * @param user user object that you want to update
     * @param authentication
     * @param request
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to update a user", summary = "Update User", tags = ("user"))
    @Parameters(
            @Parameter(name = "user", description = "user object that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "412", description = "Returns a HttpStatus.PRECONDITION_FAILED if the Username or email id already exists")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PutMapping(value = "/")
    public ResponseEntity editUser(@RequestBody User user, Authentication authentication, HttpServletRequest request) {
        CustomUserDetails curUser = (CustomUserDetails) authentication.getPrincipal();
        auditLogger.info("Going to update User " + user.toString(), request.getRemoteAddr(), curUser.getUsername());
        try {
            String msg = this.userService.checkIfUserExistsByEmailId(user, 2);
            if (msg.isEmpty()) {
                int row = this.userService.updateUser(user, curUser.getUserId());
                if (row > 0) {
                    auditLogger.info("User updated successfully");
                    return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
                } else {
                    auditLogger.info("User could not be updated");
                    return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                auditLogger.info("Failed to add the User beacuse the Username or email id already exists");
                return new ResponseEntity(new ResponseCode(msg), HttpStatus.PRECONDITION_FAILED);
            }
        } catch (Exception e) {
            auditLogger.info("User could not be updated", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PutMapping(value = "/unlockAccount/{userId}/{emailId}")
//    public ResponseEntity unlockAccount(@PathVariable int userId, @PathVariable String emailId, Authentication authentication, HttpServletRequest request) throws UnsupportedEncodingException {
//        CustomUserDetails curUser = (CustomUserDetails) authentication.getPrincipal();
//        auditLogger.info("Going to unlock account for userId: " + userId + " emailId:" + emailId, request.getRemoteAddr(), curUser.getUsername());
//        try {
//            User user = this.userService.getUserByUserId(userId, curUser);
//            if (!user.getEmailId().equals(emailId)) {
//                auditLogger.info("Incorrect emailId or UserId");
//                return new ResponseEntity(new ResponseCode("static.message.accountUnlocked"), HttpStatus.OK);
//            }
//            PasswordEncoder encoder = new BCryptPasswordEncoder();
//            String password = PassPhrase.getPassword();
//            String hashPass = encoder.encode(password);
//            int row = this.userService.unlockAccount(userId, hashPass);
//            if (row > 0) {
//                String token = this.userService.generateTokenForEmailId(user.getEmailId(), 1);
//                if (token == null || token.isEmpty()) {
//                    auditLogger.info("User could not be unlocked as Token could not be generated");
//                    return new ResponseEntity(new ResponseCode("static.message.tokenNotGenerated"), HttpStatus.INTERNAL_SERVER_ERROR);
//                } else {
//                    auditLogger.info("User unlocked and email sent with credentials link");
//                    return new ResponseEntity(new ResponseCode("static.message.accountUnlocked"), HttpStatus.OK);
//                }
//            } else {
//                auditLogger.info("User could not be unlocked");
//                return new ResponseEntity(new ResponseCode("static.message.tokenNotGenerated"), HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        } catch (Exception e) {
//            auditLogger.info("User could not be unlocked", e);
//            return new ResponseEntity(new ResponseCode("static.message.tokenNotGenerated"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    /**
     * API used to update expired password
     *
     * @param password password object that you want to update
     *
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to update expired password", summary = "Update Expired Password", tags = ("user"))
    @Parameters(
            @Parameter(name = "password", description = "password object that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "412", description = "Returns a HttpStatus.PRECONDITION_FAILED if the old password and new password does not match")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the passwords does not match")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping(value = "/updateExpiredPassword")
    public ResponseEntity updateExpiredPassword(@RequestBody Password password) {
        try {
            if (password.getOldPassword().equals(password.getNewPassword())) {
                return new ResponseEntity(new ResponseCode("static.message.passwordSame"), HttpStatus.PRECONDITION_FAILED);
            }
            if (!this.userService.confirmPassword(password.getEmailId(), password.getOldPassword().trim())) {
                return new ResponseEntity(new ResponseCode("static.message.incorrectPassword"), HttpStatus.FORBIDDEN);
            } else {
                final CustomUserDetails userDetails = this.customUserDetailsService.loadUserByUsername(password.getEmailId());
                PasswordEncoder encoder = new BCryptPasswordEncoder();
                String hashPass = encoder.encode(password.getNewPassword());
                password.setNewPassword(hashPass);
                int row = this.userService.updatePassword(userDetails.getUserId(), password.getNewPassword(), 365);
                if (row > 0) {
                    userDetails.setSessionExpiresOn(sessionExpiryTime);
                    userDetails.setPassword(hashPass);
                    final String token = jwtTokenUtil.generateToken(userDetails);
                    this.userService.resetFailedAttemptsByUsername(password.getEmailId());
                    this.userService.updateSuncExpiresOn(password.getEmailId());
                    return ResponseEntity.ok(new JwtTokenResponse(token));
                } else {
                    return new ResponseEntity(new ResponseCode("static.message.failedPasswordUpdate"), HttpStatus.PRECONDITION_FAILED);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity(new ResponseCode("static.message.failedPasswordUpdate"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to change password
     *
     * @param password password object that you want to change
     * @param auth
     *
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to update changed password", summary = "Change password", tags = ("user"))
    @Parameters(
            @Parameter(name = "password", description = "password object that you want to change"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "412", description = "Returns a HttpStatus.PRECONDITION_FAILED if the old password and new password does not match")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping(value = "/changePassword")
    public ResponseEntity changePassword(@RequestBody Password password, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            User user = this.userService.getUserByUserId(password.getUserId(), curUser);
            if (!this.userService.confirmPassword(user.getEmailId(), password.getOldPassword().trim())) {
                return new ResponseEntity(new ResponseCode("static.message.incorrectPassword"), HttpStatus.PRECONDITION_FAILED);
            } else {
                PasswordEncoder encoder = new BCryptPasswordEncoder();
                String hashPass = encoder.encode(password.getNewPassword());
                password.setNewPassword(hashPass);
                int row = this.userService.updatePassword(password.getUserId(), password.getNewPassword(), 365);
                if (row > 0) {
                    Map<String, String> params = new HashMap<>();
                    params.put("hashPass", hashPass);
                    return new ResponseEntity(params, HttpStatus.OK);
                } else {
                    return new ResponseEntity(new ResponseCode("static.message.failedPasswordUpdate"), HttpStatus.PRECONDITION_FAILED);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity(new ResponseCode("static.message.failedPasswordUpdate"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to reset forgot password
     *
     * @param user EmailUser object for which password has to reset
     * @param request
     *
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to reset forgot password", summary = "Reset forgot password", tags = ("user"))
    @Parameters(
            @Parameter(name = "user", description = "EmailUser object for which password has to reset "))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User is disabled")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if User does not exists with given Email Id.")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping(value = "/forgotPassword")
    public ResponseEntity forgotPassword(@RequestBody EmailUser user, HttpServletRequest request) {
        auditLogger.info("Forgot password action triggered for Email Id:" + user.getEmailId(), request.getRemoteAddr());
        try {
            CustomUserDetails customUser = this.userService.getCustomUserByEmailId(user.getEmailId());
            if (customUser != null) {
                if (customUser.isActive()) {
                    String token = this.userService.generateTokenForEmailId(user.getEmailId(), 1);
                    if (token == null || token.isEmpty()) {
                        auditLogger.info("Could not process request as Token could not be generated");
                        return new ResponseEntity(new ResponseCode("static.message.tokenNotGenerated"), HttpStatus.INTERNAL_SERVER_ERROR);
                    } else {
                        auditLogger.info("Forgot password request processed for Email Id: " + user.getEmailId() + " email with password reset link sent");
                        Map<String, String> params = new HashMap<>();
                        params.put("token", token);
                        return new ResponseEntity(params, HttpStatus.OK);
                    }
                } else {
                    auditLogger.info("User is disabled Email Id: " + user.getEmailId());
                    return new ResponseEntity(new ResponseCode("static.message.user.disabled"), HttpStatus.FORBIDDEN);
                }
            } else {
                auditLogger.info("User does not exists with this Email Id " + user.getEmailId());
                return new ResponseEntity(new ResponseCode("static.message.user.forgotPasswordSuccess"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            auditLogger.info("Error while generating Token for forgot password", e);
            return new ResponseEntity(new ResponseCode("static.message.tokenNotGenerated"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to confirm if forgot password token is valid
     *
     * @param user EmailUser object for which token has to validate
     * @param request
     *
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to confirm if forgot password token is valid", summary = "Confirm Forgot Password", tags = ("user"))
    @Parameters(
            @Parameter(name = "user", description = "EmailUser object for which token has to validate"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping("/confirmForgotPasswordToken")
    public ResponseEntity confirmForgotPasswordToken(@RequestBody EmailUser user, HttpServletRequest request) {
        try {
            logger.info("------------------------------------------------------ Reset password Start ----------------------------------------------------");
            ForgotPasswordToken fpt = this.userService.getForgotPasswordToken(user.getEmailId(), user.getToken());
            auditLogger.info("Confirm forgot password has been triggered for EmailId:" + user.getEmailId() + " with ForgotPasswordToken:" + fpt, request.getRemoteAddr());
//            if (fpt.isValidForCompletion()) {
            logger.info("fpt.isValidForCompletion()=true");
            auditLogger.info("Token is valid and reset can proceed");
//                this.userService.updateTriggeredDateForForgotPasswordToken(user.getEmailId(), user.getToken());
            return new ResponseEntity(HttpStatus.OK);
//            } else {
//                System.out.println("---------------3-----------");
//                logger.info("fpt.isValidForCompletion()=true");
//                auditLogger.info("Token is not valid or has expired");
//                this.userService.updateCompletionDateForForgotPasswordToken(user.getEmailId(), user.getToken());
//                return new ResponseEntity(new ResponseCode(fpt.inValidReasonForTriggering()), HttpStatus.FORBIDDEN);
//            }
        } catch (Exception e) {
            auditLogger.info("Could not confirm Token", e);
            return new ResponseEntity(new ResponseCode("static.message.forgotPasswordTokenError"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to update password
     *
     * @param user EmailUser object for which password has to update
     * @param request
     *
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to update password", summary = "Update password", tags = ("user"))
    @Parameters(
            @Parameter(name = "user", description = "EmailUser object for which password has to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the token is invalid")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "412", description = "Returns a HttpStatus.PRECONDITION_FAILED if the new password is same as current password")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping("/updatePassword")
    public ResponseEntity updatePassword(@RequestBody EmailUser user, HttpServletRequest request) {
        try {
            auditLogger.info("Update password triggered for Email: " + user.getEmailId(), request.getRemoteAddr());
            ForgotPasswordToken fpt = this.userService.getForgotPasswordToken(user.getEmailId(), user.getToken());
            logger.info("ForgotPasswordToken is " + fpt);
            System.out.println("ForgotPasswordToken is " + fpt.toString());
            if (fpt.isValidForCompletion()) {
                logger.info("fpt.isValidForCompletion()=true");
                System.out.println("fpt.isValidForCompletion()=true");
                // Go ahead and reset the password
                CustomUserDetails curUser = this.userService.getCustomUserByEmailId(user.getEmailId());
                logger.info("Current userL " + curUser);
                BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
                if (bcrypt.matches(user.getPassword(), curUser.getPassword())) {
                    auditLogger.info("Failed to reset the password because New password is same as current password");
                    System.out.println("Failed to reset the password because New password is same as current password");
                    return new ResponseEntity(new ResponseCode("static.message.user.previousPasswordSame"), HttpStatus.PRECONDITION_FAILED);
                } else {
                    logger.info("Password provided is valid and we can proceed");
                    System.out.println("Password provided is valid and we can proceed");
                    this.userService.updatePassword(user.getEmailId(), user.getToken(), user.getHashPassword(), 365);
                    auditLogger.info("Password has now been updated successfully for Username: " + user.getUsername());
                    System.out.println("Password has now been updated successfully for Username: " + user.getUsername());
                    return new ResponseEntity(new ResponseCode("static.message.passwordSuccess"), HttpStatus.OK);
                }
            } else {
                logger.info("fpt.isValidForCompletion()=false");
                auditLogger.info("Failed to reset the password invlaid Token");
                return new ResponseEntity(new ResponseCode(fpt.inValidReasonForCompletion()), HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            auditLogger.info("Could not update password", e);
            return new ResponseEntity(new ResponseCode("static.message.user.forgotPasswordTokenError"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to logout
     *
     * @param authentication
     * @param request
     *
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to logout", summary = "Logout", tags = ("user"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if token is invalid")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
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
                return new ResponseEntity(new ResponseCode("static.message.logoutFailed"), HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            auditLogger.info("Error while trying to logout Username: " + curUser.getUsername(), e);
            return new ResponseEntity(new ResponseCode("static.message.logoutFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping(value = "/checkIfUserExists")
//    public ResponseEntity checkIfUserExists(HttpServletRequest request) throws UnsupportedEncodingException {
//        try {
//            String username = request.getHeader("username");
//            String password = request.getHeader("password");
//            if (username == null || password == null) {
//                return new ResponseEntity("static.message.notExist", HttpStatus.NOT_ACCEPTABLE);
//            }
//            Map<String, Object> responseMap = this.userService.checkIfUserExists(username, password);
//            CustomUserDetails customUserDetails = (CustomUserDetails) responseMap.get("customUserDetails");
//            if (customUserDetails != null) {
//                return new ResponseEntity(customUserDetails, HttpStatus.OK);
//            } else {
//                return new ResponseEntity("static.message.listFailed", HttpStatus.NOT_ACCEPTABLE);
//            }
//        } catch (Exception e) {
//            logger.error("Error", e);
//            return new ResponseEntity("static.message.listFailed", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    /**
     * API used to map access controls like realmCountry, organisation,
     * healthArea and program with specified user
     *
     * @param user user object for which access control need to map with
     * @param auth
     *
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to map access controls like realmCountry, organisation, healthArea and program with specified user", summary = "Map Access Control to User", tags = ("user"))
    @Parameters(
            @Parameter(name = "user", description = "user object for which access control need to map with"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PutMapping(value = "/accessControls")
    public ResponseEntity accessControl(@RequestBody User user, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            int row = this.userService.mapAccessControls(user, curUser);
            if (row > 0) {
                auditLogger.error(user + " updated successfully");
                return new ResponseEntity(new ResponseCode("static.message.accessControlSuccess"), HttpStatus.OK);
            } else if (row == -2) {
                auditLogger.error("Either add All access or specific access " + user);
                return new ResponseEntity(new ResponseCode("static.message.allAclAccess"), HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                auditLogger.error("Could not updated " + user + " 0 rows updated");
                return new ResponseEntity(new ResponseCode("static.message.updateFailedAcl"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            auditLogger.error("Duplicate Access Controls", e);
            return new ResponseEntity(new ResponseCode("static.message.user.duplicateacl"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            auditLogger.error("Error while trying to Add Access Controls", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailedAcl"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to update user language
     *
     * @param languageUser LanguageUser object that you want language to update
     * for
     * @param auth
     *
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to update user language", summary = "Update user language", tags = ("user"))
    @Parameters(
            @Parameter(name = "languageUser", description = "LanguageUser object that you want language to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping("/language")
    public ResponseEntity updateUserLanguage(@RequestBody LanguageUser languageUser, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            auditLogger.info("Update language change triggered for Username: " + curUser.getUsername());
            this.userService.updateUserLanguage(curUser.getUserId(), languageUser.getLanguageCode());
            auditLogger.info("Preferred language updated successfully for Username: " + curUser.getUsername());
            return new ResponseEntity("", HttpStatus.OK);
        } catch (Exception e) {
            auditLogger.info("Could not update preferred language", e);
            return new ResponseEntity(new ResponseCode("static.message.user.languageChangeError"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to update user agreement accepted
     *
     * @param auth
     *
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to update user agreement accepted", summary = "Update user agreement accepted", tags = ("user"))

    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping("/agreement")
    public ResponseEntity acceptUserAgreement(Authentication auth) {
        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            System.out.println("cur user---"+curUser);
            auditLogger.info("auth 1: " + (CustomUserDetails) auth.getPrincipal());
            auditLogger.info("auth 2: " + auth);
            auditLogger.info("auth 3: " + ((CustomUserDetails) auth.getPrincipal()).getUserId());
//            auditLogger.info("Update agreement for Username: " + curUser);
            this.userService.acceptUserAgreement(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            auditLogger.info("Agreement updated successfully for Username: " + curUser.getUsername());
            return new ResponseEntity("", HttpStatus.OK);
        } catch (Exception e) {
            auditLogger.info("Could not update agreement", e);
            return new ResponseEntity(new ResponseCode("static.message.user.languageChangeError"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
