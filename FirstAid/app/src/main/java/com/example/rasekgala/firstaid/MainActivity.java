package com.example.rasekgala.firstaid;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("First Aid+");

        StartAnimations();

        //this is a thread for splash screen, it runs in the background for specified second
        Thread background = new Thread() {
            public void run() {

                try{

                    //set the activity to sleep for 4 seconds
                    sleep(4*1000);

                    // After 2 seconds redirect to another intent
                    Intent i = new Intent(getBaseContext(),AllMessageActivity.class);
                    startActivity(i);


                    //Remove activity
                    finish();

                } catch (Exception e) {

                }
            }
        };
        // start thread
        background.start();


    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

    private void StartAnimations() {

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();

        RelativeLayout mainLinearLayout=(RelativeLayout) findViewById(R.id.activity_main);
        mainLinearLayout.clearAnimation();

        mainLinearLayout.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();

        ImageView iv = (ImageView) findViewById(R.id.imageView);
        iv.clearAnimation();
        iv.startAnimation(anim);
    }

}
