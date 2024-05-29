package com.example.api.ObjectMapper;

import java.util.List;

public class Library {
    private float serialNumber;
    private int year;
    private String libraryName;
    private List<Music> myMusic;


    public Library(float serialNumber, int year, String libraryName, List<Music> myMusic) {
        this.serialNumber = serialNumber;
        this.year = year;
        this.libraryName = libraryName;
        this.myMusic = myMusic;
    }

    // Getters and setters

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public List<Music> getMyMusic() {
        return myMusic;
    }

    public void setMyMusic(List<Music> myMusic) {
        this.myMusic = myMusic;
    }

    public float getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(float serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

class Music {
    private String artistName;
    private String songName;

    public Music(String artistName, String songName) {
        this.artistName = artistName;
        this.songName = songName;
    }

    // Getters and setters

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
}
