package com.tindfire.model.GetMatchModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vcareall on 30/12/16.
 */

public class GetMatchPerson  implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ping_time")
    @Expose
    private String pingTime;
    @SerializedName("photos")
    @Expose
    private List<GetMatchPhoto> photos = null;
    @SerializedName("badges")
    @Expose
    private List<Object> badges = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPingTime() {
        return pingTime;
    }

    public void setPingTime(String pingTime) {
        this.pingTime = pingTime;
    }

    public List<GetMatchPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<GetMatchPhoto> photos) {
        this.photos = photos;
    }

    public List<Object> getBadges() {
        return badges;
    }

    public void setBadges(List<Object> badges) {
        this.badges = badges;
    }

    @Override
    public String toString() {
        return "GetMatchPerson{" +
                "badges=" + badges +
                ", id='" + id + '\'' +
                ", bio='" + bio + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", gender=" + gender +
                ", name='" + name + '\'' +
                ", pingTime='" + pingTime + '\'' +
                ", photos=" + photos +
                '}';
    }
}
