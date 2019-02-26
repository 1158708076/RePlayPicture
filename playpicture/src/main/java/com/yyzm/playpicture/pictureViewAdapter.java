package com.yyzm.playpicture;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 DGW
 * 创建时间 2017/10/31
 * 本类相关 自定义轮播图适配器
 */

public abstract class pictureViewAdapter<T> extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private List<T> data = new ArrayList<>();
    private Context mContext;
    private pictureView mView;
    private OnPictureViewItemClickListener listener;

    public pictureViewAdapter(List<T> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    public pictureViewAdapter(List<T> data, Context mContext, OnPictureViewItemClickListener listener) {
        this.data = data;
        this.mContext = mContext;
        this.listener = listener;
    }

    public void init(pictureView viewPager, pictureViewAdapter adapter) {
        mView = viewPager;
        mView.setAdapter(adapter);
        mView.addOnPageChangeListener(this);
        if (data == null || data.size() == 0) {
            return;
        }
        //设置初始为中间，这样一开始就能够往左滑动了
        int position = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % getRealCount();
        mView.setCurrentItem(position);
        mView.onStart();
        mView.updatePointView(getRealCount());
    }

    public void setListener(OnPictureViewItemClickListener listener) {
        this.listener = listener;
    }


    public void add(T t) {
        data.add(t);
        notifyDataSetChanged();
        mView.updatePointView(getRealCount());
    }

    @Override
    public int getCount() {
        return data == null ? 0 : Integer.MAX_VALUE;
    }

    public int getRealCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.pictureview, container, false);
        ImageView iv = view.findViewById(R.id.imageView);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemclick(data.get(position % getRealCount()), position % getRealCount());
                }
            }
        });
        int newposition = position % getRealCount();
        loadImage(iv, newposition, data.get(position % getRealCount()));
        container.addView(view);
        return view;
    }

    public abstract void loadImage(ImageView view, int position, T t);

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mView.onPageSelected(position % getRealCount());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public interface OnPictureViewItemClickListener<T> {
        void onItemclick(T t, int position);
    }
}
