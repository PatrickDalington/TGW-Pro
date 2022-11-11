package com.tgw.tgwpro.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tgw.tgwpro.R;
import com.tgw.tgwpro.main.TaskActivity;
import com.tgw.tgwpro.models.Promo_Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    List<Promo_Model> promos;
    Promo_Model promo_model;
    Context context;
    CountDownTimer cTimer = null;
    Handler handler = new Handler();
    Runnable runnable;
    FirebaseUser firebaseUser;
    FirebaseAuth fAuth;

    private final String EVENT_DATE_TIME = "2022-09-31 10:30:00";
    private final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final int INVITE_TASK = 1;
    public static final int SHARE_TASK = 2;

    View view;

    public TaskAdapter(Context context, List<Promo_Model> promos){
        this.context = context;
        this.promos = promos;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == INVITE_TASK){
            view = LayoutInflater.from(context).inflate(R.layout.task_invite_layout, parent, false);
        }
        else {
            view = LayoutInflater.from(context).inflate(R.layout.task_share_layout, parent, false);
        }
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {

        fAuth = FirebaseAuth.getInstance();
        firebaseUser = fAuth.getCurrentUser();

        handler = new Handler();

        promo_model = promos.get(position);
        holder.title.setText(promo_model.getTitle());







        if (promo_model.getAvailability().equals("Yes")){
            holder.availability.setText("Available");
            holder.availability.setTextColor(Color.parseColor("#349338"));
        }else if (promo_model.getAvailability().equals("Almost")){
            holder.availability.setText("Almost full");
            holder.availability.setTextColor(Color.parseColor("#FF9800"));
        }else{
            holder.availability.setText("(Maximum promoters reached)");
            holder.availability.setTextColor(Color.RED);
        }

        if (!promo_model.getLogoUrl().equals("default")){
            Glide.with(context).load(promo_model.getLogoUrl()).into(holder.promo_logo);
        }

        if (promo_model.getTime_visibility().equals("no")){
            holder.days.setVisibility(View.GONE);
            holder.hours.setVisibility(View.GONE);
            holder.minutes.setVisibility(View.GONE);
            holder.seconds.setVisibility(View.GONE);
        }

        if (promo_model.getRewardType().equals("gift")){
            holder.reward.setText(promo_model.getReward());
            holder.reward.setTextSize(15);
        }else{
            holder.reward.setText(promo_model.getReward());
        }

        countDownStart(promos.get(holder.getAdapterPosition()).getExpiring_date()
                , holder.days, holder.hours, holder.minutes, holder.seconds);

        holder.itemView.setOnClickListener(v->{
            promo_model = promos.get(position);
            if (promo_model.getRewardType().equals("gift")){

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                HashMap<String,Object> giftTopper =new HashMap<>();
                giftTopper.put("gift_id", promo_model.getKey());

                Intent intent = new Intent(context, TaskActivity.class);
                context.startActivity(intent);
                int i = 0;
                i++;
                Toast.makeText(context, i +" gift item added", Toast.LENGTH_SHORT).show();

            }else if (promo_model.getRewardType().equals("cash")){
                Intent intent = new Intent(context, TaskActivity.class);
                context.startActivity(intent);
                int i = 0;
                i++;
                int i1 = i + Integer.parseInt(promo_model.getReward());
                Toast.makeText(context, i1 +" naira added to your wallet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return promos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView promo_logo;
        TextView reward, title,seconds, minutes, hours, days,availability;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            promo_logo = itemView.findViewById(R.id.promo_logo);
            reward = itemView.findViewById(R.id.reward);
            title = itemView.findViewById(R.id.promo_title);
            days = itemView.findViewById(R.id.days);
            hours = itemView.findViewById(R.id.hours);
            minutes = itemView.findViewById(R.id.mins);
            seconds = itemView.findViewById(R.id.seconds);
            availability = itemView.findViewById(R.id.isAvailable);

        }
    }

    private void countDownStart(String EVENT_DATE_TIME,TextView days, TextView hours, TextView minutes, TextView sec) {
        handler = new Handler();
        runnable = new Runnable() {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void run() {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
                    Date event_date = dateFormat.parse(EVENT_DATE_TIME);
                    Date current_date = new Date();
                    if (!current_date.after(event_date)) {
                        long diff = event_date.getTime() - current_date.getTime();
                        long Days = diff / (24 * 60 * 60 * 1000);
                        long Hours = diff / (60 * 60 * 1000) % 24;
                        long Minutes = diff / (60 * 1000) % 60;
                        long Seconds = diff / 1000 % 60;
                        //

                        if (Days == 0){
                            days.setText("Today");
                        }else if (Days == 1) {
                            days.setText(String.format("%2d", Days) + "day");
                        }else{
                            days.setText(String.format("%2d", Days) + "days");
                        }
                        hours.setText(String.format("%02d", Hours )+":");
                        minutes.setText(String.format("%02d", Minutes )+":");
                        sec.setText(String.format("%02d", Seconds));


                    } else {
                        days.setVisibility(View.GONE);
                        hours.setVisibility(View.GONE);
                        minutes.setVisibility(View.GONE);
                        sec.setVisibility(View.GONE);
                        handler.removeCallbacks(runnable);
                    }

                    handler.postDelayed(this, 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);

    }

    public void onStop() {
        handler.removeCallbacks(runnable);
    }

    void startTimer(long secMilli, TextView textView) {
        cTimer = new CountDownTimer((long) Math.rint(secMilli), 1000) {
            public void onTick(long millisUntilFinished) {

                textView.setText(millisUntilFinished / 1000 +"s" );
            }
            public void onFinish() {

                textView.setText("00:00");
                textView.setTextColor(Color.RED);
            }
        };
        cTimer.start();
    }

    @Override
    public int getItemViewType(int position) {

        if (promos.get(position).getPromo_task().equals("Invite")){
            return INVITE_TASK;
        }
        return super.getItemViewType(position);
    }
}
