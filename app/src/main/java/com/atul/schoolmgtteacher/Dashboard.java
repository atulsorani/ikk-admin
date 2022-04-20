package com.atul.schoolmgtteacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Dashboard extends AppCompatActivity {
    TextView name,designn;
    public static String numb;
    //private static final String URL = "http://192.168.43.244:8080/simple_crud/api/getadmin.php";
    private static final String URL = "https://atulsproject.000webhostapp.com/simple_crud/api/getadmin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dashboard);

        LoadName();

        Intent intent = getIntent();
        numb = intent.getStringExtra("usernmmm");

        if (numb.equals("12345")){
            View profiles = findViewById(R.id.profile);
            profiles.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Only For Teachers", Toast.LENGTH_SHORT).show();
                }
            });

            View pclass = findViewById(R.id.plsclass);
            pclass.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Dashboard.this, plusclass.class);
                    startActivity(intent);
                }
            });

            View addstud = findViewById(R.id.addstudent);
            addstud.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    /**/
                    PopupMenu popupMenu = new PopupMenu(Dashboard.this,addstud);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_manu,popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int id = item.getItemId();
                            if (id == R.id.studadd){
                                Intent intent = new Intent(Dashboard.this, add_student.class);
                                startActivity(intent);
                            }else if (id == R.id.teaadd){
                                Intent intent = new Intent(Dashboard.this, add_teacher.class);
                                startActivity(intent);
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }
            });

        }else{
            View pclass = findViewById(R.id.plsclass);
            pclass.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Only Admin Can Change The Class", Toast.LENGTH_SHORT).show();
                }
            });

            View profiles = findViewById(R.id.profile);
            profiles.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Dashboard.this, Profile.class);
                    startActivity(intent);
                }
            });

            View addstud = findViewById(R.id.addstudent);
            addstud.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    /**/
                    PopupMenu popupMenu = new PopupMenu(Dashboard.this,addstud);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_manu,popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int id = item.getItemId();
                            if (id == R.id.studadd){
                                Intent intent = new Intent(Dashboard.this, add_student.class);
                                startActivity(intent);
                            }else if (id == R.id.teaadd){
                                Toast.makeText(getApplicationContext(), "Only Admin Can Add The Teacher", Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }
            });

        }

        View logot = findViewById(R.id.logout);
        logot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences(logout.PREFS_NAME, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("hasLoggedIn");
                editor.remove("usernm");
                editor.commit();

                Intent intent = new Intent(Dashboard.this, logout.class);
                startActivity(intent);
                finish();
            }
        });



        View gsugg = findViewById(R.id.suggesition);
        gsugg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Suggestion.class);
                startActivity(intent);
            }
        });

        View hmwork = findViewById(R.id.homework);
        hmwork.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, HomeWork.class);
                startActivity(intent);
            }
        });

        View asignmnt = findViewById(R.id.assignment);
        asignmnt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Assignment.class);
                startActivity(intent);
            }
        });

        View mtrl = findViewById(R.id.material);
        mtrl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Material.class);
                startActivity(intent);
            }
        });



        View notii = findViewById(R.id.notice);
        notii.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, noticee.class);
                startActivity(intent);
            }
        });

        View quot = findViewById(R.id.quotes);
        quot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, quotess.class);
                startActivity(intent);
            }
        });
    }

    private void LoadName() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject noticeObject = jsonArray.getJSONObject(0);

                    String names = noticeObject.getString("name");
                    String stds = noticeObject.getString("class");

                    name = findViewById(R.id.nm);
                    designn = findViewById(R.id.enr);

                    name.setText(names);
                    designn.setText(stds);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("mobileno", numb);
                return param;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);

        Volley.newRequestQueue(this).add(stringRequest);

    }
}