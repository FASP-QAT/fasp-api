package cc.altius.FASP.service;

import cc.altius.FASP.dao.UnitDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Unit;
import cc.altius.FASP.service.impl.UnitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UnitServiceImplTest {

    @InjectMocks
    private UnitServiceImpl unitService;

    @Mock
    private UnitDao unitDao;

    private CustomUserDetails testUser;
    private Unit testUnit;

    @BeforeEach
    public void setUp() {
        // Setup a test user
        testUser = new CustomUserDetails();
        testUser.setUserId(1);
        testUser.setUsername("test.user@example.com");

        // Setup a test unit
        testUnit = new Unit();
        testUnit.setUnitId(1);
        testUnit.setUnitCode("KG");
        
        Label label = new Label();
        label.setLabel_en("Kilogram");
        label.setLabel_fr("Kilogramme");
        testUnit.setLabel(label);

        SimpleObject dimension = new SimpleObject();
        dimension.setId(1);
        testUnit.setDimension(dimension);
    }

    @Test
    public void getUnitList_ShouldReturnAllUnits() {
        List<Unit> expectedUnits = Arrays.asList(testUnit);
        when(unitDao.getUnitList()).thenReturn(expectedUnits);
        List<Unit> actualUnits = unitService.getUnitList();
        assertNotNull(actualUnits);
        assertEquals(1, actualUnits.size());
        assertEquals("KG", actualUnits.get(0).getUnitCode());
        verify(unitDao, times(1)).getUnitList();
    }

    @Test
    public void getUnitList_WhenEmpty_ShouldReturnEmptyList() {
        when(unitDao.getUnitList()).thenReturn(Collections.emptyList());
        List<Unit> actualUnits = unitService.getUnitList();
        assertNotNull(actualUnits);
        assertTrue(actualUnits.isEmpty());
        verify(unitDao, times(1)).getUnitList();
    }

    @Test
    public void addUnit_WithValidData_ShouldReturnSuccessfully() {
        when(unitDao.addUnit(any(Unit.class), any(CustomUserDetails.class))).thenReturn(1);
        int result = unitService.addUnit(testUnit, testUser);
        assertEquals(1, result);
        verify(unitDao).addUnit(testUnit, testUser);
    }

    @Test
    public void updateUnit_WithValidData_ShouldUpdateSuccessfully() {
        when(unitDao.updateUnit(any(Unit.class), any(CustomUserDetails.class))).thenReturn(1);
        Label newLabel = new Label();
        newLabel.setLabel_en("Updated Kilogram");
        testUnit.setLabel(newLabel);
        int result = unitService.updateUnit(testUnit, testUser);
        assertEquals(1, result);
        verify(unitDao).updateUnit(testUnit, testUser);
    }

    @Test
    public void getUnitById_WithValidId_ShouldReturnUnit() {
        when(unitDao.getUnitById(1)).thenReturn(testUnit);
        Unit result = unitService.getUnitById(1);
        assertNotNull(result);
        assertEquals(testUnit.getUnitId(), result.getUnitId());
        assertEquals(testUnit.getUnitCode(), result.getUnitCode());
        verify(unitDao).getUnitById(1);
    }

    @Test
    public void getUnitById_WithInvalidId_ShouldReturnNull() {
        when(unitDao.getUnitById(999)).thenReturn(null);
        Unit result = unitService.getUnitById(999);
        assertNull(result);
        verify(unitDao).getUnitById(999);
    }

    @Test
    public void getUnitListByDimensionId_ShouldReturnFilteredUnits() {
        int dimensionId = 1;
        List<Unit> expectedUnits = Arrays.asList(testUnit);
        when(unitDao.getUnitListByDimensionId(dimensionId)).thenReturn(expectedUnits);
        List<Unit> actualUnits = unitService.getUnitListByDimensionId(dimensionId);
        assertNotNull(actualUnits);
        assertEquals(1, actualUnits.size());
        assertEquals(testUnit.getUnitCode(), actualUnits.get(0).getUnitCode());
        verify(unitDao).getUnitListByDimensionId(dimensionId);
    }

    @Test
    public void getUnitListForSync_WithValidDate_ShouldReturnUpdatedUnits() {
        String lastSyncDate = "2024-01-01";
        List<Unit> expectedUnits = Arrays.asList(testUnit);
        when(unitDao.getUnitListForSync(lastSyncDate)).thenReturn(expectedUnits);
        List<Unit> actualUnits = unitService.getUnitListForSync(lastSyncDate);
        assertNotNull(actualUnits);
        assertFalse(actualUnits.isEmpty());
        assertEquals(testUnit.getUnitId(), actualUnits.get(0).getUnitId());
        verify(unitDao).getUnitListForSync(lastSyncDate);
    }
} 