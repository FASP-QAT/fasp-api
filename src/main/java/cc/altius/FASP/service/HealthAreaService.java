/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.HealthArea;
import java.util.List;

/**
 *
 * @author akil
 */
public interface HealthAreaService {

    public int addHealthArea(HealthArea h, int curUser);
    
    public int updateHealthArea(HealthArea h, int CurUser);
    
    public List<HealthArea> getHealthAreaList();
    
    public HealthArea getHealthAreaById(int healthAreaId);
}
