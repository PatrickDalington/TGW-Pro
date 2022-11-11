package com.tgw.tgwpro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tgw.tgwpro.R;

public class StatesAdapter extends RecyclerView.Adapter<StatesAdapter.ViewHolder> {

    Context context;
    String[] states;
    RecyclerView.ViewHolder viewHolder;
    private ItemClickListener onItemClickListener;


    public StatesAdapter(Context context, String[] states, ItemClickListener onItemClickListener){
        this.context = context;
        this.states = states;
        this.onItemClickListener = onItemClickListener;
    }



    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.states_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.state.setText(states[position]);
        holder.state.setOnClickListener(view -> {
            onItemClickListener.onItemClick(view, holder.getAdapterPosition());
        });
    }


    @Override
    public int getItemCount() {
        return states.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView state;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            state = itemView.findViewById(R.id.stateName);
        }
    }
}
