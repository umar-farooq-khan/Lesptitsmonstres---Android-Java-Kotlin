package com.whatsup.hey;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class ActivitiesShow extends AppCompatActivity {
    TextToSpeech mTTS;
    int result;
    ImageView fab;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    String thisnewvar="";

    boolean playpause=false;
    private NavigationView nv;

    TextView tx,heading,date;

    ImageView iv_banner;
    TextView pdflinktv;

    ActivitiesPOJO  activitiesPOJO;
    String stringg;

    Intent recipePOJOintent,stringintent;
    DatabaseReference myref;
   // int progresspref;

    RecyclerView recyclerView; String thedataforrecycler=""; final ArrayList<Model> list= new ArrayList<>();
    private float speechrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_show);

        recyclerView = findViewById(R.id.rvforrecipes);
        recyclerView.setNestedScrollingEnabled(false);
        pdflinktv = (TextView) findViewById(R.id.tvlinks);
        fab = (ImageView) findViewById(R.id.fab);

        iv_banner = (ImageView) findViewById(R.id.articleshow_iv_bannner);
        recipePOJOintent = getIntent();
        stringintent = getIntent();


        stringg = (String) stringintent.getSerializableExtra("stringg");
        activitiesPOJO = (ActivitiesPOJO) recipePOJOintent.getSerializableExtra("obj");
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


                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        // Toast.makeText(getApplicationContext(), "language not supported", Toast.LENGTH_SHORT).show();
                        // Toast.makeText(getApplicationContext(), "error code "+ result, Toast.LENGTH_SHORT).show();

                        // Log.e("TTS", "Language not supported");

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Initialization failed", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "error code " + result, Toast.LENGTH_SHORT).show();
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


        try {

            if (activitiesPOJO != null) {
                // setthedata(recipePOJO);
                /////////////////////////////////
                settonormal(activitiesPOJO, "###tyu");
            }

            if (stringg != null) {

                if (!stringg.isEmpty()) {
                    myref = FirebaseDatabase.getInstance().getReference("Activities");
                    Query q=myref.orderByChild("heading").equalTo(stringg);
                    q.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            activitiesPOJO = dataSnapshot.getValue(ActivitiesPOJO.class);
                            ////setthedata(recipePOJO);
                            //abhiiiii kiya
                            heading = (TextView) findViewById(R.id.quizzesshow_tv_heading);
                            Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/SuezOne.ttf");
                            heading.setTypeface(custom_font);
                            heading.setText(activitiesPOJO.getHeading());
                            date = (TextView) findViewById(R.id.quizzesshow_tv_date);

                            try {
                                Picasso.get().load(activitiesPOJO.getBannerpic()).networkPolicy(NetworkPolicy.OFFLINE).into(iv_banner);

                            } catch (Exception e) {

                            }
                            date.setText(activitiesPOJO.getDate());
                            /////////tx.setTypeface(Typeface.SANS_SERIF);
                            //////////////  tx.setVisibility(View.VISIBLE);

                            try {
                                thedataforrecycler = activitiesPOJO.getSimpletext();
                            } catch (Exception e) {

                            }

                            ArrayList<Model> al2 = new ArrayList<Model>();
                            al2.clear();
                            list.clear();

                            try {
                                String[] splitted = thedataforrecycler.split("@@@");
                                for (int i = 0; i < splitted.length; i++) {
                                    if (!splitted[i].contains("bit.ly")) { //auik aur check lagana hai,proper contains ka

                                        list.add(new Model(Model.BANNER_TYPE, R.drawable.banner, splitted[i].concat("###tyu")));
                                        al2.add(list.get(i));//add text chap 1
                                    } else if (splitted[i].contains("bit.ly")) {
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


                            // tx.setText(Html.fromHtml(recipePOJO.getPlaintext_article(),0));
//       Spanned plaintextt= lirecolorsettext.setthetext(recipePOJO.getPlaintext_article());
//        tx.setText(plaintextt);

                            if (!activitiesPOJO.getPdflink().equals("")) {
                                // imginarticle.setVisibility(View.VISIBLE);
                                pdflinktv.setVisibility(View.VISIBLE);
                                Spanned xx = lirecolorsettext.setthetext(activitiesPOJO.getPdflink());
                                pdflinktv.setText(xx);
                                pdflinktv.setMovementMethod(LinkMovementMethod.getInstance());

                            }
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
                }
            }
        }
            catch(Exception i )
            {

            }

        //fab = (FloatingActionButton) findViewById(R.id.fab);
        // fab.setVisibility(View.GONE);

        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        t.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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


    }//oncreate


    @Override
    protected void onPause() {
        super.onPause();
        if(mTTS!=null) { mTTS.stop(); }


    }

    private void setthedata(final RecipePOJO recipePOJO) {


        try {

            Picasso.get().load(recipePOJO.getImageicon()).networkPolicy(NetworkPolicy.OFFLINE).into(iv_banner);
        }
        catch (Exception e )
        {
            Toast.makeText(this, "could not load recipes photo", Toast.LENGTH_SHORT).show();
        }
        date = (TextView) findViewById(R.id.quizzesshow_tv_date);
         date.setText(recipePOJO.getDate_article());
        try
        {
            if(!recipePOJO.getPdflink().equals(""))
            {
                pdflinktv.setVisibility(View.VISIBLE);
                // String[] splitteddate= recipePOJO.getDyslexicfonts().split("&&");
                //pdflinktv.setText(Html.fromHtml(splitteddate[1],0));   //link to pdf
                Spanned xx=  lirecolorsettext.setthetext(recipePOJO.getPdflink());
                pdflinktv.setText(xx);
                pdflinktv.setMovementMethod(LinkMovementMethod.getInstance());

            }
            else if (recipePOJO.getDyslexicfonts().equals(""))
            {
                pdflinktv.setVisibility(View.GONE);
            }


        }
        catch (Exception e)

        {
            //means only
            pdflinktv.setText(" ");
        }

        pdflinktv.setMovementMethod(LinkMovementMethod.getInstance());
        heading = (TextView) findViewById(R.id.quizzesshow_tv_heading);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/SuezOne.ttf");
        heading.setTypeface(custom_font);
        heading.setText(recipePOJO.getHeading_article());



    }

    private void settonormal(ActivitiesPOJO activitiesPOJO, String whichmode) {
        heading = (TextView) findViewById(R.id.quizzesshow_tv_heading);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/SuezOne.ttf");
        heading.setTypeface(custom_font);
        heading.setText(activitiesPOJO.getHeading());
        date = (TextView) findViewById(R.id.quizzesshow_tv_date);

        String whichmode1=whichmode;
        try
        {
            Picasso.get().load(activitiesPOJO.getBannerpic()).networkPolicy(NetworkPolicy.OFFLINE).into(iv_banner);

        }
        catch (Exception e )
        {

        }
       date.setText(activitiesPOJO.getDate());
        /////////tx.setTypeface(Typeface.SANS_SERIF);
        //////////////  tx.setVisibility(View.VISIBLE);

            try {
                thedataforrecycler = activitiesPOJO.getSimpletext();
            }
            catch(Exception e )
            {

            }

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





        if(!activitiesPOJO.getPdflink().equals(""))
        {
            // imginarticle.setVisibility(View.VISIBLE);
            pdflinktv.setVisibility(View.VISIBLE);
            Spanned xx=  lirecolorsettext.setthetext(activitiesPOJO.getPdflink());
            pdflinktv.setText(xx);
            pdflinktv.setMovementMethod(LinkMovementMethod.getInstance());

        }


    }

    @Override
    protected void onDestroy() {
        if(mTTS != null) {

            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }

    private void clickeditem(int id) {
        if(id==R.id.Activities)
        {
            startActivity(new Intent(ActivitiesShow.this, Activities.class));

        }
        if (id==R.id.Home)
        {
            //Toast.makeText(MainActivity.this, "Exercise", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ActivitiesShow.this, MainActivity.class));
        }
        if(id==R.id.Article)
        {

            //Toast.makeText(MainActivity.this, "quizz", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ActivitiesShow.this, ArticlesActivity.class));
        }
        if(id== R.id.Exercises)
        {
            // Toast.makeText(MainActivity.this, "addrecipe", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ActivitiesShow.this, ExercisesActivity.class));
        }
        if(id==R.id.Quizzes)
        {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ActivitiesShow.this, Quizzes.class));

        }
        if(id==R.id.participate)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ActivitiesShow.this, Participate.class));

        }
        if(id==R.id.Recipes)
        {
            // Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ActivitiesShow.this, Recipes.class));

        }
        if(id==R.id.Settings)
        {
            startActivity(new Intent(ActivitiesShow.this, Settings.class));

        }

        if(id==R.id.ParentArticles)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ActivitiesShow.this, ParentArticles.class));

        }


        if(id==R.id.Contact)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ActivitiesShow.this, Contact.class));

        }
        if(id==R.id.youtube)
        {
            //Toast.makeText(MainActivity.this, "ADDparent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ActivitiesShow.this, Youtube.class));

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
            //Log.e("speech got in act", String.valueOf(speechrate));
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
                x= activitiesPOJO.getSimpletext();

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

}
