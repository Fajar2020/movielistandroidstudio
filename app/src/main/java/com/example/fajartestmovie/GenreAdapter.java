package com.example.fajartestmovie;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreAdapterHolder> {
    private Context context;
    private ArrayList<GenreItem> genreList;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    public GenreAdapter(Context context, ArrayList<GenreItem> genreList){
        this.context=context;
        this.genreList=genreList;
    }

    @NonNull
    @Override
    public GenreAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.genre_item, parent, false);
        return new GenreAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreAdapterHolder holder, int position) {
        GenreItem currentItem = genreList.get(position);

        int id=currentItem.getId();
        String genreName=currentItem.getNameGenre();

        if(id%5==0){
            holder.linearLayout.setBackgroundColor(Color.rgb(250,240,230));
        }else if(id%5==1){
            holder.linearLayout.setBackgroundColor(Color.rgb(240,255,240));
        }else if(id%5==2){
            holder.linearLayout.setBackgroundColor(Color.rgb(221,160,221));
        }else if(id%5==3){
            holder.linearLayout.setBackgroundColor(Color.rgb(245,245,245));
        }else{
            holder.linearLayout.setBackgroundColor(Color.rgb(210,180,140));
        }
        holder.tvGenre.setText(genreName);
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public class GenreAdapterHolder extends RecyclerView.ViewHolder{
        public LinearLayout linearLayout;
        public TextView tvGenre;

        public GenreAdapterHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.ll_genre);
            tvGenre=itemView.findViewById(R.id.nameGenre);

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
