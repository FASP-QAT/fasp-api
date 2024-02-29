/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.utils.DateUtils;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author akil
 */
public class MasterSupplyPlan implements Serializable {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    /* introduced newBatchCounter in this class */
    private int programId;
    private int versionId;
    private int newBatchCounter;
    private List<NewSupplyPlan> nspList;
    private static final String REGION_FILE = "/home/akil/Desktop/region.txt";
    private static final String BATCH_FILE = "/home/akil/Desktop/batch.txt";

    public MasterSupplyPlan() {
        this.newBatchCounter = -1;
    }

    public MasterSupplyPlan(int programId, int versionId) {
        this.programId = programId;
        this.versionId = versionId;
        this.newBatchCounter = -1;
    }

    public int getProgramId() {
        return programId;
    }

    public int getVersionId() {
        return versionId;
    }

    public int getNewBatchCounter() {
        return newBatchCounter;
    }

    public void setNewBatchCounter(int newBatchCounter) {
        this.newBatchCounter = newBatchCounter;
    }

    public List<NewSupplyPlan> getNspList() {
        return nspList;
    }

    public void setNspList(List<NewSupplyPlan> nspList) {
        this.nspList = nspList;
    }

    private class NewSupplyComparator implements Comparator<NewSupplyPlan> {

        @Override
        public int compare(NewSupplyPlan t, NewSupplyPlan t1) {
            if (t.getTransDate() == null && t1.getTransDate() == null) {
                return 0;
            } else if (t.getTransDate() == null && t1.getTransDate() != null) {
                return 1;
            } else if (t.getTransDate() != null && t1.getTransDate() == null) {
                return -1;
            } else {
                return DateUtils.compareDates(t.getTransDate(), t1.getTransDate());
            }
        }
    }

    private NewSupplyPlan getPrevMonth(int planningUnitId, String transDate) {
        NewSupplyPlan before = new NewSupplyPlan(planningUnitId, transDate);
        return this.nspList.stream().filter(tp -> tp.getPlanningUnitId() == before.getPlanningUnitId() && DateUtils.compareDates(before.getTransDate(), tp.getTransDate()) >= 0).sorted(new NewSupplyComparator().reversed()).findFirst().orElse(null);
    }

    private void updateOpeningBalance(NewSupplyPlan nsp) throws ParseException {
        NewSupplyPlan prevNsp = getPrevMonth(nsp.getPlanningUnitId(), nsp.getPrevTransDate());
        logger.info("Prev NSP PU Id "+nsp.getPlanningUnitId()+ "Trans Date "+nsp.getPrevTransDate()+" Prev Nsp"+prevNsp);
        if (prevNsp == null) {
            nsp.setOpeningBalance(0);
            nsp.setOpeningBalanceWps(0);
        } else {
            logger.info("Prev Closing Balance "+prevNsp.getClosingBalance());
            nsp.setOpeningBalance(prevNsp.getClosingBalance());
            logger.info("NSP Opening Balance "+nsp.getOpeningBalance());
            nsp.setOpeningBalanceWps(prevNsp.getClosingBalanceWps());
            for (BatchData bd : prevNsp.getBatchDataList()) {
                BatchData newBd = new BatchData();
                newBd.setBatchId(bd.getBatchId());
                int idx2 = nsp.getBatchDataList().indexOf(newBd);
                if (idx2 == -1) {
                    newBd.setOpeningBalance(bd.getClosingBalance());
                    newBd.setOpeningBalanceWps(bd.getClosingBalanceWps());
                    newBd.setExpiryDate(bd.getExpiryDate());
                    newBd.setShelfLife(bd.getShelfLife());
                    nsp.getBatchDataList().add(newBd);
                } else {
                    newBd = nsp.getBatchDataList().get(idx2);
                    newBd.setOpeningBalance(bd.getClosingBalance());
                    newBd.setOpeningBalanceWps(bd.getClosingBalanceWps());
                }
            }
            nsp.getBatchDataList().sort(new ComparatorBatchData());
        }
    }

