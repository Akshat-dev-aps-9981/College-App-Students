package com.aksapps.nehrucollagestudents.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.aksapps.nehrucollagestudents.R;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;

public class HomeFragment extends Fragment {
    private SliderLayout sliderLayout;
    private TextView mapTxt;
    private ImageButton map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sliderLayout = view.findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setSliderTransformAnimation(SliderAnimations.CUBEOUTDEPTHTRANSFORMATION);
        sliderLayout.setScrollTimeInSec(5);

        setSliderViews();

        mapTxt = view.findViewById(R.id.map_txt);
        map = view.findViewById(R.id.map);

        mapTxt.setSelected(true);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });

        return view;
    }

    private void openMap() {
        Uri uri = Uri.parse("geo:0, 0?q=Govt. Nehru Collage Agar Malwa");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }

    private void setSliderViews() {
        for (int i = 0; i < 4; i++) {
            DefaultSliderView sliderView = new DefaultSliderView(getContext());

            switch (i) {
                case 0:
                    sliderView.setDescription("Advertisement");
//                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/govt-nehru-collage-agar.appspot.com/o/Collage%2Fadvertisement.png?alt=media&token=107c7332-4edf-4373-b280-c9d351c8c20b");
                    sliderView.setImageDrawable(R.drawable.advertisement);
                    break;
                case 1:
                    sliderView.setDescription("Collage 1");
//                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/govt-nehru-collage-agar.appspot.com/o/Collage%2Fcollage1.jpg?alt=media&token=58d0d936-b5a3-4eaf-9304-d28d34f895ee");
                    sliderView.setImageDrawable(R.drawable.collage_1);
                    break;
                case 2:
                    sliderView.setDescription("Collage 2");
//                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/govt-nehru-collage-agar.appspot.com/o/Collage%2Fcollage2.jpg?alt=media&token=76485ac5-2f76-4beb-844c-d5d6253e5428");
                    sliderView.setImageDrawable(R.drawable.collage_2);
                    break;
                case 3:
                    sliderView.setDescription("Collage 3");
//                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/govt-nehru-collage-agar.appspot.com/o/Collage%2Fcollage3.jpg?alt=media&token=e08b72f4-124d-482e-aafe-63d02a489927");
                    sliderView.setImageDrawable(R.drawable.collage_3);
                    break;
            }
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderLayout.addSliderView(sliderView);
        }
    }
}