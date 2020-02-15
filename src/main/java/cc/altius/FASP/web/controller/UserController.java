/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.BusinessFunction;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.model.Password;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.model.Role;
import cc.altius.FASP.model.User;
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
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = "http://localhost:4202")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @GetMapping(value = "/getRoleList")
    public String getRoleList() {
        String json = null;
        try {
            List<Role> roleList = this.userService.getRoleList();
            Gson gson = new Gson();
            Type typeList = new TypeToken<List>() {
            }.getType();
            json = gson.toJson(roleList, typeList);
        } catch (Exception e) {
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
    public ResponseEntity addNewUser(@RequestBody(required = true) String json) throws UnsupportedEncodingException {
        Map<String, Object> responseMap = null;
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            Gson g = new Gson();
            User user = g.fromJson(json, User.class);
            System.out.println("user------------" + Arrays.toString(user.getCountryIds()));
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashPass = encoder.encode(PassPhrase.getPassword());
            user.setPassword(hashPass);
            String msg = this.userService.checkIfUserExistsByEmailIdAndPhoneNumber(user, 1);
            if (msg.isEmpty()) {
                int userId = this.userService.addNewUser(user);
                if (userId > 0) {
                    responseFormat.setStatus("Success");
                    responseFormat.setMessage("User created successfully.");
                    return new ResponseEntity(responseFormat, HttpStatus.OK);
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
        } catch (Exception e) {
            e.printStackTrace();
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getUserList")
    public List<User> getUserList() throws UnsupportedEncodingException {
        try {
            List<User> userList = this.userService.getUserList();
            return userList;
        } catch (Exception e) {
            return null;
        }
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
    public ResponseEntity editUser(@RequestBody(required = true) String json) throws UnsupportedEncodingException {
        Map<String, Object> responseMap = null;
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            Gson g = new Gson();
            User user = g.fromJson(json, User.class);
            String msg = this.userService.checkIfUserExistsByEmailIdAndPhoneNumber(user, 2);
            if (msg.isEmpty()) {
                int row = this.userService.updateUser(user);
                if (row > 0) {
                    responseFormat.setStatus("Success");
                    responseFormat.setMessage("User updated successfully.");
                    return new ResponseEntity(responseFormat, HttpStatus.OK);
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
        } catch (Exception e) {
            e.printStackTrace();
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/unlockAccount")
    public ResponseEntity unlockAccount(@RequestBody(required = true) String json) throws UnsupportedEncodingException {
        Map<String, Object> responseMap = null;
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            Gson g = new Gson();
            User user = g.fromJson(json, User.class);
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashPass = encoder.encode(PassPhrase.getPassword());
            user.setPassword(hashPass);
            int row = this.userService.unlockAccount(user);
            if (row > 0) {
                responseFormat.setStatus("Success");
                responseFormat.setMessage("Account unlocked successfully and new password is sent on the registered email id.");
                return new ResponseEntity(responseFormat, HttpStatus.OK);
            } else {
                responseFormat.setStatus("failed");
                responseFormat.setMessage("Exception Occured. Please try again");
                return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/updateExpiredPassword")
    public ResponseEntity updateExpiredPassword(@RequestBody Password password) throws UnsupportedEncodingException {
        Map<String, Object> responseMap = null;
        System.out.println("password--------------" + password);
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            Gson g = new Gson();
            if (!this.userService.confirmPassword(password.getUserId(), password.getOldPassword())) {
                responseFormat.setStatus("Success");
                responseFormat.setMessage("Old password is incorrect.");
                return new ResponseEntity(responseFormat, HttpStatus.NOT_ACCEPTABLE);
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
                    responseFormat.setMessage("Exception Occured. Please try again");
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
    public ResponseEntity forgotPassword(@PathVariable String username) throws UnsupportedEncodingException {
        Map<String, Object> responseMap = null;
        System.out.println("username--------------" + username);
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            CustomUserDetails customUser = this.userService.getCustomUserByUsername(username);
            if (customUser != null) {
                String pass = PassPhrase.getPassword();
                PasswordEncoder encoder = new BCryptPasswordEncoder();
                String hashPass = encoder.encode(pass);
                int row = this.userService.updatePassword(customUser.getUserId(), hashPass, -1);
                if (row > 0) {

                    EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(1);
                    String[] subjectParam = new String[]{};
                    String[] bodyParam = new String[]{username, pass};
                    Emailer emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), customUser.getEmailId(), emailTemplate.getCcTo(), subjectParam, bodyParam);
                    int emailerId = this.emailService.saveEmail(emailer);
                    emailer.setEmailerId(emailerId);
                    this.emailService.sendMail(emailer);

                    responseFormat.setStatus("Success");
                    responseFormat.setMessage("New password sent on your registered email id.");
                    return new ResponseEntity(responseFormat, HttpStatus.OK);
                } else {
                    responseFormat.setStatus("failed");
                    responseFormat.setMessage("Exception Occured. Please try again");
                    return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                responseFormat.setStatus("failed");
                responseFormat.setMessage("User does not exists with this username.");
                return new ResponseEntity(responseFormat, HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
