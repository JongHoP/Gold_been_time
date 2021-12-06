package com.example.Gold_Being_Time;

import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Color2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Color2Fragment extends Fragment {
    public static Color2Fragment fragment2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView textView;

    public Color2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Color2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Color2Fragment newInstance(String param1, String param2) {
        Color2Fragment fragment = new Color2Fragment();
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
        fragment2 = Color2Fragment.this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.viewfragment_color5, null);

        textView = (TextView) view.findViewById(R.id.textView1);
        Spannable span1 = (Spannable) textView.getText();         //getText();
        span1.setSpan(new RelativeSizeSpan(1.5f), 15, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //return inflater.inflate(R.layout.viewfragment_color5, container, false);
        return view;
    }
}