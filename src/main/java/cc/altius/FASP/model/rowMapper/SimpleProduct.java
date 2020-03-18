/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Label;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class SimpleProduct implements Serializable {

    private int productId;
    private Label label;
    private int minMonth;
    private int maxMonth;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public int getMinMonth() {
        return minMonth;
    }

    public void setMinMonth(int minMonth) {
        this.minMonth = minMonth;
    }

    public int getMaxMonth() {
        return maxMonth;
    }

    public void setMaxMonth(int maxMonth) {
        this.maxMonth = maxMonth;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.productId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SimpleProduct other = (SimpleProduct) obj;
        if (this.productId != other.productId) {
            return false;
        }
        return true;
    }

}
