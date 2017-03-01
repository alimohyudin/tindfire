package com.tindfire.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tindfire.R;
import com.tindfire.preference.PreferenceManager;
import com.tindfire.util.Constants;

/**
 * Created by vcareall on 7/12/16.
 */
public class ActivtiySetting extends AppCompatActivity {
    private Context context;
    private View headerLayout;
    private Toolbar toolbar;
    private TextView titleView;
    private ImageView mSideBar;
    private RadioButton distanceInKm;
    private RadioButton distanceInMiles;
    private RadioButton menRadioButton,womenRadioButton;

    private PreferenceManager mPref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        context=this;
        mPref=PreferenceManager.getInstatnce(context);
        setActionBar();
        init();
        clickListner();

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
        titleView.setText(getResources().getText(R.string.setting));
        distanceInKm=(RadioButton)findViewById(R.id.distanceInKm);
        distanceInMiles=(RadioButton)findViewById(R.id.distanceInMiles);
        menRadioButton=(RadioButton)findViewById(R.id.men_radioButton);
        womenRadioButton=(RadioButton)findViewById(R.id.women_radioButton);
        if(mPref.getButtonRadio().equalsIgnoreCase(Constants.DISTANCE_MILES)){
            distanceInKm.setChecked(false);
            distanceInMiles.setChecked(true);
        }else {
            distanceInKm.setChecked(true);
            distanceInMiles.setChecked(false);
        }

        if(mPref.getButtonRadioFacebook().equalsIgnoreCase(Constants.WOMEN)){
            womenRadioButton.setChecked(true);
            menRadioButton.setChecked(false);
        }else{
            menRadioButton.setChecked(true);
            womenRadioButton.setChecked(false);
        }
    }
    private void clickListner() {
        distanceInKm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distanceInKm.setChecked(true);
                distanceInMiles.setChecked(false);
                mPref.setButtonRadio(Constants.DISTANCE_KM);

            }
        });
        distanceInMiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distanceInMiles.setChecked(true);
                distanceInKm.setChecked(false);
                mPref.setButtonRadio(Constants.DISTANCE_MILES);
            }
        });
        menRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menRadioButton.setChecked(true);
                womenRadioButton.setChecked(false);
                mPref.setButtonRadioFacebook(Constants.MEN);
            }
        });

        womenRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                womenRadioButton.setChecked(true);
                menRadioButton.setChecked(false);
                mPref.setButtonRadioFacebook(Constants.WOMEN);
            }
        });
        mSideBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivtiySetting.this.finish();
            }
        });

    }

}
