package com.whatsup.hey;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;

public class PostArticle extends AppCompatActivity {
    DatabaseReference myref;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_article);
                dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        t.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    private void clickeditem(int id) {
        if(id==R.id.Activities)
        {
            startActivity(new Intent(PostArticle.this, Activities.class));

        }
        if (id==R.id.Home)
        {
            //Toast.makeText(MainActivity.this, "Exercise", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostArticle.this, MainActivity.class));
        }
        if(id==R.id.Article)
        {

            //Toast.makeText(MainActivity.this, "quizz", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostArticle.this, ArticlesActivity.class));
        }
        if(id== R.id.Exercises)
        {
            // Toast.makeText(MainActivity.this, "addrecipe", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostArticle.this, ExercisesActivity.class));
        }
        if(id==R.id.Quizzes)
        {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostArticle.this, Quizzes.class));

        }
        if(id==R.id.participate)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostArticle.this, Participate.class));

        }
        if(id==R.id.Recipes)
        {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostArticle.this, Recipes.class));

        }
        if(id==R.id.youtube)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostArticle.this, Youtube.class));

        }

        if(id==R.id.ParentArticles)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostArticle.this, ParentArticles.class));

        }


        if(id==R.id.Contact)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostArticle.this, Contact.class));

        }
        if(id==R.id.Settings)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostArticle.this, Settings.class));

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

    /*public void tohome(MenuItem item) {
        startActivity(new Intent(ParentArticles.this, MainActivity.class));


    }*/

//ArrayList<ArticlePOJO> arrayList = new ArrayList<ArticlePOJO>();


}
