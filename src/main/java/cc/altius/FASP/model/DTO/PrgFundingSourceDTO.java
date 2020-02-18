/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.User;
import java.util.Date;

/**
 *
 * @author altius
 */
public class PrgFundingSourceDTO {

    private int fundingSourceId;
    private PrgLabelDTO label;

    public int getFundingSourceId() {
        return fundingSourceId;
    }

    public void setFundingSourceId(int fundingSourceId) {
        this.fundingSourceId = fundingSourceId;
    }

    public PrgLabelDTO getLabel() {
        return label;
    }

    public void setLabel(PrgLabelDTO label) {
        this.label = label;
    }

}
