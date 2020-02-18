/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.DataSourceType;
import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.service.DataSourceTypeService;
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
public class DataSourceTypeController {

    @Autowired
    private DataSourceTypeService dataSourceTypeService;

    @PutMapping(value = "/addDataSourceType")
    public int addDataSourceType(@RequestBody(required = true) String json) {
        Gson g = new Gson();
        DataSourceType dataSourceType = new DataSourceType();
        Label l = g.fromJson(json, Label.class);
        dataSourceType.setLabel(l);
        try {
            int insertedRow = this.dataSourceTypeService.addDataSourceType(dataSourceType);
            return insertedRow;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    @GetMapping(value = "/getDataSourceTypeList")
    public String getDataSourceTypeList() throws UnsupportedEncodingException {
        String json;
        List<DataSourceType> dataSourceTypeList = this.dataSourceTypeService.getDataSourceTypeList(false);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(dataSourceTypeList, typeList);
        return json;
    }
    @GetMapping(value = "/getDataSourceTypeListActive")
    public String getDataSourceTypeListActive() throws UnsupportedEncodingException {
        String json;
        List<DataSourceType> dataSourceTypeList = this.dataSourceTypeService.getDataSourceTypeList(true);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(dataSourceTypeList, typeList);
        return json;
    }

    @PutMapping(value = "/editDataSourceType")
    public ResponseEntity editDataSourceType(@RequestBody(required = true) String json) {
        
        Gson g = new Gson();
        DataSourceType dataSourceType = g.fromJson(json, DataSourceType.class);
        int updateRow=this.dataSourceTypeService.updateDataSourceType(dataSourceType);
        
        
        ResponseFormat responseFormat = new ResponseFormat();
        responseFormat.setStatus("Success");
        return new ResponseEntity(responseFormat, HttpStatus.OK);
    }
}
