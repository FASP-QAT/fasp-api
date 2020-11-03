/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.controller;

import cc.altius.FASP.service.ImportProductCatalogueService;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

/**
 *
 * @author altius
 */
@Controller
public class ImportProductCatalogueController {

    @Autowired
    private ImportProductCatalogueService importProductCatalogueService;

    @RequestMapping(value = "importProductCatalog")
    @ResponseBody
//    @Scheduled(cron = "00 */05 * * * *")
    public String importProductcatalogue() throws ParserConfigurationException, SAXException, IOException {
//        String orderDataFilePath = "/home/akil/Documents/Altius/Software/FHI360/Artmis Data Import";
        return this.importProductCatalogueService.importProductCatalogue();

    }
}
