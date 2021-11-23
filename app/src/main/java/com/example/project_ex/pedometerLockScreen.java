package com.example.gold_being_time;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.widget.CalendarView;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.dinuscxj.progressbar.CircleProgressBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class pedometerLockScreen extends Activity implements SensorEventListener { //

    private static final String DEFAULT_PATTERN = "%d"+ "/" +"%d";

    BackRunnable runnable = new BackRunnable();
    private Thread timeThread = null;
    TextView myOutput;
    TextView tv_sensor;
    TextView goal_count;
    TextView time;
    TextView kcal;
    SensorManager sm;
    Sensor sensor_step_detector;
    int steps = 0;
    CircleProgressBar circleProgressBar;
    private static Handler mHandler ;
    Long ell;  //타이머 시간(초로 계산하여 나옴)

    //스톱워치 상태를 위한 상수
    final static int RUNNING = 1;
    final static int STOP = 0;
    int mStatus = RUNNING;
    long mBaseTime;
    long mPauseTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goal_count = (TextView)findViewById(R.id.tv2);  //목표 값 가져오기
        time = findViewById(R.id.tv4);
        //myOutput = (TextView)findViewById(R.id.tv4);
        tv_sensor = findViewById(R.id.sensor);
        tv_sensor.setText("0");  //걸음 수 초기화 및 출력

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);  //센서 매니저 생성
        sensor_step_detector = sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);  //스텝 감지 센서 등록

        circleProgressBar = findViewById(R.id.cpb_circlebar);

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){

            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }

        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();

      /*  kcalRunnable runnable1 = new kcalRunnable();
        Thread thread1 = new Thread(runnable1);
        thread1.setDaemon(true);
        thread1.start();
*/
        circleBar();  //원형 프로세스 바
        timerOn();  //타이머
    }

    //원형 프로세스바
    public void circleBar(){

        int num = Integer.parseInt(goal_count.getText().toString());
        CircleProgressBar.ProgressFormatter progressFormatter = new CircleProgressBar.ProgressFormatter() {
            @Override
            public CharSequence format(int progress, int max) {
                return String.format(DEFAULT_PATTERN,steps, num);
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
        int degree = start-(int)((steps/(double)num)*pi);
        circleProgressBar.setMax(num);
        circleProgressBar.setProgress(steps);
        circleProgressBar.setStartDegree(degree);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this, sensor_step_detector, SensorManager.SENSOR_DELAY_NORMAL );
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {  //센서값이 변할 때
        switch(sensorEvent.sensor.getType()){  //센서 유형이 스텝 감지 센서인 경우 걸음수를 1증가하여 출력력
            case Sensor.TYPE_STEP_DETECTOR:
                tv_sensor.setText(""+ (++steps));
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }


    //스톱워치에 사용하는 핸들러
    Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {

            time.setText(getEllapse()); //텍스트뷰 수정
            handler.sendEmptyMessage(0);  //메시지 다시 보냄(0은 메시지 구분 위ㅏㅁ)
            allStop();

        }
    };

    @Override
    protected void onDestroy() {
        handler.removeMessages(0);  //메시지 지워서 메모리누수 방지
        super.onDestroy();
    }

    public void timerOn(){
        switch(mStatus){
            case RUNNING:  //시작상태이면
                mBaseTime = SystemClock.elapsedRealtime();  //현재값 세팅
                handler.sendEmptyMessage(0);  //헨들러로 메시지 보냄
                System.out.println("러닝 반복");
                //mStatus = STOP;
                break;
            /*case STOP:
                long now = SystemClock.elapsedRealtime();
                mBaseTime += (now - mPauseTime);
                handler.sendEmptyMessage(0);
                mStatus = RUNNING;
                break;*/
            case STOP:
                handler.removeMessages(0);
                //처음상태로 원상복귀시킴
                time.setText(String.format("%02d:%02d:%02d", ell / 1000 / 60 / 60, ell / 1000 / 60, (ell/1000)%60));
                //mStatus = RUNNING;
                break;
        }
    }

    //타이머 시간 계산
    public String getEllapse(){
        long now = SystemClock.elapsedRealtime();
        ell = now - mBaseTime;   //현재 시간과 지난 시간을 빼서 ell값을 구함함
       String sEll = String.format("%02d:%02d:%02d", ell / 1000 / 60 / 60, ell / 1000 / 60, (ell/1000)%60);
        return sEll;
    }

    public void allStop(){  //시간 멈추기
        String a = goal_count.getText().toString();
        if(steps > Integer.parseInt(a)){
            mStatus = STOP;
            runnable.stop();
            timerOn();
        }else
            mStatus = RUNNING;
    }




/*
    public void calKcal(){
        kcal = findViewById(R.id.tv6);
        double met;
        met = 3.3* 3.5 * 70 * (3);//(ell/1000/60); //3.3 * 3.5 * 70 * ell;
        kcal.setText("aa");//String.valueOf(5) );
    }*/



    class BackRunnable implements Runnable{
        private boolean stopped=false;
        @Override
        public void run() {
            while(!stopped){   //!Thread.currentThread().isInterrupted()
                circleBar();
               // calKcal();
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
   /* class kcalRunnable implements Runnable{
        private boolean stopped=false;
        @Override
        public void run() {
            while(!stopped){   //!Thread.currentThread().isInterrupted()
                    calKcal();
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }*/
}
