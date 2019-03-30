package com.example.lizejun.pttapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * create by lizejun
 * date 2018/9/6
 */
public class MyFrameLayout extends FrameLayout {

    private Context context;

    public MyFrameLayout(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public MyFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    public MyFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getAction();
        long downMills = 0;
        long upMills = 0;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downMills = System.currentTimeMillis();
            case MotionEvent.ACTION_UP:
                upMills = System.currentTimeMillis();
            default:
                break;
        }
        if ((upMills - downMills) > 2000) {
            return true;
        } else {
            return super.onInterceptTouchEvent(event);
        }
    }

}


