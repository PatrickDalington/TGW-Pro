package com.tgw.tgwpro.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tgw.tgwpro.R;
import com.tgw.tgwpro.main.TaskActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Description#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Description extends Fragment {

    public Description() {

    }

    public static Description newInstance(String param1, String param2) {
        Description fragment = new Description();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    TextView des, est_amount, how_to_earn;
    String description, estAmount, howToEarn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TaskActivity taskActivity = (TaskActivity)getActivity();
        if (taskActivity !=null) {
            Bundle bundle = taskActivity.getMyData();
            description = bundle.getString("description");
            howToEarn = bundle.getString("howTo");
            estAmount = bundle.getString("estAmount");
        }


        View view = inflater.inflate(R.layout.fragment_description, container, false);

        des = view.findViewById(R.id.des);
        how_to_earn = view.findViewById(R.id.how_to_earn);
        est_amount = view.findViewById(R.id.amount);

        des.setText(description);
        est_amount.setText(estAmount);
        how_to_earn.setText(howToEarn);
        return view;
    }
}