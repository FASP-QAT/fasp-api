/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author akil
 */
public class ArrayToString {

    public static void main(String[] args) {
        String[] arr = new String[]{"Akil", "Qasim"};
        String opt = String.join("','", arr);
        if (arr.length > 0) {
            System.out.println("'" + opt + "'");
        } else {
            System.out.println(opt);
        }

        Integer[] arrI = new Integer[]{};
        System.out.println(Arrays.toString(arrI));
        List<Integer> programIdList = Arrays.asList(arrI);
        System.out.println("3rd");
        System.out.println(programIdList.stream()
                .map(n -> String.valueOf(n))
                .collect(Collectors.joining(","
                )));
    }

}
