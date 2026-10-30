/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.UserNotificationPreference;
import cc.altius.FASP.service.BroadcastService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api/public")
@Tag(name = "Public Broadcast", description = "Public unsubscribe and email preferences API")
public class PublicBroadcastRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BroadcastService broadcastService;

    /**
     * Get preferences for unsubscribe token
     *
     * @param token
     * @return
     */
    @GetMapping(value = "/unsubscribe/preferences")
    @Operation(summary = "Get preferences by unsubscribe token", description = "Public endpoint to load user preferences via email token")
    public ResponseEntity getPreferencesByToken(@RequestParam("token") String token) {
        try {
            if (token == null || token.trim().isEmpty()) {
                return new ResponseEntity(new ResponseCode("static.message.validationFailed"), HttpStatus.BAD_REQUEST);
            }
            List<UserNotificationPreference> prefs = this.broadcastService.getNotificationPreferencesByToken(token);
            if (prefs == null || prefs.isEmpty()) {
                return new ResponseEntity(new ResponseCode("static.message.invalidToken"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(prefs, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching preferences by token", e);
            return new ResponseEntity(new ResponseCode("static.message.failed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update preferences using unsubscribe token
     *
     * @param payload
     * @return
     */
    @PostMapping(value = "/unsubscribe")
    @Operation(summary = "Opt-out or update preferences by token", description = "Public endpoint to unsubscribe or update preferences via email token")
    public ResponseEntity unsubscribe(@RequestBody Map<String, Object> payload) {
        try {
            String token = (String) payload.get("token");
            if (token == null || token.trim().isEmpty()) {
                return new ResponseEntity(new ResponseCode("static.message.validationFailed"), HttpStatus.BAD_REQUEST);
            }

            // Either a single category unsubscribe
            if (payload.containsKey("categoryId")) {
                int categoryId = Integer.parseInt(payload.get("categoryId").toString());
                int result = this.broadcastService.unsubscribeByToken(token, categoryId);
                if (result == -1) {
                    return new ResponseEntity(new ResponseCode("static.message.cannotOptOutMandatory"), HttpStatus.BAD_REQUEST);
                } else if (result == 0) {
                    return new ResponseEntity(new ResponseCode("static.message.invalidToken"), HttpStatus.NOT_FOUND);
                }
                Map<String, Object> res = new HashMap<>();
                res.put("status", "SUCCESS");
                return new ResponseEntity(res, HttpStatus.OK);
            }

            return new ResponseEntity(new ResponseCode("static.message.validationFailed"), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error while processing unsubscribe by token", e);
            return new ResponseEntity(new ResponseCode("static.message.failed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
