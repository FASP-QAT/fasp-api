/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.framework;

/**
 *
 * @author altius
 */
public class GlobalConstants {

    public static final int PROGRAM_TYPE_SUPPLY_PLAN = 1; // Supply Plan Programs
    public static final int PROGRAM_TYPE_DATASET = 2; // Dataset Programs
    public static final int USAGE_TEMPLATE_DISCRETE = 1;
    public static final int USAGE_TEMPLATE_CONTINUOUS = 2;

    public static final int NODE_TYPE_AGGREGATION = 1;
    public static final int NODE_TYPE_NUMBER = 2;
    public static final int NODE_TYPE_PERCENTAGE = 3;
    public static final int NODE_TYPE_FU = 4;
    public static final int NODE_TYPE_PU = 5;
    public static final int NODE_TYPE_DOWNWARD_AGGREGATION = 6;

    public static final int VERSION_TYPE_DRAFT = 1;
    public static final int VERSION_TYPE_FINAL = 2;

    public static final int COMMIT_REQUEST_NEW = 1;
    public static final int COMMIT_REQUEST_SUCCESS = 2;
    public static final int COMMIT_REQUEST_ERROR = 3;
    
    public static final int COMMIT_STATUS_TYPE_FINAL_SUBMITTED = 1; // -> Final Submitted
    public static final int COMMIT_STATUS_TYPE_FINAL_APPROVED = 2; // -> Final Approved
    public static final int COMMIT_STATUS_TYPE_FINAL_REJECTED = 3; // -> Final Rejected
    
    public static final int COMPRESS_LIMIT_SIZE = 2; // In MBs
}
