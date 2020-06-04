/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.LanguageDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Language;
import cc.altius.FASP.service.LanguageService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    private LanguageDao languageDao;

    @Override
    public List<Language> getLanguageList(boolean active, CustomUserDetails curUser) {
        return this.languageDao.getLanguageList(active, curUser);
    }

    @Override
    public Language getLanguageById(int languageId, CustomUserDetails curUser) {
        return this.languageDao.getLanguageById(languageId, curUser);
    }

    @Override
    public int addLanguage(Language language, CustomUserDetails curUser) {
        return this.languageDao.addLanguage(language, curUser);
    }

    @Override
    public int editLanguage(Language language, CustomUserDetails curUser) {
        return this.languageDao.editLanguage(language, curUser);
    }

    @Override
    public List<Language> getLanguageListForSync(String lastSyncDate) {
        return this.languageDao.getLanguageListForSync(lastSyncDate);
    }

    @Override
    public Map<String, String> getLanguageJsonForStaticLabels(String language) {
        return this.languageDao.getLanguageJsonForStaticLabels(language);
    }

}
