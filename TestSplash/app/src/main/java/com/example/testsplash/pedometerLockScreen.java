package com.example.testsplash;

import android.Manifest;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dinuscxj.progressbar.CircleProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static android.content.ContentValues.TAG;

public class pedometerLockScreen extends Activity implements SensorEventListener { //
    private static final String DEFAULT_PATTERN = "%d"+ "/" +"%d";

    public static Object activity2;
    private Activity activity;
    private boolean mIsBound;
    private Intent intent;

    private PedometerService mService;

    Animation animation;
    ImageView imageView;
    BackRunnable runnable = new BackRunnable();
    private Thread timeThread = null;
    TextView tv_sensor;
    TextView time;
    TextView selected_walk;
    SensorManager sm;
    Sensor sensor_step_detector;
    int steps = 19999;
    CircleProgressBar circleProgressBar;
    private static Handler mHandler ;
    Long ell;  //타이머 시간(초로 계산하여 나옴)
    final static int PERMISSION_REQUEST_CODE = 1000;


    //스톱워치 상태를 위한 상수
    final static int RUNNING = 1;
    final static int STOP = 0;
    int mStatus = RUNNING;
    long mBaseTime;
    long mPauseTime;

    // 마지막으로 뒤로 가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    // 첫 번째 뒤로 가기 버튼을 누를 때 표시
    private Toast toast;
    LockApp lock = new LockApp();

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_pedometer_main);
        lock.setFlag(false);

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);  //센서 매니저 생성
        sensor_step_detector = sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);  //스텝 감지 센서 등록

        circleProgressBar = findViewById(R.id.cpb_circlebar);

        if(ContextCompat.checkSelfPermission(this,

                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){

            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }
        permissionCheck();
        checkAccessibilityPermissions();
        setAccessibilityPermissions();

        selected_walk = (TextView) findViewById(R.id.swalk); // 이전 화면에서 선택한 걸음을 받을 변수
        Intent myIntent = getIntent();
        String walk = myIntent.getExtras().getString("walk_value");
        selected_walk.setText(walk); // 받아온 걸음수를 selected_walk 텍스트 박스에 저장한다.

//        Toast.makeText(pedometerLockScreen.this, "선택된 아이템 : "
//                + walk, Toast.LENGTH_SHORT).show();

        imageView = findViewById(R.id.imageView);
        imageView.setColorFilter(Color.parseColor("#0066cc"));
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake_shoe);
        imageView.startAnimation(animation);

        time = findViewById(R.id.tv4);
        tv_sensor = findViewById(R.id.sensor);
        tv_sensor.setText("0");  //걸음 수 초기화 및 출력

        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();

        circleBar();  //원형 프로세스 바
        timerOn();  //타이머
        intent = new Intent(this,PedometerService.class);

    }


    //원형 프로세스바
    public void circleBar(){
        int num = Integer.parseInt(selected_walk.getText().toString());
        CircleProgressBar.ProgressFormatter progressFormatter = new CircleProgressBar.ProgressFormatter() {
            @Override
            public CharSequence format(int progress, int max) {
                return String.format(DEFAULT_PATTERN,steps, num);
            }
        };

        circleProgressBar.setProgressFormatter(progressFormatter);
        circleProgressBar.setProgressTextColor(ContextCompat.getColor(this, R.color.white_gray_color));
        circleProgressBar.setProgressBackgroundColor(ContextCompat.getColor(this, R.color.white_gray_color));
        circleProgressBar.setProgressStartColor(ContextCompat.getColor(this, R.color.teal_200));
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
        String a = selected_walk.getText().toString();
        if(steps > Integer.parseInt(a)){
            mStatus = STOP;
            runnable.stop();
            timerOn();

            Intent myIntent1 = new Intent(getApplicationContext(),Complete.class);
            startActivity(myIntent1);

        }else
            mStatus = RUNNING;
    }

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

   private void permissionCheck() {
       if (android.os.Build.VERSION.SDK_INT >= 23) {
           int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
           ArrayList<String> arrayPermission = new ArrayList<String>();

           if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
               arrayPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
           }

           permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
           if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
               arrayPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
           }

           if (arrayPermission.size() > 0) {
               String strArray[] = new String[arrayPermission.size()];
               strArray = arrayPermission.toArray(strArray);
               ActivityCompat.requestPermissions(this, strArray, PERMISSION_REQUEST_CODE);
           } else {
               // Initialize 코드
           }
       }
   }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length < 1) {
//                    Toast.makeText(this, "Failed get permission", Toast.LENGTH_SHORT).show();
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                    return ;
                }

                for (int i=0; i<grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission is denied : " + permissions[i], Toast.LENGTH_SHORT).show();
                        finish();
                        return ;
                    }
                }

//                Toast.makeText(this, "Permission is granted", Toast.LENGTH_SHORT).show();
                // Initialize 코드
            }
            break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public boolean checkAccessibilityPermissions(){
        AccessibilityManager accessibilityManager =
                (AccessibilityManager)getSystemService(Context.ACCESSIBILITY_SERVICE);

        List<AccessibilityServiceInfo> list =
                accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);

        Log.d("service_test", "size : " + list.size());
        for(int i = 0; i < list.size(); i++){
            AccessibilityServiceInfo info = list.get(i);
            if(info.getResolveInfo().serviceInfo.packageName.equals(getApplication().getPackageName())){
                return true;
            }
        }
        return false;
    }

    public void setAccessibilityPermissions(){
        AlertDialog.Builder permissionDialog = new AlertDialog.Builder(this);
        permissionDialog.setTitle("접근성 권한 설정");
        permissionDialog.setMessage("앱을 사용하기 위해 접근성 권한이 필요합니다.");
        permissionDialog.setPositiveButton("허용", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //접근성 화면으로 이동하기
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                return;
            }
        }).create().show();
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        // 기존 뒤로 가기 버튼의 기능을 막기 위해 주석 처리 또는 삭제

        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지났으면 Toast 출력
        // 2500 milliseconds = 2.5 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지나지 않았으면 종료
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            finish();

            DayMainActivity DM = (DayMainActivity) DayMainActivity.activity;
            Pedometer pedo = (Pedometer) Pedometer.activity;
            MainBeforeLock MB = (MainBeforeLock) MainBeforeLock.activity;
            ColorFragment CF1 = (ColorFragment) ColorFragment.fragment1;
            Color2Fragment CF2 = (Color2Fragment) Color2Fragment.fragment2;
            Color3Fragment CF3 = (Color3Fragment) Color3Fragment.fragment3;

            DM.finish();
            pedo.finish();
            MB.finish();



            toast.cancel();
            toast = Toast.makeText(this,"이용해 주셔서 감사합니다.",Toast.LENGTH_LONG);
            toast.show();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm:ss");

        String getTime = dateFormat2.format(date);
        Log.e(TAG, "current time is " + getTime);
        return getTime;
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

        //Intent message_intent = new Intent(this.getPackageManager().getLaunchIntentForPackage("com.android.mms"));
        Intent message_intent = this.getPackageManager().getLaunchIntentForPackage("com.samsung.android.messaging");
        startActivity(message_intent);
    }

    public void pedometerback(){
        startService(intent);
        android.util.Log.i("Music Start Service","StartService()");
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PedometerService.LocalBinder binder = (PedometerService.LocalBinder) iBinder;
            mService =binder.getService();
            mIsBound = true;

            Log.e("LOG", "onServiceConnected()");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mService = null;
            mIsBound = false;
            Log.e("LOG", "onServiceDisconnected()");
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, PedometerService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }


}
