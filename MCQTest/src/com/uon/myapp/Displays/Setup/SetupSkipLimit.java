package com.uon.myapp.Displays.Setup;

public class SetupSkipLimit {

    // This class will setup and allocate the maximum number of skips
    // a game can take
    private final int iRemainingSkips;

    // This constructor will be used to set the maximum number of skips a game can have
    public SetupSkipLimit(){ iRemainingSkips = 3; }

    public int GetSkipLimit(){ return iRemainingSkips; }

} // end class SetupSkipLimit
