package com.uon.myapp.QuestionPrepAndDisplay;

import com.uon.myapp.MyApplication;
import com.uon.myapp.QuestionPrepAndDisplay.ShuffleAnswers.ShuffleAnswers;

public class PrepAnswers {

    // Integer for the right answer
    int i, iRightAns, iSetQuant, iSetOperation;

    // This array will be used to contain the correct and incorrect
    // answers when displayed onto the buttons
    int[] iAnswers = new int[4];

    MyApplication myApp;

    public PrepAnswers(int iGetQuant, int iGetOperation){
        myApp = new MyApplication();
        iSetQuant = iGetQuant;
        iSetOperation = iGetOperation;
        iRightAns = 0;
    }

    public int equateRightAnswer(int[] iNumValue){
        // Whenever a new question is revealed, the value of the right
        // answer must be reset to zero

        // Addition
        if(iSetOperation == 0){
            for (i = 0; i < iNumValue.length; i++) iRightAns += iNumValue[i];
        }

        // Subtraction
        else if(iSetOperation == 1) iRightAns = iNumValue[0] - iNumValue[1];

        // Multiplication
        else if(iSetOperation == 2) iRightAns = iNumValue[1] * iNumValue[0];

        // Division
        else{
            // Making sure the 2nd number is divisible by the first
            if (iNumValue[1] > iNumValue[0]){
                int iSwapper = iNumValue[1];
                iNumValue[1] = iNumValue[0];
                iNumValue[0] = iSwapper;
            }

            iRightAns = iNumValue[0] / iNumValue[1];
        }

        return iRightAns;
    } // end equateRightAnswer

    // This method will allocate the wrong/incorrect answers to the
    // last three cells of the iAnswers array
    public int[] createWrongAnswers(int iRightAns){

        // Each wrong answer will vary based on the difficulty and/or mode selected
        iAnswers[0] = iRightAns;
        iAnswers[1] = iRightAns + 2;
        iAnswers[2] = iRightAns - 2;
        iAnswers[3] = iRightAns - 1;

        return iAnswers;
    } // end createWrongAnswers

    // This method will shuffle the answers for each question
    // meaning that the right answer not always be on the
    // same button
    public int[] ShuffleAnswers(int[] iAnswers){

        // The parameter array iAnswers will be used to shuffle or
        // randomised the answers
        ShuffleAnswers shuffleAnswers = new ShuffleAnswers(iAnswers);

        // Declaring the iFinalAnswers array
        int[] iFinalAnswers;

        // Placing the shuffled answers into the iFinalAnswers array
        iFinalAnswers = shuffleAnswers.Shuffler(iAnswers);

        return iFinalAnswers;
    } // end ShuffleAnswers

    // This method prepares the selection of answers that will be displayed
    public String[] dispAnsSel(int[] iAnswers){
        // First, each element in the iAnswers Array will need to be converted to a string
        String[] sOptConv = new String[iAnswers.length];

        // Once converted, each element will be allocated to the array
        for(i = 0; i < iAnswers.length; i++){
            sOptConv[i] = String.valueOf(iAnswers[i]);
        }
        return sOptConv;
    } // end dispAnsSel

} // class PrepAnswers
