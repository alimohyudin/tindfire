package com.tindfire.model.GetMatchModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vcareall on 30/12/16.
 */

public class GetMatchExample {

    @SerializedName("matches")
    @Expose
    private List<GetMatchMatch> matches = null;
    @SerializedName("blocks")
    @Expose
    private List<String> blocks = null;
    @SerializedName("last_activity_date")
    @Expose
    private String lastActivityDate;

    public List<GetMatchMatch> getMatches() {
        return matches;
    }

    public void setMatches(List<GetMatchMatch> matches) {
        this.matches = matches;
    }

    public List<String> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<String> blocks) {
        this.blocks = blocks;
    }



    public String getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(String lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    @Override
    public String toString() {
        return "GetMatchExample{" +
                "blocks=" + blocks +
                ", matches=" + matches.toString()+
                ", lastActivityDate='" + lastActivityDate + '\'' +
                '}';
    }
}
