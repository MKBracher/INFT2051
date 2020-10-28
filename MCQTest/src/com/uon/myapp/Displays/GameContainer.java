package com.uon.myapp.Displays;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.Layout;
import com.uon.myapp.CheckAnswer.CheckAnswer;
import com.uon.myapp.QuestionPrepAndDisplay.PrepAnswers;
import com.uon.myapp.QuestionPrepAndDisplay.PrepQuestion;

public class GameContainer extends Container {

    // This Container will display the a mathematics question.
    // The question is multiple-choice, where it contains 4 options (1 right, 3 wrong).
    // There is also a time limit that users will need to pay attention for.
    // Whenever the user wants to skip a question, the user can shake or tilt the device

    // These integers store the indexes/IDs for the difficulty and mode chosen in the lobby
    final private int iSelDiff, iSelMode;

    // How many numbers will be used for the equation
    // Depending on the difficulty and/or mode, the quantity of numbers may fluctuate between 2 and 3
    private int iQuant;

    // Used for iterator loops, including for loops
    private int i;

    // To keep track of score
    private static int iScore;

    private Boolean bActivateTimer;

    private String sMin, sSec;

    // This string displays the max time for the countdown
    private String sTimer;

    private Display dispTimer;

    private Label lblTimer;

    public GameContainer(Layout layout, int iSelDiff, int iSelMode, String sTimer){
        super(layout);
        this.setScrollableY(false);

        this.iSelDiff = iSelDiff;
        this.iSelMode = iSelMode;

        this.sTimer = sTimer;

        this.init();
        this.Countdown();
    }

    private void init(){

        iQuant = 2;

        // To prepare the question with how many numbers will be used
        PrepQuestion prepQ = new PrepQuestion(iQuant, iSelMode, iSelDiff);
        int[] iRandNumbers = prepQ.collectRandNums();
        String sDispQuestion = prepQ.displayQuestion(iRandNumbers);

        // Displaying the question
        this.add(new Label(sDispQuestion));

        // Displaying Score
        this.add(new Label("Score: " + (iScore)));

        String sNewTimer = sTimer;
        lblTimer = new Label(sNewTimer);
        this.add(lblTimer);

        // Calculating the equation, creating the incorrect answers from the right answer,
        // and shuffling the answers to make sure that the right answer is in a different place
        PrepAnswers prepA = new PrepAnswers(iQuant, iSelMode);
        int iRightAnswer = prepA.equateRightAnswer(iRandNumbers);
        int[] iWrongAnswers = prepA.createWrongAnswers(iRightAnswer);
        int[] iShuffledAns = prepA.ShuffleAnswers(iWrongAnswers);

        // Allocating the shuffled answers into the four option buttons
        String[] sOpt = prepA.dispAnsSel(iShuffledAns);
        Button[] btnOpt = new Button[sOpt.length];

        // For loop to apply the buttons for each question
        for (i = 0; i < btnOpt.length; i++){
            btnOpt[i] = new Button(sOpt[i]);
            this.add(btnOpt[i]);
        }

        // These lambda action listener event handlers
        // will be used to check if the answer selected
        // is either the right answer or the wrong answer
        CheckAnswer chkAnswer = new CheckAnswer(iRightAnswer, iSelDiff, iSelMode, sCurrent);

        // The number from the button's text will need to be collected
        // to determine whether the answer selected is correct or not.
        // The process of checking the answer is the same for the next three event handlers

        btnOpt[0].addActionListener((e) -> { // Button A
            String sOptAns = btnOpt[0].getText();
            chkAnswer.checkAns(sOptAns);
        });

        btnOpt[1].addActionListener((e) -> { // Button B
            String sOptAns = btnOpt[1].getText();
            chkAnswer.checkAns(sOptAns);
        });

        btnOpt[2].addActionListener((e) -> { // Button C
            String sOptAns = btnOpt[2].getText();
            chkAnswer.checkAns(sOptAns);
        });

        btnOpt[3].addActionListener((e) -> { // Button D
            String sOptAns = btnOpt[3].getText();
            chkAnswer.checkAns(sOptAns);
        });

    } // end init

    // This method will update the score if the user selects the correct answer
    public static void setScore(int score){
        iScore += score;
    }

    private static String sCurrent;

    private static String remaining;

    public void Countdown(){
        // Get the wait length specified by the user
        int waitLength;

        if(sTimer == "60"){
            waitLength = Integer.parseInt(lblTimer.getText());
        }
        else {
            waitLength = Integer.parseInt(sCurrent);
        }

        // Start on a background thread
        new Thread(() -> {
            // Count for n seconds
            // This loop will countdown the timer until it reaches zero,
            // When it has reached zero, the user can pick a new number and restart the countdown
            for (int i = waitLength; i >= 0; i--)
            {
                remaining = "" + i;
                sCurrent = remaining;
                lblTimer.setText(remaining);

                // The timer will go down one unit for one second (1000 millis)
                try { Thread.sleep(1000); } catch (InterruptedException ex) { }
            }

        }).start();
    }

} // end class GameContainer
