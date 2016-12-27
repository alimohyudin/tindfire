package com.tindfire.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tindfire.R;

/**
 * Created by vcareall on 7/12/16.
 */
public class ActivtiySetting extends AppCompatActivity {
    private Context context;
    private View headerLayout;
    private Toolbar toolbar;
    private TextView titleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        context=this;
        setActionBar();
        init();

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
        titleView.setText(getResources().getText(R.string.setting));
    }

}
