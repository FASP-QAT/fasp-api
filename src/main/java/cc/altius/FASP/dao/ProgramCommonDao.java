/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.Version;
import java.util.List;

/**
 *
 * @author akil
 */
public interface ProgramCommonDao {

    public Program getProgramById(int programId, int programTypeId, CustomUserDetails curUser);

    public Program getBasicProgramById(int programId, int programTypeId, CustomUserDetails curUser);
    
    public List<Version> getVersionListForProgramId(int programTypeId, int programId, CustomUserDetails curUser);
    
    public SimpleCodeObject getSimpleSupplyPlanProgramById(int programId, CustomUserDetails curUser);

}
