/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.test;

import cc.altius.utils.DateUtils;
import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class PrevMonth {

    public static void main(String[] args) {
        new PrevMonth().build();

    }

    public void build() {
        List<TestPlan> tpList = new LinkedList<>();
        tpList.add(new TestPlan("2019-06-01"));
        tpList.add(new TestPlan("2019-08-01"));
        tpList.add(new TestPlan("2020-09-01"));
        tpList.add(new TestPlan("2018-04-01"));
        tpList.add(new TestPlan("2019-02-01"));
        tpList.forEach(tp -> {
            System.out.println(tp);
        });
        tpList.sort(new TestPlanComparator());
        System.out.println("After Sorting");
        tpList.forEach(tp -> {
            System.out.println(tp);
        });
        TestPlan before = new TestPlan("2018-06-01");
        System.out.println("Before " + before);
        System.out.println(tpList.stream().filter(tp -> DateUtils.compareDates(before.getDt(), tp.getDt()) > 0).sorted(new TestPlanComparator().reversed()).findFirst().orElse(null));
    }

    private class TestPlan implements Serializable {

        private String dt;

        public TestPlan(String dt) {
            this.dt = dt;
        }

        public String getDt() {
            return dt;
        }

        @Override
        public String toString() {
            return dt;
        }

    }

    private class TestPlanComparator implements Comparator<TestPlan> {

        @Override
        public int compare(TestPlan t, TestPlan t1) {
            if (t.getDt() == null && t1.getDt() == null) {
                return 0;
            } else if (t.getDt() == null && t1.getDt() != null) {
                return 1;
            } else if (t.getDt() != null && t1.getDt() == null) {
                return -1;
            } else {
                return DateUtils.compareDates(t.getDt(), t1.getDt());
            }
        }
    }
}
