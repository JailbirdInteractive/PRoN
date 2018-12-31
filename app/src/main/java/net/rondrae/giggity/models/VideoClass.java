package net.rondrae.giggity.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to create Objects for each video
 * <p>
 * Creates each object by parsing JSON response from Redtube API call
 *
 * @Author Adrian
 * @Version 1.0
 */
public class VideoClass {
    public String videoID, videoTitle, videoDescription, videoUrl, thumbnail,duration;
    public String vid_views;
    public String rating;
    public List<String> thumbUrls, tags;

    public VideoClass(String videoID, String videoTitle, String videoDescription, String videoUrl, String thumbnail, String vid_views, String rating,String duration, List<String> thumbUrls, List<String> tags) {
        this.videoTitle = videoTitle;
        this.videoDescription = videoDescription;
        this.videoUrl = videoUrl;
        this.thumbnail = thumbnail;
        this.vid_views = vid_views;
        this.rating = rating;
        this.thumbUrls = thumbUrls;
        this.videoID = videoID;
        this.tags = tags;
    }

    public VideoClass() {

    }

    /**
     * This method accepts a single JSON Object and parses it into a video Object
     * @param jsonObject
     * @return
     */
    public static VideoClass fromJson(JSONObject jsonObject) {
        VideoClass videoClass = new VideoClass();
        try {
            videoClass.videoID = jsonObject.getString("video_id");
            videoClass.setVideoTitle(jsonObject.getString("title"));
            videoClass.setVideoUrl(jsonObject.getString("url"));
            videoClass.setRating((jsonObject.getString("rating")));
            videoClass.setThumbnail(jsonObject.getString("default_thumb"));
            videoClass.setThumbUrls(null);
            videoClass.setTags(null);
            videoClass.duration=jsonObject.getString("duration");
            videoClass.setVid_views(jsonObject.getString("views"));

            String desc=String.format("Views: %s   Duration: %s",videoClass.vid_views,videoClass.duration);
            videoClass.videoDescription=desc;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return videoClass;
    }

    /**
     * This method Accepts a JSON Array, iterates through it and parses each element into a VideoClass Object
     * @param jsonArray
     * @return ArrayList VideoClass
     */
    public static ArrayList<VideoClass> fromJson(JSONArray jsonArray){
        JSONObject videoJsonObject;
        ArrayList<VideoClass> videoClasses=new ArrayList<VideoClass>(jsonArray.length());
        for (int i=0;i<jsonArray.length();i++){
            try {
                videoJsonObject=jsonArray.getJSONObject(i);

            } catch (JSONException e) {
                e.printStackTrace();
            continue;
            }
            VideoClass videoClass=VideoClass.fromJson(videoJsonObject);
            if(videoClass!=null){
                videoClasses.add(videoClass);
            }
        }

        return videoClasses;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVid_views() {
        return vid_views;
    }

    public void setVid_views(String vid_views) {
        this.vid_views = vid_views;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<String> getThumbUrls() {
        return thumbUrls;
    }

    public void setThumbUrls(List<String> thumbUrls) {
        this.thumbUrls = thumbUrls;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

}
