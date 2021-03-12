/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.DatabaseTranslationsDTO;
import cc.altius.FASP.model.DTO.StaticLabelDTO;
import cc.altius.FASP.model.Label;
import java.util.List;

/**
 *
 * @author altius
 */
public interface LabelDao {

    public int addLabel(Label label, int sourceId, int curUser);

    public List<DatabaseTranslationsDTO> getDatabaseLabelsList(int realmId);
    
    public boolean saveDatabaseLabels(List<String> label,CustomUserDetails curUser);
    
    public List<StaticLabelDTO> getStaticLabelsList();
    
    public boolean saveStaticLabels(List<StaticLabelDTO> staticLabelList,CustomUserDetails curUser);
}
