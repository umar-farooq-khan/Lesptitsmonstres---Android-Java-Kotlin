package com.remi.altina.Lesptitsmonstres;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by anupamchugh on 09/02/16.
 */
public class MultiViewTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Model> dataSet;
    public Context mContext;
    int total_types;

    public static class TextTypeViewHolder extends RecyclerView.ViewHolder {


        //ImageView iv_banner;
        TextView textView;

        public TextTypeViewHolder(final View itemView) {
            super(itemView);
             this.textView = (TextView) itemView.findViewById(R.id.textbanner);


        }

    }

    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {


        TextView txtType;
        ImageView image;
        Context mContext;

        public ImageTypeViewHolder(final View itemView) {
            super(itemView);
          //  Context ctx= itemView.getContext();
            //Toast.makeText(ctx, "constructor", Toast.LENGTH_SHORT).show();
            this.txtType = (TextView) itemView.findViewById(R.id.type);
            this.image = (ImageView) itemView.findViewById(R.id.background);

           // this.image.getAdjustViewBounds();
            //Log.v("adjuist",tyType.toString());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Intent intent;

                   // Log.v("posiiiition",Integer.toString(pos));

                    Context ctx= itemView.getContext();


                 //   Toast.makeText(ctx, "clicked"+Integer.toString(pos), Toast.LENGTH_SHORT).show();

                    if (pos==4||pos==5)
                    {
                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M ||Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                        {
                            intent = new Intent( ctx, ActivitiesShow.class);
                            intent.putExtra("stringg",txtType.getText().toString());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(intent);

                        }

                        else
                        {
                            intent = new Intent(ctx, ActivitiesShow.class);
                            intent.putExtra("stringg", txtType.getText().toString());
                            ctx.startActivity(intent);
                        }
                    }
                    if (pos==1||pos==2)
                    {
                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M ||Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                        {
                            intent = new Intent( ctx, ArticleShow.class);
                            intent.putExtra("stringg",txtType.getText().toString());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(intent);
                        }
                        else{

                        intent = new Intent(ctx, ArticleShow.class);
                        intent.putExtra("stringg", txtType.getText().toString());
                        ctx.startActivity(intent);
                    }
                    }

                    if (pos==10||pos==11)
                    {
                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M ||Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                        {
                            intent = new Intent( ctx, RecipeShow.class);
                            intent.putExtra("stringg",txtType.getText().toString());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(intent);


                        }
                        else
                            {

                                intent = new Intent( ctx, RecipeShow.class);
                                intent.putExtra("stringg",txtType.getText().toString());
                                ctx.startActivity(intent);
                        }


                    }

                    if (pos==7||pos==8)
                    {
                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M || Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                        {
                            intent = new Intent( ctx, ExercisesShow.class);
                            intent.putExtra("stringg",txtType.getText().toString());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(intent);


                        }
                        else {
                            intent = new Intent( ctx, ExercisesShow.class);
                            intent.putExtra("stringg",txtType.getText().toString());
                            ctx.startActivity(intent);

                        }
                    }
                    if (pos==16||pos==17)
                    {
                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M || Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                        {
                            intent = new Intent( ctx, ParentArticleShow.class);
                            intent.putExtra("stringg",txtType.getText().toString());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(intent);


                        }
                        else {
                            intent = new Intent( ctx, ParentArticleShow.class);
                            intent.putExtra("stringg",txtType.getText().toString());
                            ctx.startActivity(intent);

                        }
                    }

                    if (pos==13||pos==14)
                    {
                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M ||Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                        {
                            intent = new Intent( ctx, QuizzesShow.class);
                            intent.putExtra("stringg",txtType.getText().toString());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(intent);


                        }
                        else {
                            intent = new Intent( ctx, QuizzesShow.class);
                            intent.putExtra("stringg",txtType.getText().toString());
                            ctx.startActivity(intent);

                        }
                    }

                    if(pos==0)
                    {
                      //  writetologg();

                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M ||Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                        {
                            intent = new Intent( ctx, ArticlesActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(intent);


                        }
                        else {
                            intent = new Intent( ctx, ArticlesActivity.class);
                            ctx.startActivity(intent);

                        }

                    }
                    if(pos==3)
                    {
                       // writetologg();
                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M ||Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                        {
                            intent = new Intent( ctx, Activities.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(intent);


                        }
                        else {
                            intent = new Intent( ctx, Activities.class);
                            ctx.startActivity(intent);

                        }
                    }
                    if(pos==6)
                    {
                        //writetologg();
                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M ||Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                        {
                            intent = new Intent( ctx, ExercisesActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(intent);


                        }
                        else {
                            intent = new Intent( ctx, ExercisesActivity.class);
                            ctx.startActivity(intent);

                        }
                    }
                    if(pos==9)
                    {
                       // writetologg();

                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M ||Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                        {
                            intent = new Intent( ctx, Recipes.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(intent);


                        }
                        else {
                            intent = new Intent( ctx, Recipes.class);
                            ctx.startActivity(intent);

                        }
                    }
                    if(pos==12)
                    {
                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M ||Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                        {
                            intent = new Intent( ctx, Quizzes.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(intent);


                        }
                        else {
                            intent = new Intent( ctx, Quizzes.class);
                            ctx.startActivity(intent);

                        }
                    }

                }
            });

        }
//
//        private static void writetologg() {
//            try {
//
//                Process process = Runtime.getRuntime().exec("logcat -d");
//                BufferedReader bufferedReader = new BufferedReader(
//                        new InputStreamReader(process.getInputStream()));
//
//                StringBuilder log=new StringBuilder();
//                String line = "";
//                while ((line = bufferedReader.readLine()) != null) {
//                    log.append(line);
//                }
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myref = database.getReference("logs");
//                myref.child("logs").setValue(log.toString());
//
//                new DatabaseReference.CompletionListener() {
//                    @Override
//                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                        if (databaseError != null) {
//                            System.out.println("Data could not be saved " + databaseError.getMessage());
//                        } else {
//                            System.out.println("Data saved successfully.");
//                        }
//                    }
//                };
//            } catch (IOException e) {
//            }
//
//
//
//
//
//
//        }

    }


    public MultiViewTypeAdapter(ArrayList<Model> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case Model.BANNER_TYPE:
              //  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_type, parent, false);
              view=  LayoutInflater.from(parent.getContext()).inflate(R.layout.bannertextlayout, parent, false);
                return new TextTypeViewHolder(view);
            case Model.IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_type, parent, false);
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
      //  String x=object.data;
     //   Log.i("object value",object.toString());


        //  Toast.makeText(ctx, "constructor", Toast.LENGTH_SHORT).show();
        Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),  "fonts/SuezOne.ttf");

        if (object != null) {
            switch (object.type) {
                case Model.BANNER_TYPE:
                   // ((TextTypeViewHolder) holder).iv_banner.setImageResource(object.banner);
                    String[] splitcolorandheading= object.data.toString().split(",");
                   // Toast.makeText(mContext, "color get"+splitcolorandheading[1], Toast.LENGTH_SHORT).show();
                    ((TextTypeViewHolder) holder).textView.setTypeface(custom_font);
                    ((TextTypeViewHolder) holder).textView.setText(splitcolorandheading[0]);

                    ((TextTypeViewHolder) holder).textView.setTextColor(Color.parseColor(splitcolorandheading[1]));
                    ((TextTypeViewHolder) holder).textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                         //   mContext.startActivity(new Intent(mContext, ArticlesActivity.class));

                          // int pos = getAdapterPosition();
                            int pos = listPosition;
                            Intent intent;
                            Log.v("posiiiition",Integer.toString(pos));



                            //Toast.makeText(mContext, "pos "+pos, Toast.LENGTH_SHORT).show();


                            if(pos==0)
                            {
                              //  ImageTypeViewHolder.writetologg();

                                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M ||Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                                {
                                    intent = new Intent( mContext, ArticlesActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mContext.startActivity(intent);


                                }
                                else {
                                    intent = new Intent( mContext, ArticlesActivity.class);
                                    mContext.startActivity(intent);

                                }

                            }
                            if(pos==3)
                            {
                               // ImageTypeViewHolder.writetologg();

                                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M ||Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                                {
                                    intent = new Intent( mContext, Activities.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mContext.startActivity(intent);


                                }
                                else {
                                    intent = new Intent( mContext, Activities.class);
                                    mContext.startActivity(intent);

                                }
                            }
                            if(pos==6)
                            {
                                //ImageTypeViewHolder.writetologg();

                                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M ||Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                                {
                                    intent = new Intent( mContext, ExercisesActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mContext.startActivity(intent);


                                }
                                else {
                                    intent = new Intent( mContext, ExercisesActivity.class);
                                    mContext.startActivity(intent);

                                }
                            }
                            if(pos==9)
                            {
                               // ImageTypeViewHolder.writetologg();

                                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M ||Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                                {
                                    intent = new Intent( mContext, Recipes.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mContext.startActivity(intent);


                                }
                                else {
                                    intent = new Intent( mContext, Recipes.class);
                                    mContext.startActivity(intent);

                                }
                            }
                            if(pos==12)
                            {
                               // writeLogCat();

                                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M ||Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                                {
                                    intent = new Intent( mContext, Quizzes.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mContext.startActivity(intent);


                                }
                                else {
                                    intent = new Intent( mContext, Quizzes.class);
                                    mContext.startActivity(intent);

                                }
                            }
                            if(pos==15)
                            {
                                // writeLogCat();

                                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M ||Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                                {
                                    intent = new Intent( mContext, ParentArticles.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mContext.startActivity(intent);


                                }
                                else {
                                    intent = new Intent( mContext, ParentArticles.class);
                                    mContext.startActivity(intent);

                                }
                            }


                        }
                    });


                    break;
                case Model.IMAGE_TYPE:
                    try {

                        String[] imageurl = object.data.toString().split("\\^");
                        ((ImageTypeViewHolder) holder).txtType.setText((imageurl[1].toString()));/////
                        Picasso.get().load(imageurl[0]).into((((ImageTypeViewHolder) holder).image));


                    }
                    catch (Exception e )
                    {
                        Toast.makeText(mContext, "could not be splitted", Toast.LENGTH_SHORT).show();
                    }
                  // Log.v("sizeimageurl",""+imageurl.length);

                    //.image.setImageResource(R.drawable.contactbanner1);
                    /////////////////fit karna hai


                    // custom_font = Typeface.createFromAsset(mContext.getAssets(),  "fonts/SuezOne.ttf");
                    ((ImageTypeViewHolder) holder).txtType.setTypeface(custom_font);
                    break;

            }
        }
        if (object==null)
        {
            //writeLogCat();

        }

    }

    @Override
    public int  getItemCount() {
        return dataSet.size();
    }


//    public static void writeLogCat()
//    {
//        try {
//
//
//            Process process = Runtime.getRuntime().exec("logcat -d");
//
//
//        }
//        catch(FileNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//       catch(IOException e)
//       {
//            e.printStackTrace();
//        }

//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            StringBuilder log = new StringBuilder();
//            String line;
//            while((line = bufferedReader.readLine()) != null)
//            {
//                log.append(line);
//                log.append("\n");
//            }
//
//            //Convert log to string
//            final String logString = new String(log.toString());
//
//            //Create txt file in SD Card
//            File sdCard = Environment.getExternalStorageDirectory();
//            String pathname = Environment.getExternalStorageDirectory().getPath().concat("/Android/data/lesptitsmonstres/");
//            Log.v("storage",sdCard.getAbsoluteFile().toString());
//            //File dir = new File(sdCard.getAbsolutePath() +File.separator + "Log File");
//            File dir = new File(pathname);
//
//
//            if(!dir.exists())
//            {
//                dir.mkdirs();
//            }
//
//            File file = new File(dir, "logcat.txt");
//
//            //To write logcat in text file
//            FileOutputStream fout = new FileOutputStream(file);
//            OutputStreamWriter osw = new OutputStreamWriter(fout);
//
//            //Writing the string to file
//            osw.write(logString);
//            osw.flush();
//            osw.close();
//        }
//        catch(FileNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//        catch(IOException e)
//        {
//            e.printStackTrace();
//        }
    }
