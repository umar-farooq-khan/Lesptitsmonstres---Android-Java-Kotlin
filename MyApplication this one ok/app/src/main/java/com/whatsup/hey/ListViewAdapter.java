package com.whatsup.hey;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by umar on 9/26/2019.
 */

class ListViewAdapter implements ListAdapter {
        ArrayList<ArticlePOJO> arrayList;
        Context context;
        public ListViewAdapter(Context context, ArrayList<ArticlePOJO> arrayList) {
            this.arrayList=arrayList;
            this.context=context;
        }
        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }
        @Override
        public boolean isEnabled(int position) {
            return true;
        }
        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
        }
        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
        }
        @Override
        public int getCount() {
            return arrayList.size();
        }
        @Override
        public Object getItem(int position) {
            return position;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public boolean hasStableIds() {
            return false;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ArticlePOJO articledata = arrayList.get(position);


            if(convertView==null) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                convertView=layoutInflater.inflate(R.layout.customlistviewlayout, null);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });
                TextView title= (TextView) convertView.findViewById(R.id.textView);
                TextView date= (TextView) convertView.findViewById(R.id.datetxt);
                ImageView imag= (ImageView) convertView.findViewById(R.id.ivinlistview);
            /*title.setText(articledata.getHeading());
            Picasso.with(context)
                    .load(articledata.getImage())
                    .into(imag);*/
                //imag.setImageResource(imag);
            }
            return convertView;
        }
        @Override
        public int getItemViewType(int position) {
            return position;
        }
        @Override
        public int getViewTypeCount() {
            return arrayList.size();
        }
        @Override
        public boolean isEmpty() {
            return false;
        }
    }

