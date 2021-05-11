package com.whatsup.hey;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by umar on 10/1/2019.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    List<YoutubeVideo> youtubeVideosList;
    Context ctx;
    DatabaseReference myref;
    String apikey;

    public VideoAdapter() {
    }

    public VideoAdapter(List<YoutubeVideo> youtubeVideosList,String apikey,Context ctx) {
        this.youtubeVideosList = youtubeVideosList;
        this.ctx=ctx;
    }



    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_video, parent, false);
            savekey();
            return new VideoViewHolder(view);


    }

    private void savekey() {
        myref = FirebaseDatabase.getInstance().getReference("YoutubeAPIKEY");
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                YoutubeApiKeyPOJO youtubeApiKeyPOJO = dataSnapshot.getValue(YoutubeApiKeyPOJO.class);
                apikey=youtubeApiKeyPOJO.getKey();
              //  Log.e("getkeywithin",apikey);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onBindViewHolder(final VideoViewHolder holder, int position)
    {
        try
        {
        final String[] imageurl= youtubeVideosList.get(position).getVideourl().split("\\^");
        holder.vidname.setText(youtubeVideosList.get(position).getVideoname());
        Picasso.get().load(imageurl[0]).into(holder.vidbanner);
        holder.vidbanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               navigate_youtube_show(imageurl);
            }
         });
        holder.vidname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               navigate_youtube_show(imageurl);
            }
        });

        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            //e.printStackTrace();
          //  Log.e("out of bound","out of bound");
        }

    }

    private void navigate_youtube_show(String[] imageurl)
    {
        try {
            Intent intent = new Intent(ctx, YoutubeShow.class);
            intent.putExtra("videourl", imageurl[1]);
            intent.putExtra("apikey", apikey);
            intent.putExtra("videodesc", imageurl[2]);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
          //  Log.e("out of bound","out of bound");
            Intent intent = new Intent(ctx, YoutubeShow.class);
            intent.putExtra("videourl", imageurl[1]);
            intent.putExtra("apikey", apikey);
            intent.putExtra("videodesc", "None");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return youtubeVideosList.size();
    }

    public class VideoViewHolder extends  RecyclerView.ViewHolder{

        TextView vidname;
        ImageView vidbanner;

        public VideoViewHolder(View itemView) {
            super(itemView);

            vidname= (TextView) itemView.findViewById(R.id.vidname);
            vidbanner= itemView.findViewById(R.id.youtubebanner);
            Typeface custom_font = Typeface.createFromAsset(ctx.getAssets(), "fonts/SuezOne.ttf");
            vidname.setTypeface(custom_font);
            
        }
    }
}
