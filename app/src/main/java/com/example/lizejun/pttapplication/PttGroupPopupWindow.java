package com.example.lizejun.pttapplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import java.util.List;

/**
 * create by lizejun
 * date 2018/9/17
 */
public class PttGroupPopupWindow {

    private static PttGroupPopupWindow instance;
    private static int offsetX;
    private static Context mContext;
    private static int popupWindowWidth;
    private static int popupWindowheight;
    private final LayoutInflater layoutInflater;

    private PttGroupPopupWindow(Context context) {
        mContext = context.getApplicationContext();
        WindowManager mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = mWindowManager.getDefaultDisplay();
        offsetX = (int) (display.getWidth() * 0.05);
        popupWindowWidth = (int) (display.getWidth() * 0.9);
        popupWindowheight = (int) (display.getHeight() * 0.5);
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    public static PttGroupPopupWindow getInstance(Context context) {
        if (instance == null) {
            synchronized (PttGroupPopupWindow.class) {
                instance = new PttGroupPopupWindow(context);
            }
        }
        return instance;
    }


//    public void showGroupsPopupWindow(View targetView, final String currentGroupId, PopupWindow.OnDismissListener onDismissListener, final GroupListAdapter.OnItemClickListener onItemClickListener) {
//        View contentView = layoutInflater.inflate(R.layout.rc_ptt_groups_dialog, null);
//
//        final PopupWindow popupWindow = new PopupWindow(contentView, popupWindowWidth, popupWindowheight, false);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        popupWindow.setOutsideTouchable(true);
//        popupWindow.setOnDismissListener(onDismissListener);
//        popupWindow.showAsDropDown(targetView, offsetX,10);
//
//        final RecyclerView popupRecyclerView = contentView.findViewById(R.id.group_list);
//        final FrameLayout container = contentView.findViewById(R.id.rc_ptt_popup_loading_container);
//        View loadingView = new SpinView(mContext);
//        container.addView(loadingView,40,40);
//
//        popupRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
//        popupRecyclerView.setHasFixedSize(true);
//        final GroupListAdapter groupListAdapter = new GroupListAdapter(mContext, currentGroupId,null);
//        groupListAdapter.setOnItemClickListener(new GroupListAdapter.OnItemClickListener() {
//            @Override
//            public void onClick(String targetGroupId) {
//                popupWindow.dismiss();
//                onItemClickListener.onClick(targetGroupId);
//            }
//        });
//        popupRecyclerView.setAdapter(groupListAdapter);
//
//        GroupTask.getInstance().getPTTGroups(new SimpleResultCallback<List<GroupInfo>>() {
//            @Override
//            public void onSuccessOnUiThread(List<GroupInfo> groupInfos) {
//                container.setVisibility(View.GONE);
//                groupListAdapter.setData(groupInfos);
//                groupListAdapter.notifyDataSetChanged();
//            }
//        });
//
//
//
//
//    }

}
