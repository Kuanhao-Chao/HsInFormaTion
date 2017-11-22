package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Daily_Reminder;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Daily_Reminder.Fragment_Daily_Reminder.Daily_Reminder_Burglar;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Daily_Reminder.Fragment_Daily_Reminder.Daily_Reminder_Fire;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Daily_Reminder.Fragment_Daily_Reminder.Daily_Reminder_News;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Daily_Reminder.Fragment_Daily_Reminder.Daily_Reminder_Transportation;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Daily_Reminder.Fragment_Daily_Reminder.Daily_Reminder_Weather;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Information_List_Activity;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;

/**
 * Created by chaokuanhao on 22/11/2017.
 */

public class Activity_Daily_Reminder_Intro extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // no set content view here

        // do design your own background
        addSlide( new Daily_Reminder_News() );
        addSlide( new Daily_Reminder_Weather());
        addSlide( new Daily_Reminder_Transportation());
        addSlide( new Daily_Reminder_Fire() );
        addSlide( new Daily_Reminder_Burglar() );

        // do use the set original one

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
//        setSeparatorColor(Color.parseColor("#2196F3"));

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);
    }

    // to some animation between switching~~~~~

    /**
     * Called when the user clicked the skip button
     *
     * @param currentFragment Instance of the currently displayed fragment
     */
    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // do something when user pressed skip~~
        Intent intent = new Intent();
        intent.setClass(Activity_Daily_Reminder_Intro.this, Information_List_Activity.class);
        startActivity(intent);
    }

    /**
     * Called when the user clicked the done button
     *
     * @param currentFragment Instance of the currently displayed fragment
     */
    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // do something when user pressed done~~
        Intent intent = new Intent();
        intent.setClass(Activity_Daily_Reminder_Intro.this, Information_List_Activity.class);
        startActivity(intent);
    }

    /**
     * Called when the selected fragment changed. This will be called automatically if the into starts or is finished via the done button.
     *
     * @param oldFragment Instance of the fragment which was displayed before. This might be null if the the intro has just started.
     * @param newFragment Instance of the fragment which is displayed now. This might be null if the intro has finished
     */
    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // do something when the slide is changed
    }
}
