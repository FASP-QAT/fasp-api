/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Language;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.LanguageService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Map;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api")
@Tag(
    name = "Language",
    description = "Manage system languages and localization settings"
)
public class LanguageRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LanguageService languageService;
    @Autowired
    private UserService userService;

    /**
     * Get the entire list of Static labels for the Language
     *
     * @param languageCode
     * @return
     */
    @GetMapping("/locales/{languageCode}")
    @Operation(
        summary = "Get Language JSON",
        description = "Retrieve localized static labels for a given language code."
    )
    @Parameter(name = "languageCode", description = "The language code to retrieve localized labels for")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Map.class)), responseCode = "200", description = "Returns the localized labels for the given language code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting language JSON")
    ResponseEntity getLanguageJson(@PathVariable("languageCode") String languageCode) {
        return new ResponseEntity(this.languageService.getLanguageJsonForStaticLabels(languageCode), HttpStatus.OK); // 200
    }

    /**
     * Get list of active Languages
     *
     * @param auth
     * @return
     */
    @GetMapping(value = "/language")
    @Operation(
        summary = "Get Languages",
        description = "Retrieve a list of all active languages."
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Language.class))), responseCode = "200", description = "Returns the list of active languages")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting language list")
    public ResponseEntity getLanguageList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.languageService.getLanguageList(true, curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while getting language list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of all Languages
     *
     * @param auth
     * @return
     */
    @GetMapping(value = "/language/all")
    @Operation(
        summary = "Get Languages All",
        description = "Retrieve a complete list of all languages (active and disabled)."
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Language.class))), responseCode = "200", description = "Returns the list of all languages")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting language list")
    public ResponseEntity getLanguageListAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.languageService.getLanguageList(false, curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while getting language list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get Language by Id
     *
     * @param languageId
     * @param auth
     * @return
     */
    @GetMapping(value = "/language/{languageId}")
    @Operation(
        summary = "Get Language",
        description = "Retrieve a language by its ID."
    )
    @Parameter(name = "languageId", description = "The ID of the language to retrieve")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Language.class)), responseCode = "200", description = "Returns the language for the given ID")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Language not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting language")
    public ResponseEntity getLanguageById(@PathVariable("languageId") int languageId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.languageService.getLanguageById(languageId, curUser), HttpStatus.OK); // 200
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while getting languageId=" + languageId, er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while getting languageId=" + languageId, e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Add a Language
     *
     * @param language
     * @param auth
     * @return
     */
    @PostMapping(value = "/language")
    @Operation(
        summary = "Add Language",
        description = "Add a new language to the system."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The Language to add",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Language.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to add this object")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "Language code already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while adding language")
    public ResponseEntity addLanguage(@RequestBody(required = true) Language language, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            int languageId = this.languageService.addLanguage(language, curUser);
            if (languageId > 0) {
                return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK); // 200
            } else {
                logger.error("Error while adding language no Id returned");
                return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
            }
        } catch (DuplicateKeyException e) {
            logger.error("Error while adding language", e);
            return new ResponseEntity(new ResponseCode("static.message.languageCodeAlreadyExists"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (Exception e) {
            logger.error("Error while adding language", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }

    }

    /**
     * Update a Language
     *
     * @param language
     * @param auth
     * @return
     */
    @PutMapping(value = "/language")
    @Operation(
        summary = "Update Language",
        description = "Update an existing language."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The Language to update",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Language.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to update this object")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "Language code already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating language")
    public ResponseEntity editLanguage(@RequestBody(required = true) Language language, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            int updatedId = this.languageService.editLanguage(language, curUser);
            if (updatedId > 0) {
                return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK); // 200
            } else {
                logger.error("Error while updating language, no rows updated");
                return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
            }
        } catch (DuplicateKeyException e) {
            logger.error("Error while updating language", e);
            return new ResponseEntity(new ResponseCode("static.message.languageCodeAlreadyExists"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (Exception e) {
            logger.error("Error while updating language", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

}
