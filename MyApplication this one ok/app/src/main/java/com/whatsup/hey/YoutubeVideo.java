package com.whatsup.hey;

/**
 * Created by umar on 10/1/2019.
 */

public class YoutubeVideo {
  String videourl;
  String videoname;

    public YoutubeVideo() {
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }

    public YoutubeVideo(String videourl,String videoname) {
        this.videourl = videourl;
        this.videoname= videoname;
    }


    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }
}
