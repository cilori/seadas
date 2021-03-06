package gov.nasa.gsfc.seadas.processing.general;

import com.bc.ceres.core.runtime.RuntimeContext;
import gov.nasa.gsfc.seadas.processing.core.*;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by IntelliJ IDEA.
 * User: knowles
 * Date: 6/13/12
 * Time: 4:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileInfo {

    private File file;

    private static final String FILE_INFO_SYSTEM_CALL = "get_obpg_file_type.py";

    private static final boolean DEFAULT_MISSION_AND_FILE_TYPE_ENABLED = true;

    private final MissionInfo missionInfo = new MissionInfo();
    private final FileTypeInfo fileTypeInfo = new FileTypeInfo();
    private boolean missionAndFileTypeEnabled = DEFAULT_MISSION_AND_FILE_TYPE_ENABLED;


    public FileInfo(String defaultParent, String child) {
        this(defaultParent, child, DEFAULT_MISSION_AND_FILE_TYPE_ENABLED);
    }

    public FileInfo(String defaultParent, String child, boolean missionAndFileTypeEnabled) {

        this.missionAndFileTypeEnabled = missionAndFileTypeEnabled;
        file = SeadasFileUtils.createFile(defaultParent, child);
        if (file != null && file.exists()) {
            initMissionAndFileTypeInfos();
        }
    }

    public FileInfo(String filename) {
        if (filename != null) {
            file = new File(filename);
            if (file.exists()) {
                initMissionAndFileTypeInfos();
            }
        }
    }

    public void clear() {
        file = null;
        missionInfo.clear();
        fileTypeInfo.clear();
    }

    private void initMissionAndFileTypeInfos() {
        FileInfoFinder fileInfoFinder = new FileInfoFinder(file.getAbsolutePath());
        fileTypeInfo.setName(fileInfoFinder.getFileType());
        missionInfo.setName(fileInfoFinder.getMissionName());
    }


    //-------------------------- Indirect Get Methods ----------------------------


    public MissionInfo.Id getMissionId() {
        return missionInfo.getId();
    }

    public String getMissionName() {
        return missionInfo.getName();
    }

    public File getMissionDirectory() {
        return missionInfo.getDirectory();
    }

    public boolean isMissionId(MissionInfo.Id missionId) {
        return missionInfo.isId(missionId);
    }

    public boolean isSupportedMission() {
        return missionInfo.isSupported();
    }


    public FileTypeInfo.Id getTypeId() {
        return fileTypeInfo.getId();
    }

    public String getTypeName() {
        return fileTypeInfo.getName();
    }

    public boolean isTypeId(FileTypeInfo.Id type) {
        return fileTypeInfo.isId(type);
    }


    public boolean isGeofileRequired() {
        return missionInfo.isGeofileRequired();
    }


    public File getFile() {
        return file;
    }

    public boolean isMissionAndFileTypeEnabled() {
        return missionAndFileTypeEnabled;
    }

    public void setMissionAndFileTypeEnabled(boolean missionAndFileTypeEnabled) {
        this.missionAndFileTypeEnabled = missionAndFileTypeEnabled;
    }
}
