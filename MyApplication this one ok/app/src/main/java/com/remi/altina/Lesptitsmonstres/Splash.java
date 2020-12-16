package com.remi.altina.Lesptitsmonstres;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Window;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class Splash extends AppCompatActivity {
    String ok="defnotok";
    String[] amount;
    FirebaseOptions options;
    FirebaseApp secondApp;
    String URL;
    String xx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
      //  getActionBar().hide();

        getSupportActionBar().hide(); //abi kiya


        options = new FirebaseOptions.Builder()
                .setApiKey("AIzaSyCCP8K7vRB4yvZU_H1P_aawBt0hK4RiSr8")
                .setApplicationId("1:268228967081:android:4e40d5842cf99026d4c7b8")
                .setDatabaseUrl("https://torunornot.firebaseio.com/")
                .build();
        if(FirebaseApp.getApps(this).size()<=1)
        {
            // Log.e("pre  init", "first time");
            secondApp = FirebaseApp.initializeApp(getApplicationContext(), options,"myapp");
        }
            FirebaseApp secondary = FirebaseApp.getInstance("myapp");
            final FirebaseDatabase secondDatabase = FirebaseDatabase.getInstance(secondary);
            DatabaseReference secondDatabaseReference1 = secondDatabase.getReference("websitelink");
            secondDatabaseReference1.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    URL = dataSnapshot.getValue(String.class);
                    SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    final SharedPreferences.Editor editor1 = pref1.edit();
                    editor1.putString("URL", URL);
                    editor1.apply();
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    xx = response.toString();
                                    // getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolorforsplash));
                                    // Log.e("preokfalse ", ok.toLowerCase());

                                    DatabaseReference secondDatabaseReference = secondDatabase.getReference("run");
                                    secondDatabaseReference.addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                            final String valuee = dataSnapshot.getValue(String.class);
                                            if (valuee.equals("run") && xx.contains("<h1>RUN</h1>")) {
                                                SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                                final SharedPreferences.Editor editor1 = pref1.edit();
                                                editor1.putInt("byamount", 2);
                                                editor1.apply();
                                                //Log.e("pre","firstif106");
                                                Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Intent i = new Intent(Splash.this, MainActivity.class);
                                                        startActivity(i);
                                                        finish();
                                                    }
                                                }, 1800); //2500
                                            } else if (valuee.equals("someother") && xx.contains("<h1>SOMEOTHER</h1>")) {
                                                //  Log.e("pre","secondif106");
                                                SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                                final SharedPreferences.Editor editor1 = pref1.edit();
                                                editor1.putInt("byamount", 2);
                                                editor1.apply();
                                                //startActivity(new Intent(Splash.this, LinkShow.class));

                                            } else if (valuee.contains("salorda") && xx.contains("<h1>SALORDA</h1>")) {
                                                //   Log.e("pre","THIRDIF");

                                                amount = valuee.split(",");
                                                SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                                final SharedPreferences.Editor editor1 = pref1.edit();
                                                editor1.putInt("byamount", Integer.parseInt(amount[1]));
                                                editor1.apply();
                                                Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        Intent i = new Intent(Splash.this, MainActivity.class);
                                                        startActivity(i);
                                                        finish();
                                                    }
                                                }, 1800); //2500

                                            }
                                        }

                                        @Override
                                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                        }

                                        @Override
                                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                                        }

                                        @Override
                                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    queue.add(stringRequest);

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

}
