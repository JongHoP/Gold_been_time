package com.example.testsplash;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class TimeActivity extends AppCompatActivity {
    int hour = 0;
    int minute = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_timepicker);
    }

    public void timerPicker(View view){

        TimePickerDialog timePickerDialog = new TimePickerDialog
                (TimeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        String time = "Hour: " + String.valueOf(hourOfDay) + "\n"
                                    + "Minute: " + String.valueOf(minute);
                        Toast.makeText(TimeActivity.this, time, Toast.LENGTH_LONG).show();
                    }
                },hour, minute, false);
        timePickerDialog.show();
    }// 참고: https://mainia.tistory.com/733

}