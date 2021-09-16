package com.example.fajartestmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.Date;

public class MovieListActivity extends AppCompatActivity implements MovieListAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private MovieListAdapter movieListAdapter;
    private ArrayList<MovieListItem> movieListItemArrayList;
    private RequestQueue requestQueue;
    private int genreID;
    private boolean canLoadMore=true;
    private int page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        genreID=intent.getIntExtra("ID", 0);
        setContentView(R.layout.activity_movie_list);

        recyclerView=findViewById(R.id.recycler_view_movies);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieListItemArrayList = new ArrayList<>();
        requestQueue= Volley.newRequestQueue(this);

        parseJSON();
    }

    private void parseJSON(){
        String key=""; //api key
        String url="https://api.themoviedb.org/3/discover/movie?api_key="+key+"&with_genres="+genreID+"&page="+page;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getInt("total_pages")>=page){
                        final JSONArray jsonArray=response.getJSONArray("results");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject genre=jsonArray.getJSONObject(i);
                            int movieID=genre.getInt("id");
                            String movieName = genre.getString("title");
                            String movieDesc=genre.getString("overview");
                            String moviePoster=genre.getString("backdrop_path");
                            String releaseDate=genre.getString("release_date");
                            String vote_average="Vote Score : "+genre.getDouble("vote_average");
//                            String keyVideo = parseVideo(movieID);
                            movieListItemArrayList.add(new MovieListItem(movieID, movieName, movieDesc, releaseDate, moviePoster, vote_average));
                        }
                        movieListAdapter = new MovieListAdapter(MovieListActivity.this, movieListItemArrayList);
                        recyclerView.setAdapter(movieListAdapter);
                        movieListAdapter.setOnItemClickListener(MovieListActivity.this);

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
                    parseJSON();
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

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        MovieListItem choosenMovie=movieListItemArrayList.get(position);
        Log.i("TEST", "choosenMovie.getId() "+ choosenMovie.getId());
        detailIntent.putExtra("ID", choosenMovie.getId());
        detailIntent.putExtra("NAME", choosenMovie.getMovieName());
        detailIntent.putExtra("IMAGE", choosenMovie.getMoviePosterPath());
        detailIntent.putExtra("OVERVIEW", choosenMovie.getMovieDesc());
        detailIntent.putExtra("DATE", choosenMovie.getReleaseDate());
        detailIntent.putExtra("VOTE", choosenMovie.getVote_average());
//        detailIntent.putExtra("VIDEO", choosenMovie.getKeyVideo());
        startActivity(detailIntent);
    }



}
