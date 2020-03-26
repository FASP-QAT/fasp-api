/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgProductDTO;
import cc.altius.FASP.model.Product;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.ProductService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io", "chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop"})
public class ProductRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductService productService;

    @PostMapping(path = "/product")
    public ResponseEntity postProduct(@RequestBody Product product, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            this.productService.addProduct(product, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Product", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Error while trying to add Product", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/product")
    public ResponseEntity putProduct(@RequestBody Product product, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            this.productService.updateProduct(product, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update Product", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Error while trying to update Product", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product")
    public ResponseEntity getProduct(Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.productService.getProductList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Product", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/realm/{realmId}")
    public ResponseEntity getProductForRealm(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.productService.getProductList(realmId, true, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Product", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Error while trying to list Product", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity getProductById(@PathVariable("productId") int productId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.productService.getProductById(productId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to list Product", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list Product", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getProductListForSync")
    public String getProductListForSync(@RequestParam String lastSyncDate, int realmId) throws UnsupportedEncodingException {
        String json;
        List<PrgProductDTO> productList = this.productService.getProductListForSync(lastSyncDate, realmId);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(productList, typeList);
        return json;
    }
}
