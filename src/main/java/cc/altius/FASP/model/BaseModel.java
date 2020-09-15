/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class BaseModel implements Serializable {

    @JsonIgnore
    private transient BasicUser createdBy;
    @JsonIgnore
    private transient Date createdDate;
    @JsonIgnore
    private transient BasicUser lastModifiedBy;
    @JsonIgnore
    private transient Date lastModifiedDate;
    @JsonIgnore
    private transient boolean active;

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

    public BasicUser getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(BasicUser lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setBaseModel(BaseModel b) {
        this.active = b.isActive();
        this.createdBy = b.getCreatedBy();
        this.createdDate = b.getCreatedDate();
        this.lastModifiedBy = b.getLastModifiedBy();
        this.lastModifiedDate = b.getLastModifiedDate();
    }
}
