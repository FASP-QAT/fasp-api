/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.DTO.QuantimedImportDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author altius
 */
public interface QuantimedImportService {
    
    public QuantimedImportDTO importForecastData(MultipartFile file, String programId);
}
