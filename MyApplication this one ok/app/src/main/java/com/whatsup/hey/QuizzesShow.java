package com.whatsup.hey;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class QuizzesShow extends AppCompatActivity {
    WebView webView;
    TextView tv_heading,tv_date;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    QuizzPOJO  quizzPOJO;
    String stringg,quizzlinkarticleString;

    Intent quizzPOJOintent,stringintent,quizzlinkarticlesintent;
    DatabaseReference myref;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizzes_show);

        webView= (WebView) findViewById(R.id.quizzesshow_webv);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);



        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        t.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dl.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS); /* add this line */

        progressBar= (ProgressBar) findViewById(R.id.prog);
        quizzPOJOintent = getIntent();
        stringintent = getIntent();
        quizzlinkarticlesintent= getIntent();
        tv_heading= (TextView) findViewById(R.id.quizzesshow_tv_heading);
        tv_date= (TextView) findViewById(R.id.quizzesshow_tv_date);
        quizzlinkarticleString= (String) quizzlinkarticlesintent.getSerializableExtra("quizzlinkfromarticle");
        //stringg=quizzlinkarticleString;
        stringg = (String) stringintent.getSerializableExtra("stringg");
        // Toast.makeText(this, "stringdata"+stringg, Toast.LENGTH_SHORT).show();
        quizzPOJO = (QuizzPOJO) quizzPOJOintent.getSerializableExtra("obj");

        //Toast.makeText(this, "obj "+quizzPOJO, Toast.LENGTH_SHORT).show();
        try {
            // Toast.makeText(ArticleShow.this, "try block", Toast.LENGTH_SHORT).show();

            if (quizzPOJO!=null)
            {
                // Toast.makeText(ArticleShow.this, "greater than 5 obj", Toast.LENGTH_SHORT).show();
                setthedata(quizzPOJO);
            }

            if (stringg!=null) {
                if (!stringg.isEmpty())
                {
                    // Toast.makeText(QuizzesShow.this, "stringg"+stringg, Toast.LENGTH_SHORT).show();
                    myref = FirebaseDatabase.getInstance().getReference("Quizz");
                    Query q=myref.orderByChild("heading").equalTo(stringg);
                    q.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            quizzPOJO =dataSnapshot.getValue(QuizzPOJO.class);
                        //    Toast.makeText(QuizzesShow.this, "stringg"+dataSnapshot.getValue(), Toast.LENGTH_SHORT).show();
                            setthedata(quizzPOJO);

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                            finish();
//                            startActivity(getIntent());
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


                       /* myref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
    */
                }
            }

            else if (quizzlinkarticleString!=null)
            {
                // Toast.makeText(QuizzesShow.this, "stringg"+stringg, Toast.LENGTH_SHORT).show();
                myref = FirebaseDatabase.getInstance().getReference("Quizz");
                Query q=myref.orderByChild("heading").equalTo(quizzlinkarticleString);
                q.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                     QuizzPOJO   quizzPOJO =dataSnapshot.getValue(QuizzPOJO.class);
                        //    Toast.makeText(QuizzesShow.this, "stringg"+dataSnapshot.getValue(), Toast.LENGTH_SHORT).show();
                        setthedata(quizzPOJO);

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                        finish();
//                        startActivity(getIntent());
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
        catch(Exception i ) {
            //  Toast.makeText(this, "exception"+i, Toast.LENGTH_SHORT).show();
        }




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

    private void setthedata(QuizzPOJO quizzPOJO) {
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                // Page loading started
                // Do something
                progressBar.setVisibility(View.VISIBLE);

            }

            @Override
            public void onPageFinished(WebView view, String url){
                // Page loading finished
               // Toast.makeText(getApplicationContext(),"Page Loaded.",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
        webView.loadUrl(quizzPOJO.getQuizzlink());


                tv_heading.setText(quizzPOJO.getHeading());
                tv_date.setText(quizzPOJO.getDate());
                Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/SuezOne.ttf");
                tv_heading.setTypeface(custom_font);



    }

    private void clickeditem(int id) {
        if(id==R.id.Activities)
        {
            startActivity(new Intent(QuizzesShow.this, Activities.class));

        }
        if (id==R.id.Home)
        {
            //Toast.makeText(MainActivity.this, "Exercise", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(QuizzesShow.this, MainActivity.class));
        }
        if(id==R.id.Article)
        {

            //Toast.makeText(MainActivity.this, "quizz", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(QuizzesShow.this, ArticlesActivity.class));
        }
        if(id== R.id.Exercises)
        {
            // Toast.makeText(MainActivity.this, "addrecipe", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(QuizzesShow.this, ExercisesActivity.class));
        }
        if(id==R.id.Quizzes)
        {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(QuizzesShow.this, Quizzes.class));

        }
        if(id==R.id.participate)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(QuizzesShow.this, Participate.class));

        }
        if(id==R.id.Recipes)
        {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(QuizzesShow.this, Recipes.class));

        }
        if(id==R.id.youtube)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(QuizzesShow.this, Youtube.class));

        }


        if(id==R.id.ParentArticles)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(QuizzesShow.this, ParentArticles.class));

        }

        if(id==R.id.Contact)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(QuizzesShow.this, Contact.class));

        }
        if(id==R.id.Settings)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(QuizzesShow.this, Settings.class));

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
        startActivity(new Intent(ArticlesActivity.this, MainActivity.class));


    }*/
}
//ArrayList<ArticlePOJO> arrayList = new ArrayList<ArticlePOJO>();
