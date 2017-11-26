package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Fragment_Information;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Adapter_Information.Info_AEDAdapter;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Adapter_Information.Info_MonitorAdapter;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Adapter_Information.Info_ToolAdapter;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Adapter_Information.Info_hydrantAdapter;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Data_information.Info_AEDData;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Data_information.Info_MonitorData;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Data_information.Info_hydrantData;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.QueryUtils;
import com.example.chaokuanhao.information.R;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by chaokuanhao on 21/11/2017.
 */

public class Information_AED_Fragment extends Fragment{
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


    private RecyclerView _recycleView;
    private TextView _text;
//    private CheckBox _check;
    private Info_ToolAdapter _toolAdapter;
    private final String back = "    按此返回";
    private final String choose = "    點選查看詳細資料";
    private ImageView mImageView;
    private enum STATE{
        ROOT,
        AED,
        HYDRANT,
        MONITOR,
        NOT_YET
    }
    private STATE state;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        state = STATE.NOT_YET;
        View view = inflater.inflate(R.layout.fragment_information_aed, container, false);
        mImageView = view.findViewById(R.id.ic_action_go_inside);

        _text = (TextView)view.findViewById(R.id.ToolText);
        _text.setText("讀取中");
        _text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state == STATE.AED || state == STATE.HYDRANT || state == STATE.MONITOR){
//                    _toolAdapter.checkAllcheck();
                    mImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_go_inside));
                    _recycleView.setAdapter(_toolAdapter);
//                    _check.setChecked(_toolAdapter.isAllChecked());
                    _text.setText(choose);
                    state = STATE.ROOT;
                }
            }
        });
//
////        _check = (CheckBox)view.findViewById(R.id.ToolCheck);
////        _check.setVisibility(View.INVISIBLE);
////        _check.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
//                boolean check = ((CheckBox)view).isChecked();
//                switch (state){
//                    case ROOT:
//                        _toolAdapter.setAll(0, check);
//                        _toolAdapter.setAll(1, check);
//                        _toolAdapter.setAll(2, check);
//                        _recycleView.setAdapter(_toolAdapter);
//                        break;
//                    case AED:
//                        _toolAdapter.getAED().setChecked(check);
//                        _recycleView.setAdapter(_toolAdapter.getAED());
//                        break;
//                    case HYDRANT:
//                        _toolAdapter.getHydrant().setChecked(check);
//                        _recycleView.setAdapter(_toolAdapter.getHydrant());
//                        break;
//                    case MONITOR:
//                        _toolAdapter.getMonitor().setChecked(check);
//                        _recycleView.setAdapter(_toolAdapter.getMonitor());
//
//                }
//            }
//        });
        _recycleView = (RecyclerView)view.findViewById(R.id.ToolRecycleView);
        _recycleView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        _recycleView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        });

        getDataAsyncTask thread = new getDataAsyncTask();
        thread.execute(view);

        return view;
    }

    private class getDataAsyncTask extends AsyncTask<View, Boolean, Void>{

        View _v;
        @Override
        protected Void doInBackground(View... views) {
            _v = views[0];
            while(!QueryUtils.isOK());
            return null;
        }

        @Override
        protected void onPostExecute(Void v){
            _text.setText(choose);
//            _check.setVisibility(View.VISIBLE);

            _toolAdapter = new Info_ToolAdapter(getAED(), getHydrant(), getMonitor());
            _toolAdapter.setOnItemClickListener(new Info_ToolAdapter.OnItemClickListener(){

                @Override
                public void onItemClick(View v, int position) {
                    mImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_go_back));
                    if(v instanceof CheckBox){
//                        _toolAdapter.setAll(position, ((CheckBox) v).isChecked());
//                        _check.setChecked(_toolAdapter.isAllChecked());
                    } else {
                        if(position == 0){
                            _recycleView.setAdapter(_toolAdapter.getAED());
//                            _check.setChecked(_toolAdapter.getAED().isAllChecked());
                            _text.setText(back);
                            state = STATE.AED;
                        } else if(position == 1){
                            _recycleView.setAdapter(_toolAdapter.getHydrant());
//                            _check.setChecked(_toolAdapter.getHydrant().isAllChecked());
                            _text.setText(back);
                            state = STATE.HYDRANT;
                        }else if(position == 2){
                            _recycleView.setAdapter(_toolAdapter.getMonitor());
//                            _check.setChecked(_toolAdapter.getMonitor().isAllChecked());
                            _text.setText(back);
                            state = STATE.MONITOR;
                        }
                    }

                }
            });
            _recycleView.setAdapter(_toolAdapter);
            state = STATE.ROOT;
        }
    }

    private Info_AEDAdapter getAED(){
        ArrayList<Info_AEDData> data = new ArrayList<Info_AEDData>() {
        };
        for(int i = 0; i < QueryUtils.getAED().length(); ++i)
            try {
                data.add(new Info_AEDData(QueryUtils.getAED().getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        Info_AEDAdapter AED = new Info_AEDAdapter(data);
        AED.setOnItemClickListener(new Info_AEDAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(view instanceof CheckBox){
                    _toolAdapter.getAED().setChecked(position, ((CheckBox) view).isChecked());
//                    _check.setChecked(_toolAdapter.getAED().isAllChecked());
                }
            }
        });
        return AED;
    }

    private Info_hydrantAdapter getHydrant(){
        ArrayList<Info_hydrantData> data = new ArrayList<Info_hydrantData>() {
        };
        for(int i = 0; i < QueryUtils.getHydrant().length(); ++i)
            try {
                data.add(new Info_hydrantData(QueryUtils.getHydrant().getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        Info_hydrantAdapter hydrant = new Info_hydrantAdapter(data);
        hydrant.setOnItemClickListener(new Info_hydrantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(view instanceof CheckBox){
                    _toolAdapter.getHydrant().setChecked(position, ((CheckBox) view).isChecked());
//                    _check.setChecked(_toolAdapter.getHydrant().isAllChecked());
                }
            }
        });
        return hydrant;
    }

    private Info_MonitorAdapter getMonitor(){
        ArrayList<Info_MonitorData> data = new ArrayList<Info_MonitorData>() {
        };
        for(int i = 0; i < QueryUtils.getMonitor().length(); ++i)
            try {
                data.add(new Info_MonitorData(QueryUtils.getMonitor().getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        Info_MonitorAdapter Monitor = new Info_MonitorAdapter(data);
        Monitor.setOnItemClickListener(new Info_MonitorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(v instanceof CheckBox){
                    _toolAdapter.getMonitor().setChecked(position, ((CheckBox) v).isChecked());
//                    _check.setChecked(_toolAdapter.getMonitor().isAllChecked());
                }
            }
        });
        return Monitor;
    }
}


