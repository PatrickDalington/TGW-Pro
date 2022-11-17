package com.tgw.tgwpro.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.tgw.tgwpro.R;
import com.tgw.tgwpro.main.MainActivity;
import com.tgw.tgwpro.models.User;

import java.security.Timestamp;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {

    EditText phoneNumber, code;
    SharedPreferences sectionA, sectionB;

    String fName,lName,state,phone,codeTxt,completePhoneNumber;
    TextView getCode,ErrorDisplay, timmer;
    Button login;
    boolean isComplete;
    ProgressBar progressBar;
    CountDownTimer cTimer = null;

    private String verificationId;

    FirebaseAuth firebaseAuth;
    DatabaseReference userRef, refIdRef;
    FirebaseUser user;
    private String userAvailability="";

    private static final int  MAX = 26;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();

        phoneNumber = findViewById(R.id.phone);
        code = findViewById(R.id.code);
        getCode = findViewById(R.id.getCode);
        ErrorDisplay = findViewById(R.id.bb);
        progressBar = findViewById(R.id.progress);
        timmer = findViewById(R.id.timmer);
        login = findViewById(R.id.login);




        sectionA = getSharedPreferences("SectionA",MODE_PRIVATE);
        sectionB = getSharedPreferences("SectionB",MODE_PRIVATE);


        //SECTION_A
        if (sectionA !=null){
            fName = sectionA.getString("fName","");
            lName = sectionA.getString("lName","");
        }else{
            isComplete = false;
        }

        //SECTION_B
        if (sectionB !=null){
            state = sectionB.getString("state","");
        }else{
            isComplete = false;
        }

        timmer.setOnClickListener(view -> {
            if (timmer.getText().equals("Retry")){
                startTimer();
                sendVerificationCode(completePhoneNumber);
            }
        });

        getCode.setOnClickListener(view -> {
            char checkZero = 0;
            phone = phoneNumber.getText().toString();
            if (!phone.equals("")) {
                checkZero = phone.charAt(0);
            }
            if (TextUtils.isEmpty(phoneNumber.getText())) {
                phoneNumber.setError("Phone number required");
                phoneNumber.requestFocus();
            } else if (checkZero == '0') {
                phoneNumber.setError("Phone number should not start with zero");
                phoneNumber.requestFocus();
            } else {

                checkUserAvailability("+234" + phoneNumber.getText().toString());

            }

        });
        login.setOnClickListener(view -> {
            if (TextUtils.isEmpty(phoneNumber.getText())) {
                phoneNumber.setError("Phone number required");
                phoneNumber.requestFocus();
            } else if (TextUtils.isEmpty(code.getText())) {
                code.setError("Code is required");
                code.requestFocus();
            } else if (code.length() < 6) {
                code.setError("Code not complete");
                code.requestFocus();
            } else {

                progressBar.setVisibility(View.VISIBLE);
                timmer.setVisibility(View.GONE);
                codeTxt = code.getText().toString();

                verifyCode(codeTxt);
            }


        });




    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void checkUserAvailability(String phoneNumber){

            userRef = FirebaseDatabase.getInstance().getReference("Users");
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            // User user = dataSnapshot.getValue(User.class);
                                Log.d("Login", dataSnapshot.child("Phone").getValue().toString().trim());
                               if (dataSnapshot.child("Phone").getValue().toString().trim().equals(phoneNumber)) {
                                    Log.d("Login", "yes");
                                    userAvailability="yes";
                                    break;
                                }



                        }

                        if(user!=null || userAvailability.equalsIgnoreCase("yes")) {

                            completePhoneNumber = phoneNumber;
                            startTimer();
                            getCode.setEnabled(false);
                            Runnable runnable = () -> getCode.setEnabled(true);
                            Handler handler = new Handler();
                            handler.postDelayed(runnable, 60000);
                            timmer.setVisibility(View.VISIBLE);
                            sendVerificationCode(completePhoneNumber);
                        }else {
                            AlertDialog.Builder alertDialog=new AlertDialog.Builder(Login.this);
                            alertDialog.setMessage("sorry you are not registered yet click register button below to continue");
                            alertDialog.setPositiveButton("Register", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(Login.this,WelcomePage.class));
                                }
                            });
                            alertDialog.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            Dialog dialog=alertDialog.create();
                            dialog.show();



                        }
                    } else {
                        Log.d("Login", "yes");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }

    static String getRandAlpha(int n)
    {

        char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u',
                'v', 'w', 'x', 'y', 'z' };

        String res = "";
        for (int i = 0; i < n; i++)
            res = res + alphabet[(int) (Math.random() * 100 % MAX)];

        return res;
    }

    private void sendVerificationCode(String number) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBack)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            stopTimer();
            timmer.setText("Code sent");
            progressBar.setVisibility(View.GONE);
            Toast.makeText(Login.this, "Code sent. Please check your inbox!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String codeT = phoneAuthCredential.getSmsCode();
            if (codeT != null) {
                code.setText(codeT);
                progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    };


    void startTimer() {
        cTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                timmer.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                timmer.setTextSize(15);
                timmer.setTextColor(Color.BLACK);
                timmer.setText(millisUntilFinished / 1000 +"s" );
            }
            public void onFinish() {
                progressBar.setVisibility(View.GONE);
                timmer.setText("Retry");
                timmer.setTextSize(18);
                timmer.setTextColor(Color.RED);
                getCode.setText("Get Code");

            }
        };
        cTimer.start();
    }

    void stopTimer(){
        if (cTimer !=null) {
            cTimer.cancel();
        }
    }

}