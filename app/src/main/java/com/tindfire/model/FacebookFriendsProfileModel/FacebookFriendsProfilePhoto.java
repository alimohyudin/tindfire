package com.tindfire.model.FacebookFriendsProfileModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vcareall on 16/2/17.
 */

public class FacebookFriendsProfilePhoto {
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("processedFiles")
    @Expose
    private List<FacebookFriendsProfileProcessedFile> processedFiles = null;
    @SerializedName("extension")
    @Expose
    private String extension;
    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("shape")
    @Expose
    private String shape;
    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("id")
    @Expose
    private String id;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<FacebookFriendsProfileProcessedFile> getProcessedFiles() {
        return processedFiles;
    }

    public void setProcessedFiles(List<FacebookFriendsProfileProcessedFile> processedFiles) {
        this.processedFiles = processedFiles;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
