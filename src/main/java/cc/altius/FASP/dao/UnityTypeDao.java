/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.UnitType;
import java.util.List;

/**
 *
 * @author palash
 */
public interface UnityTypeDao {

    public int addUnitType(UnitType unitType, int userId);

    public List<UnitType> getUnitTypeList(boolean active);

    public int updateUnitType(UnitType unitType, int userId);
}
