package com.example.whackamole;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class GameMode {
    static String sTag = "GameMode";

    private static int iMaxLattice = 10;
    private static int iMinLattice = 3;
    private int iProcess = 0;
    private static int iProcessCount = 10;
    private int iThreshold = 30;
    private int iRound = 10;
    public static int iCDT = 60;
    public int iTime = 0;

    public static int iVitality_Max = 3;
    public static int iVitality = iVitality_Max;

    Thread model_Normal = new Thread(new Runnable() {
        @Override
        public void run() {
            ArrayList<Integer> number = new ArrayList<Integer>();
            try {
                iTime = iCDT;
                do {
//                    Log.i(sTag,"iProcess : " + iProcess);
                    Random ran = new Random();
                    iProcess++;
                    //show
                    if (iProcess == 1) {
                        int iShowNumber = ran.nextInt(iMaxLattice);

                        if (iShowNumber < iMinLattice) iShowNumber = iMinLattice;
                        Log.i(sTag,"iShowNumber " + iShowNumber);

                        for (int i = 0; i < iShowNumber; i++) {
                            int k = ran.nextInt(GamePage.bPos.size());
                            Log.i(sTag,"ran num " + k);

                            number.add(k);
                            GamePage.setButton(k,"VISIBLE");
                        }
                    } else if (iProcess == iProcessCount) {
                        for (int i : number) {
                            GamePage.setButton(i,"INVISIBLE");
                        }
                        iProcess = 0;
                        number.clear();
                    }
                    Thread.sleep(500/GamePage.iLevel);
                } while (GamePage.iCount <= iThreshold && iTime > 0);
            } catch (Exception e) {
                e.getMessage();
            } finally {
                Log.i(sTag,"Stop : " + GamePage.iCount + ", " + iTime);
                GamePage.cdt.cancel();
                for (int i : number) {
                    GamePage.setButton(i,"INVISIBLE");
                }

                if(GamePage.iCount >= iThreshold) {
                    GamePage.iLevel++;
                    GamePage.seteLevel(GamePage.iLevel);
                } else{
                    GamePage.tLife.setText(--iVitality);
                }
                GamePage.setStartButton("VISIBLE");
            }
        }
    });
}
