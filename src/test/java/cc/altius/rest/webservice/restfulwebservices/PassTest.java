/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.rest.webservice.restfulwebservices;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author akil
 */
public class PassTest {
    public static void main(String[] args) {
        PasswordEncoder p = new BCryptPasswordEncoder();
        System.out.println(p.matches("pass","$2a$10$nYitLMRpZxm3fKObZ.j/E.dLfRqKtVwC4ZCOJd323mbcQkr2zDm7y"));
    }
    
}
