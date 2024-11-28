/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.DatabaseTranslationsDTO;
import cc.altius.FASP.model.DTO.StaticLabelDTO;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.service.LabelService;
import cc.altius.FASP.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.Gson;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 *
 * @author palash
 */
@RestController
@RequestMapping("/api")
@Tag(
    name = "Label", 
    description = "Label Management for FASP"
)
public class LabelRestController {

    @Autowired
    private LabelService labelService;
    @Autowired
    private UserService userService;

    /**
     * Get the list of all Database Labels
     *
     * @param auth
     * @return
     */
    @JsonView(Views.InternalView.class)
    @GetMapping(value = "/getDatabaseLabelsListAll")
    @Operation(
        summary = "Get Database Labels",
        description = "Retrieve a complete list of all database labels."
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = DatabaseTranslationsDTO.class))), responseCode = "200", description = "Returns the Database Label list")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retreival of Database Label list")
    public ResponseEntity getDatabaseLabelsList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.labelService.getDatabaseLabelsList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Get the list of all Static Labels
     *
     * @return
     */
    @GetMapping(value = "/getStaticLabelsListAll")
    @Operation(
        summary = "Get Static Labels",
        description = "Retrieve a complete list of all static labels."
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = StaticLabelDTO.class))), responseCode = "200", description = "Returns the Static Label list")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retreival of Static Label list")
    public ResponseEntity getStaticLabelsList() {
        try {
            return new ResponseEntity(this.labelService.getStaticLabelsList(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update the Database labels
     *
     * @param json
     * @param auth
     * @return
     */
    @PutMapping(path = "/saveDatabaseLabels")
    @Operation(
        summary = "Save Database Labels",
        description = "Update the database labels."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of database labels to update",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the update of Database Label list")
    public ResponseEntity putDatabaseLabels(@RequestBody String json, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            Gson gson = new Gson();
            List<String> labelArray = gson.fromJson(json, LinkedList.class);
            this.labelService.saveDatabaseLabels(labelArray, curUser);
            return new ResponseEntity(new ResponseCode("static.label.labelSuccess"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseCode("static.label.labelFail"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update Static labels
     *
     * @param staticLabelList
     * @param auth
     * @return
     */
    @PutMapping(path = "/saveStaticLabels")
    @Operation(
        summary = "Save Static Labels",
        description = "Update the static labels."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of static labels to update",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the update of Static Label list")
    public ResponseEntity putStaticLabels(@RequestBody List<StaticLabelDTO> staticLabelList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.labelService.saveStaticLabels(staticLabelList, curUser);
            return new ResponseEntity(new ResponseCode("static.label.labelSuccess"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseCode("static.label.labelFail"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
