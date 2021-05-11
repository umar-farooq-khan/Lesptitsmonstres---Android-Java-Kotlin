
package com.whatsup.hey;

import android.content.Intent;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ParentArticleShow extends YouTubeBaseActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    TextView tx,heading,date;
    ImageView iv_banner;
    String youtubeurl_string;

    ParentPOJO  parentPOJO;
    String stringg;

    Intent parentPOJOintent,stringintent;
    DatabaseReference myref;

    RecyclerView recyclerView; String thedataforrecycler=""; final ArrayList<Model> list= new ArrayList<>();

    YouTubePlayer.OnInitializedListener mlistener;
    YouTubePlayerView youTubePlayerView;

    private String[] youtubeurl;
    private String apikey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_article_show);

        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        t.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(t);
        t.syncState();
        dl.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS); /* add this line */
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setDisplayHomeAsUpEnabled(true);


        recyclerView=findViewById(R.id.rvforparents);
        recyclerView.setNestedScrollingEnabled(false);


        parentPOJOintent = getIntent();
        stringintent = getIntent();
        stringg = (String) stringintent.getSerializableExtra("stringg");
        // Toast.makeText(this, "stringdata"+stringg, Toast.LENGTH_SHORT).show();
        parentPOJO = (ParentPOJO) parentPOJOintent.getSerializableExtra("obj");
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.view);

        try {
            // Toast.makeText(ArticleShow.this, "try block", Toast.LENGTH_SHORT).show();

            if (parentPOJO!=null)
            {
                // Toast.makeText(ArticleShow.this, "greater than 5 obj", Toast.LENGTH_SHORT).show();
                setthedata(parentPOJO);

            }

            if (stringg!=null) {
                if (!stringg.isEmpty())
                {
                    // Toast.makeText(ArticleShow.this, "string not null", Toast.LENGTH_SHORT).show();
                    myref = FirebaseDatabase.getInstance().getReference("Parents");
                    Query q=myref.orderByChild("heading_article").equalTo(stringg);
                    q.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            parentPOJO =dataSnapshot.getValue(ParentPOJO.class);
                            //Toast.makeText(ArticleShow.this, "la ren"+parentPOJO.getPlaintext_article_link(), Toast.LENGTH_SHORT).show();
                            setthedata(parentPOJO);

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                          //  finish();
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


        }
        catch(Exception i ) {
            //  Toast.makeText(this, "exception"+i, Toast.LENGTH_SHORT).show();
        }




        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                clickeditem(id);
                return true;
            }
        });

    }


    private void setthedata(ParentPOJO parentPOJO) {

        iv_banner= (ImageView) findViewById(R.id.articleshow_iv_bannner);
      //  Log.e("below", "below");
        heading = (TextView) findViewById(R.id.quizzesshow_tv_heading);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/SuezOne.ttf");
        heading.setTypeface(custom_font);
        heading.setText(parentPOJO.getHeading_article());

        date = (TextView) findViewById(R.id.quizzesshow_tv_date);
        date.setText(parentPOJO.getDate_article());
      // String[] youtubeurlx = parentPOJO.getIconlink().split("@@@");

        Picasso.get().load(parentPOJO.getIconlink()).into(iv_banner);

        //Toast.makeText(this, "url"+parentPOJO.getIconlink(), Toast.LENGTH_SHORT).show();
        //if(parentPOJO.getIconlink().contains("@@@")) {
        //    try {
               // youtubeurl = parentPOJO.getIconlink().split("@@@");
                // youtubeurl_string  = youtubeurlx[1];
                playytvideo(parentPOJO.getYoutubelink());
                youTubePlayerView.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW, R.id.view);
                params.topMargin = 50;
                recyclerView.setLayoutParams(params);


      //      } catch (ArrayIndexOutOfBoundsException e) {
                //youTubePlayerView.setVisibility(View.GONE);
                //zaroroat nai wese hi gone hai
        if(parentPOJO.getYoutubelink().length()<3)
        {
            youTubePlayerView.setVisibility(View.GONE);
        }

       //     }
      //  }




        // tx = (TextView) findViewById(R.id.datatext);
       // tx.setMovementMethod(LinkMovementMethod.getInstance());
       // Spanned plaint= lirecolorsettext.setthetext(parentPOJO.getPlaintext_article());
       // tx.setText(plaint);
        String whichmode1="###fromnormal";
        /////////tx.setTypeface(Typeface.SANS_SERIF);
        //////////////  tx.setVisibility(View.VISIBLE);

            thedataforrecycler=parentPOJO.getPlaintext_article();
        ArrayList<Model> al2 = new ArrayList<Model>();
        al2.clear();
        list.clear();

        try {
            String[] splitted = thedataforrecycler.split("@@@");
            for (int i =0 ;i<splitted.length ; i++)
            {
                if(!splitted[i].contains("bit.ly"))
                { //auik aur check lagana hai,proper contains ka

                    list.add(new Model(Model.BANNER_TYPE, R.drawable.banner, splitted[i].concat(whichmode1)));
                    al2.add(list.get(i));//add text chap 1
                }
                else if(splitted[i].contains("bit.ly"))
                {
                    list.add(new Model(Model.IMAGE_TYPE, R.drawable.banner, splitted[i]));
                    al2.add(list.get(i));//add text chap 1
                }

            }

        }
        catch(Exception e )
        {
            list.add(new Model(Model.BANNER_TYPE, R.drawable.banner, thedataforrecycler));
            al2.add(list.get(0));
        }



        MultiViewTypeForArticles adapter = new MultiViewTypeForArticles(al2, getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



    }

    private void playytvideo(final String youtubeurl_string)
    {
        mlistener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                //Toast.makeText(ArticleShow.this, "oninisuccess", Toast.LENGTH_SHORT).show();

                try { youTubePlayer.cueVideo(youtubeurl_string); } catch (Exception e) {}
                //youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        getkey();

    }

    private void getkey() {
        myref = FirebaseDatabase.getInstance().getReference("YoutubeAPIKEY");
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                YoutubeApiKeyPOJO youtubeApiKeyPOJO = dataSnapshot.getValue(YoutubeApiKeyPOJO.class);
                apikey=youtubeApiKeyPOJO.getKey();
                //   Log.e("getkeywithin",apikey);
                byte[] decodedApiKey = Base64.decode(apikey, Base64.DEFAULT);
                String decodedApiKeyString = Base64.decode(apikey, Base64.DEFAULT).toString();
                youTubePlayerView.initialize(decodedApiKeyString, mlistener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void clickeditem(int id) {
        if (id == R.id.Home) {
            //Toast.makeText(MainActivity.this, "Exercise", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ParentArticleShow.this, MainActivity.class));
        }
        if (id == R.id.Article) {

            //Toast.makeText(MainActivity.this, "quizz", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ParentArticleShow.this, ArticlesActivity.class));
        }
        if (id == R.id.Exercises) {
            // Toast.makeText(MainActivity.this, "addrecipe", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ParentArticleShow.this, ExercisesActivity.class));
        }
        if (id == R.id.Quizzes) {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ParentArticleShow.this, Quizzes.class));

        }
        if (id == R.id.participate) {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ParentArticleShow.this, Participate.class));

        }
        if (id == R.id.Recipes) {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ParentArticleShow.this, Recipes.class));

        }
        if (id == R.id.youtube) {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ParentArticleShow.this, Youtube.class));

        }

        if (id == R.id.ParentArticles) {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ParentArticleShow.this, ParentArticles.class));

        }


        if (id == R.id.Contact) {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ParentArticleShow.this, Contact.class));

        }
        if (id == R.id.Settings) {
            startActivity(new Intent(ParentArticleShow.this, Settings.class));

        }
        if (id == R.id.Activities) {
            startActivity(new Intent(ParentArticleShow.this, Activities.class));

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

    /*public void tohome(MenuItem item) {
        startActivity(new Intent(ArticlesActivity.this, MainActivity.class));


    }*/

    //ArrayList<ArticlePOJO> arrayList = new ArrayList<ArticlePOJO>();

}

