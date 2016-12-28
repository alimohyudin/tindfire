package com.tindfire.model.RecomondationModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vcareall on 27/12/16.
 */
public class RecomondationResponce implements Serializable {
    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("results")
    @Expose
    private List<RecomondationResult> results = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<RecomondationResult> getResults() {
        return results;
    }

    public void setResults(List<RecomondationResult> results) {
        this.results = results;
    }



    @Override
    public String toString() {
        return "RecomondationResponce{" +
                ", status=" + status +
                ", results=" + results.toString() +
                '}';
    }
}
