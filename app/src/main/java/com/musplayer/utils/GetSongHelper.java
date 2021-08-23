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

    static public ArrayList<String> getAllAudioFromDevice(final Context context) {

        final ArrayList<String> tempAudioList = new ArrayList<>();

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

                tempAudioList.add(path);
            }
            c.close();
        }

        return tempAudioList;
    }

}
