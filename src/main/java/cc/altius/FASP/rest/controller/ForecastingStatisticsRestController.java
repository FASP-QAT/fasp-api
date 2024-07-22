/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Value("${url.forecastStatsServer}")
    private String forecastStatsServerUrl;
    private double pMin = 0, pMax = 4, pStep = 1;
    private double  dMin = 0, dMax = 4, dStep = 1;
    private double qMin = 0, qMax = 4, qStep = 1;
    private double alphaMin = 0, alphaMax = 0, alphaStep = 0;
    private double betaMin = 0, betaMax = 0, betaStep = 0;
    private double gammaMin = 0, gammaMax = 0, gammaStep = 0;
    

    @PostMapping(path = "/arima/optimize/{optimize}")
    public ResponseEntity postArima(@PathVariable(value = "optimize", required = false) boolean optimize, HttpServletRequest request, Authentication auth) {
        try {
            String json = IOUtils.toString(request.getReader());
            System.out.println(json);
            if (optimize == false) {
                RestTemplate apiCall = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity = new HttpEntity<String>(json, headers);
                String output = apiCall.postForObject(forecastStatsServerUrl + "/arima", entity, String.class
                );
                return new ResponseEntity(output, HttpStatus.OK);
            } else {
                // run for Optimized values
                RestTemplate apiCall = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity = new HttpEntity<String>(json, headers);
                String output = apiCall.postForObject(this.forecastStatsServerUrl + "/arima", entity, String.class
                );
                return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
            }
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
            String output = apiCall.postForObject(this.forecastStatsServerUrl + "/tes", entity, String.class);
            return new ResponseEntity(output, HttpStatus.OK);
        } catch (IOException ioe) {
            logger.error("Error while trying to read the Json", ioe);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
        } catch (Exception e) {
            logger.error("Error while trying to read the Json", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/regression")
    public ResponseEntity postRegression(HttpServletRequest request, Authentication auth) {
        try {
            String json = IOUtils.toString(request.getReader());
            RestTemplate apiCall = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(json, headers);
            String output = apiCall.postForObject(forecastStatsServerUrl + "/regression", entity, String.class);
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
