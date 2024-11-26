/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.model.SimpleProgram;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.EmailService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api")
@Tag(
    name = "Test",
    description = "Test endpoints for ACL permissions and email functionality"
)
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
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the access check results")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Access denied")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while checking access")
    public ResponseEntity postCheckAccessToProgram(@RequestBody Integer[] programIdList, Authentication auth) {
        StringBuilder sb = new StringBuilder();
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            for (int p : programIdList) {
                sb.append("\nChecking for access to " + p + "\n");
                try {
                    SimpleProgram prog = this.programService.getSimpleProgramById(p, curUser);
                    boolean access = this.aclService.checkAccessForUser(
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
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the access check results")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Access denied")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while checking access")
    public ResponseEntity postCheckAccessViaQuery(@RequestBody String[] programIdList, Authentication auth) {
        StringBuilder sb = new StringBuilder();
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
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

    @GetMapping(path = "/sendTestEmail/{emailerId}")
    @Operation(
        summary = "Send a test email",
        description = "Send a test email"
    )
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the email sent status")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while sending the email")
    public ResponseEntity sendTestEmail(@PathVariable(value = "emailerId", required = true) int emailerId) {
        Emailer e = this.emailService.getEmailByEmailerId(emailerId);
        this.emailService.sendMail(e);
        return new ResponseEntity(HttpStatus.OK);
    }
}
