package com.tindfire.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.tindfire.activity.LikedMeProfileActivity;
import com.tindfire.activity.MyAdmovAds;
import com.tindfire.adapter.LikeAdapter;
import com.tindfire.firebase.FirebaseLike;
import com.tindfire.util.Constants;
import com.tindfire.util.Utility;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by vcareall on 25/2/17.
 */

public class LikedFragment extends Fragment {
    private View view;
    private RelativeLayout addRelativeLayout;
    private RecyclerView alreadyLikedRLView;
    private TextView noList;
    private ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    public FirebaseUser firebaseUser;
    public  FirebaseAuth firebaseAuth;
    private LikeAdapter alreadyLikeAdapter;
    public static LikedFragment newInstance(int page, String title) {
        LikedFragment fragmentFirst = new LikedFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.content_likedme,container,false);
        init();
        firebaseAuth= FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            databaseReference= FirebaseDatabase.getInstance().getReference();
            firebaseUser=firebaseAuth.getCurrentUser();
            Log.d("Android :","firebasseuser.getuid " +firebaseUser.getUid());
            Log.d("Android :","firebasseuser.getEmail: " +firebaseUser.getEmail());
        }
        if(Utility.isConnectingToInternet(getActivity())){
            getLikeUserFromFireBase();
        }else{
            Utility.showMessage(getActivity(), Constants.NO_INTERNET_CONNECTION);
            alreadyLikedRLView.setVisibility(View.GONE);
            noList.setVisibility(View.VISIBLE);
            noList.setText(Constants.NO_INTERNET_CONNECTION);
        }
        return view;
    }
    private void init() {
        addRelativeLayout=(RelativeLayout)view.findViewById(R.id.addRelative);
        addRelativeLayout.addView(MyAdmovAds.loadAdmodAd(getActivity()));
        alreadyLikedRLView=(RecyclerView)view.findViewById(R.id.already_liked_RLView);
        noList=(TextView)view.findViewById(R.id.nolist);
    }

    private void getLikeUserFromFireBase() {
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage(Constants.PLEASE_WAIT);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        databaseReference.child(firebaseUser.getUid()).child(Constants.Like_of_+firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
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
                        Log.d("LIkedFragment","firebaseLikeList size :" +firebaseLikeList.size());
                        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),2);
                        alreadyLikedRLView.setLayoutManager(layoutManager);
                        alreadyLikeAdapter = new LikeAdapter(LikedFragment.this,getActivity(),firebaseLikeList);
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

    public void setlikeProfile(String userID) {
        Intent intent=new Intent(getActivity(),LikedMeProfileActivity.class);
        intent.putExtra(Constants.PROFILE_ID,userID);
        startActivity(intent);
    }
}
