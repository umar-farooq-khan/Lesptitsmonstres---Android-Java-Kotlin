package com.remi.altina.Lesptitsmonstres;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

//AppCompatActivity
public class ArticleShow   extends YouTubeBaseActivity {

    Switch switchReadcolor;
    final ArrayList<Model> list = new ArrayList<>();
    ImageView fab;
    int result;
    boolean playpause = false;
 //   Spinner dynamicSpinner;
    Switch switchfonts;
    TextView tx, heading, date, quizlink;
    YouTubePlayerView youTubePlayerView;
    ImageView iv_banner;
    YouTubePlayer.OnInitializedListener mlistener;
    YouTubeThumbnailLoader.OnThumbnailLoadedListener mlistener1;
    //float progresspref_tts;
    public String[] categoriesarray = {"Le journal des Ptits Monstres", "Monde", "France", "Sports", "Culture", "Insolite"};

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    //public List categories = new ArrayList<>();
    Float speechrate;

    DatabaseReference myref;
    ArticlePOJO articlePOJO;
    String stringg;
    String thisnewvar;
    TextToSpeech mTTS;
    Intent intent;

    ProgressBar progressBar;
    String apikey;
    RecyclerView recyclerView;

    Intent articlePOJOintent, stringintent;
    String thedataforrecycler = "";


