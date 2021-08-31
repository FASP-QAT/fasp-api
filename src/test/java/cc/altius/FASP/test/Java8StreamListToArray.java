/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.test;

import cc.altius.FASP.model.Country;
import cc.altius.FASP.model.HealthArea;
import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.RealmCountry;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class Java8StreamListToArray {

    public static void main(String[] args) {
        HealthArea ha = new HealthArea(1, new Label(1));
        List<RealmCountry> rcList = new LinkedList<>();
        rcList.add(new RealmCountry(1, new Country(1, "A", new Label(1))));
        rcList.add(new RealmCountry(2, new Country(2, "B", new Label(1))));
        rcList.add(new RealmCountry(3, new Country(3, "C", new Label(1))));
        rcList.add(new RealmCountry(4, new Country(4, "D", new Label(1))));
        ha.setRealmCountryArray(rcList.stream().map(rc -> Integer.toString(rc.getRealmCountryId())).toArray(String[]::new));
        System.out.println(ha);
    }
}
