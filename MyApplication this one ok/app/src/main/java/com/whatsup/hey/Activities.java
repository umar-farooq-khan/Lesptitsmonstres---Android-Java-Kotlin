package com.whatsup.hey;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Activities extends AppCompatActivity {

    ListView lv_exerciseslist;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    DatabaseReference myref;
    List<ActivitiesPOJO> exerciseslist;
    ProgressBar progressBar;

    public List categories= new ArrayList<>();

    String stringgfromshowact;
    Intent stringintent;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        progressBar= (ProgressBar) findViewById(R.id.progacti);
        lv_exerciseslist = (ListView) findViewById(R.id.listacti);
        exerciseslist = new ArrayList<>();

        stringintent = getIntent();
        stringgfromshowact = (String) stringintent.getSerializableExtra("stringgfromshowact");
        final List categories = new ArrayList<>();
        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        t.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipe);


        lv_exerciseslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(Activities.this, "clicked "+position, Toast.LENGTH_SHORT).show();
                ActivitiesPOJO activitiesPOJO = (ActivitiesPOJO) lv_exerciseslist.getItemAtPosition(position);
                //Toast.makeText(Activities.this, "clicked "+exercisePojo.getPheading(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Activities.this, ActivitiesShow.class);
                intent.putExtra("obj", activitiesPOJO);
                startActivity(intent);

                //startActivity(new Intent(Activities.this, ArticleShow.class));

            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(new Intent(Activities.this,Activities.class));
            }
        });


        dl.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS); /* add this line */

        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                clickeditem(id);
                return true;
            }
        });
    ///////////////getallllllllllllllll data

            myref = FirebaseDatabase.getInstance().getReference("Activities");
          //  Query q = myref.orderByChild("pcategory").equalTo(stringgfromshowact);
            exerciseslist.clear();
            myref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    ActivitiesPOJO activitiesPOJO = dataSnapshot.getValue(ActivitiesPOJO.class);
                    //Toast.makeText(ArticlesActivity.this, " childevent " + articlePOJO.getDate_article(), Toast.LENGTH_SHORT).show();
                        exerciseslist.add(activitiesPOJO);
                        Collections.sort(exerciseslist,Collections.<ActivitiesPOJO>reverseOrder());
                        ActivitiesListAdapter activitiesListAdapter = new ActivitiesListAdapter(Activities.this, exerciseslist); //idhar aik object bhjo
                        lv_exerciseslist.setAdapter(activitiesListAdapter);
                        progressBar.setVisibility(View.GONE);
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
            stringgfromshowact=null;
        }




    private void clickeditem(int id) {
        if(id==R.id.Activities)
        {
            startActivity(new Intent(Activities.this, Activities.class));

        }
        if (id==R.id.Home)
        {
            //Toast.makeText(MainActivity.this, "Exercise", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Activities.this, MainActivity.class));
        }
        if(id==R.id.Article)
        {

            //Toast.makeText(MainActivity.this, "quizz", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Activities.this, ArticlesActivity.class));
        }
        if(id== R.id.Exercises)
        {
            // Toast.makeText(MainActivity.this, "addrecipe", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Activities.this, ExercisesActivity.class));
        }
        if(id==R.id.Quizzes)
        {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Activities.this, Quizzes.class));

        }
        if(id==R.id.participate)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Activities.this, Participate.class));

        }
        if(id==R.id.Recipes)
        {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Activities.this, Recipes.class));

        }
        if(id==R.id.Settings)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Activities.this, Settings.class));

        }

        if(id==R.id.ParentArticles)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Activities.this, ParentArticles.class));

        }


        if(id==R.id.Contact)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Activities.this, Contact.class));

        }
        if(id==R.id.youtube)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Activities.this, Youtube.class));

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

        if (dl.isDrawerOpen(GravityCompat.START))
            dl.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    public void settheadapter(String[] categoriesarray) {
        Spinner dynamicSpinner = (Spinner) findViewById(R.id.spinner);
        // Toast.makeText(this, "list"+categoriesarray[2], Toast.LENGTH_SHORT).show();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,categoriesarray);
        adapter.setDropDownViewResource(R.layout.text);
        dynamicSpinner.setAdapter(adapter);
    }


}

//ArrayList<ArticlePOJO> arrayList = new ArrayList<ArticlePOJO>();






