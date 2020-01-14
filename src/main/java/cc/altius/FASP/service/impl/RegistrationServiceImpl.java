/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.RegistrationDao;
import cc.altius.FASP.model.Registration;
import cc.altius.FASP.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    RegistrationDao registrationDao;

    @Override
    public int saveRegistration(Registration registration) {
        try{
        return this.registrationDao.saveRegistration(registration);
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

}
