/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgProgramDataDTO;
import cc.altius.FASP.model.DTO.ProgramDTO;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.ProgramProduct;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.service.ProgramDataService;
import cc.altius.FASP.service.ProgramService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProgramRestController {

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
    public String getProgramList(Authentication auth) throws UnsupportedEncodingException {
        CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
        String json;
        List<ProgramDTO> programList = this.programService.getProgramListForDropdown(curUser);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(programList, typeList);
        return json;
    }

    @PostMapping(path = "/program")
    public ResponseFormat postProgram(@RequestBody Program program, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int programId = this.programService.addProgram(program, curUser);
            return new ResponseFormat("Successfully added Program with Id " + programId);
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @PutMapping(path = "/program")
    public ResponseFormat putProgram(@RequestBody Program program, Authentication auth) {
        try {
            CustomUserDetails curUser = ((CustomUserDetails) auth.getPrincipal());
            this.programService.updateProgram(program, curUser);
            return new ResponseFormat("Successfully updated Program");
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/program")
    public ResponseFormat getProgram(Authentication auth) {
        try {
            CustomUserDetails curUser = ((CustomUserDetails) auth.getPrincipal());
            return new ResponseFormat("Success", "", this.programService.getProgramList(curUser));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/programProduct/{programId}")
    public ResponseFormat getProgramProductForProgram(@PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = ((CustomUserDetails) auth.getPrincipal());
            return new ResponseFormat("Success", "", this.programService.getProgramProductListForProgramId(programId, curUser));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }
    
    @PutMapping("/programProduct")
    public ResponseFormat saveProgramProductForProgram(@RequestBody ProgramProduct pp, Authentication auth) {
        try {
            CustomUserDetails curUser = ((CustomUserDetails) auth.getPrincipal());
            this.programService.saveProgramProduct(pp, curUser);
            return new ResponseFormat("Successfully update Program Product list");
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/program/realmId/{realmId}")
    public ResponseFormat getProgramForRealm(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = ((CustomUserDetails) auth.getPrincipal());
            return new ResponseFormat("Success", "", this.programService.getProgramList(realmId, curUser));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/program/{programId}")
    public ResponseFormat getProgram(@PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = ((CustomUserDetails) auth.getPrincipal());
            return new ResponseFormat("Success", "", this.programService.getProgramById(programId, curUser));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }
}
