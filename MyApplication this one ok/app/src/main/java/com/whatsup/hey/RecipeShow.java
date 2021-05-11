package com.whatsup.hey;

import android.content.Intent;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
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

public class RecipeShow extends AppCompatActivity {
    Switch switchReadcolor;
    TextToSpeech mTTS;
    int result;
    Switch switchfonts;
    ImageView fab;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    String thisnewvar="";

    boolean playpause=false;
    private NavigationView nv;

    TextView tx,heading,date;

    ImageView iv_banner;
    TextView pdflinktv;

    RecipePOJO  recipePOJO;
    String stringg;

    Intent recipePOJOintent,stringintent;
    DatabaseReference myref;

    RecyclerView recyclerView; String thedataforrecycler=""; final ArrayList<Model> list= new ArrayList<>();
    private float speechrate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_show);
        recyclerView=findViewById(R.id.rvforrecipes);


        recyclerView.setNestedScrollingEnabled(false);
        switchfonts = findViewById(R.id.switchfonts);
        switchReadcolor= findViewById(R.id.switchreadcolor);
        pdflinktv = (TextView) findViewById(R.id.tvlinks);
        fab= (ImageView) findViewById(R.id.fab);
        iv_banner = (ImageView) findViewById(R.id.articleshow_iv_bannner);
        recipePOJOintent = getIntent();
        stringintent = getIntent();

        stringg = (String) stringintent.getSerializableExtra("stringg");
        recipePOJO = (RecipePOJO) recipePOJOintent.getSerializableExtra("obj");
        heading=findViewById(R.id.quizzesshow_tv_heading);
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


        try {

            if (recipePOJO!=null)
            {
               // setthedata(recipePOJO);
                /////////////////////////////////
                settonormal(recipePOJO,"###fromnormal");
            }

            if (stringg!=null)
            {

                if (!stringg.isEmpty())
                {
                    myref = FirebaseDatabase.getInstance().getReference("Recipes");
                    Query q=myref.orderByChild("heading_article").equalTo(stringg);
                    q.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            recipePOJO =dataSnapshot.getValue(RecipePOJO.class);
                            ////setthedata(recipePOJO);
                            //abhiiiii kiya
                            settonormal(recipePOJO,"###fromnormal");
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                          //  Toast.makeText(RecipeShow.this, "childchanged", Toast.LENGTH_SHORT).show();
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
        // settonormal(recipePOJO,"###fromnormal");
         ///////////////////////

        switchfonts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchReadcolor.setChecked(false);
                    settonormal(recipePOJO,"###tyu");
//                    Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/open.ttf");
//                    tx.setTypeface(custom_font);
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    params.addRule(RelativeLayout.BELOW, R.id.datatext);
                    //////////////////////////////////
                } else {
                    settonormal(recipePOJO,"###fromnormal");
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    params.addRule(RelativeLayout.BELOW, R.id.datatext);
                    ////////////////
                    // pdflinktv.setLayoutParams(params);
                }
            }
        });
        switchReadcolor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    switchfonts.setChecked(false);
                    settonormal(recipePOJO,"###fromreadcolor");
                    //////////  // tx.setText(Html.fromHtml(recipePOJO.getReadcolor_article(),0));
                    ///////  Spanned text = lirecolorsettext.setthetext(recipePOJO.getReadcolor_article());
                    // tx.setText(text);
                    //  tx.setCompoundDrawablesWithIntrinsicBounds(R.drawable.banner,0,0,0);


                    // pdflinktv.setLayoutParams(params);
                    // pdflinktv.setVisibility(View.VISIBLE);
                }
                else {
                   settonormal(recipePOJO,"###fromnormal");
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    params.addRule(RelativeLayout.BELOW, R.id.datatext);
                    ///////////no need of webview
                    //    pdflinktv.setLayoutParams(params);

                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mTTS!=null) { mTTS.stop(); }


    }

    private void setthedata(final RecipePOJO recipePOJO) {
        Toast.makeText(this, "set the data", Toast.LENGTH_SHORT).show();


        switchReadcolor = (Switch) findViewById(R.id.switchreadcolor);
        try {
            
            Picasso.get().load(recipePOJO.getPdflink()).networkPolicy(NetworkPolicy.OFFLINE).into(iv_banner);
        }
        catch (Exception e )
        {
            Toast.makeText(this, "could not load recipes photo", Toast.LENGTH_SHORT).show();
        }
        switchfonts = (Switch) findViewById(R.id.switchfonts);
        date = (TextView) findViewById(R.id.quizzesshow_tv_date);
       // date.setText(recipePOJO.getDate_article());
        try
        {
            if(!recipePOJO.getDyslexicfonts().equals(""))
            {
                pdflinktv.setVisibility(View.VISIBLE);
               // String[] splitteddate= recipePOJO.getDyslexicfonts().split("&&");
                //pdflinktv.setText(Html.fromHtml(splitteddate[1],0));   //link to pdf
                Spanned xx=  lirecolorsettext.setthetext(recipePOJO.getDyslexicfonts());
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

    private void settonormal(RecipePOJO recipePOJO, String whichmode) {
        String whichmode1=whichmode;
        try
        {
            Picasso.get().load(recipePOJO.getPdflink()).networkPolicy(NetworkPolicy.OFFLINE).into(iv_banner);

        }
        catch (Exception e )
        {

        }
        heading = (TextView) findViewById(R.id.quizzesshow_tv_heading);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/SuezOne.ttf");
        heading.setTypeface(custom_font);
        heading.setText(recipePOJO.getHeading_article());

        /////////tx.setTypeface(Typeface.SANS_SERIF);
        //////////////  tx.setVisibility(View.VISIBLE);

        if(whichmode1.equals("###fromreadcolor"))
        {
            try {
                thedataforrecycler = recipePOJO.getReadcolor_article();
            }
            catch(Exception e )
            {

            }

        }
        else
        {
            try {
                thedataforrecycler = recipePOJO.getPlaintext_article();
            }
            catch(Exception e )
            {

            }
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




        // tx.setText(Html.fromHtml(recipePOJO.getPlaintext_article(),0));
//       Spanned plaintextt= lirecolorsettext.setthetext(recipePOJO.getPlaintext_article());
//        tx.setText(plaintextt);

        if(!recipePOJO.getDyslexicfonts().equals(""))
        {
            // imginarticle.setVisibility(View.VISIBLE);
            pdflinktv.setVisibility(View.VISIBLE);
            Spanned xx=  lirecolorsettext.setthetext(recipePOJO.getDyslexicfonts());
            pdflinktv.setText(xx);
            pdflinktv.setMovementMethod(LinkMovementMethod.getInstance());

        }


        //////////////////////////////////////
        //   tx.setCompoundDrawablesWithIntrinsicBounds(R.drawable.banner,0,0,);
        //////////////////////////tx.setMovementMethod(LinkMovementMethod.getInstance());

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

    private void clickeditem(int id) {
        if(id==R.id.Activities)
        {
            startActivity(new Intent(RecipeShow.this, Activities.class));

        }
        if (id==R.id.Home)
        {
            startActivity(new Intent(RecipeShow.this, MainActivity.class));
        }
        if(id==R.id.Article)
        {

            startActivity(new Intent(RecipeShow.this, ArticlesActivity.class));
        }
        if(id== R.id.Exercises)
        {
            startActivity(new Intent(RecipeShow.this, ExercisesActivity.class));
        }
        if(id==R.id.Quizzes)
        {
            startActivity(new Intent(RecipeShow.this, Quizzes.class));

        }
        if(id==R.id.participate)
        {
            startActivity(new Intent(RecipeShow.this, Participate.class));

        }
        if(id==R.id.Recipes)
        {
            startActivity(new Intent(RecipeShow.this, Recipes.class));

        }
        if(id==R.id.youtube)
        {
            startActivity(new Intent(RecipeShow.this, Youtube.class));

        }

        if(id==R.id.ParentArticles)
        {
            startActivity(new Intent(RecipeShow.this, ParentArticles.class));

        }


        if(id==R.id.Contact)
        {
            startActivity(new Intent(RecipeShow.this, Contact.class));

        }
        if(id==R.id.Settings)
        {
            startActivity(new Intent(RecipeShow.this, Settings.class));

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
         //   Log.e("speech got in recipe", String.valueOf(speechrate));

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
                x= recipePOJO.getPlaintext_article();

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


//ArrayList<ArticlePOJO> arrayList = new ArrayList<ArticlePOJO>();



