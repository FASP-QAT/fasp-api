/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class RealmProblem extends BaseModel implements Serializable {

    @JsonView(Views.InternalView.class)
    private int realmProblemId;
    @JsonView(Views.InternalView.class)
    private SimpleCodeObject realm;
    @JsonView(Views.InternalView.class)
    private Problem problem;
    @JsonView(Views.InternalView.class)
    private Criticality criticality;
    @JsonView(Views.InternalView.class)
    private String data1;
    @JsonView(Views.InternalView.class)
    private String data2;
    @JsonView(Views.InternalView.class)
    private String data3;
    @JsonView(Views.InternalView.class)
    private SimpleObject problemType;

    public int getRealmProblemId() {
        return realmProblemId;
    }

    public void setRealmProblemId(int realmProblemId) {
        this.realmProblemId = realmProblemId;
    }

    public SimpleCodeObject getRealm() {
        return realm;
    }

    public void setRealm(SimpleCodeObject realm) {
        this.realm = realm;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public Criticality getCriticality() {
        return criticality;
    }

    public void setCriticality(Criticality criticality) {
        this.criticality = criticality;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getData3() {
        return data3;
    }

    public void setData3(String data3) {
        this.data3 = data3;
    }

    public SimpleObject getProblemType() {
        return problemType;
    }

    public void setProblemType(SimpleObject problemType) {
        this.problemType = problemType;
    }

}
