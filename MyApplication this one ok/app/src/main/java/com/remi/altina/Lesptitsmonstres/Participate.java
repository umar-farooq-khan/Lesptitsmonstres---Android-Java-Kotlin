package com.remi.altina.Lesptitsmonstres;

import android.content.Intent;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Participate extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    DatabaseReference myref;
    TextView sometext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participate);
        sometext= (TextView) findViewById(R.id.sometext);
        Typeface custom_font = Typeface.createFromAsset(Participate.this.getAssets(),  "fonts/open.ttf");
        sometext.setTypeface(custom_font);
       // tvphotoheading.setTypeface(custom_font);
        //ImageView ivphoto=(ImageView) findViewById(R.id.iv_photo);
       // TextView tvphotosmall= (TextView) findViewById(R.id.textView4);
        //tvphotosmall.setTypeface(custom_font);
        final EditText edt_articlepost= (EditText) findViewById(R.id.postarticle_postarticleedt);
        Button btn_articlepost= (Button) findViewById(R.id.postarticle_postarticlebtn);

            btn_articlepost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (edt_articlepost.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Enter Some data", Toast.LENGTH_SHORT).show();
                    } else if (!edt_articlepost.getText().toString().equals("")) {

                        myref = FirebaseDatabase.getInstance().getReference("Participation Email");
                        myref.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                ParticipationPOJO participationPOJO = dataSnapshot.getValue(ParticipationPOJO.class);
                                //String mailto= (String) dataSnapshot.getValue();
                                // Toast.makeText(PostArticle.this, "get"+dataSnapshot.getValue(), Toast.LENGTH_SHORT).show();
                                //Toast.makeText(PostArticle.this, "get"+dataSnapshot.getChildren(), Toast.LENGTH_SHORT).show();
                                new SendMailTask(Participate.this).execute(participationPOJO.getFromEmail(),
                                        participationPOJO.getFromPassword(), participationPOJO.getToMailList(), "Participate to Lesptitsmonstres", edt_articlepost.getText().toString());


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
            });




        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        t.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(t);
        t.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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
            startActivity(new Intent(Participate.this, Activities.class));

        }
        if (id==R.id.Home)
        {
            //Toast.makeText(MainActivity.this, "Exercise", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Participate.this, MainActivity.class));
        }
        if(id==R.id.Article)
        {

            //Toast.makeText(MainActivity.this, "quizz", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Participate.this, ArticlesActivity.class));
        }
        if(id== R.id.Exercises)
        {
            // Toast.makeText(MainActivity.this, "addrecipe", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Participate.this, ExercisesActivity.class));
        }
        if(id==R.id.Quizzes)
        {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Participate.this, Quizzes.class));

        }
        if(id==R.id.participate)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Participate.this, Participate.class));

        }
        if(id==R.id.Recipes)
        {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Participate.this, Recipes.class));

        }
        if(id==R.id.Settings)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Participate.this, Settings.class));

        }

        if(id==R.id.ParentArticles)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Participate.this, ParentArticles.class));

        }


        if(id==R.id.Contact)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Participate.this, Contact.class));

        }
        if(id==R.id.youtube)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Participate.this, Youtube.class));


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



    public void postarticle(View view) {
        Intent intent = new Intent(Participate.this, PostArticle.class);
        startActivity(intent);
    }

}
