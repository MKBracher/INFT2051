package com.uon.myapp.QuestionPrepAndDisplay.ShuffleAnswers;

import java.util.Random;

public class ShuffleAnswers {

    // This class' function is to randomise or shuffle the right and wrong answers
    // to make sure that the correct and incorrect answers are in different positions
    // in the iSetRawAnswers array

    // Used for iterator loops, including for loops
    int i, j;

    // Used for randomly selecting an index number (from 0 to 3)
    // in the iSetRawAnswers Array
    int iRandSelIndex;

    // Integer array to store the un-shuffled answers
    int[] iSetRawAnswers;

    // Random object used to randomly select numbers 0-3 (0-1 for caller and callee)
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
            // The caller selected will need to exchange its number with the
            // randomly selected number from the callee
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

    private int Randomiser(int iRange){
        // This method will randomly select the caller and callee to shuffle with
        iRandSelIndex = randSect.nextInt(iSetRawAnswers.length - iRange);
        return iRandSelIndex;
    } // end Randomiser

} // end class ShuffleAnswers