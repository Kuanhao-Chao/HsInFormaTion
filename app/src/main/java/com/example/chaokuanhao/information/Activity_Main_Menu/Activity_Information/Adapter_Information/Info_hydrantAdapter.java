package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Adapter_Information;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Data_information.Info_hydrantData;
import com.example.chaokuanhao.information.R;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/25.
 */

public class Info_hydrantAdapter extends RecyclerView.Adapter<Info_hydrantAdapter.ViewHolder> implements View.OnClickListener{
    private ArrayList<Info_hydrantData> _data;
    private OnItemClickListener mOnItemClickListener = null;

    public Info_hydrantAdapter(ArrayList<Info_hydrantData> d){
        _data = d;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_information_aedlist, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void setChecked(boolean c){
        for(int i = 0; i < _data.size(); ++i)_data.get(i).setCheck(c);
    }

    public void setChecked(int pos, boolean c){
        _data.get(pos).setCheck(c);
    }

    public boolean isAllChecked(){
        for(int i = 0; i < _data.size(); ++i)
            if(!_data.get(i).isCheck())return false;

        return true;
    }

    public ArrayList<Info_hydrantData> getSeleted(){
        ArrayList<Info_hydrantData> ans = new ArrayList<>();
        for(int i = 0; i < _data.size(); ++i)
            if(_data.get(i).isCheck())ans.add(_data.get(i));

        return ans;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Info_hydrantData temp = _data.get(position);
        holder.addr.setText(temp.getNo());
        holder.name.setText(temp.getPlace());


    }

    @Override
    public int getItemCount() {
        return _data.size();
    }

    @Override
    public void onClick(View view) {
        if(mOnItemClickListener != null)mOnItemClickListener.onItemClick(view, (int)view.getTag());
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView addr, name;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.AEDPlace);
            addr = itemView.findViewById(R.id.AEDAddress);
        }
    }
}
