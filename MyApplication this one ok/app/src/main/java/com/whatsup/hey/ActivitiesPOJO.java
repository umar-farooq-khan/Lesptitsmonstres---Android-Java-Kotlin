package com.whatsup.hey;


import java.io.Serializable;

import androidx.annotation.NonNull;

public class ActivitiesPOJO implements Serializable,Comparable<ActivitiesPOJO> {
    String heading ,date, bannerpic, simpletext, pdflink, someother,another;
    public ActivitiesPOJO() {

    }
    public  ActivitiesPOJO (String heading, String date, String bannerpic, String simpletext, String pdflink, String someother, String another)
    {
        this.heading=heading;
        this.date=date;
        this.bannerpic=bannerpic;
        this.simpletext=simpletext;
        this.pdflink=pdflink;
        this.someother=someother;
        this.another=another;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBannerpic() {
        return bannerpic;
    }

    public void setBannerpic(String bannerpic) {
        this.bannerpic = bannerpic;
    }

    public String getSimpletext() {
        return simpletext;
    }

    public void setSimpletext(String simpletext) {
        this.simpletext = simpletext;
    }

    public String getPdflink() {
        return pdflink;
    }

    public void setPdflink(String pdflink) {
        this.pdflink = pdflink;
    }

    public String getSomeother() {
        return someother;
    }

    public void setSomeother(String someother) {
        this.someother = someother;
    }

    public String getAnother() {
        return another;
    }

    public void setAnother(String another) {
        this.another = another;
    }

    @Override
    public int compareTo(@NonNull ActivitiesPOJO o) {
        return  this.getDate().compareTo(o.getDate());
    }

}
