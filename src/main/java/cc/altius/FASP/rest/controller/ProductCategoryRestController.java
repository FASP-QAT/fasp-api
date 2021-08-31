/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.BaseModel;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProductCategory;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.ProductCategoryService;
import cc.altius.FASP.service.UserService;
import cc.altius.utils.TreeUtils.Node;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.io.Serializable;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api/productCategory")
public class ProductCategoryRestController extends BaseModel implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private UserService userService;

    /**
     * API used to get the ProductCategory list for a Realm
     *
     * @param realmId RealmId that you want the ProductCategory List from
     * @param auth
     * @return returns the complete list of productCategories
     */
    @GetMapping("/realmId/{realmId}")
    @Operation(description = "API used to get the complete ProductCategory list for a Realm", summary = "Get ProductCategory list for Realm", tags = ("productCategory"))
    @Parameters(
            @Parameter(name = "realmId", description = "RealmId that you want the ProductCategory list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ProductCategory list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ProductCategory list")
    public ResponseEntity getProductCategory(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.productCategoryService.getProductCategoryListForRealm(curUser, realmId), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to list Product Category", ae);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Product Category", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the ProductCategory list for a Realm
     *
     * @param realmId RealmId that you want the ProductCategory List from
     * @param productCategoryId ProductCategoryId that you want the Tree for
     * @param includeCurrentLevel true indicates that you want to include the
     * current node of the tree in the output, false will skip the current level
     * and give all the child nodes
     * @param includeAllChildren true will include all the child nodes in the
     * tree in the response, false will only include the immediate child nodes
     * @param auth
     * @return returns the complete list of productCategories
     */
    @GetMapping("/realmId/{realmId}/list/{productCategoryId}/{includeCurrentLevel}/{includeAllChildren}")
    @Operation(description = "API used to get the complete ProductCategory list for a Realm", summary = "Get ProductCategory list for Realm", tags = ("productCategory"))
    @Parameters(
            {
                @Parameter(name = "realmId", description = "RealmId that you want the ProductCategory list for"),
                @Parameter(name = "productCategoryId", description = "ProductCategoryId that you want the Tree for"),
                @Parameter(name = "includeCurrentLevel", description = "true indicates that you want to include the current node of the tree in the output, false will skip the current level and give all the child nodes"),
                @Parameter(name = "includeAllChildren", description = "true will include all the child nodes in the tree in the response, false will only include the immediate child nodes")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ProductCategory list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ProductCategory list")
    public ResponseEntity getProductCategoryByRealmId(@PathVariable(value = "realmId", required = true) int realmId, @PathVariable(value = "productCategoryId", required = true) int productCategoryId, @PathVariable(value = "includeCurrentLevel", required = false) Optional<Boolean> includeCurrentLevel, @PathVariable("includeAllChildren") Optional<Boolean> includeAllChildren, Authentication auth) {
        boolean bolIncludeCurrentLevel = true;
        boolean bolIncludeAllChildren = false;
        if (includeCurrentLevel.isPresent()) {
            bolIncludeCurrentLevel = includeCurrentLevel.get();
        }
        if (includeAllChildren.isPresent()) {
            bolIncludeAllChildren = includeAllChildren.get();
        }
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.productCategoryService.getProductCategoryList(curUser, realmId, productCategoryId, bolIncludeCurrentLevel, bolIncludeAllChildren), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Product Category", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the ProductCategory list for a Realm and Program
     *
     * @param realmId RealmId that you want the ProductCategory List from
     * @param programId ProgramId that you want the ProductCategory List from
     * @param auth
     * @return returns the complete list of productCategories
     */
    @GetMapping("/realmId/{realmId}/programId/{programId}")
    @Operation(description = "API used to get the complete ProductCategory list for a Realm and Program", summary = "Get ProductCategory list for Realm and Program", tags = ("productCategory"))
    @Parameters(
            {
                @Parameter(name = "realmId", description = "RealmId that you want the ProductCategory list for"),
                @Parameter(name = "programId", description = "ProgramId that you want the ProductCategory list for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ProductCategory list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ProductCategory list")
    public ResponseEntity getProductCategoryForProgram(@PathVariable(value = "realmId", required = true) int realmId, @PathVariable(value = "programId", required = true) int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.productCategoryService.getProductCategoryListForProgram(curUser, realmId, programId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Product Category", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @GetMapping(value = "/sync/productCategory/{lastSyncDate}")
//    public ResponseEntity getProductCategoryListForSync(@PathVariable("lastSyncDate") String lastSyncDate, Authentication auth) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.parse(lastSyncDate);
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.productCategoryService.getProductCategoryListForSync(lastSyncDate, curUser), HttpStatus.OK);
//        } catch (ParseException p) {
//            logger.error("Error while listing productCategory", p);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
//        } catch (Exception e) {
//            logger.error("Error while listing productCategory", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    /**
     * API used to update the ProductCategory tree
     *
     * @param productCategories Array of the ProductCategories that you want to
     * update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PutMapping(path = "/")
    @Operation(description = "API used to update the ProductCategory tree", summary = "Update ProductCategory tree", tags = ("productCategory"))
    @Parameters(
            @Parameter(name = "productCategories", description = "The Array of ProductCategories that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity updateProductCategory(@RequestBody Node<ProductCategory>[] productCategories, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.productCategoryService.saveProductCategoryList(productCategories, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update Product Category", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to update Product Category", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
