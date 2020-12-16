package com.remi.altina.Lesptitsmonstres;

import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Quizzes extends AppCompatActivity {
    ListView lv_quizzes;
    DatabaseReference myref;
    List<QuizzPOJO> quizzeslist;
    ProgressBar progressBar;



    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizzes);

        progressBar= (ProgressBar) findViewById(R.id.progquizz);
        lv_quizzes = (ListView) findViewById(R.id.list_articles);
        lv_quizzes.setBackgroundColor(Color.parseColor("#FFFFFF"));
        quizzeslist= new ArrayList<>();
        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        t.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(t);
        t.syncState();
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lv_quizzes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(Quizzes.this, "clicked "+position, Toast.LENGTH_SHORT).show();
                QuizzPOJO quizzPOJO=(QuizzPOJO) lv_quizzes.getItemAtPosition(position);
                // Toast.makeText(Quizzes.this, "clicked "+articlePOJO.getHeading_article(), Toast.LENGTH_SHORT).show();
                //Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(quizzPOJO.getQuizzlink()));
                Intent intent = new Intent(Quizzes.this, QuizzesShow.class);
                intent.putExtra("obj",quizzPOJO);
                startActivity(intent);

            }
        });
        dl.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS); /* add this line */
        myref = FirebaseDatabase.getInstance().getReference("Quizz");
        Query q = myref.orderByChild("date");
        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                QuizzPOJO quizzPOJO = dataSnapshot.getValue(QuizzPOJO.class);
                //Toast.makeText(Quizzes.this, " childevent " + articlePOJO.getDate_article(), Toast.LENGTH_SHORT).show();
                quizzeslist.add(quizzPOJO);
                Collections.sort(quizzeslist,Collections.<QuizzPOJO>reverseOrder());
                quizzeslistAdapter quizzeslistAdapter = new quizzeslistAdapter(Quizzes.this, quizzeslist); //idhar aik object bhjo
                lv_quizzes.setAdapter(quizzeslistAdapter);
                progressBar.setVisibility(View.GONE);

            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
              //  startActivity(getIntent());

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





        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                int id=item.getItemId();
                clickeditem(id);
                return true;
            }
        });

    }
    private void clickeditem(int id) {
        if(id==R.id.Activities)
        {
            startActivity(new Intent(Quizzes.this, Activities.class));

        }
        if (id==R.id.Home)
        {
            //Toast.makeText(MainActivity.this, "Exercise", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Quizzes.this, MainActivity.class));
        }
        if(id==R.id.Article)
        {

            //Toast.makeText(MainActivity.this, "quizz", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Quizzes.this, ArticlesActivity.class));
        }
        if(id== R.id.Exercises)
        {
            // Toast.makeText(MainActivity.this, "addrecipe", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Quizzes.this, ExercisesActivity.class));
        }
        if(id==R.id.Quizzes)
        {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Quizzes.this, Quizzes.class));

        }
        if(id==R.id.participate)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Quizzes.this, Participate.class));

        }
        if(id==R.id.Recipes)
        {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Quizzes.this, Recipes.class));

        }
        if(id==R.id.Settings)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Quizzes.this, Settings.class));

        }

        if(id==R.id.ParentArticles)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Quizzes.this, ParentArticles.class));

        }


        if(id==R.id.Contact)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Quizzes.this, Contact.class));

        }
        if(id==R.id.youtube)
        {
            startActivity(new Intent(Quizzes.this, Youtube.class));

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
}
//ArrayList<ArticlePOJO> arrayList = new ArrayList<ArticlePOJO>();




