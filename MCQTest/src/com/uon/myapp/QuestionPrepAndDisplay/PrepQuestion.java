package com.uon.myapp.QuestionPrepAndDisplay;

import java.util.Random;

public class PrepQuestion {
    // This class will prepare the question based on the mode and difficulty selected or randomly determined

    // This random object will be used to display different numbers for each
    // equation
    Random randNumGen = new Random();

    // Allocating the iterator (for loops)
    int i, iSetQuant, iSetOp, iDifficulty;

    // These arrays will be used to store the random numbers
    int[] iRandNum, iNumValue;

    // These strings will prepare and finalise the question showing the equation
    String sQuestion, sFinalQuestion;

    //=============================================
    // Reference C4: externally sourced code
    // Purpose: to obtain Multiplication and division symbols found in the Windows character map (not online)
    // Date: 7/11/2020
    // Source: Windows Character Map
    // Author: Unknown
    // url: Unknown
    // Adaptation required: Acquiring the following symbols: "×" and "÷"
    //=============================================

    // String array to store the four main operations
    String[] sOperation = {" + ", " - ", " × ", " ÷ "};

    // =============================================
    // end Reference C4
    // =============================================

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

            // The difficulty will determine how big the numbers should be
            iRandNum[i] = checkDifficulty();
            // Each number will have a number between 1 and 12
            // e.g. 1 is inclusive, 12
            iNumValue[i] = 1 + randNumGen.nextInt(iRandNum[i]);
        }

        return iNumValue;
    } // end collectRandNums

    // comments to be filled for matt (remove this line when finished)
    public int checkDifficulty() {
        // This method will check with the difficulty to determine the
        // maxim
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
    } //end checkDifficulty

    // This method will create the question based on the collected numbers
    public String displayQuestion(int[] iNumValue){

        // This string be used to store the specified operation symbol
        String sSelOperation;

        // Depending on the value specified by iSetOp,
        // the value will determine which operation to be used
        // when the question is displayed
        if(iSetOp == 0) sSelOperation = sOperation[0];
        else if(iSetOp == 1) sSelOperation = sOperation[1];
        else if(iSetOp == 2) sSelOperation = sOperation[2];
        else sSelOperation = sOperation[3];

        // If dividing or subtracting, make sure that the highest number resides in iNumValue[0]
        if (iSetOp == 1 || iSetOp == 3) {
            if (iNumValue[1] > iNumValue[0]) {
                int iSwapper = iNumValue[1];
                iNumValue[1] = iNumValue[0];
                iNumValue[0] = iSwapper;
            }
        }

        // If the mode/operation division is selected, then one of the numbers
        // for the question will need to be setup in advance via multiplication
        if (iSetOp == 3) iNumValue[0] = iNumValue[0] * iNumValue[1];

        // Compiling all of the numbers (separated by a specified operation)
        // together in a single string
        for (i = 0; i < iNumValue.length; i++) sQuestion += iNumValue[i] + sSelOperation;

        // Removing the unwanted operation symbol at the end of the string
        sQuestion = sQuestion.substring(sOperation.length, sQuestion.length() - sSelOperation.length());

        // Preparing the final question
        sFinalQuestion = "What is " + sQuestion + "?";

        // Returning the question in order for the question to be displayed
        return sFinalQuestion;
    } // end displayQuestion

} // end class PrepQuestion
