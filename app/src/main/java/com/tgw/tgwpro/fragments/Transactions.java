package com.tgw.tgwpro.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tgw.tgwpro.R;
import com.tgw.tgwpro.adapters.CashTableAdapter;
import com.tgw.tgwpro.main.Table;
import com.tgw.tgwpro.models.Cash_Model;

import java.util.ArrayList;

public class Transactions extends Fragment {


    public Transactions() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    CashTableAdapter adapter;
    Cash_Model model;
    DatabaseReference ref;
    ArrayList<Cash_Model> list;
    FirebaseUser firebaseUser;
    public static Transactions newInstance(String param1, String param2) {
        Transactions fragment = new Transactions();
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
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);


        list = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recycler);

        LinearLayoutManager lm = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(lm);

        loadTable();

        return view;
    }

    private void loadTable(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("Payments_Record").child(firebaseUser.getUid())
                .child("cash");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        model = dataSnapshot.getValue(Cash_Model.class);
                        list.add(model);
                    }

                    adapter = new CashTableAdapter(getActivity(),list);
                    recyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}