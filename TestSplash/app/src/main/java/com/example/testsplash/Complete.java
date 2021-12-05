package com.example.testsplash;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


public class Complete extends AppCompatActivity {

    Animation animation;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete);

        textView = findViewById(R.id.tt1);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink_text);
        textView.startAnimation(animation);
    }





}

