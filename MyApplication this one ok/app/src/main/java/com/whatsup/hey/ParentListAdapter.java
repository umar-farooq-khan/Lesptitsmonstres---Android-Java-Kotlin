package com.whatsup.hey;

import android.app.Activity;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;



public class ParentListAdapter extends ArrayAdapter<ParentPOJO> {

    private Activity context;
    String[]  iconlink;
    private List<ParentPOJO> articleslist;
    public ParentListAdapter(Activity context  , List<ParentPOJO> articleslist)
    {
        super(context,R.layout.customlistview_articles_layout,articleslist);
        this.articleslist=articleslist;
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
        ParentPOJO articlePOJO= articleslist.get(position);
        tv_date.setText(articlePOJO.getDate_article());

        tv_heading.setText(articlePOJO.getHeading_article());

        try
        {
           //iconlink = articlePOJO.getIconlink().split("@@@");
            Picasso.get().load(articlePOJO.getIconlink()).into(imageView);
        }
        catch(Exception e )
        {

        }
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/SuezOne.ttf");
        tv_heading.setTypeface(custom_font);
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





