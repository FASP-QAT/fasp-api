/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.QuantimedImportDTO;
import cc.altius.FASP.model.DTO.QuantimedImportProductDTO;
import cc.altius.FASP.model.DTO.QuantimedImportRecordDTO;
import cc.altius.FASP.service.QuantimedImportService;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author altius
 */
@Service
public class QuantimedImportServiceImpl implements QuantimedImportService {

    @Value("${qat.filePath}")
    private String QAT_FILE_PATH;
    @Value("${qat.quantimedFilePath}")
    private String QMED_FILE_PATH;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());    

    @Override
    public QuantimedImportDTO importForecastData(MultipartFile file, String programId, CustomUserDetails curUser) {
        String extension = "";
        QuantimedImportDTO importDTO = new QuantimedImportDTO();
        List<QuantimedImportProductDTO> productDTOs = new LinkedList<>();
        List<QuantimedImportRecordDTO> recordDTOs = new LinkedList<>();
        QuantimedImportProductDTO importProductDTO = null;
        QuantimedImportRecordDTO importRecordDTO = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int i = file.getOriginalFilename().lastIndexOf('.');
        if (i > 0) {
            extension = file.getOriginalFilename().substring(i + 1);
        }

        if (!extension.equalsIgnoreCase("xml")) {
        } else {

            try {
                File file1 = new File(QAT_FILE_PATH+QMED_FILE_PATH + curUser.getUserId() + "_" +file.getOriginalFilename());
                file.transferTo(file1);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                FileReader fr = new FileReader(file1);
                Document doc;
                NodeList nList1, nList2;
                MapSqlParameterSource[] batchParams;
                Map<String, QuantimedImportProductDTO> map = new HashedMap<String, QuantimedImportProductDTO>();
                int[] rows1;
                int x;
                if (fr.read() == -1) {
                    //file is empty
                    logger.info("Order file is empty");
                } else {
                    doc = dBuilder.parse(file1);
                    doc.getDocumentElement().normalize();

                    importDTO.setProgramId(programId);
                    importDTO.setFileType(doc.getElementsByTagName("FileType").item(0).getTextContent());
                    importDTO.setDtmDataExported(doc.getElementsByTagName("dtmDataExported").item(0).getTextContent());
                    importDTO.setDtmStart(doc.getElementsByTagName("dtmStart").item(0).getTextContent());
                    importDTO.setDtmEnd(doc.getElementsByTagName("dtmEnd").item(0).getTextContent());
                    importDTO.setDblDataInterval(doc.getElementsByTagName("dblDataInterval").item(0).getTextContent());
                    importDTO.setSourceName(doc.getElementsByTagName("SourceName").item(0).getTextContent());

                    nList1 = doc.getElementsByTagName("Product");
                    x = 0;
                    for (int temp2 = 0; temp2 < nList1.getLength(); temp2++) {
                        Node nNode1 = nList1.item(temp2);
                        if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
                            Element dataRecordElement = (Element) nNode1;

                            importProductDTO = new QuantimedImportProductDTO();
                            importProductDTO.setProductName(dataRecordElement.getElementsByTagName("strName").item(0).getTextContent().trim());
                            importProductDTO.setProductId(dataRecordElement.getElementsByTagName("strProductID").item(0).getTextContent().trim());                            
                            importProductDTO.setSource(dataRecordElement.getElementsByTagName("Source").item(0).getTextContent().trim());
                            importProductDTO.setUserDefined(dataRecordElement.getElementsByTagName("UserDefined").item(0).getTextContent().trim());
                            importProductDTO.setProductGroup(dataRecordElement.getElementsByTagName("ProductGroup").item(0).getTextContent().trim());
                            importProductDTO.setInnovatorName(dataRecordElement.getElementsByTagName("InnovatorName").item(0).getTextContent().trim());
                            importProductDTO.setLowestUnitQuantity(dataRecordElement.getElementsByTagName("LowestUnitQty").item(0).getTextContent().trim());
                            importProductDTO.setLowestUnitMeasure(dataRecordElement.getElementsByTagName("LowestUnitMeasure").item(0).getTextContent().trim());
                            importProductDTO.setQuantificationFactor(dataRecordElement.getElementsByTagName("QuantificationFactor").item(0).getTextContent().trim());
                            productDTOs.add(importProductDTO);
                            map.put(dataRecordElement.getElementsByTagName("strProductID").item(0).getTextContent().trim(), importProductDTO);
                        }
                    }
                    productDTOs.sort((QuantimedImportProductDTO s1, QuantimedImportProductDTO s2)->s1.getProductName().compareTo(s2.getProductName()));
                    importDTO.setProducts(productDTOs);

                    nList2 = doc.getElementsByTagName("Record");
                    int tempCosumption, tempAdjustment;
                    String tempCosumptionStr, tempAdjustmentStr;
                    for (int temp2 = 0; temp2 < nList2.getLength(); temp2++) {
                        Node nNode2 = nList2.item(temp2);
                        tempCosumption = tempAdjustment = 0;
                        tempCosumptionStr = tempAdjustmentStr = null;
                        
                        if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
                            Element dataRecordElement = (Element) nNode2;

                            importRecordDTO = new QuantimedImportRecordDTO();
                            importRecordDTO.setProductId(dataRecordElement.getElementsByTagName("strProductID").item(0).getTextContent().trim());
                            
                            String sDate1=dataRecordElement.getElementsByTagName("dtmPeriod").item(0).getTextContent().trim();
                            Date date1=new SimpleDateFormat("MM/dd/yyyy").parse(sDate1);
                            
                            importRecordDTO.setDtmPeriod(dateFormat.format(date1));
                            
                            tempCosumptionStr = dataRecordElement.getElementsByTagName("lngConsumption").item(0).getTextContent().trim();
                            tempCosumption = (tempCosumptionStr != null && !tempCosumptionStr.equals("")) ? Integer.parseInt(tempCosumptionStr) : 0;
                            importRecordDTO.setIngConsumption(tempCosumption);
                            
                            tempAdjustmentStr = dataRecordElement.getElementsByTagName("lngAdjustments").item(0).getTextContent().trim();
                            tempAdjustment = (tempAdjustmentStr != null && !tempAdjustmentStr.equals("")) ? Integer.parseInt(tempAdjustmentStr) : 0;
                            importRecordDTO.setIngAdjustments(tempAdjustment);
                            
                            importRecordDTO.setProduct(map.get(dataRecordElement.getElementsByTagName("strProductID").item(0).getTextContent().trim()));
                            recordDTOs.add(importRecordDTO);
                        }
                    }
                    recordDTOs.sort((QuantimedImportRecordDTO s1, QuantimedImportRecordDTO s2)->s1.getProduct().getProductName().compareTo(s2.getProduct().getProductName()));
                    for(int s=1; s<recordDTOs.size(); s++) {
                        if(!recordDTOs.get(s-1).getProductId().equals(recordDTOs.get(s).getProductId()) && recordDTOs.get(s-1).getIngConsumption() == 0) {
                           recordDTOs.remove(s-1);
                        }
                    }
                    if(recordDTOs.size() > 1) {
                        recordDTOs.remove(recordDTOs.size() - 1);
                    }
                    importDTO.setRecords(recordDTOs);
                }
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(QuantimedImportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(QuantimedImportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(QuantimedImportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(QuantimedImportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return importDTO;
    }
}
