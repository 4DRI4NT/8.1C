package com.example.a81c.data;

import java.util.List;

public class User {

    private String fullname, username, password, playlistList;

    public User(String fullname, String username, String password, String playlistList) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.playlistList = playlistList;
    }

    //getters
    public String getFullname() {
        return fullname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPlaylistList() {
        return playlistList;
    }

    //setters
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPlaylist(String playlistList) {
        this.playlistList = playlistList;
    }
}
