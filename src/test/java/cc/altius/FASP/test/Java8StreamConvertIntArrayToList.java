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
public class Java8StreamConvertIntArrayToList {

    public static void main(String[] args) {
        int[] arr = new int[]{5, 7, 2, 12, 100};
        Arrays.stream(arr).forEach(i -> {
            System.out.println(i);
        });

    }

}
