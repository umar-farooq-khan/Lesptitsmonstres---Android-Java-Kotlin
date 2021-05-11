package com.whatsup.hey;

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

import java.util.List;

/**
 * Created by umar on 9/25/2019.
 */

public class quizzeslistAdapter  extends ArrayAdapter<QuizzPOJO> {
    private Activity context;
    private List<QuizzPOJO> articleslist;
    public quizzeslistAdapter(Activity context   , List<QuizzPOJO> articleslist)
    {
        super(context,R.layout.customlistviewlayout,articleslist);
        this.articleslist=articleslist;
        this.context= context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= context.getLayoutInflater();
        View view=inflater.inflate(R.layout.customlistview_articles_layout,null,true);
        ImageView imageView=(ImageView) view.findViewById(R.id.iv_custom_articles);
        imageView.setImageResource(R.drawable.quizbanner);
        final TextView tv_heading= (TextView)view.findViewById(R.id.tv_heading);
        final TextView tv_date= (TextView)view.findViewById(R.id.tv_date);
        // imageView.setBackgroundColor(Color.parseColor(("#FFFFFF")));
        tv_heading.setEllipsize(TextUtils.TruncateAt.END);
        tv_heading.setMaxLines(3);
        QuizzPOJO quizzPOJO= articleslist.get(position);
        tv_heading.setText(quizzPOJO.getHeading());
        tv_date.setText(quizzPOJO.getDate());
        //Picasso.with(context).load(quizzPOJO.getImageicon()).into(imageView);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/SuezOne.ttf");
        tv_heading.setTypeface(custom_font);
        //Picasso.with(context).load("https://images.pexels.com/photos/67636/rose-blue-flower-rose-blooms-67636.jpeg?cs=srgb&dl=beauty-bloom-blue-67636.jpg&fm=jpg").into(imageView);

        return view;
    }

}

