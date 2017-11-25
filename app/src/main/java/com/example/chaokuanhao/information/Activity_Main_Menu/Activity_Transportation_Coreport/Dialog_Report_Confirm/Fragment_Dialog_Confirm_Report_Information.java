package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.Dialog_Report_Confirm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.QueryUtils_Report_Accident;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Transportation_Coreport.models.PlaceInfo;
import com.example.chaokuanhao.information.R;

import java.util.List;

/**
 * Created by chaokuanhao on 24/11/2017.
 */

public class Fragment_Dialog_Confirm_Report_Information extends DialogFragment {


    private static final String TAG = Fragment_Dialog_Confirm_Report_Information.class.getSimpleName();

    private static final String USGS_REQUEST_URL_REPORT_ACCIDENT = "http://192.168.0.110:5000/bell/";
    private String lagLng;
    private String typeAccident;


    public static Fragment_Dialog_Confirm_Report_Information newInstance(String msg1, String msg2, String msg3, int msg4 ) {
        Fragment_Dialog_Confirm_Report_Information fragment = new Fragment_Dialog_Confirm_Report_Information();

        Bundle bundle = new Bundle();

        bundle.putString("msg1", msg1); // set msg here
        bundle.putString("msg2", msg2);
        bundle.putString("msg3", msg3);
        bundle.putInt("msg4", msg4);
        fragment.setArguments(bundle);

        return fragment;
    }

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * Override to build your own custom Dialog container.  This is typically
     * used to show an AlertDialog instead of a generic Dialog; when doing so,
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} does not need
     * to be implemented since the AlertDialog takes care of its own content.
     * <p>
     * <p>This method will be called after {@link #onCreate(Bundle)} and
     * before {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.  The
     * default implementation simply instantiates and returns a {@link Dialog}
     * class.
     * <p>
     * <p><em>Note: DialogFragment own the {@link Dialog#setOnCancelListener
     * Dialog.setOnCancelListener} and {@link Dialog#setOnDismissListener
     * Dialog.setOnDismissListener} callbacks.  You must not set them yourself.</em>
     * To find out about these events, override {@link #onCancel(DialogInterface)}
     * and {@link #onDismiss(DialogInterface)}.</p>
     *
     * @param savedInstanceState The last saved instance state of the Fragment,
     *                           or null if this is a freshly created Fragment.
     * @return Return a new Dialog instance to be displayed by the Fragment.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // to get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //inflate an layout for the dialog
//        builder.setView(inflater.inflate(R.layout.fragment_transportation_coreport_map_popup_dialog, null));

//        TextView textView_lagLng = (TextView) inflater.inflate(R.layout.fragment_transportation_coreport_map_popup_dialog, null).findViewById(R.id.fragment_trasportation_coreport_map_popup_dialog_latlng);
//        textView_lagLng.setText(getArguments().getString("msg1"));
//
//        TextView textView_accident_type = (TextView) inflater.inflate(R.layout.fragment_transportation_coreport_map_popup_dialog, null).findViewById(R.id.fragment_trasportation_coreport_map_popup_dialog_kind);
//        textView_accident_type.setText(getArguments().getString("msg2"));

        builder.setTitle("事故回報")
                .setIcon(R.drawable.ic_action_report)
                .setMessage("經 度 ：" + getArguments().getString("msg1") + '\n' + "緯 度 ：" + getArguments().getString("msg2") + '\n' + "事故種類：" + getArguments().getString("msg3"))
//                .setMessage(getArguments().getString("msg2"))
                .setPositiveButton("確定回報", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // to start the httprequest!!! report the data to server
                        ReportAsyncTask task01 = new ReportAsyncTask();
                        task01.execute(USGS_REQUEST_URL_REPORT_ACCIDENT);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Fragment_Dialog_Confirm_Report_Information.this.getDialog().cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        Log.d(TAG, getArguments().getString("msg1"));
        Log.d(TAG, getArguments().getString("msg2"));
//        TextView textView_lagLng = (TextView) dialog.findViewById()
//        textView_lagLng.setText(getArguments().getString("msg1").toString());
//        TextView textView_accident_type = (TextView) dialog.findViewById(R.id.fragment_trasportation_coreport_map_popup_dialog_kind);
//        textView_accident_type.setText(getArguments().getString("msg2").toString());

        return dialog;
    }

    private class ReportAsyncTask extends AsyncTask<String, Void, List<PlaceInfo>> {
        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param urls The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected List<PlaceInfo> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            String _latitude;
            String _longitude;
            String _report_type;
            int _report_type_int_value;
            _latitude = getArguments().getString("msg1");
            _longitude = getArguments().getString("msg2");
            _report_type = getArguments().getString("msg3");
            _report_type_int_value = getArguments().getInt("msg4");

            urls[0] = urls[0] + _latitude + '/' + _longitude + '/' + _report_type_int_value;
            Log.i(TAG, urls[0]);
            //Log.i(LOG_TAG, urls[1]);
            QueryUtils_Report_Accident.report_Accident_Point(urls[0]);
            return null;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param placeInfoList The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(List<PlaceInfo> placeInfoList) {
            if (placeInfoList != null && !placeInfoList.isEmpty()) {
            }
        }
    }


}
