package com.example.frequentflyer2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.view.View;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity4 extends AppCompatActivity {

    private String pid;
    private Spinner spinnerFlightId;
    private TextView textViewFlightDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        spinnerFlightId = findViewById(R.id.spinnerFlightId);
        textViewFlightDetails = findViewById(R.id.textViewFlightDetails);

        pid = getIntent().getStringExtra("passID");
        String url = "http://10.0.2.2:8080/android/flight.jsp?passid=" + pid;

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] flightsData = response.split("#");

                // Create an array to store the flight IDs
                String[] flightIds = new String[flightsData.length];

                // Iterate over each flight data and extract the flight ID
                for (int i = 0; i < flightsData.length; i++) {
                    String[] flightInfo = flightsData[i].split(",");
                    if (flightInfo.length >= 1) { // Check if flightInfo array has at least 1 element
                        String flightId = flightInfo[0].trim();
                        flightIds[i] = flightId;
                    }
                }

                // Check if any flight IDs were extracted
                if (flightIds.length > 0) {
                    // Create an ArrayAdapter and set it as the spinner adapter
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity4.this,
                            android.R.layout.simple_spinner_item, flightIds);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerFlightId.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity4.this, "No flight data available", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity4.this, "Error occurred while fetching flight data", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);

        spinnerFlightId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedFlightId = parent.getItemAtPosition(position).toString();
                String flightDetailsUrl = "http://10.0.2.2:8080/android/FlightDetails.jsp?flightid=" + selectedFlightId;

                StringRequest flightDetailsRequest = new StringRequest(Request.Method.GET, flightDetailsUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        StringBuilder formattedOutput = new StringBuilder();

                        String[] flightDetails = response.split("#");

                        for (String flightDetail : flightDetails) {
                            String[] flightInfo = flightDetail.split(",");
                            if (flightInfo.length >= 5) { // Check if flightInfo array has at least 5 elements
                                String flightDeptDatetime = flightInfo[0].trim();
                                String flightArrivalDatetime = flightInfo[1].trim();
                                String flightMiles = flightInfo[2].trim();
                                String tripId = flightInfo[3].trim();
                                String tripMiles = flightInfo[4].trim();

                                formattedOutput.append("Flight Departure Datetime: ").append(flightDeptDatetime).append("\n");
                                formattedOutput.append("Flight Arrival Datetime: ").append(flightArrivalDatetime).append("\n");
                                formattedOutput.append("Flight Miles: ").append(flightMiles).append("\n");
                                formattedOutput.append("Trip ID: ").append(tripId).append("\n");
                                formattedOutput.append("Trip Miles: ").append(tripMiles).append("\n\n");
                            }
                        }

                        // Update the TextView with the formatted flight details
                        textViewFlightDetails.setText(formattedOutput.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity4.this, "Error occurred while fetching flight details", Toast.LENGTH_LONG).show();
                    }
                });

                queue.add(flightDetailsRequest);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
}


