package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 * POJO representing a draft planning unit record
 */
public class PlanningUnitDraft extends BaseModel implements Serializable {

    @JsonView(Views.InternalView.class)
    private int planningUnitDraftId;
    
    @JsonView(Views.InternalView.class)
    private Integer planningUnitId;
    
    @JsonView(Views.InternalView.class)
    private Integer action;
    
    @JsonView(Views.InternalView.class)
    private String taskOrder;
    
    @JsonView(Views.InternalView.class)
    private String commodityCouncil;
    
    @JsonView(Views.InternalView.class)
    private String subcategory;
    
    @JsonView(Views.InternalView.class)
    private String tracerCategory;
    
    @JsonView(Views.InternalView.class)
    private String productActive;
    
    @JsonView(Views.InternalView.class)
    private String productIdNoPack;
    
    @JsonView(Views.InternalView.class)
    private String productNameNoPack;
    
    @JsonView(Views.InternalView.class)
    private String productId;
    
    @JsonView(Views.InternalView.class)
    private String productName;
    
    @JsonView(Views.InternalView.class)
    private String orderUom;
    
    @JsonView(Views.InternalView.class)
    private String packSize;
    
    @JsonView(Views.InternalView.class)
    private String noOfBaseUnits;
    
    @JsonView(Views.InternalView.class)
    private String baseUnit;
    
    @JsonView(Views.InternalView.class)
    private String l5DataTrusteeCode;
    
    @JsonView(Views.InternalView.class)
    private String unspsc;
    
    @JsonView(Views.InternalView.class)
    private String inn;
    
    @JsonView(Views.InternalView.class)
    private String controlled;
    
    @JsonView(Views.InternalView.class)
    private String route;
    
    @JsonView(Views.InternalView.class)
    private String form;
    
    @JsonView(Views.InternalView.class)
    private String qaCategory;
    
    @JsonView(Views.InternalView.class)
    private String qaCriteria;
    
    @JsonView(Views.InternalView.class)
    private String drug1Name;
    
    @JsonView(Views.InternalView.class)
    private String drug1Abbr;
    
    @JsonView(Views.InternalView.class)
    private String drug1Qty;
    
    @JsonView(Views.InternalView.class)
    private String drug1Meas;
    
    @JsonView(Views.InternalView.class)
    private String drug1Unit;
    
    @JsonView(Views.InternalView.class)
    private String drug2Name;
    
    @JsonView(Views.InternalView.class)
    private String drug2Abbr;
    
    @JsonView(Views.InternalView.class)
    private String drug2Qty;
    
    @JsonView(Views.InternalView.class)
    private String drug2Meas;
    
    @JsonView(Views.InternalView.class)
    private String drug2Unit;
    
    @JsonView(Views.InternalView.class)
    private String drug3Name;
    
    @JsonView(Views.InternalView.class)
    private String drug3Abbr;
    
    @JsonView(Views.InternalView.class)
    private String drug3Qty;
    
    @JsonView(Views.InternalView.class)
    private String drug3Meas;
    
    @JsonView(Views.InternalView.class)
    private String drug3Unit;
    
    @JsonView(Views.InternalView.class)
    private String drug4Name;
    
    @JsonView(Views.InternalView.class)
    private String drug4Abbr;
    
    @JsonView(Views.InternalView.class)
    private String drug4Qty;
    
    @JsonView(Views.InternalView.class)
    private String drug4Meas;
    
    @JsonView(Views.InternalView.class)
    private String drug4Unit;
    
    @JsonView(Views.InternalView.class)
    private String usaidArvTier;
    
    @JsonView(Views.InternalView.class)
    private String planningUnitMoq;
    
    @JsonView(Views.InternalView.class)
    private String planningUnitsPerPallet;
    
    @JsonView(Views.InternalView.class)
    private String planningUnitsPerContainer;
    
    @JsonView(Views.InternalView.class)
    private String planningUnitVolumeM3;
    
    @JsonView(Views.InternalView.class)
    private String planningUnitWeightKg;
    
    @JsonView(Views.InternalView.class)
    private String itemId;
    
    @JsonView(Views.InternalView.class)
    private String itemName;
    
    @JsonView(Views.InternalView.class)
    private String supplier;
    
    @JsonView(Views.InternalView.class)
    private String weightUom;
    
    @JsonView(Views.InternalView.class)
    private String weight;
    
    @JsonView(Views.InternalView.class)
    private String heightUom;
    
    @JsonView(Views.InternalView.class)
    private String height;
    
    @JsonView(Views.InternalView.class)
    private String length;
    
    @JsonView(Views.InternalView.class)
    private String width;
    
    @JsonView(Views.InternalView.class)
    private String gtin;
    
    @JsonView(Views.InternalView.class)
    private String labeling;
    
