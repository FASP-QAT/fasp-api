/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author akil
 */
@Controller
public class LanguageRestController {

    
    @GetMapping("/locales/{language}")
    @ResponseBody
    String getLanguageJson(@PathVariable("language") String language) {
        switch (language) {
            case "sp":
                return "{\n"
                        + "\"btn.primary\": \"primaria\",\n"
                        + "\"btn.secondary\": \"secundaria\",\n"
                        + "\"btn.success\": \"éxito\",\n"
                        + "\"btn.warning\": \"Advertencia\",\n"
                        + "\"btn.info\": \"Informacion\",\n"
                        + "\"btn.light\": \"Ligera\",\n"
                        + "\"btn.dark\": \"Oscura\",\n"
                        + "\"btn.link\": \"Enlace\",\n"
                        + "\"btn.stdtype\": \"Botón estándar\",\n"
                        + "\"btn.otltype\": \"Botón de esquema\",\n"
                        + "\"btn.ghostType\": \"Botón fantasma\",\n"
                        + "\"btn.sqrType\": \"Botón cuadrado\",\n"
                        + "\"btn.statenorm\": \"Estado normal\",\n"
                        + "\"btn.stateActive\": \"Estado activo\",\n"
                        + "\"btn.stateDisable\": \"Estado discapacitado\"\n"
                        + "}";
            default:
            case "en":
                return "{\n"
                        + "\"btn.primary\": \"primary\",\n"
                        + "\"btn.secondary\": \"secondary\",\n"
                        + "\"btn.success\": \"success\",\n"
                        + "\"btn.warning\": \"warning\",\n"
                        + "\"btn.info\": \"info\",\n"
                        + "\"btn.light\": \"light\",\n"
                        + "\"btn.dark\": \"dark\",\n"
                        + "\"btn.link\": \"link\",\n"
                        + "\"btn.stdtype\": \"stdtype\",\n"
                        + "\"btn.otltype\": \"otltype\",\n"
                        + "\"btn.ghostType\": \"ghostType\",\n"
                        + "\"btn.sqrType\": \"sqrType\",\n"
                        + "\"btn.statenorm\": \"statenorm\",\n"
                        + "\"btn.stateActive\": \"stateActive\",\n"
                        + "\"btn.stateDisable\": \"stateDisable\"\n"
                        + "}";

        }
    }
}
