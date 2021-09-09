package com.musplayer.utils;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

public class GlobalPlayer {

    private static MediaPlayer mp = new MediaPlayer();

    public static boolean isPlayingNow() {
        return mp.isPlaying();
    }

    public static void mediaPlayerInit(String uri) {
        try {
            mp.reset();
            mp.setDataSource(uri);
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int durationSong() {
        return mp.getDuration();
    }

    public static int currentPositionSong() {
        return mp.getCurrentPosition();
    }

    public static void seekToSong(int mSec) {
        mp.seekTo(mSec);
    }

    public static void playSong() {
        mp.start();
    }

    public static void pauseSong() {
        mp.pause();
    }

}
