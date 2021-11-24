package com.example.testsplash;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String walk2 = "5000";
        Button btn1;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedometer);
        setTitle("걸음수 설정 화면");

        final String[] walk = {"5,000", "10,000", "20,000", "30,000", "직접입력"};


        Spinner spinner = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, walk);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                walk3 = (String) spinner.getItemAtPosition(position);
//                Toast.makeText(Pedometer.this, "선택된 아이템 : "
//                        + spinner.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btn1 = (Button) findViewById(R.id.btn_confirm1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent1 = new Intent(getApplicationContext(),DayMainActivity.class);
                Intent myIntent2 = new Intent(getApplicationContext(),TestCombo.class);
                myIntent2.putExtra("걸음수", walk3);

                startActivity(myIntent1);
            }
        });

    }

}
