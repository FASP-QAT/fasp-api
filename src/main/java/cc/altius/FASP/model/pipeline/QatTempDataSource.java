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
public class QatTempDataSource {
private int dataSourceId;
private int dataSourceTypeId;
private String pipelineDataSourceType;
    private String pipelineDataSource;
  private String pipelineDataSourceId;

public QatTempDataSource(){
}
public QatTempDataSource(int dataSourceId,int dataSourceTypeId,String pipelineDataSourceType,String pipelineDataSource,String pipelineDataSourceId){
 this.dataSourceId = dataSourceId;
 this.pipelineDataSourceType = pipelineDataSourceType;
   this.pipelineDataSource = pipelineDataSource;
this.pipelineDataSourceId = pipelineDataSourceId;
this.dataSourceTypeId = dataSourceTypeId;
}
 public int getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(int dataSourceId) {
        this.dataSourceId = dataSourceId;
    }
public int getDataSourceTypeId() {
        return dataSourceTypeId;
    }

    public void setDataSourceTypeId(int dataSourceTypeId) {
        this.dataSourceTypeId = dataSourceTypeId;
    }
    public String getPipelineDataSourceType() {
        return pipelineDataSourceType;
    }

    public void setPipelineDataSourceType(String pipelineDataSourceType) {
        this.pipelineDataSourceType = pipelineDataSourceType;
    }

    public String getPipelineDataSource() {
        return pipelineDataSource;
    }

    public void setPipelineDataSource(String pipelineDataSource) {
        this.pipelineDataSource = pipelineDataSource;
    }

 public String getPipelineDataSourceId() {
        return pipelineDataSourceId;
    }

    public void setPipelineDataSourceId(String pipelineDataSourceId) {
        this.pipelineDataSourceId = pipelineDataSourceId;
    }
   


}
