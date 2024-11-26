/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import org.springframework.http.HttpMethod;

/**
 *
 * @author akil
 */
public class SecurityRequestMatcher implements Serializable {

    private int securityId;
    private int method;
    private String urlList;
    private String bfList;

    public SecurityRequestMatcher(int securityId, int method, String urlList, String bfList) {
        this.securityId = securityId;
        this.method = method;
        this.urlList = urlList;
        this.bfList = bfList;
    }

    public int getSecurityId() {
        return securityId;
    }

    public void setSecurityId(int securityId) {
        this.securityId = securityId;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public String getUrlList() {
        return urlList;
    }

    public void setUrlList(String urlList) {
        this.urlList = urlList;
    }

    public String getBfList() {
        return bfList;
    }

    public void setBfList(String bfList) {
        this.bfList = bfList;
    }

    public HttpMethod getHttpMethod() throws Exception {
        switch (this.method) {
            case 0:
                return null;
            case 1:
                return HttpMethod.GET;
            case 2:
                return HttpMethod.POST;
            case 3:
                return HttpMethod.PUT;
            case 4:
                return HttpMethod.DELETE;
            default:
                throw new Exception("RequestMatcher not defined correctly");
        }
    }

}
