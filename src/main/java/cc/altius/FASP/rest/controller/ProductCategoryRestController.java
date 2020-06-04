/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.BaseModel;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ExtendedProductCategory;
import cc.altius.FASP.model.ProductCategory;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.ProductCategoryService;
import cc.altius.utils.TreeUtils.Node;
import cc.altius.utils.TreeUtils.Tree;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
@RequestMapping("/api")
public class ProductCategoryRestController extends BaseModel implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductCategoryService productCategoryService;

    @PutMapping(path = "/productCategory")
    public ResponseEntity putProductCategory(@RequestBody Node<ProductCategory>[] productCategories, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
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

    @GetMapping("/productCategory/realmId/{realmId}")
    public ResponseEntity getProductCategory(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.productCategoryService.getProductCategoryListForRealm(curUser, realmId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Product Category", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/productCategory/realmId/{realmId}/list/{productCategoryId}/{includeCurrentLevel}/{includeAllChildren}")
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
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.productCategoryService.getProductCategoryList(curUser, realmId, productCategoryId, bolIncludeCurrentLevel, bolIncludeAllChildren), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Product Category", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/sync/productCategory/{lastSyncDate}")
    public ResponseEntity getProductCategoryListForSync(@PathVariable("lastSyncDate") String lastSyncDate, Authentication auth) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.parse(lastSyncDate);
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.productCategoryService.getProductCategoryListForSync(lastSyncDate, curUser), HttpStatus.OK);
        } catch (ParseException p) {
            logger.error("Error while listing productCategory", p);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
        } catch (Exception e) {
            logger.error("Error while listing productCategory", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/productCategory/realmId/{realmId}/programId/{programId}")
    public ResponseEntity getProductCategoryForProgram(@PathVariable(value = "realmId", required = true) int realmId, @PathVariable(value = "programId", required = true) int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.productCategoryService.getProductCategoryListForProgram(curUser, realmId, programId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Product Category", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
