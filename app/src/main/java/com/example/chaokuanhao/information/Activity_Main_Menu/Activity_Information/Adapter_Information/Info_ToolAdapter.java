package com.example.chaokuanhao.information.Activity_Main_Menu.Activity_Information.Adapter_Information;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.chaokuanhao.information.R;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/25.
 */


public class Info_ToolAdapter extends RecyclerView.Adapter<Info_ToolAdapter.ViewHolder> implements View.OnClickListener{
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<Boolean> all = new ArrayList<Boolean>();
    private OnItemClickListener mOnItemClickListener = null;
    private Info_AEDAdapter AED;
    private Info_hydrantAdapter hydrant;
    private Info_MonitorAdapter Monitor;

    public Info_ToolAdapter(Info_AEDAdapter a, Info_hydrantAdapter h, Info_MonitorAdapter m){
        name.add("AED"); all.add(false);
        name.add("消防栓"); all.add(false);
        name.add("監視器"); all.add(false);
        AED = a;
        hydrant = h;
        Monitor = m;
    }

    public Info_AEDAdapter getAED() {return AED; }
    public Info_hydrantAdapter getHydrant() {return  hydrant; }
    public Info_MonitorAdapter getMonitor() { return Monitor; }

//    public void setAll(int pos, boolean value){
//        all.set(pos, value);
//        switch (pos){
//            case 0:
//                AED.setChecked(value);
//                break;
//            case 1:
//                hydrant.setChecked(value);
//                break;
//            case 2:
//                Monitor.setChecked(value);
//                break;
//        }
//    }

//    public void checkAllcheck(){
//        all.set(0, AED.isAllChecked());
//        all.set(1, hydrant.isAllChecked());
//        all.set(2, Monitor.isAllChecked());
//    }
//    public boolean isAllChecked(){
//        for(int i = 0; i < all.size(); ++i)
//            if(!all.get(i))return false;
//        return true;
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_information_rootlist, parent, false);
        ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(name.get(position));
//        holder.check.setChecked(all.get(position));
//        holder.check.setTag(position);
//        holder.check.setOnClickListener(this);
        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView text;
        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView)itemView.findViewById(R.id.RootText);
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
