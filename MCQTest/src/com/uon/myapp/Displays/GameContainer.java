package com.uon.myapp.Displays;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.Layout;
import com.uon.myapp.CheckAnswer.CheckAnswer;
import com.uon.myapp.Displays.Setup.SetupTimer;
import com.uon.myapp.MyApplication;
import com.uon.myapp.QuestionPrepAndDisplay.PrepAnswers;
import com.uon.myapp.QuestionPrepAndDisplay.PrepQuestion;
import com.codename1.sensors.*;
import java.util.Random;

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

    // This string will contain the verdict to show whether the question is correct or not
    private static String sVerdict;

    // This string displays the max time for the countdown
    private String sTimer;

    // These labels show the timer, score, and question
    private Label lblTimer, lblScore, lblQuestion;

    // This label will hold the information to determine if the answer selected is
    // either correct or incorrect
    private Label lblVerdict;

    // This string holds the time remaining throughout the quiz
    private static String remaining;

    // The long and float variables are used to determine the orientation of
    // the phone during real-time via the phone's accelerometer
    private float fXAxis;

    // This label will alert the user if the device does not use accelerometer
    // and warn that the shake functionality to skip questions will not work
    private Label lblSkipLimit;

    // To prevent misuse of the shaking feature, as well as to prevent the program from crashing
    // this integer will store the max number of shakes to skip a question for each game
    private int iCurrentSkips;

    // This integer will be used to check the x-axis angle of the accelerometer
    // when trying to shake the device to skip a question
    private int iXTilt;

    // This boolean is used to check if the Random mode is selected.
    // If this mode is selected, each question will be based on a randomly selected mode
    // from the following operations (addition, subtraction, multiplication, and division)
    private final Boolean bRandomModeSel;

    // This Random object will be used to randomly select a mode (operation) if the random
    // mode is selected
    private final Random randMode = new Random();

    private int modeDifficulty;


    MyApplication myApp = new MyApplication();

    public GameContainer(Layout layout, int iSelDiff, int iSelMode, String sTimer, Boolean bRandomModeSel, int iCurrentSkips){
        super(layout);
        this.setScrollableY(false);

        this.bRandomModeSel = bRandomModeSel;

        this.iSelDiff = iSelDiff;

        // If random mode were chosen the modes (operations) 0 to 3 will randomised for each question
        // until the timer runs out
        if(this.bRandomModeSel) this.iSelMode = randMode.nextInt(myApp.sMode.length - 1);
        else this.iSelMode = iSelMode;

        this.sTimer = sTimer;

        this.iCurrentSkips = iCurrentSkips;

        this.init();
        this.initAccelerometer(true);
        this.Countdown();
    }

    private void init(){

        // This number sets up the quantity (how many) of numbers used
        // for each equation
        iQuant = 2;

        // To prepare the question with how many numbers will be used
        PrepQuestion prepQ = new PrepQuestion(iQuant, iSelMode, iSelDiff);
        int[] iRandNumbers = prepQ.collectRandNums();
        String sDispQuestion = prepQ.displayQuestion(iRandNumbers);

        // Displaying the question
        lblQuestion = new Label(sDispQuestion);

        // Displaying Score
        lblScore = new Label("Score: " + (iScore));

        // Displaying the counting-down timer
        lblTimer = new Label(sTimer);

        // Displaying the verdict
        lblVerdict = new Label(sVerdict);

        // Displays how many skips are remaining
        // - OR -
        // Displays the no accelerometer warning if there is no accelerometer
        // or the accelerometer is unsupported
        lblSkipLimit = new Label("Skips left: " + (iCurrentSkips));

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
        for (i = 0; i < btnOpt.length; i++) btnOpt[i] = new Button(sOpt[i]);

        // These lambda action listener event handlers
        // will be used to check if the answer selected
        // is either the right answer or the wrong answer
        CheckAnswer chkAnswer = new CheckAnswer(iRightAnswer, iSelDiff, iSelMode, remaining, bRandomModeSel, iCurrentSkips);

        Button btnExit = new Button("Exit Game");

        // The number from the button's text will need to be collected
        // to determine whether the answer selected is correct or not.
        // The process of checking the answer is the same for the next three event handlers
        // btnOpt from indexes 0 to 3
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

        btnExit.addActionListener(this::ExitGame);

        this.addAll(lblQuestion, lblScore, lblTimer);

        // This for loop will add all four buttons that display the four answers
        for(i = 0; i < btnOpt.length; i++) this.add(btnOpt[i]);

        this.addAll(lblVerdict, lblSkipLimit, btnExit);

    } // end init

    public void initAccelerometer(Boolean bAccelActiveCheck){
        // This method will initiate the accelerometer's processes.
        // The aim of the accelerometer is to determine if the phone has been tilted or shaken
        // If the phone (or other suitable device) is shaken, the player can skip the question used

        // Acquire the sensor manager to use the device's accelerometer.
        SensorsManager sensorsManager = SensorsManager.getSensorsManager(SensorsManager.TYPE_ACCELEROMETER);

        // Integer used to determine how many degrees must be made to skip a question
        // by shaking the phone on its x-axis
        iXTilt = 15;

        // If null is returned, the accelerometer is non-existent or unsupported for this device.
        if(sensorsManager != null){

            sensorsManager.registerListener(new SensorListener() {
                @Override
                public void onSensorChanged(long lGetTimeStamp, float fGetXAxis, float fGetYAxis, float fGetZAxis) {

                    // The shaking function depends on the readings of the accelerometer's x-axis
                    fXAxis = fGetXAxis;

                    // To prevent the accelerometer being used outside of the game a Boolean must be used
                    // to check if the program can be able to use the nested if statement
                    if(bAccelActiveCheck && iCurrentSkips > 0) {
                        if (fXAxis <= -iXTilt || fXAxis >= iXTilt) {
                            myApp.SkipQuestionBGColour();
                            SetVerdict("Skipped");
                            // When skip is made, the number of skips remaining will be deducted by one
                            iCurrentSkips--;
                            myApp.playGame(iSelDiff, iSelMode, remaining, bRandomModeSel, iCurrentSkips);
                        }
                    }

                } // end onSensorChanged

            }); // sensorsManager.register listener

        }  // end if
        else {
            // If the device has no accelerometer, or the accelerometer is unsupported
            // alert the user of what will happen
            lblSkipLimit.setText(
                    "This device has an unsupported or has no accelerometer."
                    + "\r\n The game will still be playable but you will be unable to shake the device when "
                    + "skipping questions."
            );

        } // end else

    } // end initAccelerometer

    // This method will update the score if the user selects the correct answer
    public static void setScore(int score){ iScore += score; }



    // This method will setup the verdict for the answer.
    // The verdict will show if the answer to the previous question is correct or incorrect
    // or if the previous question is skipped
    public static void SetVerdict(String sGetVerdict){ sVerdict = sGetVerdict; }



    public void Countdown(){
        // Get the wait length specified by the user
        int waitLength;

        // Collecting the max time limit.
        SetupTimer setupTimer = new SetupTimer();
        String sMaxTime = setupTimer.GetTimer();

        // If sTimer contains the same value as sMaxTime
        // then the max time will be collected
        if(sTimer == sMaxTime) waitLength = Integer.parseInt(sMaxTime);

        // If sTimer is not equal to sMaxTime
        // Then the remaining time will be collected instead
        else waitLength = Integer.parseInt(remaining);

        // Starting a thread to initiate the countdown
        new Thread(() -> {

            // The timer will continue to count down until it reaches zero
            for (int i = waitLength; i >= 0; i--)
            {
                // The integer i will be converted to a string for the
                // remaining string
                remaining = String.valueOf(i);

                // Displaying the remaining time time
                lblTimer.setText(remaining);

                // If the timer reads zero, the app will redirect to the results screen
                if (waitLength == 0){

                    // To prevent the accelerometer from being used outside the game,
                    // and to avoid the app from crashing, the initAccelerometer method
                    // must be re-run with a false argument to stop the skipping algorithm
                    initAccelerometer(false);

                    myApp.Results(String.valueOf(iScore), iSelDiff, iSelMode, bRandomModeSel);
                    // The score will be reset to zero, to prepare for the next game
                    iScore = 0;
                }

                // The timer will go down one unit for one second (1000 milliseconds)
                try { Thread.sleep(1000); } catch (InterruptedException exception) { }

            } // end for loop

        }).start(); // Starts the thread

    } // end Countdown




    private void ExitGame(ActionEvent e) {
        // If the player wants to exit mid-game, a dialog box will confirm if the player wants to go back
        // to the lobby

        // When the dialog box is open, the shake function via the accelerometer will be temporarily deactivated
        initAccelerometer(false);
        if (Dialog.show("Exit Game?", "Are you sure that you want to exit?", "Yes", "No")) {
            // If yes, then the timer and score will be set/reset to zero, and the app will go back the the lobby
            sTimer = "0";
            iScore = 0;
            myApp.Lobby();
        } else {
            // The accelerometer will be re-activated when no is selected, thus resuming the game.
            initAccelerometer(true);
        }
    } // end ExitGame

} // end class GameContainer