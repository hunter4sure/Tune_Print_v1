package com.morekolodimotsumi.root.u_dj;

import java.io.Serializable;

public class Music  implements Serializable {

    private String data;
    private String title;
    private String album;
    private String artist;
    private String path ;


    public Music(String data, String title, String album, String artist, String path) {
        this.data = data;
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.path = path;
    }

    public Music() {
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
