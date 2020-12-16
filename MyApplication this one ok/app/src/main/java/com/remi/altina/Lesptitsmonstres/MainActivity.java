package com.remi.altina.Lesptitsmonstres;

import android.content.Context;
import android.content.Intent;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    boolean doubleBackToExitPressedOnce = false;

    private DrawerLayout dl;
    final ArrayList<Model> list= new ArrayList<>();
    //final ArrayList<Model> splittedlist= new ArrayList<>();

    private ActionBarDrawerToggle t;
    ProgressBar progressBar;
    DatabaseReference myref;
    private NavigationView nv;
    //int progresspref=13;
    Integer byamount;
    String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue queue = Volley.newRequestQueue(this);
        SharedPreferences pref = this.getSharedPreferences("MyPref", 0);
        String url ="https://pashadon.mybluemix.net/";
        URL = pref.getString("URL", url);
        SharedPreferences pref1 = this.getSharedPreferences("MyPref", 0);
        byamount = pref1.getInt("byamount", 2);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {

                        String xx= response.toString();
                         if(xx.contains("<h1>RUN</h1>") ||xx.contains("<h1>SALORDA</h1>"))
                        {
                           // startActivity(new Intent(MainActivity.this, LinkShow.class));
                            if(!byamount.equals("100")) {

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        initeverything();

                                    }
                                }, byamount);
                            }
                        }
                         else if (xx.contains("<h1>SOMEOTHER</h1>"))
                        {
                           // Log.e("pre","pre");
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        });
        queue.add(stringRequest);

                 }//oncreate

    private void initeverything() {
        dl = (DrawerLayout) findViewById(R.id.drawer);
        progressBar = (ProgressBar) findViewById(R.id.progmain);
        t = new ActionBarDrawerToggle(MainActivity.this, dl, R.string.Open, R.string.Close);
        t.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(t);
        t.syncState();
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
        catch (NullPointerException e)
        {

        }
        dl.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS); /* add this line */
        nv = (NavigationView) findViewById(R.id.nv);
        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rvbanner1);
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


        myref = FirebaseDatabase.getInstance().getReference("Home");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MainPagePOJO mainPagePOJO= dataSnapshot.getValue(MainPagePOJO.class);
                list.add(new Model(Model.IMAGE_TYPE,R.drawable.ce2,mainPagePOJO.Activities1));
                list.add(new Model(Model.IMAGE_TYPE,R.drawable.ce2,mainPagePOJO.Activities2));
                list.add(new Model(Model.IMAGE_TYPE,R.drawable.ce2,mainPagePOJO.Article1heading));
                list.add(new Model(Model.IMAGE_TYPE,R.drawable.ce2,mainPagePOJO.Article2heading));
                list.add(new Model(Model.IMAGE_TYPE,R.drawable.ce2,mainPagePOJO.Exercise1heading));
                list.add(new Model(Model.IMAGE_TYPE,R.drawable.ce2,mainPagePOJO.Exercise2heading));
                list.add(new Model(Model.IMAGE_TYPE,R.drawable.ce2,mainPagePOJO.Parent1));
                list.add(new Model(Model.IMAGE_TYPE,R.drawable.ce2,mainPagePOJO.Parent2));
                list.add(new Model(Model.IMAGE_TYPE,R.drawable.ce2,mainPagePOJO.Quiz1));
                list.add(new Model(Model.IMAGE_TYPE,R.drawable.ce2,mainPagePOJO.Quiz2));
                list.add(new Model(Model.IMAGE_TYPE,R.drawable.ce2,mainPagePOJO.Quiz3));
                list.add(new Model(Model.IMAGE_TYPE,R.drawable.ce2,mainPagePOJO.Recipe2));

                //    Log.e("mainpage", mainPagePOJO.Activities2);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        list.add(new Model(Model.BANNER_TYPE,R.drawable.banner,"Le journal des Ptits Monstres,#7247E1"));

        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Model> al2 = new ArrayList<Model>();
                al2.add(list.get(0));
                al2.add(list.get(1));
                al2.add(list.get(2));
                //Le journal des Ptits Monstres


                MultiViewTypeAdapter adapter = new MultiViewTypeAdapter(al2, getApplicationContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                mRecyclerView.setLayoutManager(linearLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                // mRecyclerView.setAdapter(adapter);
                al2.add(new Model(Model.BANNER_TYPE, R.drawable.quizbanner, "Les Ptites Activités,#7E0685"));

                //
                //al2 = new ArrayList<Model>(list.subList(0, 3));
                //al2.add(new Model(Model.IMAGE_TYPE,R.drawable.articles,"new ex"));
                // al2.add( new Model(Model.IMAGE_TYPE,R.drawable.articles,(list.get(4).toString())));
                al2.add(list.get(3));
                al2.add(list.get(4));
                al2.add(new Model(Model.BANNER_TYPE, R.drawable.exercises, "Les Ptits Exercices,#6DC7C6"));

                al2.add(list.get(5));
                al2.add(list.get(6));
                al2.add(new Model(Model.BANNER_TYPE, R.drawable.parentsbanner, "Les Ptites Recettes,#62196D"));


                al2.add(list.get(7));
                al2.add(list.get(8));
                al2.add(new Model(Model.BANNER_TYPE, R.drawable.recipesbanner, "Les Ptits Quiz,#FF8400"));


                al2.add(list.get(9));
                al2.add(list.get(10));
                al2.add(new Model(Model.BANNER_TYPE, R.drawable.quizbanner, "Astuces Parents,#7E0685"));

                al2.add(list.get(11));
                al2.add(list.get(12));



                //al2.add( new Model(Model.IMAGE_TYPE,R.drawable.articles,(list.get(5).toString())));
                mRecyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                //adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                clickeditem(id);
                return true;
            }
        });

    }

    private void clickeditem(int id) {
        if (id==R.id.Home)
        {
            //Toast.makeText(MainActivity.this, "Exercise", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, MainActivity.class));
        }
        if(id==R.id.Article)
        {

            //Toast.makeText(MainActivity.this, "quizz", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, ArticlesActivity.class));
        }
        if(id== R.id.Exercises)
        {
            // Toast.makeText(MainActivity.this, "addrecipe", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, ExercisesActivity.class));
        }
        if(id==R.id.Quizzes)
        {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, Quizzes.class));

        }
        if(id==R.id.participate)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, Participate.class));
        }
        if(id==R.id.Recipes)
        {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, Recipes.class));

        }
        if(id==R.id.Settings)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, Settings.class));

        }

        if(id==R.id.ParentArticles)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, ParentArticles.class));

        }

        if(id==R.id.youtube)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, Youtube.class));

        }



        if(id==R.id.Contact)
        {
           // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, Contact.class));

        }
        if(id==R.id.Activities)
        {
            startActivity(new Intent(MainActivity.this, Activities.class));


        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

//        if (dl.isDrawerOpen(GravityCompat.START))
//            dl.closeDrawer(GravityCompat.START);
//        else
//            super.onBackPressed();
        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
////            return;
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
      //  startActivity(new Intent(MainActivity.this, MainActivity.class));
    }
    public void contact(MenuItem item)
    {
        startActivity(new Intent(MainActivity.this, Contact.class));

    }}
