/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.utils.DateUtils;
import java.util.Comparator;
import java.util.Optional;

/**
 *
 * @author akil
 */
public class ComparatorBatchData implements Comparator<BatchData> {

    @Override
    public int compare(BatchData bd1, BatchData bd2) {
        if (bd1.getExpiryDate() == null && bd2.getExpiryDate() == null) {
            return Optional.ofNullable(bd1.getBatchId()).orElse(999999999) - Optional.ofNullable(bd2.getBatchId()).orElse(999999999);
        } else if (bd1.getExpiryDate() == null && bd2.getExpiryDate() != null) {
            return 1;
        } else if (bd1.getExpiryDate() != null && bd2.getExpiryDate() == null) {
            return -1;
        } else {
            int compare = DateUtils.compareDates(bd1.getExpiryDate(), bd2.getExpiryDate());
            if (compare != 0) {
                return compare;
            } else {
                return Optional.ofNullable(bd1.getBatchId()).orElse(999999999) - Optional.ofNullable(bd2.getBatchId()).orElse(999999999);
            }
        }

    }

}
