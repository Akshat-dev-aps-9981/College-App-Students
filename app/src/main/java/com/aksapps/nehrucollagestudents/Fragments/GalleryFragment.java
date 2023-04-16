package com.aksapps.nehrucollagestudents.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aksapps.nehrucollagestudents.Adapters.GalleryAdapter;
import com.aksapps.nehrucollagestudents.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    RecyclerView convoRecycler, othersRecycler;
    GalleryAdapter adapter;

    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        convoRecycler = view.findViewById(R.id.convo_recycler);
        othersRecycler = view.findViewById(R.id.others_recycler);

        reference = FirebaseDatabase.getInstance().getReference().child("Gallery");

        getConvoImage();
        getOthersImage();

        return view;
    }

    private void getOthersImage() {
        reference.child("Other Events").addValueEventListener(new ValueEventListener() {
            ArrayList<String> imagesList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String data = (String) snapshot.getValue().toString();
                    imagesList.add(data);
                }

                adapter = new GalleryAdapter(getContext(), imagesList);
                othersRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
                othersRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void getConvoImage() {
        reference.child("Convocation").addValueEventListener(new ValueEventListener() {
            ArrayList<String> imagesList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String data = (String) snapshot.getValue().toString();
                    imagesList.add(data);
                }

                adapter = new GalleryAdapter(getContext(), imagesList);
                convoRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
                convoRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}