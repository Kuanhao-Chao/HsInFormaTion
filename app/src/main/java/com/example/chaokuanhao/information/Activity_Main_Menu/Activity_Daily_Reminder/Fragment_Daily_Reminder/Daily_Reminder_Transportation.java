package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Daily_Reminder.Fragment_Daily_Reminder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chaokuanhao.information.R;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;

/**
 * Created by chaokuanhao on 22/11/2017.
 */

public final class Daily_Reminder_Transportation extends Fragment implements ISlideBackgroundColorHolder {
    private final String TAG = Daily_Reminder_Transportation.class.getSimpleName();
    private View mView;
    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "OnCreareView is called");
        View view = inflater.inflate(R.layout.fragment_daily_reminder_transportation, container, false);
        setBackgroundColor(getDefaultBackgroundColor());
        mView = view;
        return  view;
    }

    @Override
    public int getDefaultBackgroundColor() {
        // Return the default background color of the slide.
        Log.d(TAG, "getDefaultBackgroundColor() is called ");
        Log.d(TAG, String.valueOf(Color.parseColor("#000000")) );
        return Color.parseColor("#000000");
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        Log.d(TAG, "setBackgroundColor() is called");
        // Set the background color of the view within your slide to which the transition should be applied.
        if ( mView != null) {
            mView.setBackgroundColor(backgroundColor);
        }
    }
}
