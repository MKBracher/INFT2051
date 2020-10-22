package com.uon.myapp.QuestionPrepAndDisplay;

import java.util.Random;

public class PrepQuestion {
    // This is this random object that will show different numbers for each
    // equation
    Random randNumGen = new Random();

    int i, iSetQuant, iSetOp, iDifficulty;

    // These arrays will be used to store the random numbers
    int[] iRandNum, iNumValue;

    // These strings will prepare and finalise the question showing the equation
    String sQuestion, sFinalQuestion;

    // String array to store the four main operations
    // Multiplication and division symbols found in the Windows character map (not online)
    String[] sOperation = {" + ", " - ", " × ", " ÷ "};

    public PrepQuestion(int iGetQuant, int iGetOp, int iGetDifficulty){
        iSetQuant = iGetQuant;
        iSetOp = iGetOp;
        iDifficulty = iGetDifficulty;
        iRandNum = new int[iSetQuant];
        iNumValue = new int[iSetQuant];
    }

    // This method will collect the random numbers
    // The quantity of numbers will be determined by iGetQuant
    public int[] collectRandNums(){

        for (i = 0; i < iRandNum.length; i++){

            iRandNum[i] = checkDifficulty();
            // Each number will have a number between 1 and 12
            // e.g. 1 is inclusive, 12
            iNumValue[i] = 1 + randNumGen.nextInt(iRandNum[i]);
        }


        return iNumValue;
    } // end collectRandNums

    //Choose between difficulties
    public int checkDifficulty() {
        int iNumber = 0;

        if (iSetOp == 2 || iSetOp == 3) {
            switch(iDifficulty){
                case 0: iNumber = 12;
                    break;
                case 1: iNumber = 15;
                    break;
                case 2: iNumber = 20;
                    break;
            }
        }
        else {
            switch(iDifficulty){
                case 0: iNumber = 100;
                break;
                case 1: iNumber = 250;
                break;
                case 2: iNumber = 500;
                break;
            }

        }
        return iNumber;
    }//end checkDifficulty

    // This method will create the question based on the collected numbers
    public String displayQuestion(int[] iNumValue){
        String sSelOperation;

        // Depending on the value specified by iSetOp,
        // the value will determine which operation to be used
        // when the question is displayed
        if(iSetOp == 0) sSelOperation = sOperation[0];
        else if(iSetOp == 1) sSelOperation = sOperation[1];
        else if(iSetOp == 2) sSelOperation = sOperation[2];
        else sSelOperation = sOperation[3];

        //if dividing or subtracting, make sure the the 2nd number is divisible by the first
        if (iSetOp == 1 || iSetOp == 3) {
            if (iNumValue[1] > iNumValue[0]) {
                int iSwapper = iNumValue[1];
                iNumValue[1] = iNumValue[0];
                iNumValue[0] = iSwapper;
            }
        }

        if (iSetOp == 3){
            iNumValue[0] = iNumValue[0] * iNumValue[1];
        }

        // Compiling all of the numbers (separated by a specified operation)
        // together in a single string
        for (i = 0; i < iNumValue.length; i++){
            sQuestion += iNumValue[i] + sSelOperation;
        }

        // Removing the unwanted operation symbol at the end of the string
        sQuestion = sQuestion.substring(sOperation.length, sQuestion.length() - sSelOperation.length());

        // Preparing the final question
        sFinalQuestion = "What is " + sQuestion + "?";
        return sFinalQuestion;
    } // end displayQuestion
}
