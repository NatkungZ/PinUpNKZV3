package com.example.undine.project_ooad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.appevents.AppEventsLogger;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
        System.out.println("In OnResume");
        System.out.println(AccessToken.getCurrentAccessToken());
        //System.out.println(AccessToken.getCurrentAccessToken().getUserId());
        if (AccessToken.getCurrentAccessToken()!=null)
            finish();
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
        System.out.println("In OnPause");
        System.out.println(AccessToken.getCurrentAccessToken());
    }
}
