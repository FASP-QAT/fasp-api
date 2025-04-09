/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author altius
 */
public class ErpOrderDTO {

    private int eoErpOrderId;
    private String eoRoNo;
    private String eoRoPrimeLineNo;
    private String eoOrderNo;
    private int eoPrimeLineNo;
    private String eoOrderType;
    private Date eoCreatedDate;
    private String eoParentRo;
    private Date eoParentCreatedDate;
    private String eoPlanningUnitSkuCode;
    private int eoPlanningUnitId; // Cannot be 0
    private int eoShelfLife;
    private String eoProcurementUnitSkuCode;
    private Integer eoProcurementUnitId;
    private Integer eoSupplierId;
    private long eoQty;
    private Date eoOrderedDate;
    private Date eoCurrentEstimatedDeliveryDate;
    private Date eoReqDeliveryDate;
    private Date eoAgreedDeliveryDate;
    private String eoSupplierName;
    private double eoPrice;
    private double eoShippingCost;
    private String eoShipBy;
    private String eoRecipentName;
    private String eoRecipentCountry;
    private String eoStatus;
    private int eoChangeCode;
    private Integer eoShipmentStatusId;
//    private Date eoActualShipmentDate;
//    private Date eoActualDeliveryDate;
//    private Date eoArrivalAtDestinationDate;
    private float conversionFactor;
    List<ErpShipmentDTO> eoShipmentList;
    private int erpPlanningUnitId;

    private boolean manualTagging;

    private int shProgramId; // Cannot be 0
    private int shShipmentId; // Cannot be 0
    private int shVersionId; // Cannot be 0
    private Integer shShipmentTransId; // Cannot be null
    private Boolean shActive; // Cannot be null
    private Boolean shErpFlag;
    private Integer shParentShipmentId;
    private Integer shFundingSourceId;
    private Integer shProcurementAgentId;
    private Integer shBudgetId;
    private Boolean shAccountFlag;
    private Integer shDataSourceId;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ErpOrderDTO() {
        this.eoShipmentList = new LinkedList<>();
    }

    public int getEoErpOrderId() {
        return eoErpOrderId;
    }

    public void setEoErpOrderId(int eoErpOrderId) {
        this.eoErpOrderId = eoErpOrderId;
    }

    public String getEoRoNo() {
        return eoRoNo;
    }

    public void setEoRoNo(String eoRoNo) {
        this.eoRoNo = eoRoNo;
    }

    public String getEoRoPrimeLineNo() {
        return eoRoPrimeLineNo;
    }

    public void setEoRoPrimeLineNo(String eoRoPrimeLineNo) {
        this.eoRoPrimeLineNo = eoRoPrimeLineNo;
    }

    public String getEoOrderNo() {
        return eoOrderNo;
    }

    public void setEoOrderNo(String eoOrderNo) {
        this.eoOrderNo = eoOrderNo;
    }

    public int getEoPrimeLineNo() {
        return eoPrimeLineNo;
    }

    public void setEoPrimeLineNo(int eoPrimeLineNo) {
        this.eoPrimeLineNo = eoPrimeLineNo;
    }

    public String getEoOrderType() {
        return eoOrderType;
    }

    public void setEoOrderType(String eoOrderType) {
        this.eoOrderType = eoOrderType;
    }

    public Date getEoCreatedDate() {
        return eoCreatedDate;
    }

    public void setEoCreatedDate(Date eoCreatedDate) {
        this.eoCreatedDate = eoCreatedDate;
    }

    public String getEoParentRo() {
        return eoParentRo;
    }

    public void setEoParentRo(String eoParentRo) {
        this.eoParentRo = eoParentRo;
    }

    public Date getEoParentCreatedDate() {
        return eoParentCreatedDate;
    }

    public void setEoParentCreatedDate(Date eoParentCreatedDate) {
        this.eoParentCreatedDate = eoParentCreatedDate;
    }