    public void printSupplyPlan() {
        StringBuilder regionString = new StringBuilder()
                .append("PlngUnt").append("\t")
                .append("TrnsDt").append("\t")
                .append("OB").append("\t")
                .append("OBW").append("\t")
                .append("Exp").append("\t")
                .append("ExpW").append("\t")
                .append("Shpt").append("\t")
                .append("ShptW").append("\t")
                .append("ActCon").append("\t")
                .append("FnlCon").append("\t")
                .append("Stock").append("\t")
                .append("UseAdj").append("\t")
                .append("FnlAdj").append("\t")
                .append("Exptd").append("\t")
                .append("ExptdW").append("\t")
                .append("NA").append("\t")
                .append("NAW").append("\t")
                .append("CB").append("\t")
                .append("CBW").append("\t")
                .append("Unmet").append("\t")
                .append("UnmetW").append("\r\n");
        StringBuilder batchString = new StringBuilder()
                .append("PlngUnt").append("\t")
                .append("TrnsDt").append("\t\t")
                .append("BtchId").append("\t\t")
                .append("ExpDt").append("\t")
                .append("OB").append("\t")
                .append("OBW").append("\t")
                .append("Exp").append("\t")
                .append("ExpW").append("\t")
                .append("Shpt").append("\t")
                .append("ShptW").append("\t")
                .append("UseAct").append("\t")
                .append("ActCon").append("\t")
                .append("AllReg").append("\t")
                .append("Stock").append("\t")
                .append("UseAdj").append("\t")
                .append("Adj").append("\t")
                .append("UnFE").append("\t")
                .append("CaFE").append("\t")
                .append("UnLE").append("\t")
                .append("CaLE").append("\t")
                .append("UnFEW").append("\t")
                .append("CaFEW").append("\t")
                .append("UnLEW").append("\t")
                .append("CaLEW").append("\t")
//                .append("UnalCW").append("\t")
//                .append("CaclCW").append("\t")
                .append("CB").append("\t")
                .append("CBW").append("\r\n");
        this.nspList.forEach(nsp -> {
            nsp.getBatchDataList().forEach(bd -> {
                batchString
                        .append(nsp.getPlanningUnitId()).append("\t")
                        .append(nsp.getTransDate()).append("\t")
                        .append(bd.getBatchId()).append("\t")
                        .append(bd.getExpiryDate()).append("\t")
                        .append(bd.getOpeningBalance()).append("\t")
                        .append(bd.getOpeningBalanceWps()).append("\t")
                        .append(bd.getExpiredStock()).append("\t")
                        .append(bd.getExpiredStockWps()).append("\t")
                        .append(bd.getShipment()).append("\t")
                        .append(bd.getShipmentWps()).append("\t")
                        .append(bd.isUseActualConsumption()).append("\t")
                        .append(bd.getActualConsumption()).append("\t")
                        .append(bd.isAllRegionsReportedStock()).append("\t")
                        .append(bd.getStock()).append("\t")
                        .append(bd.isUseAdjustment()).append("\t")
                        .append(bd.getAdjustment()).append("\t")
                        .append(bd.getUnallocatedFEFO()).append("\t")
                        .append(bd.getCalculatedFEFO()).append("\t")
                        .append(bd.getUnallocatedLEFO()).append("\t")
                        .append(bd.getCalculatedLEFO()).append("\t")
                        .append(bd.getUnallocatedFEFOWps()).append("\t")
                        .append(bd.getCalculatedFEFOWps()).append("\t")
                        .append(bd.getUnallocatedLEFOWps()).append("\t")
                        .append(bd.getCalculatedLEFOWps()).append("\t")
                        .append(bd.getClosingBalance()).append("\t")
                        .append(bd.getClosingBalanceWps()).append("\r\n");
            });
            regionString
                    .append(nsp.getPlanningUnitId()).append("\t")
                    .append(nsp.getTransDate()).append("\t")
                    .append(nsp.getOpeningBalance()).append("\t")
                    .append(nsp.getOpeningBalanceWps()).append("\t")
                    .append(nsp.getExpiredStock()).append("\t")
                    .append(nsp.getExpiredStockWps()).append("\t")
                    .append(nsp.getManualShipmentTotal() + nsp.getErpShipmentTotal()).append("\t")
                    .append(nsp.getManualShipmentTotalWps() + nsp.getErpShipmentTotalWps()).append("\t")
                    .append(nsp.isActualConsumptionFlag()).append("\t")
                    .append(nsp.getActualConsumptionQty()).append("\t")
                    .append(nsp.getStockQty()).append("\t")
                    .append(nsp.isUseAdjustment()).append("\t")
                    .append(nsp.getFinalAdjustmentQty()).append("\t")
                    .append(nsp.getExpectedStock()).append("\t")
                    .append(nsp.getExpectedStockWps()).append("\t")
                    .append(nsp.getNationalAdjustment()).append("\t")
                    .append(nsp.getNationalAdjustmentWps()).append("\t")
                    .append(nsp.getClosingBalance()).append("\t")
                    .append(nsp.getClosingBalanceWps()).append("\t")
                    .append(nsp.getUnmetDemand()).append("\t")
                    .append(nsp.getUnmetDemandWps()).append("\r\n");
        });
        FileWriter myWriter;
        try {
            myWriter = new FileWriter(REGION_FILE);
            myWriter.write(regionString.toString());
            myWriter.close();
            myWriter = new FileWriter(BATCH_FILE);
            myWriter.write(batchString.toString());
            myWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(MasterSupplyPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buildPlan() throws ParseException {
        for (NewSupplyPlan nsp : this.nspList) {
//            System.out.println(nsp.getTransDate() + " " + nsp.getPlanningUnitId());
            // For Regions
            updateOpeningBalance(nsp); // handles both All and WPS // Also copies Opening Balance and Batch info from the previous month
            // Shipments are already stored from the ResultSetExtractor
            nsp.updateExpiredStock(); // handles both All and WPS, updates the Batch wise Expired stock
            // Final Consumption is done from the ResultSetExtractor
            // Final Adjustment is done from the ResultSetExtractor
            nsp.updateExpectedStock(); // handles both All and WPS //?? Should we update this for Batches as well
            nsp.updateNationalAdjustment();  // handles both All and WPS
            nsp.updateClosingBalance();  // handles both All and WPS
            nsp.updateUnmetDemand();  // handles both All and WPS

            // Batches
            // Opening Balance is already set with Regions
            // Shipments are set through the ResultSetExtractor
            // Expired stock is already set with the Regions
            setNewBatchCounter(nsp.updateBatchData(this.newBatchCounter));   // handles both All and WPS
            nsp.removeUnusedBatches();   // handles both All and WPS
            nsp.getBatchDataList().forEach(bd -> {
                bd.setUseActualConsumption(nsp.isActualConsumptionFlag());
                bd.setUseAdjustment(nsp.isUseAdjustment());

            });
        }
    }

}

