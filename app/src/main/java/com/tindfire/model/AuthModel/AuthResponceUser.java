package com.tindfire.model.AuthModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vcareall on 27/12/16.
 */
public class AuthResponceUser {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("active_time")
    @Expose
    private String activeTime;
    @SerializedName("can_create_squad")
    @Expose
    private Boolean canCreateSquad;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("age_filter_max")
    @Expose
    private Integer ageFilterMax;
    @SerializedName("age_filter_min")
    @Expose
    private Integer ageFilterMin;
    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("connection_count")
    @Expose
    private Integer connectionCount;
    @SerializedName("distance_filter")
    @Expose
    private Integer distanceFilter;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("groups")
    @Expose
    private List<String> groups = null;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("gender_filter")
    @Expose
    private Integer genderFilter;
    @SerializedName("interests")
    @Expose
    private List<AuthResponceInterest> interests = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ping_time")
    @Expose
    private String pingTime;
    @SerializedName("discoverable")
    @Expose
    private Boolean discoverable;
    @SerializedName("photos")
    @Expose
    private List<AuthResponcePhoto> photos = null;
    @SerializedName("photos_processing")
    @Expose
    private Boolean photosProcessing;
    @SerializedName("jobs")
    @Expose
    private List<AuthResponceJob> jobs = null;
    @SerializedName("schools")
    @Expose
    private List<AuthResponceSchool> schools = null;
    @SerializedName("squads_discoverable")
    @Expose
    private Boolean squadsDiscoverable;
    @SerializedName("squads_only")
    @Expose
    private Boolean squadsOnly;
    @SerializedName("purchases")
    @Expose
    private List<Object> purchases = null;
    @SerializedName("is_new_user")
    @Expose
    private Boolean isNewUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public Boolean getCanCreateSquad() {
        return canCreateSquad;
    }

    public void setCanCreateSquad(Boolean canCreateSquad) {
        this.canCreateSquad = canCreateSquad;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getAgeFilterMax() {
        return ageFilterMax;
    }

    public void setAgeFilterMax(Integer ageFilterMax) {
        this.ageFilterMax = ageFilterMax;
    }

    public Integer getAgeFilterMin() {
        return ageFilterMin;
    }

    public void setAgeFilterMin(Integer ageFilterMin) {
        this.ageFilterMin = ageFilterMin;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
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

    public Integer getConnectionCount() {
        return connectionCount;
    }

    public void setConnectionCount(Integer connectionCount) {
        this.connectionCount = connectionCount;
    }

    public Integer getDistanceFilter() {
        return distanceFilter;
    }

    public void setDistanceFilter(Integer distanceFilter) {
        this.distanceFilter = distanceFilter;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getGenderFilter() {
        return genderFilter;
    }

    public void setGenderFilter(Integer genderFilter) {
        this.genderFilter = genderFilter;
    }

    public List<AuthResponceInterest> getInterests() {
        return interests;
    }

    public void setInterests(List<AuthResponceInterest> interests) {
        this.interests = interests;
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

    public Boolean getDiscoverable() {
        return discoverable;
    }

    public void setDiscoverable(Boolean discoverable) {
        this.discoverable = discoverable;
    }

    public List<AuthResponcePhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<AuthResponcePhoto> photos) {
        this.photos = photos;
    }

    public Boolean getPhotosProcessing() {
        return photosProcessing;
    }

    public void setPhotosProcessing(Boolean photosProcessing) {
        this.photosProcessing = photosProcessing;
    }

    public List<AuthResponceJob> getJobs() {
        return jobs;
    }

    public void setJobs(List<AuthResponceJob> jobs) {
        this.jobs = jobs;
    }

    public List<AuthResponceSchool> getSchools() {
        return schools;
    }

    public void setSchools(List<AuthResponceSchool> schools) {
        this.schools = schools;
    }

    public Boolean getSquadsDiscoverable() {
        return squadsDiscoverable;
    }

    public void setSquadsDiscoverable(Boolean squadsDiscoverable) {
        this.squadsDiscoverable = squadsDiscoverable;
    }

    public Boolean getSquadsOnly() {
        return squadsOnly;
    }

    public void setSquadsOnly(Boolean squadsOnly) {
        this.squadsOnly = squadsOnly;
    }

    public List<Object> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Object> purchases) {
        this.purchases = purchases;
    }

    public Boolean getIsNewUser() {
        return isNewUser;
    }

    public void setIsNewUser(Boolean isNewUser) {
        this.isNewUser = isNewUser;
    }
}
