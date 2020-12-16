package com.remi.altina.Lesptitsmonstres;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ActivitiesListAdapter extends ArrayAdapter<ActivitiesPOJO> {
    private Activity context;
    private List<ActivitiesPOJO> activitieslist;
    public ActivitiesListAdapter(Activity context   , List<ActivitiesPOJO> activitieslist)
    {
        super(context,R.layout.customlistviewlayout,activitieslist);
        this.activitieslist=activitieslist;
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
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/SuezOne.ttf");
        tv_heading.setTypeface(custom_font);
        imageView.setBackgroundColor(Color.parseColor(("#FFFFFF")));
        tv_heading.setEllipsize(TextUtils.TruncateAt.END);
        tv_heading.setMaxLines(3);
        ActivitiesPOJO activitiesPOJO= activitieslist.get(position);
        tv_heading.setText(activitiesPOJO.getHeading());
        tv_date.setText(activitiesPOJO.getDate());
        try {
            Picasso.get().load(activitiesPOJO.getBannerpic()).into(imageView);
        }
        catch(Exception e )
        {

        }



        return view;
    }
}

