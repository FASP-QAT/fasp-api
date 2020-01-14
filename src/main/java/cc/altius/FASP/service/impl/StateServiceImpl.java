/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.StateDao;
import cc.altius.FASP.model.State;
import cc.altius.FASP.service.StateService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class StateServiceImpl implements StateService {

    @Autowired
    StateDao stateDao;

    @Override
    public List<State> getStateList(int countryId) {
        return this.stateDao.getStateList(countryId);
    }

}
