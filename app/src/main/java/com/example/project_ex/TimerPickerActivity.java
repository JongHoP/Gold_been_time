package com.example.project_ex;

import static android.widget.Toast.LENGTH_LONG;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.sql.Time;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Timer;

public class TimerPickerActivity extends AppCompatActivity implements TimePicker.OnTimeChangedListener {

    TimePicker mTimePicker;
    Calendar mCalender;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_timepicker);

        mCalender = Calendar.getInstance();

        int hour = mCalender.get(mCalender.HOUR_OF_DAY);
        int minute = mCalender.get(mCalender.MINUTE);
        int second = mCalender.get(mCalender.SECOND);

        mTimePicker = (TimePicker) findViewById(R.id.timePicker);
        mTimePicker.setOnTimeChangedListener((TimePicker.OnTimeChangedListener) this);


    }


    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

        Toast setTimetoast;
        setTimetoast = Toast.makeText(TimerPickerActivity.this, "현재 설정된 시간 \n: " + hourOfDay + "시 " + minute + "분", Toast.LENGTH_LONG);
    }
}