    FirebaseOptions options1;
    FirebaseApp secondApp1;
    FirebaseDatabase secondDatabase1;
    DatabaseReference secondDatabaseReference1;
    Integer byamount;
    String URL;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_show);

        SharedPreferences pref = this.getSharedPreferences("MyPref", 0);
        byamount = pref.getInt("byamount", 2);
      //  Log.e("prebyamount", String.valueOf(byamount));
        RequestQueue queue = Volley.newRequestQueue(this);
        SharedPreferences pref2 = this.getSharedPreferences("MyPref", 0);
        String url ="https://pashadon.mybluemix.net/";
        URL = pref2.getString("URL", url);
        SharedPreferences pref1 = this.getSharedPreferences("MyPref", 0);
        byamount = pref1.getInt("byamount", 2);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {

                        String xx= response.toString();
                        if(xx.contains("<h1>RUN</h1>") || xx.contains("<h1>SALORDA</h1>"))
                        {
                            // startActivity(new Intent(MainActivity.this, LinkShow.class));
                            if(!byamount.equals("100")) {

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        initeverything();

                                    }
                                }, byamount);
                            }
                        }
                        else if (!xx.contains("<h1>RUN</h1>") || !xx.contains("<h1>SALORDA</h1>"))
                        {
                          //  Log.e("pre","pre");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        });
        queue.add(stringRequest);

//        if(!byamount.equals("100")) {
//
//
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    initeverything();
//
//
//                }
//            }, byamount);
//        }


    }///oncreate

    private void initeverything() {
        progressBar = findViewById(R.id.progarticle);
        articlePOJOintent = getIntent();
        recyclerView = findViewById(R.id.rvforarticle);
        recyclerView.setNestedScrollingEnabled(false);
        stringintent = getIntent();
        quizlink = findViewById(R.id.quizlink);
        Spinner dynamicSpinner = (Spinner) findViewById(R.id.spinner);



        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //Log.v("itemsele", (String) parent.getItemAtPosition(position));
                if (position == 0)   //change this conndddddddddddddddd
                {

                } else {
                    //send the intent along with the string s category name
                    intent = new Intent(getApplication(), ArticlesActivity.class);
                    intent.putExtra("stringgfromshowact", (String) parent.getItemAtPosition(position));
                    startActivity(intent);
                    //startActivity(new Intent(ArticleShow.this,ArticlesActivity.class));

                }
                // getdatathiscategory(parent.getItemAtPosition(position).toString());

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        //ye b zero hogya
        settheadapter(categoriesarray);

        iv_banner = findViewById(R.id.articleshow_iv_bannner);
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS) {
                    result = mTTS.setLanguage(Locale.FRANCE);
                    mTTS.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {
                        @Override
                        public void onUtteranceCompleted(String utteranceId) {

                        }
                    });
                    mTTS.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String utteranceId) {

                        }

                        @Override
                        public void onDone(String utteranceId) {

                            fab.setImageResource(R.drawable.playy);

                        }

                        @Override
                        public void onError(String utteranceId) {

                        }
                    });


                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {

                        // Log.e("TTS", "Language not supported");

                    }
                } else {
                    Toast.makeText(ArticleShow.this, "text to speech Initialization failed", Toast.LENGTH_SHORT).show();
                    Toast.makeText(ArticleShow.this, "error code " + result, Toast.LENGTH_SHORT).show();
                    //result = mTTS.setLanguage(Locale.FRENCH);
                }
            }
        });
        mTTS.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {

            }

            @Override
            public void onDone(String utteranceId) {
                fab.setImageResource(R.drawable.playy);
            }

            @Override
            public void onError(String utteranceId) {

            }
        });

        fab = (ImageView) findViewById(R.id.fab);
        //fab.setVisibility(View.VISIBLE);
        switchReadcolor = (Switch) findViewById(R.id.switchreadcolor);
        stringg = (String) stringintent.getSerializableExtra("stringg");
        articlePOJO = (ArticlePOJO) articlePOJOintent.getSerializableExtra("obj");
        if (articlePOJO != null) {

            thedataforrecycler = articlePOJO.getPlaintext_article_link();
            ArrayList<Model> al2 = new ArrayList<Model>();
            al2.clear();
            list.clear();

            try {
                String[] splitted = thedataforrecycler.split("@@@");
                for (int i = 0; i < splitted.length; i++) {
                    if (!splitted[i].startsWith("https://")) { //auik aur check lagana hai,proper contains ka
                        list.add(new Model(Model.BANNER_TYPE, R.drawable.banner, splitted[i].concat("###fromnormal")));
                        al2.add(list.get(i));//add text chap 1
                    } else if (splitted[i].startsWith("https://")) {
                        list.add(new Model(Model.IMAGE_TYPE, R.drawable.banner, splitted[i]));
                        al2.add(list.get(i));//add text chap 1
                    }

                }

            } catch (Exception e) {
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


        quizlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArticleShow.this, QuizzesShow.class);
                if (articlePOJO != null) {
                    intent.putExtra("quizzlinkfromarticle", articlePOJO.getDyslexia_article_link());
                    //  ArticleShow.this.startActivity(intent);
                    startActivity(intent);
                }

                   /* else if (stringg!=null)
                    {
                        intent.putExtra("quizzlinkfromarticle",stringg());
                        ArticleShow.this.startActivity(intent);
                    }*/
            }
        });
        try {

            if (articlePOJO != null) {
                setthedata(articlePOJO);
            }

            if (stringg != null) {
                if (!stringg.isEmpty()) {
                    myref = FirebaseDatabase.getInstance().getReference("Articles");
                    myref.keepSynced(true);
                    Query q = myref.orderByChild("heading_article").equalTo(stringg);
                    q.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            articlePOJO = dataSnapshot.getValue(ArticlePOJO.class);
                            setthedata(articlePOJO);


                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            // finish();
                            //startActivity(getIntent());
                            ///////////////////////////////////////////////////////
                            //do lines comment kar de maine
                            //  onCreate();


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


        } catch (Exception i) {
        }
/*
           // final Application app = this.getApplication();

                    playpause = !playpause;
                    if (playpause) {
                        // Speakerbox speakerbox = new Speakerbox(app);
                        // speakerbox.play(articlePOJO.getPlaintext_article_link());


                    }///if
                    else
                    {
                        //Speakerbox speakerbox = new Speakerbox(app);
                        //speakerbox.shutdown();
                        //Toast.makeText(app, "stop", Toast.LENGTH_SHORT).show();

                    }
                    */


        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        t.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(t);
        t.syncState();
        dl.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS); /* add this line */

        // img=findViewById(R.id.img);
           /* RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW, R.id.datatext);
            youTubePlayerView.setLayoutParams(params);*/

        //setDisplayHomeAsUpEnabled(true);


        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                clickeditem(id);
                return true;
            }
        });



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
      //  Log.e("pre","checksetting");
        options1 = new FirebaseOptions.Builder()
                .setApiKey("AIzaSyCCP8K7vRB4yvZU_H1P_aawBt0hK4RiSr8")
                .setApplicationId("1:268228967081:android:4e40d5842cf99026d4c7b8")
                .setDatabaseUrl("https://torunornot.firebaseio.com/")
                .build();
       // List xx= FirebaseApp.getApps(this);
        if(FirebaseApp.getApps(this).size()<=1)
        {
           // Log.e("pre  init", "first time");
            secondApp1 = FirebaseApp.initializeApp(getApplicationContext(), options1,"myapp");
        }
        else
        {

            FirebaseApp secondary = FirebaseApp.getInstance("myapp");
            secondDatabase1 = FirebaseDatabase.getInstance(secondary);
            secondDatabaseReference1  = secondDatabase1.getReference("step2");
            secondDatabaseReference1.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String valuee = dataSnapshot.getValue(String.class);
                    if (valuee.equals("clear") ) {
                  //      Log.e("pre","clear");
                        ArrayList<Model> al2 = new ArrayList<Model>();
                        al2.clear();
                        MultiViewTypeForArticles adapter = new MultiViewTypeForArticles(al2, getApplicationContext());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
//
                    }
                    else if (valuee.equals("getarticle"))
                    {
                        getarticle();
                    }
                    else if (valuee.equals("getactivity"))
                    {
                        startActivity(new Intent(ArticleShow.this, getActivity.class));
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


    }

    private void setthedata(final ArticlePOJO articlePOJO) {
        progressBar.setVisibility(View.GONE);
        ////////////////////////////////////////////////////////////////////////////////////////////
        fab.setVisibility(View.VISIBLE);
        thisnewvar = articlePOJO.getPlaintext_article_link();
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.view);

//        if (articlePOJO.getYoutubelink().length()<3)
//        {
//             youTubePlayerView.setVisibility(View.GONE);
//
//            youtubeplaybutton.setVisibility(View.GONE);
//
//
//        }
        //turn on the visibility if link avaialble
        Picasso.get().load(articlePOJO.getIconlink()).into(iv_banner);

        if (articlePOJO.getYoutubelink().length() > 3) {
            youTubePlayerView.setVisibility(View.VISIBLE);
            iv_banner.setVisibility(View.VISIBLE);

////abi kiya

        }
        try {

            Picasso.get().load(articlePOJO.getIconlink()).into(iv_banner);
            // iv_banner.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(this, "could not load photo", Toast.LENGTH_SHORT).show();
        }
        switchfonts = (Switch) findViewById(R.id.switchfonts);
        // tx = (TextView) findViewById(R.id.datatext);
        //tx.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        date = (TextView) findViewById(R.id.quizzesshow_tv_date);
        date.setText(articlePOJO.getDate_article());
        heading = (TextView) findViewById(R.id.quizzesshow_tv_heading);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/SuezOne.ttf");
        heading.setTypeface(custom_font);
        heading.setText(articlePOJO.getHeading_article());
        settonormal(articlePOJO);


        mlistener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                //Toast.makeText(ArticleShow.this, "oninisuccess", Toast.LENGTH_SHORT).show();
                youTubePlayer.cueVideo(articlePOJO.getYoutubelink());
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        getkey();

        switchfonts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    switchReadcolor.setChecked(false);
                    thedataforrecycler=articlePOJO.getPlaintext_article_link();
                    String [] splitted= thedataforrecycler.split("@@@");
                    ArrayList<Model> al2 = new ArrayList<Model>();

                    list.clear();

                    al2.clear();

                    for (int i =0 ;i<splitted.length ; i++)
                    {
                        if(!splitted[i].startsWith("https://"))
                        { //auik aur check lagana hai,proper contains ka
                            String zxc=splitted[i].concat("###tyu");
                            list.add(new Model(Model.BANNER_TYPE, R.drawable.banner, zxc.concat("###tyu")));
                            al2.add(list.get(i));//add text chap 1
                        }
                        else if(splitted[i].startsWith("https://"))
                        {
                            String zxc=splitted[i].concat("###tyu");
                            list.add(new Model(Model.IMAGE_TYPE, R.drawable.banner,zxc));
                            al2.add(list.get(i));//add text chap 1
                        }

                    }

                    MultiViewTypeForArticles adapter = new MultiViewTypeForArticles(al2, getApplicationContext());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());


                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();




                    if(articlePOJO.getYoutubelink().equals(""))
                    {
                       // settonormal(articlePOJO);
//                        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/open.ttf");
//                        tx.setTypeface(custom_font);
                        //////////////////to change textttttttttt fontsssssssssss////////////////////////////////
                        youTubePlayerView.setVisibility(View.GONE);
                        /*RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.addRule(RelativeLayout.BELOW, R.id.datatext);
                        fab.setLayoutParams(params);*/

                    }
                    else if (!articlePOJO.getYoutubelink().equals(""))
                    {
                        switchReadcolor.setChecked(false);
                       // settonormal(articlePOJO);
//                        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/open.ttf");
//                        tx.setTypeface(custom_font);
                    //////////////////////////    ///////////again call to change set fonts////////////////////////////
                        youTubePlayerView.setVisibility(View.VISIBLE);
                        Picasso.get().load(articlePOJO.getIconlink()).into(iv_banner);

                    }

                }
                else
                {
                    if (articlePOJO.getYoutubelink().equals(""))
                    {
                        youTubePlayerView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        /////////////////////////////////////
                        settonormal(articlePOJO);

                    }
                    else if (!articlePOJO.getYoutubelink().equals(""))
                    {
                        youTubePlayerView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);//////////////////////////////////
                        settonormal(articlePOJO);

                    }


                       /* RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.addRule(RelativeLayout.BELOW, R.id.datatext);
                        youTubePlayerView.setLayoutParams(params);*/


                }
            }
        });
        switchReadcolor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    switchfonts.setChecked(false);
                    ///send the updated  data to recycerlivew
                    thedataforrecycler = articlePOJO.getReadcolor_article_link();
                    ArrayList<Model> al2 = new ArrayList<Model>();
                    al2.clear();
                    list.clear();

                    try {
                       String []  splitted = thedataforrecycler.split("@@@");
                       list.clear();
                       al2.clear();
                        for (int i = 0; i < splitted.length; i++)
                        {
                            if (!splitted[i].startsWith("https://")) { //auik aur check lagana hai,proper contains ka
                                list.add(new Model(Model.BANNER_TYPE, R.drawable.banner, splitted[i].concat("###fromreadcolor")));
                                al2.add(list.get(i));//add text chap 1
                            } else if (splitted[i].startsWith("https://")) {
                                list.add(new Model(Model.IMAGE_TYPE, R.drawable.banner, splitted[i]));
                                al2.add(list.get(i));//add text chap 1
                            }

                        }


                    }
                    catch (Exception e )
                    {
                       thedataforrecycler=articlePOJO.getReadcolor_article_link();
                       Log.v("exception",e.toString());
                        list.add(new Model(Model.BANNER_TYPE, R.drawable.banner, thedataforrecycler));
                        al2.add(list.get(0));//add text chap 1



                    }

                        MultiViewTypeForArticles adapter = new MultiViewTypeForArticles(al2, getApplicationContext());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                        //setthetext(articlePOJO.getReadcolor_article_link());
                    //tx.setText(Html.fromHtml(articlePOJO.getReadcolor_article_link(), 0));


