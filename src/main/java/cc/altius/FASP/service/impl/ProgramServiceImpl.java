/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ProgramDao;
import cc.altius.FASP.model.DTO.ProgramDTO;
import cc.altius.FASP.service.ProgramService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class ProgramServiceImpl implements ProgramService {

    @Autowired
    ProgramDao programDao;

    @Override
    public List<ProgramDTO> getProgramList() {
        return this.programDao.getProgramList();
    }

}
