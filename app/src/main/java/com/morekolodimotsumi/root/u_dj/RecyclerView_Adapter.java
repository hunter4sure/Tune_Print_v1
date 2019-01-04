package com.morekolodimotsumi.root.u_dj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class RecyclerView_Adapter extends RecyclerView.Adapter<ViewHolder> {


    List<Music> list = Collections.emptyList();
    Context context;

    public RecyclerView_Adapter(List<Music> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.title.setText(list.get(position).getTitle());
       Bitmap bmp = BitmapFactory.decodeByteArray(list.get(position).getPath().getBytes(), 0,list.get(position).getPath().length());
       holder.icon.setImageBitmap(bmp);


    }

    @Override
    public int getItemCount() {
         return list.size();
    }


}

 class ViewHolder extends RecyclerView.ViewHolder
{

    TextView title;
    ImageView play_pause;
    ImageView icon;

    public ViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        play_pause = (ImageView) itemView.findViewById(R.id.play_pause);
        icon = (ImageView) itemView.findViewById(R.id.icon);
    }
}
