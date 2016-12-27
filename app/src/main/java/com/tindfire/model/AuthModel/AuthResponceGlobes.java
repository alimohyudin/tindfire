package com.tindfire.model.AuthModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vcareall on 27/12/16.
 */
public class AuthResponceGlobes {
    @SerializedName("friends")
    @Expose
    private Boolean friends;
    @SerializedName("invite_type")
    @Expose
    private String inviteType;
    @SerializedName("recs_interval")
    @Expose
    private Integer recsInterval;
    @SerializedName("updates_interval")
    @Expose
    private Integer updatesInterval;
    @SerializedName("recs_size")
    @Expose
    private Integer recsSize;
    @SerializedName("matchmaker_default_message")
    @Expose
    private String matchmakerDefaultMessage;
    @SerializedName("share_default_text")
    @Expose
    private String shareDefaultText;
    @SerializedName("boost_decay")
    @Expose
    private Integer boostDecay;
    @SerializedName("boost_up")
    @Expose
    private Integer boostUp;
    @SerializedName("boost_down")
    @Expose
    private Integer boostDown;
    @SerializedName("sparks")
    @Expose
    private Boolean sparks;
    @SerializedName("kontagent")
    @Expose
    private Boolean kontagent;
    @SerializedName("sparks_enabled")
    @Expose
    private Boolean sparksEnabled;
    @SerializedName("kontagent_enabled")
    @Expose
    private Boolean kontagentEnabled;
    @SerializedName("mqtt")
    @Expose
    private Boolean mqtt;
    @SerializedName("tinder_sparks")
    @Expose
    private Boolean tinderSparks;
    @SerializedName("moments_interval")
    @Expose
    private Integer momentsInterval;
    @SerializedName("fetch_connections")
    @Expose
    private Boolean fetchConnections;
    @SerializedName("plus")
    @Expose
    private Boolean plus;

    public Boolean getFriends() {
        return friends;
    }

    public void setFriends(Boolean friends) {
        this.friends = friends;
    }

    public String getInviteType() {
        return inviteType;
    }

    public void setInviteType(String inviteType) {
        this.inviteType = inviteType;
    }

    public Integer getRecsInterval() {
        return recsInterval;
    }

    public void setRecsInterval(Integer recsInterval) {
        this.recsInterval = recsInterval;
    }

    public Integer getUpdatesInterval() {
        return updatesInterval;
    }

    public void setUpdatesInterval(Integer updatesInterval) {
        this.updatesInterval = updatesInterval;
    }

    public Integer getRecsSize() {
        return recsSize;
    }

    public void setRecsSize(Integer recsSize) {
        this.recsSize = recsSize;
    }

    public String getMatchmakerDefaultMessage() {
        return matchmakerDefaultMessage;
    }

    public void setMatchmakerDefaultMessage(String matchmakerDefaultMessage) {
        this.matchmakerDefaultMessage = matchmakerDefaultMessage;
    }

    public String getShareDefaultText() {
        return shareDefaultText;
    }

    public void setShareDefaultText(String shareDefaultText) {
        this.shareDefaultText = shareDefaultText;
    }

    public Integer getBoostDecay() {
        return boostDecay;
    }

    public void setBoostDecay(Integer boostDecay) {
        this.boostDecay = boostDecay;
    }

    public Integer getBoostUp() {
        return boostUp;
    }

    public void setBoostUp(Integer boostUp) {
        this.boostUp = boostUp;
    }

    public Integer getBoostDown() {
        return boostDown;
    }

    public void setBoostDown(Integer boostDown) {
        this.boostDown = boostDown;
    }

    public Boolean getSparks() {
        return sparks;
    }

    public void setSparks(Boolean sparks) {
        this.sparks = sparks;
    }

    public Boolean getKontagent() {
        return kontagent;
    }

    public void setKontagent(Boolean kontagent) {
        this.kontagent = kontagent;
    }

    public Boolean getSparksEnabled() {
        return sparksEnabled;
    }

    public void setSparksEnabled(Boolean sparksEnabled) {
        this.sparksEnabled = sparksEnabled;
    }

    public Boolean getKontagentEnabled() {
        return kontagentEnabled;
    }

    public void setKontagentEnabled(Boolean kontagentEnabled) {
        this.kontagentEnabled = kontagentEnabled;
    }

    public Boolean getMqtt() {
        return mqtt;
    }

    public void setMqtt(Boolean mqtt) {
        this.mqtt = mqtt;
    }

    public Boolean getTinderSparks() {
        return tinderSparks;
    }

    public void setTinderSparks(Boolean tinderSparks) {
        this.tinderSparks = tinderSparks;
    }

    public Integer getMomentsInterval() {
        return momentsInterval;
    }

    public void setMomentsInterval(Integer momentsInterval) {
        this.momentsInterval = momentsInterval;
    }

    public Boolean getFetchConnections() {
        return fetchConnections;
    }

    public void setFetchConnections(Boolean fetchConnections) {
        this.fetchConnections = fetchConnections;
    }

    public Boolean getPlus() {
        return plus;
    }

    public void setPlus(Boolean plus) {
        this.plus = plus;
    }
}
