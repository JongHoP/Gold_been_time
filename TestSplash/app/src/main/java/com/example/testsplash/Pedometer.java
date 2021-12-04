package com.example.testsplash;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Pedometer extends AppCompatActivity {
    String walk3;

    String selected_walk;
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String walk2 = "5000";
        Button btn1;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedometer);
        setTitle("걸음수 설정 화면");

        activity = Pedometer.this;

        final String[] walk_arr = {"5000", "10000", "20000", "30000"};


        Spinner spinner = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, walk_arr);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                walk3 = (String) spinner.getItemAtPosition(position);
//                Toast.makeText(Pedometer.this, "선택된 아이템 : "
//                        + spinner.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                selected_walk = walk_arr[position];
                //Toast.makeText(Pedometer.this, selected_walk, Toast.LENGTH_LONG).show(); // 선택된 값을 selected_walk에 전달하는가 보는 테스튼
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        btn1 = (Button) findViewById(R.id.btn_confirm1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = selected_walk;

                Intent myIntent1 = new Intent(getApplicationContext(),DayMainActivity.class);
                myIntent1.putExtra("walk_value", temp);

                startActivity(myIntent1);
            }
        });

    }

}


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        input = (EditText)findViewById(R.id.main_edit_input);
//
//        btn01 = (Button)findViewById(R.id.main_btn_move);
//        btn02 = (Button)findViewById(R.id.main_btn_close);
//
//        btn01.setOnClickListener(this);
//        btn02.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        if(v.getId() == R.id.main_btn_move) {
//            // 데이터 첨부를하고 액티비티 실행
//            String temp = input.getText().toString();
//            Intent intent01 = new Intent(this, Activity2.class);
//            intent01.putExtra("key01", temp);
//            startActivity(intent01);
//        }
//        if (v.getId() == R.id.main_btn_close) {
//            // 종료
//            finish();
//        }
//    }