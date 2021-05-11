package com.whatsup.hey;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Base64;

import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class YoutubeShow extends YouTubeBaseActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    YouTubePlayer.OnInitializedListener mlistener;
    YouTubePlayerView youTubePlayerView;
    Intent youtubeurl_intent,apikey_intent,videodesc_intent;

    String youtubelink,apikey,videodesc_str;
    TextView videodesc;
    DatabaseReference myref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_show);
//        dl = (DrawerLayout) findViewById(R.id.activity_main);
//
//        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
//        t.setDrawerIndicatorEnabled(true);
//        dl.addDrawerListener(t);
//        t.syncState();

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubeview);
        videodesc = (TextView) findViewById(R.id.videodesc);
      //  videodesc.setText("rgdrtgdrrt sdrtdrtdrt drtydr dydtyd dydtydt dydtyd ydydyd");
        myref=  FirebaseDatabase.getInstance().getReference("Youtube");


        youtubeurl_intent = getIntent();
        apikey_intent = getIntent();
        videodesc_intent=getIntent();
        youtubelink = (String) youtubeurl_intent.getSerializableExtra("videourl");
        apikey = (String) apikey_intent.getSerializableExtra("apikey");
        videodesc_str= (String) videodesc_intent.getSerializableExtra("videodesc");
        if(!videodesc_str.equals("None")) {
           lirecolorsettext desc_lirecolor= new lirecolorsettext();
           videodesc.setText(desc_lirecolor.setthetext(videodesc_str));
            videodesc.setLinksClickable(true);
            videodesc.setMovementMethod(LinkMovementMethod.getInstance());

        }


        mlistener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                //Toast.makeText(ArticleShow.this, "oninisuccess", Toast.LENGTH_SHORT).show();
                youTubePlayer.loadVideo(youtubelink);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        //byte[] decodedApiKey = Base64.decode(apikey, Base64.DEFAULT);
        // Now use `decodedApiKey` in your codebase.
        String decodedApiKeyString = Base64.decode(apikey, Base64.DEFAULT).toString();
        youTubePlayerView.initialize(decodedApiKeyString, mlistener);

//        nv = (NavigationView) findViewById(R.id.nv);
//        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item)
//            {
//                int id=item.getItemId();
//                clickeditem(id);
//                return true;
//            }
//        });
    }
//    private void clickeditem(int id) {
//        if (id==R.id.Home)
//        {
//            //Toast.makeText(MainActivity.this, "Exercise", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(YoutubeShow.this, MainActivity.class));
//        }
//        if(id==R.id.Article)
//        {
//
//            //Toast.makeText(MainActivity.this, "quizz", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(YoutubeShow.this, ArticlesActivity.class));
//        }
//        if(id== R.id.Exercises)
//        {
//            // Toast.makeText(MainActivity.this, "addrecipe", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(YoutubeShow.this, ExercisesActivity.class));
//        }
//        if(id== R.id.Activities)
//        {
//            // Toast.makeText(MainActivity.this, "addrecipe", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(YoutubeShow.this, Activities.class));
//        }
//        if(id==R.id.Quizzes)
//        {
//            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(YoutubeShow.this, Quizzes.class));
//
//        }
//        if(id==R.id.participate)
//        {
//            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(YoutubeShow.this, Participate.class));
//
//        }
//        if(id==R.id.Recipes)
//        {
//            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(YoutubeShow.this, Recipes.class));
//
//        }
//        if(id==R.id.youtube)
//        {
//            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(YoutubeShow.this, Youtube.class));
//
//        }
//
//        if(id==R.id.ParentArticles)
//        {
//            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(YoutubeShow.this, ParentArticles.class));
//
//        }
//
//
//        if(id==R.id.Contact)
//        {
//            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(YoutubeShow.this, Contact.class));
//
//        }
//        if(id==R.id.Settings)
//        {
//            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(YoutubeShow.this, Settings.class));
//
//        }

    }



