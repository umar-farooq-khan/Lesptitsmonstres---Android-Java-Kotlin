package com.whatsup.hey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MultiViewTypeForArticles extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Model> dataSet;
    public Context mContext;

    int progresspref;

    int total_types;

    public static class TextTypeViewHolder extends RecyclerView.ViewHolder {


        TextView textView;

        public TextTypeViewHolder(final View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.textbanner);


        }

    }

    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        Context mContext;

        public ImageTypeViewHolder(final View itemView) {
            super(itemView);

            this.image = (ImageView) itemView.findViewById(R.id.background);


        }
    }


    public MultiViewTypeForArticles(ArrayList<Model> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case Model.BANNER_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_typeforarticles, parent, false);

                return new TextTypeViewHolder(view);

            case Model.IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_typeforarticles, parent, false);
                return new ImageTypeViewHolder(view);

        }
        return null;


    }


    @Override
    public int getItemViewType(int position) {

        switch (dataSet.get(position).type) {
            case 0:
                return Model.BANNER_TYPE;
            case 1:
                return Model.IMAGE_TYPE;
            case 2:
                return Model.AUDIO_TYPE;
            default:
                return -1;
        }


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        Model object = dataSet.get(listPosition);


        if (object != null) {
            switch (object.type) {
                case Model.BANNER_TYPE:  //text type
                    //set the properties of textview
                    ((com.whatsup.hey.MultiViewTypeForArticles.TextTypeViewHolder) holder).textView.setMovementMethod(LinkMovementMethod.getInstance());
                    //yaha par textview laga wa hai, links bhi yahe hain, inko ghero.


                    try {
                        String[] xx = object.data.split("###");
                      //  Log.e("prebeforesplit", object.data);
                       // Log.e("preafter split 0", xx[0]);

                        SharedPreferences pref = mContext.getSharedPreferences("MyPref", 0);
                        progresspref = pref.getInt("progressvar", 22);

                        if (xx[1].equals("fromnormal")) {
                           // Log.e("prefrom normal", object.data);


                            CharSequence sequence = Html.fromHtml(object.data.replaceAll("###fromnormal", ""));
                            SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
                            URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
                            for (URLSpan span : urls) {
                                makeLinkClickable(strBuilder, span);
                            }

                            ((com.whatsup.hey.MultiViewTypeForArticles.TextTypeViewHolder) holder).textView.setText(strBuilder);

                            ((com.whatsup.hey.MultiViewTypeForArticles.TextTypeViewHolder) holder).textView.setTypeface(Typeface.SANS_SERIF);
                            ((com.whatsup.hey.MultiViewTypeForArticles.TextTypeViewHolder) holder).textView.setTextSize(progresspref);

                        } else if (xx[1].equals("tyu")) {
                            CharSequence sequence = Html.fromHtml(xx[0].replaceAll("###tyu", ""));
                            SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
                            URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
                            for (URLSpan span : urls) {
                                makeLinkClickable(strBuilder, span);
                            }
                            //Log.e("prefrom tyu",object.data);

                            ((com.whatsup.hey.MultiViewTypeForArticles.TextTypeViewHolder) holder).textView.setText(strBuilder);
                            Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(), "fonts/open.ttf");
                            ((com.whatsup.hey.MultiViewTypeForArticles.TextTypeViewHolder) holder).textView.setTypeface(custom_font);
                            ((com.whatsup.hey.MultiViewTypeForArticles.TextTypeViewHolder) holder).textView.setTextSize(progresspref);

                        } else if (xx[1].equals("fromreadcolor")) {
                            CharSequence sequence = Html.fromHtml(object.data.replaceAll("###fromreadcolor", ""));
                            SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
                            URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
                            for (URLSpan span : urls) {
                                makeLinkClickable(strBuilder, span);
                            }
                          //  Log.e("prefrom read", object.data);

                            ((com.whatsup.hey.MultiViewTypeForArticles.TextTypeViewHolder) holder).textView.setText(strBuilder);
                            ((com.whatsup.hey.MultiViewTypeForArticles.TextTypeViewHolder) holder).textView.setTypeface(Typeface.SANS_SERIF);
                            ((com.whatsup.hey.MultiViewTypeForArticles.TextTypeViewHolder) holder).textView.setTextSize(progresspref);


                        }
                    } catch (Exception e) {

                    }


                    // ((com.remi.altina.Lesptitsmonstres.MultiViewTypeAdapter.TextTypeViewHolder) holder).textView.setTextColor(Color.parseColor(splitcolorandheading[1]));


                case Model.IMAGE_TYPE:   //image type
                    try {
                        Picasso.get().load(object.data).into(((MultiViewTypeForArticles.ImageTypeViewHolder) holder).image);


                    } catch (Exception e) {

                    }


                    break;

            }
        }
        if (object == null) {

        }

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span) {
       // Log.e("pre", "pref");
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        ClickableSpan clickable = new ClickableSpan() {
            public void onClick(View view) {
                // Do something with span.getURL() to handle the link click...
               // Log.e("pre", span.getURL().toLowerCase());
                //Toast.makeText(mContext, "link got "+span.getURL(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, LinkShow.class);
                intent.putExtra("websitelink", span.getURL());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);


            }
        };
        strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.removeSpan(span);
    }

}

