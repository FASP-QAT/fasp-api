/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.Label;
import java.util.List;

/**
 *
 * @author altius
 */
public interface LabelDao {

    public int addLabel(Label label, int curUser);

    public List<Label> getLabelsListAll();

    public int updateLabels(Label label, int userId);
}
