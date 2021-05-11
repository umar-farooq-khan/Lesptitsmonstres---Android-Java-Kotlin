package com.whatsup.hey;

import android.app.Activity;
import android.graphics.Color;
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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by umar on 9/24/2019.
 */

public class ExercisesListAdapter extends ArrayAdapter<ExercisePojo> {
    private Activity context;
    private List<ExercisePojo> exerciselist;
    public ExercisesListAdapter(Activity context   , List<ExercisePojo> exerciselist)
    {
        super(context,R.layout.customlistviewlayout,exerciselist);
        this.exerciselist=exerciselist;
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
        ExercisePojo exercisePojo= exerciselist.get(position);
        tv_heading.setText(exercisePojo.getPheading());
        tv_date.setText(exercisePojo.getPdate());
        try
        {
            if (exercisePojo.getPcategory().equals("CP"))
            {
                try {

                    Picasso.get().load(R.drawable.cpbanner).into(imageView);
                }
                catch ( Exception e)
                {
                    Toast.makeText(getContext(), "could not load photo", Toast.LENGTH_SHORT).show();
                }
            }
            else if (exercisePojo.getPcategory().equals("CE1"))
            {
                try {

                    Picasso.get().load(R.drawable.ce1).into(imageView);
                }
                catch ( Exception e)
                {
                    Toast.makeText(getContext(), "could not load photo", Toast.LENGTH_SHORT).show();
                }
            }
            else if (exercisePojo.getPcategory().equals("CE2"))
            {
                try {

                    Picasso.get().load(R.drawable.ce2).into(imageView);
                }
                catch ( Exception e)
                {
                    Toast.makeText(getContext(), "could not load photo", Toast.LENGTH_SHORT).show();
                }

            }

            else if (exercisePojo.getPcategory().equals("anyother"))
            {
                try {

                    Picasso.get().load(R.drawable.exercises).into(imageView);
                }
                catch ( Exception e)
                {
                    Toast.makeText(getContext(), "could not load photo", Toast.LENGTH_SHORT).show();
                }

            }

            else if (exercisePojo.getPcategory().equals("CM1"))
            {
                try {

                    Picasso.get().load(R.drawable.cm1).into(imageView);
                }
                catch ( Exception e)
                {
                    Toast.makeText(getContext(), "could not load photo", Toast.LENGTH_SHORT).show();
                }
            }

            else  if (exercisePojo.getPcategory().equals("CM2"))
            {
                try {

                    Picasso.get().load(R.drawable.cm2).into(imageView);
                }
                catch ( Exception e)
                {
                    Toast.makeText(getContext(), "could not load photo", Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch(Exception e )
        {
           // Toast.makeText(context, "Path of 'icon link'' is empty in the db,Please add something there", Toast.LENGTH_SHORT).show();
        }


        return view;
    }

}