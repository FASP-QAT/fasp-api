/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.Registration;
import java.util.List;

/**
 *
 * @author altius
 */
public interface RegistrationDao {

    public int saveRegistration(Registration registration);

    public List<Registration> getUserApprovalList();

    public int updateRegistration(Registration registration);

}
