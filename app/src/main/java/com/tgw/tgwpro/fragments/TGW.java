package com.tgw.tgwpro.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tgw.tgwpro.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TGW#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TGW extends Fragment {



    public TGW() {
        // Required empty public constructor
    }


    public static TGW newInstance(String param1, String param2) {
        TGW fragment = new TGW();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_t_g_w, container, false);


        return view;
    }
}