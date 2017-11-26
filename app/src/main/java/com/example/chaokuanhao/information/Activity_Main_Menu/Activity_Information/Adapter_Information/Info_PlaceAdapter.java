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
 * Created by user on 2017/11/26.
 */

public class Info_PlaceAdapter extends RecyclerView.Adapter<Info_PlaceAdapter.ViewHolder>  implements View.OnClickListener{
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<Boolean> all = new ArrayList<Boolean>();
    private OnItemClickListener mOnItemClickListener = null;
    private Info_policeAdapter police;
    private Info_fireDepAdapter fire;
    private Info_emergAdapter emerg;

    public Info_PlaceAdapter(Info_policeAdapter p, Info_fireDepAdapter f, Info_emergAdapter e){
        name.add("警察局"); all.add(false);
        name.add("消防局"); all.add(false);
        name.add("緊急避難所"); all.add(false);
        police = p;
        fire = f;
        emerg = e;
    }

    public Info_policeAdapter getPolice() { return police; }
    public Info_fireDepAdapter getFire() { return  fire; }
    public Info_emergAdapter getEmerg() { return  emerg; }

//    public void setAll(int pos, boolean value){
//        all.set(pos, value);
//        switch (pos){
//            case 0:
//                police.setChecked(value);
//                break;
//            case 1:
//                fire.setChecked(value);
//                break;
//            case 2:
//                emerg.setChecked(value);
//                break;
//        }
//    }
//
//    public void checkAllcheck(){
//        all.set(0, police.isAllChecked());
//        all.set(1, fire.isAllChecked());
//        all.set(2, emerg.isAllChecked());
//    }
//
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
    public int getItemCount() { return name.size(); }

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView)itemView.findViewById(R.id.RootText);
        }
    }
}
