/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.City;
import java.util.List;

/**
 *
 * @author altius
 */
public interface CityService {

    public List<City> getCityList(int countryId, int stateId);

}
