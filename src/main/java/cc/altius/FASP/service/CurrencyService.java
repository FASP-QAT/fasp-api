/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.Currency;
import java.util.List;

/**
 *
 * @author palash
 */
public interface CurrencyService {
    
    public int addCurrency(Currency currency);
    
    public List<Currency> getCurrencyList(boolean active);
    
    public int updateCurrency(Currency currency);
}
