package com.tuhocandroid.navdrawerandtablayout.Process;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.tuhocandroid.navdrawerandtablayout.R;

import java.util.ArrayList;

/**
 * Created by HODUCVIET on 11/10/2016.
 */
public class RecycleViewAdapterPlaylist extends RecyclerView.Adapter<RecycleViewAdapterPlaylist.ViewHolderPlaylist> {
    private ArrayList<String> itemList;
    private Fragment context;
    View layoutView;
    public RecycleViewAdapterPlaylist(Fragment context,ArrayList<String>itemList){
        this.itemList = itemList;
        this.context = context;
    }
    @Override
    public ViewHolderPlaylist onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recycle_playlist,null
        );
        ViewHolderPlaylist rcv = new ViewHolderPlaylist(layoutView);
        return rcv;

    }

    @Override
    public void onBindViewHolder(ViewHolderPlaylist holder, int position) {
        holder.name_playlist.setText(itemList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class ViewHolderPlaylist extends RecyclerView.ViewHolder
            implements RecyclerView.OnClickListener,AdapterView.OnItemClickListener{
        public TextView name_playlist;
        public ImageButton bt_playlist;
        public ViewHolderPlaylist(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name_playlist=(TextView)itemView.findViewById(R.id.name_playlist);
            bt_playlist=(ImageButton)itemView.findViewById(R.id.bt_playlist);
            bt_playlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup=new PopupMenu(itemView.getContext(),bt_playlist);
                    popup.getMenuInflater().inflate(R.menu.popup_playlist,popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.rename:
                                    final Dialog dialog=new Dialog(itemView.getContext());
                                    dialog.setTitle("Tên playlist");
                                    dialog.setContentView(R.layout.playlist);
                                    dialog.setCancelable(false);
                                    EditText ed_name=(EditText)dialog.findViewById(R.id.tv_playlist);
                                    ed_name.setText(itemList.get(getPosition()).toString());
                                    Button cancel=(Button)dialog.findViewById(R.id.cancel);
                                    cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.cancel();
                                        }
                                    });

                                     Button add=(Button)dialog.findViewById(R.id.add);
                                    add.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            EditText name = (EditText) dialog.findViewById(R.id.tv_playlist);
                                            String string = name.getText().toString();
                                            itemList.remove(getPosition());
                                            notifyItemRemoved(getPosition());
                                            notifyItemRangeChanged(getPosition(), itemList.size());
                                            itemList.add(getPosition(), string);
                                            dialog.cancel();
                                        }
                                    });
                                    dialog.show();
                                    break;
                                case R.id.delete_playlist: {
                                    itemList.remove(getPosition());
                                    notifyItemRemoved(getPosition());
                                    notifyItemRangeChanged(getPosition(), itemList.size());
                                    break;
                                }

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

        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}
