package com.tindfire.preference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vcareall on 7/12/16.
 */
public class PreferenceManager {

    public static Context mContext;
    public static PreferenceManager preferenceManager;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    public PreferenceManager(){

    }
    public static PreferenceManager getInstatnce(Context context){
        if(preferenceManager==null)
            preferenceManager=new PreferenceManager();
            mContext=context;
            sharedPreferences=mContext.getSharedPreferences(PreferenceConstant.PREFERENCE_NAME,Context.MODE_PRIVATE);
            editor=sharedPreferences.edit();
            return preferenceManager;
    }
    public static SharedPreferences.Editor getEditor(){
        return editor;
    }
    //............IsFirsttime...............
    public static void removePref(){
        editor.clear();
        editor.commit();
    }

    /**
     *
     * @param token
     */
    public void setToken(String token){
        editor.putString(PreferenceConstant.TOKEN,token);
        editor.commit();
    }
    public String getToken(){
        return sharedPreferences.getString(PreferenceConstant.TOKEN,"");
    }
    /**
     *
     * @param firstTime
     */

    public void setFirstTime(boolean firstTime){
        editor.putBoolean(PreferenceConstant.FIRSTTIME,firstTime);
        editor.commit();
    }
    public boolean getFirstTime(){
        return sharedPreferences.getBoolean(PreferenceConstant.FIRSTTIME,true);
    }

    /**
     *
     * @param profileImage
     */

    public void setProfileImage(String profileImage){
        editor.putString(PreferenceConstant.PROFILEIMAGE,profileImage);
        editor.commit();
    }
    public String getProfileImage(){
        return sharedPreferences.getString(PreferenceConstant.PROFILEIMAGE,"");
    }

    /**
     *
     * @param userName
     */

    public void setUserName(String userName){
        editor.putString(PreferenceConstant.USERNAME,userName);
        editor.commit();
    }
    public String getUserName(){
        return sharedPreferences.getString(PreferenceConstant.USERNAME,"");
    }

    /**
     *
     * @param emailid
     */

    public void setEmailid(String emailid){
        editor.putString(PreferenceConstant.EMAILID,emailid);
        editor.commit();
    }
    public String getEmailid(){
        return sharedPreferences.getString(PreferenceConstant.EMAILID,"");
    }

    /**
     *
     * @param socialId
     */

    public void setSocialId(String socialId){
        editor.putString(PreferenceConstant.SOCIALID,socialId);
        editor.commit();
    }
    public String getSocialId(){
        return sharedPreferences.getString(PreferenceConstant.SOCIALID,"");
    }



}
