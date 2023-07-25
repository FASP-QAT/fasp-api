/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author akil
 */
public class IntegerListToString {

    public static void main(String[] args) {
        List<String> iList = new LinkedList<>();
//        iList.add("1");
//        iList.add("2");
//        iList.add("3");
//        iList.add(4);
//        iList.add(5);
//        iList.add(6);
        System.out.println(iList);
        System.out.println(iList.stream().collect(Collectors.joining("','")));
    }

}
