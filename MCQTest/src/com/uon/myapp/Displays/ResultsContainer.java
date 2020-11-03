package com.uon.myapp.Displays;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.Layout;
import com.uon.myapp.Displays.Setup.SetupTimer;
import com.uon.myapp.MyApplication;

public class ResultsContainer extends Container {

    // This container will display a summary of the results by including:
    // score achieved, the mode, and difficulty.

    // The results screen will be shown when the time is up

    final private String sFinalScore;

    private final String sSelDiff;
    private final String sSelMode;

    private String sRefreshTimer;

    final private int iSelDiffIndex, iSelModeIndex;

    private Label lblSummaryDiff, lblSummaryMode, lblSummaryScore;

    private Button btnRetry, btnLobby;

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

    }

    private void init(){

        lblSummaryDiff = new Label("Difficulty: " + sSelDiff);

        lblSummaryMode = new Label("Mode: " + sSelMode);

        lblSummaryScore = new Label("Your final score is: " + sFinalScore);

        btnRetry = new Button("Retry");
        btnLobby = new Button("Lobby");

        MyApplication myApp = new MyApplication();
        SetupTimer setupTimer = new SetupTimer();

        btnRetry.addActionListener((e) -> {
            sRefreshTimer = setupTimer.GetTimer();
            myApp.playGame(iSelDiffIndex, iSelModeIndex, sRefreshTimer, bRandModeSel);
        });

        btnLobby.addActionListener((e) -> myApp.Lobby());

        this.addAll(
                lblSummaryDiff,
                lblSummaryMode,
                lblSummaryScore,
                btnRetry,
                btnLobby
        );

    }

}
