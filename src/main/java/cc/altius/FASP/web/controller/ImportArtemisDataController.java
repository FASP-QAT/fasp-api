/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

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
        String orderDataFilePath = "/home/akil/Desktop/Data/Software/FHI360/Artmis Data Import/202005121226_orderdata.csv";
        String shipmentDataFilePath = "/home/akil/Desktop/Data/Software/FHI360/Artmis Data Import/202005121409_shipmentdata.csv";
        this.importArtemisDataService.importOrderAndShipmentData(orderDataFilePath, shipmentDataFilePath);

    }
}
