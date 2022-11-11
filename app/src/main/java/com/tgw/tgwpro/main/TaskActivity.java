package com.tgw.tgwpro.main;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tgw.tgwpro.R;
import com.tgw.tgwpro.adapters.FragmentAdapter2;
import com.tgw.tgwpro.fragments.Description;
import com.tgw.tgwpro.fragments.Home;
import com.tgw.tgwpro.fragments.TGW;
import com.tgw.tgwpro.fragments.Task;

public class TaskActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    FragmentAdapter2 fragmentAdapter2;
    TabLayout tabLayout;
    String description, title, imageUrl, taskType, taskId, about, howTo, estAmount;
    Intent intent;
    Bundle bundle;

    TextView titleCom, aboutCompany;
    ImageView companyLogo, backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        intent = getIntent();



        viewPager = findViewById (R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        companyLogo = findViewById(R.id.coverImage);
        titleCom = findViewById(R.id.title);
        aboutCompany = findViewById(R.id.ab);
        backButton = findViewById(R.id.back);


        if (intent !=null){
            description = intent.getStringExtra("description");
            title = intent.getStringExtra("title");
            imageUrl = intent.getStringExtra("imageUrl");
            taskType = intent.getStringExtra("taskType");
            taskId = intent.getStringExtra("key");
            about = intent.getStringExtra("about");
            howTo = intent.getStringExtra("howTo");
            estAmount = intent.getStringExtra("estAmount");

            Glide.with(this).load(imageUrl).into(companyLogo);
            titleCom.setText(title);
            aboutCompany.setText(about);


        }


        tabLayout.setTabTextColors(Color.WHITE, Color.BLACK);
        tabLayout.setSelectedTabIndicator(R.drawable.tab_indicator);


        fragmentAdapter2 = new FragmentAdapter2(getSupportFragmentManager(), getLifecycle());
        fragmentAdapter2.addFragment(new Description());
        fragmentAdapter2.addFragment(new Task());


        tabLayout.setBackgroundColor(Color.BLACK);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setTabTextColors(Color.WHITE, Color.BLACK);



        viewPager.setAdapter(fragmentAdapter2);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position ==0){
                tab.setText("Description");
            }else if (position ==1){
                tab.setText("Task");
            }
        }).attach();


        viewPager.requestDisallowInterceptTouchEvent(true);



        backButton.setOnClickListener(v->{
            onBackPressed();
        });

    }

    public Bundle getMyData() {
        bundle = new Bundle();
        bundle.putString("description", description);
        bundle.putString("title", title);
        bundle.putString("imageUrl", imageUrl);
        bundle.putString("taskType", taskType);
        bundle.putString("taskId", taskId);
        bundle.putString("howTo", howTo);
        bundle.putString("estAmount", estAmount);

        return bundle;
    }
}