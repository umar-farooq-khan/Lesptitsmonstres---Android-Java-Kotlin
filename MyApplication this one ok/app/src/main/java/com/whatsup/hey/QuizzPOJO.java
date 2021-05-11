package com.whatsup.hey;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;

/**
 * Created by umar on 9/20/2019.
 */

class QuizzPOJO implements Serializable,Comparable<QuizzPOJO> {
    @PropertyName("date")

    public  String date;
    @PropertyName("quizzlink")

    public  String  quizzlink;
    @PropertyName("heading")
    public  String  heading;
    @PropertyName("imageicon")
    public  String imageicon;

    public QuizzPOJO(String date, String quizzlink, String heading,String imageicon) {
        this.date = date;
        this.quizzlink = quizzlink;
        this.heading = heading;
        this.imageicon=imageicon;
    }

    public QuizzPOJO() {
    }

    public String getImageicon() {
        return imageicon;
    }

    public void setImageicon(String imageicon) {
        this.imageicon = imageicon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuizzlink() {
        return quizzlink;
    }

    public void setQuizzlink(String quizzlink) {
        this.quizzlink = quizzlink;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }


    @Override
    public int compareTo(QuizzPOJO o) {
        return this.getDate().compareTo(o.getDate());
    }
}

