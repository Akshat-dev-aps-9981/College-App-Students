package com.aksapps.nehrucollagestudents.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.aksapps.nehrucollagestudents.Adapters.BranchAdapter;
import com.aksapps.nehrucollagestudents.Models.BranchModel;
import com.aksapps.nehrucollagestudents.R;

import java.util.ArrayList;

public class AboutFragment extends Fragment {
    private ViewPager viewPager;
    private BranchAdapter adapter;
    private ArrayList<BranchModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        list = new ArrayList<>();
        list.add(new BranchModel(R.drawable.computer_science, "Computer Science", "Computer Science is our main branch of this collage."));
        list.add(new BranchModel(R.drawable.maths, "Mathematics", "Mathematics is our second main branch of this collage."));
        list.add(new BranchModel(R.drawable.physics, "Physics", "Physics is our third main branch of this collage."));
        list.add(new BranchModel(R.drawable.chemistry, "Chemistry", "Chemistry is our additional branch of this collage."));
        adapter = new BranchAdapter(getContext(), list);
        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        ImageView imageView = view.findViewById(R.id.collage_image);
//        Glide.with(getContext()).load("").into(imageView);
        imageView.setImageResource(R.drawable.collage_1);

        return view;
    }
}