/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO;

import java.util.Comparator;

/**
 *
 * @author akil
 */
public class ForecastMethodOptimizationDTOComparator implements Comparator<ForecastMethodOptimizationDTO> {

    @Override
    public int compare(ForecastMethodOptimizationDTO t, ForecastMethodOptimizationDTO t1) {
        if (t.getError() > t1.getError()) {
            return 1;
        } else if (t.getError() < t1.getError()) {
            return -1;
        } else {
            if (t.getVar1() > t1.getVar1()) {
                return 1;
            } else if (t.getVar1() < t1.getVar1()) {
                return -1;
            } else {
                if (t.getVar2() > t1.getVar2()) {
                    return 1;
                } else if (t.getVar2() < t1.getVar2()) {
                    return -1;
                } else {
                    if (t.getVar3() > t1.getVar3()) {
                        return 1;
                    } else if (t.getVar3() < t1.getVar3()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }
        }
    }
}
