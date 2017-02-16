package com.tindfire.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tindfire.R;
import com.tindfire.apiClient.TinderAPiClient;
import com.tindfire.apiClient.TinderAPiInterface;
import com.tindfire.model.FacebookFriendsProfileModel.FacebookFriendsProfileExample;
import com.tindfire.model.FacebookFriendsProfileModel.FacebookFriendsProfileResults;
import com.tindfire.model.LikeResponce.LikeResponceExample;
import com.tindfire.model.LikeResponce.Match;
import com.tindfire.model.SuperLikeModel.SuperLikeExample;
import com.tindfire.preference.PreferenceManager;
import com.tindfire.util.Constants;
import com.tindfire.util.Utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vcareall on 16/2/17.
 */

public class FacebookFriendsProfileActivity extends AppCompatActivity {
    private static final String TAG=FacebookFriendsProfileActivity.class.getSimpleName();
    private Context context;
    private View headerLayout;
    private Toolbar toolbar;
    private TextView titleView,userProfileName,userAge,distance,pingtime,status;
    private ImageView profileImage,mSideBar;
    String profileId="";
    private ImageView like_rl,superLike_rl;
    private ProgressDialog progressDialog;
    private PreferenceManager mPref;
    private RelativeLayout addRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_friends_profile);
        context=this;
        initActionBar();
        mPref=PreferenceManager.getInstatnce(context);
        Bundle intent=getIntent().getExtras();
        if(intent!=null){
            profileId=intent.getString(Constants.PROFILE_ID);
        }
        init();
        if(Utility.isConnectingToInternet(context)){
            if(!profileId.equalsIgnoreCase("")){
              getProfileOfUser(profileId);
            }

        }else{
            Utility.showMessage(context,Constants.NO_INTERNET_CONNECTION);
        }
        addListner();

    }



    private void initActionBar(){
        headerLayout= LayoutInflater.from(context).inflate(R.layout.action_bar,null);
        toolbar=(Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setCustomView(headerLayout);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            toolbar.setContentInsetsAbsolute(0,0);
            toolbar.setContentInsetsRelative(0,0);
        }
    }
    private void init(){
        mSideBar=(ImageView)toolbar.findViewById(R.id.side_bar);
        mSideBar.setVisibility(View.INVISIBLE);
        titleView=(TextView)toolbar.findViewById(R.id.titleView);
        titleView.setText(getResources().getText(R.string.facebook_friends));
        profileImage=(ImageView)findViewById(R.id.profileImage);
        userProfileName=(TextView)findViewById(R.id.userProfileName);
        userAge=(TextView)findViewById(R.id.userAge);
        distance=(TextView)findViewById(R.id.distance);
        pingtime=(TextView)findViewById(R.id.pingtime);
        status=(TextView)findViewById(R.id.status);
        like_rl=(ImageView) findViewById(R.id.like_rl);
        superLike_rl=(ImageView) findViewById(R.id.superLike_rl);
        addRelativeLayout=(RelativeLayout)findViewById(R.id.addRelative);
        addRelativeLayout.addView(MyAdmovAds.loadAdmodAd(context));
    }
    private void addListner(){
        like_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utility.isConnectingToInternet(context)){
                    showLikeDailog();
                }else{
                    Utility.showMessage(context,Constants.NO_INTERNET_CONNECTION);
                }
            }
        });
        superLike_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utility.isConnectingToInternet(context)){
                    showSuperLikeDailog();
                }else{
                    Utility.showMessage(context,Constants.NO_INTERNET_CONNECTION);
                }
            }
        });
    }
    private void showSuperLikeDailog() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title(Constants.SUPERLIKE_TITLE)
                .content(Constants.SUPERLIKE_LIST_USER)
                .positiveText(Constants.AGREES)
                .negativeText(Constants.DISAGRESS);
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                if (Utility.isConnectingToInternet(context)) {

                    dialog.dismiss();
                    superLike_rl.setImageResource(R.mipmap.superlike_active);
                    hitSuperLikeAPi();
                } else {
                    Utility.showMessage(context, Constants.NO_INTERNET_CONNECTION);
                }

            }
        });
        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });
        MaterialDialog dialog = builder.build();
        dialog.show();

    }
    private void hitSuperLikeAPi(){
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(Constants.PLEASE_WAIT);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        TinderAPiInterface tinderAPiInterface=TinderAPiClient.getCLient().create(TinderAPiInterface.class);
        Call<SuperLikeExample> superLikeExampleCall = tinderAPiInterface.getSuperLikeExampleCall(mPref.getToken(), profileId);

        superLikeExampleCall.enqueue(new Callback<SuperLikeExample>() {
            @Override
            public void onResponse(Call<SuperLikeExample> call, Response<SuperLikeExample> response) {
                progressDialog.dismiss();
                try {
                    Log.d("ANdroid :", "ActivityProfile :" + response.body().toString());
                    if(response.body().getStatus()==200){

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuperLikeExample> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
    private void showLikeDailog() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title(Constants.LIKE_TITLE)
                .content(Constants.LIKE_LIST_USER)
                .positiveText(Constants.AGREES)
                .negativeText(Constants.DISAGRESS);
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                if (Utility.isConnectingToInternet(context)) {

                    dialog.dismiss();
                    like_rl.setImageResource(R.mipmap.profile_like_btn);
                    hitLikeAPi();
                } else {
                    Utility.showMessage(context, Constants.NO_INTERNET_CONNECTION);
                }

            }
        });
        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });
        MaterialDialog dialog = builder.build();
        dialog.show();

    }
    private void getProfileOfUser(String profileId) {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(Constants.PLEASE_WAIT);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        TinderAPiInterface tinderAPiInterface= TinderAPiClient.getCLient().create(TinderAPiInterface.class);
        Call<FacebookFriendsProfileExample> facebookFriendsProfileExampleCall = tinderAPiInterface.getFacebookFriendsProfileExampleCall(mPref.getToken(),profileId);

        facebookFriendsProfileExampleCall.enqueue(new Callback<FacebookFriendsProfileExample>() {
            @Override
            public void onResponse(Call<FacebookFriendsProfileExample> call, Response<FacebookFriendsProfileExample> response) {
                progressDialog.dismiss();
                try {
                    if (response != null) {
                        Log.d("ANdroid :", "facebook_Friends :" + response.body().toString());
                        if (response.body().getStatus() == Constants.STATUS_200) {
                            FacebookFriendsProfileResults facebookFriendsProfileResults= response.body().getResults();
                            userProfileName.setText(facebookFriendsProfileResults.getName()+"");
                            if(facebookFriendsProfileResults.getPingTime().trim().length()>0){
                                splitePingTime(facebookFriendsProfileResults.getPingTime());
                            }
                            if(facebookFriendsProfileResults.getBirthDate().trim().length()>0){
                                spliteDateAndTime(facebookFriendsProfileResults.getBirthDate());
                            }
                            try{
                                if(facebookFriendsProfileResults.getDistanceMi()!=0){
                                    if(mPref.getButtonRadio().equalsIgnoreCase(Constants.DISTANCE_KM)){
                                        int kilometerDist= (int) (facebookFriendsProfileResults.getDistanceMi()*1.609344);
                                        distance.setText(kilometerDist +" " +"km away");
                                        Log.d("Android :","kilometerDist :" +kilometerDist);
                                    }else{
                                        distance.setText(facebookFriendsProfileResults.getDistanceMi() +" " +"miles away");
                                    }
                                }else{
                                    distance.setText("");
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            if(facebookFriendsProfileResults.getBio().trim().length()>0){
                                status.setText(facebookFriendsProfileResults.getBio());
                            }
                            if(facebookFriendsProfileResults.getPhotos().size()>0){
                                String userImage=facebookFriendsProfileResults.getPhotos().get(0).getUrl();
                                if(!userImage.equalsIgnoreCase("")){
                                    Glide.with(context).load(userImage)
                                            .thumbnail(0.5f)
                                            .crossFade()
                                            .placeholder(R.mipmap.app_icon)
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .into(profileImage);
                                }
                            }

                        } else {

                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<FacebookFriendsProfileExample> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }
    private void splitePingTime(String recomondationnPingTime) {
        try{
            String spliteTimeWithT[]=recomondationnPingTime.split("T");
            Log.d("Android :","spliteTimeWithT[1] "+spliteTimeWithT[1]);
            Log.d("Android :","spliteTimeWithT[0] "+spliteTimeWithT[0]);
            String splTimeWithDot[]=spliteTimeWithT[1].split("\\.");
            Log.d("Android :","splTimeWithDot[0] "+splTimeWithDot[0]);
            totalTime(spliteTimeWithT[0],splTimeWithDot[0]);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void totalTime(String datestring, String timeSTring) {
        String dateStart = datestring+" "+timeSTring;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateEnd = new Date();
        System.out.println(dateFormat.format(dateEnd));
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Date d1 = null;
        try {
            d1 = format.parse(dateStart);

        } catch (Exception  e) {
            e.printStackTrace();
        }
        long diff = dateEnd.getTime() - d1.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        int diffInDays = (int) ((dateEnd.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));

        if (diffInDays > 1) {
            System.err.println("Difference in number of days (2) : " + diffInDays);
            pingtime.setText(" , "+diffInDays + " " +"days ago");
        } else if (diffHours > 24) {
            System.err.println(">24" +diffHours);
            pingtime.setText(" , "+diffHours +" " +"hours ago");
        } else if (diffMinutes >= 1) {
            System.err.println("minutes" +diffMinutes);
            pingtime.setText(" , "+diffMinutes +" " +"minutes ago");
        }

    }
    private void spliteDateAndTime(String recomondationnBirthDate) {
        try{
            String getDate[]=recomondationnBirthDate.split("T");
            Log.d("Android :","getDate[0] "+getDate[0]);
            if(getDate[0].length()>0){
                Date getDateFormate= Utility.convertStringDateToDateFormate(getDate[0]);
                Log.d("Android :","getDateFormate "+getDateFormate);
                int totalAge=Utility.getAge(getDateFormate);
                Log.d("Android :","totalAge "+totalAge);
                if(totalAge!=0){
                    userAge.setText("," + totalAge);
                }else{
                    userAge.setText("");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void hitLikeAPi() {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(Constants.PLEASE_WAIT);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        TinderAPiInterface tinderAPiInterface= TinderAPiClient.getCLient().create(TinderAPiInterface.class);
        Call<LikeResponceExample> likeResponceExampleCall=tinderAPiInterface.getLikeResponceExampleCall(mPref.getToken(),profileId);

        likeResponceExampleCall.enqueue(new Callback<LikeResponceExample>() {
            @Override
            public void onResponse(Call<LikeResponceExample> call, Response<LikeResponceExample> response) {
                progressDialog.dismiss();
                try{
                    Object obj=response.body().match();
                    Log.d("ANdroid :","obj :" +obj.toString());
                    if(obj instanceof Match){
                        Match match=(Match)response.body().match();
                        Log.d("Android :","match valuse :" +match.toString());
                        Utility.showMessage(context,"Match");
//                        finish();
                    }else if(obj instanceof Boolean){
                        Boolean b=(Boolean)response.body().match();
                        Log.d("Android :","boolean valuse :" +b);
                        Utility.showMessage(context,"Liked");
//                        finish();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<LikeResponceExample> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
