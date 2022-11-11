package com.tgw.tgwpro.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tgw.tgwpro.R;
import com.tgw.tgwpro.adapters.PromoAdapter;
import com.tgw.tgwpro.models.Promo_Model;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {


    RecyclerView recyclerView;
    PromoAdapter promoAdapter;
    Promo_Model model;
    DatabaseReference promoRef;

    List<Promo_Model> mPromos;
    public Home() {

    }


    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = view.findViewById(R.id.recycler);

        promoRef = FirebaseDatabase.getInstance().getReference("Promotions");

        LinearLayoutManager lm = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(lm);

        mPromos = new ArrayList<>();

        loadPromotions();
        return view;
    }

    private void loadPromotions(){
        promoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    mPromos.clear();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        model = dataSnapshot.getValue(Promo_Model.class);
                        mPromos.add(model);
                    }
                    promoAdapter = new PromoAdapter(getActivity(), mPromos);
                    recyclerView.setAdapter(promoAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        promoAdapter.onStop();
    }
}