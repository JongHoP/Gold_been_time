package com.example.testsplash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TestCombo extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //인텐트 값 받기
        Intent myIntent = getIntent();
        String walk = myIntent.getExtras().getString("걸음수");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        //인텐값 Toast
        Toast.makeText(TestCombo.this, "선택된 아이템 : "
                + walk, Toast.LENGTH_SHORT).show();


        setTitle("시간 설정화면");

        final String[] time = {"30분", "1시간", "2시간", "3시간", "직접입력"};

        Spinner spinner = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, time);
        spinner.setAdapter(adapter);
    }
}
