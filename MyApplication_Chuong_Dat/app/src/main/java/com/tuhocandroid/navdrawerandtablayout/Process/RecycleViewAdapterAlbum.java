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
import java.util.HashMap;

/**
 * Created by HODUCVIET on 11/10/2016.
 */
public class RecycleViewAdapterAlbum extends RecyclerView.Adapter<RecycleViewAdapterAlbum.ViewHolderAlbum> {
    private ArrayList<HashMap<String,String>> itemList;
    private Fragment context;
    View layoutView;
    public RecycleViewAdapterAlbum(Fragment context,
                                    ArrayList<HashMap<String,String>> itemList)
    {
        this.itemList = itemList;
        this.context = context;
    }
    @Override
    public ViewHolderAlbum onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutView= LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recycle_album, null);
        ViewHolderAlbum rcv=new ViewHolderAlbum(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ViewHolderAlbum holder, int position) {
        holder.name_album.setText(itemList.get(position).get("Album name"));
        holder.singer.setText(itemList.get(position).get("Singer name"));

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class ViewHolderAlbum extends RecyclerView.ViewHolder
            implements RecyclerView.OnClickListener,AdapterView.OnItemClickListener {
        private TextView name_album;
        private TextView singer;
        private ImageButton bt_album;

        public ViewHolderAlbum(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name_album = (TextView)itemView.findViewById(R.id.name_album);
            singer = (TextView)itemView.findViewById(R.id.singer);
            bt_album = (ImageButton)itemView.findViewById(R.id.bt_album);
            bt_album.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(itemView.getContext(),bt_album);
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
            String album=name_album.getText().toString();
            Intent intent=new Intent(v.getContext(), SongList.class);
            intent.putExtra("Value",album);
            v.getContext().startActivity(intent);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}
