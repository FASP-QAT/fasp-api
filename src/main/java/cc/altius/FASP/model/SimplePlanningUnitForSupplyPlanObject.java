/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class SimplePlanningUnitForSupplyPlanObject extends SimpleObject {

    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class})
    private boolean active;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class})
    private double conversionFactor;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class})
    private List<SimpleProcurementAgentSkuObject> procurementAgentSkuList;

    public SimplePlanningUnitForSupplyPlanObject() {
        super();
        procurementAgentSkuList = new LinkedList<>();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public List<SimpleProcurementAgentSkuObject> getProcurementAgentSkuList() {
        return procurementAgentSkuList;
    }

    public void setProcurementAgentSkuList(List<SimpleProcurementAgentSkuObject> procurementAgentSkuList) {
        this.procurementAgentSkuList = procurementAgentSkuList;
    }

}
