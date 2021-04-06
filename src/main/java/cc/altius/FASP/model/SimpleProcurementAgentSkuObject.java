/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;

/**
 *
 * @author akil
 */
public class SimpleProcurementAgentSkuObject extends SimpleCodeObject {

    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class})
    private String skuCode;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class})
    private boolean active;

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
