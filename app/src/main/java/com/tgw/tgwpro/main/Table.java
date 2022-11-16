package com.tgw.tgwpro.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tgw.tgwpro.R;
import com.tgw.tgwpro.adapters.CashTableAdapter;
import com.tgw.tgwpro.models.Cash_Model;

import java.util.ArrayList;

public class Table extends AppCompatActivity {

    RecyclerView recyclerView;
    CashTableAdapter adapter;
    Cash_Model model;
    DatabaseReference ref;
    ArrayList<Cash_Model> list;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = findViewById(R.id.recycler);

        LinearLayoutManager lm = new LinearLayoutManager(Table.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(lm);

        loadTable();
    }

    private void loadTable(){
        list = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("Payments_Record").child(firebaseUser.getUid())
                .child("cash");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list.clear();

                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        model = dataSnapshot.getValue(Cash_Model.class);
                        list.add(model);
                    }

                    adapter = new CashTableAdapter(Table.this,list);
                    recyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}