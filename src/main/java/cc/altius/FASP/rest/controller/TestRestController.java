
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Role;
import cc.altius.FASP.model.SimpleProgram;
import cc.altius.FASP.model.User;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.EmailService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author akil
 */
@RestController
@Tag(
    name = "Test",
    description = "Test endpoints for ACL permissions and email functionality"
)
@RequestMapping("/api/test")
public class TestRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private AclService aclService;
    @Autowired
    private ProgramService programService;
    @Autowired
    private EmailService emailService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping(path = "/aclTest")
    @Operation(
        summary = "Check access to programs",
        description = "Check access to programs"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The program IDs to check access for",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Integer.class)))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = String.class)), responseCode = "200", description = "Returns the access check results")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "Access denied")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while checking access")
    public ResponseEntity postCheckAccessToProgram(@RequestBody Integer[] programIdList, Authentication auth) {
        StringBuffer url = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURL();
        System.out.println(url.toString());
        StringBuilder sb = new StringBuilder();
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            for (int p : programIdList) {
                sb.append("\nChecking for access to " + p + "\n");
                try {
                    SimpleProgram prog = this.programService.getSimpleProgramById(p, curUser);
                    boolean access = this.aclService.userHasAccessToResources(
                            curUser,
                            prog.getRealmId(),
                            prog.getRealmCountry().getId(),
                            prog.getHealthAreaIdList(),
                            prog.getOrganisation().getId(),
                            prog.getId());
                    sb.append(p + " access = " + access + "\n");
                } catch (AccessDeniedException ae) {
                    sb.append("Could not get the Program so cant check for access");
                }
            }
            return new ResponseEntity(sb.toString(), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Supplier", ae);
            sb.append(ae.getMessage());
            return new ResponseEntity(sb.toString(), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            sb.append(e.getMessage());
            logger.error("Error while trying to add Supplier", e);
            return new ResponseEntity(sb.toString(), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PostMapping(path = "/aclTestQuery")
    @Operation(
        summary = "Check access to programs via query",
        description = "Check access to programs via query"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The program IDs to check access for",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = String.class)), responseCode = "200", description = "Returns the access check results")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "Access denied")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while checking access")
    public ResponseEntity postCheckAccessViaQuery(@RequestBody String[] programIdList, Authentication auth) {
        StringBuilder sb = new StringBuilder();
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getProgramListForProgramIds(programIdList, curUser), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Supplier", ae);
            sb.append(ae.getMessage());
            return new ResponseEntity(sb.toString(), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            sb.append(e.getMessage());
            logger.error("Error while trying to add Supplier", e);
            return new ResponseEntity(sb.toString(), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping(path = "/buildSecurity")
    @Operation(
        summary = "Build security",
        description = "Build security"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = String.class)), responseCode = "200", description = "Returns the build security result")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while building security")
    public String buildSecurity() {
        int fail = this.aclService.buildSecurity();
        if (fail == 0) {
            return "Completed build";
        } else {
            return "Build failed for " + fail + " cases";
        }
    }

    @GetMapping(path = "/sendTestEmail/{emailerId}")
    @Operation(
        summary = "Send a test email",
        description = "Send a test email"
    )
    @Parameter(name = "emailerId", description = "The ID of the emailer to send the test email for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while sending the email")
    public ResponseEntity sendTestEmail(@PathVariable(value = "emailerId", required = true) int emailerId) {
        Emailer e = this.emailService.getEmailByEmailerId(emailerId);
        this.emailService.sendMail(e);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "/retrieveData")
    @Operation(
        summary = "Retrieve data",
        description = "Retrieve data"
    )
    @Parameter(name = "var", description = "The variable to retrieve data for", required = false)
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the data retrieved")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while retrieving the data")
    public String retrieveData(@RequestParam(value = "var", required = false) String var) {
        StringBuffer url = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURL();
        String uri = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI();
        String method = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod();
        StringBuilder sb = new StringBuilder();
        return sb
                .append("URL=").append(url.toString()).append("<br/>")
                .append("URI=").append(uri).append("<br/>")
                .append("METHOD=").append(method).append("<br/>")
                .append("var=").append(var).toString();
    }

    @GetMapping(path = "/canEdit/{userId}")
    @Operation(
        summary = "Check user edit permissions",
        description = "Check if a user can edit another user"
    )
    @Parameter(name = "userId", description = "The ID of the user to check if the current user can edit", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = String.class)), responseCode = "200", description = "Returns the edit check result")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while checking the edit")
    public String canEdit(@PathVariable("userId") int userId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), "GET", "/api/user");
            User user = this.userService.getUserByUserId(userId, curUser);
            Map<String, List<String>> canCreateRoleMap = new HashMap<>();
            for (Role role : curUser.getRoles()) {
                if (!canCreateRoleMap.containsKey(role.getRoleId())) {
                    canCreateRoleMap.put(role.getRoleId(), this.userService.getRoleById(role.getRoleId()).getCanCreateRoleList().stream().map(r1 -> r1.getRoleId()).toList());
                }
            }
            boolean result = this.aclService.canEditUser(user, curUser, canCreateRoleMap);
            return "The user " + curUser.getEmailId() + " can edit the user " + user.getEmailId() + " check - " + result;
        } catch (AccessControlFailedException ex) {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), "GET", "/api/user");
            return "The user " + curUser.getEmailId() + " can edit the user Id " + userId + " check - " + false;
        }
    }

}
