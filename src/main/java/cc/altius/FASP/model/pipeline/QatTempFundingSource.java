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
public class QatTempFundingSource {
private int fundingSourceId;
    private String pipelineFundingSource;
  private String pipelineFundingSourceId;

public QatTempFundingSource(){
}
public QatTempFundingSource(int fundingSourceId,String pipelineFundingSource,String pipelineFundingSourceId){
 this.fundingSourceId = fundingSourceId;
   this.pipelineFundingSource = pipelineFundingSource;
this.pipelineFundingSourceId = pipelineFundingSourceId;
}
 public int getFundingSourceId() {
        return fundingSourceId;
    }

    public void setFundingSourceId(int fundingSourceId) {
        this.fundingSourceId = fundingSourceId;
    }


    public String getPipelineFundingSource() {
        return pipelineFundingSource;
    }

    public void setPipelineFundingSource(String pipelineFundingSource) {
        this.pipelineFundingSource = pipelineFundingSource;
    }

 public String getPipelineFundingSourceId() {
        return pipelineFundingSourceId;
    }

    public void setPipelineFundingSourceId(String pipelineFundingSourceId) {
        this.pipelineFundingSourceId = pipelineFundingSourceId;
    }
   


}
