package com.morekolodimotsumi.root.u_dj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomMusicAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Music> music_list;

    public CustomMusicAdapter(Context context, int layout, ArrayList<Music> music_list) {
        this.context = context;
        this.layout = layout;
        this.music_list = music_list;
    }

    @Override
    public int getCount() {
        return music_list.size();
    }

    @Override
    public Object getItem(int position) {
        return music_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(layout, null);
            viewHolder.songName = convertView.findViewById(R.id.txtName);
            viewHolder.artist = convertView.findViewById(R.id.txtartist);
            viewHolder.previous = convertView.findViewById(R.id.previous);
            viewHolder.next = convertView.findViewById(R.id.next);
            viewHolder.play = convertView.findViewById(R.id.play);
            viewHolder.stop = convertView.findViewById(R.id.stop);


            convertView.setTag(viewHolder);


        }else {

                viewHolder =(ViewHolder) convertView.getTag();
            }

            Music music = music_list.get(position);
            viewHolder.songName.setText(music.getTitle());
            viewHolder.artist.setText(music.getArtist());

        return convertView;
    }

    private class  ViewHolder
    {

         TextView songName, artist;
         ImageView play,next,previous,stop;


    }
}
