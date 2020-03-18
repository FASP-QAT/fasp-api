/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ProgramDTO;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.ProgramProduct;
import java.util.List;

/**
 *
 * @author altius
 */
public interface ProgramDao {

    public List<ProgramDTO> getProgramListForDropdown(CustomUserDetails curUser);
    
    public int addProgram(Program p, CustomUserDetails curUser);

    public int updateProgram(Program p, CustomUserDetails curUser);

    public List<Program> getProgramList(CustomUserDetails curUser);
    
    public List<Program> getProgramList(int realmId, CustomUserDetails curUser);

    public Program getProgramById(int programId, CustomUserDetails curUser);

    public ProgramProduct getProgramProductListForProgramId(int programId, CustomUserDetails curUser);
    
    public int saveProgramProduct(ProgramProduct pp, CustomUserDetails curUser);
}
