/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateTimeDeserializer;
import cc.altius.FASP.framework.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class Version implements Serializable {

    private int versionId;
    private SimpleObject versionType;
    private SimpleObject versionStatus;
    private String notes;
    private transient BasicUser createdBy;
    private transient BasicUser lastModifiedBy;
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private transient Date createdDate;
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private transient Date lastModifiedDate;

    public Version() {
    }

    public Version(int versionId, SimpleObject versionType, SimpleObject versionStatus, String notes, BasicUser createdBy, Date createdDate, BasicUser lastModifiedBy, Date lastModifiedDate) {
        this.versionId = versionId;
        this.versionType = versionType;
        this.versionStatus = versionStatus;
        this.notes = notes;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
    }

    public Version(int versionId, SimpleObject versionType, SimpleObject versionStatus, String notes, int createdByUserId, Date createdDate, int lastModifiedByUserId, Date lastModifiedDate) {
        this.versionId = versionId;
        this.versionType = versionType;
        this.versionStatus = versionStatus;
        this.notes = notes;
        this.createdBy = new BasicUser(createdByUserId, "");
        this.createdDate = createdDate;
        this.lastModifiedBy = new BasicUser(lastModifiedByUserId, "");
        this.lastModifiedDate = lastModifiedDate;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public SimpleObject getVersionType() {
        return versionType;
    }

    public void setVersionType(SimpleObject versionType) {
        this.versionType = versionType;
    }

    public SimpleObject getVersionStatus() {
        return versionStatus;
    }

    public void setVersionStatus(SimpleObject versionStatus) {
        this.versionStatus = versionStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BasicUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BasicUser createdBy) {
        this.createdBy = createdBy;
    }

    public BasicUser getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(BasicUser lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.versionId;
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
        final Version other = (Version) obj;
        if (this.versionId != other.versionId) {
            return false;
        }
        return true;
    }

}
