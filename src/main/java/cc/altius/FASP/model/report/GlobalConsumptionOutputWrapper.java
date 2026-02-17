/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class GlobalConsumptionOutputWrapper implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleObject product;
    @JsonView(Views.ReportView.class)
    private List<GlobalConsumptionOutput> dataList;

    public GlobalConsumptionOutputWrapper(SimpleObject product, List<GlobalConsumptionOutput> dataList) {
        this.product = product;
        this.dataList = dataList;
    }

    public SimpleObject getProduct() {
        return product;
    }

    public void setProduct(SimpleObject product) {
        this.product = product;
    }

    public List<GlobalConsumptionOutput> getDataList() {
        return dataList;
    }

    public void setDataList(List<GlobalConsumptionOutput> dataList) {
        this.dataList = dataList;
    }

}
