/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class StockStatusMatrixOutput implements Serializable {

    @JsonView(Views.ReportView.class)
    private List<StockStatusMatrix> stockStatusMatrix;
    @JsonView(Views.ReportView.class)
    private List<StockStatusDetails> stockStatusDetails;

    public List<StockStatusMatrix> getStockStatusMatrix() {
        return stockStatusMatrix;
    }

    public void setStockStatusMatrix(List<StockStatusMatrix> stockStatusMatrix) {
        this.stockStatusMatrix = stockStatusMatrix;
    }

    public List<StockStatusDetails> getStockStatusDetails() {
        return stockStatusDetails;
    }

    public void setStockStatusDetails(List<StockStatusDetails> stockStatusDetails) {
        this.stockStatusDetails = stockStatusDetails;
    }

}
