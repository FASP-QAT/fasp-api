/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

/**
 *
 * @author Akil Mahimwala
 */
public class ResponseFormat {

    private String messageCode; // Code that you want to show, will be converted to actual text by i18
    private Object data; // Actual payload if you are returning some data

    public ResponseFormat() {
    }

    public ResponseFormat(String messageCode) {
        this.messageCode = messageCode;
    }

    public ResponseFormat(String messageCode, Object data) {
        this.messageCode = messageCode;
        this.data = data;
    }

    public ResponseFormat(String messageCode, String temp, Object data) {
        this.messageCode = messageCode;
        this.data = data;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessage(String message) {
        this.messageCode = message;
    }
    
    public void setStatus(String status) {
        
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
