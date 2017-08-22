package com.tuhocandroid.navdrawerandtablayout.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.params.Face;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.tuhocandroid.navdrawerandtablayout.Object.Song;
import com.tuhocandroid.navdrawerandtablayout.R;
import com.facebook.FacebookSdk;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.content.Context.MODE_PRIVATE;

public class ShareFacebookActivity extends AppCompatActivity {

    public static final String PROFILE_USER = "profile_user";
    public static final String USERNAME_DEFAUL = "Default";
    public static final String IMAGE_DEFAUL = "https://firebasestorage.googleapis.com/v0/b/authenticationdemo-6a91a.appspot.com/o/ic_account_circle_black_36dp.png?alt=media&token=bc0c71ad-1a5a-4775-b483-050758eba5a2";

    public static SharedPreferences profileUser;
    private CallbackManager mCallbackManager;
    public static FacebookCallback<LoginResult> loginResultFacebookCallback;
    private LoginButton loginButton;
    private ShareDialog shareDialog;
    private boolean loggIn;
    private String avatar_url;
    private String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(this);

        shareDialog = new ShareDialog(this);

        setContentView(R.layout.activity_share_facebook);

        Bundle bundleSong = getIntent().getBundleExtra("Song");
        Song song = (Song) bundleSong.getSerializable("Song");
        profileUser = getSharedPreferences(PROFILE_USER, MODE_PRIVATE);

        loggIn = isFacebookLogIn();

        loginButton = (LoginButton) findViewById(R.id.btn_login_fb);
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request= GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            avatar_url = object.getJSONObject("picture").getJSONObject("data").getString("url");
                            avatar_url = avatar_url.replace("\\","");
                            Log.d("CommentSong avatar", avatar_url);
                            user_name = object.getString("name");
                            Log.d("CommentSong name", user_name);
                            try {
                                SharedPreferences.Editor editorAvatar = profileUser.edit();
                                editorAvatar.putString("name", user_name);
                                editorAvatar.putString("avatar_url", avatar_url);
                                editorAvatar.commit();

                            } catch (Exception ex){
                                SharedPreferences.Editor editorAvatar = profileUser.edit();
                                editorAvatar.putString("avatar_url", IMAGE_DEFAUL);
                                editorAvatar.putString("name", USERNAME_DEFAUL);
                                editorAvatar.commit();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name, picture.width(50).height(50)");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        shareDialog.registerCallback(mCallbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(ShareFacebookActivity.this, "You shared already!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        if (shareDialog.canShow(ShareLinkContent.class)){

            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(song.getUrlSong()))
                    .build();

            shareDialog.show(linkContent);

        };

        if(!loggIn) {
            SharedPreferences.Editor editorAvatar = profileUser.edit();
            editorAvatar.putString("avatar_url", IMAGE_DEFAUL);
            editorAvatar.putString("name", USERNAME_DEFAUL);
            editorAvatar.commit();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mCallbackManager.onActivityResult(requestCode, resultCode, data)) {
            Toast.makeText(ShareFacebookActivity.this, IMAGE_DEFAUL, Toast.LENGTH_SHORT).show();
//            onBackPressed();
        };
    }

    public boolean isFacebookLogIn() {
        if (AccessToken.getCurrentAccessToken() != null){
            return true;
        }
        return false;
    }

    public static String BitmaptoString(Bitmap bitmap) {
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteOutput);
        byte[] output = byteOutput.toByteArray();
        String stringOutput = Base64.encodeToString(output, Base64.DEFAULT);

        return stringOutput;
    }

    public static Bitmap StringtoBitmap(String encodedString) {
        byte[] encodBytes = Base64.decode(encodedString, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodBytes, 0, encodBytes.length);
        return bitmap;
    }


}

//class DownloadImage extends AsyncTask<String, Void, Bitmap> {
//
//
//    @Override
//    protected Bitmap doInBackground(String... strings) {
//        String imageURL = strings[0];
//
//        Bitmap bitmap = null;
//        try {
//            // Download Image from URL
//            InputStream input = new java.net.URL(imageURL).openStream();
//            // Decode Bitmap
//            bitmap = BitmapFactory.decodeStream(input);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return bitmap;
//    }
//
//    @Override
//    protected void onPostExecute(Bitmap bitmap) {
//        SharedPreferences.Editor editorAvatar = ShareFacebookActivity.profileUser.edit();
//        editorAvatar.putString("avatar", ShareFacebookActivity.BitmaptoString(bitmap));
//        editorAvatar.commit();
//    }
//}