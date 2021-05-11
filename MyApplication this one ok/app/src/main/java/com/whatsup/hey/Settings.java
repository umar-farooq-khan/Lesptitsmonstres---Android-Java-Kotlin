package com.whatsup.hey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Locale;

public class Settings extends AppCompatActivity {
    TextView tvfontsize,sampletext,tv_tts_progress;
    SeekBar seekBar,seekBar_tts;
     DrawerLayout dl;
     ActionBarDrawerToggle t;
     NavigationView nv;
     int progresspref;
     float progresspref_tts;
     int progress22;
     float progress_tts_float;

    private Vibrator myVib;
    int justtomakeitInt,result;
    TextToSpeech mTTS;
    ImageView play_btn;
    float progresspref_tts_toplay;
    boolean playpause=false;
    String samplespeechString;
    int progress_orignal_toshowitonseekbar;
    private int ori_progress_toshow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        findviewbyids();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences pref_tts = getApplicationContext().getSharedPreferences("MyPref_tts", MODE_PRIVATE);

        final SharedPreferences.Editor editor = pref.edit();
        final SharedPreferences.Editor editor_tts = pref_tts.edit();


        pref = getApplication().getSharedPreferences("MyPref", 0);
        progresspref=pref.getInt("progressvar", 22);
        seekBar.setProgress(progresspref-22);

        pref_tts = getApplication().getSharedPreferences("MyPref_tts", 0);
        progress_tts_float=pref_tts.getFloat("progressvar_tts", 5000);

        ori_progress_toshow=pref_tts.getInt("oriprogress", 22);

       //seekBar.setProgress(progresspref-22);
      //  Log.e("prog saved", String.valueOf(progresspref));
        if(progress_tts_float==5000)
        {
            SharedPreferences pref2 = getApplicationContext().getSharedPreferences("MyPref_tts", MODE_PRIVATE);
            final SharedPreferences.Editor editor2 = pref2.edit();
            editor2.putFloat("progressvar_tts", 1);
            editor2.apply();
            seekBar_tts.setProgress(50);
          //  Log.e("not set","not set");
            tv_tts_progress.setText("1");


        }
        else if (progress_tts_float!=5000)
        {
            justtomakeitInt= (int) (progress_tts_float*50);
            seekBar_tts.setProgress(justtomakeitInt);
            tv_tts_progress.setText(String.valueOf(progress_tts_float));

        }

        sampletext.setTextSize(progresspref);

        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

       seekBar_tts.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               progress_tts_float= (float)(progress)/(float)(50);
               tv_tts_progress.setText(String.valueOf(progress_tts_float));
               SharedPreferences pref2 = getApplicationContext().getSharedPreferences("MyPref_tts", MODE_PRIVATE);
               final SharedPreferences.Editor editor2 = pref2.edit();
              // progress22=progress+22;
               //Log.e("speechrate progress", String.valueOf(progress));
               //Log.e("speechrate saved", String.valueOf(progress_tts_float));
               editor2.putFloat("progressvar_tts", progress_tts_float);
               editor2.apply();
               if(progress_tts_float==0.25 || progress_tts_float== 0.5 || progress_tts_float== 0.75 || progress_tts_float==2.0 || progress_tts_float==1.5 )
               {
                   myVib.vibrate(100);
               }
           }

           @Override
           public void onStartTrackingTouch(SeekBar seekBar) {

           }

           @Override
           public void onStopTrackingTouch(SeekBar seekBar)
           {
               play();

           }
       });



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                final SharedPreferences.Editor editor1 = pref1.edit();
                progress22=progress+22;
                sampletext.setTextSize(progress22);
                progress_orignal_toshowitonseekbar= progress;
              // tvfontsize.setText(String.valueOf(progress));
                editor1.putInt("progressvar", progress22);
                editor1.putInt("oriprogress",progress_orignal_toshowitonseekbar);
                editor1.apply();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        setuptts_forplaying();
    }

    public void play() {


        play_btn.setImageResource(R.drawable.stopp);
        mTTS.setPitch((1));
        SharedPreferences pref_tts = getApplicationContext().getSharedPreferences("MyPref_tts", MODE_PRIVATE);

        pref_tts = getApplication().getSharedPreferences("MyPref_tts", 0);

        progresspref_tts_toplay=pref_tts.getFloat("progressvar_tts", 1);
        mTTS.setSpeechRate(progresspref_tts_toplay);
         samplespeechString=getResources().getString(R.string.samplespeech);

          try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mTTS.speak(samplespeechString, TextToSpeech.QUEUE_FLUSH, null,null);

            }
            else {
                HashMap<String, String> param = new HashMap<>();
                param.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_MUSIC));
                mTTS.speak(samplespeechString, TextToSpeech.QUEUE_FLUSH, param);
            }
        }
        catch(Exception e)
        {
            Toast.makeText(this, getResources().getString(R.string.errorloadingtts), Toast.LENGTH_SHORT).show();
        }


    }

