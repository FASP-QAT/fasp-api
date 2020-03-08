/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

/**
 *
 * @author altius
 */

/* 
############################################
Use the RealmRestController instead
############################################
*/
//@RestController
//@RequestMapping("/api")
//@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io"})
//public class RealmController {

//    @Autowired
//    private RealmService realmService;

//    @GetMapping(value = "/getRealmList")
//    public String getRealmList() throws UnsupportedEncodingException {
//        String json = null;
//        try {
//            List<Realm> realmList = this.realmService.getRealmList(true);
//            Gson gson = new Gson();
//            Type typeList = new TypeToken<List>() {
//            }.getType();
//            json = gson.toJson(realmList, typeList);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return json;
//    }
//
//    @GetMapping(value = "/getRealmCountryList")
//    public String getRealmCountryList() throws UnsupportedEncodingException {
//        String json = null;
//        try {
//            List<RealmCountry> realmCountryList = this.realmService.getRealmCountryList(true);
//            Gson gson = new Gson();
//            Type typeList = new TypeToken<List>() {
//            }.getType();
//            json = gson.toJson(realmCountryList, typeList);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return json;
//    }
//
//    @GetMapping(value = "/getRealmCountryListByRealmId/{realmId}")
//    public String getRealmCountryListByRealmId(@PathVariable("realmId") int realmId) throws UnsupportedEncodingException {
//        String json = null;
//        try {
//            List<RealmCountry> realmCountryList = this.realmService.getRealmCountryListByRealmId(realmId);
//            Gson gson = new Gson();
//            Type typeList = new TypeToken<List>() {
//            }.getType();
//            json = gson.toJson(realmCountryList, typeList);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return json;
//    }
//}
