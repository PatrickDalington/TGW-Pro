package com.tgw.tgwpro.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.tgw.tgwpro.R;
import com.tgw.tgwpro.adapters.BankAdapter;

import java.util.ArrayList;
import java.util.List;

public class WelcomePage extends AppCompatActivity {


    Button con;
    EditText lName,fName;
    CheckBox checkBox;
    TextView errorText, login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);


        con = findViewById(R.id.con);
        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        checkBox = findViewById(R.id.check);
        errorText = findViewById(R.id.bb);
        login = findViewById(R.id.login);


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    errorText.setVisibility(View.GONE);
                }
            }
        });

        con.setOnClickListener(v->{
            if (checkBox.isChecked()) {
                if (TextUtils.isEmpty(fName.getText())) {
                    fName.setError("Enter first name");
                    fName.requestFocus();
                }else if (TextUtils.isEmpty(lName.getText())){
                    lName.setError("Enter last name");
                    lName.requestFocus();
                }else{
                    SharedPreferences.Editor editor = getSharedPreferences("SectionA",MODE_PRIVATE).edit();

                    editor.putString("fName",fName.getText().toString());
                    editor.putString("lName",lName.getText().toString());
                    editor.apply();

                    Intent intent = new Intent(WelcomePage.this, StateActivity.class);
                    startActivity(intent);
                }

            }else{
                errorText.setVisibility(View.VISIBLE);
                errorText.setTextColor(Color.RED);
                errorText.setText("Have you read and agree to the terms and condition?");
            }

        });


        login.setOnClickListener(v->{
            Intent intent = new Intent(WelcomePage.this, Login.class);
            startActivity(intent);
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences editor = getSharedPreferences("SectionA",MODE_PRIVATE);
        if (editor !=null){
            String fN = editor.getString("fName","");
            String lN = editor.getString("lName","");

            fName.setText(fN);
            lName.setText(lN);
        }
    }
}