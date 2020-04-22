/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProgramData;

/**
 *
 * @author altius
 */
public interface ProgramDataService {

    public ProgramData getProgramData(int programId, int versionId, CustomUserDetails curUser);

}
