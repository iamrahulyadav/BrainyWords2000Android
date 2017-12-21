package com.example.gisela.worldworld;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by Gisela on 6/8/17.
 */

public class QuizCongrats extends AppCompatActivity {

    private ImageView logo;

    private MediaPlayer play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_congrats);

        logo = (ImageView) findViewById(R.id.logo);

        playSound("Quiz_Sounds/congratulations.mp3");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 45000);

    }

    public void playSound(String path)
    {
        AssetFileDescriptor openassets;

        try
        {
            //open audio file from Assets folder
            openassets = getAssets().openFd(path);

            play = new MediaPlayer();
            play.setDataSource(openassets.getFileDescriptor(),openassets.getStartOffset(),openassets.getLength());
            play.prepare();
            play.start();


        } // end try
        catch (IOException e)
        {
            System.out.print(e.toString());
        } // end catch


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (play != null){
            play.stop();
            if (isFinishing()){
                play.stop();
                play.release();
            }
        }
    }



}
