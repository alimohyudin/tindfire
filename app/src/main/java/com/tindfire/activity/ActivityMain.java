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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tindfire.R;
import com.tindfire.adapter.CategoryAdapter;
import com.tindfire.apiClient.TinderAPiClient;
import com.tindfire.apiClient.TinderAPiInterface;
import com.tindfire.model.RecomondationModel.RecomondationResponce;
import com.tindfire.model.RecomondationModel.RecomondationResult;
import com.tindfire.preference.PreferenceManager;
import com.tindfire.util.Constants;
import com.tindfire.util.Utility;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vcareall on 7/12/16.
 */
public class ActivityMain extends AppCompatActivity {
    private Context context;
    private static final String TAG=ActivityMain.class.getSimpleName();
    private View headerLayout;
    private Toolbar toolbar;
    TextView titleView;
    private ImageView settingImage;
    private RecyclerView mRecycleView;
    private CategoryAdapter categoryAdapter;
    private RelativeLayout mLikeRejectBtLayer;
    private RelativeLayout mRejectButtonLayer;
    private ImageView mRejectBtnLayerIv;
    private ImageView mRejectBtRl1;
    private ImageView mLikeBtRl1;
    private ProgressDialog progressDialog;
    private PreferenceManager mPref;
    private TextView mNolist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        mPref=PreferenceManager.getInstatnce(context);
        setActionBar();
        init();
        clickListner();
        if(Utility.isConnectingToInternet(context)){
            hitGetRecsApi();
        }else{
            Utility.showMessage(context, Constants.NO_INTERNET_CONNECTION);
            mRecycleView.setVisibility(View.GONE);
            mLikeRejectBtLayer.setVisibility(View.GONE);
            mNolist.setVisibility(View.VISIBLE);
            mNolist.setText(Constants.NO_INTERNET_CONNECTION);
        }

    }

    private void hitGetRecsApi() {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(Constants.PLEASE_WAIT);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        TinderAPiInterface tinderAPiInterface= TinderAPiClient.getCLient().create(TinderAPiInterface.class);

        Call<RecomondationResponce> recomondationResponceCall=tinderAPiInterface.getrRecomondationResponceCall(mPref.getToken());
        recomondationResponceCall.enqueue(new Callback<RecomondationResponce>() {
            @Override
            public void onResponse(Call<RecomondationResponce> call, Response<RecomondationResponce> response) {
                progressDialog.cancel();
                try{
                    if(Constants.STATUS_200==response.body().getStatus()){
                        mRecycleView.setVisibility(View.VISIBLE);
                        mLikeRejectBtLayer.setVisibility(View.VISIBLE);
                        mNolist.setVisibility(View.GONE);
                        List<RecomondationResult> recomondationResultList=response.body().getResults();
                        mRecycleView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),3);
                        mRecycleView.setLayoutManager(layoutManager);
                        categoryAdapter = new CategoryAdapter(ActivityMain.this,context,recomondationResultList);
                        mRecycleView.setAdapter(categoryAdapter);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<RecomondationResponce> call, Throwable t) {
                progressDialog.cancel();
                mRecycleView.setVisibility(View.GONE);
                mLikeRejectBtLayer.setVisibility(View.GONE);
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

    private void init() {
        titleView=(TextView)toolbar.findViewById(R.id.titleView);
        settingImage=(ImageView)toolbar.findViewById(R.id.settinfImage);
        titleView.setText(getResources().getText(R.string.tindfire));
        settingImage.setBackground(getResources().getDrawable(R.mipmap.settings));
        mRecycleView=(RecyclerView)findViewById(R.id.recycle_view);
        mLikeRejectBtLayer=(RelativeLayout)findViewById(R.id.like_reject_bt_layerRl);
        mRejectBtRl1=(ImageView)findViewById(R.id.reject_bt_rl1);
        mLikeBtRl1=(ImageView)findViewById(R.id.like_bt_rl1);
        mNolist=(TextView)findViewById(R.id.nolist);
        mRejectButtonLayer=(RelativeLayout)findViewById(R.id.reject_button_layerRl);
        mRejectBtnLayerIv=(ImageView)findViewById(R.id.reject_btn_layerIv);


    }

    private void clickListner() {
        settingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityMain.this,ActivtiySetting.class));
            }
        });
        mRejectBtRl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRejectButtonLayer.setVisibility(View.VISIBLE);
                mLikeRejectBtLayer.setVisibility(View.GONE);
            }
        });
        mRejectBtnLayerIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRejectButtonLayer.setVisibility(View.GONE);
                mLikeRejectBtLayer.setVisibility(View.VISIBLE);
            }
        });
    }

    public void clickImage(int position) {
        startActivity(new Intent(ActivityMain.this,ActivtiyProfile.class));
    }
}
