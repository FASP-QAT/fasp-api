/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.xml.sax.SAXException;

/**
 *
 * @author altius
 */
public interface ImportProductCatalogueDao {

    public void importProductCatalogue(String filePath) throws ParserConfigurationException, SAXException, IOException, FileNotFoundException, BadSqlGrammarException;

    public void pullUnitTable();

    public void pullTracerCategoryFromTmpTables();

    public void pullForecastingUnitFromTmpTables();

    public void pullPlanningUnitFromTmpTables();

    public void pullSupplierFromTmpTables();

    public void pullProcurementUnitFromTmpTables();
}
