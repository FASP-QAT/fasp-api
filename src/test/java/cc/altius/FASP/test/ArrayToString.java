/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.test;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author akil
 */
public class ArrayToString {

    public static void main(String[] args) {
        String[] arr = new String[]{};
        String opt = String.join("','", arr);
        if (arr.length > 0) {
            System.out.println("'" + opt + "'");
        } else {
            System.out.println(opt);
        }
    }

}
