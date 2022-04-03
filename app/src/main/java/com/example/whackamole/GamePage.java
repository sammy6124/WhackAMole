package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class GamePage extends AppCompatActivity {

    static String sTag = "GamePage";
    public static ArrayList<Button> bPos = new ArrayList<>();

    private static Button bRunGame;
    private Button bDebug;
    private TextView tCountNumber;
    private static EditText eLevel;
    private static TextView tTime;

    public static int iCount = 0;
    public static int iLevel = 0;
    StringBuilder sDebug = new StringBuilder();
    public static Handler mHandler;

    static GameMode gamemode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(sTag,"onCreate start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        mHandler = new Handler();

        tCountNumber = (TextView)findViewById(R.id.countnumberid);
//        bDebug = (Button)findViewById(R.id.debug);
        bRunGame = (Button)findViewById(R.id.rungameId);
        eLevel = (EditText)findViewById(R.id.level_id);
        tTime = (TextView)findViewById(R.id.timeid);
        gamemode = new GameMode();

        initButton();
        Log.i(sTag,"onCreate end");
    }

    private void initButton(){
        Log.i(sTag,"initButton start");
        bPos.add((Button)findViewById(R.id.B11));
        bPos.add((Button)findViewById(R.id.B12));
        bPos.add((Button)findViewById(R.id.B13));
        bPos.add((Button)findViewById(R.id.B14));
        bPos.add((Button)findViewById(R.id.B15));

        bPos.add((Button)findViewById(R.id.B21));
        bPos.add((Button)findViewById(R.id.B22));
        bPos.add((Button)findViewById(R.id.B23));
        bPos.add((Button)findViewById(R.id.B24));
        bPos.add((Button)findViewById(R.id.B25));

        bPos.add((Button)findViewById(R.id.B31));
        bPos.add((Button)findViewById(R.id.B32));
        bPos.add((Button)findViewById(R.id.B33));
        bPos.add((Button)findViewById(R.id.B34));
        bPos.add((Button)findViewById(R.id.B35));

        bPos.add((Button)findViewById(R.id.B41));
        bPos.add((Button)findViewById(R.id.B42));
        bPos.add((Button)findViewById(R.id.B43));
        bPos.add((Button)findViewById(R.id.B44));
        bPos.add((Button)findViewById(R.id.B45));

        bPos.add((Button)findViewById(R.id.B51));
        bPos.add((Button)findViewById(R.id.B52));
        bPos.add((Button)findViewById(R.id.B53));
        bPos.add((Button)findViewById(R.id.B54));
        bPos.add((Button)findViewById(R.id.B55));
        Log.i(sTag,"initButton end");
    }

    public void RunGame_OnClick(View view){
        bRunGame.setVisibility(View.INVISIBLE);
        iCount = 0;
        iLevel = Integer.parseInt(String.valueOf(eLevel.getText()));

        Thread model = new Thread(gamemode.model_Normal);

        cdt.start();
        model.start();
    }

    public void Button_OnClick(View view){
        try {
            int id = view.getId();
            for(Button i : bPos){
                if(i.getId() == id){
                    i.setVisibility(View.INVISIBLE);
                    iCount++;
                    tCountNumber.setText(String.valueOf(iCount));
                }
            }
        } catch (Exception e){
            e.getMessage();
        }
    }

    public void Debug_OnClick(View view){
        bDebug.setText(sDebug);
    }

    public static void setButton(final int i, String str) {
        Thread tSetButton = new Thread (new Runnable() {
            @Override
            public void run() {
                if (str.equals("VISIBLE"))
                    bPos.get(i).setVisibility(View.VISIBLE);
                else
                    bPos.get(i).setVisibility(View.INVISIBLE);
            }
        });
        mHandler.post(tSetButton);
    }

    public static void setStartButton(String str) {
        Thread tSetStartButton = new Thread(new Runnable() {
            @Override
            public void run() {
                if (str.equals("VISIBLE"))
                    bRunGame.setVisibility(View.VISIBLE);
                else
                    bRunGame.setVisibility(View.INVISIBLE);
            }
        });
        mHandler.post(tSetStartButton);
    }

    public static void seteLevel(int i) {
        Thread tSetLevel = new Thread(new Runnable() {
            @Override
            public void run() {
                eLevel.setText(String.valueOf(i));
            }
        });
        mHandler.post(tSetLevel);
    }

    public static void seteTime(String s) {
        Thread tSetTime = new Thread(new Runnable() {
            @Override
            public void run() {
                tTime.setText(String.valueOf(s));
            }
        });
        mHandler.post(tSetTime);
    }


    public static CountDownTimer cdt = new CountDownTimer(GameMode.iCDT*1000, 1000) {

        public void onTick(long millisUntilFinished) {
            seteTime(String.valueOf(millisUntilFinished / 1000));
            gamemode.iTime = (int)millisUntilFinished;
        }

        public void onFinish() {
            gamemode.iTime = 0;
            seteTime("0");
        }
    };
}