/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.test;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class MatchingPatterns {

    public static void main(String[] args) {
        List<String> approvedList = new LinkedList<>();
        approvedList.add("/api/budget");
        approvedList.add("/api/budget/");
        approvedList.add("/api/budget/**");

        List<String> testList = new LinkedList<>();
        testList.add("/api/budget");
        testList.add("/api/budget/");
        testList.add("/api/budget/all");
        testList.add("/api/budget/2");
        testList.add("/api/budget/realmId/1");

        
        testList.forEach(testUrl -> {
            System.out.println("Going to check for URL -> " + testUrl);
            approvedList.forEach(url -> {
                String tmpUrl = url;
                if (tmpUrl.contains("**")) {
                    tmpUrl = tmpUrl.replace("**", "");
                    if (testUrl.startsWith(tmpUrl)) {
                        System.out.println("Match found -> " + url);
                    } else {
                        System.out.println("Did not match -> " + url);
                    }
                } else if (testUrl.equals(tmpUrl)) {
                    System.out.println("Match found -> " + url);
                } else {
                    System.out.println("Did not match -> " + url);
                }
            });
            System.out.println("");
            System.out.println("");
        });
    }

}
