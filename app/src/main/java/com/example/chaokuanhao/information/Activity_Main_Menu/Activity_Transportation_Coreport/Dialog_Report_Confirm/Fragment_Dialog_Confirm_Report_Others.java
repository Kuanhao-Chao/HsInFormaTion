package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Dialog_Report_Confirm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.chaokuanhao.information.R;

/**
 * Created by chaokuanhao on 25/11/2017.
 */

public class Fragment_Dialog_Confirm_Report_Others extends DialogFragment  {

    private static final String TAG = Fragment_Dialog_Confirm_Report_Others.class.getSimpleName();

    /**
     *  -----------------------------  to get data that are going to pop up the window ----------------------------
     */
    public static Fragment_Dialog_Confirm_Report_Others newInstance(String msg1, String msg2, String msg3, int msg4 ) {
        Fragment_Dialog_Confirm_Report_Others fragment = new Fragment_Dialog_Confirm_Report_Others();

        Bundle bundle = new Bundle();

        bundle.putString("msg1", msg1); // set msg here
        bundle.putString("msg2", msg2);
        bundle.putString("msg3", msg3);
        bundle.putInt("msg4", msg4);
        fragment.setArguments(bundle);

        return fragment;
    }
    /**
     *  -----------------------------  interface to get the word in the EditText  --------------------------------
     */

//    public interface NoticeDialogListener{
//        public void onDialogPositiveClick(DialogFragment dialog);
//        public void onDialogNegativeClick(DialogFragment dialog);
//    }
//
//    NoticeDialogListener mListener;
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        try {
//            // Instantiate the NoticeDialogListener so we can send events to the host
//            mListener = (NoticeDialogListener) context;
//        } catch (ClassCastException e) {
//            // The activity doesn't implement the interface, throw exception
//            throw new ClassCastException(context.toString()
//                    + " must implement NoticeDialogListener");
//        }
//    }
    /**
     *  -----------------------------  call the function to modify the dialog  --------------------------------
     */




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();



        builder.setView( inflater.inflate(R.layout.fragment_transportation_coreport_map_popup_dialog, null))
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String other_accident_name;
                        Dialog f = (Dialog) dialog;
                        EditText inputText = (EditText) f.findViewById(R.id.fragment_trasportation_coreport_map_popup_dialog_others_editText);
                        other_accident_name = inputText.getText().toString();

                        // call another diolog
                        Fragment_Dialog_Confirm_Report_Information fragment_dialog_confirm_report_information = new Fragment_Dialog_Confirm_Report_Information()
                                .newInstance(getArguments().getString("msg1"),getArguments().getString("msg2"), other_accident_name, getArguments().getInt("msg3"));
                        fragment_dialog_confirm_report_information.show(getFragmentManager(), "Transportation_coreport_map_popup_dialog_accident_report_other_second_level");
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Fragment_Dialog_Confirm_Report_Others.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
