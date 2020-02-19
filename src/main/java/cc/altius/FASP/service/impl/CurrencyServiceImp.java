/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.CurrencyDao;
import cc.altius.FASP.model.Currency;
import cc.altius.FASP.service.CurrencyService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author palash
 */
@Service
public class CurrencyServiceImp implements CurrencyService{
@Autowired
private CurrencyDao currencyDao;
    @Override
    public int addCurrency(Currency currency) {
        return this.currencyDao.addCurrency(currency);
    }

    @Override
    public List<Currency> getCurrencyList(boolean active) {
        return this.currencyDao.getCurrencyList(active);
    }

    @Override
    public int updateCurrency(Currency currency) {
       return  this.currencyDao.updateCurrency(currency);
    }

    @Override
    public String getAllCurrencyCode() {
    return  this.currencyDao.getAllCurrencyCode();    
    }

    @Override
    public void updateCurrencyConversionrate(Map<String, Double> currencyConversions) {
        this.currencyDao.updateCurrencyConversionrate(currencyConversions);
    }
    
    
}
