package com.example.project_ex;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void timerPicker(View view){

        int hour = 0;
        int minute = 0;

        TimePickerDialog timePickerDialog = new TimePickerDialog
                (MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        String time = "Hour: " + String.valueOf(hourOfDay) + "\n"
                                    + "Minute: " + String.valueOf(minute);
                        Toast.makeText(MainActivity.this, time, Toast.LENGTH_LONG).show();
                    }
                },hour, minute, false);
        timePickerDialog.show();
    }// 참고: https://mainia.tistory.com/733

    public void gotoSetting(View view){

        Button buttonSetting;

        buttonSetting = (Button)findViewById(R.id.setting_btn);

        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        startActivityForResult(intent, 0);
        // 참고: https://lcw126.tistory.com/277
    }


    public void run_contacts(View view){

        Button button_contacts;
        button_contacts = (Button)findViewById(R.id.contacts_btn);

        Intent contact_intent = new Intent(Intent.ACTION_DIAL);
        startActivity(contact_intent);
    }

    public void run_message(View view){

        Button button_message;
        button_message = (Button) findViewById(R.id.message_btn);

        //Intent message_intent = new Intent(Intent.ACTION_VIEW);
        Intent message_intent = new Intent(Intent.ACTION_VIEW);
        startActivity(message_intent);
    }

//    public void XgotoSettingX(View view){
//
//        Button buttonSetting;
//
//        buttonSetting = (Button)findViewById(R.id.setting_btn);
//
//        buttonSetting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//                startActivityForResult(intent, 0);
//            }
//        });
//    }


}