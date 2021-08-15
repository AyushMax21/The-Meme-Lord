package com.eggdevs.thememelord;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

   Button btnMeme;
   ImageView ivMeme;
   String url = "https://meme-api.herokuapp.com/gimme/funny";
   MySingleton mySingleton;
   ProgressBar progressBar;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      btnMeme = findViewById(R.id.btnMeme);
      ivMeme = findViewById(R.id.ivMeme);
      progressBar = findViewById(R.id.progressBar);

      mySingleton = MySingleton.getInstance(this);

      btnMeme.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View view) {
            setUpJsonNetworkRequest();
         }
      });


   }

   void setUpJsonNetworkRequest() {

      JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
              (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                 @Override
                 public void onResponse(JSONObject response) {
                    String memeUrl = null;
                     progressBar.setVisibility(View.GONE);
                     ivMeme.setVisibility(View.VISIBLE);
                    try {
                       
                       
                       memeUrl = response.getString("url");
                        displayMeme(memeUrl);
                       
                    }
                    
                    
                    catch (JSONException e) {
                       
                       
                       e.printStackTrace();
                       
                       
                    }
                 }
              }, new Response.ErrorListener() {

                 @Override
                 public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                 }
              });

      mySingleton.addToRequestQueue(jsonObjectRequest);

   }

   private void displayMeme(String memeUrl) {
            Glide
              .with(this)
              .load(memeUrl)
                    .listener(new RequestListener<Drawable>() {
                       @Override
                       public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                          ivMeme.setVisibility(View.GONE);
                          progressBar.setVisibility(View.GONE);
                          Toast.makeText(MainActivity.this, "Image could not be loaded!", Toast.LENGTH_SHORT).show();
                          return false;
                       }

                       @Override
                       public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                          ivMeme.setVisibility(View.VISIBLE);
                          progressBar.setVisibility(View.GONE);
                          return false;
                       }
                    })
              .into(ivMeme);
   }

}