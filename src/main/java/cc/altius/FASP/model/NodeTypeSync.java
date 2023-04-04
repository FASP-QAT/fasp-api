/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class NodeTypeSync extends NodeType {

    private List<Integer> allowedChildList;
    private BasicUser createdBy;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date createdDate;
    private BasicUser lastModifiedBy;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date lastModifiedDate;
    private boolean active;

    public NodeTypeSync() {
        this.allowedChildList = new LinkedList<>();
    }

    public NodeTypeSync(int id, Label label, boolean modelingAllowed, boolean extrapolationAllowed, boolean treeTemplateAllowed, boolean forecastTreeAllowed) {
        super(id, label, modelingAllowed, extrapolationAllowed, treeTemplateAllowed, forecastTreeAllowed);
        this.allowedChildList = new LinkedList<>();
    }

    public List<Integer> getAllowedChildList() {
        return allowedChildList;
    }

    public void setAllowedChildList(List<Integer> allowedChildList) {
        this.allowedChildList = allowedChildList;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.createdBy);
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
        final NodeTypeSync other = (NodeTypeSync) obj;
        if (!Objects.equals(super.getId(), other.getId())) {
            return false;
        }
        return true;
    }

}
