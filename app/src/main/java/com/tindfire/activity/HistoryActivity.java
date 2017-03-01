package com.tindfire.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tindfire.R;
import com.tindfire.adapter.MyPagerAdapter;

/**
 * Created by vcareall on 25/2/17.
 */

public class HistoryActivity extends AppCompatActivity {
    private static final String TAG=HistoryActivity.class.getSimpleName();
    private Context context;
    private View headerLayout;
    private Toolbar toolbar;
    private ViewPager viewpager;
    private ImageView mSideBar;
    private TextView likeTab;
    private TextView superLikeTab;
    private MyPagerAdapter viewPagerAdapter;
    private TextView titleView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        context=this;
        setActionBar();
        inits();
        addListner();
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
    private void inits(){
        mSideBar=(ImageView)toolbar.findViewById(R.id.side_bar);
        mSideBar.setImageResource(R.mipmap.back);
        titleView=(TextView)toolbar.findViewById(R.id.titleView);
        titleView.setText(getResources().getText(R.string.history));
        viewpager=(ViewPager)findViewById(R.id.viewpager);
        superLikeTab=(TextView) findViewById(R.id.superLikeTab);
        likeTab=(TextView) findViewById(R.id.likeTab);
        viewPagerAdapter=new MyPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(viewPagerAdapter);
        viewpager.setCurrentItem(0);
        setActiveUpdateTab(0);
    }
    private void addListner(){
        mSideBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryActivity.this.finish();
            }
        });
        viewpager.setOffscreenPageLimit(1);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                setActiveUpdateTab(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        likeTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager.setCurrentItem(0);
                setActiveUpdateTab(0);
            }
        });
        superLikeTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager.setCurrentItem(1);
                setActiveUpdateTab(1);
            }
        });
    }
    private void setActiveUpdateTab(int position) {
        superLikeTab.setTextColor(getResources().getColor(R.color.white));
        likeTab.setTextColor(getResources().getColor(R.color.white));
        switch (position){
            case 0:
                likeTab.setTextColor(getResources().getColor(R.color.black));
                break;
            case 1:
                superLikeTab.setTextColor(getResources().getColor(R.color.black));
                break;
        }
    }
}
