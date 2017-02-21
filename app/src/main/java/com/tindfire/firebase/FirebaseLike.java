package com.tindfire.firebase;

/**
 * Created by vcareall on 21/2/17.
 */

public class FirebaseLike {
    public String userName;
    public String imageUrl;
    public String userID;

    public FirebaseLike (){

    }
    public FirebaseLike (String userID,String userName,String imageUrl){
        this.userID=userID;
        this.userName=userName;
        this.imageUrl=imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }
}
