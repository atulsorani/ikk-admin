package com.atul.schoolmgtteacher;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Suggestion extends AppCompatActivity {
    //private static final String URL = "http://192.168.43.244:8080/simple_crud/api/getsugge.php";
    private static final String URL = "https://atulsproject.000webhostapp.com/simple_crud/api/getsugge.php";
    List<model_suggestion> suggestionList;
    RecyclerView recyclerView;
    suggestionAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Suggestion");
        setContentView(R.layout.activity_suggestion);

        suggestionList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.qrecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadSugg();
    }

    private void loadSugg() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray qUotes = new JSONArray(response);

                    for (int i = qUotes.length()-1; i >= 0; i--) {
                        JSONObject noticeObject = qUotes.getJSONObject(i);
                        String name = noticeObject.getString("name");
                        String sugg = noticeObject.getString("sugg");


                        model_suggestion suggq = new model_suggestion(name, sugg);
                        suggestionList.add(suggq);
                    }

                    adapter = new suggestionAdapter(Suggestion.this, suggestionList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Suggestion.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}