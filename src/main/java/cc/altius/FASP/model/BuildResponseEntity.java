/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import org.springframework.http.ResponseEntity;

/**
 *
 * @author akil
 */
public class BuildResponseEntity {

    public static final ResponseEntity<ResponseFormat> buildSuccessResponseEntity(ResponseFormat responseFormat) {
        return ResponseEntity.ok(responseFormat);
    }
}
