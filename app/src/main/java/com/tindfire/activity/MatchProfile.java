package com.tindfire.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tindfire.R;
import com.tindfire.model.GetMatchModel.GetMatchPhoto;
import com.tindfire.util.Constants;
import com.tindfire.util.Utility;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vcareall on 7/12/16.
 */
public class MatchProfile extends AppCompatActivity {
    private Context context;
    private View headerLayout;
    private Toolbar toolbar;
    private TextView titleView;
    private ViewPager viewPager;
    private List<GetMatchPhoto> recomondationnPhotoList;
    private String recomondationnName;
    private String recomondationnBio;
    private TextView userProfileName;
    private String recomondationnPingTime;
    private String recomondationnBirthDate;
    ArrayList<String> profilePhotoOfUser;
    private CustomPagerAdapter mCustomPagerAdapter;
    private TextView userPingtime;
    private TextView userstatus;
    private TextView userAge;
    private ImageView mSideBar;
    private int recomondationnPosition;
    private RelativeLayout addRelativeLayout;
    private LinearLayout chatIcon;
    private static int NUM_PAGES = 0;
    private static int currentPage = 0;
    private CirclePageIndicator indicator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_profile);
        context=this;
        setActionBar();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            recomondationnPhotoList= (List<GetMatchPhoto>)bundle.getSerializable(Constants.RECOM_PHO_LIST);
            recomondationnName= bundle.getString(Constants.RECOM_Name);
            recomondationnBio= bundle.getString(Constants.RECOM_BIO);
            recomondationnPingTime= bundle.getString(Constants.RECOM_PINGTIME);
            recomondationnBirthDate= bundle.getString(Constants.RECOM_BIRTHDATE);
            recomondationnPosition= bundle.getInt(Constants.RECOM_POSITION);
        }
        init();
        addListner();
    }

    private void addListner() {
        mSideBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatchProfile.this.finish();
            }
        });
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
        chatIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MatchProfile.this,ChatActivity.class));

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
        mSideBar.setImageResource(R.mipmap.back);
        titleView=(TextView)toolbar.findViewById(R.id.titleView);
        titleView.setText(getResources().getText(R.string.match));
        addRelativeLayout=(RelativeLayout)findViewById(R.id.addRelative);
        addRelativeLayout.addView(MyAdmovAds.loadAdmodAd(context));
        viewPager =(ViewPager)findViewById(R.id.ViewPager);
        userProfileName =(TextView)findViewById(R.id.userProfileName);
        userPingtime =(TextView)findViewById(R.id.pingtime);
        userstatus =(TextView)findViewById(R.id.status);
        userAge =(TextView)findViewById(R.id.userAge);
        chatIcon =(LinearLayout)findViewById(R.id.chatIcon);
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
            Utility.splitePingTime(recomondationnPingTime,userPingtime);
        }
        if(recomondationnBirthDate.trim().length()>0){
            Utility.spliteDateAndTime(recomondationnBirthDate,userAge);
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
        finish();
    }
}
