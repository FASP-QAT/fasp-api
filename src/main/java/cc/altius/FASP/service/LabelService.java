/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.StaticLabelDTO;
import cc.altius.FASP.model.Label;
import java.util.List;

/**
 *
 * @author palash
 */
public interface LabelService {

    public List<Label> getDatabaseLabelsList();
    
    public boolean saveDatabaseLabels(List<String> label,CustomUserDetails curUser);
    
    public List<StaticLabelDTO> getStaticLabelsList();
    
    public boolean saveStaticLabels(List<String> label,CustomUserDetails curUser);
}
