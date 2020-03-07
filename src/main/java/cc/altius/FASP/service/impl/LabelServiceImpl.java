/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.model.Label;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.altius.FASP.service.LabelService;

/**
 *
 * @author palash
 */
@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelDao labelDao;

    @Override
    public List<Label> getLabelsListAll() {
        return this.labelDao.getLabelsListAll();
    }

    @Override
    public int updateLabels(Label label,int userId) {
        return this.labelDao.updateLabels(label,userId);
    }

}
