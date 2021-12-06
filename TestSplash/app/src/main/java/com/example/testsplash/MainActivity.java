package com.example.testsplash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {

    Color3Fragment color3Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewactivity_main);

        ViewPager viewPager = findViewById((R.id.pager));
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

    }


    public void nextLayout(View v) {
        int id = v.getId();
        LinearLayout layout = (LinearLayout) v.findViewById(id);
        String tag = (String) layout.getTag();

//        Toast.makeText(this, "클릭한 아이템: " + tag, Toast.LENGTH_SHORT).show();
        Intent it = new Intent(this, MainBeforeLock.class);
        it.putExtra("it_tag", tag);
        startActivity(it);
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

    public void nextLayout3(View v) {
        int id = v.getId();
        LinearLayout layout = (LinearLayout) v.findViewById(id);
        String tag = (String) layout.getTag();

//        Toast.makeText(this, "클릭한 아이템: " + tag, Toast.LENGTH_SHORT).show();
        Intent it = new Intent(this, TestListView.class);
        it.putExtra("it_tag", tag);
        startActivity(it);
    }

    public void nextLayout4(View v) {
        int id = v.getId();
        LinearLayout layout = (LinearLayout) v.findViewById(id);
        String tag = (String) layout.getTag();

//        Toast.makeText(this, "클릭한 아이템: " + tag, Toast.LENGTH_SHORT).show();
        Intent it = new Intent(this, DynamicListView.class);
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

