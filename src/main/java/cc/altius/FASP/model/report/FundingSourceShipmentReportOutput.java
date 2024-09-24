/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class FundingSourceShipmentReportOutput extends ShipmentReportOutput implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleCodeObject fundingSource;
    @JsonView(Views.ReportView.class)
    private SimpleCodeObject fundingSourceType;

    public FundingSourceShipmentReportOutput(SimpleObject planningUnit, double qty, double productCost, double freightPerc, double freightCost) {
        super(planningUnit, qty, productCost, freightPerc, freightCost);
    }

    public SimpleCodeObject getFundingSource() {
        return fundingSource;
    }

    public void setFundingSource(SimpleCodeObject fundingSource) {
        this.fundingSource = fundingSource;
    }

    public SimpleCodeObject getFundingSourceType() {
        return fundingSourceType;
    }

    public void setFundingSourceType(SimpleCodeObject fundingSourceType) {
        this.fundingSourceType = fundingSourceType;
    }

}
