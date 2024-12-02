package org.ravn.calmusic.model;

public class Song {

    private int songId;
    private int trackNumber;
    private String title;
    private int albumId;

    public Song() {
    }

    public Song(int songId, int trackNumber, String title, int albumId) {
        this.songId = songId;
        this.trackNumber = trackNumber;
        this.title = title;
        this.albumId = albumId;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }
}
