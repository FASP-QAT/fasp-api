/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.DTO.PrgProgramDataDTO;
import cc.altius.FASP.model.DTO.ProgramDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cc.altius.FASP.service.ProgramDataService;
import cc.altius.FASP.service.ProgramService;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4202")
public class ProgramController {

    @Autowired
    private ProgramDataService programDataService;
    @Autowired
    private ProgramService programService;

    @GetMapping(value = "/getProgramData")
    public String getProgramData(@RequestParam String programId) throws UnsupportedEncodingException {
        String json;
        List<PrgProgramDataDTO> programList = this.programDataService.getProgramData(programId);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(programList, typeList);
        return json;
    }

    @GetMapping(value = "/getProgramList")
    public String getProgramList() throws UnsupportedEncodingException {
        System.out.println("in get Program kist");
        String json;
        List<ProgramDTO> programList = this.programService.getProgramList();
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(programList, typeList);
        return json;
    }
}
