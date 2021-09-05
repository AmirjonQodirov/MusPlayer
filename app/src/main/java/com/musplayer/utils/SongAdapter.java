package com.musplayer.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.musplayer.R;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyViewHolder>{
    public ArrayList<Song> songs;
    public RecycleViewClickListener listener;
    public Context context;

    public SongAdapter(ArrayList<Song> songs, RecycleViewClickListener listener, Context context) {
        this.listener = listener;
        this.songs = songs;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView song_title, song_author;
        private final ImageView play_song, more;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            song_title = itemView.findViewById(R.id.song_title);
            song_author = itemView.findViewById(R.id.song_author);
            play_song = itemView.findViewById(R.id.song_play);
            more = itemView.findViewById(R.id.song_more);

            itemView.setOnClickListener(this);
            more.setOnClickListener(this);
            play_song.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.song_play){
                listener.onClick(itemView, getAdapterPosition(), 1);
            }else if(v.getId() == R.id.song_more){
                listener.onClick(itemView, getAdapterPosition(), 2);
            }else{
                listener.onClick(itemView, getAdapterPosition(), 0);
            }
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item,parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.song_title.setText(songs.get(position).getTitle());
        holder.song_author.setText(songs.get(position).getArtist());

    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public interface RecycleViewClickListener{
        void onClick(View view, int position, int type);
    }

}