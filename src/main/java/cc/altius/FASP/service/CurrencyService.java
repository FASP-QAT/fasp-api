/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.Currency;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgCurrencyDTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author palash
 */
public interface CurrencyService {
    
    public int addCurrency(Currency currency, CustomUserDetails curUser);
    
    public List<Currency> getCurrencyList(boolean active, CustomUserDetails curUser);
    
    public int updateCurrency(Currency currency, CustomUserDetails curUser);
    
    public Currency getCurrencyById(int currencyId, CustomUserDetails curUser);
    
    public String getAllCurrencyCode();

    public void updateCurrencyConversionRate(Map<String, Double> currencyConversions);
    
    public List<PrgCurrencyDTO> getCurrencyListForSync(String lastSyncDate);
}
