/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author akil
 */
public class ProgramIntegrationDTO implements Serializable {

    private int programVersionTransId;
    private int programId;
    private String programCode;
    private int versionId;
    private int versionTypeId;
    private int versionStatusId;
    private int integrationProgramId;
    private int integrationId;
    private String integrationName;
    private String fileName;
    private String folderName;
    private int integrationViewId;
    private String integrationViewName;
    private static final SimpleDateFormat YMDHMS = new SimpleDateFormat("yyMMddHHmmss");

    public int getProgramVersionTransId() {
        return programVersionTransId;
    }

    public void setProgramVersionTransId(int programVersionTransId) {
        this.programVersionTransId = programVersionTransId;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public int getVersionTypeId() {
        return versionTypeId;
    }

    public void setVersionTypeId(int versionTypeId) {
        this.versionTypeId = versionTypeId;
    }

    public int getVersionStatusId() {
        return versionStatusId;
    }

    public void setVersionStatusId(int versionStatusId) {
        this.versionStatusId = versionStatusId;
    }

    public int getIntegrationProgramId() {
        return integrationProgramId;
    }

    public void setIntegrationProgramId(int integrationProgramId) {
        this.integrationProgramId = integrationProgramId;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
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

    public String getFinalFileName(Date curDate) {
        String fn = this.fileName;
        fn = replaceString(fn, "<%PROGRAM_CODE%>", this.programCode);
        fn = replaceString(fn, "<%PROGRAM_ID%>", String.format("%08d", this.programId));
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

    @Override
    public String toString() {
        return "ProgramIntegrationDTO{" + "programVersionTransId=" + programVersionTransId + ", programId=" + programId + ", programCode=" + programCode + ", versionId=" + versionId + ", integrationName=" + integrationName + ", fileName=" + fileName + '}';
    }

}
