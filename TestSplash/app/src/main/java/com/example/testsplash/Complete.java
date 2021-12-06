package com.example.testsplash;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;


public class Complete extends AppCompatActivity {
    public static Activity activity;
    // 마지막으로 뒤로 가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    // 첫 번째 뒤로 가기 버튼을 누를 때 표시
    private Toast toast;
    LockApp lock = new LockApp();

    Animation animation;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete);
        lock.setFlag(true);

        activity = Complete.this;
        textView = findViewById(R.id.tt1);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink_text);
        textView.startAnimation(animation);
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
//            toast = Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG);
//            toast.show();
            return;
        }
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지나지 않았으면 종료
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            finish();
            Intent myIntent = new Intent(getApplicationContext(),MainBeforeLock.class);
            startActivity(myIntent);

//            DayMainActivity DM = (DayMainActivity) DayMainActivity.activity;
//            Pedometer pedo = (Pedometer) Pedometer.activity;
//            MainBeforeLock MB = (MainBeforeLock) MainBeforeLock.activity;
//            ColorFragment CF1 = (ColorFragment) ColorFragment.fragment1;
//            Color2Fragment CF2 = (Color2Fragment) Color2Fragment.fragment2;
//            Color3Fragment CF3 = (Color3Fragment) Color3Fragment.fragment3;
//
//            DM.finish();
//            pedo.finish();
//            MB.finish();
//
//            toast.cancel();
//            toast = Toast.makeText(this,"이용해 주셔서 감사합니다.",Toast.LENGTH_LONG);
//            toast.show();
        }
    }
}

