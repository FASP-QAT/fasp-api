/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.Label;

/**
 *
 * @author altius
 */
public interface LabelDao {

    public int addLabel(Label label, int curUser);
}
