/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.DTO.CurrencyQuote;
import cc.altius.FASP.service.CurrencyService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ekta
 */
@Controller
public class CurrencyConversionController {

    @Autowired
    CurrencyService currencyService;
//@RequestMapping(value="/test.htm")
    @Scheduled(cron = "0 0 8 * * ?")
//@Scheduled(cron = "0 */1 * * * *")
    public void updateCurrencyConversion() {

        String currencyCodes = currencyService.getAllCurrencyCode();
        Map<String, Double> currencyConversions = null;
        if (currencyCodes != null && !currencyCodes.isEmpty() && !currencyCodes.equals("")) {
            currencyConversions = getLiveCurrencyValue(currencyCodes);
        }
        System.out.println(""+currencyConversions);
        if(currencyConversions!=null){
            currencyService.updateCurrencyConversionrate(currencyConversions);
        }

    }

    private Map<String, Double> getLiveCurrencyValue(String allCurrencyCodes) {
        RestTemplate apiCall = new RestTemplate();
        CurrencyQuote cq = apiCall.getForObject("http://www.apilayer.net/api/live?access_key=42e787cb76610f5756965e39808148f2&format=1&currencies=" + allCurrencyCodes, CurrencyQuote.class);
        return cq.getQuotes();
    }
}
