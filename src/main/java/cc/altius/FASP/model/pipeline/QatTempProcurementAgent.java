/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cc.altius.FASP.model.pipeline;

/**
 *
 * @author ekta
 */
public class QatTempProcurementAgent {
private int procurementAgentId;
    private String pipelineProcurementAgent;
  private String pipelineProcurementAgentId;

public QatTempProcurementAgent(){
}
public QatTempProcurementAgent(int procurementAgentId,String pipelineProcurementAgent,String pipelineProcurementAgentId){
 this.procurementAgentId = procurementAgentId;
   this.pipelineProcurementAgent = pipelineProcurementAgent;
this.pipelineProcurementAgentId = pipelineProcurementAgentId;
}
 public int getProcurementAgentId() {
        return procurementAgentId;
    }

    public void setProcurementAgentId(int procurementAgentId) {
        this.procurementAgentId = procurementAgentId;
    }


    public String getPipelineProcurementAgent() {
        return pipelineProcurementAgent;
    }

    public void setPipelineProcurementAgent(String pipelineProcurementAgent) {
        this.pipelineProcurementAgent = pipelineProcurementAgent;
    }

 public String getPipelineProcurementAgentId() {
        return pipelineProcurementAgentId;
    }

    public void setPipelineProcurementAgentId(String pipelineProcurementAgentId) {
        this.pipelineProcurementAgentId = pipelineProcurementAgentId;
    }
   


}
