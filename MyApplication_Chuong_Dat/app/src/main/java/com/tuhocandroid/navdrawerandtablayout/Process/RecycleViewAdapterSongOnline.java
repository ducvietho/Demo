package com.tuhocandroid.navdrawerandtablayout.Process;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tuhocandroid.navdrawerandtablayout.Activity.CommentSong;
import com.tuhocandroid.navdrawerandtablayout.Activity.ShareFacebookActivity;
import com.tuhocandroid.navdrawerandtablayout.Object.Song;
import com.tuhocandroid.navdrawerandtablayout.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chuong Phung on 11/5/2016.
 */

public class RecycleViewAdapterSongOnline extends RecyclerView.Adapter<VHSongOnline> {
    private List<Song> songList =  new ArrayList<Song>();
    private LayoutInflater inflater;
    private Typeface typeface;


    public interface OnItemClickListener {
        void OnItemClick (int position);
    }

    private OnItemClickListener listener;
    public void setOnItemClickListener (OnItemClickListener listener) {
        this.listener = listener;
    }

    public RecycleViewAdapterSongOnline (Context context, List<Song> songList) {

        this.songList = songList;
        inflater = LayoutInflater.from(context);
        typeface = Typeface.createFromAsset(inflater.getContext().getAssets(), "fonts/LobsterTwo-BoldItalic.otf");
    }

    @Override
    public VHSongOnline onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        v = inflater.inflate(R.layout.row_song, parent, false);

        return new VHSongOnline(v);
    }

    @Override
    public void onBindViewHolder(final VHSongOnline holder, final int position) {

        holder.textViewRanking.setText(String.valueOf(position+1));
        holder.textViewSinger.setText(songList.get(position).getSinger());
        holder.textViewSongName.setText(songList.get(position).getSongName());
        holder.textViewSongName.setTypeface(typeface);
        holder.textViewSongName.setTextColor(Color.parseColor("#000000"));

        holder.textViewSongName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClick(position);
            }
        });
        holder.btnFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(inflater.getContext(), holder.btnFunction);
                popupMenu.getMenuInflater().inflate(R.menu.popupfunctiononline, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().toString().equalsIgnoreCase("Share"))
                        {
                            Intent intentShare = new Intent(inflater.getContext(), ShareFacebookActivity.class);
                            Bundle bundleShare = new Bundle();
                            bundleShare.putSerializable("Song", songList.get(position));
                            intentShare.putExtra("Song", bundleShare);
                            inflater.getContext().startActivity(intentShare);
                        } else if (item.getTitle().toString().equalsIgnoreCase("Download")) {

                            DownloadMusicOnline downloadManager = new DownloadMusicOnline(songList.get(position), inflater.getContext());
                            downloadManager.DowloadSong();
                            downloadManager.pushNotifycation();
                        } else if (item.getTitle().toString().equalsIgnoreCase("Comment")) {
                            Intent intentComment  = new Intent(inflater.getContext(), CommentSong.class);
                            Bundle bundleComment = new Bundle();
                            bundleComment.putSerializable("Song", songList.get(position));
                            intentComment.putExtra("Song", bundleComment);
                            inflater.getContext().startActivity(intentComment);

                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

    }

    public void filter (String filterSong) {

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }
}

class VHSongOnline extends RecyclerView.ViewHolder {

    TextView textViewRanking;
    TextView textViewSongName;
    TextView textViewSinger;
    ImageButton btnFunction;
    public static final String ARRAY_FUNCTION_SPINNER[] = {
            "Download",
            "Share",
            "Comment"
    };


    public VHSongOnline(View itemView) {
        super(itemView);
        textViewRanking = (TextView) itemView.findViewById(R.id.textviewRanking);
        textViewSinger = (TextView) itemView.findViewById(R.id.textviewSinger);
        textViewSongName = (TextView) itemView.findViewById(R.id.textviewSongName);

        btnFunction = (ImageButton) itemView.findViewById(R.id.btn_function);


    }
}
