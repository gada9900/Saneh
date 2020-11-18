package com.example.saneh;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashscreenActivity extends AppCompatActivity {

    VideoView splash ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splashscreen);
       // getSupportActionBar().hide();
        splash = (VideoView)findViewById(R.id.videoView);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/"+ R.raw.splash);
        splash.setVideoURI(video);

        splash.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(isFinishing())
                    return;

                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                boolean firstStart = prefs.getBoolean("firstStart", true);

                if(firstStart) {
                    startActivity(new Intent(SplashscreenActivity.this,onBoarding.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashscreenActivity.this,Login.class));
                    finish();
                }
            }
        });
        splash.start();
    }
}
