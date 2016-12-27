package com.tindfire.model.AuthModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vcareall on 27/12/16.
 */
public class AuthResponceExample {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("user")
    @Expose
    private AuthResponceUser user;
    @SerializedName("versions")
    @Expose
    private AuthResponceVersions versions;
    @SerializedName("globals")
    @Expose
    private AuthResponceGlobes globals;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuthResponceUser getUser() {
        return user;
    }

    public void setUser(AuthResponceUser user) {
        this.user = user;
    }

    public AuthResponceVersions getVersions() {
        return versions;
    }

    public void setVersions(AuthResponceVersions versions) {
        this.versions = versions;
    }

    public AuthResponceGlobes getGlobals() {
        return globals;
    }

    public void setGlobals(AuthResponceGlobes globals) {
        this.globals = globals;
    }

    @Override
    public String toString() {
        return "AuthResponceExample{" +
                "globals=" + globals.toString() +
                ", token='" + token + '\'' +
                ", user=" + user.toString() +
                ", versions=" + versions.toString() +
                '}';
    }
}
