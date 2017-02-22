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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tindfire.R;
import com.tindfire.adapter.AlreadySuperLikeAdapter;
import com.tindfire.firebase.FirebaseLike;
import com.tindfire.util.Constants;
import com.tindfire.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vcareall on 7/1/17.
 */

public class AlreadySuperLikedMe extends AppCompatActivity {
    private static final String TAG=AlreadySuperLikedMe.class.getSimpleName();
    private Context context;
    private View headerLayout;
    private Toolbar toolbar;
    private TextView titleView;
    private RelativeLayout addRelativeLayout;
    private ImageView mSideBar;
    private RecyclerView alreadyLikedRLView;
    private TextView noList;
    private ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    public FirebaseUser firebaseUser;
    public  FirebaseAuth firebaseAuth;
    private AlreadySuperLikeAdapter alreadyLikeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_super_likedme);
        context=this;
        setActionBar();
        init();
        firebaseAuth= FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            databaseReference= FirebaseDatabase.getInstance().getReference();
            firebaseUser=firebaseAuth.getCurrentUser();
            Log.d("Android :","firebasseuser.getuid " +firebaseUser.getUid());
            Log.d("Android :","firebasseuser.getEmail: " +firebaseUser.getEmail());
        }
        if(Utility.isConnectingToInternet(context)){
            getLikeUserFromFireBase();
        }else{
            Utility.showMessage(context, Constants.NO_INTERNET_CONNECTION);
            alreadyLikedRLView.setVisibility(View.GONE);
            noList.setVisibility(View.VISIBLE);
            noList.setText(Constants.NO_INTERNET_CONNECTION);
        }


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
        titleView.setText(getResources().getText(R.string.superlike));
        addRelativeLayout=(RelativeLayout)findViewById(R.id.addRelative);
        addRelativeLayout.addView(MyAdmovAds.loadAdmodAd(context));
        alreadyLikedRLView=(RecyclerView)findViewById(R.id.already_liked_RLView);
        noList=(TextView)findViewById(R.id.nolist);
    }

    private void getLikeUserFromFireBase() {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(Constants.PLEASE_WAIT);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        databaseReference.child(Constants.SUPER_Like_of_+firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                try{
                    List<FirebaseLike> firebaseLikeList=new ArrayList<FirebaseLike>();
                    if(null!=dataSnapshot){
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                            FirebaseLike data = postSnapshot.getValue(FirebaseLike.class);
                            firebaseLikeList.add(new FirebaseLike(data.getUserID(),data.userName,data.imageUrl));
                        }
                    }
                    if(firebaseLikeList.size()>0&&firebaseLikeList!=null){
                        alreadyLikedRLView.setVisibility(View.VISIBLE);
                        noList.setVisibility(View.GONE);
                        Log.d(TAG,"firebaseLikeList size :" +firebaseLikeList.size());
                        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),2);
                        alreadyLikedRLView.setLayoutManager(layoutManager);
                        alreadyLikeAdapter = new AlreadySuperLikeAdapter(AlreadySuperLikedMe.this,context,firebaseLikeList);
                        alreadyLikedRLView.setAdapter(alreadyLikeAdapter);

                    }else{
                        alreadyLikedRLView.setVisibility(View.GONE);
                        noList.setVisibility(View.VISIBLE);
                        noList.setText(Constants.NO_LIST_AVAILABLE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void setSuperLikeMe(String userID) {
        Intent intent=new Intent(AlreadySuperLikedMe.this,AlreadySuperlikeMeProfileActivity.class);
        intent.putExtra(Constants.PROFILE_ID,userID);
        startActivity(intent);
    }
}
