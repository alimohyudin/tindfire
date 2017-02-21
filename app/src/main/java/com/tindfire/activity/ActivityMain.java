package com.tindfire.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
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
import com.dinuscxj.refresh.RecyclerRefreshLayout;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tindfire.R;
import com.tindfire.adapter.CategoryAdapter;
import com.tindfire.apiClient.TinderAPiClient;
import com.tindfire.apiClient.TinderAPiInterface;
import com.tindfire.firebase.FirebaseLike;
import com.tindfire.model.LikeResponce.LikeResponceExample;
import com.tindfire.model.PassModel.PassData;
import com.tindfire.model.RecomondationModel.RecomondationResponce;
import com.tindfire.model.RecomondationModel.RecomondationResult;
import com.tindfire.model.RecomondationModel.RecomondationnPhoto;
import com.tindfire.model.SuperLikeModel.SuperLikeExample;
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

//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

/**
 * Created by vcareall on 7/12/16.
 */
public class ActivityMain extends AppCompatActivity {
    private Context context;
    private static final String TAG = ActivityMain.class.getSimpleName();
    private View headerLayout;
    private Toolbar toolbar;
    TextView titleView;
    private ImageView settingImage;
    private RecyclerView mRecycleView;
    public static CategoryAdapter categoryAdapter;
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
    @Bind(R.id.like_bt_rl1)
    ImageView mLikeBtRl1;
    private ProgressDialog progressDialogs;
    private List<RecomondationResult> recomondationResultList;
    private LinearLayout mMatchll;
    private RecyclerRefreshLayout mRefreshLayout;
    private final RefreshEventDetector mRefreshEventDetector = new RefreshEventDetector();
    Boolean autoLike;
    private ContentLoadingProgressBar progress_bar;
    private RelativeLayout addRelativeLayout;
    private LinearLayout mAlreadyLikedMe, mFacebookFriends;
    private ImageView mTwitterLinkUv, mFacebookLinkIv;
    private LinearLayout mSettingLL;
    private LinearLayout mMessageLL;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private LinearLayout mSuperLikeMe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);
        Log.d("", "onCreate" + "onCreate");
        context = this;
        mPref = PreferenceManager.getInstatnce(context);
        ButterKnife.bind(this);
        setActionBar();
        init();
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            databaseReference= FirebaseDatabase.getInstance().getReference();
            firebaseUser=firebaseAuth.getCurrentUser();
            Log.d("Android :","firebasseuser.getuid " +firebaseUser.getUid());
            Log.d("Android :","firebasseuser.getEmail: " +firebaseUser.getEmail());
        }
        clickListner();
        if (Utility.isConnectingToInternet(context)) {
            hitGetRecsApi();
        } else {
            Utility.showMessage(context, Constants.NO_INTERNET_CONNECTION);
            mRecycleView.setVisibility(View.GONE);
            mLikeRejectBtLayer.setVisibility(View.GONE);
            mNolist.setVisibility(View.VISIBLE);
            mNolist.setText(Constants.NO_INTERNET_CONNECTION);
        }
        mRefreshLayout.setOnRefreshListener(mRefreshEventDetector);

        try {
            MyAdmovAds.loadintertitisalAdmodAd(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {

        super.onPause();
        Log.d("", "pause" + "pause");
    }

    @Override
    protected void onResume() {

        super.onResume();
        Log.d("", "onResume" + "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("", "onStop" + "onStop");

    }

    public void selectedLike(String id, int position,String username ,String imageUrl) {
        if (Utility.isConnectingToInternet(context)) {
            hitLikeAPi(id, position,username,imageUrl);


//            recomondationResultList.get(position).setLike(true);
//            createUser(id);
        } else {
            Utility.showMessage(context, Constants.NO_INTERNET_CONNECTION);
        }

    }

    private void saveDataInFireBase(String id, String username, String imageUrl) {
            Log.d("Android :" ,"firebaseUser.getUid() :" +firebaseUser.getUid());
           databaseReference.child(Constants.Like_of_+firebaseUser.getUid()).child(id).setValue(new FirebaseLike(id,username,imageUrl));
    }
    private void saveDataSuperLikeInFireBase(String id, String username, String imageUrl) {
        Log.d("Android :" ,"firebaseUser.getUid() :" +firebaseUser.getUid());
        databaseReference.child(Constants.SUPER_Like_of_+firebaseUser.getUid()).child(id).setValue(new FirebaseLike(id,username,imageUrl));
    }


//    private void createUser(String id) {
//        AlreadyLikeUser alreadyLikeUser = new AlreadyLikeUser();
//        alreadyLikeUser.setId(id);
//        String userId = mFirebaseDatabase.push().getKey();
//        mFirebaseDatabase.child(userId).setValue(alreadyLikeUser);
//        addUserChangeListener();
//
//    }

    private void addUserChangeListener() {

    }

    public void selectedDislike(String id, int position) {
        if (Utility.isConnectingToInternet(context)) {
            hitPassAPi(id, position);
//            recomondationResultList.get(position).setLike(false);
        } else {
            Utility.showMessage(context, Constants.NO_INTERNET_CONNECTION);
        }


    }
    public void selectedSuperLike(String id, int position,String username ,String imageUrl) {

        if (Utility.isConnectingToInternet(context)) {
            hitSuperLikeAPi(id, position,username,imageUrl);
//            recomondationResultList.get(position).setSuperlike(true);

        } else {
            Utility.showMessage(context, Constants.NO_INTERNET_CONNECTION);
        }

    }

    public void refershAgainRecsApi() {
        if (Utility.isConnectingToInternet(context)) {
            hitGetRecsApi();
        } else {
            Utility.showMessage(context, Constants.NO_INTERNET_CONNECTION);
        }
    }


    public class RefreshEventDetector implements RecyclerRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            if (Utility.isConnectingToInternet(context)) {
                try {
                    if (Utility.isConnectingToInternet(context)) {
                        mRefreshLayout.setRefreshing(true);
                        mRefreshLayout.setAnimateToRefreshDuration(2);
                        hitAgainGetRecsApi();
                    } else {
                        Utility.showMessage(context, Constants.NO_INTERNET_CONNECTION);
                        mRefreshLayout.setRefreshing(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                Utility.showMessage(context, Constants.NO_INTERNET_CONNECTION);
                mRefreshLayout.setRefreshing(false);
            }

        }
    }


    public void setActionBar() {
        headerLayout = LayoutInflater.from(context).inflate(R.layout.action_bar, null);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setCustomView(headerLayout);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            toolbar.setContentInsetsAbsolute(0, 0);
            toolbar.setContentInsetsRelative(0, 0);
        }
    }

    private void init() {
        mSideBar = (ImageView) toolbar.findViewById(R.id.side_bar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        titleView = (TextView) toolbar.findViewById(R.id.titleView);
        settingImage = (ImageView) toolbar.findViewById(R.id.settinfImage);
        titleView.setText(getResources().getText(R.string.tindfire));
        settingImage.setBackground(getResources().getDrawable(R.mipmap.settings));
        settingImage.setVisibility(View.INVISIBLE);
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);
        mRefreshLayout = (RecyclerRefreshLayout) findViewById(R.id.refresh_layout);
        mLikeRejectBtLayer = (RelativeLayout) findViewById(R.id.like_reject_bt_layerRl);
        mRejectBtRl1 = (ImageView) findViewById(R.id.reject_bt_rl1);
        progress_bar = (ContentLoadingProgressBar) findViewById(R.id.progress_bar);
        addRelativeLayout = (RelativeLayout) findViewById(R.id.addRelative);
        addRelativeLayout.addView(MyAdmovAds.loadAdmodAd(context));
        mNolist = (TextView) findViewById(R.id.nolist);
        mRejectButtonLayer = (RelativeLayout) findViewById(R.id.reject_button_layerRl);
        mRejectBtnLayerIv = (ImageView) findViewById(R.id.reject_btn_layerIv);
        mLogoutIv = (ImageView) findViewById(R.id.logoutIv);
        mLogoutLL = (LinearLayout) findViewById(R.id.logoutLL);
        mAlreadyLikedMe = (LinearLayout) findViewById(R.id.already_liked_me);
        mSuperLikeMe = (LinearLayout) findViewById(R.id.already_super_liked_me);
        mFacebookFriends = (LinearLayout) findViewById(R.id.facebook_frndLL);
        mSettingLL = (LinearLayout) findViewById(R.id.settingLL);
        mMessageLL = (LinearLayout) findViewById(R.id.messagee_ll);
        mUserNameTv = (TextView) findViewById(R.id.user_name_tv);
        mEmailIdTv = (TextView) findViewById(R.id.email_id_tv);
        mUserProfileIv = (ImageView) findViewById(R.id.user_profile_iv);
        mFacebookLinkIv = (ImageView) findViewById(R.id.facebookLink);
        mTwitterLinkUv = (ImageView) findViewById(R.id.twitterLink);
        mMatchll = (LinearLayout) findViewById(R.id.matchll);
        if (!mPref.getUserName().equalsIgnoreCase("")) {
            mUserNameTv.setText(mPref.getUserName());
        } else {
            mUserNameTv.setVisibility(View.GONE);
        }
        if (!mPref.getProfileImage().equalsIgnoreCase("")) {
            Glide.with(context).load(mPref.getProfileImage())
                    .thumbnail(0.5f)
                    .crossFade()
                    .placeholder(R.mipmap.app_icon)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mUserProfileIv);
        } else {
            mUserProfileIv.setBackgroundResource(R.mipmap.app_icon);
        }
        if (!mPref.getEmailid().equalsIgnoreCase("")) {
            mEmailIdTv.setText(mPref.getEmailid());
        } else {
            mEmailIdTv.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.like_bt_rl1)
    void autoLikeClick() {
        try {
            if (!recomondationResultList.get(3).isLike() && !recomondationResultList.get(2).isLike()) {
                AutoLikeClickApi();
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void AutoLikeClickApi() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title(Constants.AUTO_LIKE_TITLE)
                .content(Constants.AUTO_LIKE_CONTENT)
                .positiveText(Constants.AGREES)
                .negativeText(Constants.DISAGRESS);
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull final MaterialDialog dialog, @NonNull DialogAction which) {
                if (Utility.isConnectingToInternet(context)) {

                    try {
                        final List<String> getAllIdOfUser = categoryAdapter.getAllId();
                        dialog.dismiss();
                        if (getAllIdOfUser != null) {

                            for (int i = 0; i < getAllIdOfUser.size(); i++) {
                                Log.d("Android :", "getAllIdOfUser size  id:" + getAllIdOfUser.get(i));
                                recomondationResultList.get(i).setLike(true);
                                Handler handler = new Handler();
                                final int finalI = i;
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        autoLike = true;
                                        hitLikeAllAPi(getAllIdOfUser.get(finalI), finalI);
                                        dialog.dismiss();

                                    }
                                };
                                handler.postDelayed(runnable, 1000);

                            }

                            Log.d("Android :", "getAllIdOfUser size :" + getAllIdOfUser.size());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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


    private void hitLikeAllAPi(String id, final int i) {
        progress_bar.setVisibility(View.VISIBLE);

        TinderAPiInterface tinderAPiInterface = TinderAPiClient.getCLient().create(TinderAPiInterface.class);
        Call<LikeResponceExample> likeResponceExampleCall = tinderAPiInterface.getLikeResponceExampleCall(mPref.getToken(), id);

        likeResponceExampleCall.enqueue(new Callback<LikeResponceExample>() {
            @Override
            public void onResponse(Call<LikeResponceExample> call, Response<LikeResponceExample> response) {
                progress_bar.setVisibility(View.INVISIBLE);
                try {
                    Log.d("ANdroid :", "ActivityProfile :" + response.body().toString());

                    if (response.body().match() == Constants.MATCH_FALCE) {
                        categoryAdapter.notifyDataSetChanged();
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LikeResponceExample> call, Throwable t) {
                progress_bar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void hitLikeAPi(final String id, final int i,final String userName,final String imageUrl) {
        progress_bar.setVisibility(View.VISIBLE);

        TinderAPiInterface tinderAPiInterface = TinderAPiClient.getCLient().create(TinderAPiInterface.class);
        Call<LikeResponceExample> likeResponceExampleCall = tinderAPiInterface.getLikeResponceExampleCall(mPref.getToken(), id);

        likeResponceExampleCall.enqueue(new Callback<LikeResponceExample>() {
            @Override
            public void onResponse(Call<LikeResponceExample> call, Response<LikeResponceExample> response) {
                progress_bar.setVisibility(View.INVISIBLE);
                try {
                    Log.d("ANdroid :", "ActivityProfile :" + response.body().toString());

                    if (response.body().match() == Constants.MATCH_FALCE) {
                        saveDataInFireBase(id,userName,imageUrl);
                        recomondationResultList.remove(i);
                        categoryAdapter.notifyDataSetChanged();

                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LikeResponceExample> call, Throwable t) {
                progress_bar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void hitSuperLikeAPi(final String id, final int i,final String userName,final String imageUrl){
        progress_bar.setVisibility(View.VISIBLE);
        TinderAPiInterface tinderAPiInterface=TinderAPiClient.getCLient().create(TinderAPiInterface.class);
        Call<SuperLikeExample> superLikeExampleCall = tinderAPiInterface.getSuperLikeExampleCall(mPref.getToken(), id);

        superLikeExampleCall.enqueue(new Callback<SuperLikeExample>() {
            @Override
            public void onResponse(Call<SuperLikeExample> call, Response<SuperLikeExample> response) {
                progress_bar.setVisibility(View.INVISIBLE);
                try {
                    Log.d("ANdroid :", "ActivityProfile :" + response.body().toString());
                    if(response.body().getStatus()==200){
                        saveDataSuperLikeInFireBase(id,userName,imageUrl);
                        recomondationResultList.remove(i);
                        categoryAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuperLikeExample> call, Throwable t) {
                progress_bar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void hitGetRecsApi() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(Constants.PLEASE_WAIT);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        TinderAPiInterface tinderAPiInterface = TinderAPiClient.getCLient().create(TinderAPiInterface.class);
        Log.d("Android :", "mPref.getToken() :" + mPref.getToken());

        Call<RecomondationResponce> recomondationResponceCall = tinderAPiInterface.getrRecomondationResponceCall(mPref.getToken());
        recomondationResponceCall.enqueue(new Callback<RecomondationResponce>() {
            @Override
            public void onResponse(Call<RecomondationResponce> call, Response<RecomondationResponce> response) {
                progressDialog.cancel();
                try {
                    if (response != null) {
                        Log.d("Android :", "response.body().getStatus() :" + response.body().getStatus());
                        if (response.body().getStatus() == Constants.STATUS_200) {
                            if (response.body().getResults() != null) {
                                if (response.body().getResults().get(0).getName() != null) {
                                    mRecycleView.setVisibility(View.VISIBLE);
                                    mLikeRejectBtLayer.setVisibility(View.VISIBLE);
                                    mNolist.setVisibility(View.GONE);
                                    recomondationResultList = response.body().getResults();
                                    mRecycleView.setHasFixedSize(true);
                                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
                                    mRecycleView.setLayoutManager(layoutManager);
                                    categoryAdapter = new CategoryAdapter(ActivityMain.this, context, recomondationResultList);
                                    mRecycleView.setAdapter(categoryAdapter);
                                } else {
                                    mRecycleView.setVisibility(View.GONE);
                                    mLikeRejectBtLayer.setVisibility(View.GONE);
                                    mNolist.setVisibility(View.VISIBLE);
                                    mNolist.setText(Constants.NO_LIST_AVAILABLE);
                                }

                            } else {
                                mRecycleView.setVisibility(View.GONE);
                                mLikeRejectBtLayer.setVisibility(View.GONE);
                                mNolist.setVisibility(View.VISIBLE);
                                mNolist.setText(Constants.NO_LIST_AVAILABLE);
                            }
                        } else {
                            mRecycleView.setVisibility(View.GONE);
                            mLikeRejectBtLayer.setVisibility(View.GONE);
                            mNolist.setVisibility(View.VISIBLE);
                            mNolist.setText(Constants.NO_LIST_AVAILABLE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Utility.showMessage(context, "You are not Login On tinder account.Please first login on tinder.");
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
                startActivity(new Intent(ActivityMain.this, ActivtiySetting.class));
            }
        });
        mRejectBtRl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    showPassAndRefereshDailog();

//                    if (recomondationResultList.get(3).isLike() && recomondationResultList.get(2).isLike()) {
//                        showPassAndRefereshDailog();
//                    } else {
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        mRejectBtnLayerIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mRejectButtonLayer.setVisibility(View.GONE);
//                mLikeRejectBtLayer.setVisibility(View.VISIBLE);
//                if(Utility.isConnectingToInternet(context)){
//                    hitAgainGetRecsApi();
//                }else{
//                    Utility.showMessage(context, Constants.NO_INTERNET_CONNECTION);
//                }

            }
        });
        mAlreadyLikedMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityMain.this, AlreadyLikedMe.class));
            }
        });
        mLogoutIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    removeDataFromPref();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        mLogoutLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    removeDataFromPref();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        mSuperLikeMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityMain.this, AlreadySuperLikedMe.class));
            }
        });
        mMatchll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityMain.this, MatchActivity.class));
            }
        });
        mFacebookFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityMain.this, FacebookFriendsActivity.class));

            }
        });
        mFacebookLinkIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String facebookLink = Constants.FACEBOOK_LINK;
                newFacebookIntent(context.getPackageManager(), facebookLink);
            }
        });
        mTwitterLinkUv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTwitterIntent();
            }
        });
        mSettingLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityMain.this, ActivtiySetting.class));
            }
        });
        mMessageLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void newTwitterIntent() {
        Uri uri = Uri.parse(Constants.TWITTER_LINK);
        Intent intent = null;
        try {
            // get the Twitter app if possible
            this.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, uri);
        }
        this.startActivity(intent);
    }

    public void newFacebookIntent(PackageManager pm, String facebookLink) {
        Uri uri = Uri.parse(facebookLink);
        Intent intent = null;
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
            intent = new Intent(Intent.ACTION_VIEW, uri);
        }
        this.startActivity(intent);
    }

    private void showPassAndRefereshDailog() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title(Constants.PASS_LIST_TITLE)
                .content(Constants.PASS_LIST_CONTENT)
                .positiveText(Constants.AGREES)
                .negativeText(Constants.DISAGRESS);
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                if (Utility.isConnectingToInternet(context)) {

                    try {
                        final List<String> getAllIdOfUser = categoryAdapter.getAllId();
                        dialog.dismiss();
                        if (getAllIdOfUser != null) {
                            for (int i = 0; i < getAllIdOfUser.size(); i++) {
                                Log.d("Android :", "getAllIdOfUser size  id:" + getAllIdOfUser.get(i));
//                                recomondationResultList.get(i).setLike(false);
                               final int valueId=i;
                                hitPassAllAPi(getAllIdOfUser.get(valueId), valueId);
                                dialog.dismiss();
                            }
                            Log.d("Android :", "getAllIdOfUser size :" + getAllIdOfUser.size());
                        }
                        hitGetRecsApi();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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

    private void hitPassAllAPi(String id, final int i) {
        progress_bar.setVisibility(View.VISIBLE);

        TinderAPiInterface tinderAPiInterface = TinderAPiClient.getCLient().create(TinderAPiInterface.class);
        Call<PassData> likePassData = tinderAPiInterface.getLiPassDataCall(mPref.getToken(), id);

        likePassData.enqueue(new Callback<PassData>() {
            @Override
            public void onResponse(Call<PassData> call, Response<PassData> response) {
                progress_bar.setVisibility(View.INVISIBLE);
                try {
                    Log.d("ANdroid :", "ActivityProfile :" + response.body().toString());
                    if (response.body().getStatus() == 200) {
                        categoryAdapter.notifyDataSetChanged();

                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PassData> call, Throwable t) {
                progress_bar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void hitPassAPi(String id, final int i) {
        progress_bar.setVisibility(View.VISIBLE);

        TinderAPiInterface tinderAPiInterface = TinderAPiClient.getCLient().create(TinderAPiInterface.class);
        Call<PassData> likePassData = tinderAPiInterface.getLiPassDataCall(mPref.getToken(), id);

        likePassData.enqueue(new Callback<PassData>() {
            @Override
            public void onResponse(Call<PassData> call, Response<PassData> response) {
                progress_bar.setVisibility(View.INVISIBLE);
                try {
                    Log.d("ANdroid :", "ActivityProfile :" + response.body().toString());
                    if (response.body().getStatus() == 200) {
                        recomondationResultList.remove(i);
                        categoryAdapter.notifyDataSetChanged();

                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PassData> call, Throwable t) {
                progress_bar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void hitAgainGetRecsApi() {
        TinderAPiInterface tinderAPiInterface = TinderAPiClient.getCLient().create(TinderAPiInterface.class);
        Log.d("Android :", "mPref.getToken() :" + mPref.getToken());

        Call<RecomondationResponce> recomondationResponceCall = tinderAPiInterface.getrRecomondationResponceCall(mPref.getToken());
        recomondationResponceCall.enqueue(new Callback<RecomondationResponce>() {
            @Override
            public void onResponse(Call<RecomondationResponce> call, Response<RecomondationResponce> response) {

                try {
                    if (response != null) {
                        Log.d("Android :", "tostringvalue :" + response.body().toString());
                        if (Constants.STATUS_200 == response.body().getStatus()) {
                            mRefreshLayout.setRefreshing(false);
                            recomondationResultList.clear();
                            recomondationResultList = response.body().getResults();
                            Log.d("Android :", "size of again recom :" + recomondationResultList.size());
                            mRecycleView.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
                            mRecycleView.setLayoutManager(layoutManager);
                            categoryAdapter = new CategoryAdapter(ActivityMain.this, context, recomondationResultList);
                            mRecycleView.setAdapter(categoryAdapter);
                        } else {

                        }
                    }
                } catch (Exception e) {
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
                mRefreshLayout.setRefreshing(false);
            }
        });

    }

    public void notingClick(View view) {

    }

    public void removeDataFromPref() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
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
                           String name, String bio, String pingtime, String birthDate, int DistanceMi, String Id, boolean like) {
        try {
            Intent intent = new Intent(ActivityMain.this, ActivtiyProfile.class);
            intent.putExtra(Constants.RECOM_PHO_LIST, (Serializable) recomondationnPhotoList);
            intent.putExtra(Constants.RECOM_Name, name);
            intent.putExtra(Constants.RECOM_BIO, bio);
            intent.putExtra(Constants.RECOM_PINGTIME, pingtime);
            intent.putExtra(Constants.RECOM_BIRTHDATE, birthDate);
            intent.putExtra(Constants.RECOM_DISTANCEMIL, DistanceMi);
            intent.putExtra(Constants.RECOM_ID, Id);
            intent.putExtra(Constants.RECOM_POSITION, position);
            intent.putExtra(Constants.RECOM_LIKE, like);
            intent.putExtra(Constants.RECOM_RESULT, (Serializable) recomondationResultList);
            startActivity(intent);
        } catch (Exception e) {
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
