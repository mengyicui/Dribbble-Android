package com.mengyi.app.dribbbo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;
import com.mengyi.app.dribbbo.R;
import com.mengyi.app.dribbbo.auth.Auth;
import com.mengyi.app.dribbbo.auth.AuthActivity;
import com.mengyi.app.dribbbo.auth.Login;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.activity_login_btn) TextView loginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        // load access token from shared preference
        Login.init(this);

        if (!Login.isLoggedIn()) {
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Auth.openAuthActivity(LoginActivity.this);
                }
            });
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Auth.REQ_CODE && resultCode == RESULT_OK) {
            final String authCode = data.getStringExtra(AuthActivity.KEY_CODE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // this is a network call and it's time consuming
                        // that's why we're doing this in a non-UI thread
                        String token = Auth.fetchAccessToken(authCode);

                        // store access token in SharedPreferences
                        Login.login(LoginActivity.this, token);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (IOException | JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
