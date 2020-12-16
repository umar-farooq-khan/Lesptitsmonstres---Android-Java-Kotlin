package com.remi.altina.Lesptitsmonstres;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by umar on 10/4/2019.
 */

public class SendMailTask extends AsyncTask {

    private ProgressDialog statusDialog;
    private Activity sendMailActivity;

    public SendMailTask(Activity activity) {
        sendMailActivity = activity;

    }

    protected void onPreExecute() {
        statusDialog = new ProgressDialog(sendMailActivity);
        statusDialog.setMessage("Un instant s'il vous plaît...");
        statusDialog.setIndeterminate(false);
        statusDialog.setCancelable(false);
        statusDialog.show();

    }
    @Override
    protected Object doInBackground(Object... args) {
        try {
           // Log.i("SendMailTask", "About to instantiate GMail...");
            //publishProgress("Processing input....");
            GMail androidEmail = new GMail(args[0].toString(),
                    args[1].toString(), (String) args[2], args[3].toString(),
                    args[4].toString());

            androidEmail.createEmailMessage();
            publishProgress("Envoi d'un e-mail");
            androidEmail.sendEmail();
            publishProgress("Votre message a été envoyé, merci.");
            Toast.makeText(sendMailActivity, "Votre message a été envoyé, merci.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            publishProgress(e.getMessage());
        }
        return null;
    }

    @Override
    public void onProgressUpdate(Object... values) {
       // statusDialog.setMessage(values[0].toString());

    }

    @Override
    public void onPostExecute(Object result) {
        Toast.makeText(sendMailActivity, "Votre message a été envoyé, merci.", Toast.LENGTH_SHORT).show();

        statusDialog.dismiss();
    }

}