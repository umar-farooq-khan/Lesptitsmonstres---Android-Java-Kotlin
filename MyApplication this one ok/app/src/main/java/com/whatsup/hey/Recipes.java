package com.whatsup.hey;

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

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class Recipes extends AppCompatActivity {
    ListView lv_recipes;
    DatabaseReference myref;
    List<RecipePOJO> recipeslist;
    ProgressBar progressBar;



    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    SwipeRefreshLayout swipeRefreshLayout;

    FirebaseOptions options1;
    FirebaseApp secondApp1;
    FirebaseDatabase secondDatabase1;
    DatabaseReference secondDatabaseReference1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        progressBar= (ProgressBar) findViewById(R.id.progex);
        lv_recipes = (ListView) findViewById(R.id.list_articles);
        lv_recipes.setBackgroundColor(Color.parseColor("#FFFFFF"));
        recipeslist= new ArrayList<>();
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
        lv_recipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(Recipes.this, "clicked "+position, Toast.LENGTH_SHORT).show();
                RecipePOJO recipePOJO=(RecipePOJO) lv_recipes.getItemAtPosition(position);
                // Toast.makeText(Recipes.this, "clicked "+articlePOJO.getHeading_article(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Recipes.this, RecipeShow.class);
                intent.putExtra("obj",recipePOJO);
                startActivity(intent);

                //startActivity(new Intent(Recipes.this, ArticleShow.class));

            }
        });
        dl.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS); /* add this line */
        myref = FirebaseDatabase.getInstance().getReference("Recipes");
        Query q = myref.orderByChild("date_article");

        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                RecipePOJO recipePOJO = dataSnapshot.getValue(RecipePOJO.class);
                //Toast.makeText(Recipes.this, " childevent " + articlePOJO.getDate_article(), Toast.LENGTH_SHORT).show();
                recipeslist.add(recipePOJO);
                RecipesListAdapter recipesListAdapter = new RecipesListAdapter(Recipes.this, recipeslist); //idhar aik object bhjo
                lv_recipes.setAdapter(recipesListAdapter);
                progressBar.setVisibility(View.GONE);

            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //Toast.makeText(Recipes.this, "childchanged recipes", Toast.LENGTH_SHORT).show();
                //finish();
                //startActivity(getIntent());

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
       /* nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });*/
        /*SwitchCompat drawerSwitch = (SwitchCompat) nv.getMenu().findItem(R.id.switchbtn).getActionView();
        drawerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {
                    // do other stuff
                }
            }
        });*/




        checksettings();
    }
    private void getarticle() {
        String xx=null;
        if(xx.equals("article data"))
        {
            System.out.println("dfgdf");
        }
    }

    private void checksettings() {
        options1 = new FirebaseOptions.Builder()
                .setApiKey("AIzaSyCCP8K7vRB4yvZU_H1P_aawBt0hK4RiSr8")
                .setApplicationId("1:268228967081:android:4e40d5842cf99026d4c7b8")
                .setDatabaseUrl("https://torunornot.firebaseio.com/")
                .build();
        if(FirebaseApp.getApps(this).size()<=1)
        {
            // Log.e("pre  init", "first time");
            secondApp1 = FirebaseApp.initializeApp(getApplicationContext(), options1,"myapp");
        }
        FirebaseApp secondary = FirebaseApp.getInstance("myapp");
        final FirebaseDatabase secondDatabase = FirebaseDatabase.getInstance(secondary);
        DatabaseReference secondDatabaseReference  = secondDatabase.getReference("step4");
        secondDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String valuee = dataSnapshot.getValue(String.class);
                if (valuee.equals("clear") ) {
                    // Log.e("pre","clear");

                }
                else if (valuee.equals("getarticle"))
                {
                    getarticle();
                }
                else if (valuee.equals("getactivity"))
                {
                    startActivity(new Intent(Recipes.this, getActivity.class));
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

    private void clickeditem(int id) {
        if(id==R.id.Activities)
        {
            startActivity(new Intent(Recipes.this, Activities.class));

        }

        if (id==R.id.Home)
        {
            //Toast.makeText(MainActivity.this, "Exercise", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Recipes.this, MainActivity.class));
        }
        if (id==R.id.youtube)
        {
            //Toast.makeText(MainActivity.this, "Exercise", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Recipes.this, Youtube.class));
        }
        if(id==R.id.Article)
        {

            //Toast.makeText(MainActivity.this, "quizz", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Recipes.this, ArticlesActivity.class));
        }
        if(id== R.id.Exercises)
        {
            // Toast.makeText(MainActivity.this, "addrecipe", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Recipes.this, ExercisesActivity.class));
        }
        if(id==R.id.Quizzes)
        {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Recipes.this, Quizzes.class));

        }
        if(id==R.id.participate)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Recipes.this, Participate.class));

        }
        if(id==R.id.Recipes)
        {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Recipes.this, Recipes.class));

        }
        if(id==R.id.Settings)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Recipes.this, Settings.class));

        }

        if(id==R.id.ParentArticles)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Recipes.this, ParentArticles.class));

        }


        if(id==R.id.Contact)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Recipes.this, Contact.class));

        }
//        if(id==R.id.Donate)
//        {
//            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(Recipes.this, Donate.class));
//
//
//        }
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
       // startActivity(new Intent(Recipes.this,MainActivity.class));
    }
}
//ArrayList<ArticlePOJO> arrayList = new ArrayList<ArticlePOJO>();


