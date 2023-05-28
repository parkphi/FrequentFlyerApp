package com.example.frequentflyer2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity6 extends AppCompatActivity {
    private Spinner spinnerPassengerIds;
    private EditText editTextPointsToTransfer;
    private TextView textViewTransferResult;
    private Button buttonTransferPoints;
    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        spinnerPassengerIds = findViewById(R.id.spinnerPassengerIds);
        editTextPointsToTransfer = findViewById(R.id.editTextPointsToTransfer);
        textViewTransferResult = findViewById(R.id.textViewTransferResult);
        buttonTransferPoints = findViewById(R.id.buttonTransferPoints);

        // Retrieve the pid value from the previous activity
        pid = getIntent().getStringExtra("passID");

        // Create a RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Define the URL for retrieving passenger IDs
        String passengerIdsUrl = "http://10.0.2.2:8080/android/GetPassengerIds.jsp?passid= "+ pid;

        // Send a GET request to retrieve the passenger IDs
        StringRequest passengerIdsRequest = new StringRequest(Request.Method.GET, passengerIdsUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Split the response string by "#" to get individual passenger IDs
                        String[] passengerIds = response.split("#");

                        // Create an ArrayAdapter and set it as the spinner adapter
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity6.this,
                                android.R.layout.simple_spinner_item, passengerIds);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerPassengerIds.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity6.this, "Error occurred while fetching passenger IDs", Toast.LENGTH_LONG).show();
                    }
                });

        // Add the passenger IDs request to the RequestQueue
        queue.add(passengerIdsRequest);

        // Set the item selection listener for the spinner
        spinnerPassengerIds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle passenger ID selection
                String selectedPassengerId = parent.getItemAtPosition(position).toString();
                // TODO: Perform actions based on selected passenger ID
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

// Set the click listener for the "Transfer Points" button
        buttonTransferPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the points to transfer from the input field
                int pointsToTransfer = Integer.parseInt(editTextPointsToTransfer.getText().toString());

                // Get the selected passenger ID from the spinner
                String selectedPassengerId = spinnerPassengerIds.getSelectedItem().toString();

                // Construct the URL for the TransferPoints.jsp page with the selected passenger ID and points to transfer
                String transferPointsUrl = "http://10.0.2.2:8080/android/TransferPoints.jsp?sourcePassengerId="
                        + pid + "&destinationPassengerId=" + selectedPassengerId + "&pointsToTransfer=" + pointsToTransfer;

                // Create a request to transfer the points
                StringRequest transferPointsRequest = new StringRequest(Request.Method.GET, transferPointsUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display a success message in the text view
                                textViewTransferResult.setText("Points transferred successfully");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity6.this, "Error occurred while transferring points", Toast.LENGTH_LONG).show();
                            }
                        });
                // Add the transfer points request to the request queue
                queue.add(transferPointsRequest);
            }
        });
    }
}