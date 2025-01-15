package cc.altius.FASP.service;

import cc.altius.FASP.dao.CountryDao;
import cc.altius.FASP.model.Country;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Label;
import cc.altius.FASP.service.impl.CountryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CountryServiceImplTest {

    @InjectMocks
    private CountryServiceImpl countryService;

    @Mock
    private CountryDao countryDao;

    private CustomUserDetails testUser;
    private Country testCountry;

    @BeforeEach
    public void setUp() {
        testUser = new CustomUserDetails();
        testUser.setUserId(1);
        testCountry = new Country();
        testCountry.setCountryId(1);
        Label label = new Label();
        label.setLabel_en("Canada");
        testCountry.setLabel(label);
    }

    @Test
    public void testGetCountryList_ShouldReturnCountries() {
        when(countryDao.getCountryList(true, testUser)).thenReturn(Arrays.asList(testCountry));
        List<Country> result = countryService.getCountryList(true, testUser);
        assertFalse(result.isEmpty());
        assertEquals(testCountry.getCountryId(), result.get(0).getCountryId());
    }

    @Test
    public void testAddCountry_ShouldReturnSuccess() {
        when(countryDao.addCountry(any(), any())).thenReturn(1);
        int result = countryService.addCountry(testCountry, testUser);
        assertEquals(1, result);
        verify(countryDao).addCountry(testCountry, testUser);
    }

    @Test
    public void testGetCountryById_ShouldReturnCountry() {
        when(countryDao.getCountryById(1, testUser)).thenReturn(testCountry);
        Country result = countryService.getCountryById(1, testUser);
        assertNotNull(result);
        assertEquals(testCountry.getCountryId(), result.getCountryId());
    }

    @Test
    public void testGetCountryListForSyncProgram_EmptyProgramIdsString() {
        List<Country> result = countryService.getCountryListForSyncProgram("", testUser);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetCountryListForSyncProgram_NonEmptyProgramIdsString() {
        String programIdsString = "programId1,programId2";
        List<Country> expectedCountries = Arrays.asList(new Country(), new Country());
        when(countryDao.getCountryListForSyncProgram(programIdsString, testUser))
                .thenReturn(expectedCountries);
        List<Country> result = countryService.getCountryListForSyncProgram(programIdsString, testUser);
        assertNotNull(result);
        assertEquals(expectedCountries, result);
    }
} 