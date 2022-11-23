package com.tgw.tgwpro.main;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.WHITE;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tgw.tgwpro.R;
import com.tgw.tgwpro.adapters.FragmentAdapter2;
import com.tgw.tgwpro.fragments.Account;
import com.tgw.tgwpro.fragments.Transactions;

public class Table extends AppCompatActivity {



    ViewPager2 viewPager;
    FragmentAdapter2 fragmentAdapter2;
    TabLayout tabLayout;
    TextView txt,txt2;

    private static final int RED = 0xffFF8080;
    private static final int BLUE = 0xff8080FF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);



        viewPager = findViewById (R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        txt = findViewById(R.id.txt);
        txt2 = findViewById(R.id.txt2);



        ValueAnimator colorAnim = ObjectAnimator.ofInt(txt, "backgroundColor", WHITE, Color.parseColor("#037107"));
        colorAnim.setDuration(2000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();

        ValueAnimator colorAnim2 = ObjectAnimator.ofInt(txt2, "backgroundColor", WHITE, Color.parseColor("#CD7C03"));
        colorAnim2.setDuration(4000);
        colorAnim2.setEvaluator(new ArgbEvaluator());
        colorAnim2.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim2.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim2.start();

        fragmentAdapter2 = new FragmentAdapter2(getSupportFragmentManager(), getLifecycle());
        fragmentAdapter2.addFragment(new Account());
        fragmentAdapter2.addFragment(new Transactions());

        viewPager.setAdapter(fragmentAdapter2);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position ==0){
                tab.setText("Account");
            }else if (position ==1){
                tab.setText("Transactions");
            }
        }).attach();

        viewPager.requestDisallowInterceptTouchEvent(true);

    }

}