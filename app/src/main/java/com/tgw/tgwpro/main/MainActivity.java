package com.tgw.tgwpro.main;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tgw.tgwpro.R;
import com.tgw.tgwpro.adapters.FragmentAdapter2;
import com.tgw.tgwpro.fragments.Home;
import com.tgw.tgwpro.fragments.TGW;
import com.tgw.tgwpro.intro.IntroSlider;
import com.tgw.tgwpro.models.User;
import com.tgw.tgwpro.register.Login;
import com.tgw.tgwpro.register.WelcomePage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Window window;
    Animation animation,animation2;
    String title,message,updateURL;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    ActivityResultLauncher<Intent>    pickImageFromFolderPermission;
    private Uri imageUri;

    FirebaseUser firebaseUser;
    FirebaseAuth auth,auth1;
    CircleImageView profileImage,imageProfile;
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
        profileImage=(CircleImageView) findViewById(R.id.profile_image_icon);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled ( true );
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference().child("UserProfiles");
        pickImageFromFolderPermission=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent data=result.getData();
                if(data!=null) {
                    Uri profileImageUri = data.getData();
                    imageProfile.setImageURI(profileImageUri);
                    uploadUserProfile(profileImageUri);
                    Toast.makeText(MainActivity.this, "collected" + profileImageUri.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
         profileImage.setOnClickListener(v->{
             showProfile();

         });

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
        }else if(item.getItemId()==R.id.settings){
            startActivity(new Intent(MainActivity.this,SettingsActivity.class));
        } else
         {
            return false;
        }
        return true;
    }


    public  void showProfile(){
        Dialog dialog=new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.profile_layout);
        dialog.setCanceledOnTouchOutside(true);
        CircleImageView profile=(CircleImageView) dialog.findViewById(R.id.profile_image_I);
        imageProfile=profile;
        Objects.requireNonNull(profile).setOnClickListener(v->{
            Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            pickImageFromFolderPermission.launch(Intent.createChooser(intent,"pick image"));
        });

        dialog.show();
    }
private void uploadUserProfile(Uri uri){
    try {
        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
       long time=System.currentTimeMillis();
       String  imageName ="IMG_"+time;
        ByteArrayOutputStream byteImage=new ByteArrayOutputStream();
       bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteImage);
        storageReference.child(imageName).putBytes(byteImage.toByteArray()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(MainActivity.this, "uploaded successfully", Toast.LENGTH_LONG).show();
                taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        Map<String,Object> imageUpload=new HashMap<>();
                        imageUpload.put("profileImage",task.getResult().toString());
                        FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid()).updateChildren(imageUpload).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(MainActivity.this, "profile uploaded", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Log.d("Main", task.getResult().toString());
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    } catch (Exception e) {
        e.printStackTrace();
    }

}

}