package com.remi.altina.Lesptitsmonstres;

import androidx.annotation.NonNull;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;

/**
 * Created by umar on 9/20/2019.
 */

public class RecipePOJO implements Serializable,Comparable<RecipePOJO> {
    @PropertyName("heading_article")

    public String heading_article;
    @PropertyName("plaintext_article")

    public String plaintext_article;
    @PropertyName("date_article")

    public String  date_article;
    @PropertyName("readcolor_article")

    public String  readcolor_article;
    @PropertyName("pdflink")

    public String  pdflink;
    @PropertyName("dyslexicfonts")

    public String  dyslexicfonts;
    @PropertyName("imageicon")

    public String  imageicon;

    public RecipePOJO(String heading_article, String plaintext_article, String date_article, String readcolor_article, String pdflink, String dyslexicfonts, String imageicon) {
        this.heading_article = heading_article;
        this.plaintext_article = plaintext_article;
        this.date_article = date_article;
        this.readcolor_article = readcolor_article;
        this.pdflink = pdflink;
        this.dyslexicfonts = dyslexicfonts;
        this.imageicon = imageicon;
    }

    public RecipePOJO() {
    }

    public String getImageicon() {
        return imageicon;
    }

    public void setImageicon(String imageicon) {
        this.imageicon = imageicon;
    }

    public String getDyslexicfonts() {
        return dyslexicfonts;
    }

    public void setDyslexicfonts(String dyslexicfonts) {
        this.dyslexicfonts = dyslexicfonts;
    }

    public String getPdflink() {
        return pdflink;
    }

    public void setPdflink(String pdflink) {
        this.pdflink = pdflink;
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

    public String getReadcolor_article() {
        return readcolor_article;
    }

    public void setReadcolor_article(String readcolor_article) {
        this.readcolor_article = readcolor_article;
    }

    @Override
    public int compareTo(@NonNull RecipePOJO o) {
        return this.getDate_article().compareTo(o.getDate_article());

    }
}

