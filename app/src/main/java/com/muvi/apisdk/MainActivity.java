package com.muvi.apisdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.home.apisdk.apiController.SDKInitializer;

public class MainActivity extends AppCompatActivity implements SDKInitializer.SDKInitializerListner{
    public static final String APP_TOKEN = "your token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       SDKInitializer.getInstance().init(this,this,APP_TOKEN);
    }

    @Override
    public void onPreExexuteListner() {
        //Show dialog

    }

    @Override
    public void onPostExecuteListner() {
        //Hide Dialog

    }


}
