/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Dimension;
import java.util.List;

/**
 *
 * @author altius
 */
public interface DimensionService {

    public int addDimension(Dimension d, CustomUserDetails curUser);

    public int updateDimension(Dimension d, CustomUserDetails curUser);

    public List<Dimension> getDimensionList(boolean active);

    public Dimension getDimensionById(int dimensionId);

    public List<Dimension> getDimensionListForSync(String lastSyncDate);
}
