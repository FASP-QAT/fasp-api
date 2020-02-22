/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.Region;

/**
 *
 * @author altius
 */
public interface RegionService {

    public int addRegion(Region region);

    public int editRegion(Region region);

    public int getRegionList(boolean active);
}
