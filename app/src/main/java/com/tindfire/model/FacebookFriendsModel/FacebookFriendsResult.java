package com.tindfire.model.FacebookFriendsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vcareall on 15/2/17.
 */

public class FacebookFriendsResult {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("photo")
    @Expose
    private List<FacebookFriendsPhoto> photo = null;
    @SerializedName("in_squad")
    @Expose
    private Boolean inSquad;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FacebookFriendsPhoto> getPhoto() {
        return photo;
    }

    public void setPhoto(List<FacebookFriendsPhoto> photo) {
        this.photo = photo;
    }

    public Boolean getInSquad() {
        return inSquad;
    }

    public void setInSquad(Boolean inSquad) {
        this.inSquad = inSquad;
    }

    @Override
    public String toString() {
        return "FacebookFriendsResult{" +
                "inSquad=" + inSquad +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", photo=" + photo.toString()+
                '}';
    }
}
