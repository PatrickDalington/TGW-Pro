package com.tgw.tgwpro.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.tgw.tgwpro.R;
import com.tgw.tgwpro.models.Cash_Model;

import java.util.List;

public class CashTableAdapter extends RecyclerView.Adapter<CashTableAdapter.ViewHolder> {
    Context context;
    List<Cash_Model> cashModels;
    Cash_Model model;
    LayoutInflater inflater;
    int pos;

    public CashTableAdapter(Context context, List<Cash_Model> cashModels){

        this.context = context;
        this.cashModels = cashModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_list_items, parent, false);
        return new ViewHolder(view);
    }

    public void setHeader (View view){
        view.setBackgroundResource(R.drawable.table_header_cell_bg);
    }

    public void setContent (View view){
        view.setBackgroundResource(R.drawable.table_content_cell_bg);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        pos = holder.getAdapterPosition();

        if (pos == 0){

            setHeader(holder.id);
            setHeader(holder.name);
            setHeader(holder.verification);
            setHeader(holder.amount);
            setHeader(holder.pay_date);
            setHeader(holder.cashable);

            holder.id.setText("ID");
            holder.name.setText("Company's ID");
            holder.verification.setText("Verification");
            holder.amount.setText("Earnings");
            holder.pay_date.setText("Pay Date");
            holder.cashable.setText("Cashable");



        }else {
            model = cashModels.get(holder.getAdapterPosition() -1);

            setContent(holder.id);
            setContent(holder.name);
            setContent(holder.verification);
            setContent(holder.amount);
            setContent(holder.pay_date);
            setContent(holder.cashable);

            holder.id.setText(model.getId());
            holder.name.setText(model.getCompany_name());
            holder.verification.setText(model.getTask_verification());
            holder.amount.setText(model.getAmount());
            holder.pay_date.setText(model.getPay_date());
            holder.cashable.setText("Deposit");
            holder.cashable.setTextColor(Color.WHITE);


            holder.id.setTextColor(Color.BLACK);
            holder.name.setTextColor(Color.BLACK);
            holder.verification.setTextColor(Color.BLACK);
            holder.amount.setTextColor(Color.BLACK);
            holder.pay_date.setTextColor(Color.BLACK);
            holder.cashable.setTextColor(Color.BLACK);
            holder.cashable.setTextColor(Color.BLACK);
            holder.cashable.setTextColor(Color.BLACK);

            if (model.isCashable()){

                holder.cashable.setTextColor(Color.WHITE);
                //holder.cashable.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,
                        //AppCompatResources.getDrawable(context,R.drawable.edit_backgroud1));


            }else{
                holder.cashable.setTextColor(Color.WHITE);
               // holder.cashable.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,AppCompatResources.getDrawable(context,R.drawable.edit_backgroud2),
                       // null);
            }
        }


    }

    @Override
    public int getItemCount() {
        return cashModels.size() +1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, verification, amount, pay_date;
        Button cashable;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            verification = itemView.findViewById(R.id.verification_status);
            amount = itemView.findViewById(R.id.amount);
            pay_date = itemView.findViewById(R.id.pay_date);
            cashable = itemView.findViewById(R.id.depo);

        }
    }
}
