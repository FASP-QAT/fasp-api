/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import cc.altius.FASP.model.SimpleObject;
import java.io.Serializable;

/**
 *
 * @author altius
 */
public class ERPNotificationDTO extends ManualTaggingDTO implements Serializable{

    private int notificationId;
    private SimpleObject notificationType;
    private boolean addressed;

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public SimpleObject getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(SimpleObject notificationType) {
        this.notificationType = notificationType;
    }

    public boolean isAddressed() {
        return addressed;
    }

    public void setAddressed(boolean addressed) {
        this.addressed = addressed;
    }

    @Override
    public String toString() {
        return "ERPNotificationDTO{" + "notificationId=" + notificationId + ", notificationType=" + notificationType + ", addressed=" + addressed + '}';
    }

}
