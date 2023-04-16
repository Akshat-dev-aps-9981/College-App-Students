package com.aksapps.nehrucollagestudents.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.aksapps.nehrucollagestudents.R;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class FullImageView extends AppCompatActivity {
    private PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_view);

        String image = getIntent().getStringExtra("image");
        photoView = findViewById(R.id.image_view_full);

        Glide.with(this).load(image).into(photoView);
    }
}