package com.uon.myapp;

import java.util.Random;

public class PrepAnswers {

    // Integer for the right answer
    int i, iRightAns, iSetQuant, iSetOperation;

    int[] iAnswers = new int[4];

    MyApplication myApp;

    Random randToss = new Random();

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
            for (i = 0; i < iNumValue.length; i++){
                iRightAns += iNumValue[i];
            }
        }
        // Subtraction
        else if(iSetOperation == 1){
            if(iSetQuant == 2)
                iRightAns = iNumValue[0] - iNumValue[1];
            else if(iSetQuant == 3)
                iRightAns = iNumValue[0] - iNumValue[1] - iNumValue[2];
            else if(iSetQuant == 4)
                iRightAns = iNumValue[0] - iNumValue[1] - iNumValue[2] - iNumValue[3];
        }
        // Multiplication
        else if(iSetOperation == 2){
            if(iSetQuant == 2)
                iRightAns = iNumValue[1] * iNumValue[0];
            else if(iSetQuant == 3)
                iRightAns = iNumValue[2] * iNumValue[1] * iNumValue[0];
            else if(iSetQuant == 4)
                iRightAns = iNumValue[3] * iNumValue[2] * iNumValue[1] * iNumValue[0];

        }
        // Division
        else{
            //Making sure the 2nd number is divisible by the first
            if (iNumValue[1] > iNumValue[0]){
                int iSwapper = iNumValue[1];
                iNumValue[1] = iNumValue[0];
                iNumValue[0] = iSwapper;
            }

            if(iSetQuant == 2)
                iRightAns = iNumValue[0] /iNumValue[1];
            else if(iSetQuant == 3)
                iRightAns = iNumValue[0] / iNumValue[1] / iNumValue[2];
            else if(iSetQuant == 4)
                iRightAns = iNumValue[0] / iNumValue[1] / iNumValue[2] / iNumValue[3];
        }

        return iRightAns;
    } // end equateRightAnswer

    // This method will allocate the wrong/incorrect answers to the
    // last three cells of the iAnswers array
    public int[] createWrongAnswers(int iRightAns){
        iAnswers[0] = iRightAns;
        iAnswers[1] = iRightAns + 2;
        iAnswers[2] = iRightAns - 2;
        iAnswers[3] = iRightAns - 1;

        return iAnswers;
    } // end createWrongAnswers

    // This method will shuffle the answers for each question
    // meaning that the right answer will be very likely be on a
    // different button
    public int[] ShuffleAnswers(int[] iAnswers){
        int iSwap;

        int[] iTosses = new int[iAnswers.length];

        for(i = 0; i < iTosses.length; i++){
            iTosses[i] = randToss.nextInt(2);
        }

        if(iTosses[0] == 1) {
            iSwap = iAnswers[0];
            iAnswers[0] = iAnswers[1];
            iAnswers[1] = iSwap;
        }

        if(iTosses[1] == 1) {
            iSwap = iAnswers[1];
            iAnswers[1] = iAnswers[2];
            iAnswers[2] = iSwap;
        }

        if(iTosses[2] == 1) {
            iSwap = iAnswers[2];
            iAnswers[2] = iAnswers[3];
            iAnswers[3] = iSwap;
        }

        if(iTosses[3] == 1) {
            iSwap = iAnswers[3];
            iAnswers[3] = iAnswers[0];
            iAnswers[0] = iSwap;
        }

        if(iTosses[0] == 1 && iTosses[2] == 1){
            iSwap = iAnswers[0];
            iAnswers[0] = iAnswers[2];
            iAnswers[2] = iSwap;
        }

        if(iTosses[1] == 1 && iTosses[3] == 1){
            iSwap = iAnswers[1];
            iAnswers[1] = iAnswers[3];
            iAnswers[3] = iSwap;
        }

        return iAnswers;
    } // end ShuffleAnswers

    // This method prepare the
    public String[] dispAnsSel(int[] iAnswers){
        // First, each element in the iAnswers Array will need to be converted to a string
        String[] sOptConv = new String[iAnswers.length];

        // Once converted, each element will be allocated to the array
        for(i = 0; i < iAnswers.length; i++){
            sOptConv[i] = String.valueOf(iAnswers[i]);
        }
        return sOptConv;
    } // end dispAnsSel

}
