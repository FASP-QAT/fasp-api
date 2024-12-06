package cc.altius.FASP.service;

import cc.altius.FASP.dao.UserDao;
import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.User;
import cc.altius.FASP.model.UserAcl;
import cc.altius.FASP.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;

    @Mock
    private AclService aclService;

    @Test
    public void testAddNewUserWithValidAcl() throws AccessControlFailedException {
        CustomUserDetails curUser = new CustomUserDetails();
        User user = new User();
        UserAcl userAcl = new UserAcl();
        userAcl.setRoleId("ROLE_APPLICATION_ADMIN");
        user.setUserAclList(List.of(userAcl));
        when(userDao.checkCanCreateRole("ROLE_APPLICATION_ADMIN", curUser)).thenReturn(true);
        when(aclService.expandUserAccess(any(), eq(curUser))).thenReturn(List.of(userAcl));
        when(userDao.addNewUser(user, curUser)).thenReturn(1);
        int result = userService.addNewUser(user, curUser);
        verify(userDao).addNewUser(user, curUser);
        assertEquals(1, result);
    }

    @Test
    public void testAddNewUserWithInvalidAclThrowsException() {
        CustomUserDetails curUser = new CustomUserDetails();
        User user = new User();
        UserAcl userAcl = new UserAcl();
        userAcl.setRoleId("ROLE_INVALID");
        user.setUserAclList(List.of(userAcl));
        when(userDao.checkCanCreateRole("ROLE_INVALID", curUser)).thenReturn(false);
        assertThrows(AccessControlFailedException.class, () -> userService.addNewUser(user, curUser));
    }
}
