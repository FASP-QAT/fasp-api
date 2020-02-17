/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.DataSource;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.service.DataSourceService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author palash
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4202")
public class DataSourceController {

    @Autowired
    private DataSourceService dataSourceService;

    @PutMapping(value = "/addDataSource")
    public ResponseEntity addDataSourceType(@RequestBody(required = true) String json) {
        //System.out.println("json---->" + json);
        Gson g = new Gson();
        DataSource dataSource = g.fromJson(json, DataSource.class);
        ResponseFormat responseFormat = new ResponseFormat();
        try {

            int row = this.dataSourceService.addDataSource(dataSource);
            if (row > 0) {
                responseFormat.setMessage("Data Source Added successfully");
                responseFormat.setStatus("Success");
                return new ResponseEntity(responseFormat, HttpStatus.OK);
            } else {
                responseFormat.setStatus("failed");
                responseFormat.setMessage("Error accured");
                return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);

            }

        } catch (Exception e) {
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/getDataSourceList")
    public String getDataSourceTypeList() throws UnsupportedEncodingException {
        String json;
        List<DataSource> dataSourceList = this.dataSourceService.getDataSourceList(false);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(dataSourceList, typeList);
        return json;
    }

    @PutMapping(value = "/editDataSource")
    public ResponseEntity editDataSource(@RequestBody(required = true) String json) {
        //System.out.println("----->" + json);
        Gson g = new Gson();
        DataSource dataSource = g.fromJson(json, DataSource.class);
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            int updateRow = this.dataSourceService.updateDataSource(dataSource);
            if (updateRow > 0) {
                responseFormat.setStatus("Success");
                responseFormat.setMessage("Data Source Updated successfully");
                return new ResponseEntity(responseFormat, HttpStatus.OK);
            } else {
                responseFormat.setStatus("Failed");
                responseFormat.setMessage("Updated failed");
                return new ResponseEntity(responseFormat, HttpStatus.OK);
            }
        } catch (Exception e) {
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
