/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.service.UserManualService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author altius
 */
@Service
public class UserManualServiceImpl implements UserManualService {

    @Value("${qat.filePath}")
    private String QAT_FILE_PATH;
    @Value("${qat.additionalFiles.path}")
    private String QAT_ADDITIONAL_FILES;
    @Value("${qat.userGuideFileName}")
    private String QAT_USER_GUIDE;

    @Override
    public void uploadUserManual(MultipartFile file) {
        try {
            byte[] userManualBytes = file.getBytes();
            String attachmentFilePath = QAT_FILE_PATH.concat(QAT_ADDITIONAL_FILES).concat(QAT_USER_GUIDE);

            File attachmentFile = new File(attachmentFilePath);
            FileOutputStream attachmentFileFos;
            try {
                attachmentFileFos = new FileOutputStream(attachmentFile);
                attachmentFileFos.write(userManualBytes);
                attachmentFileFos.flush();
                attachmentFileFos.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            Logger.getLogger(UserManualServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
