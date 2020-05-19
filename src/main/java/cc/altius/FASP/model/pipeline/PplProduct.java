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
public class PplProduct implements Serializable {

    private String productid;
    private String productname;
    private Integer productminmonths;
    private Integer productmaxmonths;
    private String supplierid;
    private String methodid;
    private Boolean productactiveflag;
    private Date productactivedate;
    private Integer defaultcasesize;
    private String productnote;
    private Integer prodcmax;
    private Integer prodcmin;
    private Integer proddesstock;
    private String txtinnovatordrugname;
    private Double dbllowestunitqty;
    private String txtlowestunitmeasure;
    private String txtsubstitutionlist;
    private Boolean fpermittedincountry;
    private String memavailabilitynotes;
    private Boolean favailabilitystatus;
    private Boolean fuserdefined;
    private String strimportsource;
    private Integer buconversion;
    private String txtpreferencenotes;
    private Integer lngamcstart;
    private Integer lngamcmonths;
    private Boolean famcchanged;
    private Integer txtmigrationstatus;
    private Date txtmigrationstatusdate;
    private String strtype;
    private String oldproductid;
    private String oldproductname;
    private Integer lngbatch;
    private String oldmethodid;
    private int pipelineId;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public Integer getProductminmonths() {
        return productminmonths;
    }

    public void setProductminmonths(Integer productminmonths) {
        this.productminmonths = productminmonths;
    }

    public Integer getProductmaxmonths() {
        return productmaxmonths;
    }

    public void setProductmaxmonths(Integer productmaxmonths) {
        this.productmaxmonths = productmaxmonths;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }

    public String getMethodid() {
        return methodid;
    }

    public void setMethodid(String methodid) {
        this.methodid = methodid;
    }

    public Boolean getProductactiveflag() {
        return productactiveflag;
    }

    public void setProductactiveflag(Boolean productactiveflag) {
        this.productactiveflag = productactiveflag;
    }

    public Date getProductactivedate() {
        return productactivedate;
    }

    public void setProductactivedate(Date productactivedate) {
        this.productactivedate = productactivedate;
    }

    public Integer getDefaultcasesize() {
        return defaultcasesize;
    }

    public void setDefaultcasesize(Integer defaultcasesize) {
        this.defaultcasesize = defaultcasesize;
    }

    public String getProductnote() {
        return productnote;
    }

    public void setProductnote(String productnote) {
        this.productnote = productnote;
    }

    public Integer getProdcmax() {
        return prodcmax;
    }

    public void setProdcmax(Integer prodcmax) {
        this.prodcmax = prodcmax;
    }

    public Integer getProdcmin() {
        return prodcmin;
    }

    public void setProdcmin(Integer prodcmin) {
        this.prodcmin = prodcmin;
    }

    public Integer getProddesstock() {
        return proddesstock;
    }

    public void setProddesstock(Integer proddesstock) {
        this.proddesstock = proddesstock;
    }

    public String getTxtinnovatordrugname() {
        return txtinnovatordrugname;
    }

    public void setTxtinnovatordrugname(String txtinnovatordrugname) {
        this.txtinnovatordrugname = txtinnovatordrugname;
    }

    public Double getDbllowestunitqty() {
        return dbllowestunitqty;
    }

    public void setDbllowestunitqty(Double dbllowestunitqty) {
        this.dbllowestunitqty = dbllowestunitqty;
    }

    public String getTxtlowestunitmeasure() {
        return txtlowestunitmeasure;
    }

    public void setTxtlowestunitmeasure(String txtlowestunitmeasure) {
        this.txtlowestunitmeasure = txtlowestunitmeasure;
    }

    public String getTxtsubstitutionlist() {
        return txtsubstitutionlist;
    }

    public void setTxtsubstitutionlist(String txtsubstitutionlist) {
        this.txtsubstitutionlist = txtsubstitutionlist;
    }

    public Boolean getFpermittedincountry() {
        return fpermittedincountry;
    }

    public void setFpermittedincountry(Boolean fpermittedincountry) {
        this.fpermittedincountry = fpermittedincountry;
    }

    public String getMemavailabilitynotes() {
        return memavailabilitynotes;
    }

    public void setMemavailabilitynotes(String memavailabilitynotes) {
        this.memavailabilitynotes = memavailabilitynotes;
    }

    public Boolean getFavailabilitystatus() {
        return favailabilitystatus;
    }

    public void setFavailabilitystatus(Boolean favailabilitystatus) {
        this.favailabilitystatus = favailabilitystatus;
    }

    public Boolean getFuserdefined() {
        return fuserdefined;
    }

    public void setFuserdefined(Boolean fuserdefined) {
        this.fuserdefined = fuserdefined;
    }

    public String getStrimportsource() {
        return strimportsource;
    }

    public void setStrimportsource(String strimportsource) {
        this.strimportsource = strimportsource;
    }

    public Integer getBuconversion() {
        return buconversion;
    }

    public void setBuconversion(Integer buconversion) {
        this.buconversion = buconversion;
    }

    public String getTxtpreferencenotes() {
        return txtpreferencenotes;
    }

    public void setTxtpreferencenotes(String txtpreferencenotes) {
        this.txtpreferencenotes = txtpreferencenotes;
    }

    public Integer getLngamcstart() {
        return lngamcstart;
    }

    public void setLngamcstart(Integer lngamcstart) {
        this.lngamcstart = lngamcstart;
    }

    public Integer getLngamcmonths() {
        return lngamcmonths;
    }

    public void setLngamcmonths(Integer lngamcmonths) {
        this.lngamcmonths = lngamcmonths;
    }

    public Boolean getFamcchanged() {
        return famcchanged;
    }

    public void setFamcchanged(Boolean famcchanged) {
        this.famcchanged = famcchanged;
    }

    public Integer getTxtmigrationstatus() {
        return txtmigrationstatus;
    }

    public void setTxtmigrationstatus(Integer txtmigrationstatus) {
        this.txtmigrationstatus = txtmigrationstatus;
    }

    public Date getTxtmigrationstatusdate() {
        return txtmigrationstatusdate;
    }

    public void setTxtmigrationstatusdate(Date txtmigrationstatusdate) {
        this.txtmigrationstatusdate = txtmigrationstatusdate;
    }

    public String getStrtype() {
        return strtype;
    }

    public void setStrtype(String strtype) {
        this.strtype = strtype;
    }

    public String getOldproductid() {
        return oldproductid;
    }

    public void setOldproductid(String oldproductid) {
        this.oldproductid = oldproductid;
    }

    public String getOldproductname() {
        return oldproductname;
    }

    public void setOldproductname(String oldproductname) {
        this.oldproductname = oldproductname;
    }

    public Integer getLngbatch() {
        return lngbatch;
    }

    public void setLngbatch(Integer lngbatch) {
        this.lngbatch = lngbatch;
    }

    public String getOldmethodid() {
        return oldmethodid;
    }

    public void setOldmethodid(String oldmethodid) {
        this.oldmethodid = oldmethodid;
    }

    public int getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(int pipelineId) {
        this.pipelineId = pipelineId;
    }

}
