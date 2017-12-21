package com.example.gisela.worldworld;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Range;
import android.view.View;
import android.view.ViewDebug;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Gisela on 2/12/17.
 */

public class Quiz extends AppCompatActivity {

    private QuestionLibrary mQuestionLibrary;
    private Random num = new Random();
    private MediaPlayer play;

    private TextView mquestionText;
    private ImageView mImageChoice1;
    private ImageView mImageChoice2;
    private ImageView mImageChoice3;
    private ImageView mImageChoice4;

    private Drawable mAnswer;
    private int mScore = 0;
    private String cat;
    private int mcorrectId;
    private int num1 = 0;
    private int mtry = 0;
    private int scoins = 0;
    private int bags = 0;

    private List<String> soundpath1;
    private List<String> congrats;

    //golden coins
    private ImageView mCoin1;
    private ImageView mCoin2;
    private ImageView mCoin3;
    private ImageView mCoin4;
    private ImageView mCoin5;

    //silver coins
    private ImageView mScoin1;
    private ImageView mScoin2;


    //bags
    private ImageView mBag1;
    private ImageView mBag2;
    private ImageView mBag3;
    private ImageView mBag4;
    private ImageView mBag5;

    private boolean bag;
    private boolean end;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Bundle b = getIntent().getExtras();
        cat = b.getString("cat");

        if(cat.contains("addition"))
        {
            cat = "School/addition/addition_quiz";
        }
        else if(cat.contains("letters"))
        {
            cat = "School/letters/letters_quiz";
        }

        mImageChoice1 = (ImageView) findViewById(R.id.choice1);
        mImageChoice2 = (ImageView) findViewById(R.id.choice2);
        mImageChoice3 = (ImageView) findViewById(R.id.choice3);
        mImageChoice4 = (ImageView) findViewById(R.id.choice4);
        mquestionText = (TextView) findViewById(R.id.question);

