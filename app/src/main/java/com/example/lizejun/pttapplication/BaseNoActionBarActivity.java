package com.example.lizejun.pttapplication;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;


public class BaseNoActionBarActivity extends FragmentActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
//        Context newContext = RongConfigurationManager.getInstance().getConfigurationContext(newBase);
        super.attachBaseContext(newBase);
    }
}
