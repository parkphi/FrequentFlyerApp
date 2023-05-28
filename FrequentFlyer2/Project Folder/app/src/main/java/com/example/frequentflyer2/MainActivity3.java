package com.example.frequentflyer2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity3 extends AppCompatActivity {

    private String pid;
    private TextView textViewFlightDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        textViewFlightDetails = findViewById(R.id.textViewTitle);

        pid = getIntent().getStringExtra("passID");
        String url = "http://10.0.2.2:8080/android/flight.jsp?passid=" + pid;

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] flightsData = response.split("#");

                // Create a StringBuilder to store the flight details
                StringBuilder flightDetails = new StringBuilder();

                // Iterate over each flight data and extract the flight details
                for (String flightData : flightsData) {
                    String[] flightInfo = flightData.split(",");
                    if (flightInfo.length >= 3) { // Check if flightInfo array has at least 3 elements
                        String flightId = flightInfo[0].trim();
                        String flightMiles = flightInfo[1].trim();
                        String destination = flightInfo[2].trim();

                        // Append the flight details to the StringBuilder
                        flightDetails.append("Flight ID: ").append(flightId).append("\n");
                        flightDetails.append("Flight Miles: ").append(flightMiles).append("\n");
                        flightDetails.append("Destination: ").append(destination).append("\n\n");
                    }
                }

                // Check if any flight details were extracted
                if (flightDetails.length() > 0) {
                    // Display the flight details in the TextView
                    textViewFlightDetails.setText(flightDetails.toString());
                } else {
                    Toast.makeText(MainActivity3.this, "No flight data available", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity3.this, "Error occurred while fetching flight data", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    }
}
