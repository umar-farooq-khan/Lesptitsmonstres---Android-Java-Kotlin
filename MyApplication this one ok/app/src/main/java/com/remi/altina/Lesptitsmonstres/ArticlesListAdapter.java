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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umar on 9/18/2019.
 */


 public class ArticlesListAdapter extends ArrayAdapter<ArticlePOJO> {
    private Activity context;
    List<ArticlePOJO> articleslist;


    public ArticlesListAdapter(Activity context, List<ArticlePOJO> articleslist) {
        super(context, R.layout.customlistviewlayout, articleslist);
        this.articleslist = articleslist;
        this.context = context;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.customlistview_articles_layout, null, true);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_custom_articles);
        final TextView tv_heading = (TextView) view.findViewById(R.id.tv_heading);
        final TextView tv_date = (TextView) view.findViewById(R.id.tv_date);


        tv_heading.setEllipsize(TextUtils.TruncateAt.END);
        tv_heading.setMaxLines(3);
        ArticlePOJO articlePOJO = articleslist.get(position);
        tv_heading.setText(articlePOJO.getHeading_article());
            tv_date.setText(articlePOJO.getDate_article());

            if (!articlePOJO.getIconlink().equals(""))
            {
                try {

                    Picasso.get().load(articlePOJO.getIconlink()).into(imageView);

                } catch (Exception e) {
                    Toast.makeText(context, "could not load photo", Toast.LENGTH_SHORT).show();
                }

            }


   ////////////////////////////////////// //this code is for no-delay in image loading///////////////////// upar wala if mita dena phir/////////////////////////////
        /*if (!articlePOJO.getIconlink().equals("")) {
            //check the file in that path
            String fullpath = pathname  + articlePOJO.getHeading_article() + ".jpeg";
            File file = new File(fullpath);
            if (file.exists()) {
                //  Toast.makeText(context, "yes exists"+pathname+"/"+articlePOJO.getHeading_article()+".jpeg", Toast.LENGTH_SHORT).show();
                Log.v("loading from disk", pathname);

                imageView.setImageURI(Uri.parse(fullpath));
            }
//Do something
            else if (!file.exists()) {
                // Toast.makeText(context, "not exists"+pathname+"/"+articlePOJO.getHeading_article(), Toast.LENGTH_SHORT).show();
                Log.v("loading from net", pathname + "/" + articlePOJO.getHeading_article());
               Picasso.get().load(articlePOJO.getIconlink()).into(imageView);
                saveimage(articlePOJO, pathname);


            }
// Do something else
            //if exists ----> setimageresource
            //if not exists ---> save image
        }*/

        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/SuezOne.ttf");
        tv_heading.setTypeface(custom_font);
        //.with(context).load("https://images.pexels.com/photos/67636/rose-blue-flower-rose-blooms-67636.jpeg?cs=srgb&dl=beauty-bloom-blue-67636.jpg&fm=jpg").into(imageView);

        return view;
    }
    public  void filterList(ArrayList<ArticlePOJO> filteredList)
    {
        articleslist= filteredList;
        notifyDataSetChanged();
    }



    private void checksize() {



    }
}





