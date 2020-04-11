/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Dimension;
import java.util.List;

/**
 *
 * @author altius
 */
public interface DimensionDao {

    public List<Dimension> getDimensionListForSync(String lastSyncDate);

    public int addDimension(Dimension d, CustomUserDetails curUser);

    public int updateDimension(Dimension d, CustomUserDetails curUser);

    public List<Dimension> getDimensionList();

    public Dimension getDimensionById(int dimensionId);

}
