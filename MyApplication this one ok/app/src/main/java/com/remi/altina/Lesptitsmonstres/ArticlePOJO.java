package com.remi.altina.Lesptitsmonstres;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;

import androidx.annotation.NonNull;

/**
 * Created by umar on 9/18/2019.
 */

public class ArticlePOJO implements Serializable,Comparable<ArticlePOJO> {
    @PropertyName("heading_article")
    public String heading_article;
    @PropertyName("datesorting")
    public String datesorting;

    @PropertyName("category_article")
    public String category_article;
    @PropertyName("plaintext_article_link")
    public String plaintext_article_link;
    @PropertyName("readcolor_article_link")
    public String readcolor_article_link;
    @PropertyName("dyslexia_article_link")
    public String dyslexia_article_link;
    @PropertyName("youtubelink")
    public String youtubelink;
    @PropertyName("iconlink")
    public String iconlink;
    @PropertyName("date_article")
    public String date_article;

    public ArticlePOJO() {
    }

    public ArticlePOJO(String heading_article, String date_article, String category_article, String plaintext_article_link, String readcolor_article_link, String dyslexia_article_link, String youtubelink, String iconlink, String datesorting) {
        this.heading_article = heading_article;
        this.date_article = date_article;
        this.category_article = category_article;
        this.plaintext_article_link = plaintext_article_link;
        this.readcolor_article_link = readcolor_article_link;
        this.dyslexia_article_link = dyslexia_article_link;
        this.youtubelink = youtubelink;
        this.iconlink = iconlink;
        this.datesorting = datesorting;
    }

    public String getHeading_article() {
        return heading_article;
    }

    public void setHeading_article(String heading_article) {
        this.heading_article = heading_article;
    }

    public String getDatesorting() {
        return datesorting;
    }

    public void setDatesorting(String datesorting) {
        this.datesorting = datesorting;
    }

    public String getCategory_article() {
        return category_article;
    }

    public void setCategory_article(String category_article) {
        this.category_article = category_article;
    }

    public String getPlaintext_article_link() {
        return plaintext_article_link;
    }

    public void setPlaintext_article_link(String plaintext_article_link) {
        this.plaintext_article_link = plaintext_article_link;
    }

    public String getReadcolor_article_link() {
        return readcolor_article_link;
    }

    public void setReadcolor_article_link(String readcolor_article_link) {
        this.readcolor_article_link = readcolor_article_link;
    }

    public String getDyslexia_article_link() {
        return dyslexia_article_link;
    }

    public void setDyslexia_article_link(String dyslexia_article_link) {
        this.dyslexia_article_link = dyslexia_article_link;
    }

    public String getYoutubelink() {
        return youtubelink;
    }

    public void setYoutubelink(String youtubelink) {
        this.youtubelink = youtubelink;
    }

    public String getIconlink() {
        return iconlink;
    }

    public void setIconlink(String iconlink) {
        this.iconlink = iconlink;
    }

    public String getDate_article() {
        return date_article;
    }

    public void setDate_article(String date_article) {
        this.date_article = date_article;
    }

    @Override
    public int compareTo(@NonNull ArticlePOJO o) {
        return  this.getDate_article().compareTo(o.getDate_article());
    }
}
