/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.Currency;
import java.util.List;
import java.util.Map;

/**
 *
 * @author palash
 */
public interface CurrencyDao {

    public int addCurrency(Currency currency);

    public List<Currency> getCurrencyList(boolean active);

    public int updateCurrency(Currency currency);

    public String getAllCurrencyCode();
    
    public void updateCurrencyConversionrate(Map<String, Double> currencyConversions);
}
