/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 02-May-2021
 */

update rm_batch_info bi left join 
 (
       SELECT s1.BATCH_ID, bi.AUTO_GENERATED, MIN(s1.REC_DT) `MIN_RECV_DT`, DATE(bi.CREATED_DATE)`CREATED_DATE`, bi.EXPIRY_DATE, CONCAT(LEFT(DATE_ADD(MIN(s1.REC_DT), INTERVAL ppu.sHELF_LIFE MONTH),7), "-01") `CALC_EXP_DT` FROM (
       SELECT s.SHIPMENT_ID, st.SHIPMENT_TRANS_ID, COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) REC_DT, s.PROGRAM_ID, st.PLANNING_UNIT_ID, stbi.BATCH_ID
       FROM rm_shipment s
    LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID
       LEFT JOIN rm_shipment_trans_batch_info stbi ON st.SHIPMENT_TRANS_ID=stbi.SHIPMENT_TRANS_ID
       WHERE
           st.SHIPMENT_TRANS_ID IS NOT NULL
    GROUP BY s.SHIPMENT_ID) s1
       LEFT JOIN rm_batch_info bi on s1.BATCH_ID=bi.BATCH_ID
       LEFT JOIN rm_program_planning_unit ppu ON bi.PROGRAM_ID=ppu.PROGRAM_ID AND bi.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID
       GROUP BY s1.BATCH_ID) s2 ON s2.BATCH_ID=bi.BATCH_ID
       set bi.CREATED_DATE=s2.MIN_RECV_DT,bi.EXPIRY_DATE=s2.CALC_EXP_DT
       WHERE (s2.MIN_RECV_DT!=s2.`CREATED_DATE` OR s2.EXPIRY_DATE!=s2.CALC_EXP_DT) and s2.AUTO_GENERATED=1 and s2.CALC_EXP_DT is not null;


update rm_batch_info bi left join 
 (
       SELECT s1.BATCH_ID, bi.AUTO_GENERATED, MIN(s1.REC_DT) `MIN_RECV_DT`, DATE(bi.CREATED_DATE)`CREATED_DATE`, bi.EXPIRY_DATE, CONCAT(LEFT(DATE_ADD(MIN(s1.REC_DT), INTERVAL ppu.sHELF_LIFE MONTH),7), "-01") `CALC_EXP_DT` FROM (
       SELECT s.SHIPMENT_ID, st.SHIPMENT_TRANS_ID, COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) REC_DT, s.PROGRAM_ID, st.PLANNING_UNIT_ID, stbi.BATCH_ID
       FROM rm_shipment s
    LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID
       LEFT JOIN rm_shipment_trans_batch_info stbi ON st.SHIPMENT_TRANS_ID=stbi.SHIPMENT_TRANS_ID
       WHERE
           st.SHIPMENT_TRANS_ID IS NOT NULL
    GROUP BY s.SHIPMENT_ID) s1
       LEFT JOIN rm_batch_info bi on s1.BATCH_ID=bi.BATCH_ID
       LEFT JOIN rm_program_planning_unit ppu ON bi.PROGRAM_ID=ppu.PROGRAM_ID AND bi.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID
       GROUP BY s1.BATCH_ID) s2 ON s2.BATCH_ID=bi.BATCH_ID
       set bi.CREATED_DATE=s2.MIN_RECV_DT
       WHERE (s2.MIN_RECV_DT!=s2.`CREATED_DATE`) and s2.AUTO_GENERATED=0;