package com.uon.myapp.CheckAnswer;

import com.codename1.ui.Dialog;
import com.uon.myapp.Displays.GameContainer;
import com.uon.myapp.MyApplication;

public class CheckAnswer {

    int iFinalAnswer;
    int iDifficulty;
    int iMode;
    String sTimer;

    public CheckAnswer(int iFinalAnswer, int iDifficulty, int iMode, String sTimer){

        this.iFinalAnswer = iFinalAnswer;
        this.iDifficulty = iDifficulty;
        this.iMode = iMode;
        this.sTimer = sTimer;
    }

    // This method will check the answer based on the button selected
    // and determine if that number stored in the button is correct or not
    public void checkAns(String sChkAns){
        // Converting the string into an integer so that the value
        // can be checked with the right answer
        int iChkAns = Integer.parseInt(sChkAns);

        // Going to a new question regardless if the current question was correct or not
        MyApplication MyApp = new MyApplication();

        if(iFinalAnswer == iChkAns) {
            Dialog.show(null, "That is correct!", "OK", null);
            GameContainer.setScore(1);
        }
        else {
            Dialog.show(null, "That is incorrect!", "OK", null);
        }

        MyApp.playGame(iDifficulty, iMode, sTimer);

    } // end checkAns
}
