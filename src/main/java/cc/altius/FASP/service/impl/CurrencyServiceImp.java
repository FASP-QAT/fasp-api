/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.CurrencyDao;
import cc.altius.FASP.model.Currency;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgCurrencyDTO;
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
public class CurrencyServiceImp implements CurrencyService {

    @Autowired
    private CurrencyDao currencyDao;

    @Override
    public int addCurrency(Currency currency, CustomUserDetails curUser) {
        return this.currencyDao.addCurrency(currency, curUser);
    }

    @Override
    public List<Currency> getCurrencyList(boolean active, CustomUserDetails curUser) {
        return this.currencyDao.getCurrencyList(active, curUser);
    }

    @Override
    public int updateCurrency(Currency currency, CustomUserDetails curUser) {
        return this.currencyDao.updateCurrency(currency, curUser);
    }

    @Override
    public Currency getCurrencyById(int currencyId, CustomUserDetails curUser) {
        return this.currencyDao.getCurrencyById(currencyId, curUser);
    }

    @Override
    public String getAllCurrencyCode() {
        return this.currencyDao.getAllCurrencyCode();
    }

    @Override
    public void updateCurrencyConversionRate(Map<String, Double> currencyConversions) {
        this.currencyDao.updateCurrencyConversionRate(currencyConversions);
    }

    @Override
    public List<Currency> getCurrencyListForSync(String lastSyncDate) {
        return this.currencyDao.getCurrencyListForSync(lastSyncDate);
    }

}
