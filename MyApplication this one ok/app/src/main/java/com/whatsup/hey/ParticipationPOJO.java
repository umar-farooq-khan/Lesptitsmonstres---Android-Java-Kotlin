package com.whatsup.hey;

import com.google.firebase.database.PropertyName;

/**
 * Created by umar on 10/4/2019.
 */

public class ParticipationPOJO {
    @PropertyName("fromEmail")

    public String fromEmail;
    @PropertyName("fromPassword")

    public String fromPassword;
    @PropertyName("toMailList")

    public String  toMailList;

    public ParticipationPOJO(String fromEmail, String fromPassword, String toMailList) {
        this.fromEmail = fromEmail;
        this.fromPassword = fromPassword;
        this.toMailList = toMailList;
    }

    public ParticipationPOJO() {
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getFromPassword() {
        return fromPassword;
    }

    public void setFromPassword(String fromPassword) {
        this.fromPassword = fromPassword;
    }

    public String getToMailList() {
        return toMailList;
    }

    public void setToMailList(String toMailList) {
        this.toMailList = toMailList;
    }
}

