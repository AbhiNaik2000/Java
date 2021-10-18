package com.example.vms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout main;
    ImageView imageView;
    TextView textView, text;
    Animation fadeSlow, fade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main = (ConstraintLayout) findViewById(R.id.main);
        imageView = (ImageView) findViewById(R.id.imageViewVisitor);
        textView = (TextView) findViewById(R.id.textViewVisitorManagementSystem);
        text = (TextView) findViewById(R.id.textViewSafetyAtYourHand);

        fade = AnimationUtils.loadAnimation(this, R.anim.fade);
        fadeSlow = AnimationUtils.loadAnimation(this, R.anim.fadeslow);
        imageView.setAnimation(fadeSlow);
        textView.setAnimation(fadeSlow);
        text.setAnimation(fadeSlow);
        main.setAnimation(fade);
        final Intent intent = new Intent(this, IpAddressActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(intent);
                    finish();
                }
            }

        };
        timer.start();
    }
}