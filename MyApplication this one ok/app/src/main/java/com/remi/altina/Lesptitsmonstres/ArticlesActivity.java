package com.remi.altina.Lesptitsmonstres;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import android.support.design.widget.NavigationView;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
//import <androidx.drawerlayout.widget.DrawerLayout;
//import androidx.core.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.EditText;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ArticlesActivity extends AppCompatActivity {
   // String pathname = Environment.getExternalStorageDirectory().getPath().concat("/Android/data/lesptitsmonstres/");
  int progresspref;
    ListView lv_articles;
    EditText aa,aa1;
    DatabaseReference myref;
    ProgressBar progressBar;
    DrawerLayout dl;
    List<ArticlePOJO> articleslist;
   // List<ArticlePOJO> articleslist1;

    Spinner dynamicSpinner;
    public String[] categoriesarray = {"Le journal des Ptits Monstres", "Monde", "France", "Sports", "Culture","Insolite"};

     ActionBarDrawerToggle t;
     NavigationView nv;
    String stringgfromshowact="";
    Intent stringintent;
   SwipeRefreshLayout swipeRefreshLayout;
    ArticlesListAdapter articlesListAdapter ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        //aa1= findViewById(R.id.plain_text_input2);

        dl= (DrawerLayout) findViewById(R.id.activity_maindrawer);
        lv_articles = (ListView) findViewById(R.id.list_articles);
        nv= (NavigationView) findViewById(R.id.nv);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        lv_articles.setBackgroundColor(Color.parseColor("#FFFFFF"));
        articleslist = new ArrayList<>(30);
        dynamicSpinner = (Spinner) findViewById(R.id.spinner);

        stringintent = getIntent();
        progressBar= (ProgressBar) findViewById(R.id.progart);
        stringgfromshowact = (String) stringintent.getSerializableExtra("stringgfromshowact");
        ArticlesListAdapter articlesListAdapter = new ArticlesListAdapter(ArticlesActivity.this, articleslist); //idhar aik object bhjo

//        searchbox.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s)
//            {
//                filtermethod(s.toString());
//
//            }
//        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(new Intent(ArticlesActivity.this, ArticlesActivity.class));
            }
        });




        settheadapter(categoriesarray);
            if (stringgfromshowact == null || TextUtils.isEmpty(stringgfromshowact)) {
            }
            else if (stringgfromshowact != null || !TextUtils.isEmpty(stringgfromshowact)) {
                if (stringgfromshowact.equals("Le journal des Ptits Monstres")) {
                    dynamicSpinner.setSelection(0);
                } else if (stringgfromshowact.equals("Monde")) {
                    dynamicSpinner.setSelection(1);
                } else if (stringgfromshowact.equals("France")) {
                    dynamicSpinner.setSelection(2);
                } else if (stringgfromshowact.equals("Sports")) {
                    dynamicSpinner.setSelection(3);
                } else if (stringgfromshowact.equals("Culture")) {
                    dynamicSpinner.setSelection(4);
                } else if (stringgfromshowact.equals("Insolite")) {
                    dynamicSpinner.setSelection(5);
                }

            }


        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                getdatathiscategory(parent.getItemAtPosition(position).toString(), position);

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        lv_articles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ArticlePOJO articlePOJO = (ArticlePOJO) lv_articles.getItemAtPosition(position);
                Intent intent = new Intent(ArticlesActivity.this, ArticleShow.class);
                intent.putExtra("obj", articlePOJO);
                startActivity(intent);

            }
        });

        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        t.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(t);
        t.syncState();
        try {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(this, "null pointer exception setdisplayhome", Toast.LENGTH_SHORT).show();
        }

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
        //checkperm();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topbar, menu);
        MenuItem menuItem= menu.findItem(R.id.actionbar);
        SearchView searchView= (SearchView) menuItem.getActionView();
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
        ArrayList<ArticlePOJO> filteredList= new ArrayList<>();
        filteredList.clear();
        for (ArticlePOJO item : articleslist)
        {
            if(item.getHeading_article().toLowerCase().contains(newText.toLowerCase()))
            {
                filteredList.add(item);
                articlesListAdapter = new ArticlesListAdapter(ArticlesActivity.this, filteredList); //idhar aik object bhjo
                lv_articles.setAdapter(articlesListAdapter);
            }

        }
        //ArticlesListAdapter.filterList(filteredList);
    }

//    public static void filterList(ArrayList<ArticlePOJO> filteredList)
//    {
//        articleslist= filteredList;
//
//    }



   /*  private void checkperm() {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            1);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
        }*/


    /*@Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                //premission to read storage
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
*/
    /*private void populate() {
        if (stringgfromshowact != null) {

            Query q = myref.orderByChild("category_article").equalTo(stringgfromshowact);
           // Query q1 = q.orderByChild("datesorting");
            q.addChildEventListener(new ChildEventListener() { ////////////////yaha parrrrrrr q1 thaaaaaaaaaaaaaaaaaaaaaaaaaaaa////////////////////////
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    ArticlePOJO articlePOJO = dataSnapshot.getValue(ArticlePOJO.class);
                    if (!articlePOJO.getCategory_article().equals(""))        //////just is waja se chk lagaya takay baqi na ajaya kare sara data.,bad ma mita dena hai jab sara data dal donga
                    {
                        articleslist.clear();
                        articleslist.add(articlePOJO);
                        Collections.sort(articleslist,Collections.<ArticlePOJO>reverseOrder());
                      //  Collections.reverse(articleslist);

                        ArticlesListAdapter articlesListAdapter = new ArticlesListAdapter(ArticlesActivity.this, articleslist); //idhar aik object bhjo
                        lv_articles.setAdapter(articlesListAdapter);
                        progressBar.setVisibility(View.GONE);



                    } else {
                        //Toast.makeText(ArticlesActivity.this, "dont publish this now", Toast.LENGTH_SHORT).show();
                    }


                }


                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    finish();
                    startActivity(getIntent());
                    Toast.makeText(ArticlesActivity.this, "data is changing", Toast.LENGTH_SHORT).show();

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
*/

    // I never used this function//not in use

