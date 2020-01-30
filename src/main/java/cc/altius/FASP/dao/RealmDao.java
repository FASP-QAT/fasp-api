/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.Realm;
import java.util.List;

/**
 *
 * @author altius
 */
public interface RealmDao {

    public List<Realm> getRealmList(boolean active);
}
