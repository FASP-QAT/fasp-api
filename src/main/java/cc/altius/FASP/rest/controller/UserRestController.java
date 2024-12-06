/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.jwt.JwtTokenUtil;
import cc.altius.FASP.jwt.resource.JwtTokenResponse;
import cc.altius.FASP.model.BfAndProgramId;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EmailUser;
import cc.altius.FASP.model.ForgotPasswordToken;
import cc.altius.FASP.model.LanguageUser;
import cc.altius.FASP.model.Password;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Role;
import cc.altius.FASP.model.User;
import cc.altius.FASP.model.BusinessFunction;
import cc.altius.FASP.model.UserAcl;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.security.CustomUserDetailsService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.service.UserService;
import cc.altius.utils.PassPhrase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api")
@Tag(
    name = "User",
    description = "Manage users, authentication, roles and access controls"
)
public class UserRestController {

    private final Logger auditLogger = LoggerFactory.getLogger(UserRestController.class);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private ProgramService programService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${session.expiry.time}")
    private int sessionExpiryTime;
    @Value("${jwt.http.request.header}")
    private String tokenHeader;

    /**
     * Get list of Roles
     *
     * @param auth
     * @return
     */
    @JsonView(Views.ReportView.class)
    @GetMapping(value = "/role")
    @Operation(
        summary = "Get Roles",
        description = "Get role list"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Role.class))), responseCode = "200", description = "Returns the role list")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting the role list")
    public ResponseEntity getRoleList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.userService.getRoleList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Role", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get Role by Id
     *
     * @param roleId
     * @return
     */
    @JsonView(Views.ReportView.class)
    @GetMapping(value = "/role/{roleId}")
    @Operation(
        summary = "Get Role",
        description = "Get role by ID"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Role.class)), responseCode = "200", description = "Returns the role")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting the role")
    public ResponseEntity getRoleById(@PathVariable("roleId") String roleId) {
        try {
            return new ResponseEntity(this.userService.getRoleById(roleId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Role for roleId", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Add Role
     *
     * @param role
     * @param auth
     * @return
     */
    @PostMapping(value = "/role")
    @Operation(
        summary = "Add Role",
        description = "Add new role"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "Role already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while adding the role")
    public ResponseEntity addNewRole(@RequestBody Role role, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            int row = this.userService.addRole(role, curUser);
            if (row > 0) {
                auditLogger.error(role + " added successfully");
                return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
            } else {
                auditLogger.error("Could not add " + role + " 0 rows updated");
                return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
            }
        } catch (DuplicateKeyException e) {
            auditLogger.error("Could no add " + role, e);
            return new ResponseEntity(new ResponseCode("static.message.alreadyExists"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (Exception e) {
            auditLogger.error("Could not add " + role, e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Update Role
     *
     * @param role
     * @param auth
     * @return
     */
    @PutMapping(value = "/role")
    @Operation(
        summary = "Update Role",
        description = "Update role"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The role to update",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "Role already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating the role")
    public ResponseEntity editRole(@RequestBody Role role, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
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
     * Get list of Business functions
     *
     * @return
     */
    @GetMapping(value = "/businessFunction")
    @Operation(
        summary = "Get business function list",
        description = "Get business function list"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = BusinessFunction.class))), responseCode = "200", description = "Returns the business function list")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting the business function list")
    public ResponseEntity getBusinessFunctionList() {
        try {
            return new ResponseEntity(this.userService.getBusinessFunctionList(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Could not get BF list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Used after the User has Logged in to retrieve the ACL and other data for
     * this user
     *
     * @param auth
     * @return
     */
    @GetMapping(value = "/user/details")
    @Operation(
        summary = "Get user details",
        description = "Get user details including ACL"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = User.class)), responseCode = "200", description = "Returns the user details")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting the user details")
    public ResponseEntity getUserDetails(Authentication auth) {
        logger.info("getCustomUserByUserIdForApi==>" + ((CustomUserDetails) auth.getPrincipal()).getUserId() + "==" + ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod() + "==" + ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());

        CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
        logger.info("CustomUserDetails==>" + curUser);
        try {
            User loggedInUser = this.userService.getUserByUserId(curUser.getUserId(), curUser);
            cc.altius.FASP.model.UserDetails ud = new cc.altius.FASP.model.UserDetails();
            ud.setUser(loggedInUser);
            Map<String, BfAndProgramId> bfAndProgramMap = new HashMap<>();
            ud.setBfAndProgramIdMap(bfAndProgramMap);
            Map<String, List<String>> aclBfMap = this.userService.getAclRoleBfList(curUser.getUserId(), curUser);
            for (String role : aclBfMap.keySet()) {
                if (bfAndProgramMap.containsKey(role)) {
                    bfAndProgramMap.get(role).getBusinessFunctionList().addAll(aclBfMap.get(role));
                } else {
                    BfAndProgramId bfAndProgramId = new BfAndProgramId();
                    bfAndProgramId.getBusinessFunctionList().addAll(aclBfMap.get(role));
                    bfAndProgramMap.put(role, bfAndProgramId);
                }
            }

            for (UserAcl acl : loggedInUser.getUserAclList()) {
                curUser.getAclList().clear();
                curUser.getAclList().add(acl);
                HashSet<Integer> programSet;
                String role = acl.getRoleId();
                if (role == null) {
                    role = "";
                }
                bfAndProgramMap.get(role).getProgramIdList().addAll(this.programService.getProgramListForDropdown(curUser.getRealm().getRealmId(), 0, true, curUser, false).stream().map(p -> p.getId()).toList());
            }
            return new ResponseEntity(ud, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get User details", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get User list
     *
     * @param auth
     * @return
     */
    @JsonView(Views.UserListView.class)
    @GetMapping(value = "/user")
    @Operation(
        summary = "Get User List",
        description = "Get user list"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = User.class))), responseCode = "200", description = "Returns the user list")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting the user list")
    public ResponseEntity getUserList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.userService.getUserList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Could not get User list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get User list for Realm
     *
     * @param realmId
     * @param auth
     * @return
     */
    @JsonView(Views.UserListView.class)
    @GetMapping(value = "/user/realmId/{realmId}")
    @Operation(
        summary = "Get User List for Realm",
        description = "Get user list for realm"
    )
    @Parameter(name = "realmId", description = "The realm ID")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = User.class))), responseCode = "200", description = "Returns the user list for realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have access to this realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "User list not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting the user list")
    public ResponseEntity getUserList(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.userService.getUserListForRealm(realmId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Could not get User list for RealmId=" + realmId, e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Could not get User list for RealmId=" + realmId, e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Could not get User list for RealmId=" + realmId, e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of Users that have access to a Program
     *
     * @param programId
     * @param auth
     * @return
     */
    @GetMapping(value = "/user/programId/{programId}")
    @Operation(
        summary = "Get Users for Program",
        description = "Get list of Users that have access to a Program"
    )
    @Parameter(name = "programId", description = "The program ID")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = User.class))), responseCode = "200", description = "Returns the user list for program")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "User list not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have access to this program")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting the user list")
    public ResponseEntity getUserListForProgram(@PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.userService.getUserListForProgram(programId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Could not get User list for ProgramId=" + programId, e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Could not get User list for ProgramId=" + programId, e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Could not get User list for ProgramId=" + programId, e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get User by Id
     *
     * @param userId
     * @param auth
     * @return
     */
    @GetMapping(value = "/user/{userId}")
    @Operation(
        summary = "Get User",
        description = "Get user by ID"
    )
    @Parameter(name = "userId", description = "The user ID")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = User.class)), responseCode = "200", description = "Returns the user by user ID")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have access to this user")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "User not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "409", description = "User has partial acccess to the request")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting the user")
    public ResponseEntity getUserByUserId(@PathVariable int userId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            auditLogger.info("userId " + userId);
            return new ResponseEntity(this.userService.getUserByUserId(userId, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error(("Could not get User for UserId=" + userId));
            auditLogger.error(("Could not get User list for UserId=" + userId));
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (AccessControlFailedException e) {
            logger.error(("Could not get User for UserId=" + userId));
            auditLogger.error(("Could not get User list for UserId=" + userId));
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.CONFLICT); // 409
        } catch (EmptyResultDataAccessException e) {
            logger.error(("Could not get User for UserId=" + userId));
            auditLogger.error(("Could not get User list for UserId=" + userId));
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error(("Could not get User for UserId=" + userId));
            auditLogger.info("Could not get User list for UserId=" + e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Add User
     *
     * @param user
     * @param authentication
     * @param request
     * @return
     */
    @PostMapping(value = "/user")
    @Operation(
        summary = "Add User",
        description = "Add user"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The user to add",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "409", description = "The user has partial acccess to the request")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "412", description = "User or username already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while adding the user")
    public ResponseEntity addUser(@RequestBody User user, Authentication auth, HttpServletRequest request) {
        CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
        auditLogger.info("Adding new User " + user.toString(), request.getRemoteAddr(), curUser.getUsername());
        try {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String password = PassPhrase.getPassword();
            String hashPass = encoder.encode(password);
            user.setPassword(hashPass);
            String msg = this.userService.checkIfUserExistsByEmailId(user, 1);
            if (msg.isEmpty()) {
                int userId = this.userService.addNewUser(user, curUser);
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
                    return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
                }
            } else {
                auditLogger.info("Failed to add the User beacuse the Username or email id already exists");
                return new ResponseEntity(new ResponseCode(msg), HttpStatus.PRECONDITION_FAILED); // 412
            }
        } catch (AccessControlFailedException acfe) {
            auditLogger.error(acfe.getMessage());
            return new ResponseEntity(new ResponseCode("static.message.aclFailed"), HttpStatus.CONFLICT); // 409
        } catch (DuplicateKeyException e) {
            auditLogger.error("Duplicate Access Controls", e);
            return new ResponseEntity(new ResponseCode("static.message.user.duplicateacl"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        } catch (Exception e) {
            auditLogger.error("Error", e);
            auditLogger.info("Failed to add the User");
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Update User
     *
     * @param user
     * @param authentication
     * @param request
     * @return
     */
    @PutMapping(value = "/user")
    @Operation(
        summary = "Update User",
        description = "Update user"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The user to edit",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "409", description = "The user has partial acccess to the request")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "412", description = "The username or email id already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while editing the user")
    public ResponseEntity editUser(@RequestBody User user, Authentication auth, HttpServletRequest request) {
        CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
        auditLogger.info("Going to update User " + user.toString(), request.getRemoteAddr(), curUser.getUsername());
        try {
            String msg = this.userService.checkIfUserExistsByEmailId(user, 2);
            if (msg.isEmpty()) {
                int row = this.userService.updateUser(user, curUser);
                if (row > 0) {
                    auditLogger.info("User updated successfully");
                    return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK); // 200
                } else {
                    auditLogger.info("User could not be updated");
                    return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
                }
            } else {
                auditLogger.info("Failed to add the User beacuse the Username or email id already exists");
                return new ResponseEntity(new ResponseCode(msg), HttpStatus.PRECONDITION_FAILED); // 412
            }
        } catch (AccessControlFailedException acfe) {
            auditLogger.error(acfe.getMessage());
            return new ResponseEntity(new ResponseCode("static.message.aclFailed"), HttpStatus.CONFLICT); // 409
        } catch (Exception e) {
            auditLogger.info("User could not be updated", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Update a new passwords when a password has expired for a user
     *
     * @param password
     * @return
     */
    @PostMapping(value = "/user/updateExpiredPassword")
    @Operation(
        summary = "Update expired password",
        description = "Update expired password"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The password to update",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Password.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "412", description = "New password is same as old password")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "Incorrect old password")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating the password")
    public ResponseEntity updateExpiredPassword(@RequestBody Password password) {
        try {
            if (password.getOldPassword().equals(password.getNewPassword())) {
                return new ResponseEntity(new ResponseCode("static.message.passwordSame"), HttpStatus.PRECONDITION_FAILED); // 412
            }
            if (!this.userService.confirmPassword(password.getEmailId(), password.getOldPassword().trim())) {
                return new ResponseEntity(new ResponseCode("static.message.incorrectPassword"), HttpStatus.FORBIDDEN); // 403
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
                    return new ResponseEntity(new ResponseCode("static.message.failedPasswordUpdate"), HttpStatus.PRECONDITION_FAILED); // 412
                }
            }
        } catch (Exception e) {
            return new ResponseEntity(new ResponseCode("static.message.failedPasswordUpdate"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Update a new password for the user
     *
     * @param password
     * @param auth
     * @return
     */
    @PostMapping(value = "/user/changePassword")
    @Operation(
        summary = "Change password",
        description = "Change password"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The password to change",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Password.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "412", description = "New password is same as old password")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while changing the password")
    public ResponseEntity changePassword(@RequestBody Password password, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            User user = this.userService.getUserByUserId(password.getUserId(), curUser);
            if (curUser.getUserId() != password.getUserId() || !this.userService.confirmPassword(user.getEmailId(), password.getOldPassword().trim())) {
                return new ResponseEntity(new ResponseCode("static.message.incorrectPassword"), HttpStatus.PRECONDITION_FAILED); // 412
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
                    return new ResponseEntity(new ResponseCode("static.message.failedPasswordUpdate"), HttpStatus.PRECONDITION_FAILED); // 412
                }
            }
        } catch (Exception e) {
            return new ResponseEntity(new ResponseCode("static.message.failedPasswordUpdate"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Sends out the Forgot password email to the registered emailId
     *
     * @param user
     * @param request
     * @return
     */
    @PostMapping(value = "/user/forgotPassword")
    @Operation(
        summary = "Get forgot password token",
        description = "Generates a forgot password token and sends it via email"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The email id to forgot password",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmailUser.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User is disabled")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while generating the forgot password token")
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
                    return new ResponseEntity(new ResponseCode("static.message.user.disabled"), HttpStatus.FORBIDDEN); // 403
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
     * Used to validate the token when the link in the forgot password email is
     * clicked
     *
     * @param user
     * @param request
     * @return
     */
    @PostMapping(value = "/user/confirmForgotPasswordToken")
    @Operation(
        summary = "Confirm forgot password token",
        description = "Confirm forgot password token"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The email id and token to confirm",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmailUser.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while confirming the forgot password token")
    public ResponseEntity confirmForgotPasswordToken(@RequestBody EmailUser user, HttpServletRequest request) {
        try {
            logger.info("------------------------------------------------------ Reset password Start ----------------------------------------------------");
            ForgotPasswordToken fpt = this.userService.getForgotPasswordToken(user.getEmailId(), user.getToken());
            auditLogger.info("Confirm forgot password has been triggered for EmailId:" + user.getEmailId() + " with ForgotPasswordToken:" + fpt, request.getRemoteAddr());
            logger.info("fpt.isValidForCompletion()=true");
            auditLogger.info("Token is valid and reset can proceed");
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            auditLogger.info("Could not confirm Token", e);
            return new ResponseEntity(new ResponseCode("static.message.forgotPasswordTokenError"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update a new password from forgot password
     *
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/user/updatePassword")
    @Operation(
        summary = "Update password",
        description = "Update password from forgot password"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The email id and token to update password",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmailUser.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "Invalid token")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "412", description = "New password is same as current password")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating the password")
    public ResponseEntity updatePassword(@RequestBody EmailUser user, HttpServletRequest request) {
        try {
            auditLogger.info("Update password triggered for Email: " + user.getEmailId(), request.getRemoteAddr());
            ForgotPasswordToken fpt = this.userService.getForgotPasswordToken(user.getEmailId(), user.getToken());
            logger.info("ForgotPasswordToken is " + fpt);
            if (fpt.isValidForCompletion()) {
                logger.info("fpt.isValidForCompletion()=true");
                // Go ahead and reset the password
                CustomUserDetails curUser = this.userService.getCustomUserByEmailId(user.getEmailId());
                logger.info("Current userL " + curUser);
                BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
                if (bcrypt.matches(user.getPassword(), curUser.getPassword())) {
                    auditLogger.info("Failed to reset the password because New password is same as current password");
                    return new ResponseEntity(new ResponseCode("static.message.user.previousPasswordSame"), HttpStatus.PRECONDITION_FAILED); // 412
                } else {
                    logger.info("Password provided is valid and we can proceed");
                    this.userService.updatePassword(user.getEmailId(), user.getToken(), user.getHashPassword(), 365);
                    auditLogger.info("Password has now been updated successfully for Username: " + user.getUsername());
                    return new ResponseEntity(new ResponseCode("static.message.passwordSuccess"), HttpStatus.OK);
                }
            } else {
                logger.info("fpt.isValidForCompletion()=false");
                auditLogger.info("Failed to reset the password invlaid Token");
                return new ResponseEntity(new ResponseCode(fpt.inValidReasonForCompletion()), HttpStatus.FORBIDDEN); // 403
            }
        } catch (Exception e) {
            auditLogger.info("Could not update password", e);
            return new ResponseEntity(new ResponseCode("static.message.user.forgotPasswordTokenError"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Log a user out
     *
     * @param authentication
     * @param request
     * @return
     */
    @GetMapping(value = "/logout")
    @Operation(
        summary = "Logout",
        description = "Logout"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "Invalid token")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while logging out")
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
                return new ResponseEntity(new ResponseCode("static.message.logoutFailed"), HttpStatus.FORBIDDEN); // 403
            }
        } catch (Exception e) {
            auditLogger.info("Error while trying to logout Username: " + curUser.getUsername(), e);
            return new ResponseEntity(new ResponseCode("static.message.logoutFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets the list of Access controls for all Users
     *
     * @param user
     * @param auth
     * @return
     */
    @JsonView(Views.UserListView.class)
    @GetMapping(value = "/user/accessControls")
    public ResponseEntity accessControl(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.userService.getAccessControls(curUser), HttpStatus.OK);
        } catch (DuplicateKeyException e) {
            auditLogger.error("Duplicate Access Controls", e);
            return new ResponseEntity(new ResponseCode("static.message.user.duplicateacl"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            auditLogger.error("Error while trying to Add Access Controls", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailedAcl"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the list of Access controls for a User
     *
     * @param user
     * @param auth
     * @return
     */
    @PutMapping(value = "/user/accessControls")
    @Operation(
        summary = "Update access controls",
        description = "Update user access controls"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The user to update access controls",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating the access controls")
    public ResponseEntity accessControl(@RequestBody User user, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            int row = this.userService.mapAccessControls(user, curUser);
            if (row > 0) {
                auditLogger.error(user + " updated successfully");
                return new ResponseEntity(new ResponseCode("static.message.accessControlSuccess"), HttpStatus.OK);
            } else if (row == -2) {
                auditLogger.error("Either add All access or specific access " + user);
                return new ResponseEntity(new ResponseCode("static.message.allAclAccess"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
            } else {
                auditLogger.error("Could not updated " + user + " 0 rows updated");
                return new ResponseEntity(new ResponseCode("static.message.updateFailedAcl"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            auditLogger.error("Duplicate Access Controls", e);
            return new ResponseEntity(new ResponseCode("static.message.user.duplicateacl"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        } catch (Exception e) {
            auditLogger.error("Error while trying to Add Access Controls", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailedAcl"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Sets the default language used by a User
     *
     * @param languageUser
     * @param auth
     * @return
     */
    @PostMapping(value = "/user/language")
    @Operation(
        summary = "Update user language",
        description = "Update user default language"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The language to update",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = LanguageUser.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns an empty response if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating the preferred language")
    public ResponseEntity updateUserLanguage(@RequestBody LanguageUser languageUser, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
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
     * Sets the default module used by a User
     *
     * @param moduleId
     * @param auth
     * @return
     */
    @PostMapping(value = "/user/module/{moduleId}")
    @Operation(
        summary = "Update user module",
        description = "Update user default module"
    )
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns an empty response if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating the default module")
    public ResponseEntity updateUserModule(@PathVariable int moduleId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            auditLogger.info("Update Module change triggered for Username: " + curUser.getUsername());
            this.userService.updateUserModule(curUser.getUserId(), moduleId);
            auditLogger.info("Default Module updated successfully for Username: " + curUser.getUsername());
            return new ResponseEntity("", HttpStatus.OK);
        } catch (Exception e) {
            auditLogger.info("Could not update default module", e);
            return new ResponseEntity(new ResponseCode("static.message.user.moduleChangeError"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Sets the Theme that the user wants
     *
     * @param themeId
     * @param auth
     * @return
     */
    @PostMapping("/user/theme/{themeId}")
    @Operation(
        summary = "Update user theme",
        description = "Update user default theme"
    )
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns an empty response if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating the default theme")
    public ResponseEntity updateUserTheme(@PathVariable int themeId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            auditLogger.info("Update Theme change triggered for Username: " + curUser.getUsername());
            this.userService.updateUserTheme(curUser.getUserId(), themeId);
            auditLogger.info("Default Theme updated successfully for Username: " + curUser.getUsername());
            return new ResponseEntity("", HttpStatus.OK);
        } catch (Exception e) {
            auditLogger.info("Could not update default theme", e);
            return new ResponseEntity(new ResponseCode("static.message.user.themeChangeError"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user/decimalPreference/{showDecimals}")
    @Operation(
        summary = "Update user decimal preference",
        description = "Update user decimal preference"
    )
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns an empty response if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating the decimal preference")
    public ResponseEntity updateUserDecimalPreference(@PathVariable boolean showDecimals, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            auditLogger.info("Update Theme change triggered for Username: " + curUser.getUsername());
            this.userService.updateUserDecimalPreference(curUser.getUserId(), showDecimals);
            auditLogger.info("Default Theme updated successfully for Username: " + curUser.getUsername());
            return new ResponseEntity("", HttpStatus.OK);
        } catch (Exception e) {
            auditLogger.info("Could not update default theme", e);
            return new ResponseEntity(new ResponseCode("static.message.user.decimalPreferenceChangeError"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the I agree field for a User
     *
     * @param auth
     * @return
     */
    @PostMapping(value = "/user/agreement")
    @Operation(
        summary = "Accept user agreement",
        description = "Accept user agreement"
    )
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns an empty response if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating the agreement")
    public ResponseEntity acceptUserAgreement(Authentication auth) {
        try {
            this.userService.acceptUserAgreement(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity("", HttpStatus.OK);
        } catch (Exception e) {
            auditLogger.info("Could not update agreement", e);
            return new ResponseEntity(new ResponseCode("static.message.user.languageChangeError"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
