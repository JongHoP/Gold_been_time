package com.example.testsplash;

import static android.content.ContentValues.TAG;
import android.Manifest;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dinuscxj.progressbar.CircleProgressBar;

import java.util.ArrayList;
import java.util.List;

public class timerLockScreen extends Activity  {

    private static final String DEFAULT_PATTERN = "%s"+ ":" +"%s" + ":" + "%s" ;
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private Toast toast;
    private long backKeyPressedTime = 0;

    //접근성 서비스 객체생성
    LockApp lock = new LockApp();

    TextView selected_time;
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_lock_screen);

        lock.setFlag(false); //잠금 활성화
        circleProgressBar = findViewById(R.id.cpb_circlebar);

        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){

            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }
        permissionCheck();
        checkAccessibilityPermissions();
        setAccessibilityPermissions();

        selected_time = (TextView) findViewById(R.id.tv2); // 이전 화면에서 선택한 걸음을 받을 변수
        Intent myIntent = getIntent();
        String time = myIntent.getExtras().getString("time_value");
        selected_time.setText(time); // 받아온 시간 값을 selected_time 텍스트 박스에 저장한다.

        countDown(splitGoal());
        circleBar();
    }

    //입력받은 시간을 숫자와 문자로 나누기
    public String splitGoal(){

        String goal = selected_time.getText().toString();
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
//        Toast.makeText(this, goalNum, Toast.LENGTH_SHORT).show();
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

                int temp1 = Integer.parseInt(min);
                int temp2 = Integer.parseInt(time);
                Log.e(TAG,"time = " + temp2);
                Log.e(TAG,"min = " + temp1);

                if (temp2 >= 10000) {
                    temp2 = temp2 / 10000;
                    int temp3 = temp1 / temp2;
                    Log.e(TAG,"min / time = " + temp3);
                    min = Integer.toString(temp3);
                    Log.e(TAG,"min = " + min);
                }

                ex.setText(hour + ":" + min + ":" + second);
            }

            // 제한시간 종료시
            public void onFinish() {
                // 변경 후
                ex.setText("종료!");
                Intent myIntent1 = new Intent(getApplicationContext(),Complete.class);
                startActivity(myIntent1);
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

