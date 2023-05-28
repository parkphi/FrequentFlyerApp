package com.example.frequentflyer2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editTextU = findViewById(R.id.editTextU);
        EditText editTextP = findViewById(R.id.editTextP);
        Button button = findViewById(R.id.buttonLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String user = editTextU.getText().toString();
                String pass = editTextP.getText().toString();
                String url = "http://10.0.2.2:8080/android/login?user=" + user + "&pass=" + pass;
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String result = s.trim();
                        if (result.contains("Yes")) {
                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                            String[] auth = result.split(":");
                            if (auth.length >= 2) { // Check if auth array has at least 2 elements
                                String pid = auth[1];
                                intent.putExtra("passID", pid);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Invalid response from server", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Incorrect Username/Password", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
                queue.add(request);
            }
        });
    }
}