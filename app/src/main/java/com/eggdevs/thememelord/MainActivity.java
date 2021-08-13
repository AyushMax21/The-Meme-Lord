package com.eggdevs.thememelord;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

      final TextView textView = (TextView) findViewById(R.id.text);
      Button button = findViewById(R.id.button);
      button.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View view) {

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            String url = "https://meme-api.herokuapp.com/gimme";

// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                    new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                          textView.setText("Response is: " + response);
                       }

                    }, new Response.ErrorListener() {

               @Override
               public void onErrorResponse(VolleyError error) {
                  textView.setText("That didn't work!");
               }
            });

// Add the request to the RequestQueue.
            queue.add(stringRequest);
         }
      });




   }
}