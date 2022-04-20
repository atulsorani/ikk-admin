package com.atul.schoolmgtteacher;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

public class add_teacher extends AppCompatActivity {
    EditText teacname, teacdesig, teaccity, teacmobile, teapwd;
    Button addteacher;
    //private static final String URL = "http://192.168.43.244:8080/simple_crud/api/insetteacher.php";
    private static final String URL = "https://atulsproject.000webhostapp.com/simple_crud/api/insetteacher.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Teacher");
        setContentView(R.layout.activity_add_teacher);

        addteacher = findViewById(R.id.addtea);

        teacname = findViewById(R.id.teanm);
        teacdesig = findViewById(R.id.tdesg);
        teaccity = findViewById(R.id.tct);
        teacmobile = findViewById(R.id.tmbo);
        teapwd = findViewById(R.id.tpwd);

        addteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertTeacherData();
            }
        });
    }

    private void InsertTeacherData() {


        final String a_studname = teacname.getText().toString().trim();
        final String a_studclass = teacdesig.getText().toString().trim();
        final String a_studcity = teaccity.getText().toString().trim();
        final String a_studmobile = teacmobile.getText().toString().trim();
        final String a_studpmobile = teapwd.getText().toString().trim();

        if (a_studname.isEmpty() || a_studclass.isEmpty() || a_studcity.isEmpty() || a_studmobile.isEmpty() || a_studpmobile.isEmpty()){

            Toast.makeText(getApplicationContext(), "Some Field Empty", Toast.LENGTH_SHORT).show();

        }else {
            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    teacname.setText("");
                    teacdesig.setText("");
                    teaccity.setText("");
                    teacmobile.setText("");
                    teapwd.setText("");

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
                    param.put("desig", a_studclass);
                    param.put("city", a_studcity);
                    param.put("mobileno", a_studmobile);
                    param.put("pwd", a_studpmobile);

                    return param;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(request);

        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}