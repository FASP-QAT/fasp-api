/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class BatchQty implements Serializable {

    @JsonView({Views.InternalView.class})
    private SimpleCodeObject batch;
    @JsonView({Views.InternalView.class})
    private Integer qty;

    public SimpleCodeObject getBatch() {
        return batch;
    }

    public void setBatch(SimpleCodeObject batch) {
        this.batch = batch;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

}
