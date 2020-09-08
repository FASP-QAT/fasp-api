/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.test;

import cc.altius.FASP.model.BatchData;
import cc.altius.FASP.model.ComparatorBatchData;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class BatchSort {

    public static void main(String[] args) throws ParseException {
        List<BatchData> bdList = new LinkedList<>();
        bdList.add(new BatchData(4, "2019-12-01"));
        bdList.add(new BatchData(3, "2020-04-01"));
        bdList.add(new BatchData(6, "2019-08-01"));
        bdList.add(new BatchData(7, "2019-07-01"));
        bdList.add(new BatchData(2, "2019-08-01"));
        bdList.add(new BatchData(null, "2019-08-01"));
        bdList.add(new BatchData(9, null));
        bdList.add(new BatchData(8, null));
        System.out.println("Before sorting");
        bdList.forEach(bd -> {
            System.out.println(bd);
        });
        bdList.sort(new ComparatorBatchData());
        System.out.println("");
        System.out.println("");
        System.out.println("After sorting");
        bdList.forEach(bd -> {
            System.out.println(bd);
        });
    }

}
