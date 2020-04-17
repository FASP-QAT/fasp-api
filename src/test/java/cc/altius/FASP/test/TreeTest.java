/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.test;

import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.ProductCategory;
import cc.altius.utils.TreeUtils.Node;
import cc.altius.utils.TreeUtils.Tree;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author akil
 */
public class TreeTest {

    private List<Node> flatList = new LinkedList<>();
    private Node root;

    public TreeTest() {
        this.flatList = new LinkedList<>();
        flatList.add(new Node<>(1, null, new ProductCategory(4, new Label(0, "Root", null, null, null)), 4));
        flatList.add(new Node<>(2, 1, new ProductCategory(1, new Label(0, "CC 1: HIV / AIDS Pharmaceuticals", null, null, null)), 1));
        flatList.add(new Node<>(3, 2, new ProductCategory(2, new Label(0, "HIV/AIDS Pharmaceuticals", null, null, null)), 2));
        flatList.add(new Node<>(4, 1, new ProductCategory(5, new Label(0, "CC 2: Laboratory Commodities, VMMC", null, null, null)), 5));
        flatList.add(new Node<>(5, 4, new ProductCategory(3, new Label(0, "HIV Rapid Test Kits (RTKs", null, null, null)), 3));
        flatList.add(new Node<>(6, 4, new ProductCategory(6, new Label(0, "Laboratory Consumables", null, null, null)), 6));
        flatList.add(new Node<>(7, 4, new ProductCategory(9, new Label(0, "Laboratory Equipment", null, null, null)), 9));
        flatList.add(new Node<>(8, 4, new ProductCategory(7, new Label(0, "Laboratory Reagents", null, null, null)), 7));
        flatList.add(new Node<>(9, 4, new ProductCategory(10, new Label(0, "Voluntary Male Circumcision (VMMC – Kits)", null, null, null)), 10));
        flatList.add(new Node<>(10, 4, new ProductCategory(8, new Label(0, "Voluntary Male Circumcision (VMMC – Supplies)", null, null, null)), 8));
        flatList.add(new Node<>(11, 1, new ProductCategory(15, new Label(0, "CC 3: Malaria Pharmaceuticals", null, null, null)), 15));
        flatList.add(new Node<>(12, 11, new ProductCategory(11, new Label(0, "Malaria Pharmaceuticals", null, null, null)), 11));
        flatList.add(new Node<>(13, 1, new ProductCategory(13, new Label(0, "CC 4: LLINs, RDTs", null, null, null)), 13));
        flatList.add(new Node<>(14, 13, new ProductCategory(12, new Label(0, "Long Lasting Insecticide Treated Nets (LLINs)", null, null, null)), 12));
        flatList.add(new Node<>(15, 13, new ProductCategory(14, new Label(0, "Malaria Rapid Diagnostic Test (RDTs)", null, null, null)), 14));
        flatList.add(new Node<>(16, 1, new ProductCategory(34, new Label(0, "CC 5: Reproductive Health Pharmaceuticals, Devices", null, null, null)), 34));
        flatList.add(new Node<>(17, 16, new ProductCategory(23, new Label(0, "Contraceptive Implants", null, null, null)), 23));
        flatList.add(new Node<>(18, 16, new ProductCategory(20, new Label(0, "Injectable Contraceptives", null, null, null)), 20));
        flatList.add(new Node<>(19, 16, new ProductCategory(38, new Label(0, "Intrauterine Devices", null, null, null)), 38));
        flatList.add(new Node<>(20, 16, new ProductCategory(22, new Label(0, "Oral Contraceptives", null, null, null)), 22));
        flatList.add(new Node<>(21, 16, new ProductCategory(21, new Label(0, "Standard Days Method", null, null, null)), 21));
        flatList.add(new Node<>(22, 1, new ProductCategory(19, new Label(0, "CC 6: Male & Female Condoms, Lubricants", null, null, null)), 19));
        flatList.add(new Node<>(23, 22, new ProductCategory(37, new Label(0, "Female Condoms", null, null, null)), 37));
        flatList.add(new Node<>(24, 22, new ProductCategory(17, new Label(0, "Male Condoms", null, null, null)), 17));
        flatList.add(new Node<>(25, 22, new ProductCategory(18, new Label(0, "Personal Lubricants", null, null, null)), 18));
        flatList.add(new Node<>(26, 1, new ProductCategory(35, new Label(0, "CC 7: Essential Medicines", null, null, null)), 35));
        flatList.add(new Node<>(27, 26, new ProductCategory(36, new Label(0, "Essential Medicines", null, null, null)), 36));
        flatList.add(new Node<>(28, 26, new ProductCategory(28, new Label(0, "Nutritional Supplements", null, null, null)), 28));
        flatList.add(new Node<>(29, 1, new ProductCategory(16, new Label(0, "CC 8: Other Global Health Commodities", null, null, null)), 16));
        flatList.add(new Node<>(30, 29, new ProductCategory(29, new Label(0, "Medical Supplies", null, null, null)), 29));
        flatList.add(new Node<>(31, 29, new ProductCategory(25, new Label(0, "Other Global Health Commodities", null, null, null)), 25));
        flatList.add(new Node<>(32, 1, new ProductCategory(24, new Label(0, "CC 9: Infrastructure, Office Supplies, IT", null, null, null)), 24));
        flatList.add(new Node<>(33, 32, new ProductCategory(33, new Label(0, "IT Equipment", null, null, null)), 33));
        flatList.add(new Node<>(34, 32, new ProductCategory(26, new Label(0, "Modular Warehouse/Laboratory/Clinic", null, null, null)), 26));
        flatList.add(new Node<>(35, 32, new ProductCategory(32, new Label(0, "Office Equipment", null, null, null)), 32));
        flatList.add(new Node<>(36, 32, new ProductCategory(31, new Label(0, "Others", null, null, null)), 31));
        flatList.add(new Node<>(37, 32, new ProductCategory(30, new Label(0, "Vehicles", null, null, null)), 30));
        flatList.add(new Node<>(38, 32, new ProductCategory(27, new Label(0, "Warehouse Equipment", null, null, null)), 27));
        
//        flatList.add(new Node<>(4, null, new ProductCategory(4, new Label(0, "Root", null, null, null)), 4));
//        flatList.add(new Node<>(1, 4, new ProductCategory(1, new Label(0, "CC 1: HIV / AIDS Pharmaceuticals", null, null, null)), 1));
//        flatList.add(new Node<>(2, 1, new ProductCategory(2, new Label(0, "HIV/AIDS Pharmaceuticals", null, null, null)), 2));
//        flatList.add(new Node<>(5, 4, new ProductCategory(5, new Label(0, "CC 2: Laboratory Commodities, VMMC", null, null, null)), 5));
//        flatList.add(new Node<>(3, 5, new ProductCategory(3, new Label(0, "HIV Rapid Test Kits (RTKs", null, null, null)), 3));
//        flatList.add(new Node<>(6, 5, new ProductCategory(6, new Label(0, "Laboratory Consumables", null, null, null)), 6));
//        flatList.add(new Node<>(9, 5, new ProductCategory(9, new Label(0, "Laboratory Equipment", null, null, null)), 9));
//        flatList.add(new Node<>(7, 5, new ProductCategory(7, new Label(0, "Laboratory Reagents", null, null, null)), 7));
//        flatList.add(new Node<>(10, 5, new ProductCategory(10, new Label(0, "Voluntary Male Circumcision (VMMC – Kits)", null, null, null)), 10));
//        flatList.add(new Node<>(8, 5, new ProductCategory(8, new Label(0, "Voluntary Male Circumcision (VMMC – Supplies)", null, null, null)), 8));
//        flatList.add(new Node<>(15, 4, new ProductCategory(15, new Label(0, "CC 3: Malaria Pharmaceuticals", null, null, null)), 15));
//        flatList.add(new Node<>(11, 15, new ProductCategory(11, new Label(0, "Malaria Pharmaceuticals", null, null, null)), 11));
//        flatList.add(new Node<>(13, 4, new ProductCategory(13, new Label(0, "CC 4: LLINs, RDTs", null, null, null)), 13));
//        flatList.add(new Node<>(12, 13, new ProductCategory(12, new Label(0, "Long Lasting Insecticide Treated Nets (LLINs)", null, null, null)), 12));
//        flatList.add(new Node<>(14, 13, new ProductCategory(14, new Label(0, "Malaria Rapid Diagnostic Test (RDTs)", null, null, null)), 14));
//        flatList.add(new Node<>(34, 4, new ProductCategory(34, new Label(0, "CC 5: Reproductive Health Pharmaceuticals, Devices", null, null, null)), 34));
//        flatList.add(new Node<>(23, 34, new ProductCategory(23, new Label(0, "Contraceptive Implants", null, null, null)), 23));
//        flatList.add(new Node<>(20, 34, new ProductCategory(20, new Label(0, "Injectable Contraceptives", null, null, null)), 20));
//        flatList.add(new Node<>(38, 34, new ProductCategory(38, new Label(0, "Intrauterine Devices", null, null, null)), 38));
//        flatList.add(new Node<>(22, 34, new ProductCategory(22, new Label(0, "Oral Contraceptives", null, null, null)), 22));
//        flatList.add(new Node<>(21, 34, new ProductCategory(21, new Label(0, "Standard Days Method", null, null, null)), 21));
//        flatList.add(new Node<>(19, 4, new ProductCategory(19, new Label(0, "CC 6: Male & Female Condoms, Lubricants", null, null, null)), 19));
//        flatList.add(new Node<>(37, 19, new ProductCategory(37, new Label(0, "Female Condoms", null, null, null)), 37));
//        flatList.add(new Node<>(17, 19, new ProductCategory(17, new Label(0, "Male Condoms", null, null, null)), 17));
//        flatList.add(new Node<>(18, 19, new ProductCategory(18, new Label(0, "Personal Lubricants", null, null, null)), 18));
//        flatList.add(new Node<>(35, 4, new ProductCategory(35, new Label(0, "CC 7: Essential Medicines", null, null, null)), 35));
//        flatList.add(new Node<>(36, 35, new ProductCategory(36, new Label(0, "Essential Medicines", null, null, null)), 36));
//        flatList.add(new Node<>(28, 35, new ProductCategory(28, new Label(0, "Nutritional Supplements", null, null, null)), 28));
//        flatList.add(new Node<>(16, 4, new ProductCategory(16, new Label(0, "CC 8: Other Global Health Commodities", null, null, null)), 16));
//        flatList.add(new Node<>(29, 16, new ProductCategory(29, new Label(0, "Medical Supplies", null, null, null)), 29));
//        flatList.add(new Node<>(25, 16, new ProductCategory(25, new Label(0, "Other Global Health Commodities", null, null, null)), 25));
//        flatList.add(new Node<>(24, 4, new ProductCategory(24, new Label(0, "CC 9: Infrastructure, Office Supplies, IT", null, null, null)), 24));
//        flatList.add(new Node<>(33, 24, new ProductCategory(33, new Label(0, "IT Equipment", null, null, null)), 33));
//        flatList.add(new Node<>(26, 24, new ProductCategory(26, new Label(0, "Modular Warehouse/Laboratory/Clinic", null, null, null)), 26));
//        flatList.add(new Node<>(32, 24, new ProductCategory(32, new Label(0, "Office Equipment", null, null, null)), 32));
//        flatList.add(new Node<>(31, 24, new ProductCategory(31, new Label(0, "Others", null, null, null)), 31));
//        flatList.add(new Node<>(30, 24, new ProductCategory(30, new Label(0, "Vehicles", null, null, null)), 30));
//        flatList.add(new Node<>(27, 24, new ProductCategory(27, new Label(0, "Warehouse Equipment", null, null, null)), 27));

    }

    public static void main(String[] args) {
        try {
            new TreeTest().start();
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(TreeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void start() throws Exception {
        Tree<ProductCategory> t = null;
        boolean isFirst = true;
        for (Node<ProductCategory> n : this.flatList) {
            if (isFirst) {
                t = new Tree(n);
            } else {
                t.addNode(n);
            }
            isFirst = false;
        }
        System.out.println("");
        System.out.println("");
        t.getTreeList().forEach((node) -> {
            System.out.println(pad(node.getLevel() * 4) + node);
        });

        System.out.println("");
        System.out.println("");
        Node n = t.findNodeByPayloadId(35);
        
        System.out.println(n);
        System.out.println("");
        System.out.println("");
        t.getPayloadSubList(26).forEach((pc) -> {
            System.out.println(pc);
        });
        t.getTreeSubList(26).forEach((node) -> {
            System.out.println(pad(node.getLevel() * 4) + node);
        });

    }

    private String pad(int count) {
        String tmpString = "";
        for (int x = 0; x < count; x++) {
            tmpString += " ";
        }
        return tmpString;
    }

}
