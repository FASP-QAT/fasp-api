
import cc.altius.FASP.model.BatchData;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import cc.altius.FASP.model.NewSupplyPlan;
import static org.junit.Assert.assertTrue;

public class NewSupplyPlanTest {

    @Test
    public void testSupplyPlanTracks_Calculated() {
        System.out.println("--- STARTING TEST: Calculated Closing Balance (No Stock Report) ---");
        NewSupplyPlan plan = new NewSupplyPlan(1, "2024-01-01", 12);

        // Setup Base Data
        plan.setOpeningBalance(1000.0);
        plan.setOpeningBalanceWps(1000.0);
        plan.setOpeningBalanceWtbdps(1000.0);
        plan.setFinalConsumptionQty(500.0);
        plan.setAdjustmentQty(50.0);

        // Shipments: 100 Received, 400 Planned (150 of which are Confirmed/Not TBD)
        plan.addReceivedShipmentsTotalData(100.0);
        plan.addPlannedShipmentsTotalData(400.0);
        plan.addPlannedShipmentsTotalWtbdData(150.0);

        // Force calculation mode: 1 region total, 0 reported
        plan.setRegionCount(1);
        plan.setRegionCountForStock(0);

        plan.updateExpectedStock();
        plan.updateNationalAdjustment();
        plan.updateClosingBalance();

        System.out.println("Standard Expected Stock: " + plan.getExpectedStock());
        System.out.println("WPS Expected Stock:      " + plan.getExpectedStockWps());
        System.out.println("WTBDPS Expected Stock:   " + plan.getExpectedStockWtbdps());

        assertEquals(1050.0, plan.getClosingBalance(), 0.001);
        assertEquals(650.0, plan.getClosingBalanceWps(), 0.001);
        assertEquals(800.0, plan.getClosingBalanceWtbdps(), 0.001);
        System.out.println("TEST PASSED: Calculated balances match expected tracks.\n");
    }

    @Test
    public void testClosingBalanceWithReportedStock() {
        System.out.println("--- STARTING TEST: Reported Stock Priority ---");
        NewSupplyPlan plan = new NewSupplyPlan(1, "2024-01-01", 12);

        plan.setOpeningBalance(1000.0);
        plan.setOpeningBalanceWps(1000.0);
        plan.setOpeningBalanceWtbdps(1000.0);
        plan.setFinalConsumptionQty(200.0); // Math expects 800

        // Report Physical Stock: 1 region exists, 1 region reported 900 units
        plan.setRegionCount(1);
        plan.setRegionCountForStock(1);
        plan.addStockQty(900.0);

        plan.updateExpectedStock();
        plan.updateNationalAdjustment();
        plan.updateClosingBalance();

        System.out.println("Reported Stock Qty: " + plan.getStockQty());
        System.out.println("Standard Expected:  " + plan.getExpectedStock() + " -> National Adj: " + plan.getNationalAdjustment());
        System.out.println("Standard Closing:   " + plan.getClosingBalance());
        System.out.println("WPS Closing:        " + plan.getClosingBalanceWps());
        System.out.println("WTBDPS Closing:     " + plan.getClosingBalanceWtbdps());

        // All tracks must converge on the reported stock value
        assertEquals(900.0, plan.getClosingBalance(), 0.001);
        assertEquals(900.0, plan.getClosingBalanceWps(), 0.001);
        assertEquals(900.0, plan.getClosingBalanceWtbdps(), 0.001);
        System.out.println("TEST PASSED: All tracks converged on reported stock qty.");
    }

    @Test
    public void testScenario1_StockOutAndUnmetDemand() {
        System.out.println("--- Scenario 1: Stock-Out (Unmet Demand) ---");
        NewSupplyPlan plan = new NewSupplyPlan(1, "2024-01-01", 12);
        plan.setOpeningBalance(100.0);
        plan.setOpeningBalanceWps(100.0);
        plan.setOpeningBalanceWtbdps(100.0);

        plan.setFinalConsumptionQty(1000.0); // Huge demand
        plan.addAdjustedConsumptionQty(1000.0);
        plan.addActualConsumptionQty(0.0);

        plan.updateExpectedStock();
        plan.updateUnmetDemand();

        System.out.println("Standard Unmet Demand: " + plan.getUnmetDemand());
        System.out.println("WPS Unmet Demand:      " + plan.getUnmetDemandWps());

        // Expected Stock is -900. Unmet demand = 0 - (-900) + (1000-0) = 1900? 
        // Actually, based on logic: 0 - expectedStock + (Adjusted - Actual)
        assertTrue(plan.getUnmetDemand() > 0);
        assertEquals(plan.getUnmetDemand(), plan.getUnmetDemandWps(), 0.001);
    }

