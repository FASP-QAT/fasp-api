/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.model.rowMapper.SimpleProduct;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class ProgramProduct implements Serializable {

    private int programId;
    private Label label;
    private SimpleProduct[] prodcuts;
    private List<SimpleProduct> productList;

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public SimpleProduct[] getProdcuts() {
        return prodcuts;
    }

    public void setProdcuts(SimpleProduct[] prodcuts) {
        this.prodcuts = prodcuts;
    }

    public List<SimpleProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<SimpleProduct> productList) {
        this.productList = productList;
    }
    
}
