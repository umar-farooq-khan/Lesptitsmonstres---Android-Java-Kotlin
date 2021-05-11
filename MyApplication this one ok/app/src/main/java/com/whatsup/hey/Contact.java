package com.whatsup.hey;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Contact extends AppCompatActivity {
    DatabaseReference myref;
    WebView webView;
   // ProgressBar progressBar;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        webView= (WebView) findViewById(R.id.webcontact);
     //  progressBar= findViewById(R.id.prog);

        myref = FirebaseDatabase.getInstance().getReference("applink");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Websitelink websitelink= dataSnapshot.getValue(Websitelink.class);
             //   Log.e("data",websitelink.getLinkk());

                try {
                    webView.setWebViewClient(new WebViewClient());
                    webView.loadUrl(websitelink.getLinkk());
                    WebSettings webSettings = webView.getSettings();
                    webSettings.setJavaScriptEnabled(true);

                }


                catch(Exception e)
                {

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        webView.requestFocus(View.FOCUS_DOWN);
//        webView.setOnTouchListener(new View.OnTouchListener() {
//                                       @Override
//                                       public boolean onTouch(View v, MotionEvent event) {
//                                           Log.e("click","click");
//                                           switch (event.getAction()) {
//                                               case MotionEvent.ACTION_DOWN:
//                                               case MotionEvent.ACTION_UP:
//                                                   if (!v.hasFocus()) {
//                                                       v.requestFocus();
//                                                       webView.requestFocus();
//                                                       webView.performClick();
//
//
//                                                   }
//                                                   break;
//                                           }
//                                           return false;
//                                       }
//                                   }
//        );



        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        t.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(t);
        t.syncState();
        try
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
        catch (Exception e )
        {

        }
        dl.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS); /* add this line */
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
            startActivity(new Intent(Contact.this, Activities.class));

        }
        if (id==R.id.Home)
        {
            //Toast.makeText(MainActivity.this, "Exercise", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Contact.this, MainActivity.class));
        }
        if(id==R.id.Article)
        {

            //Toast.makeText(MainActivity.this, "quizz", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Contact.this, ArticlesActivity.class));
        }
        if(id== R.id.Exercises)
        {
            // Toast.makeText(MainActivity.this, "addrecipe", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Contact.this, ExercisesActivity.class));
        }
        if(id==R.id.Quizzes)
        {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Contact.this, Quizzes.class));

        }
        if(id==R.id.participate)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Contact.this, Participate.class));

        }
        if(id==R.id.Recipes)
        {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Contact.this, Recipes.class));

        }
        if(id==R.id.youtube)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Contact.this, Youtube.class));

        }

        if(id==R.id.ParentArticles)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Contact.this, ParentArticles.class));

        }


        if(id==R.id.Contact)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Contact.this, Contact.class));

        }
       if(id==R.id.Settings)
        {
            startActivity(new Intent(Contact.this, Settings.class));


        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {

        if (dl.isDrawerOpen(GravityCompat.START))
            dl.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();

    }


    /*public void tohome(MenuItem item) {
        startActivity(new Intent(Contact.this, MainActivity.class));


    }*/

    //ArrayList<ArticlePOJO> arrayList = new ArrayList<ArticlePOJO>();



    }

