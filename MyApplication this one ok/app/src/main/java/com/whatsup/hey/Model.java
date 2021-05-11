package com.whatsup.hey;


/**
 * Created by anupamchugh on 09/02/16.
 */
public class Model {


    public static final int BANNER_TYPE=0;
    public static final int IMAGE_TYPE=1;
    public static final int AUDIO_TYPE=2;

    public int type;
    public String data;
    public int banner;




    public Model(int type, int banner, String data)
    {
        this.type=type;
        this.data=data;
        this.banner=banner;

    }

}
