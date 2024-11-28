/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;

/**
 *
 * @author akil
 */
@RestController
@Tag(
    name = "File",
    description = "Handles file downloads for QAT-related resources."
)
public class FileController {

    @Value("${qat.homeFolder}")
    private String QAT_FILE_PATH;
    @Value("${qat.additionalFiles.path}")
    private String QAT_ADDITIONAL_FILES;
    @Value("${qat.pipelineConvertorFileName.Linux}")
    private String PIPELINE_CONVERTOR_LINUX;
    @Value("${qat.pipelineConvertorFileName.Windows}")
    private String PIPELINE_CONVERTOR_WINDOWS;
    @Value("${qat.userGuideFileName}")
    private String QAT_USER_GUIDE;
    @Value("${qat.consumptionDataEntryTemplate}")
    private String CONSUMPTION_DATA_ENTRY_TEMPLATE;
    @Value("${qat.inventoryDataEntryTemplate}")
    private String INVENTORY_DATA_ENTRY_TEMPLATE;
    @Value("${qat.adjustmentsDataEntryTemplate}")
    private String ADJUSTMENT_DATA_ENTRY_TEMPLATE;
    @Value("${qat.shipmentDataEntryTemplate}")
    private String SHIPMENT_DATA_ENTRY_TEMPLATE;


    /**
     * Get File by FileId
     *
     * @param fileName
     * @param response
     * @param auth
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    @GetMapping("/file/{fileName}")
    @Operation(
        summary = "Get File",
        description = "Retrieve a file based on the provided file name"
    )
    @Parameter(name = "fileName", description = "The name of the file to retrieve. Options: qatUserGuide, pipelineConvertorLinux, pipelineConvertorWindows," +
                                                "consumptionDataEntryTemplate, inventoryDataEntryTemplate, adjustmentsDataEntryTemplate," +
                                                "shipmentDataEntryTemplate")
    @ApiResponse(content = @Content(mediaType = "application/octet-stream"), responseCode = "200", description = "Returns the requested file")
    @ApiResponse(content = @Content(mediaType = "application/octet-stream"), responseCode = "500", description = "Internal error that prevented the retrieval of the requested file")
    public byte[] getFile(@PathVariable("fileName") String fileName, HttpServletResponse response, Authentication auth) throws FileNotFoundException, IOException {
        FileInputStream fin = null;
        switch (fileName) {
            case "qatUserGuide":
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + QAT_USER_GUIDE);
                response.setStatus(HttpServletResponse.SC_OK);
                fin = new FileInputStream(new File(QAT_FILE_PATH + QAT_ADDITIONAL_FILES + QAT_USER_GUIDE));
                break;
            case "pipelineConvertorLinux":
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + PIPELINE_CONVERTOR_LINUX);
                response.setStatus(HttpServletResponse.SC_OK);
                fin = new FileInputStream(new File(QAT_FILE_PATH + QAT_ADDITIONAL_FILES + PIPELINE_CONVERTOR_LINUX));
                break;
            case "pipelineConvertorWindows":
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=" + PIPELINE_CONVERTOR_WINDOWS);
                response.setStatus(HttpServletResponse.SC_OK);
                fin = new FileInputStream(new File(QAT_FILE_PATH + QAT_ADDITIONAL_FILES + PIPELINE_CONVERTOR_WINDOWS));
                break;
            case "consumptionDataEntryTemplate":
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=" + CONSUMPTION_DATA_ENTRY_TEMPLATE);
                response.setStatus(HttpServletResponse.SC_OK);
                fin = new FileInputStream(new File(QAT_FILE_PATH + QAT_ADDITIONAL_FILES + CONSUMPTION_DATA_ENTRY_TEMPLATE));
                break;
            case "inventoryDataEntryTemplate":
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=" + INVENTORY_DATA_ENTRY_TEMPLATE);
                response.setStatus(HttpServletResponse.SC_OK);
                fin = new FileInputStream(new File(QAT_FILE_PATH + QAT_ADDITIONAL_FILES + INVENTORY_DATA_ENTRY_TEMPLATE));
                break;
            case "adjustmentsDataEntryTemplate":
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=" + ADJUSTMENT_DATA_ENTRY_TEMPLATE);
                response.setStatus(HttpServletResponse.SC_OK);
                fin = new FileInputStream(new File(QAT_FILE_PATH + QAT_ADDITIONAL_FILES + ADJUSTMENT_DATA_ENTRY_TEMPLATE));
                break;
            case "shipmentDataEntryTemplate":
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=" + SHIPMENT_DATA_ENTRY_TEMPLATE);
                response.setStatus(HttpServletResponse.SC_OK);
                fin = new FileInputStream(new File(QAT_FILE_PATH + QAT_ADDITIONAL_FILES + SHIPMENT_DATA_ENTRY_TEMPLATE));
                break;
        }
        return IOUtils.toByteArray(fin);
    }

}
