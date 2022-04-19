/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.ResponseCode;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api/forecastStats")
public class ForecastingStatisticsRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping(path = "/arima")
    public ResponseEntity postArima(HttpServletRequest request, Authentication auth) {
        try {
            String json = IOUtils.toString(request.getReader());
            RestTemplate apiCall = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(json, headers);
            String output = apiCall.postForObject("http://50.16.62.249:8000/arima", entity, String.class);
            return new ResponseEntity(output, HttpStatus.OK);
        } catch (IOException ioe) {
            logger.error("Error while trying to read the Json", ioe);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
        } catch (Exception e) {
            logger.error("Error while trying to read the Json", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping(path = "/tes")
    public ResponseEntity postTes(HttpServletRequest request, Authentication auth) {
        try {
            String json = IOUtils.toString(request.getReader());
            RestTemplate apiCall = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(json, headers);
            String output = apiCall.postForObject("http://50.16.62.249:8000/tes", entity, String.class);
            return new ResponseEntity(output, HttpStatus.OK);
        } catch (IOException ioe) {
            logger.error("Error while trying to read the Json", ioe);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
        } catch (Exception e) {
            logger.error("Error while trying to read the Json", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
