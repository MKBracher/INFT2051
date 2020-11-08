package com.uon.myapp;

import static com.codename1.ui.CN.*;

import com.codename1.io.Storage;
import com.codename1.ui.*;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.ui.layouts.BoxLayout;
import com.uon.myapp.Displays.*;

import java.io.*;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose 
 * of building native mobile applications using Java.
 */
public class MyApplication implements Serializable {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);



        //write a high scores file to the storage object if one doesn't already exist
        Storage s = Storage.getInstance();
        if (readObjectFromStorage("highScores") == null){
            s.writeObject("highScores", highScores);
        }



        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if (err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });
    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }

        // Displaying the main form of the app.
        // The app will alternate between different containers, and display the contents of
        // the container on frmMainForm
        frmMainForm = new Form("", BoxLayout.y());

        // Displaying the app's lobby on the app's startup.
        // The lobby will allow the user to have an opportunity to change the game's settings
        // before starting the game
        Lobby();

        frmMainForm.show();
    }

    public void stop() {
        current = getCurrentForm();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = getCurrentForm();
        }
    }

    public void destroy() {
    }

    // The following strings and string arrays
    // Contain the words and information needed to navigate throughout the app

    public String sFrmTitle = "Quiz Whiz";

    public String[] sDifficulty = {"Easy", "Normal", "Hard"};

    public String[] sMode = {"Addition", "Subtraction", "Multiplication", "Division", "Random"};

    // Used to display the difficulty and mode selected on the results screen
    String sSummaryDiff, sSummaryMode;

    // This integer array will store the high scores
    private int[] highScores = new int[sDifficulty.length * sMode.length];



    // This app will be rendered in one form. This form will be able to access a series of containers,
    // but the form will be able to display one container at a time.
    static Form frmMainForm;

    


    //Reference A1: Externally Sourced Code
    //Purpose: Writing and reading an object from the codename one storage class
    //Date: 08 November 2020
    //Source: Sjhannah
    //URL: http://sjhannah.com/blog/2013/02/08/object-persistence-in-codename-one/
    //Author: Shannah
    //Adaption required: changed variables and inserted extra code


    public int getHighScores(int index){
        Storage s = Storage.getInstance();
        highScores = (int[]) s.readObject("highScores");
        return highScores[index];
    }

    public void setHighScores(int score, int index) {
        Storage s = Storage.getInstance();
        getHighScores(index);

        highScores[index] = score;

        s.writeObject("highScores", highScores);

    }
    //===================================================
    //End Reference A1
    //===================================================


    //This method is used to find a integer to represent
    //both the difficulty and mode selections
    public int findModeDifficulty(int selDiff, int selMode, boolean randomMode) {
        int difficulty = selDiff;
        int gameMode = selMode;
        int modeDifficulty = 0;
        if (randomMode) {
            gameMode = 4;
        }
        switch (gameMode) {
            case 0:
                //Addition
                switch (difficulty) {
                    case 0:
                        //easy
                        modeDifficulty = 0;
                        break;
                    case 1:
                        //medium
                        modeDifficulty = 5;
                        break;
                    case 2:
                        //hard
                        modeDifficulty = 10;
                        break;
                }
                break;
            case 1:
                //Subtraction
                switch (difficulty) {
                    case 0:
                        //easy
                        modeDifficulty = 1;

                        break;
                    case 1:
                        //medium
                        modeDifficulty = 6;
                        break;
                    case 2:
                        //hard
                        modeDifficulty = 11;
                        break;
                }
                break;

            case 2:
                //Multiplication
                switch (difficulty) {
                    case 0:
                        //easy
                        modeDifficulty = 2;

                        break;
                    case 1:
                        //medium
                        modeDifficulty = 7;
                        break;
                    case 2:
                        //hard
                        modeDifficulty = 12;
                        break;
                }
                break;
            case 3:
                //Division
                switch (difficulty) {
                    case 0:
                        //easy
                        modeDifficulty = 3;

                        break;
                    case 1:
                        //medium
                        modeDifficulty = 8;
                        break;
                    case 2:
                        //hard
                        modeDifficulty = 13;
                        break;
                }
                break;
            case 4:
                //Random
                switch (difficulty) {
                    case 0:
                        //easy
                        modeDifficulty = 4;

                        break;
                    case 1:
                        //medium
                        modeDifficulty = 9;
                        break;
                    case 2:
                        //hard
                        modeDifficulty = 14;
                        break;
                }
                break;

        }
        return modeDifficulty;
    }

    public void Lobby() {
        // This method will prepare and display the lobby container.
        // This container will allow to configure settings before playing
        // This will also be the first screen that the player will see if the game
        // is started up

        // Before displaying a new container, all visual components from the previous container must be removed
        frmMainForm.removeAll();

        // Setting the title to indicate the lobby
        frmMainForm.setTitle(sFrmTitle);

        // The container will need to specify a box layout, and string arrays of the difficulties and modes
        LobbyContainer lobbyContainer = new LobbyContainer(BoxLayout.y(), sDifficulty, sMode, highScores);

        frmMainForm.add(lobbyContainer);

        frmMainForm.show();

    } // end Lobby

    public void playGame(int iDifficulty, int iMode, String sTimer, Boolean bRandomModeSel, int iCurrentSkips) {
        // This method will prepare and display the playGame container.
        // Based on the difficulty and mode selected in the lobby, the game will be
        // played through those settings

        // Before displaying a new container, all visual components from the previous container must be removed
        frmMainForm.removeAll();

        // Displaying the title and the difficulty selected on the form
        frmMainForm.setTitle(sFrmTitle + ": " + sDifficulty[iDifficulty]);

        // Displays the contents of GameContainer to show the game's user interface.
        // GameContainer requires the layout, difficulty ID, mode ID, remaining timer,
        // the boolean to verify if the random mode is selected, and verifying the
        // remaining number of skips
        GameContainer gameContainer = new GameContainer(BoxLayout.y(), iDifficulty, iMode, sTimer, bRandomModeSel, iCurrentSkips);

        frmMainForm.add(gameContainer);

        frmMainForm.show();

    } // end playGame

    public void Results(String sFinalScore, int iGetDiff, int iGetMode, Boolean bRandModeSel){
        // This method will prepare and display the Results container.
        // When a game has finished, via time running out, the user will be given a summary
        // of their score, and what difficulty and mode they have chosen for that game.
        // On this screen the player can choose to play again with the existing settings,
        // or return to the lobby change the game settings.

        // Before displaying a new container, all visual components from the previous container must be removed
        frmMainForm.removeAll();

        // Obtaining the name of the difficulty selected
        sSummaryDiff = sDifficulty[iGetDiff];

        // If the random mode boolean is true, the mode on the results screen will show "Random"
        if(bRandModeSel) sSummaryMode = sMode[sMode.length - 1];
        // Non-random modes just get their respective mode based on the
        // index from the sMode string array
        else sSummaryMode = sMode[iGetMode];

        // The background colour will be reset to white
        ResetBackgroundColour();

        // Indicate to the player that the game is over
        frmMainForm.setTitle("Game Over");

        // To display the results, the container requires the finalised score, the indexes of the selected difficulty and mode
        // (for non-random modes only), the string of the difficulty and mode selected, and the boolean to determine if the
        // mode is random or not
        ResultsContainer resultsContainer = new ResultsContainer(BoxLayout.y(), sFinalScore, iGetDiff, iGetMode, sSummaryDiff, sSummaryMode, bRandModeSel);

        int iFinalScore = Integer.parseInt(sFinalScore);

        frmMainForm.add(resultsContainer);

        frmMainForm.show();

    } // end Results

    public void Verdict(Boolean bVerdict){
        // This method will affect the appearance of the screen if a right or wrong answer is selected

        //=============================================
        // Reference C1: Externally sourced code
        // Purpose: To change the background colour when either a right or wrong answer is selected
        // Date: 2 November 2020
        // Source: Codename One Developer Guide - Basics: Themes, Styles, Components and Layouts
        // Author: unknown
        // url: https://www.codenameone.com/manual/basics.html
        // Adaptation required: Only included the code necessary for declaring and instantiating a Style object
        //                      and only used the method setBgColor() to change the background colour
        //=============================================

        Style styleBg = frmMainForm.getAllStyles();

        // Green background is for correct answers,
        // Red background is for wrong answers
        if(!bVerdict) styleBg.setBgColor(0xff8383);
        else styleBg.setBgColor(0x00dd00);

        //=============================================
        // End reference C1
        //=============================================

    } // end Verdict

    public void ResetBackgroundColour(){
        // This method will be used to reset the background colour
        // back to white, particularly when a game is finished

        //=============================================
        // Reference C2 Externally sourced code
        // Purpose: To revert back to the default background colour.
        // Date: 2 November 2020
        // Source: Codename One Developer Guide - Basics: Themes, Styles, Components and Layouts
        // Author: unknown
        // url: https://www.codenameone.com/manual/basics.html
        // Adaptation required: Only included the code necessary for declaring and instantiating a Style object
        //                      and only used the method setBgColor() to change the background colour
        //=============================================

        // Generating a new style for the main form
        Style styleBgReset = frmMainForm.getAllStyles();

        // Changing the background colour back to white (default colour)
        styleBgReset.setBgColor(0xffffff);

        //=============================================
        // End reference C2
        //=============================================

    } // end ResetBackgroundColour

    public void SkipQuestionBGColour(){

        //=============================================
        // Reference C3 Externally sourced code
        // Purpose: To change to a yellow background colour if a question was skipped when shaking the device (phone)
        // Date: 3 November 2020
        // Source: Codename One Developer Guide - Basics: Themes, Styles, Components and Layouts
        // Author: unknown
        // url: https://www.codenameone.com/manual/basics.html
        // Adaptation required: Only included the code necessary for declaring and instantiating a Style object
        //                      and only used the method setBgColor() to change the background colour
        //=============================================

        // Generating a new style for the main form
        Style styleBgSkip = frmMainForm.getAllStyles();

        // Changing the background colour to yellow to highlight that a question was skipped
        // when shaking the device via its accelerometer
        styleBgSkip.setBgColor(0xffff00);

        //=============================================
        // End reference C3
        //=============================================

    } // end SkipQuestionBGColour

} // end class MyApplication