package com.musplayer.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GetSongHelper {

    private static boolean uris_loaded = false;
    private static ArrayList<String> uris = new ArrayList<>();

    public static boolean isUris_loaded() {
        return uris_loaded;
    }

    public static void setUris_loaded(boolean uris_loaded) {
        GetSongHelper.uris_loaded = uris_loaded;
    }

    public static ArrayList<String> getUris() {
        return uris;
    }

    public static void setUris(ArrayList<String> uris) {
        GetSongHelper.uris = uris;
    }

    static public void getAllAudioFromDevice(final Context context) {
        uris.clear();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {
                MediaStore.Audio.AudioColumns.DATA
        };
        Cursor c = context.getContentResolver().query(uri, projection, null, null, null);

        if (c != null) {
            while (c.moveToNext()) {

//                Song audioModel = new Song();
                String path = c.getString(0);

//                String name = path.substring(path.lastIndexOf("/") + 1);

//                audioModel.setUri(path);

                uris.add(path);
            }
            c.close();
        }

        GetSongHelper.setUris_loaded(true);
    }

}
