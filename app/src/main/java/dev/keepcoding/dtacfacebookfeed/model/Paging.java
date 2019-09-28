package dev.keepcoding.dtacfacebookfeed.model;

import com.google.gson.annotations.SerializedName;

public class Paging {

    /**
     * previous : https://graph.facebook.com/v4.0/2626167720751781/feed?format=json&since=1569464153&access_token=EAAK0cX70EgUBAAQAfeFro5vy1EaUvALS3EH5a49MV8UwXQSlesEcIUZCdfK5LfmdEisJdZAcC0i9CJN3ys9FsJZAszZC6MiFHu7jCmW3oSFEWRd02XJcvru49DiUTlsFNxyWbN2W7RJ7oj5SPDL2iaWlzHuVda0UXailzUX9ohCsf0pRsb8mrhJgymkWo8rfF9qyE0jsY68EJYLUV03F4ga1yX4VvbdocExKdIH5UtgiwyPT6ZCJW&limit=25&__paging_token=enc_AdCbOsjCKLoI8Cu1XaoL6GQeFats7WXKQhaQhNMURVoWpvYZAtOeNDMX5gVfDkyFGRjVaYcMZAPHgQJ2FhIbrxOrlPD7Y7tD1mF0mXrSlwwqmzBgZDZD&__previous=1
     * next : https://graph.facebook.com/v4.0/2626167720751781/feed?format=json&access_token=EAAK0cX70EgUBAAQAfeFro5vy1EaUvALS3EH5a49MV8UwXQSlesEcIUZCdfK5LfmdEisJdZAcC0i9CJN3ys9FsJZAszZC6MiFHu7jCmW3oSFEWRd02XJcvru49DiUTlsFNxyWbN2W7RJ7oj5SPDL2iaWlzHuVda0UXailzUX9ohCsf0pRsb8mrhJgymkWo8rfF9qyE0jsY68EJYLUV03F4ga1yX4VvbdocExKdIH5UtgiwyPT6ZCJW&limit=25&until=1546168867&__paging_token=enc_AdBg47iLFtVHMgXpdUU1z1JQgQ9ZANPZAZAqpJZCoe2ueHs8JL174Ig0FePoFvCrLaQTRynX4tDoBS2gvatNXfQMhh4vHHoMPcaKdZBtGNJD6vClq6wZDZD
     */

    @SerializedName("previous")
    private String previous;
    @SerializedName("next")
    private String next;

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