//        mTTS.stop();
//        play_btn.setImageResource(R.drawable.playy);





//function
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


    private void setuptts_forplaying() {
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

                            play_btn.setImageResource(R.drawable.playy);

                        }

                        @Override
                        public void onError(String utteranceId) {

                        }
                    });


                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                    {

                        // Log.e("TTS", "Language not supported");

                    }
                } else
                {
                    Toast.makeText(Settings.this, "Text to speech Initialization failed", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Settings.this, "Error code "+result, Toast.LENGTH_SHORT).show();
                    //result = mTTS.setLanguage(Locale.FRENCH);
                }
            }
        });
        mTTS.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {

            }

            @Override
            public void onDone(String utteranceId)
            {
                play_btn.setImageResource(R.drawable.playy);
            }

            @Override
            public void onError(String utteranceId) {

            }
        });

    }

    private void findviewbyids() {
        tv_tts_progress= findViewById(R.id.tv_ttsprogress);
        tvfontsize= findViewById(R.id.tv_fontsize);
        sampletext=findViewById(R.id.tvsampletext);
        seekBar= findViewById(R.id.seekbarfontsize);
        seekBar_tts= findViewById(R.id.seekbar_tts_speed);
        play_btn = (ImageView) findViewById(R.id.play_btn);
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playsimply();
            }
        });


        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                clickeditem(id);
                return true;
            }
        });
        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        t.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dl.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/open.ttf");
        sampletext.setTypeface(custom_font);
    }

    private void playsimply() {
        playpause = !playpause;
        if (playpause) {
            play_btn.setImageResource(R.drawable.stopp);
            mTTS.setPitch((1));
            SharedPreferences pref_tts = getApplicationContext().getSharedPreferences("MyPref_tts", MODE_PRIVATE);


            pref_tts = getApplication().getSharedPreferences("MyPref_tts", 0);

            progresspref_tts_toplay = pref_tts.getFloat("progressvar_tts", 1);
            mTTS.setSpeechRate(progresspref_tts_toplay);
            samplespeechString = getResources().getString(R.string.samplespeech);

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mTTS.speak(samplespeechString, TextToSpeech.QUEUE_FLUSH, null, null);

                } else {
                    HashMap<String, String> param = new HashMap<>();
                    param.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_MUSIC));
                    mTTS.speak(samplespeechString, TextToSpeech.QUEUE_FLUSH, param);
                }
            } catch (Exception e) {
                Toast.makeText(this, getResources().getString(R.string.errorloadingtts), Toast.LENGTH_SHORT).show();
            }

        }

        else if (!playpause) {
            mTTS.stop();
            play_btn.setImageResource(R.drawable.playy);


        }
    }

    private void clickeditem(int id) {
        if (id == R.id.Home) {
            startActivity(new Intent(Settings.this, MainActivity.class));
        }
        if (id == R.id.Article) {

            startActivity(new Intent(Settings.this, ArticlesActivity.class));
        }
        if (id == R.id.Exercises) {
            startActivity(new Intent(Settings.this, ExercisesActivity.class));
        }
        if (id == R.id.Quizzes) {
            startActivity(new Intent(Settings.this, Quizzes.class));

        }
        if (id == R.id.participate) {
            startActivity(new Intent(Settings.this, Participate.class));

        }
        if (id == R.id.Recipes) {
            startActivity(new Intent(Settings.this, Recipes.class));

        }
        if (id == R.id.ParentArticles) {
            startActivity(new Intent(Settings.this, ParentArticles.class));

        }


        if (id == R.id.Contact) {
            startActivity(new Intent(Settings.this, Contact.class));

        }
        if(id==R.id.Settings)
        {
            startActivity(new Intent(Settings.this, Settings.class));

        }
        if(id==R.id.Activities)
        {
            startActivity(new Intent(Settings.this, Activities.class));

        }
        if(id==R.id.youtube)
        {
            startActivity(new Intent(Settings.this, Youtube.class));

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

}
