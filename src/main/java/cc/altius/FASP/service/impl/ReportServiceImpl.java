/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ReportDao;
import cc.altius.FASP.service.ReportService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ekta
 */
@Service
public class ReportServiceImpl implements ReportService{
@Autowired
ReportDao reportDao;
    @Override
    public List<Map<String, Object>> getConsumptionData(int realmId, int productcategoryId, int planningUnitId,String StartDate,String endDate) {
    return this.reportDao.getConsumptionData(realmId, productcategoryId, planningUnitId,StartDate,endDate);
    }

    @Override
    public List<Map<String,Object>> getStockStatusMatrix(int realmId, int productcategoryId, int planningUnitId, int view) {
       return this.reportDao.getStockStatusMatrix(realmId, productcategoryId, planningUnitId, view);
    }
    
    
}
