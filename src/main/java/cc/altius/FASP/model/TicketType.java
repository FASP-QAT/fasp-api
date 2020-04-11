/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

/**
 *
 * @author palash
 */
public class TicketType extends BaseModel{
    private int ticketTypeId;
    private Label label;
    private int ticketLevel;
    

    public int getTicketLevel() {
        return ticketLevel;
    }

    public void setTicketLevel(int ticketLevel) {
        this.ticketLevel = ticketLevel;
    }
    public int getTicketTypeId() {
        return ticketTypeId;
    }

    public void setTicketTypeId(int ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "TicketType{" + "ticketTypeId=" + ticketTypeId + ", label=" + label + ", ticketLevel=" + ticketLevel + '}';
    }
    
    
}
