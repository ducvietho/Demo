package com.tuhocandroid.navdrawerandtablayout.Process;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tuhocandroid.navdrawerandtablayout.Object.ItemObject;
import com.tuhocandroid.navdrawerandtablayout.R;

import java.util.List;

/**
 * Created by HODUCVIET on 7/29/2016.
 */
public class SampleRecyclerViewAdapter extends RecyclerView.Adapter<SampleViewHolder> {
    private List<ItemObject> itemList;
    Convert convert;
    private Fragment context;
    public SampleRecyclerViewAdapter(Fragment context,
                                     List<ItemObject> itemList)
    {
        this.itemList = itemList;
        this.context = context;
    }
    @Override
    public SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recycle_offline, null);
        SampleViewHolder rcv = new SampleViewHolder(layoutView);
        return rcv;

    }

    @Override
    public void onBindViewHolder(SampleViewHolder holder, int position) {
        holder.imagev.setImageBitmap(convert.ByteArrayToBitmap(itemList.get(position).getImage()));
        holder.tv_add.setText(itemList.get(position).getName());
        holder.tv_inf.setText(String.valueOf(itemList.get(position).getInfor()));


    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
