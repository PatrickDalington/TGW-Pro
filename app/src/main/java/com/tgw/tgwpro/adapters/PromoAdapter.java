package com.tgw.tgwpro.adapters;

import static android.provider.Settings.System.DATE_FORMAT;

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
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.ViewHolder> {

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

    public PromoAdapter(Context context, List<Promo_Model> promos){
        this.context = context;
        this.promos = promos;
    }

    @NonNull
    @Override
    public PromoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.promo_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoAdapter.ViewHolder holder, int position) {

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
                //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                //HashMap<String,Object> giftTopper =new HashMap<>();
                //giftTopper.put("gift_id", promo_model.getKey());

                String taskType;
                Intent intent = new Intent(context, TaskActivity.class);
                if (promo_model.getPromo_task().equals("Invite")){
                    taskType = "Invite";
                    intent.putExtra("taskType", taskType);
                    intent.putExtra("key", promo_model.getKey());
                    intent.putExtra("about", promo_model.getAbout());
                    intent.putExtra("imageUrl", promo_model.getLogoUrl());
                    intent.putExtra("title", promo_model.getTitle());
                    intent.putExtra("description", promo_model.getDescription());
                    intent.putExtra("howTo", promo_model.getHow_to_earn());
                    intent.putExtra("estAmount", promo_model.getEst_amount());

                }else if (promo_model.getPromo_task().equals("Share")){
                    taskType = "Invite";
                    intent.putExtra("taskType", taskType);
                    intent.putExtra("key", promo_model.getKey());
                    intent.putExtra("title", promo_model.getTitle());
                    intent.putExtra("about", promo_model.getAbout());
                    intent.putExtra("description", promo_model.getDescription());
                    intent.putExtra("howTo", promo_model.getHow_to_earn());
                    intent.putExtra("estAmount", promo_model.getEst_amount());
                }else{
                    taskType = "Invite";
                    intent.putExtra("taskType", taskType);
                    intent.putExtra("key", promo_model.getKey());
                    intent.putExtra("imageUrl", promo_model.getLogoUrl());
                    intent.putExtra("title", promo_model.getTitle());
                    intent.putExtra("about", promo_model.getAbout());
                    intent.putExtra("description", promo_model.getDescription());
                    intent.putExtra("howTo", promo_model.getHow_to_earn());
                    intent.putExtra("estAmount", promo_model.getEst_amount());
                }
                context.startActivity(intent);
                int i = 0;
                i++;
                Toast.makeText(context, i +" gift item added", Toast.LENGTH_SHORT).show();

            }else if (promo_model.getRewardType().equals("cash")){

                String taskType;
                Intent intent = new Intent(context, TaskActivity.class);
                if (promo_model.getPromo_task().equals("Invite")){
                    taskType = "Invite";
                    intent.putExtra("taskType", taskType);
                    intent.putExtra("key", promo_model.getKey());
                    intent.putExtra("imageUrl", promo_model.getLogoUrl());
                    intent.putExtra("title", promo_model.getTitle());
                    intent.putExtra("about", promo_model.getAbout());
                    intent.putExtra("description", promo_model.getDescription());
                    intent.putExtra("howTo", promo_model.getHow_to_earn());
                    intent.putExtra("estAmount", promo_model.getEst_amount());

                }else if (promo_model.getPromo_task().equals("Share")){
                    taskType = "Invite";
                    intent.putExtra("taskType", taskType);
                    intent.putExtra("about", promo_model.getAbout());
                    intent.putExtra("key", promo_model.getKey());
                    intent.putExtra("title", promo_model.getTitle());
                    intent.putExtra("description", promo_model.getDescription());
                    intent.putExtra("howTo", promo_model.getHow_to_earn());
                    intent.putExtra("estAmount", promo_model.getEst_amount());
                }else{
                    taskType = "Invite";
                    intent.putExtra("taskType", taskType);
                    intent.putExtra("key", promo_model.getKey());
                    intent.putExtra("imageUrl", promo_model.getLogoUrl());
                    intent.putExtra("title", promo_model.getTitle());
                    intent.putExtra("about", promo_model.getAbout());
                    intent.putExtra("description", promo_model.getDescription());
                    intent.putExtra("howTo", promo_model.getHow_to_earn());
                    intent.putExtra("estAmount", promo_model.getEst_amount());
                }
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
                    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                    Date event_date = dateFormat.parse(EVENT_DATE_TIME);
                    Date current_date = new Date();
                    char a = 'b';
                    

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
}
