package com.example.Gold_Being_Time;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {
    public static Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        activity = SubActivity.this;
        setContentView(R.layout.main_before_lock);

    }

    public void nextLayout2(View v) {
        int id = v.getId();
        LinearLayout layout = (LinearLayout) v.findViewById(id);
        String tag = (String) layout.getTag();

//        Toast.makeText(this, "클릭한 아이템: " + tag, Toast.LENGTH_SHORT).show();
        Intent it = new Intent(this, Pedometer.class);
        it.putExtra("it_tag", tag);
        startActivity(it);
    }


    public void nextLayout5(View v) {
        int id = v.getId();
        LinearLayout layout = (LinearLayout) v.findViewById(id);
        String tag = (String) layout.getTag();

//        Toast.makeText(this, "클릭한 아이템: " + tag, Toast.LENGTH_SHORT).show();
        Intent it = new Intent(this, Timer.class);
        it.putExtra("it_tag", tag);
        startActivity(it);
    }
}
