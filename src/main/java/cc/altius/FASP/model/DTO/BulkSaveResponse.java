/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author rohit
 */
public class BulkSaveResponse implements Serializable {

    private int totalEdited;
    private int totalCreated;
    private List<Map<String, Object>> savedUnits = new LinkedList<>();

    public BulkSaveResponse() {
    }

    public BulkSaveResponse(int totalEdited, int totalCreated, List<Map<String, Object>> savedUnits) {
        this.totalEdited = totalEdited;
        this.totalCreated = totalCreated;
        this.savedUnits = savedUnits;
    }

    public int getTotalEdited() {
        return totalEdited;
    }

    public void setTotalEdited(int totalEdited) {
        this.totalEdited = totalEdited;
    }

    public int getTotalCreated() {
        return totalCreated;
    }

    public void setTotalCreated(int totalCreated) {
        this.totalCreated = totalCreated;
    }

    public List<Map<String, Object>> getSavedUnits() {
        return savedUnits;
    }

    public void setSavedUnits(List<Map<String, Object>> savedUnits) {
        this.savedUnits = savedUnits;
    }

}

