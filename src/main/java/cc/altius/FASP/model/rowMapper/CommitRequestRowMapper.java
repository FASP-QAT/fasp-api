/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.CommitRequest;
import cc.altius.FASP.model.DatasetData;
import cc.altius.FASP.model.EmptyDoubleTypeAdapter;
import cc.altius.FASP.model.EmptyIntegerTypeAdapter;
import cc.altius.FASP.model.ProgramData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class CommitRequestRowMapper implements RowMapper<CommitRequest> {

    private final String FILE_PATH;

    public CommitRequestRowMapper(String filePath) {
        this.FILE_PATH = filePath;
    }

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
        spcr.setFailedReason(rs.getString("FAILED_REASON"));
        spcr.setProgramTypeId(rs.getInt("PROGRAM_TYPE_ID"));
        if (spcr.isSaveData()) {
            Path path = FileSystems.getDefault().getPath(FILE_PATH, spcr.getCommitRequestId() + ".json");
            String json = null;
            try {
                json = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new SQLException(e);
            }
//        spcr.setJson(json);
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .registerTypeAdapter(Double.class, new EmptyDoubleTypeAdapter())
                    .registerTypeAdapter(Integer.class, new EmptyIntegerTypeAdapter())
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .setLenient();
            Gson gson = gsonBuilder.create();
            try {
                if (spcr.getProgramTypeId() == 1) {
                    ProgramData data = gson.fromJson(json, new TypeToken<ProgramData>() {
                    }.getType());
                    spcr.setProgramData(data);
                } else if (spcr.getProgramTypeId() == 2) {
                    DatasetData data = gson.fromJson(json, new TypeToken<DatasetData>() {
                    }.getType());
                    spcr.setDatasetData(data);
                }
            } catch (Exception e) {
                spcr.setFailedReason(e.getMessage());
            }
        }
        return spcr;
    }

}
