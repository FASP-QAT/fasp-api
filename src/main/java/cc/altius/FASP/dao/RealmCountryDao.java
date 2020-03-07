/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.RealmCountry;
import java.util.List;

/**
 *
 * @author altius
 */
public interface RealmCountryDao {

    public int addRealmCountry(RealmCountry realmCountry, int curUser);

    public int updateRealmCountry(RealmCountry realmCountry, int curUser);

    public List<RealmCountry> getRealmCountryList();

    public RealmCountry getRealmCountryById(int organisationId);
    
}
