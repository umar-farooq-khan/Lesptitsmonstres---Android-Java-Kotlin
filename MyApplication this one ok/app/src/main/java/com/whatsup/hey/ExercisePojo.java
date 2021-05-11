package com.whatsup.hey;

import androidx.annotation.NonNull;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;

/**
 * Created by umar on 9/24/2019.
 */

public class ExercisePojo implements Serializable,Comparable<ExercisePojo> {
    @PropertyName("pcategory")
    public String    pcategory;
    @PropertyName("pheading")
    public String   pheading;
    @PropertyName("piconlink")

    public String   piconlink;
    @PropertyName("pdyslexiclink")
    public String    pdyslexiclink;
    @PropertyName("preadcolor")
    public String    preadcolor;
    @PropertyName("psimpleblack")
    public String     psimpleblack;
    @PropertyName("pdate")
    public String    pdate;
    @PropertyName("pdflink")
    public String pdflink;

    public ExercisePojo() {

    }

    public ExercisePojo(String pcategory, String pheading, String piconlink, String pdyslexiclink, String preadcolor, String psimpleblack, String pdate, String pdflink) {
        this.pcategory = pcategory;
        this.pheading = pheading;
        this.piconlink = piconlink;
        this.pdyslexiclink = pdyslexiclink;
        this.preadcolor = preadcolor;
        this.psimpleblack = psimpleblack;
        this.pdate = pdate;
        this.pdflink = pdflink;
    }


    public String getPcategory() {
        return pcategory;
    }

    public void setPcategory(String pcategory) {
        this.pcategory = pcategory;
    }

    public String getPheading() {
        return pheading;
    }

    public void setPheading(String pheading) {
        this.pheading = pheading;
    }

    public String getPiconlink() {
        return piconlink;
    }

    public void setPiconlink(String piconlink) {
        this.piconlink = piconlink;
    }

    public String getPdyslexiclink() {
        return pdyslexiclink;
    }

    public void setPdyslexiclink(String pdyslexiclink) {
        this.pdyslexiclink = pdyslexiclink;
    }

    public String getPreadcolor() {
        return preadcolor;
    }

    public void setPreadcolor(String preadcolor) {
        this.preadcolor = preadcolor;
    }

    public String getPsimpleblack() {
        return psimpleblack;
    }

    public void setPsimpleblack(String psimpleblack) {
        this.psimpleblack = psimpleblack;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public String getPdflink() {
        return pdflink;
    }

    public void setPdflink(String pdflink) {
        this.pdflink = pdflink;
    }

    @Override
    public int compareTo(@NonNull ExercisePojo o) {
        return this.getPreadcolor().compareTo(o.getPreadcolor());

    }
}