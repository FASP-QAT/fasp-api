/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.DTO.ArimaInputDTO;
import cc.altius.FASP.model.DTO.ForecastMethodOptimizationDTO;
import cc.altius.FASP.model.DTO.ForecastMethodOptimizationDTOComparator;
import cc.altius.FASP.model.DTO.ForecastMethodOutputDTO;
import cc.altius.FASP.model.DTO.TesInputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import cc.altius.FASP.model.ResponseCode;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api/forecastStats")
@Tag(
    name = "Forecasting Statistics",
    description = "Manage statistical forecasting operations including ARIMA models, triple exponential smoothing, and regression analysis"
)
public class ForecastingStatisticsRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${forecastStats.server.url}")
    private String forecastStatsServerUrl;
    @Value("${forecastStats.arima.pMin}")
    private double pMin;
    @Value("${forecastStats.arima.pMax}")
    private double pMax;
    @Value("${forecastStats.arima.pStep}")
    private double pStep;
    @Value("${forecastStats.arima.dMin}")
    private double dMin;
    @Value("${forecastStats.arima.dMax}")
    private double dMax;
    @Value("${forecastStats.arima.dStep}")
    private double dStep;
    @Value("${forecastStats.arima.qMin}")
    private double qMin;
    @Value("${forecastStats.arima.qMax}")
    private double qMax;
    @Value("${forecastStats.arima.qStep}")
    private double qStep;
    @Value("${forecastStats.tes.alphaMin}")
    private double alphaMin;
    @Value("${forecastStats.tes.alphaMax}")
    private double alphaMax;
    @Value("${forecastStats.tes.alphaStep}")
    private double alphaStep;
    @Value("${forecastStats.tes.betaMin}")
    private double betaMin;
    @Value("${forecastStats.tes.betaMax}")
    private double betaMax;
    @Value("${forecastStats.tes.betaStep}")
    private double betaStep;
    @Value("${forecastStats.tes.gammaMin}")
    private double gammaMin;
    @Value("${forecastStats.tes.gammaMax}")
    private double gammaMax;
    @Value("${forecastStats.tes.gammaStep}")
    private double gammaStep;

    /**
     * Run Arima on dataset
     *
     * @param request
     * @param auth
     * @return
     */
    @PostMapping(path = "/arima")
    @Operation(
        summary = "Get Forecasting Statistics (ARIMA)",
        description = "Optimize ARIMA model parameters for given dataset. Returns the best-fit parameters and their corresponding RMSE."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The input data for the ARIMA optimization",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArimaInputDTO.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = String.class)), responseCode = "200", description = "Returns the best-fit parameters and their corresponding RMSE")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while parsing the input data")
    public ResponseEntity postArima(@RequestBody ArimaInputDTO input, HttpServletRequest request, Authentication auth) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(input);
            if (input.isOptimize() == false) {
                RestTemplate apiCall = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity = new HttpEntity<String>(json, headers);
                String output = apiCall.postForObject(forecastStatsServerUrl + "/arima", entity, String.class
                );
                ForecastMethodOutputDTO opt = gson.fromJson(output, ForecastMethodOutputDTO.class);
                opt.setVar1(input.getP());
                opt.setVar2(input.getD());
                opt.setVar3(input.getQ());
                return new ResponseEntity(output, HttpStatus.OK);
            } else {
                Map<ForecastMethodOptimizationDTO, ForecastMethodOutputDTO> outputMap = new HashMap<>();
                List<ForecastMethodOptimizationDTO> outputList = new LinkedList<>();
                for (double p = pMin; p <= pMax; p += pStep) {
                    for (double d = dMin; d <= dMax; d += dStep) {
                        for (double q = qMin; q <= qMax; q += qStep) {
                            input.setP(p);
                            input.setD(d);
                            input.setQ(q);
                            RestTemplate apiCall = new RestTemplate();
                            HttpHeaders headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(input), headers);
                            try {
                                String output = apiCall.postForObject(this.forecastStatsServerUrl + "/arima", entity, String.class
                                );
                                ForecastMethodOutputDTO opt = gson.fromJson(output, ForecastMethodOutputDTO.class);
                                opt.setVar1(input.getP());
                                opt.setVar2(input.getD());
                                opt.setVar3(input.getQ());
                                double rmse = opt.getRMSE(input.getData());
                                ForecastMethodOptimizationDTO key = new ForecastMethodOptimizationDTO(input.getP(), input.getD(), input.getQ(), rmse);
                                if (key.getError() != null) {
                                    outputList.add(key);
                                    outputMap.put(key, opt);
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                }
                outputList.sort(new ForecastMethodOptimizationDTOComparator());
                ForecastMethodOptimizationDTO bestFit = outputList.get(0);
                if (bestFit != null) {
                    return new ResponseEntity(outputMap.get(bestFit), HttpStatus.OK);
                } else {
                    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } catch (Exception e) {
            logger.error("Error while trying to read the Json", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Run TES on dataset
     *
     * @param request
     * @param auth
     * @return
     */
    @PostMapping(path = "/tes")
    @Operation(
        summary = "Get Forecasting Statistics (TES)",
        description = "Optimize Triple Exponential Smoothing (TES) model parameters for given dataset. Returns the best-fit parameters and their corresponding Root Mean Square Error (RMSE)."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The input data for the TES optimization",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = TesInputDTO.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = String.class)), responseCode = "200", description = "Returns the best-fit parameters and their corresponding Root Mean Square Error (RMSE)")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while parsing the input data")
    public ResponseEntity postTes(@RequestBody TesInputDTO input, HttpServletRequest request, Authentication auth) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(input);
            if (input.isOptimize() == false) {
                RestTemplate apiCall = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity = new HttpEntity<String>(json, headers);
                String output = apiCall.postForObject(this.forecastStatsServerUrl + "/tes", entity, String.class);
                ForecastMethodOutputDTO opt = gson.fromJson(output, ForecastMethodOutputDTO.class);
                opt.setVar1(input.getAlpha());
                opt.setVar2(input.getBeta());
                opt.setVar3(input.getGamma());
                double rmse = opt.getRMSE(input.getData());
                return new ResponseEntity(output, HttpStatus.OK);
            } else {
                Map<ForecastMethodOptimizationDTO, ForecastMethodOutputDTO> outputMap = new HashMap<>();
                List<ForecastMethodOptimizationDTO> outputList = new LinkedList<>();
                for (double alpha = alphaMin; alpha <= alphaMax; alpha += alphaStep) {
                    for (double beta = betaMin; beta <= betaMax; beta += betaStep) {
                        for (double gamma = gammaMin; gamma <= gammaMax; gamma += gammaStep) {
                            input.setAlpha(alpha);
                            input.setBeta(beta);
                            input.setGamma(gamma);
                            RestTemplate apiCall = new RestTemplate();
                            HttpHeaders headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(input), headers);
                            try {
                                String output = apiCall.postForObject(this.forecastStatsServerUrl + "/tes", entity, String.class);
                                ForecastMethodOutputDTO opt = gson.fromJson(output, ForecastMethodOutputDTO.class);
                                opt.setVar1(input.getAlpha());
                                opt.setVar2(input.getBeta());
                                opt.setVar3(input.getGamma());
                                double rmse = opt.getRMSE(input.getData());
                                ForecastMethodOptimizationDTO key = new ForecastMethodOptimizationDTO(input.getAlpha(), input.getBeta(), input.getGamma(), rmse);
                                if (key.getError() != null) {
                                    outputList.add(key);
                                    outputMap.put(key, opt);
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                }
                outputList.sort(new ForecastMethodOptimizationDTOComparator());
                ForecastMethodOptimizationDTO bestFit = outputList.get(0);
                if (bestFit != null) {
                    return new ResponseEntity(outputMap.get(bestFit), HttpStatus.OK);
                } else {
                    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } catch (Exception e) {
            logger.error("Error while trying to read the Json", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Run Regression on dataset
     *
     * @param request
     * @param auth
     * @return
     */
    @PostMapping(path = "/regression")
    @Operation(
        summary = "Get Forecasting Statistics (Regression)",
        description = "Perform regression analysis on given dataset. Returns the regression results."
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = String.class)), responseCode = "200", description = "Returns the regression results")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while parsing the input data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "412", description = "Internal error while parsing the input data")
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
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED); // 412
        } catch (Exception e) {
            logger.error("Error while trying to read the Json", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

}
