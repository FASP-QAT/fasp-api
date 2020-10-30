/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

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
    private int eoQty;
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
    private int shShipmentStatusId;
    private Date eoActualShippedDate;
    private Date eoActualDeliveryDate;
    List<ErpShipmentDTO> eoShipmentList;

    private boolean manualTagging;

    private int shProgramId; // Cannot be 0
    private int shShipmentId; // Cannot be 0
    private int shVersionId; // Cannot be 0
    private Boolean shActive; // Cannot be null
    private Boolean shErpFlag;
    private Integer shParentShipmentId;
    private Integer shFundingSourceId;
    private Integer shProcurementAgentId;
    private Integer shBudgetId;
    private Boolean shAccountFlag;
    private Integer shDataSourceId;
    

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

    public int getEoQty() {
        return eoQty;
    }

    public void setEoQty(int eoQty) {
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

    public int getShShipmentStatusId() {
        return shShipmentStatusId;
    }

    public void setShShipmentStatusId(int shShipmentStatusId) {
        this.shShipmentStatusId = shShipmentStatusId;
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
        if (this.shErpFlag == null) {
            return false;
        } else {
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

    public Date getEoActualShippedDate() {
        return eoActualShippedDate;
    }

    public void setEoActualShippedDate(Date eoActualShippedDate) {
        this.eoActualShippedDate = eoActualShippedDate;
    }

    public Date getEoActualDeliveryDate() {
        return eoActualDeliveryDate;
    }

    public void setEoActualDeliveryDate(Date eoActualDeliveryDate) {
        this.eoActualDeliveryDate = eoActualDeliveryDate;
    }

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

    public Date getCalculatedExpiryDate() {
        Date dt = null;
        if (this.eoActualDeliveryDate !=null) {
            dt = this.eoActualShippedDate;
        } else {
            dt = this.eoCurrentEstimatedDeliveryDate;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.MONTH, this.eoShelfLife);
        return c.getTime();
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

}
