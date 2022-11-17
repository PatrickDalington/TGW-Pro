package com.tgw.tgwpro.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tgw.tgwpro.R;
import com.tgw.tgwpro.adapters.FragmentAdapter2;
import com.tgw.tgwpro.fragments.Home;
import com.tgw.tgwpro.fragments.TGW;
import com.tgw.tgwpro.intro.IntroSlider;
import com.tgw.tgwpro.models.User;
import com.tgw.tgwpro.register.Login;
import com.tgw.tgwpro.register.WelcomePage;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Window window;
    Animation animation,animation2;
    String title,message,updateURL;
    FirebaseUser firebaseUser;
    FirebaseAuth auth,auth1;

    FragmentActivity listener;
    ViewPager2 viewPager;
    FragmentAdapter2 fragmentAdapter2;
    TabLayout tabLayout;
    TextView greeting, gift, wallet, lName,testimony;
    String [] test = {"  Philip: I am greatful to download this app   ",
            "   Diana: This app is life saving   ",
            "   Paul: I just made 1000 naira in 2hrs wow!!!   ",
            "   Chiamaka: Cool side hustle   ",
            "   Prisca: OMG!!! TGW you are the best!   ",
            "   Patrick: I love you Tonia!   ",
            "   Ruth: Thank you TGW!!!   "};


    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#FF9800"));
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled ( true );


        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        if (auth.getCurrentUser() == null){
            // Intent i =new Intent(getApplicationContext(), IntroSlider.class);
            //startActivity(i);
        }


        greeting = findViewById(R.id.greetings);
        gift = findViewById(R.id.gift_card);
        wallet = findViewById(R.id.wallet);
        lName = findViewById(R.id.lName);
        testimony = findViewById(R.id.testimony);

        greetMe(greeting);


        drawerLayout = findViewById ( R.id.drawer );
        actionBarDrawerToggle = new ActionBarDrawerToggle ( this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawerLayout.addDrawerListener ( actionBarDrawerToggle );
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        actionBarDrawerToggle.syncState ();


        navigationView = findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(this);



        viewPager = findViewById (R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        fragmentAdapter2 = new FragmentAdapter2(getSupportFragmentManager(), getLifecycle());
        fragmentAdapter2.addFragment(new Home());
        fragmentAdapter2.addFragment(new TGW());

        viewPager.setAdapter(fragmentAdapter2);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position ==0){
                tab.setText("Home");
            }else if (position ==1){
                tab.setText("TGW");
            }
        }).attach();


        viewPager.requestDisallowInterceptTouchEvent(true);


        if (firebaseUser !=null) {

            userRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        User user = snapshot.getValue(User.class);
                        assert user != null;
                        lName.setText(user.getLast_Name());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        testimony.setSelected(true);
        displayTestimony(testimony);

    }

    private void displayTestimony(TextView textView){
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : test) {
            stringBuilder = stringBuilder.append(s);
        }
        textView.setText(stringBuilder.toString());

    }

    private void greetMe(TextView textView){
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        if(hour>= 12 && hour < 17){
            textView.setText("Good Afternoon");
        } else if(hour >= 17 && hour < 21){
            textView.setText("Good Evening");
        } else if(hour >= 21 && hour < 24){
            textView.setText("Good Night");
        } else {
            textView.setText("Good Morning");
        }
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            Toast.makeText(MainActivity.this, "i am clicked", Toast.LENGTH_SHORT).show();


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth1 = FirebaseAuth.getInstance();
        if (auth1.getCurrentUser() == null){
            Intent i =new Intent(getApplicationContext(), IntroSlider.class);
            startActivity(i);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //this is to get id for the various menu items
        if (item.getItemId() == R.id.myDashboard) {
            startActivity(new Intent(MainActivity.this, DashBoard_Activity.class));
        }if (item.getItemId()== R.id.logout1) {
            if(firebaseUser!=null){
                auth.signOut();
                startActivity(new Intent(MainActivity.this,WelcomePage.class));
            }
        }else
         {
            return false;
        }
        return true;
    }
}