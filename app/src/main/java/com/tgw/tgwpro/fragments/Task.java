package com.tgw.tgwpro.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tgw.tgwpro.R;
import com.tgw.tgwpro.main.TaskActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TGW#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Task extends Fragment {



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

    String title, imageUrl, taskId, taskType;
    ImageView promoPhoto;
    TextView invite, share;
    TextView promoTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_task, container, false);

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
        }


            Glide.with(requireActivity()).load(imageUrl).into(promoPhoto);
            promoTitle.setText(title);




        return view;
    }
}