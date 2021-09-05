package com.musplayer.utils;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

public class GlobalPlayer {

    private static String uri;
    private static MediaPlayer mp;
    private static boolean playing_now = false;
    private static boolean stopped = true;

    public static boolean isPlaying_now() {
        return playing_now;
    }

    public static void setPlaying_now(boolean playing_now) {
        GlobalPlayer.playing_now = playing_now;
    }

    public static boolean isStopped() {
        return stopped;
    }

    public static void setStopped(boolean stopped) {
        GlobalPlayer.stopped = stopped;
    }

    public static String getUri() {
        return uri;
    }

    public static void setUri(String uri) {
        GlobalPlayer.uri = uri;
    }

    public static MediaPlayer getMp() {
        return mp;
    }

    public static void mediaPlayerInit(){
        try {
            mp = new MediaPlayer();
            mp.setDataSource(uri);
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void playSong(){
        mp.start();
    }

    public static void pauseSong(){
        mp.pause();
    }

    public static void stopSong(){
        mp.stop();
    }

}
