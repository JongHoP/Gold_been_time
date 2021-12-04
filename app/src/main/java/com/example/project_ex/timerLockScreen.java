package com.example.gold_being_time;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.dinuscxj.progressbar.CircleProgressBar;

public class timerLockScreen extends Activity  {

    private static final String DEFAULT_PATTERN = "%s"+ ":" +"%s" + ":" + "%s" ;

    BRunnable runnable = new BRunnable();
    CountDownTimer countDownTimer;
    TextView splitGoal; //입력받은 시간 텍스트뷰
    String goalNum;  //시간을 숫자로 쪼갠 것
    String goall;
    TextView ex;
    long conversionTime = 0;

    String hour, min, second;

    CircleProgressBar circleProgressBar;

    String exnum="000009";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_lock_screen);

        splitGoal = (TextView) findViewById(R.id.tv2);
        circleProgressBar = findViewById(R.id.cpb_circlebar);


        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();

        countDown(splitGoal());
        circleBar();
    }

    //입력받은 시간을 숫자와 문자로 나누기
    public String splitGoal(){

        String goal = splitGoal.getText().toString();
        if(goal.indexOf("시간")>0){
            goalNum = goal.substring(0, goal.indexOf("시간"));
            if(Integer.parseInt(goalNum)< 10){
                goall = "0" + goalNum + "0000";
            } else
            goall = goalNum + "0000";
        }else if(goal.indexOf("분")>0){
            goalNum = goal.substring(0, goal.indexOf("분"));
            if(Integer.parseInt(goalNum) < 10 && Integer.parseInt(goalNum) >0){
                goall = "000" + goalNum + "00";
                System.out.println("goall " + goall);
            } else
            goall = "00" + goalNum + "00";
        }
        Toast.makeText(this, goalNum, Toast.LENGTH_SHORT).show();
        return goall;
    }

    public void countDown(String time){
        ex = (TextView) findViewById(R.id.tv3);
        //long conversionTime = 0;

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
        System.out.println("conver"+conversionTime);
        // 첫번쨰 인자 : 원하는 시간 (예를들어 30초면 30 x 1000(주기))
        // 두번쨰 인자 : 주기( 1000 = 1초)
        countDownTimer = new CountDownTimer(conversionTime, 1000) {
            // 특정 시간마다 뷰 변경
            public void onTick(long millisUntilFinished) {

                // 시간단위
                hour = String.valueOf(millisUntilFinished / (60 * 60 * 1000));

                // 분단위
                long getMin = millisUntilFinished - (millisUntilFinished / (60 * 60 * 1000)) ;
                min = String.valueOf(getMin / (60 * 1000)); // 몫

                // 초단위
                second = String.valueOf((getMin % (60 * 1000)) / 1000); // 나머지

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
                ex.setText("종료!");
                // TODO : 타이머가 모두 종료될때 어떤 이벤트를 진행할지
            }
        }.start();
    }

    
    
    public void circleBar(){
       // splitGoal = (TextView)findViewById(R.id.tv2);  //목표 값 가져오기
       // int num = Integer.parseInt(splitGoal.getText().toString());
        CircleProgressBar.ProgressFormatter progressFormatter = new CircleProgressBar.ProgressFormatter() {
            @Override
            public CharSequence format(int progress, int max) {
                System.out.println("abcde"+ hour + min +second);
                return String.format(DEFAULT_PATTERN, hour, min, second);   //위의 hour, min, second 값을 못 읽어옴
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


        //이 부분 수정하면 원형 프로세스가 바뀜
        System.out.println("goall" +goall);
        int degree = (int)((30/30)*pi)-90;
        circleProgressBar.setMax((int)conversionTime);
        circleProgressBar.setProgress(100);
        circleProgressBar.setStartDegree(degree);
    }
    public void run_contacts(View view){

        ImageButton button_contacts;
        button_contacts = (ImageButton)findViewById(R.id.imageButton2);

        Intent contact_intent = new Intent(Intent.ACTION_DIAL);
        startActivity(contact_intent);
    }

    public void run_message(View view){

        ImageButton button_message;
        button_message = (ImageButton) findViewById(R.id.imageButton3);

        //Intent message_intent = new Intent(Intent.ACTION_VIEW);
        Intent message_intent = this.getPackageManager().getLaunchIntentForPackage("com.samsung.android.messaging");
        startActivity(message_intent);
    }

    class BRunnable implements Runnable{
        private boolean stopped=false;
        @Override
        public void run() {
            while(!stopped){   //!Thread.currentThread().isInterrupted()
                circleBar();
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
        public void stop(){
            stopped=true;
        }
    }
}

