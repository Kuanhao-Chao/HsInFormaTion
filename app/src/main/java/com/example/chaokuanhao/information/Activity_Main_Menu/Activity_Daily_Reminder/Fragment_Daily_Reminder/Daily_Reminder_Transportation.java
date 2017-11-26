package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Daily_Reminder.Fragment_Daily_Reminder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chaokuanhao.information.R;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;

/**
 * Created by chaokuanhao on 22/11/2017.
 */

public final class Daily_Reminder_Transportation extends Fragment implements ISlideBackgroundColorHolder {
    private LinearLayout layoutcontainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_reminder_transportation, container, false);
        layoutcontainer = (LinearLayout) view.findViewById(R.id.daily_reminder_transportation);
        ImageView img = (ImageView) view.findViewById(R.id.daily_reminder_transportation_image);
        img.setImageResource(R.drawable.car3);
        TextView text = (TextView) view.findViewById(R.id.daily_reminder_transportation_description);
        String toHtml= "<a href='http://www.hccp.gov.tw/hccp/ch/index.jsp'> 酒駕293起，超速16997起 </a>";
        text.setText(Html.fromHtml(toHtml));
        text.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }

    @Override
    public int getDefaultBackgroundColor() {
        // Return the default background color of the slide.
        return Color.parseColor("#6699ff");
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        // Set the background color of the view within your slide to which the transition should be applied.
        if (layoutcontainer!= null) {
            layoutcontainer.setBackgroundColor(backgroundColor);
        }
    }
}

