package com.atul.schoolmgtteacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class logout extends AppCompatActivity {

    EditText usernm,userpwd;
    Button login;
    public  static  String lusernm,lpass;
    public static String PREFS_NAME = "MyPrefsFilee";
    //private static final String URL = "http://192.168.43.244:8080/simple_crud/api/loginadmin.php";
    private static final String URL = "https://atulsproject.000webhostapp.com/simple_crud/api/loginadmin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Log In");
        setContentView(R.layout.activity_logout);

        login = findViewById(R.id.chlogin);
        usernm = findViewById(R.id.usernm);
        userpwd = findViewById(R.id.userpwd);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lusernm = usernm.getText().toString().trim();
                lpass = userpwd.getText().toString().trim();

                if (lusernm.isEmpty() || lpass.isEmpty()) {
                    Toast.makeText(logout.this, "Enter Username or Password", Toast.LENGTH_SHORT).show();
                } else {

                    starLogin();
                }
            }
        });
    }
    private void starLogin() {



        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("1")) {

                    SharedPreferences sharedPreferences = getSharedPreferences(logout.PREFS_NAME, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("hasLoggedIn", true);
                    editor.putString("usernm", lusernm);
                    String tempnm = sharedPreferences.getString("usernm", lusernm);
                    editor.commit();

                    try {
                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        intent.putExtra("usernmmm", lusernm);
                        startActivity(intent);
                        finish();

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Username or Password not match !", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("mobileno", lusernm);
                param.put("passw", lpass);
                return param;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}