package com.tindfire.model.RecomondationModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vcareall on 27/12/16.
 */
public class RecomondationnPhoto implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("processedFiles")
    @Expose
    private List<RecomondationProcessedFile> processedFiles = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<RecomondationProcessedFile> getProcessedFiles() {
        return processedFiles;
    }

    public void setProcessedFiles(List<RecomondationProcessedFile> processedFiles) {
        this.processedFiles = processedFiles;

    }

    @Override
    public String toString() {
        return "RecomondationnPhoto{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", processedFiles=" + processedFiles.toString() +
                '}';
    }
}
