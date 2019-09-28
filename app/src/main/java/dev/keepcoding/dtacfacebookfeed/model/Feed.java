package dev.keepcoding.dtacfacebookfeed.model;

import com.google.gson.annotations.SerializedName;

public class Feed {

    /**
     * created_time : 2017-12-08T01:08:57+0000
     * message : Love this puzzle. One of my favorite puzzles
     * id : post-id
     */

    @SerializedName("created_time")
    private String createdTime;
    @SerializedName("message")
    private String message;
    @SerializedName("id")
    private String id;

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
