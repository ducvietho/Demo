package com.tuhocandroid.navdrawerandtablayout.Process;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tuhocandroid.navdrawerandtablayout.Object.Singer;
import com.tuhocandroid.navdrawerandtablayout.Object.Type;
import com.tuhocandroid.navdrawerandtablayout.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Chuong Phung on 11/4/2016.
 */

public class RecyclerViewAdapterSinger extends RecyclerView.Adapter<VHSinger> {

    List<Singer> singerList = new ArrayList<Singer>();
    LayoutInflater inflater;

    public interface OnClickItemListener {
        void OnItemClick (int position);
    }

    private OnClickItemListener listener;

    public void setOnClickItemListener (OnClickItemListener listener) {
        this.listener = listener;
    }

    public RecyclerViewAdapterSinger (Context context, List<Singer> singerList) {
        this.inflater = LayoutInflater.from(context);
        this.singerList = singerList;
    }

    @Override
    public VHSinger onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = inflater.inflate(R.layout.singer_item, parent, false);

        return new VHSinger(view);
    }

    @Override
    public void onBindViewHolder(VHSinger holder, final int position) {
        holder.textViewSingerName.setText(singerList.get(position).getSingerName());
        Picasso.with(inflater.getContext()).load(singerList.get(position).getUrlPhoto()).placeholder(R.drawable.defaultimage)
                .error(R.drawable.defaultimage).into(holder.imageViewSinger);
        holder.imageViewSinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return singerList.size();
    }
}

class VHSinger extends RecyclerView.ViewHolder {

    CircleImageView imageViewSinger;
    TextView textViewSingerName;

    public VHSinger(View itemView) {
        super(itemView);

        imageViewSinger = (CircleImageView) itemView.findViewById(R.id.imageViewSinger);
        textViewSingerName = (TextView) itemView.findViewById(R.id.textviewSingerName);
    }
}