package com.tuhocandroid.navdrawerandtablayout.Process;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tuhocandroid.navdrawerandtablayout.Object.Song;
import com.tuhocandroid.navdrawerandtablayout.R;

import java.util.List;

/**
 * Created by HODUCVIET on 11/22/2016.
 */
public class RecycleViewAdapterSelect extends RecyclerView.Adapter<RecycleViewAdapterSelect.ViewHolderSelect>{
    View layoutView;
    private List<Song> itemList;
    private Context context;
    public RecycleViewAdapterSelect(Context context,
                                    List<Song> itemList)
    {
        this.itemList = itemList;
        this.context = context;
    }
    @Override
    public ViewHolderSelect onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_select,null);
        ViewHolderSelect rcv =new ViewHolderSelect(layoutView);
        return rcv ;
    }

    @Override
    public void onBindViewHolder(ViewHolderSelect holder,  int position) {
        final int pos =position;
        holder.tv_name.setText(itemList.get(position).getSongName());
        holder.tv_singer.setText(itemList.get(position).getSinger());
        holder.checkBox.setChecked(itemList.get(position).isSelected());
        holder.checkBox.setTag(itemList.get(position));
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkbox = (CheckBox)v;
                Song song = (Song) checkbox.getTag();
                song.setIsSelected(checkbox.isChecked());
                itemList.get(pos).setIsSelected(checkbox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class ViewHolderSelect extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener, AdapterView.OnItemSelectedListener{
        private TextView tv_name;
        private TextView tv_singer;
        private CheckBox checkBox;

        public ViewHolderSelect(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_name = (TextView)itemView.findViewById(R.id.song_name);
            tv_singer = (TextView)itemView.findViewById(R.id.singer_name);
            checkBox = (CheckBox)itemView.findViewById(R.id.chbx_select);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
