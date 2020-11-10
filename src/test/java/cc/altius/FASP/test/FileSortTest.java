/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.test;

import cc.altius.FASP.utils.FileNameComparator;
import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import org.apache.commons.io.filefilter.WildcardFileFilter;

/**
 *
 * @author akil
 */
public class FileSortTest {

    public static void main(String[] args) {
        File dir = new File("/home/ubuntu/QAT/ARTMIS/");
        FileFilter fileFilter = new WildcardFileFilter("item_data*.xml");
        File[] files = dir.listFiles(fileFilter);
        Arrays.sort(files, new FileNameComparator());
        for (File f : files) {
            System.out.println(f.getName());
        }
    }
}
