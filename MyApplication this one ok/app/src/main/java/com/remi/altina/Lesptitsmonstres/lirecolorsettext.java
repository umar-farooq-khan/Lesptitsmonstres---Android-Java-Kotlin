package com.remi.altina.Lesptitsmonstres;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

   public  class lirecolorsettext {
      public static Spanned setthetext(String htmlstring)
        {
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
            // we are using this flag to give a consistent behaviour
            //Log.e("above ","aobve");
                 return Html.fromHtml(htmlstring, Html.FROM_HTML_MODE_LEGACY);
        } else {
           return  Html.fromHtml(htmlstring);
        }
        }
    }

