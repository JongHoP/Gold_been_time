package com.example.testsplash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainBeforeLock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Intent it = new Intent(this, TestCombo.class);
        it.putExtra("it_tag", tag);
        startActivity(it);
    }
}
