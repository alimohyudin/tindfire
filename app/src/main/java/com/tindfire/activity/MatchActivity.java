package com.tindfire.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tindfire.R;
import com.tindfire.adapter.MatchAdapter;
import com.tindfire.apiClient.TinderAPiClient;
import com.tindfire.apiClient.TinderAPiInterface;
import com.tindfire.model.GetMatchModel.AuthMatch;
import com.tindfire.model.GetMatchModel.GetMatchExample;
import com.tindfire.model.GetMatchModel.GetMatchMatch;
import com.tindfire.model.GetMatchModel.GetMatchPhoto;
import com.tindfire.preference.PreferenceManager;
import com.tindfire.util.Constants;
import com.tindfire.util.Utility;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vcareall on 30/12/16.
 */

public class MatchActivity extends AppCompatActivity {
    private Context context;
    private PreferenceManager mPref;
    private View headerLayout;
    private Toolbar toolbar;
    private ImageView mSideBar;
    private TextView titleView;
    private RecyclerView matchRLView;
    private TextView mNolist;
    private ProgressDialog progressDialog;
    private MatchAdapter matchAdapter;
    private RelativeLayout addRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        context=this;
        mPref= PreferenceManager.getInstatnce(context);
        setActionBar();
        init();
        clickListner();
        if(Utility.isConnectingToInternet(context)){
            hitGetMatchesApi();
        }else{
            Utility.showMessage(context, Constants.NO_INTERNET_CONNECTION);
            matchRLView.setVisibility(View.GONE);
            mNolist.setVisibility(View.VISIBLE);
            mNolist.setText(Constants.NO_INTERNET_CONNECTION);
        }
        try{
            MyAdmovAds.loadintertitisalAdmodAd(context);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void hitGetMatchesApi() {
        AuthMatch authMatch=new AuthMatch.Builder()
                .facebookId(mPref.getSocialId()).build();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(Constants.PLEASE_WAIT);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        TinderAPiInterface tinderAPiInterface= TinderAPiClient.getCLient().create(TinderAPiInterface.class);
        Call<GetMatchExample> getMatchExampleCall=tinderAPiInterface.getMatchExampleCall(mPref.getToken(),authMatch);
        getMatchExampleCall.enqueue(new Callback<GetMatchExample>() {
            @Override
            public void onResponse(Call<GetMatchExample> call, Response<GetMatchExample> response) {
                progressDialog.cancel();
                try{
                    Log.d("Android :","responce GetMatchExample :" +response.body().toString());
                    if(response.body().getMatches()!=null){
                        List<GetMatchMatch> getMatchMatchList=response.body().getMatches();
                        if(getMatchMatchList.size()>0){
                            matchRLView.setVisibility(View.VISIBLE);
                            mNolist.setVisibility(View.GONE);
                            matchRLView.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),2);
                            matchRLView.setLayoutManager(layoutManager);
                            matchAdapter = new MatchAdapter(MatchActivity.this,context,getMatchMatchList);
                            matchRLView.setAdapter(matchAdapter);
                        }else{
                            matchRLView.setVisibility(View.GONE);
                            mNolist.setVisibility(View.VISIBLE);
                            mNolist.setText(Constants.NO_MATCH_AVAILABLE);
                        }
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<GetMatchExample> call, Throwable t) {
                progressDialog.cancel();
                Log.d("Android :","onfailure :"+t.getMessage());
                matchRLView.setVisibility(View.GONE);
                mNolist.setVisibility(View.VISIBLE);
                mNolist.setText(Constants.NO_LIST_AVAILABLE);
            }
        });

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

    private void clickListner() {

    }

    private void init() {
        mSideBar=(ImageView)toolbar.findViewById(R.id.side_bar);
        mSideBar.setVisibility(View.INVISIBLE);
        titleView=(TextView)toolbar.findViewById(R.id.titleView);
        titleView.setText(getResources().getText(R.string.match));
        matchRLView=(RecyclerView)findViewById(R.id.match_RLView);
        mNolist=(TextView)findViewById(R.id.nolist);
        addRelativeLayout=(RelativeLayout)findViewById(R.id.addRelative);
        addRelativeLayout.addView(MyAdmovAds.loadAdmodAd(context));
    }

    public void setMatch(int position,
                         List<GetMatchPhoto> getMatchPhotoList,
                         String name, String bio,
                         String pingTime, String birthDate) {
        try{
            Intent intent=new Intent(MatchActivity.this,MatchProfile.class);
            intent.putExtra(Constants.RECOM_PHO_LIST, (Serializable) getMatchPhotoList);
            intent.putExtra(Constants.RECOM_Name, name);
            intent.putExtra(Constants.RECOM_BIO, bio);
            intent.putExtra(Constants.RECOM_PINGTIME, pingTime);
            intent.putExtra(Constants.RECOM_BIRTHDATE, birthDate);
            intent.putExtra(Constants.RECOM_POSITION, position);
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
