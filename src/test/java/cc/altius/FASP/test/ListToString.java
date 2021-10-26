/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.test;

import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.SimpleObject;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class ListToString {

    public static void main(String[] args) {
        List<SimpleObject> lst = new LinkedList<>();
        lst.add(new SimpleObject(1, new Label(1)));
        lst.add(new SimpleObject(2, new Label(2)));
        lst.add(new SimpleObject(3, new Label(3)));
        lst.add(new SimpleObject(5, new Label(5)));
        StringBuilder sb = new StringBuilder();
        lst.forEach(l -> {
            sb.append(l.getId()).append(",");
        });
        if (sb.length()>0) {
            sb.setLength(sb.length()-1);
        }
        System.out.println(sb.toString());
        String s = String.join(",", lst.stream().map(l->l.getIdString()).toArray(String[]::new));
        System.out.println(s);
    }

}
