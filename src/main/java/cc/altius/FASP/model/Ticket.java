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
public class Ticket extends BaseModel{
    private int ticketId;
    private String note;
    private int refferenceId;
    private TicketStatus ticketStatus;
    private TicketType ticketType;

    public int getRefferenceId() {
        return refferenceId;
    }

    public void setRefferenceId(int refferenceId) {
        this.refferenceId = refferenceId;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }
      
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Ticket{" + "ticketId=" + ticketId + ", note=" + note + ", refferenceId=" + refferenceId + ", ticketStatus=" + ticketStatus + ", ticketType=" + ticketType + '}';
    }

}
