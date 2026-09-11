package cc.altius.FASP.rest.controller;

import cc.altius.FASP.dao.DashboardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/application")
public class ApplicationRestController {

    @Autowired
    private DashboardDao dashboardDao;

    @GetMapping("/backup-status")
    public ResponseEntity<Boolean> getBackupStatus() {
        try {
            return new ResponseEntity<>(this.dashboardDao.isBackupRunning(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
