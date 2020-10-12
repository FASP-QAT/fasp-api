/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author altius
 */
public class QuantimedImportDTO implements Serializable {
    
    private String fileType;
    private String dtmDataExported;
    private String dtmStart;
    private String dtmEnd;
    private String dblDataInterval;
    private String sourceName;
    private String programId;
    
    private List<QuantimedImportProductDTO> products;
    private List<QuantimedImportRecordDTO> records;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getDtmDataExported() {
        return dtmDataExported;
    }

    public void setDtmDataExported(String dtmDataExported) {
        this.dtmDataExported = dtmDataExported;
    }

    public String getDtmStart() {
        return dtmStart;
    }

    public void setDtmStart(String dtmStart) {
        this.dtmStart = dtmStart;
    }

    public String getDtmEnd() {
        return dtmEnd;
    }

    public void setDtmEnd(String dtmEnd) {
        this.dtmEnd = dtmEnd;
    }

    public String getDblDataInterval() {
        return dblDataInterval;
    }

    public void setDblDataInterval(String dblDataInterval) {
        this.dblDataInterval = dblDataInterval;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public List<QuantimedImportProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<QuantimedImportProductDTO> products) {
        this.products = products;
    }

    public List<QuantimedImportRecordDTO> getRecords() {
        return records;
    }

    public void setRecords(List<QuantimedImportRecordDTO> records) {
        this.records = records;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    @Override
    public String toString() {
        return "QuantimedImportDTO{" + "fileType=" + fileType + ", dtmDataExported=" + dtmDataExported + ", dtmStart=" + dtmStart + ", dtmEnd=" + dtmEnd + ", dblDataInterval=" + dblDataInterval + ", sourceName=" + sourceName + ", programId=" + programId + ", products=" + products + ", records=" + records + '}';
    }
       
}
