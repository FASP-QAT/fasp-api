/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.Language;
import java.util.List;

/**
 *
 * @author altius
 */
public interface LanguageService {

    public List<Language> getLanguageList(boolean active);
    
    public int addLanguage(Language language);
    
    public int editLanguage(Language language);
}
