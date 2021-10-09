/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.test;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class ListToString {

    public static void main(String[] args) {
        List<Integer> lst = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        lst.forEach(l -> {
            sb.append(l).append(",");
        });
        if (sb.length()>0) {
            sb.setLength(sb.length()-1);
        }
        System.out.println(sb.toString());
    }

}
