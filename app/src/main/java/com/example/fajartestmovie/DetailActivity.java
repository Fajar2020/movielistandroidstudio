package com.example.fajartestmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity{
    YouTubePlayerView youTubePlayerView;
    CollapsingToolbarLayout collapsingToolbarLayout;
    int movieID;
    String imageBackdrop, title, overview, releaseDate, vote_average="", keyVideo="";
    ImageView imageViewBackdrop;
    TextView textViewReleaseDate, textViewDesc, textViewVote;
    int page=1;
    boolean canLoadMore=true;


    private RecyclerView recyclerView;
    private ReviewListAdapter reviewAdapter;
    private ArrayList<Review> reviewArrayList;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        collapsingToolbarLayout=findViewById(R.id.collapsingToolbar);

        textViewDesc=findViewById(R.id.movieDesc);
        imageViewBackdrop = findViewById(R.id.imgBackdrop);
        textViewReleaseDate=findViewById(R.id.releaseDate);
        textViewVote=findViewById(R.id.vote_average);

        Intent intent = getIntent();
        movieID=intent.getIntExtra("ID", 0);
        title=intent.getStringExtra("NAME");
        overview=intent.getStringExtra("OVERVIEW");
        releaseDate="Release Date : "+intent.getStringExtra("DATE");
        vote_average=intent.getStringExtra("VOTE");
//        keyVideo=intent.getStringExtra("VIDEO");
        imageBackdrop="https://image.tmdb.org/t/p/w500"+intent.getStringExtra("IMAGE");

        requestQueue= Volley.newRequestQueue(this);

        parseVideo();


        Picasso.get().load(imageBackdrop).fit().centerInside().into(imageViewBackdrop);
        collapsingToolbarLayout.setTitle(title);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.rgb(255, 255, 255));
        collapsingToolbarLayout.setExpandedTitleTextColor(ColorStateList.valueOf(Color.WHITE));
        textViewDesc.setText(overview);
        textViewReleaseDate.setText(releaseDate);
        textViewVote.setText(vote_average);
        youTubePlayerView = findViewById(R.id.youtube_player_view);

        recyclerView=findViewById(R.id.recycler_view_reviewers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reviewArrayList = new ArrayList<>();
        
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                if(keyVideo.length()==0){
                    keyVideo="S0Q4gqBUs7c";
                }
                youTubePlayer.loadVideo(keyVideo, 3);
            }
        });

        parseReviewJSON();
    }

    private void parseReviewJSON(){
        String key="";
        String url="https://api.themoviedb.org/3/movie/"+movieID+"/reviews?api_key="+key+"&page="+page;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getInt("total_pages")>=page){
                        final JSONArray jsonArray=response.getJSONArray("results");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject genre=jsonArray.getJSONObject(i);
                            String reviewAuthor = genre.getString("author");
                            String reviewContent =genre.getString("content");
                            reviewArrayList.add(new Review(reviewAuthor, reviewContent));
                        }

                        reviewAdapter = new ReviewListAdapter(DetailActivity.this, reviewArrayList);
                        recyclerView.setAdapter(reviewAdapter);
                        recyclerView.addOnScrollListener(prOnScrollListener);
                    }
                    if(response.getInt("total_pages")<=page) {
                        canLoadMore = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    private RecyclerView.OnScrollListener prOnScrollListener=new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if(isLastItemDisplaying(recyclerView)){
                Log.i("TEST", "LoadMore");
                if(canLoadMore){
                    page++;
                    parseReviewJSON();
                }

            }
        }
    };

    private boolean isLastItemDisplaying(RecyclerView recyclerView){
        if(recyclerView.getAdapter().getItemCount()!=0){
            int lastVisibleItemPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if(lastVisibleItemPosition != RecyclerView.NO_POSITION
                    && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount()-1
            ) {
                return true;
            }
        }
        return false;
    }

    private void parseVideo(){

        String url="https://api.themoviedb.org/3/movie/"+movieID+"/videos?api_key=d41a8c934a142d92690eb5b05a01e389";
//        String keyvideo="S0Q4gqBUs7c";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("results");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject videoObject=jsonArray.getJSONObject(i);
                        if(videoObject.getString("type").equalsIgnoreCase("Trailer")){
                            keyVideo= videoObject.getString("key");
                            break;
                        }else{
                            keyVideo= videoObject.getString("key");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }


}