    public String getEoPlanningUnitSkuCode() {
        return eoPlanningUnitSkuCode;
    }

    public void setEoPlanningUnitSkuCode(String eoPlanningUnitSkuCode) {
        this.eoPlanningUnitSkuCode = eoPlanningUnitSkuCode;
    }

    public int getEoPlanningUnitId() {
        return eoPlanningUnitId;
    }

    public void setEoPlanningUnitId(int eoPlanningUnitId) {
        this.eoPlanningUnitId = eoPlanningUnitId;
    }

    public int getEoShelfLife() {
        return eoShelfLife;
    }

    public void setEoShelfLife(int eoShelfLife) {
        this.eoShelfLife = eoShelfLife;
    }

    public String getEoProcurementUnitSkuCode() {
        return eoProcurementUnitSkuCode;
    }

    public void setEoProcurementUnitSkuCode(String eoProcurementUnitSkuCode) {
        this.eoProcurementUnitSkuCode = eoProcurementUnitSkuCode;
    }

    public Integer getEoProcurementUnitId() {
        return eoProcurementUnitId;
    }

    public void setEoProcurementUnitId(Integer eoProcurementUnitId) {
        this.eoProcurementUnitId = eoProcurementUnitId;
    }

    public Integer getEoSupplierId() {
        return eoSupplierId;
    }

    public void setEoSupplierId(Integer eoSupplierId) {
        this.eoSupplierId = eoSupplierId;
    }

    public long getEoQty() {
        return eoQty;
    }

    public void setEoQty(long eoQty) {
        this.eoQty = eoQty;
    }

    public Date getEoOrderedDate() {
        return eoOrderedDate;
    }

    public void setEoOrderedDate(Date eoOrderedDate) {
        this.eoOrderedDate = eoOrderedDate;
    }

    public Date getEoCurrentEstimatedDeliveryDate() {
        return eoCurrentEstimatedDeliveryDate;
    }

    public void setEoCurrentEstimatedDeliveryDate(Date eoCurrentEstimatedDeliveryDate) {
        this.eoCurrentEstimatedDeliveryDate = eoCurrentEstimatedDeliveryDate;
    }

    public Date getEoReqDeliveryDate() {
        return eoReqDeliveryDate;
    }

    public void setEoReqDeliveryDate(Date eoReqDeliveryDate) {
        this.eoReqDeliveryDate = eoReqDeliveryDate;
    }

    public Date getEoAgreedDeliveryDate() {
        return eoAgreedDeliveryDate;
    }

    public void setEoAgreedDeliveryDate(Date eoAgreedDeliveryDate) {
        this.eoAgreedDeliveryDate = eoAgreedDeliveryDate;
    }

    public String getEoSupplierName() {
        return eoSupplierName;
    }

    public void setEoSupplierName(String eoSupplierName) {
        this.eoSupplierName = eoSupplierName;
    }

    public double getEoPrice() {
        return eoPrice;
    }

    public void setEoPrice(double eoPrice) {
        this.eoPrice = eoPrice;
    }

    public double getEoShippingCost() {
        return eoShippingCost;
    }

    public void setEoShippingCost(double eoShippingCost) {
        this.eoShippingCost = eoShippingCost;
    }

    public String getEoShipBy() {
        return eoShipBy;
    }

    public void setEoShipBy(String eoShipBy) {
        this.eoShipBy = eoShipBy;
    }

    public String getEoRecipentName() {
        return eoRecipentName;
    }

    public void setEoRecipentName(String eoRecipentName) {
        this.eoRecipentName = eoRecipentName;
    }

    public String getEoRecipentCountry() {
        return eoRecipentCountry;
    }

    public void setEoRecipentCountry(String eoRecipentCountry) {
        this.eoRecipentCountry = eoRecipentCountry;
    }

    public String getEoStatus() {
        return eoStatus;
    }

    public void setEoStatus(String eoStatus) {
        this.eoStatus = eoStatus;
    }

