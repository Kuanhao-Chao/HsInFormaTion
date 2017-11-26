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

import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Adapter_Information.Info_PlaceAdapter;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Adapter_Information.Info_emergAdapter;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Adapter_Information.Info_fireDepAdapter;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Adapter_Information.Info_policeAdapter;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Data_information.Info_emergData;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Data_information.Info_fireDepData;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Data_information.Info_policeData;
import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.QueryUtils;
import com.example.chaokuanhao.information.R;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by chaokuanhao on 21/11/2017.
 */

public class Information_Hydrant_Fragment extends Fragment{
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
    private Info_PlaceAdapter _RootAdapter;
    private final String back = "    按此返回";
    private final String choose = "    點選查看詳細資料";
    private ImageView mImageView;
    private enum STATE{
        ROOT,
        POLICE,
        FIRE,
        EMERG,
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
                if(state == STATE.POLICE || state == STATE.FIRE || state == STATE.EMERG){
                    mImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_go_inside));
//                    _RootAdapter.checkAllcheck();
                    _recycleView.setAdapter(_RootAdapter);
//                    _check.setChecked(_RootAdapter.isAllChecked());
                    _text.setText(choose);
                    state = STATE.ROOT;
                }
            }
        });

//        _check = (CheckBox)view.findViewById(R.id.ToolCheck);
//        _check.setVisibility(View.INVISIBLE);
//        _check.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                boolean check = ((CheckBox)view).isChecked();
//                switch (state){
//                    case ROOT:
//                        _RootAdapter.setAll(0, check);
//                        _RootAdapter.setAll(1, check);
//                        _RootAdapter.setAll(2, check);
//                        _recycleView.setAdapter(_RootAdapter);
//                        break;
//                    case POLICE:
//                        _RootAdapter.getPolice().setChecked(check);
//                        _recycleView.setAdapter(_RootAdapter.getPolice());
//                        break;
//                    case FIRE:
//                        _RootAdapter.getFire().setChecked(check);
//                        _recycleView.setAdapter(_RootAdapter.getFire());
//                        break;
//                    case EMERG:
//                        _RootAdapter.getEmerg().setChecked(check);
//                        _recycleView.setAdapter(_RootAdapter.getEmerg());
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

    private class getDataAsyncTask extends AsyncTask<View, Boolean, Void> {

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

            _RootAdapter = new Info_PlaceAdapter(getPolice(), getFire(), getEmerg());
            _RootAdapter.setOnItemClickListener(new Info_PlaceAdapter.OnItemClickListener(){

                @Override
                public void onItemClick(View v, int position) {
                    mImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_go_back));
                    if(v instanceof CheckBox){
//                        _RootAdapter.setAll(position, ((CheckBox) v).isChecked());
//                        _check.setChecked(_RootAdapter.isAllChecked());
                    } else {
                        if(position == 0){
                            _recycleView.setAdapter(_RootAdapter.getPolice());
//                            _check.setChecked(_RootAdapter.getPolice().isAllChecked());
                            _text.setText(back);
                            state = STATE.POLICE;
                        } else if(position == 1){
                            _recycleView.setAdapter(_RootAdapter.getFire());
//                            _check.setChecked(_RootAdapter.getFire().isAllChecked());
                            _text.setText(back);
                            state = STATE.FIRE;
                        }else if(position == 2){
                            _recycleView.setAdapter(_RootAdapter.getEmerg());
//                            _check.setChecked(_RootAdapter.getEmerg().isAllChecked());
                            _text.setText(back);
                            state = STATE.EMERG;
                        }
                    }

                }
            });
            _recycleView.setAdapter(_RootAdapter);
            state = STATE.ROOT;
        }
    }

    private Info_policeAdapter getPolice(){
        ArrayList<Info_policeData> data = new ArrayList<Info_policeData>() {
        };
        for(int i = 0; i < QueryUtils.getPolice().length(); ++i)
            try {
                data.add(new Info_policeData(QueryUtils.getPolice().getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        Info_policeAdapter police = new Info_policeAdapter(data);
        police.setOnItemClickListener(new Info_policeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(view instanceof CheckBox){
                    _RootAdapter.getPolice().setChecked(position, ((CheckBox) view).isChecked());
//                    _check.setChecked(_RootAdapter.getPolice().isAllChecked());
                }
            }
        });
        return police;
    }

    private Info_fireDepAdapter getFire(){
        ArrayList<Info_fireDepData> data = new ArrayList<Info_fireDepData>() {
        };
        for(int i = 0; i < QueryUtils.getFireDep().length(); ++i)
            try {
                data.add(new Info_fireDepData(QueryUtils.getFireDep().getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        Info_fireDepAdapter fire = new Info_fireDepAdapter(data);
        fire.setOnItemClickListener(new Info_fireDepAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(view instanceof CheckBox){
                    _RootAdapter.getFire().setChecked(position, ((CheckBox) view).isChecked());
//                    _check.setChecked(_RootAdapter.getFire().isAllChecked());
                }
            }
        });
        return fire;
    }

    private Info_emergAdapter getEmerg(){
        ArrayList<Info_emergData> data = new ArrayList<Info_emergData>() {
        };
        for(int i = 0; i < QueryUtils.getEmergPalce().length(); ++i)
            try {
                data.add(new Info_emergData(QueryUtils.getEmergPalce().getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        Info_emergAdapter emerg = new Info_emergAdapter(data);
        emerg.setOnItemClickListener(new Info_emergAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(view instanceof CheckBox){
                    _RootAdapter.getEmerg().setChecked(position, ((CheckBox) view).isChecked());
//                    _check.setChecked(_RootAdapter.getEmerg().isAllChecked());
                }
            }
        });
        return emerg;
    }
}
