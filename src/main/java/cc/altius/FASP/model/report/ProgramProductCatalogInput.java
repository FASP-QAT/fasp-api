/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ProgramProductCatalogInput implements Serializable {

    private int programId;
    private int productCategoryId;
    private int tracerCategoryId;

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public int getTracerCategoryId() {
        return tracerCategoryId;
    }

    public void setTracerCategoryId(int tracerCategoryId) {
        this.tracerCategoryId = tracerCategoryId;
    }

}
