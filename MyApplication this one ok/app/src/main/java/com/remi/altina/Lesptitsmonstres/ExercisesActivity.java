package com.remi.altina.Lesptitsmonstres;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

public class ExercisesActivity extends AppCompatActivity {
    ListView lv_exerciseslist;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    DatabaseReference myref;
    List<ExercisePojo> exerciseslist;
    ProgressBar progressBar;

    public List categories = new ArrayList<>();
    public String[] categoriesarray = {"Les Ptits Exercices", "CP", "CE1", "CE2", "CM1", "CM2"};
    Spinner dynamicSpinner;

    String stringgfromshowact;
    Intent stringintent;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        progressBar = (ProgressBar) findViewById(R.id.progex);
        lv_exerciseslist = (ListView) findViewById(R.id.listexercises);
        exerciseslist = new ArrayList<>();
        stringintent = getIntent();
        stringgfromshowact = (String) stringintent.getSerializableExtra("stringgfromshowact");
        final Spinner dynamicSpinner = (Spinner) findViewById(R.id.spinner);
        final List categories = new ArrayList<>();
        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        t.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);


        lv_exerciseslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(ExercisesActivity.this, "clicked "+position, Toast.LENGTH_SHORT).show();
                ExercisePojo exercisePojo = (ExercisePojo) lv_exerciseslist.getItemAtPosition(position);
                //Toast.makeText(ExercisesActivity.this, "clicked "+exercisePojo.getPheading(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ExercisesActivity.this, ExercisesShow.class);
                intent.putExtra("obj", exercisePojo);
                startActivity(intent);

                //startActivity(new Intent(ExercisesActivity.this, ArticleShow.class));

            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(new Intent(ExercisesActivity.this, ExercisesActivity.class));
            }
        });

        settheadapter(categoriesarray);

        dl.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS); /* add this line */

        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                getdatathiscategory(position, ((String) parent.getItemAtPosition(position)));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                clickeditem(id);
                return true;
            }
        });


