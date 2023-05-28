package com.example.frequentflyer2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity2 extends AppCompatActivity {

    private String pid;
    private TextView textViewName;
    private TextView textViewPoints;
    private TextView textTotalPoints;
    private ImageView passImageView;
    private Button btAllFlights;
    private Button btFlightDetails;
    private Button btRedemptInfo;
    private Button btTransferPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textViewName = findViewById(R.id.textViewName);
        textViewPoints = findViewById(R.id.textViewPoints);
        textTotalPoints = findViewById(R.id.textTotalPoints);
        passImageView = findViewById(R.id.passImageView);
        btAllFlights = findViewById(R.id.btAllFlights);
        btFlightDetails = findViewById(R.id.btFlightDetails);
        btRedemptInfo = findViewById(R.id.btRedemptInfo);
        btTransferPoints = findViewById(R.id.btTransferPoints);

        pid = getIntent().getStringExtra("passID");
        String url = "http://10.0.2.2:8080/android/info.jsp?passid=" + pid;

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] data = response.split(",");
                if (data.length >= 2) { // Check if data array has at least 2 elements
                    String name = data[0].trim();
                    String points = data[1].trim();

                    textViewName.setText(name);
                    textViewPoints.setText(points);
                    textTotalPoints.setText("Reward Points");

                    loadPassengerImage();
                } else {
                    Toast.makeText(MainActivity2.this, "Invalid response from server", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity2.this, "Error occurred while fetching data", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);

        btAllFlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity3();
            }
        });

        btFlightDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity4();
            }
        });

        btRedemptInfo.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                openMainActivity5();
            }
        });

        btTransferPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity6();
            }
        });
    }

    private void loadPassengerImage() {
        String imageUrl = "http://10.0.2.2:8080/android/" + pid + ".jpg";
        ImageRequest imageRequest = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                passImageView.setImageBitmap(bitmap);
            }
        }, 0, 0, null, null);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(imageRequest);
    }

    private void openMainActivity3() {
        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
        intent.putExtra("passID", pid); // Pass the passenger ID to MainActivity3
        startActivity(intent);
    }

    private void openMainActivity4() {
        Intent intent = new Intent(MainActivity2.this, MainActivity4.class);
        intent.putExtra("passID", pid); // Pass the passenger ID to MainActivity3
        startActivity(intent);
    }

    private void openMainActivity5() {
        Intent intent = new Intent(MainActivity2.this, MainActivity5.class);
        intent.putExtra("passID", pid); // Pass the passenger ID to MainActivity3
        startActivity(intent);
    }

    private void openMainActivity6() {
        Intent intent = new Intent(MainActivity2.this, MainActivity6.class);
        intent.putExtra("passID", pid); // Pass the passenger ID to MainActivity3
        startActivity(intent);
    }
}

