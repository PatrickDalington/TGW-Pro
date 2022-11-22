package com.tgw.tgwpro.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tgw.tgwpro.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference().child("Users");
        initViews();
    }
    private void initViews(){
        ListView settingListView = findViewById(R.id.settingsList);
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Arrays.asList("Set up bank info", "Profile"));
        settingListView.setAdapter(listAdapter);
        settingListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(SettingsActivity.this, String.valueOf(i), Toast.LENGTH_LONG).show();
        if(i==0){
            Dialog accountDetails=new Dialog(SettingsActivity.this);
            accountDetails.setContentView(R.layout.bank_details_layout);
            EditText accountN,pinCode,accountName;
            accountN=accountDetails.findViewById(R.id.account_number_edit);
            pinCode=accountDetails.findViewById(R.id.account_withdraw_pin_edit);
            accountName=accountDetails.findViewById(R.id.account_name_edit);
            Button save=accountDetails.findViewById(R.id.saveDetails);
            save.setOnClickListener(v->{
                if(!accountN.getText().toString().isEmpty() & !pinCode.getText().toString().isEmpty() & !accountName.getText().toString().isEmpty()){
                    if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                        Map<String,Object> bankDetails=new HashMap<>();
                        bankDetails.put("accountName",accountName.getText().toString());
                        bankDetails.put("withdrawPin",pinCode.getText().toString());
                        bankDetails.put("accountNumber",accountN.getText().toString());
                        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(bankDetails);
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Toast.makeText(SettingsActivity.this, "successfully added", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }else {
                    Toast.makeText(this, "please complete details", Toast.LENGTH_SHORT).show();
                }
            });

            accountDetails.show();
        }else if(i==1){
            Dialog accountDetails=new Dialog(SettingsActivity.this);
            accountDetails.setContentView(R.layout.profile_layout);
            accountDetails.show();
        }

    }


}