private void filtermethod(String tosearch)
{
 ArrayList<ArticlePOJO> filteredList= new ArrayList<>();
 for (ArticlePOJO item : articleslist)
 {
     if(item.getHeading_article().toString().toLowerCase().contains(tosearch.toLowerCase()))
     {
         filteredList.add(item);
     }
 }
 //ArticlesListAdapter.filterList(filteredList) ;
}
    private void getallarticles() {

         DatabaseReference  myref = FirebaseDatabase.getInstance().getReference("Articles");
        articleslist.clear();      //yaha parrrrrrrrrr q lihka waaaa tha/////////////////////////////////
        myref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ArticlePOJO articlePOJO = dataSnapshot.getValue(ArticlePOJO.class);

                    articleslist.add(articlePOJO);

                //////just is waja se chk lagaya takay baqi na ajaya kare sara data.,bad ma mita dena hai jab sara data dal donga

                   //  Toast.makeText(ArticlesActivity.this, " getall " + articlePOJO.getDate_article(), Toast.LENGTH_SHORT).show();


            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                /*finish();
                startActivity(getIntent());
                Toast.makeText(ArticlesActivity.this, "data is changing", Toast.LENGTH_SHORT).show();*/

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


        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Collections.sort(articleslist,Collections.<ArticlePOJO>reverseOrder());
                articlesListAdapter = new ArticlesListAdapter(ArticlesActivity.this, articleslist); //idhar aik object bhjo
                lv_articles.setAdapter(articlesListAdapter);
                progressBar.setVisibility(View.GONE);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getdatathiscategory(String s, int position) {

        if (position == 0) {
            getallarticles();
           /* String fullpath = pathname;
            File file = new File(fullpath);
            if (file.exists()) {
                //  Toast.makeText(context, "yes exists"+pathname+"/"+articlePOJO.getHeading_article()+".jpeg", Toast.LENGTH_SHORT).show();
                Log.v("pathexists", pathname);
                getallarticles();

            }
            else
            {
                Log.v("pathnotexists", pathname);
                getallarticles();
            }*/
        } else if (position != 0 ) {
          DatabaseReference  myref = FirebaseDatabase.getInstance().getReference("Articles");
            Query q = myref.orderByChild("category_article").equalTo(s);
            articleslist.clear();

            q.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    ArticlePOJO articlePOJO = dataSnapshot.getValue(ArticlePOJO.class);
                        articleslist.add(articlePOJO);
                }


                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                 /*   finish();
                    startActivity(getIntent());*/
                  //  Toast.makeText(ArticlesActivity.this, "data is changing", Toast.LENGTH_SHORT).show();
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
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Collections.sort(articleslist,Collections.<ArticlePOJO>reverseOrder());
                    ArticlesListAdapter articlesListAdapter = new ArticlesListAdapter(ArticlesActivity.this, articleslist); //idhar aik object bhjo
                    lv_articles.setAdapter(articlesListAdapter);

                    progressBar.setVisibility(View.GONE);


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
            startActivity(new Intent(ArticlesActivity.this, Activities.class));

        }
        if (id==R.id.Home)
        {
            startActivity(new Intent(ArticlesActivity.this, MainActivity.class));
        }
        if(id==R.id.Article)
        {

            startActivity(new Intent(ArticlesActivity.this, ArticlesActivity.class));
        }
        if(id== R.id.Exercises)
        {
            startActivity(new Intent(ArticlesActivity.this, ExercisesActivity.class));
        }
        if(id==R.id.Quizzes)
        {
            startActivity(new Intent(ArticlesActivity.this, Quizzes.class));

        }
        if(id==R.id.participate)
        {
            startActivity(new Intent(ArticlesActivity.this, Participate.class));

        }
        if(id==R.id.Settings)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ArticlesActivity.this, Settings.class));

        }
        if(id==R.id.Recipes)
        {
            startActivity(new Intent(ArticlesActivity.this, Recipes.class));

        }


        if(id==R.id.ParentArticles)
        {
            startActivity(new Intent(ArticlesActivity.this, ParentArticles.class));

        }


        if(id==R.id.Contact)
        {
            startActivity(new Intent(ArticlesActivity.this, Contact.class));

        }
//        if(id==R.id.Donate)
//        {
//            startActivity(new Intent(ArticlesActivity.this, Donate.class));
//
//        }
        if(id==R.id.youtube)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ArticlesActivity.this, Youtube.class));

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
       try {
           if (dl.isDrawerOpen(GravityCompat.START))
               dl.closeDrawer(GravityCompat.START);
           else
               super.onBackPressed();
       }
       catch(NullPointerException e )
       {
           Toast.makeText(this, "null pointer dl", Toast.LENGTH_SHORT).show();
       }
    }

    public void settheadapter(String[] categoriesarray) {
        Spinner dynamicSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,categoriesarray);
        adapter.setDropDownViewResource(R.layout.text);
        dynamicSpinner.setAdapter(adapter);
    }


}


