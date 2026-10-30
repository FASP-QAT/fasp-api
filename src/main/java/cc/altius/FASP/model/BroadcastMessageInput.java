/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author altius
 */
public class BroadcastMessageInput implements Serializable {

    private int categoryId;
    private String subject;
    private String bodyHtml;
    private List<String> targetRoles;
    private List<String> targetBusinessFunctions;
    private List<Integer> targetLanguages;

    public BroadcastMessageInput() {
    }

    public List<String> getTargetRoles() {
        return targetRoles;
    }

    public void setTargetRoles(List<String> targetRoles) {
        this.targetRoles = targetRoles;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    public List<String> getTargetBusinessFunctions() {
        return targetBusinessFunctions;
    }

    public void setTargetBusinessFunctions(List<String> targetBusinessFunctions) {
        this.targetBusinessFunctions = targetBusinessFunctions;
    }

    public List<Integer> getTargetLanguages() {
        return targetLanguages;
    }

    public void setTargetLanguages(List<Integer> targetLanguages) {
        this.targetLanguages = targetLanguages;
    }

}
