package com.aksapps.nehrucollagestudents.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aksapps.nehrucollagestudents.Adapters.NoticeAdapter;
import com.aksapps.nehrucollagestudents.Models.NoticeData;
import com.aksapps.nehrucollagestudents.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NoticeFragment extends Fragment {
    private RecyclerView noticerRecyclerView;
    private ProgressBar progressBar;
    private ArrayList<NoticeData> list;
    private NoticeAdapter adapter;

    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        noticerRecyclerView = view.findViewById(R.id.delete_notice_recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);
        reference = FirebaseDatabase.getInstance().getReference().child("Notice");

        noticerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        noticerRecyclerView.setHasFixedSize(true);

        getNotice();
        return view;
    }

    private void getNotice() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NoticeData data = snapshot.getValue(NoticeData.class);
                    list.add(0,data);
                    adapter = new NoticeAdapter(getContext(), list);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    noticerRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Something went wrong: " + databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}