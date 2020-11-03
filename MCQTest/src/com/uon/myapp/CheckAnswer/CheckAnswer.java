package com.uon.myapp.CheckAnswer;

import com.uon.myapp.Displays.GameContainer;
import com.uon.myapp.MyApplication;

public class CheckAnswer {

    // This class will check to see whether the answer pressed from one of the four button
    // is either correct or incorrect.

    int iFinalAnswer;
    int iDifficulty;
    int iMode;
    String sTimer;
    Boolean bRandomModeSel;

    // Creating a new MyApp object to run the methods: Verdict and playGame
    MyApplication MyApp = new MyApplication();

    public CheckAnswer(int iFinalAnswer, int iDifficulty, int iMode, String sTimer, Boolean bRandomModeSel){

        this.iFinalAnswer = iFinalAnswer;
        this.iDifficulty = iDifficulty;
        this.iMode = iMode;
        this.sTimer = sTimer;
        this.bRandomModeSel = bRandomModeSel;
    }

    // This method will check the answer based on the button selected
    // and determine if that number stored in the button is correct or not
    public void checkAns(String sChkAns) {
        // Converting the string into an integer so that the value
        // can be checked with the right answer
        int iChkAns = Integer.parseInt(sChkAns);

        if(iFinalAnswer == iChkAns) {
            // If the final answer is equal to the answer selected
            // then the verdict to changing the colour will be true,
            // which will make the background green
            MyApp.Verdict(true);

            // Since the player answered correctly, the score is incremented by 1
            GameContainer.setScore(1);

            // The verdict shown for that question will be set to show "Correct"
            GameContainer.SetVerdict("Correct");
        }
        else {
            // If the selected answer does not equal to the final answer,
            // then the verdict to changing the colour will be false,
            // which will make the background red
            MyApp.Verdict(false);

            // Since the play answered incorrectly, the score will not be incremented
            // and the verdict shown for that question will be set show "Incorrect"
            GameContainer.SetVerdict("Incorrect");
        }

        // Regardless of the question's verdict (correct or incorrect),
        // the program will proceed to the next question with the difficulty, mode, and time
        // remaining intact
        MyApp.playGame(iDifficulty, iMode, sTimer, bRandomModeSel);

    } // end checkAns

} // end class CheckAnswer
