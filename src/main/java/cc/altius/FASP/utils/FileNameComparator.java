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
        if (n1 == -1 || n2 == -1) {
            return o1.getName().compareTo(o2.getName());
        } else {
            if (n1 > n2) {
                return 1;
            } else if (n2 > n1) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private long extractNumber(String name) {
        long i = 0;
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < name.length(); x++) {
            if (name.charAt(x) >= '0' && name.charAt(x) <= '9') {
                sb.append(name.charAt(x));
            }
        }
        if (sb.length() > 0) {
            return Long.parseLong(sb.toString());
        } else {
            return -1;
        }
    }
}
