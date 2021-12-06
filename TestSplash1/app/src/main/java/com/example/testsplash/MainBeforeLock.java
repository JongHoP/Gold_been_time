package com.example.testsplash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

public class MainBeforeLock extends AppCompatActivity {
    public static Activity activity;
    private long lastTimeBackPressed;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_before_lock);
        activity = MainBeforeLock.this;
        randomThing();
    }

    public void nextLayout2(View v) {
        int id = v.getId();
        LinearLayout layout = (LinearLayout) v.findViewById(id);
        String tag = (String) layout.getTag();

        Intent it = new Intent(this, Pedometer.class);
        it.putExtra("it_tag", tag);
        startActivity(it);
    }

    public void nextLayout5(View v) {
        int id = v.getId();
        LinearLayout layout = (LinearLayout) v.findViewById(id);
        String tag = (String) layout.getTag();

        Intent it = new Intent(this, Timer.class);
        it.putExtra("it_tag", tag);
        startActivity(it);
    }

    @Override
    public void onBackPressed() {
        //프래그먼트 onBackPressedListener사용
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for(Fragment fragment : fragmentList){
            if(fragment instanceof onBackPressedListener){
                ((onBackPressedListener)fragment).onBackPressed();
                return;
            }
        }
        //두 번 클릭시 어플 종료
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500){
            finish();
            return;
        }
        lastTimeBackPressed = System.currentTimeMillis();
        Toast.makeText(this,"'뒤로' 버튼을 한 번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();

    }

    public void randomThing(){
        textView = (TextView) findViewById(R.id.content1);
        String[] text = {"Time is Gold",
                         "시간을 석섹스",
                         "행복하자"};
        int randomNum = (int)(Math.random() * text.length);

        //textView.setText(text[randomNum]);
        textView.setText("aaa");
    }

    public void scrollAnim(){

    }
}
