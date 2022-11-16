package com.tgw.tgwpro.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.tgw.tgwpro.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class DashBoard_Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        initViews();
    }

    // This is to initialize view to reduce excess code on on-create method
    private void initViews() {
        RelativeLayout relativeLayout;
        CardView income_card, gift_card,network_card;
        income_card = findViewById(R.id.naira_card);
        gift_card = findViewById(R.id.mgift_card);
        network_card=findViewById(R.id.network_card);
        income_card.setOnClickListener(this);
        gift_card.setOnClickListener(this);
        network_card.setOnClickListener(this);

        relativeLayout = findViewById(R.id.carRel);
        // This is an animation to allow view slide in
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_nim);
        relativeLayout.startAnimation(animation);
    }

    // listens to any view clicked
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.naira_card) {
            startActivity(new Intent(DashBoard_Activity.this, Table.class));
        } else if (view.getId() == R.id.mgift_card) {
            startActivity(new Intent(DashBoard_Activity.this, Giftcard_Table_Activity.class));
        } else if (view.getId() == R.id.network_card) {
            startActivity(new Intent(DashBoard_Activity.this, Network_Activity.class));
        }
    }
}