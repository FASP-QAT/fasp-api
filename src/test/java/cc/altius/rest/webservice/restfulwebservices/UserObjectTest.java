/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.rest.webservice.restfulwebservices;

import cc.altius.FASP.model.Language;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.Role;
import cc.altius.FASP.model.User;
import cc.altius.FASP.model.UserAcl;
import java.util.Date;
import java.util.LinkedList;



/**
 *
 * @author akil
 */
public class UserObjectTest {
    public static void main(String[] args) {
        User user = new User();
        user.setUsername("anchal");
        user.setActive(true);
        user.setEmailId("anchal.c@altius.cc");
        user.setLanguage(new Language(1));
        user.setPhoneNumber("9820517741");
        user.setRealm(new Realm(1));
        user.setRole(new Role("ROLE_APPLICATION_ADMIN"));
        user.setUserId(1);
        user.setLastLoginDate(new Date());
        LinkedList<UserAcl> aclList = new LinkedList<>();
        aclList.add(new UserAcl(1, 1, 1, 1, -1));
    }
}
