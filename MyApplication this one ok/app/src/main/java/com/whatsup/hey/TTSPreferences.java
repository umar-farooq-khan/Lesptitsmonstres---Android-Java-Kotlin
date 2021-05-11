package com.whatsup.hey;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class TTSPreferences {

    private float progress_tts;

    public TTSPreferences(float progress_tts) {
        this.progress_tts = progress_tts;
    }

    public TTSPreferences() {
    }

    public float getProgress_tts(Context ctx) {
        SharedPreferences pref_tts = ctx.getSharedPreferences("MyPref_tts", MODE_PRIVATE);
        SharedPreferences.Editor editor_tts = pref_tts.edit();
        pref_tts = ctx.getSharedPreferences("MyPref_tts", 0);
        progress_tts=pref_tts.getFloat("progressvar_tts", 5000);
        return progress_tts;
    }

    public void setProgress_tts(float progress_tts) {
        this.progress_tts = progress_tts;
    }
}


