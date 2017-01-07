package com.tindfire.model.AlreadyLike;

import java.util.List;

/**
 * Created by vcareall on 7/1/17.
 */

public class AlreadyLikeUser {
    public String name;
    public String bio;
    public String id;
    public String distance;
    public String pingtime;
    public String dateofBirth;
    List<AlreadyLikeUserImage> alreadyLikeUserImageList;

    public List<AlreadyLikeUserImage> getAlreadyLikeUserImageList() {
        return alreadyLikeUserImageList;
    }

    public void setAlreadyLikeUserImageList(List<AlreadyLikeUserImage> alreadyLikeUserImageList) {
        this.alreadyLikeUserImageList = alreadyLikeUserImageList;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPingtime() {
        return pingtime;
    }

    public void setPingtime(String pingtime) {
        this.pingtime = pingtime;
    }
}
