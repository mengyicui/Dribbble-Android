//package com.mengyi.app.dribbbo.auth;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.net.Uri;
//import android.support.annotation.NonNull;
//
//import com.mengyi.app.dribbbo.auth.AuthActivity;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//
//import okhttp3.FormBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class Auth {
//
//    public static final int REQ_CODE = 100;
//
//    private static final String KEY_CODE = "code";
//    private static final String KEY_CLIENT_ID = "client_id";
//    private static final String KEY_CLIENT_SECRET = "client_secret";
//    private static final String KEY_REDIRECT_URI = "redirect_uri";
//    private static final String KEY_SCOPE = "scope";
//    private static final String KEY_ACCESS_TOKEN = "access_token";
//
//    private static final String CLIENT_ID = "83fbc80899408630e7c4839534a4c534ed4784941a48a5f04488d62d706f727f";
//
//    private static final String CLIENT_SECRET = "b32efe5fcce0a46acd319a7c06941607b4a7d06b2cab6c885fc56c19067821e0";
//
//    // see http://developer.dribbble.com/v1/oauth/#scopes
//    private static final String SCOPE = "public+write";
//
//    private static final String URI_AUTHORIZE = "https://dribbble.com/oauth/authorize";
//    private static final String URI_TOKEN = "https://dribbble.com/oauth/token";
//
//    public static final String REDIRECT_URI = "http://www.google.com";
//
//    private static String getAuthorizeUrl() {
//        String url = Uri.parse(URI_AUTHORIZE)
//                .buildUpon()
//                .appendQueryParameter(KEY_CLIENT_ID, CLIENT_ID)
//                .build()
//                .toString();
//
//        // fix encode issue
//        url += "&" + KEY_REDIRECT_URI + "=" + REDIRECT_URI;
//        url += "&" + KEY_SCOPE + "=" + SCOPE;
//
//        return url;
//    }
//
//    private static String getTokenUrl(String authCode) {
//        return Uri.parse(URI_TOKEN)
//                .buildUpon()
//                .appendQueryParameter(KEY_CLIENT_ID, CLIENT_ID)
//                .appendQueryParameter(KEY_CLIENT_SECRET, CLIENT_SECRET)
//                .appendQueryParameter(KEY_CODE, authCode)
//                .appendQueryParameter(KEY_REDIRECT_URI, REDIRECT_URI)
//                .build()
//                .toString();
//    }
//
//    public static void openAuthActivity(@NonNull Activity activity) {
//        Intent intent = new Intent(activity, AuthActivity.class);
//        intent.putExtra(AuthActivity.KEY_URL, getAuthorizeUrl());
//
//        activity.startActivityForResult(intent, REQ_CODE);
//    }
//
//    public static String fetchAccessToken(String authCode)
//            throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        RequestBody postBody = new FormBody.Builder()
//                .add(KEY_CLIENT_ID, CLIENT_ID)
//                .add(KEY_CLIENT_SECRET, CLIENT_SECRET)
//                .add(KEY_CODE, authCode)
//                .add(KEY_REDIRECT_URI, REDIRECT_URI)
//                .build();
//        Request request = new Request.Builder()
//                .url(URI_TOKEN)
//                .post(postBody)
//                .build();
//        Response response = client.newCall(request).execute();
//
//        String responseString = response.body().string();
//        try {
//            JSONObject obj = new JSONObject(responseString);
//            return obj.getString(KEY_ACCESS_TOKEN);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
//
//}


package com.mengyi.app.dribbbo.auth;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.mengyi.app.dribbbo.model.AccessToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Auth {

    public static final int REQ_CODE = 100;

    private static final String KEY_CODE = "code";
    private static final String KEY_CLIENT_ID = "client_id";
    private static final String KEY_CLIENT_SECRET = "client_secret";
    private static final String KEY_REDIRECT_URI = "redirect_uri";
    private static final String KEY_SCOPE = "scope";
    private static final String KEY_ACCESS_TOKEN = "access_token";

    private static final String CLIENT_ID = "83fbc80899408630e7c4839534a4c534ed4784941a48a5f04488d62d706f727f";

    private static final String CLIENT_SECRET = "b32efe5fcce0a46acd319a7c06941607b4a7d06b2cab6c885fc56c19067821e0";

    // see http://developer.dribbble.com/v1/oauth/#scopes
    private static final String SCOPE = "public+write";

    private static final String URI_AUTHORIZE = "https://dribbble.com/oauth/authorize";
    private static final String URI_TOKEN = "https://dribbble.com/oauth/token";

    public static final String REDIRECT_URI = "http://www.google.com";

    private static String getAuthorizeUrl() {
//        String url = Uri.parse(URI_AUTHORIZE)
//                .buildUpon()
//                .appendQueryParameter(KEY_CLIENT_ID, CLIENT_ID)
//                .build()
//                .toString();
//
//        // fix encode issue
//        url += "&" + KEY_REDIRECT_URI + "=" + REDIRECT_URI;
//        url += "&" + KEY_SCOPE + "=" + SCOPE;

        String url = URI_AUTHORIZE + "?" + KEY_CLIENT_ID + "=" + CLIENT_ID + "&"
                + KEY_SCOPE + "=" + SCOPE;
//
//
        return url;
    }

    private static String getTokenUrl(String authCode) {
        return Uri.parse(URI_TOKEN)
                .buildUpon()
                .appendQueryParameter(KEY_CLIENT_ID, CLIENT_ID)
                .appendQueryParameter(KEY_CLIENT_SECRET, CLIENT_SECRET)
                .appendQueryParameter(KEY_CODE, authCode)
                .appendQueryParameter(KEY_REDIRECT_URI, REDIRECT_URI)
                .build()
                .toString();
    }

    public static void openAuthActivity(@NonNull Activity activity) {
        Intent intent = new Intent(activity, AuthActivity.class);
        intent.putExtra(AuthActivity.KEY_URL, Uri.parse(getAuthorizeUrl()));
//        intent.putExtra(AuthActivity.KEY_URL, getAuthorizeUrl());

//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getAuthorizeUrl()));

        activity.startActivityForResult(intent, REQ_CODE);
    }

    public static String fetchAccessToken(String authCode)
            throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        RequestBody postBody = new FormBody.Builder()
//                .add(KEY_CLIENT_ID, CLIENT_ID)
//                .add(KEY_CLIENT_SECRET, CLIENT_SECRET)
//                .add(KEY_CODE, authCode)
//                .add(KEY_REDIRECT_URI, REDIRECT_URI)
//                .build();
//        Request request = new Request.Builder()
//                .url(URI_TOKEN)
//                .post(postBody)
//                .build();
//        Response response = client.newCall(request).execute();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dribbble.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DribbbleClient client = retrofit.create(DribbbleClient.class);
        Call<AccessToken> accessTokenCall = client.getAccessToken(
                CLIENT_ID,
                CLIENT_SECRET,
                authCode
        );

//        accessTokenCall.enqueue(new Callback<AccessToken>() {
//            @Override
//            public void onResponse(Call<AccessToken> call, retrofit2.Response<AccessToken> response) {
//                response.body();
//                String responseString = response.body().getAccessToken();
//
//            }
//
//            @Override
//            public void onFailure(Call<AccessToken> call, Throwable t) {
//
//            }
//        });
//
        AccessToken accessToken = accessTokenCall.execute().body();
        return accessToken.getAccessToken();



//        String responseString = response.body().string();
//        try {
//            JSONObject obj = new JSONObject(responseString);
//            return obj.getString(KEY_ACCESS_TOKEN);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return "";
        }

    }

