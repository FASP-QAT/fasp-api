package cc.altius.FASP.service;

import cc.altius.FASP.dao.ProgramCommonDao;
import cc.altius.FASP.dao.ProgramDao;
import cc.altius.FASP.model.*;
import cc.altius.FASP.service.impl.ProgramServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProgramServiceImplTest {

    @InjectMocks
    private ProgramServiceImpl programService;

    @Mock
    private ProgramDao programDao;

    @Mock
    private ProgramCommonDao programCommonDao;

    private CustomUserDetails testUser;
    private Program testProgram;

    @BeforeEach
    public void setUp() {
        testUser = new CustomUserDetails();
        testUser.setUserId(1);
        testUser.setRealm(new Realm(2));

        testProgram = new Program();
        testProgram.setProgramId(1);
        Label label = new Label();
        label.setLabel_en("Test Program");
        testProgram.setLabel(label);
    }

    @Test
    public void getProgramList_ShouldReturnFilteredPrograms() {
        when(programDao.getProgramList(1, testUser, true))
            .thenReturn(Arrays.asList(testProgram));
        List<Program> result = programService.getProgramList(1, testUser, true);
        assertFalse(result.isEmpty());
        assertEquals(testProgram.getProgramId(), result.get(0).getProgramId());
    }

    @Test
    public void getFullProgramById_WithValidAccess_ShouldReturnProgram() throws Exception {
        when(programCommonDao.getFullProgramById(1, 1, testUser))
            .thenReturn(testProgram);
        Program result = programService.getFullProgramById(1, 1, testUser);
        assertNotNull(result);
        assertEquals(testProgram.getProgramId(), result.getProgramId());
    }

    @Test
    public void validateProgramCode_ShouldReturnTrue_WhenValid() {
        when(programDao.validateProgramCode(2, 1, "TEST", testUser))
            .thenReturn(true);
        boolean result = programService.validateProgramCode(2, 1, "TEST", testUser);
        assertTrue(result);
    }

    @Test
    public void validateProgramCode_ShouldThrowAccessDeniedException_WhenRealmIdsDoNotMatch() {
        // testUser has realm 2, trying to access realm 1
        AccessDeniedException exception = assertThrows(
            AccessDeniedException.class,
            () -> programService.validateProgramCode(1, 1, "TEST", testUser)
        );
        assertEquals("Access denied", exception.getMessage());
    }

} 