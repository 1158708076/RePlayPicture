package com.yyzm.playpicture;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者 DGW
 * 创建时间 2017/10/31
 * 本类相关 自定义轮播图不带小圆点
 */

public class pictureView extends ViewPager {
    private static String TAG = "pictureView";
    private List<Integer> plist;
    private int currentItem;
    private Timer mTimer;
    private AutoTask mTask;
    private AutoHandle autoHandle = new AutoHandle();
    private boolean isAuto = true;
    private float viewHeight;//控件高度
    private float viewWidth;//控件宽度


    private class AutoHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    public pictureView(Context context) {
        super(context);
    }

    public pictureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void initview(pictureView viewPager, pictureViewAdapter adapter) {
        adapter.init(viewPager, adapter);
    }

    public void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.pictureView);
        viewHeight = typedArray.getDimension(R.styleable.pictureView_height, 300);
        viewWidth = typedArray.getDimension(R.styleable.pictureView_width, getMeasuredWidth());
        typedArray.recycle();
    }

    public void updatePointView(int size) {
        if (getParent() instanceof pointPictureView) {
            pointPictureView pager = (pointPictureView) getParent();
            pager.initPointView(size);
        } else {
            Log.e(TAG, "parent view not be AutoScrollViewPager");
        }
    }

    public void onPageSelected(int position) {
        pointPictureView pager = (pointPictureView) getParent();
        pager.updatePointView(position);
    }


    private class AutoTask extends TimerTask {
        @Override
        public void run() {
            autoHandle.post(runnable);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            currentItem = getCurrentItem();
            if (currentItem == getAdapter().getCount() - 1) {
                currentItem = 0;
            } else {
                currentItem++;
            }
            setCurrentItem(currentItem);
        }
    };

    public void onStart() {
        onStop();
        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new AutoTask(), 3000, 3000);
    }

    public void onResume() {
        onStart();
    }

    private void onStop() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    public void onDestroy() {
        onStop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onStop();
                break;
            case MotionEvent.ACTION_UP:
                onResume();
                break;
        }
        return super.onTouchEvent(ev);
    }

}
