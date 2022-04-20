package com.atul.schoolmgtteacher;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class noticee extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    EditText er;
    EditText title;
    EditText notice;
    Button addnoti;
    //private static final String URL = "http://192.168.43.244:8080/simple_crud/api/insertnotice.php";
    private static final String URL = "https://atulsproject.000webhostapp.com/simple_crud/api/insertnotice.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Notice");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_noticee);

        title = findViewById(R.id.ntitle);
        er = findViewById(R.id.ddate);
        notice = findViewById(R.id.noti);
        addnoti = findViewById(R.id.caddnoti);

        addnoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserNotice();
            }
        });


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };


        er.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(noticee.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        er.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void inserNotice() {
        final String ftitle = title.getText().toString().trim();
        final String fdate = er.getText().toString().trim();
        final String fnotice = notice.getText().toString().trim();

        if (ftitle.isEmpty() || fdate.isEmpty() || fnotice.isEmpty()) {

            Toast.makeText(getApplicationContext(), "Some Field Empty", Toast.LENGTH_SHORT).show();

        } else {
            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    title.setText("");
                    er.setText("");
                    notice.setText("");
                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

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
                    param.put("title", ftitle);
                    param.put("date", fdate);
                    param.put("description", fnotice);
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