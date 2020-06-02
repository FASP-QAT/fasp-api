/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.service.ImportProductCatalogueService;
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
public class ImportProductCatalogueController {

    @Autowired
    private ImportProductCatalogueService importProductCatalogueService;

    @RequestMapping(value = "import1")
//    @Scheduled(cron = "00 */05 * * * *")
    public void importProductcatalogue() throws ParserConfigurationException, SAXException, IOException{
        System.out.println("inside controller------------------");
        String orderDataFilePath = "/home/altius/Documents/FASP/ARTEMISDATA/item_data_2020051910399.xml";
        this.importProductCatalogueService.importProductCatalogue(orderDataFilePath);

    }
}
