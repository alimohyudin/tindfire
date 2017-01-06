package com.tindfire.model.GetMatchModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by vcareall on 30/12/16.
 */

public class GetMatchPhoto implements Serializable {
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("extension")
    @Expose
    private String extension;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("fileName")
    @Expose
    private String fileName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    @Override
    public String toString() {
        return "GetMatchPhoto{" +
                "extension='" + extension + '\'' +
                ", url='" + url + '\'' +
                ", id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
