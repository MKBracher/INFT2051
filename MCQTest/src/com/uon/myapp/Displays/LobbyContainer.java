package com.uon.myapp.Displays;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.spinner.Picker;
import com.uon.myapp.Displays.Setup.SetupSkipLimit;
import com.uon.myapp.Displays.Setup.SetupTimer;
import com.uon.myapp.MyApplication;

public class LobbyContainer extends Container {

    // This container will display the possible options before starting the quiz
    // The user can play different sorts of quizzes through a variety of difficulties and modes

    // Pickers for the selecting the difficulty and mode
    private Picker pickerDifficulty;
    private Picker pickerMode;

    // Buttons to either play the game with the settings selected (difficulty, mode)
    // or return to the main menu.
    private Button btnPlayGame;

    // These string arrays contain the names of the difficulties and modes
    final private String[] sDifficulty;
    final private String[] sMode;

    // This string will be used to setup the maximum time remaining
    private String sTimer;

    // Used to allocate the maximum number of skips remaining
    private int iRemainingSkips;

    // Used to determine if the Random mode is selected or not
    private Boolean bRandomMode = true;

    public int test = 0;

    // The game will be determined by the difficulty and mode
    // The default values set
    int iSelDiff = 1;
    int iSelMode = 4;

    // This is used to hold a number that represents a combination of game mode and difficulty
    // In order Addition, Subtraction, Multiplication
    // Division, Random. Easy, Medium, Hard - Eg. Easy Subtraction is Index 1,
    // Hard subtraction is Index 12
    public int iModeDifficulty = 0;

    public LobbyContainer(Layout layout, String[] sDifficulty, String[] sMode, int[] highScores){
        super(layout);
        this.setScrollableY(false);

        this.sDifficulty = sDifficulty;
        this.sMode = sMode;

        this.init();
    }

    private void init(){

        // Selecting Difficulty between: easy, normal (default), and hard
        pickerDifficulty = new Picker();
        pickerDifficulty.setType(Display.PICKER_TYPE_STRINGS);
        pickerDifficulty.setStrings(sDifficulty);
        pickerDifficulty.setSelectedStringIndex(1);

        // Selecting Mode between: Addition (default), subtraction, multiplication, and division
        pickerMode = new Picker();
        pickerMode.setType(Display.PICKER_TYPE_STRINGS);
        pickerMode.setStrings(sMode);
        pickerMode.setSelectedStringIndex(4);

        MyApplication myApp = new MyApplication();
        SetupTimer setupTimer = new SetupTimer();
        SetupSkipLimit setupSkipLimit = new SetupSkipLimit();

        // Setting up high score label
        Label lblHighScore = new Label();
        lblHighScore.setText("High Score: " + myApp.getHighScores(9));

        //=======================================================================
        //Reference A2 : Externally sourced code
        //Purpose : Allow the UI to be updated when a picker gets changed
        //Date: 07 November 2020
        //Source: Stackoverflow
        //URL: https://stackoverflow.com/questions/50461901/how-to-add-a-selection-change-listener-to-a-picker-in-codename-one
        //Adaption required: Inserted code into event handler
        //==========================================================================


        pickerMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                iSelMode = pickerMode.getSelectedStringIndex();
                if (iSelMode != 4){
                    bRandomMode = false;
                }
                else{
                    bRandomMode = true;
                }
                iModeDifficulty = myApp.findModeDifficulty(iSelDiff, iSelMode, bRandomMode);
                lblHighScore.setText("High Score: " + myApp.getHighScores(iModeDifficulty));

            }
        });


        pickerDifficulty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                iSelDiff = pickerDifficulty.getSelectedStringIndex();
                iModeDifficulty = myApp.findModeDifficulty(iSelDiff, iSelMode, bRandomMode);
                lblHighScore.setText("High Score: " + myApp.getHighScores(iModeDifficulty));

            }
        });

        //========================================================
        //End reference A2
        //=======================================================

        // Displaying the text on the buttons
        btnPlayGame = new Button("Play Game");

        // When pressed (or clicked), the app will display the screen where the user answers the maths questions
        btnPlayGame.addActionListener((e) -> {

            // Checking if the Random mode is used or not
            if (iSelMode == (sMode.length - 1)) bRandomMode = true;
            else bRandomMode = false;

            // The timer will be set to its maximum value
            sTimer = setupTimer.GetTimer();

            // This will reset the maximum number of remaining skips
            iRemainingSkips = setupSkipLimit.GetSkipLimit();

            // Since the game has started, and the person has not answered a question yet,
            // the verdict displayed will be empty
            GameContainer.SetVerdict("");

            // Once the timer, mode and difficulty have the been prepared
            // the game start and can be played until time runs out
            myApp.playGame(iSelDiff, iSelMode, sTimer, bRandomMode, iRemainingSkips);
        }); // end btnPlayGame

        // Applying all elements for this container
        this.addAll(
                new Label("Difficulty"),
                pickerDifficulty,
                new Label("Mode"),
                pickerMode,
                lblHighScore,
                btnPlayGame
        );

    } // end init

} // end class LobbyContainer
