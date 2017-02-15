package com.tindfire.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tindfire.R;
import com.tindfire.adapter.FacebookFriendsAdapter;
import com.tindfire.apiClient.TinderAPiClient;
import com.tindfire.apiClient.TinderAPiInterface;
import com.tindfire.model.FacebookFriendsModel.FacebookFriendsExample;
import com.tindfire.model.FacebookFriendsModel.FacebookFriendsResult;
import com.tindfire.util.Constants;
import com.tindfire.util.Utility;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tindfire.activity.ActivityMain.categoryAdapter;

/**
 * Created by vcareall on 7/1/17.
 */

public class FacebookFriendsActivity extends AppCompatActivity {
    private Context context;
    private View headerLayout;
    private Toolbar toolbar;
    private TextView titleView,nolist;
    private RelativeLayout addRelativeLayout;
    private ImageView mSideBar;
    private RecyclerView facebookRecycle;
    private ProgressDialog progressDialog;
    private com.tindfire.preference.PreferenceManager mPref;
    private FacebookFriendsAdapter facebookFriendsAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_friends);
        context=this;
        mPref= com.tindfire.preference.PreferenceManager.getInstatnce(context);
        setActionBar();
        init();
        if(Utility.isConnectingToInternet(context)){
            getFacebookFriendsOfTinder();
        }else{
            facebookRecycle.setVisibility(View.GONE);
            nolist.setVisibility(View.VISIBLE);
            nolist.setText(Constants.NO_INTERNET_CONNECTION);
        }

    }
    public void setActionBar(){
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
    private void init() {
        mSideBar=(ImageView)toolbar.findViewById(R.id.side_bar);
        mSideBar.setVisibility(View.INVISIBLE);
        titleView=(TextView)toolbar.findViewById(R.id.titleView);
        titleView.setText(getResources().getText(R.string.facebook_friends));
        addRelativeLayout=(RelativeLayout)findViewById(R.id.addRelative);
        addRelativeLayout.addView(MyAdmovAds.loadAdmodAd(context));
        facebookRecycle=(RecyclerView)findViewById(R.id.facebook_recycle);
        nolist=(TextView)findViewById(R.id.nolist);
    }
    public void getFacebookFriendsOfTinder(){
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(Constants.PLEASE_WAIT);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        TinderAPiInterface tinderAPiInterface= TinderAPiClient.getCLient().create(TinderAPiInterface.class);
        Call<FacebookFriendsExample> facebookFriendsExampleCall = tinderAPiInterface.getFacebookFriendsExampleCall(mPref.getToken());

        facebookFriendsExampleCall.enqueue(new Callback<FacebookFriendsExample>() {
            @Override
            public void onResponse(Call<FacebookFriendsExample> call, Response<FacebookFriendsExample> response) {
                progressDialog.dismiss();
                try {
                    if (response != null) {
                        Log.d("ANdroid :", "facebook_Friends :" + response.body().toString());
                        if (response.body().getStatus() == Constants.STATUS_200) {
                            if (response.body().getResults() != null) {
                                    facebookRecycle.setVisibility(View.VISIBLE);
                                    nolist.setVisibility(View.GONE);
                                    List<FacebookFriendsResult> facebookFriendsResultList= response.body().getResults();
                                    facebookFriendsAdapter = new FacebookFriendsAdapter(FacebookFriendsActivity.this, context, facebookFriendsResultList);
                                    facebookRecycle.setLayoutManager(new LinearLayoutManager(context));
                                     facebookRecycle.setItemAnimator(new DefaultItemAnimator());
                                     facebookRecycle.setAdapter(facebookFriendsAdapter);

                            } else {
                                facebookRecycle.setVisibility(View.GONE);
                                nolist.setVisibility(View.VISIBLE);
                                nolist.setText(Constants.NO_LIST_AVAILABLE);
                            }
                        } else {
                            facebookRecycle.setVisibility(View.GONE);
                            nolist.setVisibility(View.VISIBLE);
                            nolist.setText(Constants.NO_LIST_AVAILABLE);
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<FacebookFriendsExample> call, Throwable t) {
                progressDialog.dismiss();
                facebookRecycle.setVisibility(View.GONE);
                nolist.setVisibility(View.VISIBLE);
                nolist.setText(Constants.NO_LIST_AVAILABLE);
            }
        });
    }
}
