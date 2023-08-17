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
import static jxl.biff.BaseCellFeatures.logger;
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
public class TestRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private AclService aclService;
    @Autowired
    private ProgramService programService;
    @Autowired
    private EmailService emailService;

    @PostMapping(path = "/aclTest")
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
            return new ResponseEntity(sb.toString(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            sb.append(e.getMessage());
            logger.error("Error while trying to add Supplier", e);
            return new ResponseEntity(sb.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

    @GetMapping(path = "/sendTestEmail/{emailerId}")
    public ResponseEntity sendTestEmail(@PathVariable(value = "emailerId", required = true) int emailerId) {
        Emailer e = this.emailService.getEmailByEmailerId(emailerId);
        this.emailService.sendMail(e);
        return new ResponseEntity(HttpStatus.OK);
    }
}
