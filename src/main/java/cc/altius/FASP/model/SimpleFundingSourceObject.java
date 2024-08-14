/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;

/**
 *
 * @author akil
 */
public class SimpleFundingSourceObject extends SimpleCodeObject {

    @JsonView(Views.DropDownView.class)
    private SimpleCodeObject fundingSourceType;

    public SimpleFundingSourceObject(Integer id, Label label, String code, SimpleCodeObject fundingSourceType) {
        super(id, label, code);
        this.fundingSourceType = fundingSourceType;
    }

    public SimpleCodeObject getFundingSourceType() {
        return fundingSourceType;
    }

    public void setFundingSourceType(SimpleCodeObject fundingSourceType) {
        this.fundingSourceType = fundingSourceType;
    }

}