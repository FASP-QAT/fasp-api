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

    public void importProductCatalogue(StringBuilder sb) throws ParserConfigurationException, SAXException, IOException, FileNotFoundException, BadSqlGrammarException;

    public void rollBackAutoIncrement(StringBuilder sb);

    public String importProductCatalogueLegacy() throws ParserConfigurationException, SAXException, IOException, FileNotFoundException, BadSqlGrammarException;
}