    @JsonView(Views.InternalView.class)
    private String itemAvailable;
    
    @JsonView(Views.InternalView.class)
    private String unitsPerCase;
    
    @JsonView(Views.InternalView.class)
    private String unitsPerPallet;
    
    @JsonView(Views.InternalView.class)
    private String unitsPerContainer;
    
    @JsonView(Views.InternalView.class)
    private String estPrice;
    
    @JsonView(Views.InternalView.class)
    private String euro1;
    
    @JsonView(Views.InternalView.class)
    private String euro2;

    public int getPlanningUnitDraftId() {
        return planningUnitDraftId;
    }

    public void setPlanningUnitDraftId(int planningUnitDraftId) {
        this.planningUnitDraftId = planningUnitDraftId;
    }

    public Integer getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(Integer planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public String getTaskOrder() {
        return taskOrder;
    }

    public void setTaskOrder(String taskOrder) {
        this.taskOrder = taskOrder;
    }

    public String getCommodityCouncil() {
        return commodityCouncil;
    }

    public void setCommodityCouncil(String commodityCouncil) {
        this.commodityCouncil = commodityCouncil;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getTracerCategory() {
        return tracerCategory;
    }

    public void setTracerCategory(String tracerCategory) {
        this.tracerCategory = tracerCategory;
    }

    public String getProductActive() {
        return productActive;
    }

    public void setProductActive(String productActive) {
        this.productActive = productActive;
    }

    public String getProductIdNoPack() {
        return productIdNoPack;
    }

    public void setProductIdNoPack(String productIdNoPack) {
        this.productIdNoPack = productIdNoPack;
    }

    public String getProductNameNoPack() {
        return productNameNoPack;
    }

    public void setProductNameNoPack(String productNameNoPack) {
        this.productNameNoPack = productNameNoPack;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderUom() {
        return orderUom;
    }

    public void setOrderUom(String orderUom) {
        this.orderUom = orderUom;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public String getNoOfBaseUnits() {
        return noOfBaseUnits;
    }

    public void setNoOfBaseUnits(String noOfBaseUnits) {
        this.noOfBaseUnits = noOfBaseUnits;
    }

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public String getL5DataTrusteeCode() {
        return l5DataTrusteeCode;
    }

    public void setL5DataTrusteeCode(String l5DataTrusteeCode) {
        this.l5DataTrusteeCode = l5DataTrusteeCode;
    }

    public String getUnspsc() {
        return unspsc;
    }

    public void setUnspsc(String unspsc) {
        this.unspsc = unspsc;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getControlled() {
        return controlled;
    }

    public void setControlled(String controlled) {
        this.controlled = controlled;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getQaCategory() {
        return qaCategory;
    }

    public void setQaCategory(String qaCategory) {
        this.qaCategory = qaCategory;
    }

    public String getQaCriteria() {
        return qaCriteria;
    }

    public void setQaCriteria(String qaCriteria) {
        this.qaCriteria = qaCriteria;
    }

    public String getDrug1Name() {
        return drug1Name;
    }

    public void setDrug1Name(String drug1Name) {
        this.drug1Name = drug1Name;
    }

    public String getDrug1Abbr() {
        return drug1Abbr;
    }

    public void setDrug1Abbr(String drug1Abbr) {
        this.drug1Abbr = drug1Abbr;
    }

    public String getDrug1Qty() {
        return drug1Qty;
    }

    public void setDrug1Qty(String drug1Qty) {
        this.drug1Qty = drug1Qty;
    }

    public String getDrug1Meas() {
        return drug1Meas;
    }

    public void setDrug1Meas(String drug1Meas) {
        this.drug1Meas = drug1Meas;
    }

    public String getDrug1Unit() {
        return drug1Unit;
    }

    public void setDrug1Unit(String drug1Unit) {
        this.drug1Unit = drug1Unit;
    }

    public String getDrug2Name() {
        return drug2Name;
    }

    public void setDrug2Name(String drug2Name) {
        this.drug2Name = drug2Name;
    }

    public String getDrug2Abbr() {
        return drug2Abbr;
    }

    public void setDrug2Abbr(String drug2Abbr) {
        this.drug2Abbr = drug2Abbr;
    }

    public String getDrug2Qty() {
        return drug2Qty;
    }

    public void setDrug2Qty(String drug2Qty) {
        this.drug2Qty = drug2Qty;
    }

    public String getDrug2Meas() {
        return drug2Meas;
    }

    public void setDrug2Meas(String drug2Meas) {
        this.drug2Meas = drug2Meas;
    }

    public String getDrug2Unit() {
        return drug2Unit;
    }

    public void setDrug2Unit(String drug2Unit) {
        this.drug2Unit = drug2Unit;
    }

    public String getDrug3Name() {
        return drug3Name;
    }

    public void setDrug3Name(String drug3Name) {
        this.drug3Name = drug3Name;
    }

    public String getDrug3Abbr() {
        return drug3Abbr;
    }

    public void setDrug3Abbr(String drug3Abbr) {
        this.drug3Abbr = drug3Abbr;
    }

    public String getDrug3Qty() {
        return drug3Qty;
    }

    public void setDrug3Qty(String drug3Qty) {
        this.drug3Qty = drug3Qty;
    }

    public String getDrug3Meas() {
        return drug3Meas;
    }

    public void setDrug3Meas(String drug3Meas) {
        this.drug3Meas = drug3Meas;
    }

    public String getDrug3Unit() {
        return drug3Unit;
    }

    public void setDrug3Unit(String drug3Unit) {
        this.drug3Unit = drug3Unit;
    }

    public String getDrug4Name() {
        return drug4Name;
    }

    public void setDrug4Name(String drug4Name) {
        this.drug4Name = drug4Name;
    }

    public String getDrug4Abbr() {
        return drug4Abbr;
    }

    public void setDrug4Abbr(String drug4Abbr) {
        this.drug4Abbr = drug4Abbr;
    }

    public String getDrug4Qty() {
        return drug4Qty;
    }

    public void setDrug4Qty(String drug4Qty) {
        this.drug4Qty = drug4Qty;
    }

    public String getDrug4Meas() {
        return drug4Meas;
    }

    public void setDrug4Meas(String drug4Meas) {
        this.drug4Meas = drug4Meas;
    }

    public String getDrug4Unit() {
        return drug4Unit;
    }

    public void setDrug4Unit(String drug4Unit) {
        this.drug4Unit = drug4Unit;
    }

    public String getUsaidArvTier() {
        return usaidArvTier;
    }

    public void setUsaidArvTier(String usaidArvTier) {
        this.usaidArvTier = usaidArvTier;
    }

    public String getPlanningUnitMoq() {
        return planningUnitMoq;
    }

    public void setPlanningUnitMoq(String planningUnitMoq) {
        this.planningUnitMoq = planningUnitMoq;
    }

    public String getPlanningUnitsPerPallet() {
        return planningUnitsPerPallet;
    }

    public void setPlanningUnitsPerPallet(String planningUnitsPerPallet) {
        this.planningUnitsPerPallet = planningUnitsPerPallet;
    }

    public String getPlanningUnitsPerContainer() {
        return planningUnitsPerContainer;
    }

    public void setPlanningUnitsPerContainer(String planningUnitsPerContainer) {
        this.planningUnitsPerContainer = planningUnitsPerContainer;
    }

    public String getPlanningUnitVolumeM3() {
        return planningUnitVolumeM3;
    }

    public void setPlanningUnitVolumeM3(String planningUnitVolumeM3) {
        this.planningUnitVolumeM3 = planningUnitVolumeM3;
    }

    public String getPlanningUnitWeightKg() {
        return planningUnitWeightKg;
    }

    public void setPlanningUnitWeightKg(String planningUnitWeightKg) {
        this.planningUnitWeightKg = planningUnitWeightKg;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getWeightUom() {
        return weightUom;
    }

    public void setWeightUom(String weightUom) {
        this.weightUom = weightUom;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeightUom() {
        return heightUom;
    }

    public void setHeightUom(String heightUom) {
        this.heightUom = heightUom;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public String getLabeling() {
        return labeling;
    }

    public void setLabeling(String labeling) {
        this.labeling = labeling;
    }

    public String getItemAvailable() {
        return itemAvailable;
    }

    public void setItemAvailable(String itemAvailable) {
        this.itemAvailable = itemAvailable;
    }

    public String getUnitsPerCase() {
        return unitsPerCase;
    }

    public void setUnitsPerCase(String unitsPerCase) {
        this.unitsPerCase = unitsPerCase;
    }

    public String getUnitsPerPallet() {
        return unitsPerPallet;
    }

    public void setUnitsPerPallet(String unitsPerPallet) {
        this.unitsPerPallet = unitsPerPallet;
    }

    public String getUnitsPerContainer() {
        return unitsPerContainer;
    }

    public void setUnitsPerContainer(String unitsPerContainer) {
        this.unitsPerContainer = unitsPerContainer;
    }

    public String getEstPrice() {
        return estPrice;
    }

    public void setEstPrice(String estPrice) {
        this.estPrice = estPrice;
    }

    public String getEuro1() {
        return euro1;
    }

    public void setEuro1(String euro1) {
        this.euro1 = euro1;
    }

    public String getEuro2() {
        return euro2;
    }

    public void setEuro2(String euro2) {
        this.euro2 = euro2;
    }
} 