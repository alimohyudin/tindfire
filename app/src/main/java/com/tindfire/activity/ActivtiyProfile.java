package com.tindfire.activity;

import android.content.Context;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tindfire.R;
import com.tindfire.model.RecomondationModel.RecomondationProcessedFile;
import com.tindfire.model.RecomondationModel.RecomondationnPhoto;
import com.tindfire.util.Constants;
import com.tindfire.util.Utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        context=this;
        setActionBar();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            recomondationnPhotoList= (List<RecomondationnPhoto>)bundle.getSerializable(Constants.RECOM_PHO_LIST);
            recomondationnName= bundle.getString(Constants.RECOM_Name);
            recomondationnBio= bundle.getString(Constants.RECOM_BIO);
            recomondationnPingTime= bundle.getString(Constants.RECOM_PINGTIME);
            recomondationnBirthDate= bundle.getString(Constants.RECOM_BIRTHDATE);
            recomondationnDisatance= bundle.getInt(Constants.RECOM_DISTANCEMIL);
        }
        init();
        addListner();

    }

    private void addListner() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("Android:"," onPageScrolled viewPager.getCurrentItem():" +viewPager.getCurrentItem()+"");
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
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
        if(recomondationnDisatance!=0){
            userDistance.setText(recomondationnDisatance +" " +"miles away");
        }else{
            userDistance.setText("");
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

}