    public int getEoChangeCode() {
        return eoChangeCode;
    }

    public void setEoChangeCode(int eoChangeCode) {
        this.eoChangeCode = eoChangeCode;
    }

    public Integer getEoShipmentStatusId() {
        return eoShipmentStatusId;
    }

    public void setEoShipmentStatusId(Integer eoShipmentStatusId) {
        this.eoShipmentStatusId = eoShipmentStatusId;
    }

    public int getShProgramId() {
        return shProgramId;
    }

    public void setShProgramId(int shProgramId) {
        this.shProgramId = shProgramId;
    }

    public int getShShipmentId() {
        return shShipmentId;
    }

    public void setShShipmentId(int shShipmentId) {
        this.shShipmentId = shShipmentId;
    }

    public boolean isManualTagging() {
        return manualTagging;
    }

    public void setManualTagging(boolean manualTagging) {
        this.manualTagging = manualTagging;
    }

    public Integer getShShipmentTransId() {
        return shShipmentTransId;
    }

    public void setShShipmentTransId(Integer shShipmentTransId) {
        this.shShipmentTransId = shShipmentTransId;
    }

    public int getShVersionId() {
        return shVersionId;
    }

    public void setShVersionId(int shVersionId) {
        this.shVersionId = shVersionId;
    }

    public Boolean getShActive() {
        return shActive;
    }

    public void setShActive(Boolean shActive) {
        this.shActive = shActive;
    }

    public Boolean getShErpFlag() {
        return shErpFlag;
    }

    public void setShErpFlag(Boolean shErpFlag) {
        this.shErpFlag = shErpFlag;
    }

    public boolean isShErpFlag() {
//        System.out.println("inside erp flag function-------------------"+this.eoOrderNo);
        if (this.shErpFlag == null) {
//            System.out.println("inside erp flag function if-------------------"+this.shErpFlag);
            return false;
        } else {
//            System.out.println("inside erp flag function else-------------------"+this.shErpFlag);
            return this.shErpFlag;
        }
    }

    public Integer getShParentShipmentId() {
        return shParentShipmentId;
    }

    public void setShParentShipmentId(Integer shParentShipmentId) {
        this.shParentShipmentId = shParentShipmentId;
    }

    public Integer getShFundingSourceId() {
        return shFundingSourceId;
    }

    public void setShFundingSourceId(Integer shFundingSourceId) {
        this.shFundingSourceId = shFundingSourceId;
    }

    public Integer getShProcurementAgentId() {
        return shProcurementAgentId;
    }

    public void setShProcurementAgentId(Integer shProcurementAgentId) {
        this.shProcurementAgentId = shProcurementAgentId;
    }

    public Integer getShBudgetId() {
        return shBudgetId;
    }

    public void setShBudgetId(Integer shBudgetId) {
        this.shBudgetId = shBudgetId;
    }

//    public Date getEoActualShipmentDate() {
//        return eoActualShipmentDate;
//    }
//
//    public void setEoActualShipmentDate(Date eoActualShipmentDate) {
//        this.eoActualShipmentDate = eoActualShipmentDate;
//    }
//
//    public Date getEoActualDeliveryDate() {
//        return eoActualDeliveryDate;
//    }
//
//    public void setEoActualDeliveryDate(Date eoActualDeliveryDate) {
//        this.eoActualDeliveryDate = eoActualDeliveryDate;
//    }
//
//    public Date getEoArrivalAtDestinationDate() {
//        return eoArrivalAtDestinationDate;
//    }
//
//    public void setEoArrivalAtDestinationDate(Date eoArrivalAtDestinationDate) {
//        this.eoArrivalAtDestinationDate = eoArrivalAtDestinationDate;
//    }
    public List<ErpShipmentDTO> getEoShipmentList() {
        return eoShipmentList;
    }

    public void setEoShipmentList(List<ErpShipmentDTO> eoShipmentList) {
        this.eoShipmentList = eoShipmentList;
    }

