package com.remi.altina.Lesptitsmonstres;

import androidx.annotation.NonNull;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;

/**
 * Created by umar on 9/20/2019.
 */

public class ParentPOJO implements Serializable,Comparable<ParentPOJO> {
    @PropertyName("heading_article")

    public String heading_article;
    @PropertyName("plaintext_article")

    public String plaintext_article;
    @PropertyName("date_article")

    public String date_article;
    @PropertyName("iconlink")
    public String iconlink;
    @PropertyName("category")

    public String category;

    @PropertyName("youtubelink")

    public String youtubelink;

    public ParentPOJO() {
    }

    public ParentPOJO(String heading_article, String plaintext_article, String date_article, String iconlink, String category,String youtubelink) {
        this.heading_article = heading_article;
        this.plaintext_article = plaintext_article;
        this.date_article = date_article;
        this.iconlink= iconlink;
        this.category= category;
        this.youtubelink=youtubelink;
    }

    public String getYoutubelink() {
        return youtubelink;
    }

    public void setYoutubelink(String youtubelink) {
        this.youtubelink = youtubelink;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHeading_article() {
        return heading_article;
    }

    public void setHeading_article(String heading_article) {
        this.heading_article = heading_article;
    }

    public String getPlaintext_article() {
        return plaintext_article;
    }

    public void setPlaintext_article(String plaintext_article) {
        this.plaintext_article = plaintext_article;
    }

    public String getDate_article() {
        return date_article;
    }

    public void setDate_article(String date_article) {
        this.date_article = date_article;
    }

    public String getIconlink() {
        return iconlink;
    }

    public void setIconlink(String iconlink) {
        this.iconlink = iconlink;
    }

    @Override
    public int compareTo(@NonNull ParentPOJO o) {
        return this.getDate_article().compareTo(o.getDate_article());

    }
}

