/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

/**
 *
 * @author Nikhil Pande
 */
public class ResponseFormat {

    private String status; // Success or Failed
    private String message; // Error or Complete message to show on React side
    private Object data; // Actual payload if you are returning some data

    public ResponseFormat() {
    }

    public ResponseFormat(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseFormat(String message) {
        this.message = message;
        this.status = "Success";
    }

    public ResponseFormat(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseFormat{" + "status=" + status + ", message=" + message + '}';
    }

}