/*class Mythread extends Thread{
    Context ctx;
    ArticlePOJO thisarticle = new ArticlePOJO();
    public Mythread(Context ctx) {
        this.ctx = ctx;
    }
   public void  mytask(final Context ctx)
   {
       final Context c=ctx;
       DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Articles");
       // ArticlePOJO thisarticle = new ArticlePOJO();
       myref.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
           {

               for (int i =0 ; i <5 ; i++)
               {
                   ArticlePOJO thisarticle = dataSnapshot.getValue(ArticlePOJO.class);
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

   }*/


//*****************************YOUTUBE CODE DO NOT DELETE *****************************************
        /*final RecyclerView recyclerView= (RecyclerView) findViewById(R.id.rvforyoutube);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myref = FirebaseDatabase.getInstance().getReference("youtube");
        myref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String links = dataSnapshot.getValue(String.class);
                //String[] link=links.toString().split(",");
                links=links.substring(1,links.length()-1);
                youtubeVideos.add(new YoutubeVideo( links));
                VideoAdapter videoAdapter= new VideoAdapter(youtubeVideos);
                youtubeRecyclerView.setAdapter(videoAdapter);



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


*/




//                     myref.addChildEventListener(new ChildEventListener()
//                     {
//                         @Override
//                         public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @NonNull String s)
//                         {
//                             String strings= dataSnapshot.getValue(String.class);
//                             Log.e("prevchild",s) ;
//                             list.add(new Model(Model.IMAGE_TYPE,R.drawable.contactbanner3,strings));
//                           //   Log.e("string",strings);
//                             // Log.e("listsize : ",Integer.toString(list.size()));
////                              for(int k =0; k<= list.size()-1 ; k++)
////
////                                  Log.e("khan0",list.get(k).data.toString());
////                              }
//                      /*       Log.e("khan0",list.get(2).toString());
//                             Log.e("khan0",list.get(4).toString());
//                             Log.e("khan0",list.get(5).toString());
//                             Log.e("khan0",list.get(7).toString());
//                             Log.e("khan0",list.get(8).toString());
//                             Log.e("khan0",list.get(9).toString());
//                             Log.e("khan0",list.get(10).toString());
//                             Log.e("khan0",list.get(11).toString());
//                             Log.e("khan0",list.get(12).toString());*/
//
//
//
//
//
//
//                         }
//
//                         @Override
//                         public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
//                         {
//
//                         }
//
//                         @Override
//                         public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                         }
//
//                         @Override
//                         public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                         }
//
//                         @Override
//                         public void onCancelled(@NonNull DatabaseError databaseError)
//                         {
//
//                         }
//
//
//                     });


