package com.tuhocandroid.navdrawerandtablayout.Process;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.tuhocandroid.navdrawerandtablayout.Activity.SongList;
import com.tuhocandroid.navdrawerandtablayout.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HODUCVIET on 11/9/2016.
 */
public class RecycleViewAdapterSinger extends RecyclerView.Adapter<RecycleViewAdapterSinger.ViewHolderSinger> {
    private List<String> itemList;
    private Fragment context;
    View layoutView;
    public class ViewHolderSinger extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener, AdapterView.OnItemSelectedListener{
        public TextView tv_singer;
        public ImageButton bt_singer;
        public ViewHolderSinger(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_singer = (TextView) itemView.findViewById(R.id.tv_singer);
            bt_singer=(ImageButton)itemView.findViewById(R.id.bt_singer);
            bt_singer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup=new PopupMenu(itemView.getContext(),bt_singer);
                    popup.getMenuInflater().inflate(R.menu.popup_menu,popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.delete:
                                    itemList.remove(getPosition());
                                    notifyItemRemoved(getPosition());
                                    notifyItemRangeChanged(getPosition(), itemList.size());
                            }
                            return true;
                        }
                    });
                    popup.show();
                }
            });

        }

        @Override
        public void onClick(View v) {
            String singer=tv_singer.getText().toString();
            Intent intent=new Intent(v.getContext(), SongList.class);
            intent.putExtra("Value",singer);
            v.getContext().startActivity(intent);

        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    public RecycleViewAdapterSinger(Fragment context,
                                    ArrayList<String> itemList)
    {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public ViewHolderSinger onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recycle_singer, null);
        ViewHolderSinger rcv = new ViewHolderSinger(layoutView);
        return rcv;
    }


    public void onBindViewHolder(ViewHolderSinger holder, int position) {
        holder.tv_singer.setText(itemList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}

