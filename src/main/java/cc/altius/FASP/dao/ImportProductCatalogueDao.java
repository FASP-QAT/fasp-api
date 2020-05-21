/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

/**
 *
 * @author altius
 */
public interface ImportProductCatalogueDao {

    public void importProductCatalogue(String filePath);

    public void pullUnitTable();

    public void pullTracerCategoryFromTmpTables();

    public void pullForecastingUnitFromTmpTables();

    public void pullPlanningUnitFromTmpTables();

    public void pullSupplierFromTmpTables();

    public void pullProcurementUnitFromTmpTables();
}
