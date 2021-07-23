/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author altius
 */
public interface UserManualService {

    public void uploadUserManual(MultipartFile file) throws IOException, FileNotFoundException;

}
