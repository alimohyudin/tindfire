package com.tindfire.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tindfire.R;
import com.tindfire.apiClient.TinderAPiClient;
import com.tindfire.apiClient.TinderAPiInterface;
import com.tindfire.model.LikeResponce.LikeResponceExample;
import com.tindfire.model.LikeResponce.Match;
import com.tindfire.model.PassModel.PassData;
import com.tindfire.model.RecomondationModel.RecomondationProcessedFile;
import com.tindfire.model.RecomondationModel.RecomondationResult;
import com.tindfire.model.RecomondationModel.RecomondationnPhoto;
import com.tindfire.model.SuperLikeModel.SuperLikeExample;
import com.tindfire.preference.PreferenceManager;
import com.tindfire.util.Constants;
import com.tindfire.util.Utility;
import com.viewpagerindicator.CirclePageIndicator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tindfire.activity.ActivityMain.categoryAdapter;


/**
 * Created by vcareall on 7/12/16.
 */
public class ActivtiyProfile extends AppCompatActivity {
    private Context context;
    private View headerLayout;
    private Toolbar toolbar;
    private TextView titleView;
    private ViewPager viewPager;
    private List<RecomondationnPhoto> recomondationnPhotoList;
    private List<RecomondationProcessedFile> recomondationnFileList;
    private String recomondationnName;
    private String recomondationnBio;
    private TextView userProfileName;
    private String recomondationnPingTime;
    private String recomondationnBirthDate;
    ArrayList<String> profilePhotoOfUser;
    private CustomPagerAdapter mCustomPagerAdapter;
    private TextView userDistance;
    private TextView userPingtime;
    private TextView userstatus;
    private TextView userAge;
    private int recomondationnDisatance;
    private ImageView mSideBar;
    private String recomondationnID;
    private ImageView likeUser;
    private PreferenceManager mPref;
    private ProgressDialog progressDialog;
    private ImageView rejectuserIv,superlikeIv;
    private int recomondationnPosition;
    private boolean recomondationnLike;
    private List<RecomondationResult> recomondationnResultList;
    private RelativeLayout addRelativeLayout;
    private static int NUM_PAGES = 0;
    private static int currentPage = 0;
    private CirclePageIndicator indicator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        context=this;
        mPref=PreferenceManager.getInstatnce(context);
        setActionBar();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            recomondationnPhotoList= (List<RecomondationnPhoto>)bundle.getSerializable(Constants.RECOM_PHO_LIST);
            recomondationnResultList= (List<RecomondationResult>)bundle.getSerializable(Constants.RECOM_RESULT);
            recomondationnName= bundle.getString(Constants.RECOM_Name);
            recomondationnBio= bundle.getString(Constants.RECOM_BIO);
            recomondationnPingTime= bundle.getString(Constants.RECOM_PINGTIME);
            recomondationnBirthDate= bundle.getString(Constants.RECOM_BIRTHDATE);
            recomondationnDisatance= bundle.getInt(Constants.RECOM_DISTANCEMIL);
            recomondationnID= bundle.getString(Constants.RECOM_ID);
            recomondationnPosition= bundle.getInt(Constants.RECOM_POSITION);
            recomondationnLike= bundle.getBoolean(Constants.RECOM_LIKE);
            Log.d("Android:"," onrecomondationnLike:" +recomondationnLike);
        }

        init();
        addListner();

    }

    private void addListner() {
//
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.d("Android:"," onPageScrolled viewPager.getCurrentItem():" +viewPager.getCurrentItem()+"");
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
        likeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utility.isConnectingToInternet(context)){
                    showLikeDailog();
//                    if(!recomondationnLike){
//                        hitLikeAPi();
//                    }else{
//                        Utility.showMessage(context,Constants.ALREADY_LICK_USER);
//                    }
                }else{
                    Utility.showMessage(context,Constants.NO_INTERNET_CONNECTION);
                }
            }
        });
        rejectuserIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utility.isConnectingToInternet(context)){
                    showPassDailog();
//                    if(recomondationnLike){
//                        hitPassAPi();
//                    }else{
////                        Utility.showMessage(context,Constants.ALREADY_LICK_USER);
//                    }
                }else{
                    Utility.showMessage(context,Constants.NO_INTERNET_CONNECTION);
                }

            }
        });
        superlikeIv.setOnClickListener(new View.OnClickListener() {
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
                    superlikeIv.setImageResource(R.mipmap.superlike_active);
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
                    likeUser.setImageResource(R.mipmap.profile_like_btn);
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
    private void showPassDailog() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title(Constants.PASS_LIST_TITLE)
                .content(Constants.PASS_LIST_USER)
                .positiveText(Constants.AGREES)
                .negativeText(Constants.DISAGRESS);
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                if (Utility.isConnectingToInternet(context)) {

                    dialog.dismiss();
                    hitPassAPi();
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
        Call<SuperLikeExample> superLikeExampleCall = tinderAPiInterface.getSuperLikeExampleCall(mPref.getToken(), recomondationnID);

        superLikeExampleCall.enqueue(new Callback<SuperLikeExample>() {
            @Override
            public void onResponse(Call<SuperLikeExample> call, Response<SuperLikeExample> response) {
                progressDialog.dismiss();
                try {
                    Log.d("ANdroid :", "ActivityProfile :" + response.body().toString());
                    if(response.body().getStatus()==200){
                        categoryAdapter.removePositonFromProfile(recomondationnPosition);
                        finish();
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
    private void hitPassAPi() {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(Constants.PLEASE_WAIT);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        TinderAPiInterface tinderAPiInterface= TinderAPiClient.getCLient().create(TinderAPiInterface.class);
        Call<PassData> likePassData=tinderAPiInterface.getLiPassDataCall(mPref.getToken(),recomondationnID);

        likePassData.enqueue(new Callback<PassData>() {
            @Override
            public void onResponse(Call<PassData> call, Response<PassData> response) {
                progressDialog.dismiss();
                try{
                    Log.d("ANdroid :","ActivityProfile :" +response.body().toString());
                    if(response.body().getStatus()==200){
//                        likeUser.setImageResource(R.mipmap.profile_like_btns);
//                        recomondationnLike=false;
//                        categoryAdapter.passProfile(recomondationnPosition);
                        categoryAdapter.removePositonFromProfile(recomondationnPosition);
                        finish();
                    }else{
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<PassData> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    private void hitLikeAPi() {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(Constants.PLEASE_WAIT);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        TinderAPiInterface tinderAPiInterface= TinderAPiClient.getCLient().create(TinderAPiInterface.class);
        Call<LikeResponceExample> likeResponceExampleCall=tinderAPiInterface.getLikeResponceExampleCall(mPref.getToken(),recomondationnID);

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
                        finish();
                    }else if(obj instanceof Boolean){
                        Boolean b=(Boolean)response.body().match();
                        Log.d("Android :","boolean valuse :" +b);
                        Utility.showMessage(context,"Liked");
//                        likeUser.setImageResource(R.mipmap.profile_like_btn);
//                        recomondationnLike=true;
//                        categoryAdapter.likeProfile(recomondationnPosition);
                        categoryAdapter.removePositonFromProfile(recomondationnPosition);
                        finish();

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
        titleView.setText(getResources().getText(R.string.profile));
        viewPager =(ViewPager)findViewById(R.id.ViewPager);
        userProfileName =(TextView)findViewById(R.id.userProfileName);
        userDistance =(TextView)findViewById(R.id.distance);
        userPingtime =(TextView)findViewById(R.id.pingtime);
        userstatus =(TextView)findViewById(R.id.status);
        userAge =(TextView)findViewById(R.id.userAge);
        likeUser =(ImageView)findViewById(R.id.likeUser);
        rejectuserIv =(ImageView)findViewById(R.id.rejectuserIv);
        superlikeIv =(ImageView)findViewById(R.id.superlikeIv);
        addRelativeLayout=(RelativeLayout)findViewById(R.id.addRelative);
        addRelativeLayout.addView(MyAdmovAds.loadAdmodAd(context));
        profilePhotoOfUser=new ArrayList<>();
        if(recomondationnPhotoList!=null){
           for(int i=0;i<recomondationnPhotoList.size();i++){
               profilePhotoOfUser.add(recomondationnPhotoList.get(i).getUrl());
               Log.d("ANdroid :","profilePhotoOfUser.size "+recomondationnPhotoList.get(i).getUrl());
           }
            Log.d("ANdroid :","profilePhotoOfUser.size "+profilePhotoOfUser.size());
            Log.d("ANdroid :","recomondationnName.size "+recomondationnName);

        }
        if(profilePhotoOfUser!=null){
            mCustomPagerAdapter = new CustomPagerAdapter(context);
            viewPager.setAdapter(mCustomPagerAdapter);
        indicator = (CirclePageIndicator)
                    findViewById(R.id.indicator);
            indicator.setViewPager(viewPager);


            final float density = getResources().getDisplayMetrics().density;
            //Set circle indicator radius
            indicator.setRadius(5 * density);

            NUM_PAGES =profilePhotoOfUser.size();

        }
        if(recomondationnName.trim().length()>0){
            userProfileName.setText(recomondationnName);
        }
        if(recomondationnBio.trim().length()>0){
            userstatus.setText(recomondationnBio);
        }
        if(recomondationnPingTime.trim().length()>0){
            splitePingTime(recomondationnPingTime);
        }
        if(recomondationnBirthDate.trim().length()>0){
            spliteDateAndTime(recomondationnBirthDate);
        }
        try{
            if(recomondationnDisatance!=0){
                if(mPref.getButtonRadio().equalsIgnoreCase(Constants.DISTANCE_KM)){
                    int kilometerDist= (int) (recomondationnDisatance*1.609344);
                    userDistance.setText(kilometerDist +" " +"km away");
                    Log.d("Android :","kilometerDist :" +kilometerDist);
                }else{
                    userDistance.setText(recomondationnDisatance +" " +"miles away");
                }
            }else{
                userDistance.setText("");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if(recomondationnLike){
            likeUser.setImageResource(R.mipmap.profile_like_btn);
        }

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
            userPingtime.setText(" , "+diffInDays + " " +"days ago");
        } else if (diffHours > 24) {
            System.err.println(">24" +diffHours);
            userPingtime.setText(" , "+diffHours +" " +"hours ago");
        } else if (diffMinutes >= 1) {
            System.err.println("minutes" +diffMinutes);
            userPingtime.setText(" , "+diffMinutes +" " +"minutes ago");
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

    public class CustomPagerAdapter extends PagerAdapter {
        private final Context context;
        private String imagebuzz;
        public CustomPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return profilePhotoOfUser.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundColor(getResources().getColor(R.color.black));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);//fircenter
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);

//            Log.d("Android :", "adpater inside 20 :" + getImageList.get(position).getImage20());
            try{
                Log.d("Android :", "adpater inside 1:" + profilePhotoOfUser.get(position));

                imagebuzz = profilePhotoOfUser.get(position);

                Glide.with(context).load(imagebuzz)
                        .thumbnail(0.5f)
                        .crossFade()
                        .placeholder(R.mipmap.app_icon)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
                ((ViewPager) container).addView(imageView, 0);
            }catch(Exception e){
                e.printStackTrace();
            }
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            if(recomondationnResultList.get(recomondationnPosition).isLike()){
                categoryAdapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
