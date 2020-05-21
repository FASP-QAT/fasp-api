/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class PplShipment implements Serializable {

    private String shipmentid;
    private String productid;
    private String supplierid;
    private String shipdatasourceid;
    private Double shipamount;
    private Date shipplanneddate;
    private Date shipordereddate;
    private Date shipshippeddate;
    private Date shipreceiveddate;
    private String shipstatuscode;
    private String shipnote;
    private Date shipdatechanged;
    private Double shipfreightcost;
    private Double shipvalue;
    private Integer shipcaselot;
    private Boolean shipdisplaynote;
    private String shippo;
    private Double old_shipment;
    private String shipfundingsourceid;
    private int pipelineId;

    public String getShipmentid() {
        return shipmentid;
    }

    public void setShipmentid(String shipmentid) {
        this.shipmentid = shipmentid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }

    public String getShipdatasourceid() {
        return shipdatasourceid;
    }

    public void setShipdatasourceid(String shipdatasourceid) {
        this.shipdatasourceid = shipdatasourceid;
    }

    public Double getShipamount() {
        return shipamount;
    }

    public void setShipamount(Double shipamount) {
        this.shipamount = shipamount;
    }

    public Date getShipplanneddate() {
        return shipplanneddate;
    }

    public void setShipplanneddate(Date shipplanneddate) {
        this.shipplanneddate = shipplanneddate;
    }

    public Date getShipordereddate() {
        return shipordereddate;
    }

    public void setShipordereddate(Date shipordereddate) {
        this.shipordereddate = shipordereddate;
    }

    public Date getShipshippeddate() {
        return shipshippeddate;
    }

    public void setShipshippeddate(Date shipshippeddate) {
        this.shipshippeddate = shipshippeddate;
    }

    public Date getShipreceiveddate() {
        return shipreceiveddate;
    }

    public void setShipreceiveddate(Date shipreceiveddate) {
        this.shipreceiveddate = shipreceiveddate;
    }

    public String getShipstatuscode() {
        return shipstatuscode;
    }

    public void setShipstatuscode(String shipstatuscode) {
        this.shipstatuscode = shipstatuscode;
    }

    public String getShipnote() {
        return shipnote;
    }

    public void setShipnote(String shipnote) {
        this.shipnote = shipnote;
    }

    public Date getShipdatechanged() {
        return shipdatechanged;
    }

    public void setShipdatechanged(Date shipdatechanged) {
        this.shipdatechanged = shipdatechanged;
    }

    public Double getShipfreightcost() {
        return shipfreightcost;
    }

    public void setShipfreightcost(Double shipfreightcost) {
        this.shipfreightcost = shipfreightcost;
    }

    public Double getShipvalue() {
        return shipvalue;
    }

    public void setShipvalue(Double shipvalue) {
        this.shipvalue = shipvalue;
    }

    public Integer getShipcaselot() {
        return shipcaselot;
    }

    public void setShipcaselot(Integer shipcaselot) {
        this.shipcaselot = shipcaselot;
    }

    public Boolean getShipdisplaynote() {
        return shipdisplaynote;
    }

    public void setShipdisplaynote(Boolean shipdisplaynote) {
        this.shipdisplaynote = shipdisplaynote;
    }

    public String getShippo() {
        return shippo;
    }

    public void setShippo(String shippo) {
        this.shippo = shippo;
    }

    public Double getOld_shipment() {
        return old_shipment;
    }

    public void setOld_shipment(Double old_shipment) {
        this.old_shipment = old_shipment;
    }

    public String getShipfundingsourceid() {
        return shipfundingsourceid;
    }

    public void setShipfundingsourceid(String shipfundingsourceid) {
        this.shipfundingsourceid = shipfundingsourceid;
    }

    public int getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(int pipelineId) {
        this.pipelineId = pipelineId;
    }

}
