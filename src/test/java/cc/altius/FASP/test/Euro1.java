/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.test;

/**
 *
 * @author akil
 */
public class Euro1 {

    public static void main(String[] args) {
        String unitsPerPallet = "EUR1-15|EUR2-21";
        String[] noOfPallets = unitsPerPallet.split("\\|");
        String euro1 = (noOfPallets[0].split("-")[1] != null && noOfPallets[0].split("-")[1] != "" ? noOfPallets[0].split("-")[1] : "0");
        String euro2 = (noOfPallets[1].split("-")[1] != null && noOfPallets[1].split("-")[1] != "" ? noOfPallets[1].split("-")[1] : "0");
        System.out.println("euro1="+euro1);
        System.out.println("euro2="+euro2);
    }
}
