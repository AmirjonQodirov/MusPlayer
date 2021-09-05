package com.musplayer.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

public class GetSongHelper {

    private static boolean uris_loaded = false;
    private static ArrayList<Song> songs = new ArrayList<>();

    public static boolean isUris_loaded() {
        return uris_loaded;
    }

    public static void setUris_loaded(boolean uris_loaded) {
        GetSongHelper.uris_loaded = uris_loaded;
    }

    public static ArrayList<Song> getSongs() {
        return songs;
    }

    public static void setSongs(ArrayList<Song> songs) {
        GetSongHelper.songs = songs;
    }

    static public void getAllAudioFromDevice(final Context context) {
        songs.clear();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.ArtistColumns.ARTIST,
                MediaStore.Audio.Albums.ALBUM,
//                MediaStore.Audio.Media.DATE_MODIFIED,
        };
        Cursor c = context.getContentResolver().query(uri, projection, null, null, null);

        if (c != null) {
            while (c.moveToNext()) {

                String path = c.getString(0);
                String title = c.getString(1);
                String artist = c.getString(2);
                String album = c.getString(3);

                Song s = new Song();
                s.setUri(path);
                s.setTitle(title);
                s.setArtist(artist);
                s.setAlbum(album);
//                String name = path.substring(path.lastIndexOf("/") + 1);


//                audioModel.setUri(path);

                songs.add(s);
            }
            c.close();
        }

        GetSongHelper.setUris_loaded(true);
    }

}
