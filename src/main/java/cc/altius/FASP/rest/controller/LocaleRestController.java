/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author akil
 */
@RestController("/api/locales")
public class LocaleRestController {

    @Autowired
    private LanguageService languageService;

    /**
     * Api returns the complete file that the system uses for the Translations.
     * Language is based on the Locale provided.
     *
     * @param languageCode for which you need the language label for
     * @return file containing labels for the Translations
     */
    @GetMapping("/{languageCode}")
    ResponseEntity getLanguageJson(@PathVariable("languageCode") String languageCode) {
        return new ResponseEntity(this.languageService.getLanguageJsonForStaticLabels(languageCode), HttpStatus.OK);
    }
}
