package com.atul.schoolmgtteacher;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class add_student extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText studname, studcity, studmobile, studpmobile;
    Button addxyzstudent;
    Spinner studclass;
    String cls;
    //private static final String URL = "http://192.168.43.244:8080/simple_crud/api/insertstudent.php";
    private static final String URL = "https://atulsproject.000webhostapp.com/simple_crud/api/insertstudent.php";
    CheckBox chbox;

    String[] courses = {"Select Class", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Student");
        setContentView(R.layout.activity_add_student);

        addxyzstudent = findViewById(R.id.submitstud);
        studname = findViewById(R.id.studnm);
        studclass = findViewById(R.id.studcls);

        studclass.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, courses);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studclass.setAdapter(ad);

        studcity = findViewById(R.id.studct);
        studmobile = findViewById(R.id.studmbo);
        studpmobile = findViewById(R.id.studpm);

        chbox = findViewById(R.id.mchbox);
        chbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studpmobile.setText(studmobile.getText().toString().trim());
            }
        });

        addxyzstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertStudentData();
            }
        });


    }

    private void InsertStudentData() {


        final String a_studname = studname.getText().toString().trim();
        final String a_studclass = cls;
        final String a_studcity = studcity.getText().toString().trim();
        final String a_studmobile = studmobile.getText().toString().trim();
        final String a_studpmobile = studpmobile.getText().toString().trim();
        if (a_studname.isEmpty() || a_studclass.isEmpty() || a_studcity.isEmpty() || a_studmobile.isEmpty() || a_studpmobile.isEmpty()) {

            Toast.makeText(getApplicationContext(), "Some Field Empty", Toast.LENGTH_SHORT).show();

        } else {

            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    studname.setText("");
                    studclass.setSelection(0);
                    studcity.setText("");
                    studmobile.setText("");
                    studpmobile.setText("");

                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("name", a_studname);
                    param.put("class", a_studclass);
                    param.put("city", a_studcity);
                    param.put("mobileno", a_studmobile);
                    param.put("perentsmbo", a_studpmobile);

                    return param;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(request);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cls = courses[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}