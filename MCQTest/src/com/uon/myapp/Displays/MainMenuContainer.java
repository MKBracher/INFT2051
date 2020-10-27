package com.uon.myapp.Displays;

import com.codename1.ui.*;
import com.codename1.ui.layouts.Layout;
import com.uon.myapp.MyApplication;

public class MainMenuContainer extends Container{

    // This container is basically the main menu of the app

    private Button btnPlay;

    public MainMenuContainer(Layout layout){
        super(layout);
        this.setScrollableY(false);
        this.init();
    }

    // This method will initiate the process of displaying components
    // in the main menu
    private void init(){

        btnPlay = new Button("Play Game");

        // Declaring and instantiating the class MyApplication
        MyApplication myApp = new MyApplication();

        // This button will show the lobby screen
        btnPlay.addActionListener((e) -> myApp.Lobby());

        this.add(btnPlay);
    }


}
