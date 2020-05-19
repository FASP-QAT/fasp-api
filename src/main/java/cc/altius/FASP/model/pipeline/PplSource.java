/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class PplSource implements Serializable {

    private String supplierid;
    private String suppliername;
    private Double supplierleadtimeplan;
    private Double supplierleadtimeorder;
    private Double supplierleadtimeship;
    private String suppliernote;
    private Double freight;
    private Boolean defaultsupplier;
    private int pipelineId;

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }

    public String getSuppliername() {
        return suppliername;
    }

    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername;
    }

    public Double getSupplierleadtimeplan() {
        return supplierleadtimeplan;
    }

    public void setSupplierleadtimeplan(Double supplierleadtimeplan) {
        this.supplierleadtimeplan = supplierleadtimeplan;
    }

    public Double getSupplierleadtimeorder() {
        return supplierleadtimeorder;
    }

    public void setSupplierleadtimeorder(Double supplierleadtimeorder) {
        this.supplierleadtimeorder = supplierleadtimeorder;
    }

    public Double getSupplierleadtimeship() {
        return supplierleadtimeship;
    }

    public void setSupplierleadtimeship(Double supplierleadtimeship) {
        this.supplierleadtimeship = supplierleadtimeship;
    }

    public String getSuppliernote() {
        return suppliernote;
    }

    public void setSuppliernote(String suppliernote) {
        this.suppliernote = suppliernote;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Boolean getDefaultsupplier() {
        return defaultsupplier;
    }

    public void setDefaultsupplier(Boolean defaultsupplier) {
        this.defaultsupplier = defaultsupplier;
    }

    public int getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(int pipelineId) {
        this.pipelineId = pipelineId;
    }

}