    @Test
    public void testScenario2_BatchExpiry() {
        System.out.println("--- Scenario 2: Batch Expiry Transition ---");
        NewSupplyPlan plan = new NewSupplyPlan(1, "2024-01-01", 12);

        // Create a batch that expires exactly on the transDate
        BatchData expiringBatch = new BatchData();
        expiringBatch.setExpiryDate("2024-01-01");
        expiringBatch.setOpeningBalance(500.0);
        expiringBatch.setOpeningBalanceWps(500.0);
        expiringBatch.setOpeningBalanceWtbdps(500.0);
        plan.getBatchDataList().add(expiringBatch);

        plan.updateExpiredStock();

        System.out.println("Total Expired Stock: " + plan.getExpiredStock());
        assertEquals(500.0, plan.getExpiredStock(), 0.001);
        assertEquals(500.0, plan.getExpiredStockWps(), 0.001);
    }

    @Test
    public void testScenario3_PartialRegionalReporting() {
        System.out.println("--- Scenario 3: Partial Regional Reporting (5/10 Regions) ---");
        NewSupplyPlan plan = new NewSupplyPlan(1, "2024-01-01", 12);
        plan.setOpeningBalance(1000.0);
        plan.setOpeningBalanceWps(1000.0);

        plan.setRegionCount(10);
        plan.setRegionCountForStock(5);
        plan.addStockQty(200.0); // Only 5 regions reported 200, but math says we should have ~1000

        plan.updateExpectedStock();
        plan.updateNationalAdjustment();
        plan.updateClosingBalance();

        // Since stockQty (200) < expectedStock (1000), and not all regions reported,
        // it should take the Max of stockQty or (Expected + Adj).
        System.out.println("Closing Balance (Partial Report): " + plan.getClosingBalance());
        assertTrue(plan.getClosingBalance() >= 1000.0);
    }

    @Test
    public void testScenario4_LargeNegativeAdjustment() {
        System.out.println("--- Scenario 4: Negative Adjustment (Stock Write-off) ---");
        NewSupplyPlan plan = new NewSupplyPlan(1, "2024-01-01", 12);
        plan.setOpeningBalance(100.0);
        plan.setOpeningBalanceWps(100.0);
        plan.setOpeningBalanceWtbdps(100.0);

        // Remove 200 units (more than we have)
        plan.addActualConsumptionQty(200.0);
        plan.setAdjustmentQty(-200.0);

        plan.updateExpectedStock();
        plan.updateUnmetDemand();
        plan.updateNationalAdjustment();
        plan.updateClosingBalance();

        System.out.println("Standard Expected: " + plan.getExpectedStock());
        System.out.println("Standard Closing:  " + plan.getClosingBalance());
        System.out.println("Standard Unmet:  " + plan.getUnmetDemand());

        // Closing balance should never be negative
        assertEquals(0.0, plan.getClosingBalance(), 0.001);
        assertEquals(-200.0, plan.getUnmetDemand(), 0.001);
    }

    @Test
    public void testScenario6_ErpVsManualShipments() {
        System.out.println("--- Scenario 6: ERP vs Manual Shipments ---");
        NewSupplyPlan plan = new NewSupplyPlan(1, "2024-01-01", 12);

        plan.addPlannedShipmentsTotalData(500.0);    // Manual
        plan.addPlannedErpShipmentsTotalData(300.0); // ERP

        plan.updateExpectedStock();

        double totalShipments = plan.getManualShipmentTotal() + plan.getErpShipmentTotal();
        System.out.println("Total Combined Shipments: " + totalShipments);
        assertEquals(800.0, totalShipments, 0.001);
    }
}
