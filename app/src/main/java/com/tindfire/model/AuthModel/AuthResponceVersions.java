package com.tindfire.model.AuthModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vcareall on 27/12/16.
 */
public class AuthResponceVersions {
    @SerializedName("active_text")
    @Expose
    private String activeText;
    @SerializedName("age_filter")
    @Expose
    private String ageFilter;
    @SerializedName("matchmaker")
    @Expose
    private String matchmaker;
    @SerializedName("trending")
    @Expose
    private String trending;
    @SerializedName("trending_active_text")
    @Expose
    private String trendingActiveText;

    public String getActiveText() {
        return activeText;
    }

    public void setActiveText(String activeText) {
        this.activeText = activeText;
    }

    public String getAgeFilter() {
        return ageFilter;
    }

    public void setAgeFilter(String ageFilter) {
        this.ageFilter = ageFilter;
    }

    public String getMatchmaker() {
        return matchmaker;
    }

    public void setMatchmaker(String matchmaker) {
        this.matchmaker = matchmaker;
    }

    public String getTrending() {
        return trending;
    }

    public void setTrending(String trending) {
        this.trending = trending;
    }

    public String getTrendingActiveText() {
        return trendingActiveText;
    }

    public void setTrendingActiveText(String trendingActiveText) {
        this.trendingActiveText = trendingActiveText;
    }
}
