/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class SimpleBudgetObject extends SimpleObject implements Serializable {

    private SimpleObject fundingSource;

    public SimpleBudgetObject() {
    }

    public SimpleBudgetObject(Integer id, Label label, SimpleObject fundingSource) {
        super(id, label);
        this.fundingSource = fundingSource;
    }

    public SimpleObject getFundingSource() {
        return fundingSource;
    }

    public void setFundingSource(SimpleObject fundingSource) {
        this.fundingSource = fundingSource;
    }

}
