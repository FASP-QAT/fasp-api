/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.dao.impl.DashboardBottomPuData;
import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author akil
 */
public class DashboardBottomForLoadProgram {

    @JsonView(Views.ReportView.class)
    private SimpleCodeObject program;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date curDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date stopDate;
    @JsonView(Views.ReportView.class)
    Map<Integer, DashboardBottomPuData> puData;

    public DashboardBottomForLoadProgram() {
        puData = new HashMap<>();
    }

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public Date getCurDate() {
        return curDate;
    }

    public void setCurDate(Date curDate) {
        this.curDate = curDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    // Key is PU Id
    public Map<Integer, DashboardBottomPuData> getPuData() {
        return puData;
    }

    public void setPuData(Map<Integer, DashboardBottomPuData> puData) {
        this.puData = puData;
    }

//    public void setStockStatus(Map<Integer, DashboardStockStatus> stockStatusMap) {
//        for (Integer pu : stockStatusMap.keySet()) {
//            if (puData.containsKey(pu)) {
//                puData.get(pu).setStockStatus(stockStatusMap.get(pu));
//            } else {
//                puData.put(pu, new DashboardBottomPuData());
//                puData.get(pu).setStockStatus(stockStatusMap.get(pu));
//            }
//        }
//    }
//
//    public void setExpiriesList(List<DashboardExpiredPu> expiriesList) {
//        expiriesList.forEach(e -> {
//            if (puData.containsKey(e.getPlanningUnit().getId())) {
//                puData.get(e.getPlanningUnit().getId()).getExpiriesList().add(e);
//            } else {
//                puData.put(e.getPlanningUnit().getId(), new DashboardBottomPuData());
//                puData.get(e.getPlanningUnit().getId()).getExpiriesList().add(e);
//            }
//        });
//    }
//
//    public void setShipmentDetailsByFundingSource(Map<Integer, List<DashboardShipmentDetailsReportBy>> shipmentDetailsMap) {
//        for (Integer pu : shipmentDetailsMap.keySet()) {
//            if (puData.containsKey(pu)) {
//                puData.get(pu).setShipmentDetailsByFundingSource(shipmentDetailsMap.get(pu));
//            } else {
//                puData.put(pu, new DashboardBottomPuData());
//                puData.get(pu).setShipmentDetailsByFundingSource(shipmentDetailsMap.get(pu));
//            }
//        }
//    }
//
//    public void setShipmentDetailsByProcurementAgent(Map<Integer, List<DashboardShipmentDetailsReportBy>> shipmentDetailsMap) {
//        for (Integer pu : shipmentDetailsMap.keySet()) {
//            if (puData.containsKey(pu)) {
//                puData.get(pu).setShipmentDetailsByProcurementAgent(shipmentDetailsMap.get(pu));
//            } else {
//                puData.put(pu, new DashboardBottomPuData());
//                puData.get(pu).setShipmentDetailsByProcurementAgent(shipmentDetailsMap.get(pu));
//            }
//        }
//    }
//
//    public void setShipmentDetailsByShipmentStatus(Map<Integer, List<DashboardShipmentDetailsReportBy>> shipmentDetailsMap) {
//        for (Integer pu : shipmentDetailsMap.keySet()) {
//            if (puData.containsKey(pu)) {
//                puData.get(pu).setShipmentDetailsByShipmentStatus(shipmentDetailsMap.get(pu));
//            } else {
//                puData.put(pu, new DashboardBottomPuData());
//                puData.get(pu).setShipmentDetailsByShipmentStatus(shipmentDetailsMap.get(pu));
//            }
//        }
//    }

}
