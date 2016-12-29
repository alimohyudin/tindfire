package com.tindfire.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.login.LoginManager;
import com.tindfire.R;
import com.tindfire.adapter.CategoryAdapter;
import com.tindfire.apiClient.TinderAPiClient;
import com.tindfire.apiClient.TinderAPiInterface;
import com.tindfire.model.LikeResponce.LikeResponceExample;
import com.tindfire.model.RecomondationModel.RecomondationResponce;
import com.tindfire.model.RecomondationModel.RecomondationResult;
import com.tindfire.model.RecomondationModel.RecomondationnPhoto;
import com.tindfire.preference.PreferenceConstant;
import com.tindfire.preference.PreferenceManager;
import com.tindfire.util.Constants;
import com.tindfire.util.Utility;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    private ProgressDialog progressDialog;
    private PreferenceManager mPref;
    private TextView mNolist;
    private ImageView mSideBar;
    private DrawerLayout drawer;
    private ImageView mLogoutIv;
    private LinearLayout mLogoutLL;
    private TextView mUserNameTv;
    private ImageView mUserProfileIv;
    private TextView mEmailIdTv;
    @Bind(R.id.like_bt_rl1)ImageView mLikeBtRl1;
    private ProgressDialog progressDialogs;
    private List<RecomondationResult> recomondationResultList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);
        context=this;
        mPref=PreferenceManager.getInstatnce(context);
        ButterKnife.bind(this);
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

    @OnClick(R.id.like_bt_rl1) void autoLikeClick(){
        AutoLikeClickApi();
    }

    private void AutoLikeClickApi() {
        MaterialDialog.Builder builder=new MaterialDialog.Builder(this)
                .title(Constants.AUTO_LIKE_TITLE)
                .content(Constants.AUTO_LIKE_CONTENT)
                .positiveText(Constants.AGREES)
                .negativeText(Constants.DISAGRESS);
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                if(Utility.isConnectingToInternet(context)){

                    try{
                        List<String> getAllIdOfUser=categoryAdapter.getAllId();
                        dialog.dismiss();
                        if(getAllIdOfUser!=null){
                            progressDialogs=new ProgressDialog(context);
                            progressDialogs.setMessage(Constants.PLEASE_WAIT);
                            progressDialogs.setCanceledOnTouchOutside(false);
                            progressDialogs.setCancelable(false);
                            progressDialogs.show();

                            for(int i=0;i<getAllIdOfUser.size();i++){
                                Log.d("Android :","getAllIdOfUser size  id:" +getAllIdOfUser.get(i));
                                if(i<getAllIdOfUser.size()){
                                    hitLikeAPi(getAllIdOfUser.get(i),i);
                                    dialog.dismiss();
                                }else{

                                }

                            }
                            Log.d("Android :","getAllIdOfUser size :" +getAllIdOfUser.size());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else{
                    Utility.showMessage(context,Constants.NO_INTERNET_CONNECTION);
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
    private void hitLikeAPi(String id, final int i) {

        TinderAPiInterface tinderAPiInterface= TinderAPiClient.getCLient().create(TinderAPiInterface.class);
        Call<LikeResponceExample> likeResponceExampleCall=tinderAPiInterface.getLikeResponceExampleCall(mPref.getToken(),id);

        likeResponceExampleCall.enqueue(new Callback<LikeResponceExample>() {
            @Override
            public void onResponse(Call<LikeResponceExample> call, Response<LikeResponceExample> response) {
                progressDialogs.dismiss();
                try{
                    Log.d("ANdroid :","ActivityProfile :" +response.body().toString());
                    if(response.body().match()==Constants.MATCH_FALCE){

                        recomondationResultList.get(i).setLike(true);
                        categoryAdapter.notifyDataSetChanged();
                    }else{

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<LikeResponceExample> call, Throwable t) {
                progressDialogs.dismiss();
            }
        });

    }

    private void hitGetRecsApi() {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(Constants.PLEASE_WAIT);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        TinderAPiInterface tinderAPiInterface= TinderAPiClient.getCLient().create(TinderAPiInterface.class);
        Log.d("Android :","mPref.getToken() :" +mPref.getToken());

        Call<RecomondationResponce> recomondationResponceCall=tinderAPiInterface.getrRecomondationResponceCall(mPref.getToken());
        recomondationResponceCall.enqueue(new Callback<RecomondationResponce>() {
            @Override
            public void onResponse(Call<RecomondationResponce> call, Response<RecomondationResponce> response) {
                progressDialog.cancel();
                try{
                    if(response!=null){
                        Log.d("Android :","tostringvalue :" +response.body().toString());
                            if(Constants.STATUS_200==response.body().getStatus()){
                                mRecycleView.setVisibility(View.VISIBLE);
                                mLikeRejectBtLayer.setVisibility(View.VISIBLE);
                                mNolist.setVisibility(View.GONE);
                                recomondationResultList=response.body().getResults();
                                mRecycleView.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),3);
                                mRecycleView.setLayoutManager(layoutManager);
                                categoryAdapter = new CategoryAdapter(ActivityMain.this,context,recomondationResultList);
                                mRecycleView.setAdapter(categoryAdapter);

                            }else{

                            }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    LoginManager.getInstance().logOut();
                    mPref.editor.remove(PreferenceConstant.FIRSTTIME).commit();
                    Intent intents = new Intent(ActivityMain.this, LoginActivity.class);
                    startActivity(intents);
                    finish();
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
        mSideBar=(ImageView)toolbar.findViewById(R.id.side_bar);
        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        titleView=(TextView)toolbar.findViewById(R.id.titleView);
        settingImage=(ImageView)toolbar.findViewById(R.id.settinfImage);
        titleView.setText(getResources().getText(R.string.tindfire));
        settingImage.setBackground(getResources().getDrawable(R.mipmap.settings));
        settingImage.setVisibility(View.INVISIBLE);
        mRecycleView=(RecyclerView)findViewById(R.id.recycle_view);
        mLikeRejectBtLayer=(RelativeLayout)findViewById(R.id.like_reject_bt_layerRl);
        mRejectBtRl1=(ImageView)findViewById(R.id.reject_bt_rl1);

        mNolist=(TextView)findViewById(R.id.nolist);
        mRejectButtonLayer=(RelativeLayout)findViewById(R.id.reject_button_layerRl);
        mRejectBtnLayerIv=(ImageView)findViewById(R.id.reject_btn_layerIv);
        mLogoutIv=(ImageView)findViewById(R.id.logoutIv);
        mLogoutLL=(LinearLayout)findViewById(R.id.logoutLL);
        mUserNameTv=(TextView)findViewById(R.id.user_name_tv);
        mEmailIdTv=(TextView)findViewById(R.id.email_id_tv);
        mUserProfileIv=(ImageView)findViewById(R.id.user_profile_iv);
        if(!mPref.getUserName().equalsIgnoreCase("")){
            mUserNameTv.setText(mPref.getUserName());
        }else{
            mUserNameTv.setVisibility(View.GONE);
        }
        if(!mPref.getProfileImage().equalsIgnoreCase("")){
            Glide.with(context).load(mPref.getProfileImage())
                    .thumbnail(0.5f)
                    .crossFade()
                    .placeholder(R.mipmap.app_icon)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mUserProfileIv);
        }else{
            mUserProfileIv.setBackgroundResource(R.mipmap.app_icon);
        }
        if(!mPref.getEmailid().equalsIgnoreCase("")){
            mEmailIdTv.setText(mPref.getEmailid());
        }else{
            mEmailIdTv.setVisibility(View.GONE);
        }




    }

    private void clickListner() {
        mSideBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
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
                if(Utility.isConnectingToInternet(context)){
                    hitAgainGetRecsApi();
                }else{
                    Utility.showMessage(context, Constants.NO_INTERNET_CONNECTION);
                }

            }
        });
        mLogoutIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    removeDataFromPref();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        mLogoutLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    removeDataFromPref();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }

    private void hitAgainGetRecsApi() {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(Constants.PLEASE_WAIT);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        TinderAPiInterface tinderAPiInterface= TinderAPiClient.getCLient().create(TinderAPiInterface.class);
        Log.d("Android :","mPref.getToken() :" +mPref.getToken());

        Call<RecomondationResponce> recomondationResponceCall=tinderAPiInterface.getrRecomondationResponceCall(mPref.getToken());
        recomondationResponceCall.enqueue(new Callback<RecomondationResponce>() {
            @Override
            public void onResponse(Call<RecomondationResponce> call, Response<RecomondationResponce> response) {
                progressDialog.cancel();
                try{
                    if(response!=null){
                        Log.d("Android :","tostringvalue :" +response.body().toString());
                        if(Constants.STATUS_200==response.body().getStatus()){
                            recomondationResultList.clear();
                             recomondationResultList=response.body().getResults();
                            Log.d("Android :","size of again recom :" +recomondationResultList.size());
                            mRecycleView.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),3);
                            mRecycleView.setLayoutManager(layoutManager);
                            categoryAdapter = new CategoryAdapter(ActivityMain.this,context,recomondationResultList);
                            mRecycleView.setAdapter(categoryAdapter);
                        }else{

                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    LoginManager.getInstance().logOut();
                    mPref.editor.remove(PreferenceConstant.FIRSTTIME).commit();
                    Intent intents = new Intent(ActivityMain.this, LoginActivity.class);
                    startActivity(intents);
                    finish();
                }
            }
            @Override
            public void onFailure(Call<RecomondationResponce> call, Throwable t) {
                progressDialog.cancel();
            }
        });

    }

    public void notingClick(View view){

    }
    public void removeDataFromPref(){
        MaterialDialog.Builder builder=new MaterialDialog.Builder(this)
                .title(Constants.LOGOUT)
                .content(Constants.DO_YOU_WANT)
                .positiveText(Constants.AGREES)
                .negativeText(Constants.DISAGRESS);
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                LoginManager.getInstance().logOut();
                mPref.editor.remove(PreferenceConstant.FIRSTTIME).commit();
                Intent intents = new Intent(ActivityMain.this, LoginActivity.class);
                startActivity(intents);
                finish();
                dialog.dismiss();

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

    public void clickImage(int position,
                           List<RecomondationnPhoto> recomondationnPhotoList,
                           String name, String bio,String pingtime, String birthDate,int DistanceMi,String Id) {
        try{
            Intent intent=new Intent(ActivityMain.this,ActivtiyProfile.class);
            intent.putExtra(Constants.RECOM_PHO_LIST, (Serializable) recomondationnPhotoList);
            intent.putExtra(Constants.RECOM_Name, name);
            intent.putExtra(Constants.RECOM_BIO, bio);
            intent.putExtra(Constants.RECOM_PINGTIME, pingtime);
            intent.putExtra(Constants.RECOM_BIRTHDATE, birthDate);
            intent.putExtra(Constants.RECOM_DISTANCEMIL, DistanceMi);
            intent.putExtra(Constants.RECOM_ID, Id);
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
            } else {
                super.onBackPressed();
            }
        }
    }
}
