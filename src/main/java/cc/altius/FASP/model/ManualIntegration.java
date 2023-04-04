/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author akil
 */
public class ManualIntegration implements Serializable {

    @JsonView(Views.ReportView.class)
    private int manualIntegrationId;
    @JsonView(Views.ReportView.class)
    private SimpleCodeObject program;
    @JsonView(Views.ReportView.class)
    private int versionId;
    @JsonView(Views.ReportView.class)
    private int integrationId;
    @JsonView(Views.ReportView.class)
    private String integrationName;
    @JsonView(Views.ReportView.class)
    private BasicUser createdBy;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date createdDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date completedDate;
    @JsonView(Views.IgnoreView.class)
    private int integrationViewId;
    @JsonView(Views.IgnoreView.class)
    private String integrationViewName;
    @JsonView(Views.IgnoreView.class)
    private String folderName;
    @JsonView(Views.IgnoreView.class)
    private String fileName;

    private static final SimpleDateFormat YMDHMS = new SimpleDateFormat("yyMMddHHmmss");

    public ManualIntegration() {
    }

    public ManualIntegration(int manualIntegrationId, SimpleCodeObject program, int versionId, int integrationId, String integrationName, BasicUser createdBy, Date createdDate, Date completedDate) {
        this.manualIntegrationId = manualIntegrationId;
        this.program = program;
        this.versionId = versionId;
        this.integrationId = integrationId;
        this.integrationName = integrationName;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.completedDate = completedDate;
    }

    public int getManualIntegrationId() {
        return manualIntegrationId;
    }

    public void setManualIntegrationId(int manualIntegrationId) {
        this.manualIntegrationId = manualIntegrationId;
    }

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public int getIntegrationId() {
        return integrationId;
    }

    public void setIntegrationId(int integrationId) {
        this.integrationId = integrationId;
    }

    public String getIntegrationName() {
        return integrationName;
    }

    public void setIntegrationName(String integrationName) {
        this.integrationName = integrationName;
    }

    public BasicUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BasicUser createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public int getIntegrationViewId() {
        return integrationViewId;
    }

    public void setIntegrationViewId(int integrationViewId) {
        this.integrationViewId = integrationViewId;
    }

    public String getIntegrationViewName() {
        return integrationViewName;
    }

    public void setIntegrationViewName(String integrationViewName) {
        this.integrationViewName = integrationViewName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String finalFileName) {
        this.fileName = finalFileName;
    }

    public String getFinalFileName(Date curDate) {
        String fn = this.fileName;
        fn = replaceString(fn, "<%PROGRAM_CODE%>", this.program.getCode());
        fn = replaceString(fn, "<%PROGRAM_ID%>", String.format("%08d", this.program.getId()));
        fn = replaceString(fn, "<%VERSION_ID%>", String.format("%06d", this.versionId));
        fn = replaceString(fn, "<%YMDHMS%>", YMDHMS.format(curDate));
        return fn;
    }

    private String replaceString(String originalString, String searchString, String replaceWith) {
        int i = originalString.indexOf(searchString);
        if (i > -1) {
            originalString = originalString.substring(0, i) + replaceWith + originalString.substring(i + searchString.length(), originalString.length());
        }
        return originalString;
    }

}
