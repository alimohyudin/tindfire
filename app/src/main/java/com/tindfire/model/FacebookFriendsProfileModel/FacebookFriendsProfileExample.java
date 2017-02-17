package com.tindfire.model.FacebookFriendsProfileModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vcareall on 16/2/17.
 */

public class FacebookFriendsProfileExample {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("results")
    @Expose
    private FacebookFriendsProfileResults results;





    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public FacebookFriendsProfileResults getResults() {
        return results;
    }

    public void setResults(FacebookFriendsProfileResults results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "FacebookFriendsProfileExample{" +
                "results=" + results +
                ", status=" + status.toString() +
                '}';
    }
}
