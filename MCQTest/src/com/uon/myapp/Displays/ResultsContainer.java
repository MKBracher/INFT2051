package com.uon.myapp.Displays;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.Layout;
import com.uon.myapp.Displays.Setup.SetupSkipLimit;
import com.uon.myapp.Displays.Setup.SetupTimer;
import com.uon.myapp.MyApplication;

public class ResultsContainer extends Container {

    // This container will display a summary of the results by including:
    // score achieved, the mode, and difficulty.
    // The results screen will be shown when the time is up

    // final string to allocate the final score
    final private String sFinalScore;

    // final strings to allocate the specified mode and difficulty used
    private final String sSelDiff;
    private final String sSelMode;

    // This string will be used to reset the timer to its maximum value
    private String sResetTimer;

    // This integer will reset the number of skips
    private int iResetSkipLimit;

    // These final integers will collect the indexes of the mode and difficulty
    // used from the game played
    final private int iSelDiffIndex, iSelModeIndex;

    // This boolean will determine if the random mode was used during the game
    private final Boolean bRandModeSel;

    public ResultsContainer(Layout layout, String sFinalScore, int iSelDiffIndex, int iSelModeIndex, String sSelDiff, String sSelMode, Boolean bRandModeSel){
        super(layout);
        this.setScrollableY(false);

        this.bRandModeSel = bRandModeSel;

        this.sFinalScore = sFinalScore;
        this.iSelDiffIndex = iSelDiffIndex;
        this.iSelModeIndex = iSelModeIndex;
        this.sSelDiff = sSelDiff;
        this.sSelMode = sSelMode;

        this.init();

    } // end ResultsContainer

    private void init(){

        // Displaying the difficulty that was used
        Label lblSummaryDiff = new Label("Difficulty: " + sSelDiff);

        // Displaying the mode that was used
        Label lblSummaryMode = new Label("Mode: " + sSelMode);

        // Displaying the final score
        Label lblSummaryScore = new Label("Your final score is: " + sFinalScore);

        // Displaying the buttons to either retry the game with the existing settings (i.e. difficulty and mode),
        // and return back to the lobby to play a game with different settings
        Button btnRetry = new Button("Retry");
        Button btnLobby = new Button("Lobby");

        // Declaring and instantiating the s
        SetupTimer setupTimer = new SetupTimer();
        MyApplication myApp = new MyApplication();
        SetupSkipLimit setupSkipLimit = new SetupSkipLimit();

        // Setting the high score
        int iFinalScore = Integer.parseInt(sFinalScore);
        int modeDifficulty = myApp.findModeDifficulty(iSelDiffIndex, iSelModeIndex, bRandModeSel);

        // Updating the high score based on the mode and difficulty
        myApp.setHighScores(iFinalScore, modeDifficulty);

        // If the "Retry" button is pressed, the game's timer will be reset back to its maximum value
        // and the game will be replayed with the settings from the summary
        btnRetry.addActionListener((e) -> {
            sResetTimer = setupTimer.GetTimer();
            iResetSkipLimit = setupSkipLimit.GetSkipLimit();
            myApp.playGame(iSelDiffIndex, iSelModeIndex, sResetTimer, bRandModeSel, iResetSkipLimit);
        });

        // This button will go back the game's lobby
        btnLobby.addActionListener((e) -> myApp.Lobby());

        // Adding the components
        this.addAll(
                lblSummaryDiff,
                lblSummaryMode,
                lblSummaryScore,
                btnRetry,
                btnLobby
        );

    } // end init

} // end class ResultsContainer
