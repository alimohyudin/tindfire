package com.tindfire.model.AuthModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vcareall on 27/12/16.
 */
public class AuthResponceJob {
    @SerializedName("company")
    @Expose
    private AuthResponceCompany company;
    @SerializedName("title")
    @Expose
    private AuthResponceTitle title;

    public AuthResponceCompany getCompany() {
        return company;
    }

    public void setCompany(AuthResponceCompany company) {
        this.company = company;
    }

    public AuthResponceTitle getTitle() {
        return title;
    }

    public void setTitle(AuthResponceTitle title) {
        this.title = title;
    }
}
