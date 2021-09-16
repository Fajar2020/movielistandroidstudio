package com.example.fajartestmovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewListAdapterHolder>{
    private Context context;
    private ArrayList<Review> reviewArrayList;

    public ReviewListAdapter(Context context, ArrayList<Review> reviewArrayList) {
        this.context = context;
        this.reviewArrayList = reviewArrayList;
    }

    @NonNull
    @Override
    public ReviewListAdapter.ReviewListAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.review_item, parent,false);
        return new ReviewListAdapter.ReviewListAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewListAdapter.ReviewListAdapterHolder holder, int position) {
        Review currentReview = reviewArrayList.get(position);
        String reviewAuthor=currentReview.getAuthor();
        String reviewContent=currentReview.getContent();
        holder.reviewAuthor.setText(reviewAuthor);
        holder.reviewContent.setText(reviewContent);
    }

    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }

    public class ReviewListAdapterHolder extends RecyclerView.ViewHolder{
        public LinearLayout linearLayout;
        public TextView reviewAuthor;
        public TextView reviewContent;

        public ReviewListAdapterHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.ll_review);
            reviewAuthor=itemView.findViewById(R.id.reviewAuthor);
            reviewContent=itemView.findViewById(R.id.reviewContent);
        }
    }
}
