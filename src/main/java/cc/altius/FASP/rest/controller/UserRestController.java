/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EmailUser;
import cc.altius.FASP.model.ForgotPasswordToken;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author akil
 */
@RestController
public class UserRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    @PostMapping("/api/user/getForgotPasswordToken")
    public ResponseFormat doForgotPassword(@RequestBody EmailUser user) {
        try {
            String token = this.userService.generateTokenForUsername(user.getUsername());
            user.setToken(token);
            if (token == null || token.isEmpty()) {
                return new ResponseFormat("Failed", "Cound not generate Token");
            } else {
                return new ResponseFormat("Success", "Email with password reset link sent", user);
            }
        } catch (Exception e) {
            logger.error("Error while generating Token for forgot password", e);
            return new ResponseFormat("Failed", "Cound not generate Token");
        }
    }

    @PostMapping("/api/user/confirmForgotPasswordToken")
    public ResponseFormat confirmForgotPasswordToken(@RequestBody EmailUser user) {
        try {
            ForgotPasswordToken fpt = this.userService.getForgotPasswordToken(user.getUsername(), user.getToken());
            if (fpt.isValidForTriggering()) {
                this.userService.updateTriggeredDateForForgotPasswordToken(user.getUsername(), user.getToken());
                return new ResponseFormat("Success", "");
            } else {
                this.userService.updateCompletionDateForForgotPasswordToken(user.getUsername(), user.getToken());
                return new ResponseFormat("Failed", fpt.inValidReasonForTriggering());
            }
        } catch (Exception e) {
            logger.error("Error while generating Token for forgot password", e);
            return new ResponseFormat("Failed", "Could not validate token");
        }
    }

    @PostMapping("/api/user/updatePassword")
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
                    return new ResponseFormat("Success", "");
                }
            } else {
                return new ResponseFormat("Failed", fpt.inValidReasonForCompletion());
            }
        } catch (Exception e) {
            logger.error("Error while generating Token for forgot password", e);
            return new ResponseFormat("Failed", "Cound not update password");
        }
    }

}