        //set font ComicSans
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "Fonts/ComicSans.ttf");
        mquestionText.setTypeface(custom_font);

        mCoin1 = (ImageView) findViewById(R.id.coin1);
        mCoin2 = (ImageView) findViewById(R.id.coin2);
        mCoin3 = (ImageView) findViewById(R.id.coin5);
        mCoin4 = (ImageView) findViewById(R.id.coin4);
        mCoin5 = (ImageView) findViewById(R.id.coin3);

        mScoin1 = (ImageView) findViewById(R.id.scoin1);
        mScoin2 = (ImageView) findViewById(R.id.scoin2);

        mBag1 = (ImageView) findViewById(R.id.bag1);
        mBag2 = (ImageView) findViewById(R.id.bag2);
        mBag3 = (ImageView) findViewById(R.id.bag5);
        mBag4 = (ImageView) findViewById(R.id.bag4);
        mBag5 = (ImageView) findViewById(R.id.bag3);

        bag = false;
        end = false;

        play = new MediaPlayer();
        play.pause();


        updateFirstQuestion();
    }

    private void updateFirstQuestion()
    {
        getQuizData(); // get all the possible answers, question and correct answer

        //play sound (question)
        try{
            mQuestionLibrary.getQuestion().prepare();
            mQuestionLibrary.getQuestion().start();
        }
        catch(IOException e){

            System.out.print(e.toString());
        }

        mImageChoice1.setImageDrawable(mQuestionLibrary.getChoice1());
        mImageChoice2.setImageDrawable(mQuestionLibrary.getChoice2());
        mImageChoice3.setImageDrawable(mQuestionLibrary.getChoice3());
        mImageChoice4.setImageDrawable(mQuestionLibrary.getChoice4());
        mquestionText.setText(mQuestionLibrary.getQuestionText());

        mAnswer = mQuestionLibrary.getAnswer();
    }

    private void updateQuestion()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally{
                    getQuizData(); // get all the possible answers, question and correct answer
                }
            }
        });

        if(bag)
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try{
                        mQuestionLibrary.getQuestion().prepare();
                        mQuestionLibrary.getQuestion().start();
                        bag = false;
                    }
                    catch(IOException e){

                        System.out.print(e.toString());
                    }

                }
            }, 5500);
        }
        else if(end)
        {
            finish();
        }
        else{
            //play sound (question)
            try{
                mQuestionLibrary.getQuestion().prepare();
                mQuestionLibrary.getQuestion().start();
            }
            catch(IOException e){

                System.out.print(e.toString());
            }
        }

        mImageChoice1.setImageDrawable(mQuestionLibrary.getChoice1());
        mImageChoice2.setImageDrawable(mQuestionLibrary.getChoice2());
        mImageChoice3.setImageDrawable(mQuestionLibrary.getChoice3());
        mImageChoice4.setImageDrawable(mQuestionLibrary.getChoice4());
        mquestionText.setText(mQuestionLibrary.getQuestionText());

        mAnswer = mQuestionLibrary.getAnswer();
    }

    private void updateScore()
    {
        if(scoins == 1 && (mScoin1.getVisibility() == View.INVISIBLE))
        {
            mScoin1.setVisibility(View.VISIBLE);
            mScore--;
        }
        else{

            if(scoins == 2 || mScore == 5)
            {
                mScoin2.setVisibility(View.VISIBLE);
                mScoin1.setVisibility(View.INVISIBLE);
                mScoin2.setVisibility(View.INVISIBLE);
                scoins = 0;
            }

            switch (mScore) {
                case 1: mCoin1.setVisibility(View.VISIBLE);
                    break;
                case 2: mCoin2.setVisibility(View.VISIBLE);
                    break;
                case 3: mCoin3.setVisibility(View.VISIBLE);
                    break;
                case 4: mCoin4.setVisibility(View.VISIBLE);
                    break;
                case 5: mCoin5.setVisibility(View.VISIBLE);
                    setBags();
                    mScore = 0;
                    if(mBag5.getVisibility() != View.VISIBLE)
                    {
                        bag = true;
                        startCelebration();
                    }
                    break;
                }

            }
    }

    public List<QuestionLibrary> getQuizData()
    {
        MediaPlayer mSoundQuestion;
        List<QuestionLibrary> data = new ArrayList<>();
        Drawable image1;
        Drawable image2;
        Drawable image3;
        Drawable image4;
        String[] addition;
        int q_num1 = 0;
        int q_num2 = 0;
        int ans = 0;
        String questionText;
        Drawable correctImage;
        int n; //random number
        String correctAnswer;

        AssetManager assets = getAssets(); // get app's AssetManager
        InputStream[] answers = new InputStream[4]; //read in images and store all the answers
        InputStream correctA ;
        AssetFileDescriptor openassets;

        try
        {

            //get sound for question
            //get all the path of the files inside Animals folder
            String[] soundpath = assets.list(cat + "/sounds");

            if(num1 == 0 || soundpath1.size() == 0)
            {
                soundpath1 = new LinkedList<>(Arrays.asList(soundpath));
                num1++;
            }

            Collections.shuffle(soundpath1);


            // get an InputStream to the asset representing the next item
            //provide access to the file
            openassets = getAssets().openFd(cat + "/sounds/" + soundpath1.get(0));

            //only for addition quiz to prevent duplicate answers
            if(cat.contains("addition"))
            {
                addition = soundpath1.get(0).split("_");
                if(addition.length < 3)
                {
                    addition = addition[1].split("");
                    q_num1 = Integer.parseInt(addition[1]);
                    q_num2 = Integer.parseInt(addition[3]);
                }
            }

            //Parse Question
            questionText = ParseQuestionText(soundpath1.get(0));

            correctAnswer = soundpath1.get(0).replace("mp3", "png");
            soundpath1.remove(0);


            mSoundQuestion = new MediaPlayer();
            mSoundQuestion.setDataSource(openassets.getFileDescriptor(),openassets.getStartOffset(),openassets.getLength());


            //get all the paths of the files inside the category folder
            List<String> imagepath = new LinkedList<String>(Arrays.asList(assets.list(cat)));

            //get all possible answers
            if(cat.contains("addition"))
            {
                ans = q_num1 + q_num2;
                answers[0] = assets.open(cat + "/" + Integer.toString(ans) + ".png");
                Log.d("add", Integer.toString(ans));
                //remove correct answer from list to avoid duplicates
                imagepath.remove(Integer.toString(ans) + ".png");
            }
            else{
                answers[0] = assets.open(cat + "/" + correctAnswer); //load asset
                //remove correct answer from list to avoid duplicates
                imagepath.remove(correctAnswer);
            }

            //store correct answer
            correctA = answers[0];



            //get other possible answers (wrong answers)
            if(!cat.contains("addition"))
            {
                for(int i = 1; i < answers.length; i++)
                {
                    n = num.nextInt(imagepath.size()-1);
                    answers[i] = assets.open(cat + "/" + imagepath.get(n)); //load asset
                    imagepath.remove(n); //remove to avoid duplicates
                }
            }
            else{

                for(int i = 1; i < answers.length; i++)
                {
                    n = num.nextInt(imagepath.size()-1);
                    answers[i] = assets.open(cat + "/" + imagepath.get(n)); //load asset
                    Log.d("add", "wrong: " + imagepath.get(n));
                    imagepath.remove(n); //remove to avoid duplicates
                }
            }

            //shuffle possible answers and assign them to the right drawable
            Collections.shuffle(Arrays.asList(answers));

            image1 = Drawable.createFromStream(answers[0], null);
            image2 = Drawable.createFromStream(answers[1], null);
            image3 = Drawable.createFromStream(answers[2], null);
            image4 = Drawable.createFromStream(answers[3], null);
            correctImage = Drawable.createFromStream(correctA, null);

            //look for the correct answer in the shuffled array
            for(int i = 0; i < answers.length; i++)
            {
                if(answers[i].equals(correctA))
                {
                    mcorrectId = i + 1;
                    break;
                }
            }

            mQuestionLibrary = new QuestionLibrary(mSoundQuestion, questionText, image1, image2, image3, image4, correctImage);
            data.add(mQuestionLibrary);


        } // end try
        catch (IOException e)
        {
            System.out.print(e.toString());
        } // end catch

        return  data;


    }

    public void checkAnswer(View view)
    {
        if(!play.isPlaying())
        {
            String tag = view.getTag().toString();
            if(tag.equals(Integer.toString(mcorrectId))){
                mScore++;
                updateScore();
                playSound("Quiz_Sounds/correct_answer.mp3");
                updateQuestion();
                mtry = 0;
            }
            else{
                playSound("Quiz_Sounds/incorrect_answer.mp3");
                mtry++;
                scoins++;
                if(mtry == 2)
                {
                    updateQuestion();
                    mtry = 0;

                }

            }
        }

    }

    public void playSound(String path)
    {
        AssetFileDescriptor openassets;

        if(!play.isPlaying())
        {
            try
            {
                play.reset();
                play = new MediaPlayer();

                //open audio file from Assets folder
                openassets = getAssets().openFd(path);
                play.setDataSource(openassets.getFileDescriptor(),openassets.getStartOffset(),openassets.getLength());
                play.prepare();
                play.start();

                if(path.equals("Quiz_Sounds/correct_answer.mp3")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                playCorrectAnswer();
                            }
                        }
                    });

                }
            } // end try
            catch (IOException e)
            {
                System.out.print(e.toString());
            } // end catch

        }

    }

    public void playCorrectAnswer()
    {
        AssetManager assets = getAssets(); // get app's AssetManager
        AssetFileDescriptor openassets;
        String path = "Quiz_Sounds/correct";
        int n;

        try
        {

            //get congrats sounds
            //get all the path of the files inside the folder
            String[] congrat = assets.list(path);

            congrats = new LinkedList<>(Arrays.asList(congrat));

            //shuffle sounds
            Collections.shuffle(congrats);
            n = num.nextInt(congrats.size()-1);

            play.reset();
            play = new MediaPlayer();
            openassets = getAssets().openFd(path + "/" + congrats.get(n));
            play.setDataSource(openassets.getFileDescriptor(),openassets.getStartOffset(),openassets.getLength());
            play.prepare();
            play.start();

        } // end try
        catch (IOException e)
        {
            System.out.print(e.toString());
        } // end catch


    }

    public void setBags()
    {
        mCoin1.setVisibility(View.INVISIBLE);
        mCoin2.setVisibility(View.INVISIBLE);
        mCoin3.setVisibility(View.INVISIBLE);
        mCoin4.setVisibility(View.INVISIBLE);
        mCoin5.setVisibility(View.INVISIBLE);

        if (bags == 0) {
            mBag1.setVisibility(View.VISIBLE);
            bags++;
        } else if (bags == 1) {
            mBag2.setVisibility(View.VISIBLE);
            bags++;
        } else if (bags == 2) {
            mBag3.setVisibility(View.VISIBLE);
            bags++;
        } else if (bags == 3) {
            mBag4.setVisibility(View.VISIBLE);
            bags++;
        } else if (bags == 4) {
            mBag5.setVisibility(View.VISIBLE);
            startCongratulations();
            end = true;
        }

    }

    public String ParseQuestionText(String correctAnswerText)
    {
        correctAnswerText = correctAnswerText.replace(".mp3","");

        if(!cat.contains("addition"))
        {
            correctAnswerText = correctAnswerText.replaceAll("\\d","");
            correctAnswerText = correctAnswerText.replace("_"," ");
            correctAnswerText = correctAnswerText.replace("}", "?");
        }
        else
        {
            String[] addition = correctAnswerText.split("_");
            correctAnswerText = addition[addition.length-1];
        }

        return correctAnswerText;

    }

    public void startCelebration()
    {
        Intent celebrate = new Intent(this, QuizCelebrate.class);
        startActivity(celebrate);
    }

    public void startCongratulations()
    {
        Intent congrats = new Intent(this, QuizCongrats.class);
        startActivity(congrats);
        finish();
    }

    public void goHome (View view)
    {
        Intent home = new Intent(Quiz.this, MainActivity.class);
        startActivity(home);
        finish();
    }

    public void goBack (View view)
    {
        finish();
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

}
