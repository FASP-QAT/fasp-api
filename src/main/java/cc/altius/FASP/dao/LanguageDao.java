/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.DTO.PrgLanguageDTO;
import cc.altius.FASP.model.Language;
import java.util.List;
import java.util.Map;

/**
 *
 * @author altius
 */
public interface LanguageDao {

    public List<Language> getLanguageList(boolean active);

    public int addLanguage(Language language);

    public int editLanguage(Language language);

    public List<PrgLanguageDTO> getLanguageListForSync(String lastSyncDate);
    
    public Map<String, String> getLanguageJsonForStaticLabels(String language);
}
