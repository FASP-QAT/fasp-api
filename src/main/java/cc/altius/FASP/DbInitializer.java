/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP;

import cc.altius.FASP.dao.LoggerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author akil
 */
@Component
public class DbInitializer {

    @Component
    public class DBInitializer implements ApplicationRunner {

        @Autowired
        private LoggerDao loggerDao;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            this.loggerDao.createTables();
        }

    }
}
