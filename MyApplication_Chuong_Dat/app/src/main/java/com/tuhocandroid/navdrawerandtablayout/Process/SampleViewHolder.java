package com.tuhocandroid.navdrawerandtablayout.Process;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuhocandroid.navdrawerandtablayout.Activity.OfflineList;
import com.tuhocandroid.navdrawerandtablayout.R;

/**
 * Created by HODUCVIET on 9/9/2016.
 */
public class SampleViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {
    public ImageView imagev;
    public TextView tv_add;
    public TextView tv_inf;
    private FragmentManager supportFragmentManager;

    public SampleViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        imagev=(ImageView)itemView.findViewById(R.id.iv_offline);
        tv_add=(TextView)itemView.findViewById(R.id.tv_menu_offline);
        tv_inf=(TextView)itemView.findViewById(R.id.tv_count);
    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(itemView.getContext(), OfflineList.class);
        int position = 0;


        switch (getPosition()){
            case 0:
                position=1;
                intent.putExtra("Value",position);
                itemView.getContext().startActivity(intent);
                break;
            case 1:
                position=2;
                intent.putExtra("Value",position);
                itemView.getContext().startActivity(intent);
                break;
            case 2:
                position=3;
                intent.putExtra("Value",position);
                itemView.getContext().startActivity(intent);
                break;
            case 3:

                itemView.getContext().startActivity(intent);
                break;
        }

    }



    public FragmentManager getSupportFragmentManager() {
        return supportFragmentManager;
    }
}
