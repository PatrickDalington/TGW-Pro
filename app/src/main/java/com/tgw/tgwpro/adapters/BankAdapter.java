package com.tgw.tgwpro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.tgw.tgwpro.R;

import java.util.List;

public class BankAdapter extends BaseAdapter {
    Context context;
    List<String> bankNames;
    LayoutInflater inflater;

    public BankAdapter(){

    }
    public BankAdapter(Context context, List<String> bankNames){
        this.context = context;
        this.bankNames = bankNames;
    }
    @Override
    public int getCount() {
       return bankNames.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.banks_layout,viewGroup);

        TextView bankName = view.findViewById(R.id.bankName);
        bankName.setText(bankNames.get(i));
        return view;
    }
}
