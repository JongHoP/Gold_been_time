package com.example.gold_being_time;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.dinuscxj.progressbar.CircleProgressBar;

public class timerLockScreen extends Activity  {

    private static final String DEFAULT_PATTERN = "%d"+ ":" +"%d" + ":" + "%d" ;

    CountDownTimer countDownTimer;
    TextView splitGoal; //입력받은 시간 텍스트뷰
    String goalNum;  //시간을 숫자로 쪼갠 것
    TextView ex;

    //String hour, min, second;

    CircleProgressBar circleProgressBar;

    String exnum="000010";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_lock_screen);

        splitGoal = (TextView) findViewById(R.id.tv2);
        circleProgressBar = findViewById(R.id.cpb_circlebar);

        //splitGoal();
        //countDown(exnum);

    }

    //입력받은 시간을 숫자와 문자로 나누기
    public String splitGoal(){

        String goal = splitGoal.getText().toString();
        if(goal.indexOf("시간")>0){
            goalNum = goal.substring(0, goal.indexOf("시간"));

        }else if(goal.indexOf("분")>0){
            goalNum = goal.substring(0, goal.indexOf("분"));
        }
        Toast.makeText(this, goalNum, Toast.LENGTH_SHORT).show();
        return goalNum;
    }

    public void countDown(String time){
        ex = (TextView) findViewById(R.id.tv3);
        long conversionTime = 0;

        // 1000 단위가 1초
        // 60000 단위가 1분
        // 60000 * 3600 = 1시간

        String getHour = time.substring(0, 2);
        String getMin = time.substring(2, 4);
        String getSecond = time.substring(4, 6);

        // "00"이 아니고, 첫번째 자리가 0 이면 제거
        if (getHour.substring(0, 1) == "0") {
            getHour = getHour.substring(1, 2);
        }

        if (getMin.substring(0, 1) == "0") {
            getMin = getMin.substring(1, 2);
        }

        if (getSecond.substring(0, 1) == "0") {
            getSecond = getSecond.substring(1, 2);
        }

        // 변환시간
        conversionTime = Long.valueOf(getHour) * 1000 * 3600 + Long.valueOf(getMin) * 60 * 1000 + Long.valueOf(getSecond) * 1000;

        // 첫번쨰 인자 : 원하는 시간 (예를들어 30초면 30 x 1000(주기))
        // 두번쨰 인자 : 주기( 1000 = 1초)
        countDownTimer = new CountDownTimer(conversionTime, 1000) {
            // 특정 시간마다 뷰 변경
            public void onTick(long millisUntilFinished) {

                // 시간단위
                String hour = String.valueOf(millisUntilFinished / (60 * 60 * 1000));

                // 분단위
                long getMin = millisUntilFinished - (millisUntilFinished / (60 * 60 * 1000)) ;
                String min = String.valueOf(getMin / (60 * 1000)); // 몫

                // 초단위
                String second = String.valueOf((getMin % (60 * 1000)) / 1000); // 나머지

                // 시간이 한자리면 0을 붙인다
                if (hour.length() == 1) {
                    hour = "0" + hour;
                }

                // 분이 한자리면 0을 붙인다
                if (min.length() == 1) {
                    min = "0" + min;
                }

                // 초가 한자리면 0을 붙인다
                if (second.length() == 1) {
                    second = "0" + second;
                }
                ex.setText(hour + ":" + min + ":" + second);

            }

            // 제한시간 종료시
            public void onFinish() {
                // 변경 후
                ex.setText("촬영종료!");
                // TODO : 타이머가 모두 종료될때 어떤 이벤트를 진행할지
            }
        }.start();
    }

    
    
   /* public void circleBar(){
*//*        splitGoal = (TextView)findViewById(R.id.tv2);  //목표 값 가져오기
        int num = Integer.parseInt(splitGoal.getText().toString());*//*
        CircleProgressBar.ProgressFormatter progressFormatter = new CircleProgressBar.ProgressFormatter() {
            @Override
            public CharSequence format(int progress, int max) {

                return String.format(DEFAULT_PATTERN, countDownTimer., min, second);   //위의 hour, min, second 값을 못 읽어옴
            }
        };

        circleProgressBar.setProgressFormatter(progressFormatter);
        circleProgressBar.setProgressTextColor(ContextCompat.getColor(this, R.color.button_color));
        circleProgressBar.setProgressBackgroundColor(ContextCompat.getColor(this, R.color.white_gray_color));
        circleProgressBar.setProgressStartColor(ContextCompat.getColor(this, R.color.button_color));
        circleProgressBar.setProgressEndColor(ContextCompat.getColor(this, R.color.button_color));
        int max = 100;
        int progress = 70;
        int pi = 360;
        int start = 270;

        int degree = start-(int)((Integer.parseInt("100")/(double)Double.parseDouble(exnum))*pi);
        circleProgressBar.setMax(Integer.parseInt(exnum));
        circleProgressBar.setProgress(1);
        circleProgressBar.setStartDegree(degree);
    }*/
}

