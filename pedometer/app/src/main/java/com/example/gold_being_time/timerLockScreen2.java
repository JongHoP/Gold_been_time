package com.example.gold_being_time;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class timerLockScreen2 extends Activity {
    private static final String DEFAULT_PATTERN = "%d"+ ":" +"%d" + ":" + "%d" ;

    TextView splitGoal; //입력받은 시간 텍스트뷰
    String goalNum;  //시간을 숫자로 쪼갠 것

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_lock_screen);


    }
    private void startTimer(){

    }
}
