/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.controller;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;
import cc.altius.FASP.ARTMIS.service.ImportArtmisDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 *
 * @author altius
 */
@RestController
@Tag(
    name = "Import Shipment Data",
    description = "Import Shipment Data from ARTMIS"
)
public class ImportArtmisDataController {

    @Autowired
    private ImportArtmisDataService importArtmisDataService;

    @GetMapping(value = "/importShipmentData")
    
//    @Scheduled(cron = "00 */05 * * * *")
    public void importArtemisData() throws ParserConfigurationException, SAXException, IOException {
        this.importArtmisDataService.importOrderAndShipmentData();
    }
}
