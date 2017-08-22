package com.tuhocandroid.navdrawerandtablayout.Process;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.tuhocandroid.navdrawerandtablayout.Activity.PlaySong;
import com.tuhocandroid.navdrawerandtablayout.Database.SongProcess;
import com.tuhocandroid.navdrawerandtablayout.Object.Song;
import com.tuhocandroid.navdrawerandtablayout.R;
import com.tuhocandroid.navdrawerandtablayout.Service.SongService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HODUCVIET on 9/9/2016.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>{
    private List<Song> itemList;
    private Context context;
    View layoutView;
    private static Intent playIntent;
    private static boolean songBound;
    private static SongService songService;

    public RecycleViewAdapter(Context context,
                              List<Song> itemList) {

        this.itemList = itemList;
        this.context = context;
    }



    private static ArrayList<Song> listSong = new ArrayList<>();
    private static ServiceConnection songConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SongService.SongBinder binder = (SongService.SongBinder) service;
            songService = binder.getService();
            songService.setList(listSong);
            songBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            songBound = false;
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recycle_song, null);
        ViewHolder rcv = new ViewHolder(layoutView);
        return rcv;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tv_add.setText(itemList.get(position).getSongName());
        holder.tv_inf.setText(itemList.get(position).getSinger());


    }


    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener, AdapterView.OnItemSelectedListener {
        public TextView tv_add;
        public TextView tv_inf;
        public ImageButton bt_song;

        public ViewHolder(final View itemView) {
            super(itemView);
            SongPlaylist songPlaylist = new SongPlaylist();
            final SongProcess songProcess = songPlaylist.ListAllSongs(itemView.getContext());
            System.out.println(songBound);
            if (playIntent == null) {
                listSong = songProcess.List_Song();
                playIntent = new Intent(itemView.getContext().getApplicationContext(), SongService.class);
                itemView.getContext().startService(playIntent);
                itemView.getContext().bindService(playIntent, songConnection, Context.BIND_AUTO_CREATE);


            }



            itemView.setOnClickListener(this);


            tv_add = (TextView) itemView.findViewById(R.id.name_song);
            tv_inf = (TextView) itemView.findViewById(R.id.artist);
            bt_song = (ImageButton) itemView.findViewById(R.id.bt_song);

            bt_song.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(itemView.getContext(), bt_song);
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.delete:
                                    songProcess.Delete_Song(itemList.get(getPosition()).getId());
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

            int position = getPosition();
            songPicked((int) itemList.get(position).getId() );
            Intent intent = new Intent(itemView.getContext(), PlaySong.class);
            v.getContext().startActivity(intent);
        }


        public void songPicked(int pos) {
            songService.setStatusOffline();
            songService.setSong(pos);
            songService.playSong();


        }


        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }

    }


}
