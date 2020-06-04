/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.controller;

import cc.altius.FASP.service.ImportArtemisDataService;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xml.sax.SAXException;

/**
 *
 * @author altius
 */
@Controller
public class ImportArtemisDataController {

    @Autowired
    private ImportArtemisDataService importArtemisDataService;

    @RequestMapping(value = "import")
//    @Scheduled(cron = "00 */05 * * * *")
    public void importArtemisData() throws ParserConfigurationException, SAXException, IOException {
        System.out.println("inside controller------------------");
        String orderDataFilePath = "/home/altius/Documents/FASP/New ARTMIS Files/order_data_202005211301.xml";
        String shipmentDataFilePath = "/home/altius/Documents/FASP/New ARTMIS Files/shipment_data_202005211314.xml";
        this.importArtemisDataService.importOrderAndShipmentData(orderDataFilePath, shipmentDataFilePath);

    }
}
