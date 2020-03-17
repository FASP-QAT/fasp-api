/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.BaseModel;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProductCategory;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.service.ProductCategoryService;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io", "chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop"})
public class ProductCategoryRestController extends BaseModel implements Serializable {

    @Autowired
    private ProductCategoryService productCategoryService;

//    @PostMapping(path = "/productCategory")
//    public ResponseFormat postProductCategory(@RequestBody List<ProductCategory> productCategoryList, Authentication auth) {
//        try {
//            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
//            int productCategoryId = this.productCategoryService.addProductCategory(productCategoryList, curUser);
//            return new ResponseFormat("Successfully added ProductCategory with Id " + productCategoryId);
//        } catch (Exception e) {
//            return new ResponseFormat("Failed", e.getMessage());
//        }
//    }
    @PutMapping(path = "/productCategory")
    public ResponseFormat putProductCategory(@RequestBody List<ProductCategory> productCategoryList, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int rows = this.productCategoryService.saveProductCategoryList(productCategoryList, curUser);
            return new ResponseFormat("Successfully updated ProductCategory");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/productCategory")
    public ResponseFormat getProductCategory(Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseFormat("Success", "", this.productCategoryService.getProductCategoryList(curUser));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/productCategory/realmId/{realmId}/list/{productCategoryId}/{includeCurrentLevel}/{includeAllChildren}")
    public ResponseFormat getProductCategoryByRealmId(@PathVariable(value = "realmId", required = true) int realmId, @PathVariable(value = "productCategoryId", required = true) int productCategoryId, @PathVariable(value = "includeCurrentLevel", required = false) Optional<Boolean> includeCurrentLevel, @PathVariable("includeAllChildren") Optional<Boolean> includeAllChildren, Authentication auth) {
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
            return new ResponseFormat("Success", "", this.productCategoryService.getProductCategoryList(curUser, realmId, productCategoryId, bolIncludeCurrentLevel, bolIncludeAllChildren));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }
    
    @GetMapping("/productCategory/list/{productCategoryId}/{includeCurrentLevel}/{includeAllChildren}")
    public ResponseFormat getProductCategory(@PathVariable(value = "productCategoryId", required = true) int productCategoryId, @PathVariable(value = "includeCurrentLevel", required = false) Optional<Boolean> includeCurrentLevel, @PathVariable("includeAllChildren") Optional<Boolean> includeAllChildren, Authentication auth) {
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
            return new ResponseFormat("Success", "", this.productCategoryService.getProductCategoryList(curUser, productCategoryId, bolIncludeCurrentLevel, bolIncludeAllChildren));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/productCategory/{productCategoryId}")
    public ResponseFormat getProductCategory(@PathVariable("productCategoryId") int productCategoryId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseFormat("Success", "", this.productCategoryService.getProductCategoryById(productCategoryId, curUser));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

}
