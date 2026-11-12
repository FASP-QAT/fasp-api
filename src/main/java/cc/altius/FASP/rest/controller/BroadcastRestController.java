/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.BroadcastMessage;
import cc.altius.FASP.model.BroadcastMessageInput;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EmailCategory;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.BroadcastService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api/broadcast")
@Tag(name = "Broadcast", description = "Broadcast Messaging and Listserv API")
public class BroadcastRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BroadcastService broadcastService;

    @Autowired
    private UserService userService;

    private static final String BF_BROADCAST_MESSAGE = "ROLE_BF_BROADCAST_MESSAGE";

    /**
     * Get all broadcast categories
     *
     * @param auth
     * @return
     */
    @GetMapping(value = "/categories")
    @Operation(summary = "Get broadcast categories", description = "Retrieve list of all broadcast categories")
    @ApiResponse(content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EmailCategory.class))), responseCode = "200")
    public ResponseEntity getEmailCategories(Authentication auth) {
        try {
            return new ResponseEntity(this.broadcastService.getEmailCategoryList(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching email categories", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Calculate recipient count matching criteria
     *
     * @param input
     * @param auth
     * @return
     */
    @PostMapping(value = "/recipientCount")
    @Operation(summary = "Get estimated recipient count", description = "Calculates total recipients based on category, business functions, and languages")
    public ResponseEntity getRecipientCount(@RequestBody BroadcastMessageInput input, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(
                    ((CustomUserDetails) auth.getPrincipal()).getUserId(),
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(),
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI()
            );

            if (!curUser.hasBusinessFunction(BF_BROADCAST_MESSAGE)) {
                return new ResponseEntity(new ResponseCode("static.message.unauthorized"), HttpStatus.FORBIDDEN);
            }

            int count = this.broadcastService.getRecipientCount(
                    input.getCategoryId(),
                    input.getTargetRoles(),
                    input.getTargetBusinessFunctions(),
                    input.getTargetLanguages()
            );
            Map<String, Object> response = new HashMap<>();
            response.put("recipientCount", count);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while calculating recipient count", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Send broadcast message
     *
     * @param input
     * @param auth
     * @return
     */
    @PostMapping(value = "/send")
    @Operation(summary = "Send broadcast message", description = "Broadcasts message to targeted users in BCC batches")
    public ResponseEntity sendBroadcast(@RequestBody BroadcastMessageInput input, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(
                    ((CustomUserDetails) auth.getPrincipal()).getUserId(),
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(),
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI()
            );

            if (!curUser.hasBusinessFunction(BF_BROADCAST_MESSAGE)) {
                return new ResponseEntity(new ResponseCode("static.message.unauthorized"), HttpStatus.FORBIDDEN);
            }

            if (input.getSubject() == null || input.getSubject().trim().isEmpty() || input.getBodyHtml() == null || input.getBodyHtml().trim().isEmpty()) {
                return new ResponseEntity(new ResponseCode("static.message.validationFailed"), HttpStatus.BAD_REQUEST);
            }

            int broadcastId = this.broadcastService.sendBroadcast(input, curUser);
            Map<String, Object> result = new HashMap<>();
            result.put("broadcastId", broadcastId);
            result.put("status", broadcastId > 0 ? "SUCCESS" : "NO_RECIPIENTS");
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while sending broadcast", e);
            return new ResponseEntity(new ResponseCode("static.message.failed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Save broadcast draft
     *
     * @param input
     * @param auth
     * @return
     */
    @PostMapping(value = "/draft")
    @Operation(summary = "Save broadcast draft", description = "Saves or updates a broadcast draft without sending")
    public ResponseEntity saveDraft(@RequestBody BroadcastMessageInput input, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(
                    ((CustomUserDetails) auth.getPrincipal()).getUserId(),
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(),
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI()
            );

            if (!curUser.hasBusinessFunction(BF_BROADCAST_MESSAGE)) {
                return new ResponseEntity(new ResponseCode("static.message.unauthorized"), HttpStatus.FORBIDDEN);
            }

            int broadcastId = this.broadcastService.saveDraft(input, curUser);
            Map<String, Object> result = new HashMap<>();
            result.put("broadcastId", broadcastId);
            result.put("status", "DRAFT");
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while saving broadcast draft", e);
            return new ResponseEntity(new ResponseCode("static.message.failed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get recipients by broadcast ID
     *
     * @param broadcastId
     * @param auth
     * @return
     */
    @GetMapping(value = "/{broadcastId}/recipients")
    @Operation(summary = "Get recipients for a broadcast message", description = "Retrieve list of recipients targeted by this broadcast message")
    public ResponseEntity getRecipientsByBroadcastId(@PathVariable("broadcastId") int broadcastId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(
                    ((CustomUserDetails) auth.getPrincipal()).getUserId(),
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(),
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI()
            );

            if (!curUser.hasBusinessFunction(BF_BROADCAST_MESSAGE)) {
                return new ResponseEntity(new ResponseCode("static.message.unauthorized"), HttpStatus.FORBIDDEN);
            }

            return new ResponseEntity(this.broadcastService.getRecipientsByBroadcastId(broadcastId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching broadcast recipients", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get broadcast history
     *
     * @param auth
     * @return
     */
    @GetMapping(value = "/history")
    @Operation(summary = "Get broadcast history", description = "Retrieve list of sent broadcasts")
    @ApiResponse(content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BroadcastMessage.class))), responseCode = "200")
    public ResponseEntity getBroadcastHistory(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(
                    ((CustomUserDetails) auth.getPrincipal()).getUserId(),
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(),
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI()
            );

            if (!curUser.hasBusinessFunction(BF_BROADCAST_MESSAGE)) {
                return new ResponseEntity(new ResponseCode("static.message.unauthorized"), HttpStatus.FORBIDDEN);
            }

            return new ResponseEntity(this.broadcastService.getBroadcastHistory(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching broadcast history", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
