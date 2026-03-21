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
public class StockStatusMatrixGlobalOutput implements Serializable {

    @JsonView(Views.ReportView.class)
    private List<StockStatusMatrixGlobal> dataList;
    @JsonView(Views.ReportView.class)
    private SimpleObject reportView;

    public List<StockStatusMatrixGlobal> getDataList() {
        return dataList;
    }

    public void setDataList(List<StockStatusMatrixGlobal> dataList) {
        this.dataList = dataList;
    }

    public SimpleObject getReportView() {
        return reportView;
    }

    public void setReportView(SimpleObject reportView) {
        this.reportView = reportView;
    }

}
