/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.utils;

/**
 *
 * @author akil
 */
public class SuggestedDisplayName {

    public static final int FUNDING_SOURCE_LENGTH = 5;
    public static final int HEALTH_AREA_LENGTH = 4;
    public static final int PROCUREMENT_AGENT_LENGTH = 8;
    public static final int ORGANISATION_LENGTH = 2;
    public static final int REALM_COUNTRY_PLANNING_UNIT_LENGTH = 12;

    public static final String getAlphaNumericString(String name, int length) {
        StringBuilder finalString = new StringBuilder();
        for (int x = 0; x < length && x < name.length(); x++) {
            if ((name.charAt(x) >= 'A' && name.charAt(x) <= 'Z')
                    || (name.charAt(x) >= 'a' && name.charAt(x) <= 'z')
                    || (name.charAt(x) >= '0' && name.charAt(x) <= '9')) {
                finalString.append(name.charAt(x));
            }
        }
        return finalString.toString().toUpperCase();
    }

    public static final String getFinalDisplayName(String name, int cnt) {
        return name + String.format("%02d", cnt);
    }

}
