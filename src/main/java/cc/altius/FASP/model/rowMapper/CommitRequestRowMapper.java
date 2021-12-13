/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.DatasetData;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.CommitRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class CommitRequestRowMapper implements RowMapper<CommitRequest> {

    @Override
    public CommitRequest mapRow(ResultSet rs, int i) throws SQLException {
        CommitRequest spcr = new CommitRequest();
        spcr.setCommitRequestId(rs.getInt("COMMIT_REQUEST_ID"));
        spcr.setProgram(new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, i), rs.getString("PROGRAM_CODE")));
        spcr.setCommittedVersionId(rs.getInt("COMMITTED_VERSION_ID"));
        spcr.setVersionType(new SimpleObject(rs.getInt("VERSION_TYPE_ID"), new LabelRowMapper("VERSION_TYPE_").mapRow(rs, i)));
        spcr.setNotes(rs.getString("NOTES"));
        spcr.setSaveData(rs.getBoolean("SAVE_DATA"));
        spcr.setCreatedBy(new BasicUser(rs.getInt("CB_USER_ID"), rs.getString("CB_USERNAME")));
        spcr.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
        spcr.setCompletedDate(rs.getTimestamp("COMPLETED_DATE"));
        spcr.setStatus(rs.getInt("STATUS"));
        String json = rs.getString("JSON");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setLenient().create();
        spcr.setProgramTypeId(rs.getInt("PROGRAM_TYPE_ID"));
        if (spcr.getProgramTypeId() == 1) {
            ProgramData data = gson.fromJson(json, new TypeToken<ProgramData>() {
            }.getType());
            spcr.setProgramData(data);
        } else if (spcr.getProgramTypeId() == 2) {
            DatasetData data = gson.fromJson(json, new TypeToken<DatasetData>() {
            }.getType());
            spcr.setDatasetData(data);
        }
        return spcr;
    }

}
