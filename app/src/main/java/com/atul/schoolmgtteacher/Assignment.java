package com.atul.schoolmgtteacher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Assignment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText sunm;
    TextView gdlink;
    Spinner studclass;
    String cls;
    String[] courses = {"Select Class", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    String PdfInBytess;
    //private static final String URL = "http://192.168.43.244:8080/simple_crud/api/inserassignment.php";
    private static final String URL = "https://atulsproject.000webhostapp.com/simple_crud/api/inserassignment.php";
    Button addmtrl, selectpdf;
    private int PDF_REQ = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Assignment");
        setContentView(R.layout.activity_assignment);

        gdlink = findViewById(R.id.drivelink);
        addmtrl = findViewById(R.id.eaddbtn);

        selectpdf = findViewById(R.id.selectpdf);
        selectpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("pdf/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PDF_REQ);
            }
        });

        addmtrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMaterilm();
            }
        });

        studclass = findViewById(R.id.eclass);
        studclass.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, courses);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studclass.setAdapter(ad);
    }

    private void addMaterilm() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dailog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        sunm = findViewById(R.id.esname);


        final String a_class = cls;
        final String a_subname = sunm.getText().toString().trim();

        if (a_class.isEmpty() || a_subname.isEmpty()) {

            Toast.makeText(getApplicationContext(), "Some Field Empty", Toast.LENGTH_SHORT).show();

        } else {

            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    studclass.setSelection(0);
                    sunm.setText("");
                    gdlink.setText("No Document Selected");
                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put("clss", a_class);
                    param.put("subjectnm", a_subname);
                    param.put("gdrlink", PdfInBytess);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        gdlink.setText("Document Selected");

        if (requestCode == PDF_REQ && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                InputStream inputStream = Assignment.this.getContentResolver().openInputStream(path);
                byte[] pdfInBytess = new byte[inputStream.available()];
                inputStream.read(pdfInBytess);
                PdfInBytess = Base64.encodeToString(pdfInBytess, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}