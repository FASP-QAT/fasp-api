///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package cc.altius.FASP.web.controller;
//
//import cc.altius.FASP.model.NextShipmentStatusAllowed;
//import cc.altius.FASP.model.ResponseFormat;
//import cc.altius.FASP.model.ShipmentStatus;
//import cc.altius.FASP.service.ShipmentStatusService;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import java.io.UnsupportedEncodingException;
//import java.lang.reflect.Type;
//import java.util.Arrays;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// *
// * @author palash
// */
//@RestController
//@RequestMapping("/api")
//public class ShipmentStatusController {
//
//    @Autowired
//    private ShipmentStatusService shipmentStatusService;
//
//    @PutMapping(value = "/addShipmentStatus")
//    public ResponseEntity addShipmentStatus(@RequestBody(required = true) String json) {
//        //System.out.println("json---->" + json);
//        Gson g = new Gson();
//        ShipmentStatus shipmentStatus = g.fromJson(json, ShipmentStatus.class);
//        System.out.println("shipmentSTatus---------->"+Arrays.toString(shipmentStatus.getNextShipmentStatusAllowed()));
//        ResponseFormat responseFormat = new ResponseFormat();
//        try {
//
//            int row = this.shipmentStatusService.addShipmentStatus(shipmentStatus);
//            if (row > 0) {
//                responseFormat.setMessage("ShipmentStatus Added successfully");
//                responseFormat.setStatus("Success");
//                return new ResponseEntity(responseFormat, HttpStatus.OK);
//            } else {
//                responseFormat.setStatus("failed");
//                responseFormat.setMessage("Error accured");
//                return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
//
//            }
//
//        } catch (Exception e) {
//            responseFormat.setStatus("failed");
//            responseFormat.setMessage("Exception Occured :" + e.getClass());
//            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }
//
//    @GetMapping(value = "/getShipmentStatusListAll")
//    public String getShipmentStatusListAll() throws UnsupportedEncodingException {
//        String json = null;
//
//        try {
//            List<ShipmentStatus> shipmentStatusList = this.shipmentStatusService.getShipmentStatusList(false);
//           
//            for (ShipmentStatus ss : shipmentStatusList) {
//                if (ss.getNextShipmentStatusAllowedList().size() > 0) {
////                    System.out.println("in if--------->");
////                    System.out.println("in if status--->"+ss.getNextShipmentStatusAllowedList());
//                    String[] shipmentStatusId = new String[ss.getNextShipmentStatusAllowedList().size()];
//                    int i = 0;
//                    for (NextShipmentStatusAllowed b : ss.getNextShipmentStatusAllowedList()) {
//                        shipmentStatusId[i] = String.valueOf(b.getShipmentStatusAllowedId());
//                        i++;
//                    }
////                    System.out.println("ids------>"+Arrays.toString(shipmentStatusId));
//                    ss.setNextShipmentStatusAllowed(shipmentStatusId);
//
//                }else{
//                ss.setNextShipmentStatusAllowed( new String [0]);
//                }
//            }
//            
//            //System.out.println("shipmentStatusList--->"+shipmentStatusList);
//            Gson gson = new Gson();
//            Type typeList = new TypeToken<List>() {
//            }.getType();
//            json = gson.toJson(shipmentStatusList, typeList);
//
//        } catch (Exception e) {
//        }
//        return json;
//    }
//
//    @GetMapping(value = "/getShipmentStatusListActive")
//    public String getShipmentStatusListActive() throws UnsupportedEncodingException {
//        String json;
//        List<ShipmentStatus> shipmentStatusList = this.shipmentStatusService.getShipmentStatusList(true);
//        Gson gson = new Gson();
//        Type typeList = new TypeToken<List>() {
//        }.getType();
//        json = gson.toJson(shipmentStatusList, typeList);
//        return json;
//    }
//
//    @PutMapping(value = "/editShipmentStatus")
//    public ResponseEntity editShipmentStatus(@RequestBody(required = true) String json) {
//        //System.out.println("json---->" + json);
//        Gson g = new Gson();
//        ShipmentStatus shipmentStatus = g.fromJson(json, ShipmentStatus.class);
//        ResponseFormat responseFormat = new ResponseFormat();
//        System.out.println("shipmentSTatsus-->"+shipmentStatus);
//        try {
//
//            int row = this.shipmentStatusService.editShipmentStatus(shipmentStatus);
//            if (row > 0) {
//                responseFormat.setMessage("ShipmentStatus Update successfully");
//                responseFormat.setStatus("Success");
//                return new ResponseEntity(responseFormat, HttpStatus.OK);
//            } else {
//                responseFormat.setStatus("failed");
//                responseFormat.setMessage("Error accured");
//                return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            responseFormat.setStatus("failed");
//            responseFormat.setMessage("Exception Occured :" + e.getClass());
//            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }
//    
////    @GetMapping(value = "/getShipmentStatusListForSync")
////    public String getShipmentStatusListForSync(@RequestParam String lastSyncDate) throws UnsupportedEncodingException {
////        String json;
////        List<PrgShipmentStatusDTO> shipmentStatusList = this.shipmentStatusService.getShipmentStatusListForSync(lastSyncDate);
////        Gson gson = new Gson();
////        Type typeList = new TypeToken<List>() {
////        }.getType();
////        json = gson.toJson(shipmentStatusList, typeList);
////        return json;
////    }
//
//    
////    @GetMapping(value = "/getShipmentStatusAllowedListForSync")
////    public String getShipmentStatusAllowedListForSync(@RequestParam String lastSyncDate) throws UnsupportedEncodingException {
////        String json;
////        List<PrgShipmentStatusAllowedDTO> shipmentStatusAllowedList = this.shipmentStatusService.getShipmentStatusAllowedListForSync(lastSyncDate);
////        Gson gson = new Gson();
////        Type typeList = new TypeToken<List>() {
////        }.getType();
////        json = gson.toJson(shipmentStatusAllowedList, typeList);
////        return json;
////    }
//
//
//}
