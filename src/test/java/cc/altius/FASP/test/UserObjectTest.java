/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.test;

import cc.altius.FASP.model.Language;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.Role;
import cc.altius.FASP.model.User;
import cc.altius.FASP.model.UserAcl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;



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
        user.setOrgAndCountry("MOH - Benin");
        user.setRealm(new Realm(1));
        List<Role> roleList = new LinkedList<>();
        roleList.add(new Role("ROLE_APPLICATION_ADMIN"));
        user.setRoleList(roleList);
        user.setUserId(1);
        user.setLastLoginDate(new Date());
        LinkedList<UserAcl> aclList = new LinkedList<>();
        aclList.add(new UserAcl(1, "ROLE_SUPER", 1, 1, 1, -1, ""));
        aclList.add(new UserAcl(1, "ROLE_ADMIN", 2, -1, -1, -1, ""));
        user.setUserAclList(aclList);
        Gson gson = new Gson();
        Type type = new TypeToken<User>() {}.getType();
        String json = gson.toJson(user, type);
        System.out.println(json);


    }
}