    public Boolean getShAccountFlag() {
        return shAccountFlag;
    }

    public void setShAccountFlag(Boolean shAccountFlag) {
        this.shAccountFlag = shAccountFlag;
    }

    public Integer getShDataSourceId() {
        return shDataSourceId;
    }

    public void setShDataSourceId(Integer shDataSourceId) {
        this.shDataSourceId = shDataSourceId;
    }

    public float getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(float conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public Date getCalculatedExpiryDate() {
        Date dt = null;

        if (getMinActualDeliveryDate() != null) {
            dt = getMinActualShipmentDate();
        } else {
            dt = getExpectedDeliveryDate();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.set(Calendar.DATE, 1);
        c.add(Calendar.MONTH, this.eoShelfLife);
        logger.info("ERP Linking : Expiry date---" + c.getTime());
        return c.getTime();
    }

    public Date getExpectedDeliveryDate() {
        if (getMinActualDeliveryDate() != null) {
            return getMinActualDeliveryDate();
        } else if (this.eoCurrentEstimatedDeliveryDate != null) {
            return this.eoCurrentEstimatedDeliveryDate;
        } else if (this.eoAgreedDeliveryDate != null) {
            return this.eoAgreedDeliveryDate;
        } else if (this.eoReqDeliveryDate != null) {
            return this.eoReqDeliveryDate;
        } else {
            return null;
        }
    }

    public Date getMinActualShipmentDate() {
        Date dt = null;
        logger.info("ERP Linking : Going to calculate minimum actual shipment date");
        if (this.eoShipmentList.size() > 0) {
//            ErpShipmentDTO erpShipmentDTO = Collections.min(this.eoShipmentList, Comparator.comparing(s -> s.getEoActualShipmentDate()));
            ErpShipmentDTO erpShipmentDTO = this.eoShipmentList.stream()
                    .filter(m -> m.getEoActualShipmentDate() != null)
                    .min(Comparator.comparing(ErpShipmentDTO::getEoActualShipmentDate)).orElse(null);
            if (erpShipmentDTO != null && erpShipmentDTO.getEoActualShipmentDate() != null) {
                logger.info("Minimum actual shipment date is : " + erpShipmentDTO.getEoActualShipmentDate());
                dt = erpShipmentDTO.getEoActualShipmentDate();
            }
        }
        return dt;
    }

    public Date getMinActualDeliveryDate() {
        Date dt = null;
        logger.info("ERP Linking : Going to calculate minimum actual delivery date date");
        if (this.eoShipmentList.size() > 0) {
//            ErpShipmentDTO erpShipmentDTO = Collections.min(this.eoShipmentList, Comparator.comparing(s -> s.getEoActualDeliveryDate()));
            ErpShipmentDTO erpShipmentDTO = this.eoShipmentList.stream()
                    .filter(m -> m.getEoActualDeliveryDate() != null)
                    .min(Comparator.comparing(ErpShipmentDTO::getEoActualDeliveryDate)).orElse(null);
            if (erpShipmentDTO != null && erpShipmentDTO.getEoActualDeliveryDate() != null) {
                logger.info("Minimum actual delivery date is : " + erpShipmentDTO.getEoActualDeliveryDate());
                dt = erpShipmentDTO.getEoActualDeliveryDate();
            }
        }
        return dt;
    }

    public Date getMinArrivalAtDestinationDate() {
        Date dt = null;
        logger.info("ERP Linking : Going to calculate minimum arrival at destination date");
        if (this.eoShipmentList.size() > 0) {
//            ErpShipmentDTO erpShipmentDTO = Collections.min(this.eoShipmentList, Comparator.comparing(s -> s.getEoArrivalAtDestinationDate()));
            ErpShipmentDTO erpShipmentDTO = this.eoShipmentList.stream()
                    .filter(m -> m.getEoArrivalAtDestinationDate() != null)
                    .min(Comparator.comparing(ErpShipmentDTO::getEoArrivalAtDestinationDate)).orElse(null);
            if (erpShipmentDTO != null && erpShipmentDTO.getEoArrivalAtDestinationDate() != null) {
                logger.info("Minimum arrival at destination date is : " + erpShipmentDTO.getEoArrivalAtDestinationDate());
                dt = erpShipmentDTO.getEoArrivalAtDestinationDate();
            }
        }
        return dt;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.eoErpOrderId;
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
        final ErpOrderDTO other = (ErpOrderDTO) obj;
        if (this.eoErpOrderId != other.eoErpOrderId) {
            return false;
        }
        return true;
    }

    public int getErpPlanningUnitId() {
        return erpPlanningUnitId;
    }

    public void setErpPlanningUnitId(int erpPlanningUnitId) {
        this.erpPlanningUnitId = erpPlanningUnitId;
    }

    public boolean isShipmentCancelled() {
//        System.out.println("shipment status id---" + this.eoShipmentStatusId);
        if (this.eoShipmentStatusId == 8) {
//            System.out.println("inside if-------------------");
            return true;
        }
//        System.out.println("inside else-------------------");
        return false;
    }

    public boolean isSkuChanged() {
//        System.out.println("pl1--------------------" + this.eoPlanningUnitId);
//        System.out.println("pl2--------------------" + this.erpPlanningUnitId);
        if (this.eoPlanningUnitId != this.erpPlanningUnitId) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ErpOrderDTO{" + "eoErpOrderId=" + eoErpOrderId + ", eoRoNo=" + eoRoNo + ", eoRoPrimeLineNo=" + eoRoPrimeLineNo + ", eoOrderNo=" + eoOrderNo + ", eoPrimeLineNo=" + eoPrimeLineNo + ", eoOrderType=" + eoOrderType + ", eoCreatedDate=" + eoCreatedDate + ", eoParentRo=" + eoParentRo + ", eoParentCreatedDate=" + eoParentCreatedDate + ", eoPlanningUnitSkuCode=" + eoPlanningUnitSkuCode + ", eoPlanningUnitId=" + eoPlanningUnitId + ", eoShelfLife=" + eoShelfLife + ", eoProcurementUnitSkuCode=" + eoProcurementUnitSkuCode + ", eoProcurementUnitId=" + eoProcurementUnitId + ", eoSupplierId=" + eoSupplierId + ", eoQty=" + eoQty + ", eoOrderedDate=" + eoOrderedDate + ", eoCurrentEstimatedDeliveryDate=" + eoCurrentEstimatedDeliveryDate + ", eoReqDeliveryDate=" + eoReqDeliveryDate + ", eoAgreedDeliveryDate=" + eoAgreedDeliveryDate + ", eoSupplierName=" + eoSupplierName + ", eoPrice=" + eoPrice + ", eoShippingCost=" + eoShippingCost + ", eoShipBy=" + eoShipBy + ", eoRecipentName=" + eoRecipentName + ", eoRecipentCountry=" + eoRecipentCountry + ", eoStatus=" + eoStatus + ", eoChangeCode=" + eoChangeCode + ", eoShipmentStatusId=" + eoShipmentStatusId + ", conversionFactor=" + conversionFactor + ", eoShipmentList=" + eoShipmentList + ", manualTagging=" + manualTagging + ", shProgramId=" + shProgramId + ", shShipmentId=" + shShipmentId + ", shVersionId=" + shVersionId + ", shShipmentTransId=" + shShipmentTransId + ", shActive=" + shActive + ", shErpFlag=" + shErpFlag + ", shParentShipmentId=" + shParentShipmentId + ", shFundingSourceId=" + shFundingSourceId + ", shProcurementAgentId=" + shProcurementAgentId + ", shBudgetId=" + shBudgetId + ", shAccountFlag=" + shAccountFlag + ", shDataSourceId=" + shDataSourceId + '}';
    }

}
