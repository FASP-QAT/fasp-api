/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.service.ImportProductCatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public void importProductcatalogue() {
        System.out.println("inside controller------------------");
        String orderDataFilePath = "/home/altius/Documents/FASP/ARTEMISDATA/item_data_202005191039.xml";
        this.importProductCatalogueService.importProductCatalogue(orderDataFilePath);

    }
}
