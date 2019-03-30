package com.example.lizejun.pttapplication;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

/**
 * create by lizejun
 * date 2018/9/3
 */
public class PttGroupsDialogFragment extends DialogFragment {

    private TextView alertInfo;
    private TextView alertInfoCountdown;
    private int countdown;
    private String groupName;
    private CountDownTimer timer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rc_ptt_alert_dialog, container, false);
        alertInfo = view.findViewById(R.id.text_alert_info);
        alertInfoCountdown = view.findViewById(R.id.text_alert_countdown);
        alertInfo.setText(groupName);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
        timer.start();
    }


    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        Bundle arguments = getArguments();
        groupName = arguments.getString("groupName", "其他组");
        countdown = arguments.getInt("countdown", 3);
        timer = new CountDownTimer(countdown * 1000 + 500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                alertInfoCountdown.setText(Html.fromHtml(getString(R.string.rc_ptt_alert_countdown,millisUntilFinished/1000)));
            }

            @Override
            public void onFinish() {
                dismiss();
            }
        };

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) {
            timer.cancel();
        }
    }
}
