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

public class QuizCelebrate extends AppCompatActivity {

    private Animation movetop;
    private Animation movebottom;
    private Animation movetop1;
    private Animation movebottom1;
    private Animation zoom;
    private Animation move;

    //golden coins
    private ImageView mCoin1;
    private ImageView mCoin2;
    private ImageView mCoin3;
    private ImageView mCoin4;
    private ImageView mCoin5;

    private ImageView mBigbag;

    private MediaPlayer play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_celebrate);

        mCoin1 = (ImageView) findViewById(R.id.coin1);
        mCoin2 = (ImageView) findViewById(R.id.coin2);
        mCoin3 = (ImageView) findViewById(R.id.coin5);
        mCoin4 = (ImageView) findViewById(R.id.coin4);
        mCoin5 = (ImageView) findViewById(R.id.coin3);

        mBigbag = (ImageView) findViewById(R.id.bigbag);

        move = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move);
        zoom = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom);
        movetop = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.movetop);
        movebottom = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.movebottom);
        movetop1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.movetop1);
        movebottom1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.movebottom1);

        startAnimation();
        playSound("Quiz_Sounds/correct_answer.mp3");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 4800);

    }

    public void startAnimation()
    {

        mCoin1.startAnimation(movetop);
        mCoin2.startAnimation(movetop1);
        mCoin3.startAnimation(move);
        mCoin4.startAnimation(movebottom1);
        mCoin5.startAnimation(movebottom);

        //animate big bag
        mBigbag.startAnimation(zoom);

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
