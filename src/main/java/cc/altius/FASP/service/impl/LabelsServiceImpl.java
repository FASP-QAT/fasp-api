/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.LabelsDao;
import cc.altius.FASP.model.Label;
import cc.altius.FASP.service.LabelsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author palash
 */
@Service
public class LabelsServiceImpl implements LabelsService {

    @Autowired
    private LabelsDao labelsDao;

    @Override
    public List<Label> getLabelsListAll() {
        return this.labelsDao.getLabelsListAll();
    }

    @Override
    public int updateLabels(Label label,int userId) {
        return this.labelsDao.updateLabels(label,userId);
    }

}
