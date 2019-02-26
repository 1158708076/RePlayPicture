package com.yyzm.playpicture;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import static android.view.Gravity.CENTER;

/**
 * 作者 DGW
 * 创建时间 2017/10/31
 * 本类相关 自定义小圆点轮播图
 */

public class pointPictureView extends RelativeLayout {

    private pictureView mViewPager;
    private Context mContext;
    private LinearLayout layout;
    private int checkpicture;
    private int normalpicture;
    public static int right = ALIGN_PARENT_RIGHT;
    public static int left = ALIGN_PARENT_LEFT;
    public static int center = CENTER_IN_PARENT;
    private int gravity = center;
    private float viewHeight;//控件高度
    private float viewWidth;//控件宽度

    public pointPictureView(Context context) {
        super(context);
    }

    public pointPictureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.pointPictureView);
        checkpicture = typedArray.getResourceId(R.styleable.pointPictureView_checkdraw, 0);
        normalpicture = typedArray.getResourceId(R.styleable.pointPictureView_normaldraw, 0);
        typedArray.recycle();

        mContext = context;
        mViewPager = new pictureView(context);
        layout = new LinearLayout(context);
        addView(mViewPager);
    }

    public void setAdapter(pictureViewAdapter adapter) {
        if (adapter != null) {
            mViewPager.initview(mViewPager, adapter);
        }
    }

    public void setPointgravity(int gravity) {
        this.gravity = gravity;
    }

    public pictureView getViewPager() {
        return mViewPager;
    }

    public void initPointView(int size) {
        layout = new LinearLayout(mContext);
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 20);
            params.leftMargin = 8;
            params.gravity = CENTER;
            imageView.setLayoutParams(params);
            if (i == 0) {
                imageView.setBackgroundResource(checkpicture);
            } else {
                imageView.setBackgroundResource(normalpicture);
            }
            layout.addView(imageView);
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(gravity);
        layoutParams.setMargins(0, 0, 0, 10);
        layout.setLayoutParams(layoutParams);
        addView(layout);
    }

    public void updatePointView(int position) {
        int size = layout.getChildCount();
        for (int i = 0; i < size; i++) {
            ImageView imageView = (ImageView) layout.getChildAt(i);
            if (i == position) {
                imageView.setBackgroundResource(checkpicture);
            } else {
                imageView.setBackgroundResource(normalpicture);
            }

        }
    }

    public void onDestroy() {
        if (mViewPager != null) {
            mViewPager.onDestroy();
        }
    }
}
