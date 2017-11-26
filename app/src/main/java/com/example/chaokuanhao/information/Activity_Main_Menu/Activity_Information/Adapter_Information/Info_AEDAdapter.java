package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Adapter_Information;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Data_information.Info_AEDData;
import com.example.chaokuanhao.information.R;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/25.
 */

public class Info_AEDAdapter extends RecyclerView.Adapter<Info_AEDAdapter.ViewHolder> implements View.OnClickListener{
    private ArrayList<Info_AEDData> _data;
    private OnItemClickListener mOnItemClickListener = null;

    public Info_AEDAdapter(ArrayList<Info_AEDData> d){
        _data = d;
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

    public ArrayList<Info_AEDData> getSeleted(){
        ArrayList<Info_AEDData> ans = new ArrayList<>();
        for(int i = 0; i < _data.size(); ++i)
            if(_data.get(i).isCheck())ans.add(_data.get(i));

        return ans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_information_aedlist, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Info_AEDData temp = _data.get(position);
        holder.addr.setText(temp.getPlaceAddress());
        holder.name.setText(temp.getPlaceName() +"  "+ temp.getPlaceDetail());

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
