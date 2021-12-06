package com.example.testsplash;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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
                DatePickerDialog dialog = new DatePickerDialog(DayMainActivity.this, callbackMethod, 2021, 10, 30);
                dialog.show();
            }
        });

        Button btn2 = (Button) findViewById(R.id.btn_time);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog
                        (DayMainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                TextView textView_time = (TextView)findViewById(R.id.textView_time);




                                Intent myIntent1 = new Intent(DayMainActivity.this, pedometerLockScreen.class);

                                myIntent1.putExtra("hour_value", hourOfDay);
                                myIntent1.putExtra("minute_value", minute);

                                System.out.println("미리보기 : " + hourOfDay + " : " + minute);

                                String time =  String.valueOf(hourOfDay) + "시 "
                                        + String.valueOf(minute) + "분";
//                                Toast.makeText(DayMainActivity.this, time, Toast.LENGTH_LONG).show();
                                textView_time.setText(time);

                                Intent myIntent = getIntent();

                                String walk = myIntent.getExtras().getString("walk_value");
                                myIntent1.putExtra("walk_value", walk);

                                startActivity(myIntent1);
                            }

//                            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute2) {
//                                hour = hourOfDay;
//                                minute = minute2;
//                            }
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