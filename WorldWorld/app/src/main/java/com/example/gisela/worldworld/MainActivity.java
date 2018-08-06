package com.example.gisela.worldworld;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private MediaPlayer play;
    private HorizontalScrollView hsv;
    private TextView mBrainyWords;
    private LinearLayout streetScenes;

    //Zoom
    private float mScale = 1f;
    private ScaleGestureDetector mScaleDetector;
    GestureDetector gestureDetector;
    private RelativeLayout relativeLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        streetScenes = (LinearLayout) findViewById(R.id.street_scenes);
        Matrix scalingMatrix = new Matrix();
        hsv = (HorizontalScrollView) findViewById(R.id.horizontal_scroll);
        mBrainyWords = (TextView) findViewById(R.id.BrainyWords);

        //set font ComicSans
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "Fonts/ComicSans.ttf");
        mBrainyWords.setTypeface(custom_font);

        play = new MediaPlayer();
        //Zoom
        gestureDetector = new GestureDetector(this, new GestureListener());
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);

        mScaleDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.SimpleOnScaleGestureListener()
        {
            @Override
            public boolean onScale(ScaleGestureDetector detector)
            {
                float scale = 1 - detector.getScaleFactor();

                float prevScale = mScale;
                mScale += scale;

                if (mScale < 0.5f) // Minimum scale condition:
                    mScale = 0.5f;

                if (mScale > 1f) // Maximum scale condition:
                    mScale = 1f;
                ScaleAnimation scaleAnimation = new ScaleAnimation(1f / prevScale, 1f / mScale, 1f / prevScale, 1f / mScale, detector.getFocusX(), detector.getFocusY());
                scaleAnimation.setDuration(0);
                scaleAnimation.setFillAfter(true);
                relativeLayout.startAnimation(scaleAnimation);
                return true;
            }
        });

    }

    //Zoom
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);
        mScaleDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return gestureDetector.onTouchEvent(event);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            // double tap fired.
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                float distanceY) {
            LinearLayout streetScenes = (LinearLayout) findViewById(R.id.street_scenes);
            int maxScrollX = streetScenes.getMeasuredWidth() - streetScenes.getChildAt(0).getMeasuredWidth();
            if(hsv.getScrollX() >= maxScrollX - 1)
            {
                hsv.scrollTo(1, hsv.getScrollY());
            }
            else if(hsv.getScrollX() == 0)
            {
                //hsv.scrollTo(hsv.getScrollX() + 40000, hsv.getScrollY());
                hsv.scrollTo(maxScrollX - 2, hsv.getScrollY());
            }

            return true;
        }
    }


    public void playSound(View view)
    {
        String tag = view.getTag().toString();
        AssetFileDescriptor openassets;

        if(!play.isPlaying())
        {
            try
            {
                play.reset();
                //open audio file from Assets folder
                openassets = getAssets().openFd(tag);

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

    public void openStore(View view) throws InterruptedException {
        //get tag from xml layout
        String tag = view.getTag().toString();
        String cat = Parse(tag); //get cat
        playCategorySound(cat);
        while(play.isPlaying()){}
        Intent itemsActivity = new Intent(this, DisplayItems.class);
        Bundle b = new Bundle();
        b.putString("cat",tag);
        itemsActivity.putExtras(b);
        startActivity(itemsActivity);
    }

    public void openInterior(View view) throws InterruptedException {

        String tag = view.getTag().toString();
        playCategorySound(tag);
        while(play.isPlaying()){}
        Intent interiorActivity = new Intent(this, Interior.class);
        Bundle b = new Bundle();
        b.putString("cat",tag);
        interiorActivity.putExtras(b);
        startActivity(interiorActivity);
    }

    public String Parse(String tag)
    {
        if(!tag.equals("Construction") && !tag.equals("Outdoors/beach/creatures"))
        {
            String[] path = tag.split("/");
            tag = path[path.length-1];
        }

        return tag;

    }

    public void playCategorySound(String cat)
    {
        AssetFileDescriptor openassets;

        if(cat.equals("clothes"))
        {
            cat = "Clothing";
        }
        else if(cat.equals("Outdoors/beach/creatures"))
        {
            cat = "beach_creatures";
        }

        if(!play.isPlaying())
        {
            try
            {
                play.reset();
                //open audio file from Assets folder
                openassets = getAssets().openFd("xtra/HEADINGS/00" + cat + ".mp3");

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
        Runtime.getRuntime().gc();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Runtime.getRuntime().gc();
    }



}
