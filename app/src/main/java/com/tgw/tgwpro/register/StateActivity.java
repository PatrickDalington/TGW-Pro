package com.tgw.tgwpro.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.tgw.tgwpro.R;
import com.tgw.tgwpro.adapters.StatesAdapter;
import com.tgw.tgwpro.main.UpdateBankInfo;

public class StateActivity extends AppCompatActivity implements StatesAdapter.ItemClickListener {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    StatesAdapter statesAdapter;
    AutoCompleteTextView ac;
String[] states = {
        "Abia", "Adamawa", "Akwa Ibom", "Anambra", "Bauchi", "Bayelsa", "Benue", "Borno", "Cross River",
        "Delta", "Ebonyi", "Edo", "Ekiti", "Enugu", "FCT - Abuja", "Gombe", "Imo", "Jigawa", "Kaduna",
        "Kano", "Katsina", "Kebbi", "Kogi", "Kwara", "Lagos", "Nasarawa", "Niger", "Ogun",
        "Ondo", "Osun", "Oyo", "Plateau", "Rivers", "Sokoto", "Taraba", "Yobe", "Zamfara"
        };

    Button con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);

        recyclerView = findViewById(R.id.recycler);
        ac = findViewById(R.id.view);
        con = findViewById(R.id.con);

        con.setOnClickListener(v->{
            if (TextUtils.isEmpty(ac.getText())) {
                ac.setError("Enter or select your state");
                ac.requestFocus();
            }else{
                SharedPreferences.Editor editor = getSharedPreferences("SectionB",MODE_PRIVATE).edit();

                editor.putString("state",ac.getText().toString());
                editor.apply();

                Intent intent = new Intent(this, PhoneActivity.class);
                startActivity(intent);
            }

        });


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.states_layout,R.id.stateName,states);
        ac.setAdapter(arrayAdapter);

        LinearLayoutManager lm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);

        statesAdapter = new StatesAdapter(this, states, this);
            recyclerView.setAdapter(statesAdapter);

    }

    @Override
    public void onItemClick(View view, int position) {
        ac.setText(states[position]);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("SectionB", MODE_PRIVATE);
        if (sharedPreferences !=null) {
            String st = sharedPreferences.getString("state", "");
            ac.setText(st);
        }
    }
}