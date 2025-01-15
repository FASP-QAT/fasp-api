package cc.altius.FASP.service;

import cc.altius.FASP.dao.DimensionDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Dimension;
import cc.altius.FASP.model.Label;
import cc.altius.FASP.service.impl.DimensionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DimensionServiceImplTest {

    @InjectMocks
    private DimensionServiceImpl dimensionService;

    @Mock
    private DimensionDao dimensionDao;

    private CustomUserDetails testUser;
    private Dimension testDimension;

    @BeforeEach
    public void setUp() {
        // Setup test user
        testUser = new CustomUserDetails();
        testUser.setUserId(1);
        testUser.setUsername("test.admin@example.com");

        // Setup test dimension
        testDimension = new Dimension();
        testDimension.setDimensionId(1);
        testDimension.setActive(true);
        
        Label label = new Label();
        label.setLabel_en("Length");
        label.setLabel_fr("Longueur");
        testDimension.setLabel(label);
    }

    @Test
    public void addDimension_WithValidData_ShouldSucceed() {
        when(dimensionDao.addDimension(any(Dimension.class), any(CustomUserDetails.class)))
            .thenReturn(1);
        int result = dimensionService.addDimension(testDimension, testUser);
        assertEquals(1, result);
        verify(dimensionDao).addDimension(testDimension, testUser);
    }

    @Test
    public void getDimensionList_WhenActive_ShouldReturnOnlyActiveDimensions() {
        when(dimensionDao.getDimensionList(true)).thenReturn(Arrays.asList(testDimension));
        List<Dimension> result = dimensionService.getDimensionList(true);
        assertFalse(result.isEmpty());
        assertTrue(result.get(0).isActive());
        verify(dimensionDao).getDimensionList(true);
    }

    @Test
    public void getDimensionList_WhenEmpty_ShouldReturnEmptyList() {
        when(dimensionDao.getDimensionList(any(Boolean.class))).thenReturn(Collections.emptyList());
        List<Dimension> result = dimensionService.getDimensionList(true);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getDimensionById_WithValidId_ShouldReturnDimension() {
        when(dimensionDao.getDimensionById(1)).thenReturn(testDimension);
        Dimension result = dimensionService.getDimensionById(1);
        assertNotNull(result);
        assertEquals(testDimension.getDimensionId(), result.getDimensionId());
        assertEquals(testDimension.getLabel().getLabel_en(), result.getLabel().getLabel_en());
    }

    @Test
    public void getDimensionById_WithInvalidId_ShouldReturnNull() {
        when(dimensionDao.getDimensionById(999)).thenReturn(null);
        Dimension result = dimensionService.getDimensionById(999);
        assertNull(result);
    }

    @Test
    public void updateDimension_WithValidData_ShouldSucceed() {
        when(dimensionDao.updateDimension(any(Dimension.class), any(CustomUserDetails.class)))
            .thenReturn(1);
        Dimension existingDimension = new Dimension();
        existingDimension.setDimensionId(1);
        when(dimensionDao.getDimensionById(1)).thenReturn(existingDimension);
        testDimension.getLabel().setLabel_en("Updated Length");
        int result = dimensionService.updateDimension(testDimension, testUser);
        assertEquals(1, result);
        verify(dimensionDao).updateDimension(testDimension, testUser);
    }
    @Test
    public void testUpdateDimension_DimensionNotFound() {
        Dimension inputDimension = new Dimension();
        inputDimension.setDimensionId(1);
        assertThrows(EmptyResultDataAccessException.class,
                () -> dimensionService.updateDimension(inputDimension, testUser));
        verify(dimensionDao).getDimensionById(1);
        verify(dimensionDao, never()).updateDimension(any(), any());
    }

    @Test
    public void getDimensionListForSync_ShouldReturnUpdatedDimensions() {
        String lastSyncDate = "2024-01-01";
        when(dimensionDao.getDimensionListForSync(lastSyncDate))
            .thenReturn(Arrays.asList(testDimension));
        List<Dimension> result = dimensionService.getDimensionListForSync(lastSyncDate);
        assertFalse(result.isEmpty());
        assertEquals(testDimension.getDimensionId(), result.get(0).getDimensionId());
        verify(dimensionDao).getDimensionListForSync(lastSyncDate);
    }
} 