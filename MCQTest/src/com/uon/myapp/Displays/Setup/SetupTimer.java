package com.uon.myapp.Displays.Setup;

public class SetupTimer {

    // This class will setup the starting remaining time based on the string sTimer
    private final String sTimer;

    // This constructor will be used to allocate the timer that will be used for the quiz/game
    public SetupTimer(){ sTimer = "60"; }

    public String GetTimer(){ return sTimer; }
} // end class SetupTimer
