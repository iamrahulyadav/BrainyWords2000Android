package com.example.gisela.worldworld;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by Gisela on 6/8/17.
 */

public class Logo extends AppCompatActivity {

    MediaPlayer play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_logo);

        play = new MediaPlayer();
        playSound();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent street = new Intent(Logo.this, MainActivity.class);
                startActivity(street);
                finish();
            }
        }, 2500);

    }

    public void playSound()
    {
        AssetFileDescriptor openassets;

        if(!play.isPlaying())
        {
            try
            {
                play.reset();
                //open audio file from Assets folder
                openassets = getAssets().openFd("xtra/HEADINGS/00brainy_words_2000.mp3");

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
