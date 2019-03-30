package com.example.lizejun.pttapplication;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public abstract class BasePttActivity extends BaseNoActionBarActivity {

    public TextView textViewMember;
    public TextView textPttPeopleNum;
    public PttViewPager contentViewpager;
    public ImageView goLeftBackground;
    public ImageView goRightBackground;
    public ImageView btnCloseMic;
    public ImageView btnPtt;
    public ImageView call;
    public ImageView openVoice;
    public ImageView callVideo;
    public ImageView imgShutdown;
    public LinearLayout layoutMiddle;
    public LinearLayout topInfoLayout;
    public ImageView imgDown;
    public ImageView imgScaleWindow;
    public TextView textGroupInfo;
    private List<Fragment> fragmentList;
    public PopupWindow popupWindow;
    public LayoutInflater layoutInflater;
    public WindowManager windowManager;
    public Display display;
    private int showX;
    public List<GroupItemBean> groupList;
    public ImageView imgSpeaker;
    public TextView speakName;
    public TextView speakTime;
    public FrameLayout rootLayout;

    /**
     * 是否关闭麦克风
     */
    private boolean closeMic;
    /**
     * 是否开启免提
     */
    private boolean openMianti;
    /**
     * 语音对讲模式下  是否切换成视频对讲
     */
    private boolean videoPtt;
    /**
     * 视频对讲模式下是否打开摄像头
     */
    private boolean openCamera;
    /**
     * 视频对讲模式下是否切换成语音对讲
     */
    private boolean voicePtt;

    /**
     * 切换背景的箭头显示状态 true为正在显示   false为已经不显示
     */
    private boolean switchArrowStatus = true;

    /**
     * 是否需要显示切换的箭头
     */
    private boolean isOnlyOneFragment;


    private Animation openRotateAnimation;
    private Animation closeRotateAnimation;
    private Handler arrowAnimHandler;
    public int currentPageIndex;
    private ObjectAnimator leftDismissAnimation;
    private ObjectAnimator rightDismissAnimation;
    private ObjectAnimator leftDisappearAnimation;
    private ObjectAnimator rightDisappearAnimation;
    private Animator.AnimatorListener disappearAnimatorListener;
    private Animator.AnimatorListener dismissAnimatorListener;
    private Runnable dissmissAnimRunnable;
    private Runnable dissmisssRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rc_ptt_activity_base_ptt);
        initViews();
        initData();
        setArrowStatus();


        textGroupInfo.setText(getString(R.string.rc_ptt_current_group_info, 30, 20));
        speakName.setText(getString(R.string.rc_ptt_speaker, "李达康"));

        contentViewpager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        contentViewpager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        contentViewpager.setOffscreenPageLimit(fragmentList.size());
        contentViewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (isOnlyOneFragment) {
                    return;
                }

                currentPageIndex = position;
                if (position == 0) {
                    goLeftBackground.setVisibility(View.GONE);
                } else if (position == 2) {
                    goRightBackground.setVisibility(View.GONE);
                } else {
                    goLeftBackground.setVisibility(View.VISIBLE);
                    goRightBackground.setVisibility(View.VISIBLE);
                }
            }
        });

        //一个fragment的时候是紧急呼叫界面的无背景fragment   多个fragment需要设置

        //首先看有没有悬浮窗进来的遗留页面
        Intent intent = getIntent();
        int viewIndex = intent.getIntExtra("viewIndex",-1);
        if (viewIndex>-1) {
            contentViewpager.setCurrentItem(viewIndex);
            if (viewIndex ==1){
                initNoBackGroundStatus();
            }else if(viewIndex == 2){
                initMapBackgroundStatus();
            }else{
               //todo chatfragment对应的界面

            }

            return;
        }

       if(fragmentList.size()==1){
           initNoBackGroundStatus();
       }


    }

    private void setArrowStatus() {
        if (fragmentList.size() == 1) {
            isOnlyOneFragment = true;
        } else {
            isOnlyOneFragment = false;
        }

        if (isOnlyOneFragment) {
            goLeftBackground.setVisibility(View.GONE);
            goRightBackground.setVisibility(View.GONE);
        } else {
            arrowAnimHandler = new Handler();
            dissmisssRunnable = new Runnable() {
                @Override
                public void run() {
                    goLeftBackground.setVisibility(View.GONE);
                    goRightBackground.setVisibility(View.GONE);
                    switchArrowStatus = false;
                }
            };
            dissmissAnimRunnable = new Runnable() {
                @Override
                public void run() {
                    if (goLeftBackground.getVisibility() == View.VISIBLE) {
                        leftDismissAnimation.start();
                    }
                    if (goRightBackground.getVisibility() == View.VISIBLE) {
                        rightDismissAnimation.start();
                    }
                }
            };
            arrowAnimHandler.postDelayed(dissmissAnimRunnable, 8000);

            dismissAnimatorListener = new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    switchArrowStatus = false;
                    goLeftBackground.setVisibility(View.GONE);
                    goRightBackground.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            };
            disappearAnimatorListener = new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    switchArrowStatus = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    arrowAnimHandler.postDelayed(dissmisssRunnable, 8000);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            };

            leftDismissAnimation = ObjectAnimator.ofFloat(goLeftBackground, "alpha", 1f, 0f);//透明度
            leftDismissAnimation.setDuration(1000);
            leftDismissAnimation.addListener(dismissAnimatorListener);
            rightDismissAnimation = ObjectAnimator.ofFloat(goRightBackground, "alpha", 1f, 0f);//透明度
            rightDismissAnimation.setDuration(1000);
            rightDismissAnimation.addListener(dismissAnimatorListener);

            leftDisappearAnimation = ObjectAnimator.ofFloat(goLeftBackground, "alpha", 0f, 1f);//透明度
            leftDisappearAnimation.setDuration(180);
            leftDisappearAnimation.addListener(disappearAnimatorListener);
            rightDisappearAnimation = ObjectAnimator.ofFloat(goRightBackground, "alpha", 0f, 1f);//透明度
            rightDisappearAnimation.setDuration(180);
            rightDisappearAnimation.addListener(disappearAnimatorListener);


            contentViewpager.setListener(new ShowArrowListener() {
                @Override
                public void show() {
                    if (switchArrowStatus) {
                        return;
                    }

                    if (currentPageIndex == 0) {
                        goRightBackground.setVisibility(View.VISIBLE);
                        rightDisappearAnimation.start();
                    } else if (currentPageIndex == 2) {
                        goLeftBackground.setVisibility(View.VISIBLE);
                        leftDisappearAnimation.start();
                    } else {
                        goLeftBackground.setVisibility(View.VISIBLE);
                        goRightBackground.setVisibility(View.VISIBLE);

                        leftDisappearAnimation.start();
                        rightDisappearAnimation.start();
                    }
                }
            });
        }
    }

    /**
     * 无背景页面的状态初始化
     */
    private void initNoBackGroundStatus() {
        topInfoLayout.setBackgroundColor(getResources().getColor(R.color.rc_ptt_transparent));
    }

    /**
     * 地图背景页面的UI设置
     */
    private void initMapBackgroundStatus() {
        topInfoLayout.setBackgroundColor(getResources().getColor(R.color.rc_ptt_shadow));
    }

    private void initViews() {

        windowManager = getWindowManager();
        layoutInflater = getLayoutInflater();
        // 获取屏幕宽、高用
        display = windowManager.getDefaultDisplay();
        textGroupInfo = findViewById(R.id.text_group_info);
        contentViewpager = findViewById(R.id.layout_content);
        goLeftBackground = findViewById(R.id.img_left);
        goRightBackground = findViewById(R.id.img_right);
        btnCloseMic = findViewById(R.id.btn_quite);
        btnPtt = findViewById(R.id.btn_ptt);
        call = findViewById(R.id.call);
        callVideo = findViewById(R.id.call_video);
        openVoice = findViewById(R.id.open_voice);
        imgShutdown = findViewById(R.id.img_shutdown);
        imgDown = findViewById(R.id.img_down);
        imgScaleWindow = findViewById(R.id.img_scale_window);
        textPttPeopleNum = findViewById(R.id.text_ptt_people_num);
        textViewMember = findViewById(R.id.text_view_member);
        topInfoLayout = findViewById(R.id.top_info_layout);
        imgSpeaker = findViewById(R.id.img_speaker);
        speakName = findViewById(R.id.text_speaker);
        speakTime = findViewById(R.id.text_speak_time);
        rootLayout = findViewById(R.id.ptt_root_layout);
        contentViewpager.setCanScroll(false);


        goLeftBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnlyOneFragment) {
                    return;
                }

                int currentItem = contentViewpager.getCurrentItem();
                if (currentItem == 1) {
                    contentViewpager.setCurrentItem(0, true);
                } else if (currentItem == 2) {
                    contentViewpager.setCurrentItem(1);
                    initNoBackGroundStatus();
                }
            }
        });
        goRightBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnlyOneFragment) {
                    return;
                }

                int currentItem = contentViewpager.getCurrentItem();
                if (currentItem == 1) {
                    contentViewpager.setCurrentItem(2, true);
                    initMapBackgroundStatus();
                } else if (currentItem == 0) {
                    contentViewpager.setCurrentItem(1);
                    initNoBackGroundStatus();
                }
            }
        });

        //ptt界面不能点击关闭麦克风
