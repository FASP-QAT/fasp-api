/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.Country;
import cc.altius.FASP.model.CustomUserDetails;
import java.util.List;

/**
 *
 * @author altius
 */
public interface CountryService {

    public List<Country> getCountryList(boolean active, CustomUserDetails curUser);
    
    public Country getCountryById(int countryId, CustomUserDetails curUser);

    public int addCountry(Country country, CustomUserDetails curUser);

    public int updateCountry(Country country, CustomUserDetails curUser);

    public List<Country> getCountryListForSync(String dtLastSyncDate);

}
