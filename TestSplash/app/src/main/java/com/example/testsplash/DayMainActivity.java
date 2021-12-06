package com.example.testsplash;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
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
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class DayMainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{
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
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
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

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        startAlarm(c);
    }


    private void startAlarm(Calendar c){
        Intent pedo_intent = new Intent(getApplicationContext(),pedometerLockScreen.class);
        Intent myIntent = getIntent();

        String walk = myIntent.getExtras().getString("walk_value");
        pedo_intent.putExtra("walk_value", walk);

        startActivity(pedo_intent);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if(c.before((Calendar.getInstance()))){
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 1*60*1000 ,  pendingIntent);

    }
}