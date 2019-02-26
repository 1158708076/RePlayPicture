package com.yyzm.autoplaypicture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.yyzm.playpicture.pictureViewAdapter;
import com.yyzm.playpicture.pointPictureView;
import com.yyzm.playpicture.pictureView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private pictureView pictureView;
    private pointPictureView pointPictureView;
    private pictureViewAdapter adapter;
    private List<Integer> olist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setData();
        initView();
    }

    private void initView() {
        pictureView = findViewById(R.id.pictureview);
        pointPictureView = findViewById(R.id.pointPictureView);

        adapter = new pictureViewAdapter(olist, MainActivity.this, new pictureViewAdapter.OnPictureViewItemClickListener() {
            @Override
            public void onItemclick(Object o, int position) {

                Toast.makeText(MainActivity.this, "你点击了第" + position + "张", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public void loadImage(ImageView view, int position, Object o) {
                view.setBackgroundResource((Integer) o);
            }
        };
        pictureView.setAdapter(adapter);
        pointPictureView.setAdapter(adapter);
    }

    private void setData() {
        int[] arr = {R.drawable.image1, R.drawable.image2, R.drawable.image3};
        for (int i : arr) {
            olist.add(i);
        }
    }
}