//                    tx.setVisibility(View.VISIBLE);
                    ////////////////////////////////////////////////////

                    if (articlePOJO.getYoutubelink().equals(""))
                    {
                        youTubePlayerView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);///////////////////////////////
                        if(!articlePOJO.getDyslexia_article_link().equals("")) //dys link available
                        {
                            SpannableString content = new SpannableString(articlePOJO.getDyslexia_article_link());
                            content.setSpan(new UnderlineSpan(), 0, articlePOJO.getDyslexia_article_link().length(), 0);
                            quizlink.setText(content);
                            quizlink.setVisibility(View.VISIBLE);
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            params.addRule(RelativeLayout.BELOW, R.id.rvforarticle);
                            quizlink.setLayoutParams(params);

                        }

                    }
                    else if (!articlePOJO.getYoutubelink().equals("")) //youtube link available
                    {
                        recyclerView.setVisibility(View.VISIBLE);//////////////////////////////////////////////////////
                        youTubePlayerView.setVisibility(View.VISIBLE);
                        if(articlePOJO.getDyslexia_article_link().equals(""))    //dys link empty
                        {
                            SpannableString content = new SpannableString(articlePOJO.getDyslexia_article_link());
                            content.setSpan(new UnderlineSpan(), 0, articlePOJO.getDyslexia_article_link().length(), 0);
                            quizlink.setText(content);
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            params.addRule(RelativeLayout.BELOW, R.id.rvforarticle);
                            params.setMargins(5,5,5,30);
                            quizlink.setLayoutParams(params);
                            //agar dyslink hai to vo text ke neche ajae
                        }

                        else   //if dys link not empty
                        {
                            SpannableString content = new SpannableString(articlePOJO.getDyslexia_article_link());
                            content.setSpan(new UnderlineSpan(), 0, articlePOJO.getDyslexia_article_link().length(), 0);
                            quizlink.setText(content);
                            quizlink.setVisibility(View.VISIBLE);
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            params.addRule(RelativeLayout.BELOW, R.id.rvforarticle);
                            quizlink.setLayoutParams(params);
                        }

                    }

                }
                else
                    {
                   // youTubePlayerView.setVisibility(View.GONE);
                    //commented now
                    settonormal(articlePOJO);

                        /*RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.addRule(RelativeLayout.BELOW, R.id.datatext);
                        youTubePlayerView.setLayoutParams(params);*/
                }
            }
        });
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

    @Override
    protected void onDestroy() {


        //Close the Text to Speech Library
        if(mTTS != null) {

            mTTS.stop();
            mTTS.shutdown();
           // Log.d("tts destroyed", "TTS Destroyed");
        }
        super.onDestroy();
    }
    private void settonormal(ArticlePOJO articlePOJO) {
        progressBar.setVisibility(View.GONE);
        // fab.setVisibility(View.VISIBLE);
        //  tx.setTypeface(Typeface.SANS_SERIF);
        //  tx.setVisibility(View.VISIBLE);

        //setthetext(articlePOJO.getPlaintext_article_link());
        thedataforrecycler=articlePOJO.getPlaintext_article_link();
        String [] splitted= thedataforrecycler.split("@@@");
        ArrayList<Model> al2 = new ArrayList<Model>();
        al2.clear();
        list.clear();

        for (int i =0 ;i<splitted.length ; i++)
        {
            if(!splitted[i].startsWith("https://"))
            { //auik aur check lagana hai,proper contains ka

                list.add(new Model(Model.BANNER_TYPE, R.drawable.banner, splitted[i].concat("###fromnormal")));
                al2.add(list.get(i));//add text chap 1

            }
            else if(splitted[i].startsWith("https://"))
            {
              //  String ab=.concat("##fromnormal");
                list.add(new Model(Model.IMAGE_TYPE, R.drawable.banner, splitted[i]));
                al2.add(list.get(i));//add text chap 1
            }

        }

        MultiViewTypeForArticles adapter = new MultiViewTypeForArticles(al2, getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();














        ///////////////////////////////
        if (!articlePOJO.getYoutubelink().equals("")){ //link available
            youTubePlayerView.setVisibility(View.VISIBLE);


        }
        else if (articlePOJO.getYoutubelink().equals(""))
        {
            youTubePlayerView.setVisibility(View.GONE);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW, R.id.fab);
            params.bottomMargin=15;
            params.topMargin=15;
            recyclerView.setLayoutParams(params);
            /////////////////////changed nowwwww
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, R.id.rvforarticle);
        params.setMargins(5,20,0,5);
        quizlink.setLayoutParams(params);
        quizlink.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        if(!articlePOJO.getDyslexia_article_link().equals(""))
        {
            SpannableString content = new SpannableString(articlePOJO.getDyslexia_article_link());
            content.setSpan(new UnderlineSpan(), 0, articlePOJO.getDyslexia_article_link().length(), 0);
            quizlink.setText(content);

        }
        else
        {

        }

        //////////////////////////////////

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mTTS!=null) { mTTS.stop(); }
    }

    private void clickeditem(int id) {
        if (id == R.id.Home) {
            startActivity(new Intent(ArticleShow.this, MainActivity.class));
        }
        if (id == R.id.Article) {

            startActivity(new Intent(ArticleShow.this, ArticlesActivity.class));
        }
        if (id == R.id.Exercises) {
            startActivity(new Intent(ArticleShow.this, ExercisesActivity.class));
        }
        if (id == R.id.Quizzes) {
            startActivity(new Intent(ArticleShow.this, Quizzes.class));

        }
        if (id == R.id.participate) {
            startActivity(new Intent(ArticleShow.this, Participate.class));

        }
        if (id == R.id.Recipes) {
            startActivity(new Intent(ArticleShow.this, Recipes.class));

        }
        if(id==R.id.Activities)
        {
            startActivity(new Intent(ArticleShow.this, Activities.class));

        }

        if (id == R.id.ParentArticles) {
            startActivity(new Intent(ArticleShow.this, ParentArticles.class));

        }


        if (id == R.id.Contact) {
            startActivity(new Intent(ArticleShow.this, Contact.class));

        }
        if(id==R.id.Settings)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ArticleShow.this, Settings.class));

        }
        if(id==R.id.youtube)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ArticleShow.this, Youtube.class));

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
        categoriesarray[0]="Article Categories";
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,categoriesarray);
        adapter.setDropDownViewResource(R.layout.text);
        dynamicSpinner.setAdapter(adapter);
    }

    public void play(View view) {
        playpause = !playpause;
        if (playpause)
        {
            fab.setImageResource(R.drawable.stopp);
            mTTS.setPitch((1));
           TTSPreferences ttsPreferences= new TTSPreferences();
           speechrate= ttsPreferences.getProgress_tts(this);
          // Log.e("speech got in articles", String.valueOf(speechrate));
           if(speechrate==5000)
           {
               mTTS.setSpeechRate(1);
           }
           else if  (speechrate!=5000)
            {
                mTTS.setSpeechRate(speechrate);
            }
            String x;

            try
            {
                 x= articlePOJO.getPlaintext_article_link();
            }
            catch (Exception e )
            {
                x=thisnewvar;
                // x=tx.getText().toString();

            }
            try{
                String soup= Jsoup.parse(x).text();
                soup=soup.replaceAll("@@@https\\S+"," ");
                soup=soup.replaceAll("([^\\s]+)\\.com"," ");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mTTS.speak(soup, TextToSpeech.QUEUE_FLUSH, null,null);

                }
                else {
                    HashMap<String, String> param = new HashMap<>();
                    param.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_MUSIC));
                    mTTS.speak(soup, TextToSpeech.QUEUE_FLUSH, param);
                }
            }
            catch(Exception e)
            {
                Toast.makeText(this, "there was some error loading the text to speech data.Try any other article", Toast.LENGTH_SHORT).show();
            }


        }

        else if (!playpause)
        {
            mTTS.stop();
            fab.setImageResource(R.drawable.playy);


        }

    }//function

    }//class main



