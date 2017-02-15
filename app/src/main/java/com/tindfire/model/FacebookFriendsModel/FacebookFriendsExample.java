package com.tindfire.model.FacebookFriendsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vcareall on 15/2/17.
 */

public class FacebookFriendsExample {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("results")
    @Expose
    private List<FacebookFriendsResult> results = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<FacebookFriendsResult> getResults() {
        return results;
    }

    public void setResults(List<FacebookFriendsResult> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "FacebookFriendsExample{" +
                "results=" + results.toString() +
                ", status=" + status +
                '}';
    }
}
