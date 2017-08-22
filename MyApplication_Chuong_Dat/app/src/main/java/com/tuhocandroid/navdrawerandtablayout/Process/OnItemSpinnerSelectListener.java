package com.tuhocandroid.navdrawerandtablayout.Process;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.tuhocandroid.navdrawerandtablayout.Object.Song;

import java.util.Arrays;

/**
 * Created by Chuong Phung on 11/10/2016.
 */
public class OnItemSpinnerSelectListener implements AdapterView.OnItemSelectedListener {
    private Context context;
    private Song song;

    public OnItemSpinnerSelectListener (Context context, Song song) {
        this.context = context;
        this.song = song;

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choose_function = VHSongOnline.ARRAY_FUNCTION_SPINNER[i];
        if (choose_function.equalsIgnoreCase("Share")) {
            Toast.makeText(context, "Share Facebook", Toast.LENGTH_LONG).show();
        } else if (choose_function.equalsIgnoreCase("Download")) {

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
