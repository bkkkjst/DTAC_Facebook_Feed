package dev.keepcoding.dtacfacebookfeed.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerResponse {

    @SerializedName("data")
    List<Feed> mFeeds;

    @SerializedName("paging")
    Paging mPaging;

    public List<Feed> getFeeds() {
        return mFeeds;
    }

    public void setFeeds(List<Feed> feeds) {
        mFeeds = feeds;
    }

    public Paging getPaging() {
        return mPaging;
    }

    public void setPaging(Paging paging) {
        mPaging = paging;
    }
}
