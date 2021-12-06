package com.example.testsplash;

import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class ColorFragment extends Fragment {
    public static ColorFragment fragment1;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TextView textView;

    public ColorFragment() {
        // Required empty public constructor
    }


    public static ColorFragment newInstance(String param1, String param2) {
        ColorFragment fragment = new ColorFragment();
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
        fragment1 = ColorFragment.this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.viewfragment_color4, null);

        textView = (TextView) view.findViewById(R.id.textView);
        Spannable span = (Spannable) textView.getText();         //getText();
        span.setSpan(new RelativeSizeSpan(1.5f), 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //return inflater.inflate(R.layout.viewfragment_color4, container, false);
        return view;
    }
}