/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.test;

import cc.altius.FASP.utils.SuggestedDisplayName;
import cc.altius.utils.PassPhrase;

/**
 *
 * @author akil
 */
public class RealmCountrySkuCode {
    public static void main(String[] args) {
        String n = "Copper TCu380A Intrauterine Device, 1 Each";
        System.out.println(SuggestedDisplayName.getAlphaNumericString(n, SuggestedDisplayName.REALM_COUNTRY_PLANNING_UNIT_LENGTH));
        System.out.println(PassPhrase.getPassword(4).toUpperCase());
    }
    
}
