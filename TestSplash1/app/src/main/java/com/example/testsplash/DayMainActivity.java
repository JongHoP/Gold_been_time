package com.example.testsplash;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DayMainActivity extends AppCompatActivity{
    int hour = 0;
    int minute = 0;
    public static Activity activity;

    private TextView textView_Date;
    private DatePickerDialog.OnDateSetListener callbackMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dayactivity_main);

        this.InitializeView();
        this.InitializeListener();
        activity = DayMainActivity.this;

        Button btn1 = (Button) findViewById(R.id.btn_selectDate);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(DayMainActivity.this,  R.style.DayPickerTheme, callbackMethod, 2021, 10, 30);
                dialog.show();
            }
        });

        Button btn2 = (Button) findViewById(R.id.btn_time);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog
                        (DayMainActivity.this, R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                TextView textView_time = (TextView)findViewById(R.id.textView_time);

                                String time =  String.valueOf(hourOfDay) + "시 "
                                        + String.valueOf(minute) + "분";
//                                Toast.makeText(DayMainActivity.this, time, Toast.LENGTH_LONG).show();
                                textView_time.setText(time);
                            }
                        },hour, minute, false);
                timePickerDialog.show();
            }// 참고: https://mainia.tistory.com/733
        });

        Button btn3 = (Button) findViewById(R.id.btn_confirm2);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pedo_intent = new Intent(getApplicationContext(),pedometerLockScreen.class);
                Intent myIntent = getIntent();

                String walk = myIntent.getExtras().getString("walk_value");
                pedo_intent.putExtra("walk_value", walk);

                startActivity(pedo_intent);

            }
        });

    }
    //    @Override
//    public void onBackPressed() {
//        //super.onBackPressed();
//    }
    public void InitializeView()
    {
        textView_Date = (TextView)findViewById(R.id.textView_date);
    }

    public void InitializeListener()
    {
        callbackMethod = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                textView_Date.setText(year + "년" + (monthOfYear + 1) + "월" + dayOfMonth + "일");
            }
        };
    }

}