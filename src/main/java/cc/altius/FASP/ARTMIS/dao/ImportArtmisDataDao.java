/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author altius
 */
public interface ImportArtmisDataDao {

    public List<Integer> importOrderAndShipmentData(File orderFile, File shipmentFile) throws ParserConfigurationException, SAXException, IOException, FileNotFoundException, Exception;
    
}
