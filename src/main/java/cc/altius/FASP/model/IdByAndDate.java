/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class IdByAndDate implements Serializable {

    private int id;
    private Integer tempId;
    private Integer tempParentId;
    private Integer tempParentLinkedId;
    private int createdBy;
    private Date createdDate;
    private int lastModifiedBy;
    private Date lastModifiedDate;

    public IdByAndDate(int id, Integer tempId, Integer tempParentId, Integer tempParentLinkedId, int createdBy, Date createdDate, int lastModifiedBy, Date lastModifiedDate) {
        this.id = id;
        this.tempId = (tempId == 0 ? null : tempId);
        this.tempParentId = (tempParentId == 0 ? null : tempParentId);
        this.tempParentLinkedId = (tempParentLinkedId == 0 ? null : tempParentLinkedId);
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getTempId() {
        return tempId;
    }

    public void setTempId(Integer tempId) {
        this.tempId = (tempId == 0 ? null : tempId);
    }

    public Integer getTempParentId() {
        return tempParentId;
    }

    public void setTempParentId(Integer tempParentId) {
        this.tempParentId = (tempParentId == 0 ? null : tempParentId);
    }

    public Integer getTempParentLinkedId() {
        return tempParentLinkedId;
    }

    public void setTempParentLinkedId(Integer tempParentLinkedId) {
        this.tempParentLinkedId = (tempParentLinkedId == 0 ? null : tempParentLinkedId);
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(int lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "IdByAndDate{" + "id=" + id + ", tempId=" + tempId + ", tempParentId=" + tempParentId + ", tempParentLinkedId=" + tempParentLinkedId + '}';
    }

}
