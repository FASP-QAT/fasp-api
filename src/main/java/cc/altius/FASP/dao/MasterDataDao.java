/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ExtrapolationMethod;
import cc.altius.FASP.model.NodeType;
import cc.altius.FASP.model.NodeTypeSync;
import cc.altius.FASP.model.ShipmentStatus;
import cc.altius.FASP.model.SimpleBaseModel;
import cc.altius.FASP.model.SimpleObject;
import java.util.List;

/**
 *
 * @author akil
 */
public interface MasterDataDao {

    public List<SimpleBaseModel> getUsageTypeList(boolean active, CustomUserDetails curUser);

    public List<NodeType> getNodeTypeList(boolean active, CustomUserDetails curUser);

    public List<SimpleBaseModel> getForecastMethodTypeList(boolean active, CustomUserDetails curUser);

    public List<SimpleBaseModel> getUsageTypeListForSync(String lastSyncDate, CustomUserDetails curUser);

    public List<NodeTypeSync> getNodeTypeListForSync(String lastSyncDate, CustomUserDetails curUser);

    public List<SimpleBaseModel> getForecastMethodTypeListForSync(String lastSyncDate, CustomUserDetails curUser);

    public List<ExtrapolationMethod> getExtrapolationMethodListForSync(String lastSyncDate, CustomUserDetails curUser);

    public List<SimpleObject> getVersionTypeList();

    public List<SimpleObject> getVersionStatusList();

    public List<ShipmentStatus> getShipmentStatusList(boolean active);

    public List<ShipmentStatus> getShipmentStatusListForSync(String lastSyncDate, CustomUserDetails curUser);
}
