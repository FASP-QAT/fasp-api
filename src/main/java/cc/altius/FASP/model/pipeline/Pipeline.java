/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline;

import java.util.List;

/**
 *
 * @author akil
 */
public class Pipeline {

    private List<PplCommodityprice> commodityprice;
    private List<PplConsumption> consumption;
    private List<PplDatasource> datasource;
    private List<PplFundingsource> fundingsource;
    private List<PplInventory> inventory;
    private List<PplMethod> method;
    private List<PplMonthlystockarchive> monthlystockarchive;
    private List<PplPasterrors> paste_errors;
    private List<PplProduct> product;
    private List<PplProductfreightcost> productfreightcost;
    private List<PplProductsuppliercasesize> productsuppliercasesize;
    private List<PplPrograminfo> programinfo;
    private List<PplShipment> shipment;
    private List<PplSource> source;
    private List<PplTblbe_version> tblbe_version;
    private List<PplTblimportproducts> tblimportproducts;
    private List<PplTblimportrecords> tblimportrecords;

    public List<PplCommodityprice> getCommodityprice() {
        return commodityprice;
    }

    public void setCommodityprice(List<PplCommodityprice> commodityprice) {
        this.commodityprice = commodityprice;
    }

    public List<PplConsumption> getConsumption() {
        return consumption;
    }

    public void setConsumption(List<PplConsumption> consumption) {
        this.consumption = consumption;
    }

    public List<PplDatasource> getDatasource() {
        return datasource;
    }

    public void setDatasource(List<PplDatasource> datasource) {
        this.datasource = datasource;
    }

    public List<PplFundingsource> getFundingsource() {
        return fundingsource;
    }

    public void setFundingsource(List<PplFundingsource> fundingsource) {
        this.fundingsource = fundingsource;
    }

    public List<PplInventory> getInventory() {
        return inventory;
    }

    public void setInventory(List<PplInventory> inventory) {
        this.inventory = inventory;
    }

    public List<PplMethod> getMethod() {
        return method;
    }

    public void setMethod(List<PplMethod> method) {
        this.method = method;
    }

    public List<PplMonthlystockarchive> getMonthlystockarchive() {
        return monthlystockarchive;
    }

    public void setMonthlystockarchive(List<PplMonthlystockarchive> monthlystockarchive) {
        this.monthlystockarchive = monthlystockarchive;
    }

    public List<PplPasterrors> getPaste_errors() {
        return paste_errors;
    }

    public void setPaste_errors(List<PplPasterrors> paste_errors) {
        this.paste_errors = paste_errors;
    }

    public List<PplProduct> getProduct() {
        return product;
    }

    public void setProduct(List<PplProduct> product) {
        this.product = product;
    }

    public List<PplProductfreightcost> getProductfreightcost() {
        return productfreightcost;
    }

    public void setProductfreightcost(List<PplProductfreightcost> productfreightcost) {
        this.productfreightcost = productfreightcost;
    }

    public List<PplProductsuppliercasesize> getProductsuppliercasesize() {
        return productsuppliercasesize;
    }

    public void setProductsuppliercasesize(List<PplProductsuppliercasesize> productsuppliercasesize) {
        this.productsuppliercasesize = productsuppliercasesize;
    }

    public List<PplPrograminfo> getPrograminfo() {
        return programinfo;
    }

    public void setPrograminfo(List<PplPrograminfo> programinfo) {
        this.programinfo = programinfo;
    }

    public List<PplShipment> getShipment() {
        return shipment;
    }

    public void setShipment(List<PplShipment> shipment) {
        this.shipment = shipment;
    }

    public List<PplSource> getSource() {
        return source;
    }

    public void setSource(List<PplSource> source) {
        this.source = source;
    }

    public List<PplTblbe_version> getTblbe_version() {
        return tblbe_version;
    }

    public void setTblbe_version(List<PplTblbe_version> tblbe_version) {
        this.tblbe_version = tblbe_version;
    }

    public List<PplTblimportproducts> getTblimportproducts() {
        return tblimportproducts;
    }

    public void setTblimportproducts(List<PplTblimportproducts> tblimportproducts) {
        this.tblimportproducts = tblimportproducts;
    }

    public List<PplTblimportrecords> getTblimportrecords() {
        return tblimportrecords;
    }

    public void setTblimportrecords(List<PplTblimportrecords> tblimportrecords) {
        this.tblimportrecords = tblimportrecords;
    }

}
