package com.example.Gold_Being_Time;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Color3Fragment extends Fragment {
    MainActivity activity;
    public static Color3Fragment fragment3;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView textView;
    public Color3Fragment() {
        // Required empty public constructor
    }

    public static Color3Fragment newInstance(String param1, String param2) {
        Color3Fragment fragment = new Color3Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        fragment3 = Color3Fragment.this;

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //현재 소속된 액티비티를 메인 액티비티로 한다.
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.viewfragment_color6, null);

        textView = (TextView) view.findViewById(R.id.textView);
        Spannable span1 = (Spannable) textView.getText();         //getText();
        span1.setSpan(new RelativeSizeSpan(1.5f), 15, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        View showWordGroupListButton = view.findViewById(R.id.button3);

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.viewfragment_color6, container, false);
        //View showWordGroupListButton = rootView.findViewById(R.id.button3);
        showWordGroupListButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), SubActivity.class);
                startActivity(intent);
            }
        });

        //return rootView;
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}