/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgSupplierDTO;
import cc.altius.FASP.model.Supplier;
import java.util.List;

/**
 *
 * @author altius
 */
public interface SupplierDao {

    public List<PrgSupplierDTO> getSupplierListForSync(String lastSyncDate,int realmId);

    public int addSupplier(Supplier m, CustomUserDetails curUser);

    public int updateSupplier(Supplier m, CustomUserDetails curUser);

    public List<Supplier> getSupplierList(boolean active, CustomUserDetails curUser);
    
    public List<Supplier> getSupplierListForRealm(int realmId, boolean active, CustomUserDetails curUser);

    public Supplier getSupplierById(int supplierId, CustomUserDetails curUser);

}
