package com.example.lizejun.pttapplication;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * create by lizejun
 * date 2018/9/6
 */
public class PttViewPager extends ViewPager {

    private boolean isCanScroll = true;
    private int lastX;
    private int slop = 150;

    private ShowArrowListener listener;


    public PttViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /* return false;//super.onTouchEvent(arg0); */
        if (!isCanScroll) {
            return false;
        } else {
            return super.onTouchEvent(arg0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!isCanScroll) {
            if(listener!=null){
                listener.show();
            }
            return false;
        } else {
            return super.onInterceptTouchEvent(event);
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

    public ShowArrowListener getListener() {
        return listener;
    }

    public void setListener(ShowArrowListener listener) {
        this.listener = listener;
    }

}