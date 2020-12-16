package com.remi.altina.Lesptitsmonstres;

import android.app.Activity;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by umar on 9/25/2019.
 */

public class RecipesListAdapter  extends ArrayAdapter<RecipePOJO> {
    private Activity context;
    private List<RecipePOJO> recipeslist;
    public RecipesListAdapter(Activity context   , List<RecipePOJO> recipeslist)
    {
        super(context,R.layout.customlistviewlayout,recipeslist);

        this.recipeslist=recipeslist;
        this.context= context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= context.getLayoutInflater();
        View view=inflater.inflate(R.layout.customlistview_articles_layout,null,true);
        ImageView imageView=(ImageView) view.findViewById(R.id.iv_custom_articles);
        final TextView tv_heading= (TextView)view.findViewById(R.id.tv_heading);
        final TextView tv_date= (TextView)view.findViewById(R.id.tv_date);
        // imageView.setBackgroundColor(Color.parseColor(("#FFFFFF")));
        tv_heading.setEllipsize(TextUtils.TruncateAt.END);
        tv_heading.setMaxLines(3);
        RecipePOJO recipePOJO= recipeslist.get(position);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/SuezOne.ttf");
        tv_heading.setTypeface(custom_font);
        tv_heading.setText(recipePOJO.getHeading_article());
       // tv_date.setText(recipePOJO.getDate_article());
        try
        {
            Picasso.get().load(recipePOJO.pdflink).into(imageView);

        }
        catch(Exception e )
        {

        }
        //Picasso.with(context).load("https://images.pexels.com/photos/67636/rose-blue-flower-rose-blooms-67636.jpeg?cs=srgb&dl=beauty-bloom-blue-67636.jpg&fm=jpg").into(imageView);



        return view;
    }

}

    /*ArticlePOJO articlePOJO=arrayList.get(position);
    //Toast.makeText(context, "after pojo "+articlePOJO.getHeading_article(), Toast.LENGTH_SHORT).show();

        if(convertView==null) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView=layoutInflater.inflate(R.layout.customlistview_articles_layout, null);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        TextView tittle=convertView.findViewById(R.id.tv_heading);
        TextView date=convertView.findViewById(R.id.tv_date);
        ImageView imag=convertView.findViewById(R.id.iv_custom_articles);
        tittle.setText(articlePOJO.getHeading_article());
        //Toast.makeText(context, "adapter data"+articlePOJO.getHeading_article(), Toast.LENGTH_SHORT).show();
        date.setText(articlePOJO.getDate_article());
            *//*Picasso.with(context)
                    .load(subjectData.Image)
                    .into(imag);*//*
    }
        return convertView;
}*/





