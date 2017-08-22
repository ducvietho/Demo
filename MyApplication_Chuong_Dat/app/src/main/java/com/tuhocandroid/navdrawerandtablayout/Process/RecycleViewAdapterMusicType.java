package com.tuhocandroid.navdrawerandtablayout.Process;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tuhocandroid.navdrawerandtablayout.Object.Type;
import com.tuhocandroid.navdrawerandtablayout.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chuong Phung on 11/4/2016.
 */

public class RecycleViewAdapterMusicType extends RecyclerView.Adapter<VHMusicType> {

    List<Type> typeList = new ArrayList<Type>();
    LayoutInflater inflater;

    public interface OnClickItemListener {
        void OnItemClick (int position);
    }

    private OnClickItemListener listener;

    public void setOnClickItemListener (OnClickItemListener listener) {
        this.listener = listener;
    }

    public RecycleViewAdapterMusicType (Context context, List<Type> typeList) {
        this.inflater = LayoutInflater.from(context);
        this.typeList = typeList;
    }

    @Override
    public VHMusicType onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = inflater.inflate(R.layout.type_music, parent, false);

        return new VHMusicType(view);
    }

    @Override
    public void onBindViewHolder(VHMusicType holder, final int position) {
        holder.textViewNameType.setText(typeList.get(position).getTypeName());
        Picasso.with(inflater.getContext()).load(typeList.get(position).getUrlImage()).placeholder(R.drawable.defaultimage)
                .error(R.drawable.defaultimage).into(holder.imageViewTypeMusic);
        holder.imageViewTypeMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }
}

class VHMusicType extends RecyclerView.ViewHolder {

    ImageView imageViewTypeMusic;
    TextView textViewNameType;

    public VHMusicType(View itemView) {
        super(itemView);

        imageViewTypeMusic = (ImageView) itemView.findViewById(R.id.imageViewMusicType);
        textViewNameType = (TextView) itemView.findViewById(R.id.textviewNameType);
    }
}