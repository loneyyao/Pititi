package com.example.lizejun.pttapplication;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class HackyViewPager extends ViewPager {

    private boolean isCanScroll = true;
    private int lastX;
    private double slop;

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isCanScroll) {
            int action = event.getAction();
            // 获取当前触摸的绝对坐标
            int rawX = (int) event.getRawX();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    // 上一次离开时的坐标
                    Log.d("lizejun", "onInterceptTouchEvent: ACTION_DOWN" + rawX);
                    lastX = rawX;
                    break;
                case MotionEvent.ACTION_UP:
                    // 两次的偏移量
                    int offsetX = Math.abs(rawX - lastX);
                    if (offsetX > slop) {
                        Log.d("lizejun", "onInterceptTouchEvent: ACTION_MOVE" + offsetX);
                    }
                    break;
                default:
                    break;
            }
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }


    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public boolean isCanScroll() {
        return isCanScroll;
    }

    public void setCanScroll(boolean canScroll) {
        isCanScroll = canScroll;
    }

}