/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author akil
 */
public class MasterSupplyPlan implements Serializable {

    private int programId;
    private int versionId;
    private List<NewSupplyPlan> nspList;
    private static final String REGION_FILE = "/home/akil/Desktop/region.txt";
    private static final String BATCH_FILE = "/home/akil/Desktop/batch.txt";

    public MasterSupplyPlan() {
    }

    public MasterSupplyPlan(int programId, int versionId) {
        this.programId = programId;
        this.versionId = versionId;
    }

    public int getProgramId() {
        return programId;
    }

    public int getVersionId() {
        return versionId;
    }

    public List<NewSupplyPlan> getNspList() {
        return nspList;
    }

    public void setNspList(List<NewSupplyPlan> nspList) {
        this.nspList = nspList;
    }

    private void updateOpeningBalance(NewSupplyPlan nsp) throws ParseException {
        NewSupplyPlan prevNsp = new NewSupplyPlan(nsp.getPlanningUnitId(), nsp.getPrevTransDate());
        int idx1 = this.nspList.indexOf(prevNsp);
        if (idx1 == -1) {
            nsp.setOpeningBalance(0);
        } else {
            prevNsp = this.nspList.get(idx1);
            nsp.setOpeningBalance(prevNsp.getClosingBalance());
            for (BatchData bd : prevNsp.getBatchDataList()) {
                BatchData newBd = new BatchData();
                newBd.setBatchId(bd.getBatchId());
                int idx2 = nsp.getBatchDataList().indexOf(newBd);
                if (idx2 == -1) {
                    newBd.setOpeningBalance(bd.getClosingBalance());
                    newBd.setExpiryDate(bd.getExpiryDate());
                    newBd.setShelfLife(bd.getShelfLife());
                    nsp.getBatchDataList().add(newBd);
                } else {
                    newBd = nsp.getBatchDataList().get(idx2);
                    newBd.setOpeningBalance(bd.getClosingBalance());
                }
            }
            nsp.getBatchDataList().sort(new ComparatorBatchData());
        }
    }

    public void buildPlan() throws ParseException {
        StringBuilder regionString = new StringBuilder()
                .append("TransDate").append("\t")
                .append("OB").append("\t")
                .append("Expred").append("\t")
                .append("Shipmnt").append("\t")
                .append("ActCon").append("\t")
                .append("FnlCon").append("\t")
                .append("Stock").append("\t")
                .append("UseAdj").append("\t")
                .append("FnlAdj").append("\t")
                .append("Expctd").append("\t")
                .append("NA").append("\t")
                .append("CB").append("\t")
                .append("Unmet").append("\r\n");
        StringBuilder batchString = new StringBuilder()
                .append("TrnsDt").append("\t")
                .append("BtchId").append("\t")
                .append("OB").append("\t")
                .append("Expred").append("\t")
                .append("Shipmnt").append("\t")
                .append("UseAct").append("\t")
                .append("ActCon").append("\t")
                .append("AllReg").append("\t")
                .append("Stock").append("\t")
                .append("UseAdj").append("\t")
                .append("Adj").append("\t")
                .append("UnalC").append("\t")
                .append("CaclC").append("\t")
                .append("CB").append("\r\n");
        for (NewSupplyPlan nsp : this.nspList) {
            // For Regions
            updateOpeningBalance(nsp);
            // Shipments are already stored from the ResultSetExtractor
            nsp.updateExpiredStock();
            // Final Consumption is done from the ResultSetExtractor
            // Final Adjustment is done from the ResultSetExtractor
            nsp.updateExpectedStock();
            nsp.updateNationalAdjustment();
            nsp.updateClosingBalance();
            nsp.updateUnmetDemand();

            
            // Batches
            // Opening Balance is already set with Regions
            // Shipments are set through the ResultSetExtractor
            // Expired stock is already set with the Regions
            nsp.updateBatchData();
            nsp.removeUnusedBatches();
            nsp.getBatchDataList().forEach(bd -> {
                bd.setUseActualConsumption(nsp.isActualConsumptionFlag());
                bd.setUseAdjustment(nsp.isUseAdjustment());
                batchString
                        .append(nsp.getTransDate()).append("\t")
                        .append(bd.getBatchId()).append("\t")
                        .append(bd.getOpeningBalance()).append("\t")
                        .append(bd.getExpiredStock()).append("\t")
                        .append(bd.getShipment()).append("\t")
                        .append(bd.isUseActualConsumption()).append("\t")
                        .append(bd.getActualConsumption()).append("\t")
                        .append(bd.isAllRegionsReportedStock()).append("\t")
                        .append(bd.getStock()).append("\t")
                        .append(bd.isUseAdjustment()).append("\t")
                        .append(bd.getAdjustment()).append("\t")
                        .append(bd.getUnallocatedConsumption()).append("\t")
                        .append(bd.getCalculatedConsumption()).append("\t")
                        .append(bd.getClosingBalance()).append("\r\n");
            });
            regionString
                    .append(nsp.getTransDate()).append("\t")
                    .append(nsp.getOpeningBalance()).append("\t")
                    .append(nsp.getExpiredStock()).append("\t")
                    .append(nsp.getShipment()).append("\t")
                    .append(nsp.isActualConsumptionFlag()).append("\t")
                    .append(nsp.getFinalConsumption()).append("\t")
                    .append(nsp.getStock()).append("\t")
                    .append(nsp.isUseAdjustment()).append("\t")
                    .append(nsp.getFinalAdjustment()).append("\t")
                    .append(nsp.getExpectedStock()).append("\t")
                    .append(nsp.getNationalAdjustment()).append("\t")
                    .append(nsp.getClosingBalance()).append("\t")
                    .append(nsp.getUnmetDemand()).append("\r\n");

        }
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

}
