/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.jwt.resource;

import cc.altius.FASP.model.CustomUserDetails;
import java.io.Serializable;

public class JwtTokenResponse implements Serializable {

    private static final long serialVersionUID = 8317676219297719109L;

    private final String token;
    private CustomUserDetails customUserDetails;

    public JwtTokenResponse(String token, CustomUserDetails customUserDetails) {
        this.token = token;
        this.customUserDetails = customUserDetails;
    }

    public String getToken() {
        return this.token;
    }

    public CustomUserDetails getCustomUserDetails() {
        return customUserDetails;
    }

}
