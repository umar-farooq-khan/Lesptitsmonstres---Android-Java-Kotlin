package com.remi.altina.Lesptitsmonstres;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;

/**
 * Created by umar on 9/28/2019.
 */

public class PostArticlePOJO implements Serializable {
    @PropertyName("article")
    public String article;

    public PostArticlePOJO() {
    }

    public PostArticlePOJO(String article) {
        this.article = article;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }
}

