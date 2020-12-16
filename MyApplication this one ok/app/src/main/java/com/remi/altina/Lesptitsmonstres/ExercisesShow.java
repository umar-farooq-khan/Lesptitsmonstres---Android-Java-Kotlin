package com.remi.altina.Lesptitsmonstres;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
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
import java.util.concurrent.ExecutionException;

public class ExercisesShow extends YouTubeBaseActivity {
    TextView tx,heading,date;
    ImageView iv_banner;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    ExercisePojo  exercisePojo;
    String stringg;
    Spinner dynamicSpinner;
    Intent exercisePojointent,stringintent;
    DatabaseReference myref;
    String stringfromshowact;
    Intent stringintentfromshowact;
    Intent intent;
    public List categories= new ArrayList<>();
    public String [] categoriesarray={"Les Ptits Exercices","CP", "CE1","CE2","CM1","CM2"};
    TextView pdflinkinexercise;


    ImageView fab;
    int result;
    boolean playpause=false;
    String thisnewvar="";
    TextToSpeech mTTS;
    int progresspref;

    YouTubePlayer.OnInitializedListener mlistener;
    YouTubePlayerView youTubePlayerView;

    private String[] youtubeurl;
    private String apikey;
    YoutubeLinkExercisesPOJO youtubeLinkExercisesPOJO= new YoutubeLinkExercisesPOJO();
    private float speechrate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_show);

        tx = (TextView) findViewById(R.id.datatextexsh);
        SharedPreferences pref = this.getSharedPreferences("MyPref", 0);
        progresspref=pref.getInt("progressvar", 22);
        tx.setTextSize(progresspref);
        heading = (TextView) findViewById(R.id.quizzesshow_tv_headingexsh);
        date = (TextView) findViewById(R.id.quizzesshow_tv_dateexsh);
        iv_banner = (ImageView) findViewById(R.id.articleshow_iv_bannnerexsh);
        dynamicSpinner = (Spinner) findViewById(R.id.spinnerexsh);
        pdflinkinexercise=findViewById(R.id.tvlinkinexercise);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.view);

        pdflinkinexercise.setMovementMethod(LinkMovementMethod.getInstance());


        exercisePojointent = getIntent();
        stringintent = getIntent();
        stringintent = getIntent();
        stringfromshowact = (String) stringintent.getSerializableExtra("stringgfromshowact");
        stringg = (String) stringintent.getSerializableExtra("stringg");
        exercisePojo = (ExercisePojo) exercisePojointent.getSerializableExtra("obj");
        dynamicSpinner = (Spinner) findViewById(R.id.spinnerexsh);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesarray);
        adapter.setDropDownViewResource(R.layout.text);
        dynamicSpinner.setAdapter(adapter);
        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        t.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(t);
        t.syncState();
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        setuptexttospeech();


        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("itemsele", (String) parent.getItemAtPosition(position));
                if (position == 0)   //change this conndddddddddddddddd
                {

                } else {
                    //send the intent along with the string s category name
                    intent = new Intent(getApplication(), ExercisesActivity.class);
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


        if (exercisePojo != null) {
            // Toast.makeText(getApplicationContext(), ""+exercisePojo.getPcategory(), Toast.LENGTH_SHORT).show();
            try {
                if (exercisePojo.getPcategory().equals("anyother")) {
                    try {
                        Picasso.get().load(R.drawable.exercises).into(iv_banner);
                    } catch (Exception e) {
                    }
                }
                if (exercisePojo.getPcategory().equals("CP")) {
                    try {
                        Picasso.get().load(R.drawable.cpbanner).into(iv_banner);
                    } catch (Exception e) {
                    }
                }
                if (exercisePojo.getPcategory().equals("CE1")) {

                    try {
                        Picasso.get().load(R.drawable.ce1).into(iv_banner);
                    } catch (Exception e) {
                    }
                }
                if (exercisePojo.getPcategory().equals("CE2")) {

                    try {
                        Picasso.get().load(R.drawable.ce2).into(iv_banner);
                    } catch (Exception e) {
                    }
                }
                if (exercisePojo.getPcategory().equals("CM1")) {
                    try {
                        Picasso.get().load(R.drawable.cm1).into(iv_banner);
                    } catch (Exception e) {
                    }
                }

                if (exercisePojo.getPcategory().equals("CM2")) {
                    try {
                        Picasso.get().load(R.drawable.cm2).into(iv_banner);
                    } catch (Exception e) {
                    }
                }

            } catch (Exception e) {
            }
            tx.setVisibility(View.VISIBLE);
            Typeface custom_font2 = Typeface.createFromAsset(getAssets(), "fonts/open.ttf");
            tx.setTypeface(custom_font2);
            Spanned text1 = lirecolorsettext.setthetext(exercisePojo.getPsimpleblack());

            CharSequence sequence = Html.fromHtml(exercisePojo.getPsimpleblack());
            SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
            URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
            for (URLSpan span : urls) {
                makeLinkClickable(strBuilder, span);
            }

            tx.setText(strBuilder);
            tx.setMovementMethod(LinkMovementMethod.getInstance());
            date.setText(exercisePojo.getPdate());
            heading.setText(exercisePojo.getPheading());
            Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/SuezOne.ttf");
            heading.setTypeface(custom_font);

            if (!exercisePojo.getPiconlink().equals("")) {
                // Log.e("notnull","notnull");
                pdflinkinexercise.setVisibility(View.VISIBLE);
                Spanned xx = lirecolorsettext.setthetext(exercisePojo.getPiconlink());
                pdflinkinexercise.setText(xx);
            }

            myref = FirebaseDatabase.getInstance().getReference("ExercisesYTLink");
            Query exytlinkquery = myref.orderByChild("title").equalTo(exercisePojo.getPheading());
            exytlinkquery.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
                {
               YoutubeLinkExercisesPOJO     youtubeLinkExercisesPOJO = dataSnapshot.getValue(YoutubeLinkExercisesPOJO.class);
                    if (youtubeLinkExercisesPOJO.getTitle().equals(exercisePojo.getPheading())) {
                        playytvideo(youtubeLinkExercisesPOJO.getYtlink());
                        youTubePlayerView.setVisibility(View.VISIBLE);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.addRule(RelativeLayout.BELOW, R.id.view);
                        params.topMargin=15;
                        tx.setLayoutParams(params);
                    } else {

                        youTubePlayerView.setVisibility(View.GONE);
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

            if (stringg != null) {
                if (!stringg.isEmpty()) {
                    myref = FirebaseDatabase.getInstance().getReference("Exercises");
                    Query q = myref.orderByChild("pheading").equalTo(stringg);
                    q.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            exercisePojo = dataSnapshot.getValue(ExercisePojo.class);
                            try {

                                if (exercisePojo.getPcategory().equals("anyother")) {
                                    Picasso.get().load(R.drawable.exercises).into(iv_banner);
                                }
                                if (exercisePojo.getPcategory().equals("CP")) {
                                    Picasso.get().load(R.drawable.cpbanner).into(iv_banner);
                                }
                                if (exercisePojo.getPcategory().equals("CE1")) {
                                    try {
                                        Picasso.get().load(R.drawable.ce1).into(iv_banner);
                                    } catch (Exception e) {
                                    }
                                }
                                if (exercisePojo.getPcategory().equals("CE2")) {

                                    Picasso.get().load(R.drawable.ce2).into(iv_banner);
                                }
                                if (exercisePojo.getPcategory().equals("CM1")) {
                                    Picasso.get().load(R.drawable.cm1).into(iv_banner);
                                }

                                if (exercisePojo.getPcategory().equals("CM2")) {
                                    Picasso.get().load(R.drawable.cm2).into(iv_banner);
                                }
                            } catch (Exception e) {
                                Toast.makeText(ExercisesShow.this, "could not load exercises photo", Toast.LENGTH_SHORT).show();
                            }
                            tx.setVisibility(View.VISIBLE);
                            Typeface custom_font2 = Typeface.createFromAsset(getAssets(), "fonts/open.ttf");
                            tx.setTypeface(custom_font2);
                            Spanned text12 = lirecolorsettext.setthetext(exercisePojo.getPsimpleblack());
                            tx.setText(text12);
                            tx.setMovementMethod(LinkMovementMethod.getInstance());
                            date.setText(exercisePojo.getPdate());
                            heading.setText(exercisePojo.getPheading());
                            Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/SuezOne.ttf");
                            heading.setTypeface(custom_font);
                            playthevideo(exercisePojo);

                            /////////////////////////////////////
                            if (!exercisePojo.getPiconlink().equals("")) {
                              //  Log.e("notnull", "notnull");
                                pdflinkinexercise.setVisibility(View.VISIBLE);
                                Spanned xx = lirecolorsettext.setthetext(exercisePojo.getPiconlink());
                                pdflinkinexercise.setText(xx);

                            }

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            //  finish();
                            // startActivity(getIntent());
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
        }

    public void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span) {
      //  Log.e("pre", "pref");
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        ClickableSpan clickable = new ClickableSpan() {
            public void onClick(View view) {
                // Do something with span.getURL() to handle the link click...
            //    Log.e("pre", span.getURL().toLowerCase());
                //Toast.makeText(mContext, "link got "+span.getURL(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ExercisesShow.this, LinkShow.class);
                intent.putExtra("websitelink", span.getURL());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        };
        strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.removeSpan(span);
    }

    private void playthevideo(final ExercisePojo exercisePojo) {

        myref = FirebaseDatabase.getInstance().getReference("ExercisesYTLink");
        Query exytlinkquery = myref.orderByChild("title").equalTo(exercisePojo.getPheading());
        exytlinkquery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                youtubeLinkExercisesPOJO = dataSnapshot.getValue(YoutubeLinkExercisesPOJO.class);
                if (youtubeLinkExercisesPOJO.getTitle().equals(exercisePojo.getPheading())) {
                //    Log.e("in if","in if");

                    playytvideo(youtubeLinkExercisesPOJO.getYtlink());
                    youTubePlayerView.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.BELOW, R.id.view);
                    params.topMargin=15;
                    tx.setLayoutParams(params);
                } else {

                    youTubePlayerView.setVisibility(View.GONE);
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


    private void playytvideo(final String youtubeurl)
    {
        mlistener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                //Toast.makeText(ArticleShow.this, "oninisuccess", Toast.LENGTH_SHORT).show();

                youTubePlayer.cueVideo(youtubeurl);
                //youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        getkey();

    }


    @Override
    protected void onDestroy() {

        //Close the Text to Speech Library
        if(mTTS != null) {

            mTTS.stop();
            mTTS.shutdown();
            //  Log.d("tts destroyed", "TTS Destroyed");
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mTTS!=null) { mTTS.stop(); }

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


    private void setuptexttospeech() {
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS) {
                    result = mTTS.setLanguage(Locale.FRANCE);
                    mTTS.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {
                        @Override
                        public void onUtteranceCompleted(String utteranceId) {
                            //   Toast.makeText(ArticleShow.this, "fin", Toast.LENGTH_SHORT).show();

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


                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                    {
                       // Toast.makeText(getApplicationContext(), "language not supported", Toast.LENGTH_SHORT).show();
                       // Toast.makeText(getApplicationContext(), "error code "+ result, Toast.LENGTH_SHORT).show();

                       // Log.e("TTS", "Language not supported");

                    }
                } else  {
                    Toast.makeText(getApplicationContext(), "Initialization failed", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "error code "+result, Toast.LENGTH_SHORT).show();
                    //result = mTTS.setLanguage(Locale.FRENCH);

                }
            }
        });
        mTTS.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                //Toast.makeText(ArticleShow.this, "started", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onDone(String utteranceId) {
                //  Toast.makeText(ArticleShow.this, "done", Toast.LENGTH_SHORT).show();

                fab.setImageResource(R.drawable.playy);

            }

            @Override
            public void onError(String utteranceId) {

            }
        });

        fab = (ImageView) findViewById(R.id.fab);


    }


    private void clickeditem(int id) {
        if(id==R.id.Activities)
        {
            startActivity(new Intent(ExercisesShow.this, Activities.class));

        }
        if (id==R.id.Home)
        {
            startActivity(new Intent(ExercisesShow.this, MainActivity.class));
        }
        if(id==R.id.Article)
        {

            startActivity(new Intent(ExercisesShow.this, ArticlesActivity.class));
        }
        if(id== R.id.Exercises)
        {
            startActivity(new Intent(ExercisesShow.this, ExercisesActivity.class));
        }
        if(id==R.id.Quizzes)
        {
            startActivity(new Intent(ExercisesShow.this, Quizzes.class));

        }
        if(id==R.id.participate)
        {
            startActivity(new Intent(ExercisesShow.this, Participate.class));

        }
        if(id==R.id.Recipes)
        {
            startActivity(new Intent(ExercisesShow.this, Recipes.class));

        }
       if(id==R.id.youtube)
        {

            startActivity(new Intent(ExercisesShow.this, Youtube.class));

        }

        if(id==R.id.ParentArticles)
        {
            startActivity(new Intent(ExercisesShow.this, ParentArticles.class));

        }


        if(id==R.id.Contact)
        {

            startActivity(new Intent(ExercisesShow.this, Contact.class));

        }
        if(id==R.id.Settings)
        {
            startActivity(new Intent(ExercisesShow.this, Settings.class));


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

    public void play(View view) {
        playpause = !playpause;
        if (playpause)
        {
            fab.setImageResource(R.drawable.stopp);

            mTTS.setPitch((1));
            TTSPreferences ttsPreferences= new TTSPreferences();
            speechrate= ttsPreferences.getProgress_tts(this);
           // Log.e("speech got in exercises", String.valueOf(speechrate));

            if(speechrate==5000)
            {
                mTTS.setSpeechRate(1);
            }
            else if  (speechrate!=5000)
            {
                mTTS.setSpeechRate(speechrate);
            }            String x;

            try
            {
                x= exercisePojo.getPsimpleblack();

            }
            catch (Exception e)
            {
                x=thisnewvar;
                // x=tx.getText().toString();

            }
            try{
                String soup= Jsoup.parse(x).text();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //Toast.makeText(this, "greater", Toast.LENGTH_SHORT).show();
                    mTTS.speak(soup, TextToSpeech.QUEUE_FLUSH, null,null);

                }
                else {
                    //  Toast.makeText(this, "lesser", Toast.LENGTH_SHORT).show();
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


}

