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
public class SimpleBudgetObject implements Serializable {

    private int id;
    private Label label;
    private SimpleObject fundingSource;

    public SimpleBudgetObject() {
    }

    public SimpleBudgetObject(int id, Label label, SimpleObject fundingSource) {
        this.id = id;
        this.label = label;
        this.fundingSource = fundingSource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public SimpleObject getFundingSource() {
        return fundingSource;
    }

    public void setFundingSource(SimpleObject fundingSource) {
        this.fundingSource = fundingSource;
    }

}
