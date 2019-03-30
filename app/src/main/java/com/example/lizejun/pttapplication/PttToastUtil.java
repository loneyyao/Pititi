package com.example.lizejun.pttapplication;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * create by lizejun
 * date 2018/9/12
 */
public class PttToastUtil {

    private WindowManager mWindowManager;
    private View view;
    WindowManager.LayoutParams params;
    private boolean isShowing;

    private LayoutInflater inflater;
    private CountDownTimer countDownTimer;
    private Context context;


    private static PttToastUtil instance;
    private static final int FIX_LONG_MILLS = 500;
    private static final int TICK_TIME = 1000;

    private PttToastUtil(Context context) {
        initWindowManager(context);
    }

    public static PttToastUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (PttToastUtil.class) {
                instance = new PttToastUtil(context);
            }
        }
        return instance;
    }

    private void initWindowManager(Context context) {
        this.context = context.getApplicationContext();;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.windowAnimations = android.R.style.Animation_Toast;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.TRANSLUCENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void showWarnningToast(String warnningMsg, int duration) {
        ImageView view = new ImageView(context);
        view.setLayoutParams(new ViewGroup.LayoutParams(30, 30));
        view.setImageResource(R.drawable.rc_ptt_speak_time_out);
        makeCustomToast(warnningMsg, view);
        showToast(new CountDownTimer(duration + FIX_LONG_MILLS, TICK_TIME) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                hide();
            }
        });
    }

    public void showSureToast(String sureMsg, int duration) {
        ImageView view = new ImageView(context);
        view.setLayoutParams(new ViewGroup.LayoutParams(30, 30));
        view.setImageResource(R.drawable.rc_ptt_get_the_right_to_speak);

        makeCustomToast(sureMsg, view);

        showToast(new CountDownTimer(duration + FIX_LONG_MILLS, TICK_TIME) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                hide();
            }
        });
    }

    public void showLoadingToast(String loadingMsg, int duration) {
        View view = new SpinView(context);
        makeCustomToast(loadingMsg, view);
        showToast(new CountDownTimer(duration + FIX_LONG_MILLS, TICK_TIME) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                hide();
            }
        });
    }

    private void makeCustomToast(String warnningMsg, View markerView) {
        if (view != null && isShowing) {
            mWindowManager.removeViewImmediate(view);
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        view = inflater.inflate(R.layout.rce_loading_dialog, null, false);
        TextView textViewMsg = view.findViewById(R.id.rce_details_label);

        FrameLayout containerFrame = view.findViewById(R.id.rce_container);
        int wrapParam = ViewGroup.LayoutParams.WRAP_CONTENT;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(wrapParam, wrapParam);

        containerFrame.addView(markerView, params);
        textViewMsg.setText(warnningMsg);
    }


    public void makeCountDownToast(String warnningMsg, int countdownTime) {
        final TextView countdown = new TextView(context);
        countdown.setTextSize(36);
        countdown.setTextColor(context.getResources().getColor(R.color.white));
        countdown.setText(String.valueOf(countdownTime / 1000));
        countdown.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        makeCustomToast(warnningMsg, countdown);
        showToast(new CountDownTimer(countdownTime + FIX_LONG_MILLS, TICK_TIME) {

            @Override
            public void onTick(long millisUntilFinished) {
                countdown.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                hide();
            }
        });
    }


    private void showToast(CountDownTimer countDownTimer) {
        this.countDownTimer = countDownTimer;
        isShowing = true;
        mWindowManager.addView(view, params);
        this.countDownTimer.start();
    }

    public PttToastUtil setGravity(int type) {
        params.gravity = type;
        return this;
    }


    public PttToastUtil setView(View view) {
        this.view = view;
        return this;
    }


    private void hide() {
        isShowing = false;
        mWindowManager.removeView(view);
    }


    public boolean isShowing() {
        return isShowing;
    }
}
