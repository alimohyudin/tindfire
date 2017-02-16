package com.tindfire.model.FacebookFriendsProfileModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vcareall on 16/2/17.
 */

public class FacebookFriendsProfileResults {
    @SerializedName("connection_count")
    @Expose
    private Integer connectionCount;
    @SerializedName("common_likes")
    @Expose
    private List<Object> commonLikes = null;
    @SerializedName("common_friends")
    @Expose
    private List<Object> commonFriends = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("badges")
    @Expose
    private List<Object> badges = null;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ping_time")
    @Expose
    private String pingTime;
    @SerializedName("photos")
    @Expose
    private List<FacebookFriendsProfilePhoto> photos = null;
    @SerializedName("jobs")
    @Expose
    private List<Object> jobs = null;
    @SerializedName("schools")
    @Expose
    private List<FacebookFriendsProfileSchool> schools = null;
    @SerializedName("teasers")
    @Expose
    private List<Object> teasers = null;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("birth_date_info")
    @Expose
    private String birthDateInfo;
    @SerializedName("distance_mi")
    @Expose
    private Integer distanceMi;

    public Integer getConnectionCount() {
        return connectionCount;
    }

    public void setConnectionCount(Integer connectionCount) {
        this.connectionCount = connectionCount;
    }

    public List<Object> getCommonLikes() {
        return commonLikes;
    }

    public void setCommonLikes(List<Object> commonLikes) {
        this.commonLikes = commonLikes;
    }

    public List<Object> getCommonFriends() {
        return commonFriends;
    }

    public void setCommonFriends(List<Object> commonFriends) {
        this.commonFriends = commonFriends;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Object> getBadges() {
        return badges;
    }

    public void setBadges(List<Object> badges) {
        this.badges = badges;
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

    public List<FacebookFriendsProfilePhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<FacebookFriendsProfilePhoto> photos) {
        this.photos = photos;
    }

    public List<Object> getJobs() {
        return jobs;
    }

    public void setJobs(List<Object> jobs) {
        this.jobs = jobs;
    }

    public List<FacebookFriendsProfileSchool> getSchools() {
        return schools;
    }

    public void setSchools(List<FacebookFriendsProfileSchool> schools) {
        this.schools = schools;
    }

    public List<Object> getTeasers() {
        return teasers;
    }

    public void setTeasers(List<Object> teasers) {
        this.teasers = teasers;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBirthDateInfo() {
        return birthDateInfo;
    }

    public void setBirthDateInfo(String birthDateInfo) {
        this.birthDateInfo = birthDateInfo;
    }

    public Integer getDistanceMi() {
        return distanceMi;
    }

    public void setDistanceMi(Integer distanceMi) {
        this.distanceMi = distanceMi;
    }

    @Override
    public String toString() {
        return "FacebookFriendsProfileResults{" +
                "badges=" + badges +
                ", connectionCount=" + connectionCount +
                ", commonLikes=" + commonLikes +
                ", commonFriends=" + commonFriends +
                ", id='" + id + '\'' +
                ", bio='" + bio + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", name='" + name + '\'' +
                ", pingTime='" + pingTime + '\'' +
                ", photos=" + photos.toString() +
                ", jobs=" + jobs +
                ", schools=" + schools +
                ", teasers=" + teasers +
                ", gender=" + gender +
                ", birthDateInfo='" + birthDateInfo + '\'' +
                ", distanceMi=" + distanceMi +
                '}';
    }
}