//        btnCloseMic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (closeMic) {
//                    btnCloseMic.setImageResource(R.drawable.rc_ptt_selector_be_quite);
//                    Toast.makeText(BasePttActivity.this, v.getId() + "打开了mic", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    btnCloseMic.setImageResource(R.drawable.rc_ptt_selector_cancle_quite);
//                    Toast.makeText(BasePttActivity.this, v.getId() + "关闭了mic", Toast.LENGTH_SHORT).show();
//
//                }
//                closeMic = !closeMic;
//            }
//        });
        btnPtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PttToastUtil.getInstance(BasePttActivity.this).makeCountDownToast("讲话时间即将结束",10000);
//                PttToastUtil.getInstance(BasePttActivity.this).showSureToast("话权到手,请按住讲话",1000);
//                PttToastUtil.getInstance(BasePttActivity.this).showWarnningToast("话权到手,请按住讲话",1000);
//                PttToastUtil.getInstance(BasePttActivity.this).showLoadingToast("话权到手,请按住讲话",10000);

                showForcePullAlertDialog("woshishei");


            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BasePttActivity.this, v.getId() + "", Toast.LENGTH_SHORT).show();

            }
        });
        callVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoPtt) {
                    callVideo.setImageResource(R.drawable.rc_ptt_selector_video);
                    Toast.makeText(BasePttActivity.this, v.getId() + "关闭了摄像头", Toast.LENGTH_SHORT).show();

                } else {
                    callVideo.setImageResource(R.drawable.rc_ptt_selector_close_canera);
                    Toast.makeText(BasePttActivity.this, v.getId() + "打开了摄像头", Toast.LENGTH_SHORT).show();
                }
                videoPtt = !videoPtt;

            }
        });
        openVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openMianti) {
                    openVoice.setImageResource(R.drawable.rc_ptt_selector_mianti);
                    Toast.makeText(BasePttActivity.this, v.getId() + "关闭了免提", Toast.LENGTH_SHORT).show();

                } else {
                    openVoice.setImageResource(R.drawable.rc_ptt_selector_cancle_mianti);
                    Toast.makeText(BasePttActivity.this, v.getId() + "打开了免提", Toast.LENGTH_SHORT).show();
                }
                openMianti = !openMianti;

            }
        });
        imgShutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BasePttActivity.this, v.getId() + "", Toast.LENGTH_SHORT).show();

            }
        });
        imgDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGroups();
            }
        });
        textGroupInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGroups();
            }
        });
        imgScaleWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BasePttActivity.this, v.getId() + "", Toast.LENGTH_SHORT).show();

            }
        });
        textPttPeopleNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BasePttActivity.this, v.getId() + "", Toast.LENGTH_SHORT).show();

            }
        });
        textViewMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 跳转群成员列表页
                Toast.makeText(BasePttActivity.this, v.getId() + "", Toast.LENGTH_SHORT).show();

            }
        });

        setViews();

    }

    /**
     * 子类复写  控制显示的view以及点击事件等
     */
    protected abstract void setViews();

    /**
     * 子类复写  控制显示的fragment等
     */
    protected abstract List<Fragment> setFragment();

    /**
     * 子类复写  切换ptt组时的列表
     *
     * @return
     */
    protected abstract List<GroupItemBean> setGroupList();

    /**
     * 被强制拉入别的组时显示的提示对话框
     */
    private void showForcePullAlertDialog(String GroupName) {
        PttGroupsDialogFragment pttGroupsDialogFragment = new PttGroupsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("groupName", GroupName);
        bundle.putInt("countdown", 3);
        pttGroupsDialogFragment.setArguments(bundle);
        pttGroupsDialogFragment.setCancelable(false);
        pttGroupsDialogFragment.show(getSupportFragmentManager(), "pttGroupsDialogFragment");
    }

    /**
     * 显示群组列表
     */
    private void showGroups() {
        groupList = new ArrayList<>();
//        GroupItemBean bean = new GroupItemBean("海能达项目1", false);
//        GroupItemBean bean1 = new GroupItemBean("海能达项目2", true);
//        GroupItemBean bean2 = new GroupItemBean("海能达项目3", false);
//        GroupItemBean bean3 = new GroupItemBean("海能达项目4", false);
//        GroupItemBean bean4 = new GroupItemBean("海能达项目5", false);
//        GroupItemBean bean5 = new GroupItemBean("海能达项目6", false);
//        GroupItemBean bean6 = new GroupItemBean("海能达项目7", false);
//        GroupItemBean bean7 = new GroupItemBean("海能达项目8", false);
//        GroupItemBean bean8 = new GroupItemBean("海能达项目9", false);
//        GroupItemBean bean9 = new GroupItemBean("海能达项目0", false);
//        GroupItemBean bean0 = new GroupItemBean("海能达项目11", false);
//        groupList.add(bean);
//        groupList.add(bean1);
//        groupList.add(bean2);
//        groupList.add(bean3);
//        groupList.add(bean4);
//        groupList.add(bean5);
//        groupList.add(bean6);
//        groupList.add(bean7);
//        groupList.add(bean8);
//        groupList.add(bean9);
//        groupList.add(bean0);

        groupList = setGroupList();

        if (popupWindow != null) {
            showPopup();
        } else {
            initPopup();
            showPopup();
        }
    }

    /**
     * 显示群组列表的 popupWindow
     */
    private void showPopup() {
        imgDown.startAnimation(openRotateAnimation);
        popupWindow.showAsDropDown(topInfoLayout, showX, 10);
    }

    /**
     * 初始化群组列表 popupWindow
     */
    private void initPopup() {
        openRotateAnimation = new
                RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //设置旋转变化动画对象
        openRotateAnimation.setDuration(500);
        openRotateAnimation.setFillAfter(true);//持续时间
        closeRotateAnimation = new
                RotateAnimation(180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //设置旋转变化动画对象
        closeRotateAnimation.setDuration(500);
        closeRotateAnimation.setFillAfter(true);//持续时间

        showX = (int) (display.getWidth() * 0.05);
        int popupWindowWidth = (int) (display.getWidth() * 0.9);
        int popupWindowheight = (int) (display.getHeight() * 0.5);

        View contentView = layoutInflater.inflate(R.layout.rc_ptt_groups_dialog, null);
        RecyclerView popupRecyclerView = contentView.findViewById(R.id.group_list);
        popupRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        popupRecyclerView.setHasFixedSize(true);
        GroupListAdapter groupListAdapter = new GroupListAdapter(this, groupList);
        groupListAdapter.setOnItemClickListener(new PttRecyclerviewItemClickListener() {

            @Override
            public void onClick(int position) {
                Toast.makeText(BasePttActivity.this, groupList.get(position).getGroupName(), Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
        popupRecyclerView.setAdapter(groupListAdapter);
        popupWindow = new PopupWindow(contentView, popupWindowWidth, popupWindowheight, false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                imgDown.startAnimation(closeRotateAnimation);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        fragmentList = new ArrayList<>();
//        chatBackgroundFragment = new ChatBackgroundFragment();
//        noBackGroundFragment = new NoBackGroundFragment();
//        mapBackGroundFragment = new ShareLocationFragment();
//        fragmentList.add(chatBackgroundFragment);
//        fragmentList.add(noBackGroundFragment);
//        fragmentList.add(mapBackGroundFragment);

        List<Fragment> fragments = setFragment();

        fragmentList.addAll(fragments);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (arrowAnimHandler != null) {
            arrowAnimHandler.removeCallbacks(dissmissAnimRunnable);
            arrowAnimHandler.removeCallbacks(dissmisssRunnable);
        }
    }
}
