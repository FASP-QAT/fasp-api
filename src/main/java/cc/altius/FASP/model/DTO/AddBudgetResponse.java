/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO;

import cc.altius.FASP.model.Label;
import java.util.List;

/**
 *
 * @author rohit
 */
public class AddBudgetResponse {
    private Integer budgetId;          // present when success
    private List<Label> duplicateLabels; // present when duplicate

    public AddBudgetResponse(Integer budgetId, List<Label> duplicateLabels) {
        this.budgetId = budgetId;
        this.duplicateLabels = duplicateLabels;
    }

    public Integer getBudgetId() {
        return budgetId;
    }

    public List<Label> getDuplicateLabels() {
        return duplicateLabels;
    }

    public boolean hasDuplicates() {
        return duplicateLabels != null && !duplicateLabels.isEmpty();
    }
}