//
    }


    private void getdatathiscategory(int x, String s) {
        if (stringgfromshowact == null) {
            if (s.equals("Les Ptits Exercices") || x == 0) {
                //  Toast.makeText(this, "all selected", Toast.LENGTH_SHORT).show();
                myref = FirebaseDatabase.getInstance().getReference("Exercises");
                Query ex = myref.orderByChild("preadcolor");
                exerciseslist.clear();
                ex.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        ExercisePojo exercisePojo = dataSnapshot.getValue(ExercisePojo.class);
                        //   Toast.makeText(ExercisesActivity.this, " firsttimeset " + exercisePojo.getPdate(), Toast.LENGTH_SHORT).show();
                        exerciseslist.add(exercisePojo);
                        Collections.sort(exerciseslist, Collections.<ExercisePojo>reverseOrder());
                        ExercisesListAdapter exercisesListAdapter = new ExercisesListAdapter(ExercisesActivity.this, exerciseslist); //idhar aik object bhjo
                        lv_exerciseslist.setAdapter(exercisesListAdapter);
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


            }

            if (x > 0) {
                Query q = myref.orderByChild("pcategory").equalTo(s);
                exerciseslist.clear();
                q.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        ExercisePojo exercisePojo = dataSnapshot.getValue(ExercisePojo.class);
                        //  Toast.makeText(ExercisesActivity.this, " data x>0 " + exercisePojo.getPheading(), Toast.LENGTH_SHORT).show();
                        // Log.i("data x>0", exercisePojo.getPheading());

                        exerciseslist.add(exercisePojo);
                        Collections.sort(exerciseslist, Collections.<ExercisePojo>reverseOrder());
                        ExercisesListAdapter exercisesListAdapter = new ExercisesListAdapter(ExercisesActivity.this, exerciseslist); //idhar aik object bhjo
                        lv_exerciseslist.setAdapter(exercisesListAdapter);
                        progressBar.setVisibility(View.GONE);

                        //Toast.makeText(ArticlesActivity.this, "dont publish this now", Toast.LENGTH_SHORT).show();


                    }


                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        // finish();
                        // startActivity(getIntent());
                        // Toast.makeText(ExercisesActivity.this, "data is changing", Toast.LENGTH_SHORT).show();

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
        if (stringgfromshowact != null) {

            myref = FirebaseDatabase.getInstance().getReference("Exercises");
            Query q = myref.orderByChild("pcategory").equalTo(stringgfromshowact);
            exerciseslist.clear();
            q.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    ExercisePojo exercisePojo = dataSnapshot.getValue(ExercisePojo.class);
                    //Toast.makeText(ArticlesActivity.this, " childevent " + articlePOJO.getDate_article(), Toast.LENGTH_SHORT).show();
                    if (!exercisePojo.getPcategory().equals(""))        //////just is waja se chk lagaya takay baqi na ajaya kare sara data.,bad ma mita dena hai jab sara data dal donga
                    {

                        exerciseslist.add(exercisePojo);
                        Collections.sort(exerciseslist, Collections.<ExercisePojo>reverseOrder());
                        ExercisesListAdapter exercisesListAdapter = new ExercisesListAdapter(ExercisesActivity.this, exerciseslist); //idhar aik object bhjo
                        lv_exerciseslist.setAdapter(exercisesListAdapter);
                        progressBar.setVisibility(View.GONE);
                    } else {
                        //Toast.makeText(ArticlesActivity.this, "dont publish this now", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                    finish();
//                    startActivity(getIntent());
//                    Toast.makeText(ExercisesActivity.this, "data is changing", Toast.LENGTH_SHORT).show();

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
            stringgfromshowact = null;
        }
    }


    private void clickeditem(int id) {
        if (id == R.id.Activities) {
            startActivity(new Intent(ExercisesActivity.this, Activities.class));

        }
        if (id == R.id.Home) {
            //Toast.makeText(MainActivity.this, "Exercise", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ExercisesActivity.this, MainActivity.class));
        }
        if (id == R.id.Article) {

            //Toast.makeText(MainActivity.this, "quizz", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ExercisesActivity.this, ArticlesActivity.class));
        }
        if (id == R.id.Exercises) {
            // Toast.makeText(MainActivity.this, "addrecipe", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ExercisesActivity.this, ExercisesActivity.class));
        }
        if (id == R.id.Quizzes) {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ExercisesActivity.this, Quizzes.class));

        }
        if (id == R.id.participate) {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ExercisesActivity.this, Participate.class));

        }
        if (id == R.id.Recipes) {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ExercisesActivity.this, Recipes.class));

        }
        if(id==R.id.youtube)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ExercisesActivity.this, Youtube.class));

        }

        if (id == R.id.ParentArticles) {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ExercisesActivity.this, ParentArticles.class));

        }
        if (id == R.id.Settings) {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ExercisesActivity.this, Settings.class));

        }


        if (id == R.id.Contact) {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ExercisesActivity.this, Contact.class));

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesarray);
        adapter.setDropDownViewResource(R.layout.text);
        dynamicSpinner.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topbar, menu);
        MenuItem menuItem = menu.findItem(R.id.actionbar);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Chercher....");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                articlesListAdapter.getFilter().filter(newText);
//                articlesListAdapter.notifyDataSetChanged();
                filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void filter(String newText)
    {
        ArrayList<ExercisePojo> filteredList= new ArrayList<>();
        for (ExercisePojo item : exerciseslist)
        {
            if(item.getPheading().toLowerCase().contains(newText.toLowerCase()))
            {
                filteredList.add(item);
                ExercisesListAdapter exercisesListAdapter = new ExercisesListAdapter(ExercisesActivity.this, filteredList); //idhar aik object bhjo
                lv_exerciseslist.setAdapter(exercisesListAdapter);

            }
        }
        //ArticlesListAdapter.filterList(filteredList);
    }
    //ArticlesListAdapter.filterList(filteredList);
}


//ArrayList<ArticlePOJO> arrayList = new ArrayList<ArticlePOJO>();






