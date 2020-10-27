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

/**
 *
 * @author altius
 */
@RestController
public class ImportArtmisDataController {

    @Autowired
    private ImportArtmisDataService importArtmisDataService;

    @GetMapping(value = "/api/importShipmentData")
//    @Scheduled(cron = "00 */05 * * * *")
    public void importArtemisData() throws ParserConfigurationException, SAXException, IOException {
//        String orderDataFilePath = "/home/altius/Documents/FASP/New ARTMIS Files/order_data_202005211301.xml";
//        String shipmentDataFilePath = "/home/altius/Documents/FASP/New ARTMIS Files/shipment_data_202005211314.xml";
        System.out.println("Inside the Controller");
        this.importArtmisDataService.importOrderAndShipmentData();

    }
}
