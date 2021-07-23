/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author altius
 */
public class ARTMISHistoryDTO extends ManualTaggingDTO {

    private List<ErpShipmentDTO> shipmentList;
    private List<FilePathDTO> files;
    private double totalCost;

    public ARTMISHistoryDTO() {
        this.shipmentList = new LinkedList<>();
        this.files = new LinkedList<>();
    }

    public List<ErpShipmentDTO> getShipmentList() {
        return shipmentList;
    }

    public void setShipmentList(List<ErpShipmentDTO> shipmentList) {
        this.shipmentList = shipmentList;
    }

    public List<FilePathDTO> getFiles() {
        return files;
    }

    public void setFiles(List<FilePathDTO> files) {
        this.files = files;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Date getMinActualDeliveryDate() {
        Date dt = null;
        if (this.shipmentList.size() > 0) {
//            ErpShipmentDTO erpShipmentDTO = Collections.min(this.eoShipmentList, Comparator.comparing(s -> s.getEoActualDeliveryDate()));
            ErpShipmentDTO erpShipmentDTO = this.shipmentList.stream()
                    .filter(m -> m.getEoActualDeliveryDate() != null && m.getFileName().equals(this.getMaxFilename()))
                    //                    .filter(m -> m.getEoActualDeliveryDate() != null)
                    .min(Comparator.comparing(ErpShipmentDTO::getEoActualDeliveryDate)).orElse(null);
            if (erpShipmentDTO != null && erpShipmentDTO.getEoActualDeliveryDate() != null) {
                dt = erpShipmentDTO.getEoActualDeliveryDate();
                System.out.println("min delivery date history---" + dt);
            }
        }
        return dt;
    }

    public Date getCalculatedExpectedDeliveryDate() {
        if (getMinActualDeliveryDate() != null) {
            return getMinActualDeliveryDate();
        } else if (this.expectedDeliveryDate != null) {
            return this.expectedDeliveryDate;
        } else {
            return null;
        }
    }

    public String getMaxFilename() {
        String fileName = "";
        if (this.files.size() > 0) {
//            FilePathDTO file = Collections.max(this.files, Comparator.comparing(s -> s.getFilename()));
            FilePathDTO file = this.files.stream()
                    .filter(m -> m.getFilename() != null)
                    .max(Comparator.comparing(FilePathDTO::getFilename)).orElse(null);
            if (file != null && file.getFilename() != null) {
                fileName = file.getFilename();
                System.out.println("ERP shipment list---" + this.shipmentList);
                System.out.println("max file name---" + fileName);
//                System.out.println("getCalculatedExpectedDeliveryDate---"+this.getCalculatedExpectedDeliveryDate());
            }
        }
        return fileName;
    }

    public String getProcurementAgentOrderNo() {
        return (this.getRoNo() + " - " + this.getRoPrimeLineNo() + " | " + this.getOrderNo() + " - " + this.getPrimeLineNo());
    }

}
