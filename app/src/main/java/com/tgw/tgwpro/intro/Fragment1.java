package com.tgw.tgwpro.intro;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.tgw.tgwpro.R;


public class Fragment1 extends Fragment {

    Button instruction;

    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_1, container, true);

        instruction = view.findViewById(R.id.ins);

        instruction.setOnClickListener(v->{
            Toast.makeText(getActivity(), "Loading instructions... please wait", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}