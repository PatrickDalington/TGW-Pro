package com.tgw.tgwpro.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tgw.tgwpro.R;
import com.tgw.tgwpro.main.TaskActivity;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TGW#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Task extends Fragment {

    DatabaseReference ref;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String key;



    public Task() {
        // Required empty public constructor
    }


    public static Task newInstance(String param1, String param2) {
        Task fragment = new Task();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() !=null) {

        }
    }

    String title, imageUrl, taskId, taskType, rewardType, reward;
    ImageView promoPhoto;
    TextView invite, share;
    TextView promoTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_task, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        promoPhoto = view.findViewById(R.id.promo_logo1);
        invite = view.findViewById(R.id.invite);
        share = view.findViewById(R.id.share);
        promoTitle = view.findViewById(R.id.promo_title);


        TaskActivity taskActivity = (TaskActivity)getActivity();
        if (taskActivity !=null) {
            Bundle bundle = taskActivity.getMyData();
            title = bundle.getString("title");
            imageUrl = bundle.getString("imageUrl");
            taskId = bundle.getString("taskId");
            taskType = bundle.getString("taskType");
            reward = bundle.getString("estAmount");
            rewardType = bundle.getString("rewardType");
        }


            Glide.with(requireActivity()).load(imageUrl).into(promoPhoto);
            promoTitle.setText(title);



            invite.setOnClickListener(v->{

                ref = FirebaseDatabase.getInstance().getReference("Payments_Record")
                        .child(firebaseUser.getUid()).child(rewardType);

                key = ref.push().getKey();

                if (rewardType.equals("cash")) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", taskId);
                    hashMap.put("company_name", title);
                    hashMap.put("task_verification", "pending");
                    hashMap.put("amount", reward );
                    hashMap.put("pay_date", "05/12/2022");
                    hashMap.put("isCashable", false);
                    hashMap.put("key", key);

                    ref.child(key).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getActivity(),
                                        "Task is being processed. Check your dashboard for more info",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            });


        return view;
    }
}