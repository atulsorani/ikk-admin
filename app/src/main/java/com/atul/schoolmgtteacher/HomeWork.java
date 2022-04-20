package com.atul.schoolmgtteacher;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HomeWork extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    final Calendar myCalendar = Calendar.getInstance();
    EditText er;
    Button adquote;
    EditText cquote;
    Spinner studclass;
    String clsss;
    String[] courses = {"Select Class","1","2","3","4","5","6","7","8","9","10","11","12"};
    //private static final String URL = "http://192.168.43.244:8080/simple_crud/api/inserthomework.php";
    private static final String URL = "https://atulsproject.000webhostapp.com/simple_crud/api/inserthomework.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Homework");
        setContentView(R.layout.activity_home_work);

        adquote = findViewById(R.id.addquotes);
        cquote = findViewById(R.id.edit_quotes);

        studclass = findViewById(R.id.eclass);
        studclass.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, courses);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studclass.setAdapter(ad);

        er = findViewById(R.id.ddate);

        adquote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });


        //Choose he date

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
                new DatePickerDialog(HomeWork.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void insertData() {
        final String date = er.getText().toString().trim();
        final String quo = cquote.getText().toString().trim();
        final String cls = clsss;

        if (date.isEmpty() || quo.isEmpty() || cls.isEmpty()) {

            Toast.makeText(getApplicationContext(), "Some Field Empty", Toast.LENGTH_SHORT).show();

        } else {
            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    er.setText("");
                    cquote.setText("");
                    studclass.setSelection(1);
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
                    param.put("clsno", cls);
                    param.put("date", date);
                    param.put("homework", quo);
                    return param;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(request);
        }
    }

        private void updateLabel () {
            String myFormat = "dd/MM/yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
            er.setText(dateFormat.format(myCalendar.getTime()));
        }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        clsss = courses[position];
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