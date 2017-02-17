package com.tindfire.model.SuperLikeModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vcareall on 14/2/17.
 */

public class SuperLikeExample {

    @SerializedName("match")
    @Expose
    private Boolean match;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("super_likes")
    @Expose
    private SuperLikes superLikes;











    public Boolean getMatch() {
        return match;
    }

    public void setMatch(Boolean match) {
        this.match = match;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public SuperLikes getSuperLikes() {
        return superLikes;
    }

    public void setSuperLikes(SuperLikes superLikes) {
        this.superLikes = superLikes;
    }

    @Override
    public String toString() {
        return "SuperLikeExample{" +
                "match=" + match +
                ", status=" + status +
                ", superLikes=" + superLikes.toString() +
                '}';
    }
}
