package com.example.fajartestmovie;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListAdapterHolder> {
    private Context context;
    private ArrayList<MovieListItem> movieListItemArrayList;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    public MovieListAdapter(Context context, ArrayList<MovieListItem> movieListItemArrayList) {
        this.context = context;
        this.movieListItemArrayList = movieListItemArrayList;
        Log.i("TEST", ""+movieListItemArrayList.size());
    }

    @NonNull
    @Override
    public MovieListAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.movie_item, parent,false);
        return new MovieListAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListAdapterHolder holder, int position) {
        MovieListItem currentItem=movieListItemArrayList.get(position);
        int id = currentItem.getId();
        String movieName=currentItem.getMovieName();
        String imgUrl="https://image.tmdb.org/t/p/w500"+currentItem.getMoviePosterPath();
        Picasso.get().load(imgUrl).fit().centerInside().into(holder.photoMovie);
        if(id%5==0){
            holder.linearLayout.setBackgroundColor(Color.rgb(255, 230, 230));
        }else if(id%5==1){
            holder.linearLayout.setBackgroundColor(Color.rgb(236, 230, 255));
        }else if(id%5==2){
            holder.linearLayout.setBackgroundColor(Color.rgb(255, 128, 159));
        }else if(id%5==3){
            holder.linearLayout.setBackgroundColor(Color.rgb(77, 255, 255));
        }else{
            holder.linearLayout.setBackgroundColor(Color.rgb(255, 255, 128));
        }
        holder.movieName.setText(movieName);
    }

    @Override
    public int getItemCount() {
        return movieListItemArrayList.size();
    }

    public class MovieListAdapterHolder extends RecyclerView.ViewHolder{
        public LinearLayout linearLayout;
        public TextView movieName;
        public ImageView photoMovie;

        public MovieListAdapterHolder(@NonNull View itemView) {
            super(itemView);
            photoMovie=itemView.findViewById(R.id.photoMovie);
            linearLayout = itemView.findViewById(R.id.ll_movie);
            movieName=itemView.findViewById(R.id.nameMovie);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
