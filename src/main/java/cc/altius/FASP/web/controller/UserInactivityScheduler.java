/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Scheduled job and REST API endpoint to handle user inactivity reminder emails and auto-inactivations.
 *
 * @author altius
 */
@RestController
@Tag(
    name = "User Inactivity",
    description = "Scheduler and REST API to check user inactivity"
)
@RequestMapping("/api")
public class UserInactivityScheduler {

    private final Logger logger = LoggerFactory.getLogger(UserInactivityScheduler.class);

    @Autowired
    private UserService userService;

    // Runs daily at 2:00 AM server time, and also available via REST API (GET and POST)
    @Scheduled(cron = "${scheduler.userInactivityCron:0 0 2 * * ?}")
    @GetMapping(value = {"/user/runInactivityChecks", "/test/runInactivityChecks"})
    @PostMapping(value = {"/user/runInactivityChecks", "/test/runInactivityChecks"})
    @Operation(summary = "Run inactivity checks", description = "Triggers user inactivity reminder emails and auto-inactivations")
    public ResponseEntity<Map<String, Object>> runInactivityChecks() {
        logger.info("Triggered User Inactivity check");
        try {
            Map<String, Object> result = this.userService.processInactivityRemindersAndLockouts();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error executing user inactivity checks", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
