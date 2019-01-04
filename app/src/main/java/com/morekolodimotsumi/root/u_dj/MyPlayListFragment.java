package com.morekolodimotsumi.root.u_dj;


import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPlayListFragment extends Fragment {

    public static final String Broadcast_PLAY_NEW_AUDIO = "com.morekolodimotsumi.root.u_dj.PlayNewAudio";


    Toolbar toolbar;

    private MediaPlayerService player;

    boolean serviceBound = false;
    ArrayList<Music> audioList;

    DjHome djHome;

    View view;

    public MyPlayListFragment() {
        // Required empty public constructor

        djHome = new DjHome();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_my_play_list, container, false);

        djHome = new DjHome();

        audioList = new ArrayList<>();

        loadAudio();

        initRecyclerView();

        return view;
    }

    //Binding this Client to the AudioPlayer Service
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            player = binder.getService();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };
//



    //Load local music files
    public void loadAudio() {

        if (djHome.checkPermissionREAD_EXTERNAL_STORAGE(getContext())) {


            ContentResolver contentResolver = getActivity().getContentResolver();//getContentResolver();

            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

          // Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

            String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
          //  String selection = MediaStore.Audio.Albums._COUNT + "!= 0";

           String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
           // String sortOrder = MediaStore.Audio.Albums.ALBUM + " ASC";


            //String SELEC = MediaStore.Audio.Media.IS_MUSIC + "!= 0";

            // String selectionsortOrder;


            Cursor cursor = contentResolver.query(uri, null, selection, null, sortOrder);


/*
            Cursor c1 = getActivity().getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                    new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                    MediaStore.Audio.Albums._ID+ "=?",
                    null,
                    MediaStore.Audio.Albums._ID);

*/

            if (cursor != null && cursor.getCount() > 0) {
                audioList = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));


                  //  String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));



                    // Save to audioList
                    audioList.add(new Music(data, title, album, artist,path));
                }

            }
            cursor.close();
        }else
            {
                Toast.makeText(getActivity(), " Permission Not Granted..",Toast.LENGTH_LONG).show();
            }
    }


    public void initRecyclerView() {
        if (audioList.size() > 0) {
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
            RecyclerView_Adapter adapter = new RecyclerView_Adapter(audioList, getContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addOnItemTouchListener(new CustomTouchListener(getContext(), new onItemClickListener() {
                @Override
                public void onClick(View view, int index) {

                    playAudio(index);

                }
            }));

        } else {
            Toast.makeText(getContext(), "No Music Files", Toast.LENGTH_LONG).show();
        }
    }

    public void loadimage(int index)
    {
        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID+ "=?",
                new String[] {String.valueOf(index)},
                null);


        if (cursor.moveToFirst()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            // do whatever you need to do
        }
    }

    public void playAudio(int audioIndex) {
        //Check is service is active
        if (!serviceBound) {
            //Store Serializable audioList to SharedPreferences
            StorageUtil storage = new StorageUtil(getContext());
            storage.storeAudio(audioList);
            storage.storeAudioIndex(audioIndex);

            //Toast.makeText(getApplicationContext(), "Playing Music....", Toast.LENGTH_SHORT).show();
            Intent playerIntent = new Intent(getContext(), MediaPlayerService.class);
            getActivity().startService(playerIntent);
            getActivity(). bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);

        } else {
            //Store the new audioIndex to SharedPreferences
            StorageUtil storage = new StorageUtil(getContext());
            storage.storeAudioIndex(audioIndex);

            //Service is active
            //Send a broadcast to the service -> PLAY_NEW_AUDIO
            Intent broadcastIntent = new Intent(Broadcast_PLAY_NEW_AUDIO);
            getActivity().sendBroadcast(broadcastIntent);
        }
    }
}
