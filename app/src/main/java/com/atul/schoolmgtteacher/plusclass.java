package com.atul.schoolmgtteacher;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class plusclass extends AppCompatActivity {

    Button incrsbtn;
    //private static final String URL = "http://192.168.43.244:8080/simple_crud/api/cngcls.php";
    private static final String URL = "https://atulsproject.000webhostapp.com/simple_crud/api/cngcls.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Change Class");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_plusclass);

        incrsbtn = findViewById(R.id.btnplus);
        incrsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrclss();
            }
        });
    }

    private void incrclss() {
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
        Toast.makeText(getApplicationContext(), "Class Incresed Successfully", Toast.LENGTH_LONG).show();

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}