package com.whatsup.hey;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParentArticles extends AppCompatActivity {
    ListView lv_articles;
    DatabaseReference myref;
    List<ParentPOJO> articleslist;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    SwipeRefreshLayout swipeRefreshLayout;

    public List categories= new ArrayList<>();
    Spinner dynamicSpinner;
    public String [] categoriesarray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_articles);


        lv_articles = (ListView) findViewById(R.id.list_articles);
        articleslist= new ArrayList<>();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        final Spinner dynamicSpinner = (Spinner) findViewById(R.id.spinner);
        final List categories= new ArrayList<>();
        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        t.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getallarticles();

        myref = FirebaseDatabase.getInstance().getReference("ParentsCategory");
        myref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                categories.add(dataSnapshot.getValue(String.class));
                //addthisdatatoarray(dataSnapshot.getValue(String.class));
                // Toast.makeText(ArticlesActivity.this, "data"+dataSnapshot.getValue(), Toast.LENGTH_SHORT).show();

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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               finish();
               startActivity(getIntent());
            }
        });
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categoriesarray = (String[]) categories.toArray(new String[categories.size()]);  //ye b zero hogya
                settheadapter(categoriesarray);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        lv_articles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(ParentArticles.this, "clicked "+position, Toast.LENGTH_SHORT).show();
                ParentPOJO parentPOJO=(ParentPOJO) lv_articles.getItemAtPosition(position);
                // Toast.makeText(ParentArticles.this, "clicked "+articlePOJO.getHeading_article(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ParentArticles.this, ParentArticleShow.class);
                intent.putExtra("obj",parentPOJO);
                startActivity(intent);

                //startActivity(new Intent(ParentArticles.this, ArticleShow.class));

            }
        });
        dl.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS); /* add this line */


        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                getdatathiscategory(parent.getItemAtPosition(position).toString());

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
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




    }

    private void getallarticles() {
        myref = FirebaseDatabase.getInstance().getReference("Parents");
        Query pa=myref.orderByChild("date_article");
        articleslist.clear();
        pa.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ParentPOJO parentPOJO = dataSnapshot.getValue(ParentPOJO.class);
                //   Toast.makeText(ParentArticles.this, " getallarticles " + parentPOJO.getHeading_article(), Toast.LENGTH_SHORT).show();
                articleslist.add(parentPOJO);
                Collections.sort(articleslist,Collections.<ParentPOJO>reverseOrder());
                ParentListAdapter articlesListAdapter = new ParentListAdapter(ParentArticles.this, articleslist); //idhar aik object bhjo
                lv_articles.setAdapter(articlesListAdapter);




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

    private void getdatathiscategory(String s) {
        if(s.equals("Espace parents")|| s.equals("Firsttime"))
        {
            getallarticles();
        }

        else {
            Query q = myref.orderByChild("category").equalTo(s);
            articleslist.clear();
            q.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    ParentPOJO parentPOJO = dataSnapshot.getValue(ParentPOJO.class);
                   // Toast.makeText(ParentArticles.this, " data " + parentPOJO.getHeading_article(), Toast.LENGTH_SHORT).show();
                    if (!parentPOJO.getCategory().equals(""))        //////just is waja se chk lagaya takay baqi na ajaya kare sara data.,bad ma mita dena hai jab sara data dal donga
                    {
                        articleslist.add(parentPOJO);
                        Collections.sort(articleslist,Collections.<ParentPOJO>reverseOrder());
                        ParentListAdapter articlesListAdapter = new ParentListAdapter(ParentArticles.this, articleslist); //idhar aik object bhjo
                        lv_articles.setAdapter(articlesListAdapter);

                    } else {
                        //Toast.makeText(ArticlesActivity.this, "dont publish this now", Toast.LENGTH_SHORT).show();
                    }


                }


                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                   // finish();
                   // startActivity(getIntent());
                    // Toast.makeText(ParentArticles.this, "data is changing", Toast.LENGTH_SHORT).show();

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
    private void clickeditem(int id) {
        if(id==R.id.Activities)
        {
            startActivity(new Intent(ParentArticles.this, Activities.class));

        }
        if (id==R.id.Home)
        {
            //Toast.makeText(MainActivity.this, "Exercise", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ParentArticles.this, MainActivity.class));
        }
        if(id==R.id.Article)
        {

            //Toast.makeText(MainActivity.this, "quizz", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ParentArticles.this, ArticlesActivity.class));
        }
        if(id== R.id.Exercises)
        {
            // Toast.makeText(MainActivity.this, "addrecipe", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ParentArticles.this, ExercisesActivity.class));
        }
        if(id==R.id.Quizzes)
        {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ParentArticles.this, Quizzes.class));

        }
        if(id==R.id.participate)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ParentArticles.this, Participate.class));

        }
        if(id==R.id.Recipes)
        {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ParentArticles.this, Recipes.class));

        }
        if(id==R.id.youtube)
        {
            startActivity(new Intent(ParentArticles.this, Youtube.class));

        }

        if(id==R.id.ParentArticles)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ParentArticles.this, ParentArticles.class));

        }


        if(id==R.id.Contact)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ParentArticles.this, Contact.class));

        }
        if(id==R.id.Settings)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ParentArticles.this, Settings.class));

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
        //Toast.makeText(this, "list"+categoriesarray[2], Toast.LENGTH_SHORT).show();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,categoriesarray);
        adapter.setDropDownViewResource(R.layout.text);
        dynamicSpinner.setAdapter(adapter);
    }

    /*public void tohome(MenuItem item) {
        startActivity(new Intent(ParentArticles.this, MainActivity.class));


    }*/
}
//ArrayList<ArticlePOJO> arrayList = new ArrayList<ArticlePOJO>();


