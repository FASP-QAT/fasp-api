/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.DTO.CurrencyQuote;
import cc.altius.FASP.service.CurrencyService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ekta
 */
@Controller
public class CurrencyConversionController {

    @Autowired
    CurrencyService currencyService;
    private @Value("#{credentials['apilayerToken']}")
    String APILAYER_TOKEN;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron = "0 0 8 * * ?")
    public void updateCurrencyConversion() {
        logger.info("Starting the Cron for Currency Conversion");
        try {
            String currencyCodes = currencyService.getAllCurrencyCode();
            Map<String, Double> currencyConversions = null;
            if (currencyCodes != null && !currencyCodes.isEmpty() && !currencyCodes.equals("")) {
                currencyConversions = getLiveCurrencyValue(currencyCodes);
            }
            if (currencyConversions != null) {
                currencyService.updateCurrencyConversionRate(currencyConversions);
            }
        } catch (Exception e) {
            logger.info("Error in Currency Scheduler", e);
        }
    }

    private Map<String, Double> getLiveCurrencyValue(String allCurrencyCodes) {
        RestTemplate apiCall = new RestTemplate();
        CurrencyQuote cq = apiCall.getForObject("http://www.apilayer.net/api/live?access_key=" + APILAYER_TOKEN + "&format=1&currencies=" + allCurrencyCodes, CurrencyQuote.class);
        return cq.getQuotes();
    }
}
