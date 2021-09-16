package com.example.fajartestmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GenreAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private GenreAdapter genreAdapter;
    private ArrayList<GenreItem> genreItemArrayList;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        genreItemArrayList = new ArrayList<>();
        requestQueue= Volley.newRequestQueue(this);

        parseJSON();
    }

    private void parseJSON(){
        String key=""; //api key
        String url="https://api.themoviedb.org/3/genre/movie/list?api_key="+key+"&language=en-US";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("genres");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject genre=jsonArray.getJSONObject(i);
                        String genreName = genre.getString("name");
                        int genreID=genre.getInt("id");

                        genreItemArrayList.add(new GenreItem(genreID, genreName));
                    }

                    genreAdapter=new GenreAdapter(MainActivity.this, genreItemArrayList);
                    recyclerView.setAdapter(genreAdapter);
                    genreAdapter.setOnItemClickListener(MainActivity.this);
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

    @Override
    public void onItemClick(int position) {
        Intent movieListIntent = new Intent(this, MovieListActivity.class);
        GenreItem choosenGenre=genreItemArrayList.get(position);
        Log.i("TEST", "choosenGenre.getId() "+ choosenGenre.getId());
        movieListIntent.putExtra("ID", choosenGenre.getId());
        startActivity(movieListIntent);
    }
}
