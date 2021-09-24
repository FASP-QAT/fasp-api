/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import static jxl.biff.BaseCellFeatures.logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
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
public class TestRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private AclService aclService;
    @Autowired
    private ProgramService programService;

    /**
     * API used to check for user access to specified programIds
     *
     * @param programIdList Array of programIds to check access for
     * @param auth
     *
     * @return returns true if user has access and false if user does not access
     */
    @Operation(description = "API used to check for user access to specified programIds", summary = "Check for user access to specified programIds", tags = ("user"))
    @Parameters(
            @Parameter(name = "programIdList", description = "Array of programIds to check access for "))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a true if user has access and false if user does not access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access to get the program")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping(path = "/aclTest")
    public ResponseEntity postCheckAccessToProgram(@RequestBody Integer[] programIdList, Authentication auth) {
        StringBuilder sb = new StringBuilder();
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            for (int p : programIdList) {
                sb.append("\nChecking for access to " + p + "\n");
                try {
                    Program prog = this.programService.getProgramById(p, curUser);
                    boolean access = this.aclService.checkAccessForUser(
                            curUser,
                            prog.getRealmCountry().getRealm().getRealmId(),
                            prog.getRealmCountry().getRealmCountryId(),
                            prog.getHealthAreaIdList(),
                            prog.getOrganisation().getId(),
                            prog.getProgramId());
                    sb.append(p + " access = " + access + "\n");
                } catch (AccessDeniedException ae) {
                    sb.append("Could not get the Program so cant check for access");
                }
            }
            return new ResponseEntity(sb.toString(), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Supplier", ae);
            sb.append(ae.getMessage());
            return new ResponseEntity(sb.toString(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            sb.append(e.getMessage());
            logger.error("Error while trying to add Supplier", e);
            return new ResponseEntity(sb.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to check for user access to specified programIds using query
     *
     * @param programIdList Array of programIds to check access for
     * @param auth
     *
     * @return returns List of program if user has access
     */
    @Operation(description = "API used to check for user access to specified programIds using query", summary = "Check for user access to specified programIds using query", tags = ("user"))
    @Parameters(
            @Parameter(name = "programIdList", description = "Array of programIds to check access for "))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a List of program if user has access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access to get the program")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping(path = "/aclTestQuery")
    public ResponseEntity postCheckAccessViaQuery(@RequestBody String[] programIdList, Authentication auth) {
        StringBuilder sb = new StringBuilder();
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramListForProgramIds(programIdList, curUser), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Supplier", ae);
            sb.append(ae.getMessage());
            return new ResponseEntity(sb.toString(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            sb.append(e.getMessage());
            logger.error("Error while trying to add Supplier", e);
            return new ResponseEntity(sb.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
