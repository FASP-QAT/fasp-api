/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.test;

import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author akil
 */
public class Java8Dates {

    public static void main(String[] args) {
        
        Period diff = Period.between(LocalDate.parse("2016-08-21"), LocalDate.parse("2018-01-01"));
        System.out.println(diff.getYears()*12+diff.getMonths());
    }

}
