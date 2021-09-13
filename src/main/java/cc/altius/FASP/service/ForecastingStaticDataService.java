/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.SimpleBaseModel;
import java.util.List;

/**
 *
 * @author akil
 */
public interface ForecastingStaticDataService {

    public List<SimpleBaseModel> getUsageTypeList(boolean active, CustomUserDetails curUser);

    public List<SimpleBaseModel> getNodeTypeList(boolean active, CustomUserDetails curUser);

    public List<SimpleBaseModel> getForecastMethodTypeList(boolean active, CustomUserDetails curUser);
    
}
