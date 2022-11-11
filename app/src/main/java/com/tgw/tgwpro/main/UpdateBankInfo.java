package com.tgw.tgwpro.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.tgw.tgwpro.R;
import com.tgw.tgwpro.register.WelcomePage;

import java.util.ArrayList;
import java.util.List;

public class UpdateBankInfo extends AppCompatActivity {

    AutoCompleteTextView spinner;
    List<String> banks = new ArrayList<>();
    Button con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bank_info);

        spinner = findViewById(R.id.banks);
        con = findViewById(R.id.con);


        //List of banks with international Authorization;
        banks.add("Access Bank Plc");
        banks.add("Fidelity Bank Plc");
        banks.add("First City Monument Bank (FCMB)");
        banks.add("First Bank of Nigeria");
        banks.add("Guaranty Trust Bank (GTCO)");
        banks.add("Union Bank of Nigeria Plc");
        banks.add("United Bank for Africa (UBA)");
        banks.add("Zenith Bank Plc");

        //List of banks with Regional Authorization;
        banks.add("Citibank");
        banks.add("Ecobank");
        banks.add("Heritage Bank");
        banks.add("Keystone Bank");
        banks.add("Polaris Bank");
        banks.add("Stanbic IBTC Bank");
        banks.add("Standard Chartered Bank");
        banks.add("Sterling Bank");
        banks.add("Titan Trust Bank");
        banks.add("Unity Bank");
        banks.add("WEMA Bank");


        //List of banks with Regional Authorization;
        banks.add("Globus Bank Limited");
        banks.add("SunTrust Bank");
        banks.add("Providus Bank");


        //List of Non-Interest Banks in Nigeria
        banks.add("Jaiz Bank Plc");
        banks.add("TAJBank Limited");

        //Microfinance Banks
        banks.add("Mutual Trust Microfinance Bank");
        banks.add("Finca Microfinance Bank Limited");
        banks.add("Fina Trust Microfinance Bank");
        banks.add("Accion Microfinance Bank");
        banks.add("Peace Microfinance Bank");
        banks.add("Infinity Microfinance Bank");
        banks.add("Pearl Microfinance Bank Limited");
        banks.add("Renmoney MFB");

        //List of Online-Only Microfinance Banks in Nigeria
        banks.add("Kuda Bank");


        ArrayAdapter bankList = new ArrayAdapter(this,
                R.layout.banks_layout, R.id.bankName, banks);

        spinner.setAdapter(bankList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bankList.getView(position,view,parent);
                Toast.makeText(UpdateBankInfo.this, String.valueOf(banks.get(position)), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}