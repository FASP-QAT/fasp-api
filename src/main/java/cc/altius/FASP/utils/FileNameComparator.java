/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.utils;

import java.io.File;
import java.util.Comparator;

/**
 *
 * @author akil
 */
public class FileNameComparator implements Comparator<File> {

    @Override
    public int compare(File o1, File o2) {
        long n1 = extractNumber(o1.getName());
        long n2 = extractNumber(o2.getName());
        if (n1 > n2) {
            return 1;
        } else if (n1 == n2) {
            return 0;
        } else {
            return -1;
        }
//        return n1 - n2;
    }

    private long extractNumber(String name) {
        long i = 0;
        try {
            int s = name.lastIndexOf('_') + 1;
            int e = name.lastIndexOf('.');
            String number = name.substring(s, e);
            i = Long.parseLong(number);
        } catch (Exception e) {
            i = 0; // if filename does not match the format
            // then default to 0
        }
        return i;
    }
}
