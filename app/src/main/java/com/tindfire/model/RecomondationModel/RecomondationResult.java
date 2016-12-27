package com.tindfire.model.RecomondationModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vcareall on 27/12/16.
 */
public class RecomondationResult {
    @SerializedName("distance_mi")
    @Expose
    private Integer distanceMi;
    @SerializedName("connection_count")
    @Expose
    private Integer connectionCount;
    @SerializedName("common_like_count")
    @Expose
    private Integer commonLikeCount;
    @SerializedName("common_friend_count")
    @Expose
    private Integer commonFriendCount;
    @SerializedName("common_likes")
    @Expose
    private List<String> commonLikes = null;


    @SerializedName("content_hash")
    @Expose
    private String contentHash;
    @SerializedName("_id")
    @Expose
    private String id;

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
    private List<RecomondationnPhoto> photos = null;



    @SerializedName("s_number")
    @Expose
    private Integer sNumber;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("birth_date_info")
    @Expose
    private String birthDateInfo;

    public Integer getDistanceMi() {
        return distanceMi;
    }

    public void setDistanceMi(Integer distanceMi) {
        this.distanceMi = distanceMi;
    }

    public Integer getConnectionCount() {
        return connectionCount;
    }

    public void setConnectionCount(Integer connectionCount) {
        this.connectionCount = connectionCount;
    }

    public Integer getCommonLikeCount() {
        return commonLikeCount;
    }

    public void setCommonLikeCount(Integer commonLikeCount) {
        this.commonLikeCount = commonLikeCount;
    }

    public Integer getCommonFriendCount() {
        return commonFriendCount;
    }

    public void setCommonFriendCount(Integer commonFriendCount) {
        this.commonFriendCount = commonFriendCount;
    }

    public List<String> getCommonLikes() {
        return commonLikes;
    }

    public void setCommonLikes(List<String> commonLikes) {
        this.commonLikes = commonLikes;
    }



    public String getContentHash() {
        return contentHash;
    }

    public void setContentHash(String contentHash) {
        this.contentHash = contentHash;
    }

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

    public List<RecomondationnPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<RecomondationnPhoto> photos) {
        this.photos = photos;
    }


    public Integer getSNumber() {
        return sNumber;
    }

    public void setSNumber(Integer sNumber) {
        this.sNumber = sNumber;
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
}
