/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.CountryDao;
import cc.altius.FASP.model.Country;
import cc.altius.FASP.service.CountryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    CountryDao countryDao;

    @Override
    public List<Country> getCountryList(boolean active) {
        return this.countryDao.getCountryList(active);
    }

    @Override
    public int addCountry(Country country) {
        return this.countryDao.addCountry(country);
    }

    @Override
    public int updateCountry(Country country) {
        return this.countryDao.updateCountry(country);
    }

}
