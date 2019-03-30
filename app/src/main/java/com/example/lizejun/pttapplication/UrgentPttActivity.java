package com.example.lizejun.pttapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * create by lizejun
 * date 2018/9/7
 */
public class UrgentPttActivity extends BasePttActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        textPttPeopleNum.setText();

    }

    @Override
    protected void setViews() {
        imgShutdown.setVisibility(View.GONE);
        imgDown.setVisibility(View.GONE);
        textGroupInfo.setText("调度员");
        textGroupInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnCloseMic.setVisibility(View.GONE);
    }

    @Override
    protected List<Fragment> setFragment() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new NoBackGroundFragment());
        return fragmentList;
    }

    @Override
    protected List<GroupItemBean> setGroupList() {
        return null;
    }
}
