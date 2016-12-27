package com.tindfire.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.tindfire.R;
import com.tindfire.apiClient.TinderAPiClient;
import com.tindfire.apiClient.TinderAPiInterface;
import com.tindfire.model.AuthModel.AuthRequest;
import com.tindfire.model.AuthModel.AuthResponceExample;
import com.tindfire.preference.PreferenceManager;
import com.tindfire.util.Constants;
import com.tindfire.util.Utility;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vcareall on 7/12/16.
 */
public class LoginActivity extends AppCompatActivity {
    private Context context;
    //private ImageView mFbButton;
    @Bind(R.id.fb_button)ImageView mFbButton;

    private CallbackManager callbackManager;
    private String fbToken;
    private String socialUniqueId="";
    private String fbmEmail;
    private String mFbName;
    private PreferenceManager mPref;
    private String facebooId="";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);
        context=this;
        ButterKnife.bind(this);
        callbackManager = CallbackManager.Factory.create();
        mPref=PreferenceManager.getInstatnce(context);
    }
    @OnClick(R.id.fb_button) void fbButtonClick(){
        fbLogin();
    }

    private void fbLogin() {
        LoginManager.getInstance().setLoginBehavior(LoginBehavior.WEB_ONLY);
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println("onSuccess");
                fbToken = loginResult.getAccessToken().getToken();
                facebooId=loginResult.getAccessToken().getUserId();
                Log.v("fbToken", fbToken);
                Log.v("fbToken userid", loginResult.getAccessToken().getUserId());

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try{
                                    Log.v("LoginActivity", response.toString());
                                    socialUniqueId = object.getString("id");
                                    fbmEmail = object.getString("email");
                                    mFbName = object.getString("name");
                                    if(!socialUniqueId.equalsIgnoreCase("")){
                                        if(Utility.isConnectingToInternet(context)){
                                            hitAuthApi(fbToken,socialUniqueId);
                                        }else{
                                            Utility.showMessage(context, Constants.NO_INTERNET_CONNECTION);
                                        }
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Log.d("ANdroid :","cancel :" +"cancel");
                if(!facebooId.equalsIgnoreCase("")){
                    if(Utility.isConnectingToInternet(context)){
                        hitAuthApi(fbToken,socialUniqueId);
                    }else{
                        Utility.showMessage(context, Constants.NO_INTERNET_CONNECTION);
                    }
                }else{
                    LoginManager.getInstance().logOut();
                    Utility.showMessage(context,Constants.FACEBOOK_LOGOUT);
                }

            }



            @Override
            public void onError(FacebookException error) {
                Log.d("ANdroid :","error :" +error.getMessage());

            }
        });
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                Arrays.asList("public_profile, email, user_birthday, user_friends"));

    }

    private void hitAuthApi(String fbToken, String socialUniqueId) {
        AuthRequest request=new AuthRequest.Builder()
                .facebookToken(fbToken)
                .facebookId(socialUniqueId).build();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(Constants.PLEASE_WAIT);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        TinderAPiInterface tinderAPiInterface= TinderAPiClient.getCLient().create(TinderAPiInterface.class);

        Call<AuthResponceExample> authResponceExampleCall=tinderAPiInterface.getAuthResponceExampleCall(request);
        Log.d("Android :","authResponceExampleCall token :" +authResponceExampleCall.toString());
        authResponceExampleCall.enqueue(new Callback<AuthResponceExample>() {
            @Override
            public void onResponse(Call<AuthResponceExample> call, Response<AuthResponceExample> response) {
                progressDialog.cancel();
                try{
                    Log.d("Android :","authResponceExampleCall token :" +response.body().toString());
                    if(!response.body().getToken().equalsIgnoreCase("")){
                        mPref.setFirstTime(false);
                        mPref.setToken(response.body().getToken());
                        startActivity(new Intent(LoginActivity.this,ActivityMain.class));
                        finish();
                    }else{

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<AuthResponceExample> call, Throwable t) {
                progressDialog.cancel();
            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

}
