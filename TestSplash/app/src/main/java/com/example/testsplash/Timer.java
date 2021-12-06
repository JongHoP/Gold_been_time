package com.example.testsplash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Timer extends AppCompatActivity {
    String selected_time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Button btn1;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        setTitle("시간 설정화면");

        final String[] time_arr = {"1분", "30분", "1시간", "2시간", "3시간"};

        Spinner spinner = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, time_arr);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_time = time_arr[position];
                //Toast.makeText(Pedometer.this, selected_walk, Toast.LENGTH_LONG).show(); // 선택된 값을 selected_walk에 전달하는가 보는 테스튼
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        btn1 = (Button) findViewById(R.id.timer_btn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = selected_time;

                Intent myIntent1 = new Intent(getApplicationContext(),DayMainActivity2.class);
                myIntent1.putExtra("time_value", temp);

                startActivity(myIntent1);
            }
        });
    }
}
