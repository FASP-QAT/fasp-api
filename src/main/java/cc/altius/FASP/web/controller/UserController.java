/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.model.Role;
import cc.altius.FASP.model.User;
import cc.altius.FASP.service.UserService;
import cc.altius.utils.PassPhrase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.boot.autoconfigure.AutoConfigurationPackages.register;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping(value = "/getRoleList")
    public String getRoleList() {
        String json = null;
        try {
            List<Role> roleList = this.userService.getRoleList();
            System.out.println("role---" + roleList);
            Gson gson = new Gson();
            Type typeList = new TypeToken<List>() {
            }.getType();
            json = gson.toJson(roleList, typeList);
        } catch (Exception e) {
            System.out.println("error---------" + e);
        }
        return json;
    }

    @PutMapping(value = "/addNewUser")
    public ResponseEntity addNewUser(@RequestBody(required = true) String json) throws UnsupportedEncodingException {
        Map<String, Object> responseMap = null;
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            Gson g = new Gson();
            System.out.println("json---" + json);
            User user = g.fromJson(json, User.class);
            System.out.println("user---" + user);
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashPass = encoder.encode(PassPhrase.getPassword());
            user.setPassword(hashPass);
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
        } catch (Exception e) {
            e.printStackTrace();
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
