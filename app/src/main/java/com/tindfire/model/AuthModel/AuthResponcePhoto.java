package com.tindfire.model.AuthModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vcareall on 27/12/16.
 */
public class AuthResponcePhoto {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("shape")
    @Expose
    private String shape;
    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("fbId")
    @Expose
    private String fbId;
    @SerializedName("extension")
    @Expose
    private String extension;
    @SerializedName("processedFiles")
    @Expose
    private List<AuthResponceProcessedFile> processedFiles = null;
    @SerializedName("url")
    @Expose
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public List<AuthResponceProcessedFile> getProcessedFiles() {
        return processedFiles;
    }

    public void setProcessedFiles(List<AuthResponceProcessedFile> processedFiles) {
        this.processedFiles = processedFiles;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
