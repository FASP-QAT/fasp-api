/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.service.UserService;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io","http://192.168.1.15:4202"})
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/checkIfUserExists")
    public ResponseEntity checkIfUserExists(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, Object> responseMap = null;
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            String username = request.getHeader("username");
            String password = request.getHeader("password");
            if (username == null || password == null) {
                responseFormat.setStatus("failed");
                responseFormat.setMessage("Username or Password not provided");
                return new ResponseEntity(responseFormat, HttpStatus.NOT_ACCEPTABLE);
            }
            responseMap = this.userService.checkIfUserExists(username, password);
            CustomUserDetails customUserDetails = (CustomUserDetails) responseMap.get("customUserDetails");
            if (customUserDetails != null) {
                return new ResponseEntity(customUserDetails, HttpStatus.OK);
            } else {
                responseFormat.setStatus("failed");
                responseFormat.setMessage(responseMap.get("message") + "");
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
