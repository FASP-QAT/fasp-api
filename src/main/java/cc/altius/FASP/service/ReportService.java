/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import java.util.List;
import java.util.Map;

/**
 *
 * @author ekta
 */
public interface ReportService {

    public List<Map<String,Object>> getConsumptionData(int realmId, int productcategoryId, int planningUnitId,String StartDate,String endDate);

    public Object getStockStatusMatrix(int realmId, int productcategoryId, int planningUnitId, int view);
    
}
