package com.uon.myapp;

import com.codename1.ui.Dialog;

public class CheckAnswer {

    int iFinalAnswer;

    public CheckAnswer(int iFinalAnswer){
        this.iFinalAnswer = iFinalAnswer;
    }

    // This method will check the answer based on the button selected
    // and determine if that number stored in the button is correct or not
    public void checkAns(String sChkAns){
        // Converting the string into an integer so that the value
        // can be checked with the right answer
        int iChkAns = Integer.parseInt(sChkAns);

        if(iFinalAnswer == iChkAns)
            Dialog.show(null, "That is correct!", "OK", null);
        else
            Dialog.show(null, "That is incorrect!", "OK", null);

        // Going to a new question regardless if the current question was correct or not
        MyApplication MyApp = new MyApplication();
        MyApp.startUp();
    } // end checkAns
}
