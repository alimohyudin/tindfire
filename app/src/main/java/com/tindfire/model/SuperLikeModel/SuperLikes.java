package com.tindfire.model.SuperLikeModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vcareall on 14/2/17.
 */

public class SuperLikes {

    @SerializedName("remaining")
    @Expose
    private Integer remaining;
    @SerializedName("alc_remaining")
    @Expose
    private Integer alcRemaining;
    @SerializedName("new_alc_remaining")
    @Expose
    private Integer newAlcRemaining;
    @SerializedName("allotment")
    @Expose
    private Integer allotment;
    @SerializedName("superlike_refresh_amount")
    @Expose
    private Integer superlikeRefreshAmount;
    @SerializedName("superlike_refresh_interval")
    @Expose
    private Integer superlikeRefreshInterval;
    @SerializedName("superlike_refresh_interval_unit")
    @Expose
    private String superlikeRefreshIntervalUnit;
    @SerializedName("resets_at")
    @Expose
    private String resetsAt;

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    public Integer getAlcRemaining() {
        return alcRemaining;
    }

    public void setAlcRemaining(Integer alcRemaining) {
        this.alcRemaining = alcRemaining;
    }

    public Integer getNewAlcRemaining() {
        return newAlcRemaining;
    }

    public void setNewAlcRemaining(Integer newAlcRemaining) {
        this.newAlcRemaining = newAlcRemaining;
    }

    public Integer getAllotment() {
        return allotment;
    }

    public void setAllotment(Integer allotment) {
        this.allotment = allotment;
    }

    public Integer getSuperlikeRefreshAmount() {
        return superlikeRefreshAmount;
    }

    public void setSuperlikeRefreshAmount(Integer superlikeRefreshAmount) {
        this.superlikeRefreshAmount = superlikeRefreshAmount;
    }

    public Integer getSuperlikeRefreshInterval() {
        return superlikeRefreshInterval;
    }

    public void setSuperlikeRefreshInterval(Integer superlikeRefreshInterval) {
        this.superlikeRefreshInterval = superlikeRefreshInterval;
    }

    public String getSuperlikeRefreshIntervalUnit() {
        return superlikeRefreshIntervalUnit;
    }

    public void setSuperlikeRefreshIntervalUnit(String superlikeRefreshIntervalUnit) {
        this.superlikeRefreshIntervalUnit = superlikeRefreshIntervalUnit;
    }

    public String getResetsAt() {
        return resetsAt;
    }

    public void setResetsAt(String resetsAt) {
        this.resetsAt = resetsAt;
    }

    @Override
    public String toString() {
        return "SuperLikes{" +
                "alcRemaining=" + alcRemaining +
                ", remaining=" + remaining +
                ", newAlcRemaining=" + newAlcRemaining +
                ", allotment=" + allotment +
                ", superlikeRefreshAmount=" + superlikeRefreshAmount +
                ", superlikeRefreshInterval=" + superlikeRefreshInterval +
                ", superlikeRefreshIntervalUnit='" + superlikeRefreshIntervalUnit + '\'' +
                ", resetsAt='" + resetsAt + '\'' +
                '}';
    }
}
