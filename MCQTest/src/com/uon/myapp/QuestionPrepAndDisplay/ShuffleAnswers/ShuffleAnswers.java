package com.uon.myapp.QuestionPrepAndDisplay.ShuffleAnswers;

import java.util.Random;

public class ShuffleAnswers {

    int i, j, iRandSelIndex;

    int[] iSetRawAnswers;

    Random randSect = new Random();

    public ShuffleAnswers(int[] iGetRawAnswers){
        iSetRawAnswers = iGetRawAnswers;
    }

    public int[] Shuffler(int[] iRawAnswers){
        // This method will randomly shuffle all answers, meaning that each question displayed
        // will most likely have the right and wrong answers in different places

        // This integer is used to swap answers from the caller to the callee
        int iSwap;

        // If the number from both the caller and callee are the same,
        // this integer (via 0 or 1) will determine if the number should be incremented by 1
        int iDecider;

        // This array is used to randomly select the answers that will be the caller and callee
        int[] iCall = new int[2];

        // This for loop will randomly shuffle the answers
        for(i = 0; i < (iRawAnswers.length ^ iCall.length); i++){
            // This for loop will collect the caller and callee numbers
            // The caller selected will need to
            for(j = 0; j < iCall.length; j++) iCall[j] = Randomiser(0);

            // To prevent the caller and callee have the same values,
            // either the caller or callee will be randomly decided to increment that value by 1
            // or go back to 0 if at the max value, which is 3.
            if (iCall[0] == iCall[1]){
                iDecider = Randomiser(iCall.length);
                if(iDecider == 0 || iDecider == 1){
                    if(iCall[iDecider] == iRawAnswers.length - 1) iCall[iDecider] = 0;
                    else iCall[iDecider]++;
                }
            }

            // Once a unique caller and callee has been chosen, they can now swap shuffle their values
            iSwap = iRawAnswers[iCall[0]];
            iRawAnswers[iCall[0]] = iRawAnswers[iCall[1]];
            iRawAnswers[iCall[1]] = iSwap;
        } // end for loop

        // Once finished, the shuffled answer array will be ready to be outputted
        return iRawAnswers;
    } // end Shuffler

    // This method will randomly select the caller and callee to shuffle with
    private int Randomiser(int iRange){
        iRandSelIndex = randSect.nextInt(iSetRawAnswers.length - iRange);
        return iRandSelIndex;
    } // end Randomiser

} // end class ShuffleAnswers