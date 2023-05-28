package com.example.frequentflyer2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity5 extends AppCompatActivity {
    private String pid;
    private Spinner spinnerAwardId;
    private TextView textViewRedemptionDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        spinnerAwardId = findViewById(R.id.spinnerAwardId);
        textViewRedemptionDetails = findViewById(R.id.textViewRedemptionDetails);
        pid = getIntent().getStringExtra("passID");
        String awardIdsUrl = "http://10.0.2.2:8080/android/AwardIds.jsp?passid=" + pid;

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest awardIdsRequest = new StringRequest(Request.Method.GET, awardIdsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] awardIds = response.split("#");

                // Check if any award IDs were extracted
                if (awardIds.length > 0) {
                    // Create an ArrayAdapter and set it as the spinner adapter
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity5.this,
                            android.R.layout.simple_spinner_item, awardIds);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerAwardId.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity5.this, "No award IDs available", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity5.this, "Error occurred while fetching award IDs", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(awardIdsRequest);

        spinnerAwardId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedAwardId = parent.getItemAtPosition(position).toString();
                String redemptionDetailsUrl = "http://10.0.2.2:8080/android/RedemptionDetails.jsp?awardid=" + selectedAwardId + "&passid=" + pid;

                StringRequest redemptionDetailsRequest = new StringRequest(Request.Method.GET, redemptionDetailsUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Split the response string by "#" to get individual redemption details
                        String[] redemptionDetails = response.split("#");

                        // Create a StringBuilder to store the formatted redemption details
                        StringBuilder formattedOutput = new StringBuilder();

                        // Iterate over each redemption detail
                        for (String redemptionDetail : redemptionDetails) {
                            // Split the redemption detail by "," to get individual fields
                            String[] fields = redemptionDetail.split(",");

                            // Check if the fields array has at least 4 elements
                            if (fields.length >= 4) {
                                String awardDescription = fields[0].trim();
                                String pointsNeeded = fields[1].trim();
                                String redemptionDates = fields[2].trim();
                                String exchangeCenterName = fields[3].trim();

                                // Append the formatted redemption details to the StringBuilder
                                formattedOutput.append("Award Description: ").append(awardDescription).append("\n");
                                formattedOutput.append("Points Needed: ").append(pointsNeeded).append("\n");
                                formattedOutput.append("Redemption Dates: ").append(redemptionDates).append("\n");
                                formattedOutput.append("Exchange Center Name: ").append(exchangeCenterName).append("\n\n");
                            }
                        }

                        // Update the TextView with the formatted redemption details
                        textViewRedemptionDetails.setText(formattedOutput.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity5.this, "Error occurred while fetching redemption details", Toast.LENGTH_LONG).show();
                    }
                });

                queue.add(redemptionDetailsRequest);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
}
