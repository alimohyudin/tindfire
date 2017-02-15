package com.tindfire.model.FacebookFriendsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vcareall on 15/2/17.
 */

public class FacebookFriendsPhoto {
    @SerializedName("processedFiles")
    @Expose
    private List<FacebookFriendsProcessedFile> processedFiles = null;

    public List<FacebookFriendsProcessedFile> getProcessedFiles() {
        return processedFiles;
    }

    public void setProcessedFiles(List<FacebookFriendsProcessedFile> processedFiles) {
        this.processedFiles = processedFiles;
    }

    @Override
    public String toString() {
        return "FacebookFriendsPhoto{" +
                "processedFiles=" + processedFiles.toString()+
                '}';
    }
}
