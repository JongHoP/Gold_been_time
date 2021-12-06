package com.example.testsplash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

public class MainBeforeLock extends AppCompatActivity {
    public static Activity activity;
    private long lastTimeBackPressed;
    TextView textView;
    ViewFlipper v_fllipper;
    Animation anim;
    ImageView anim_imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_before_lock);
        randomThing();
        /*anim_imageview = (ImageView) findViewById(R.id.imageView7);
        anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_left1);
        anim_imageview.startAnimation(anim);*/
        activity = MainBeforeLock.this;


    /*    int images[] = {
                R.drawable.kakao,
                R.drawable.sam
        };

        v_fllipper = findViewById(R.id.image_slide);

        for(int image : images) {
            fllipperImages(image);
        }*/

    }

    public void ima (){

    }

    public void fllipperImages(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        v_fllipper.addView(imageView);      // 이미지 추가
        v_fllipper.setFlipInterval(4000);       // 자동 이미지 슬라이드 딜레이시간(1000 당 1초)
        v_fllipper.setAutoStart(true);          // 자동 시작 유무 설정
        v_fllipper.startFlipping();
        // animation
        v_fllipper.setInAnimation(this,android.R.anim.slide_in_left);
        v_fllipper.setOutAnimation(this,android.R.anim.slide_out_right);
        overridePendingTransition(R.anim.slide_in_left1, R.anim.slide_out_right);
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
