package com.tindfire.model.RecomondationModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by vcareall on 27/12/16.
 */
public class RecomondationProcessedFile implements Serializable {
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("width")
    @Expose
    private Integer width;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "RecomondationProcessedFile{" +
                "height=" + height +
                ", url='" + url + '\'' +
                ", width=" + width +
                '}';
    }
}
