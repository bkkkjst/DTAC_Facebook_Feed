package dev.keepcoding.dtacfacebookfeed.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.keepcoding.dtacfacebookfeed.R;
import dev.keepcoding.dtacfacebookfeed.model.Feed;

public class FeedRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Feed> mFeeds;

    public FeedRecyclerViewAdapter(Context context, List<Feed> feeds) {
        mContext = context;
        mFeeds = feeds;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_feed, parent, false);
        return new FeedViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof FeedViewHolder) {
            Feed feed = mFeeds.get(position);
            FeedViewHolder feedViewHolder = (FeedViewHolder) holder;
            feedViewHolder.bind(feed);
        }
    }

    public void updateFeeds(List<Feed> feeds) {
        mFeeds = feeds;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mFeeds != null ? mFeeds.size() : 0;
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMessage;
        private TextView tvCreateTime;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMessage = itemView.findViewById(R.id.tv_message);
            tvCreateTime = itemView.findViewById(R.id.tv_create_time);

        }

        public void bind(Feed feed) {
            tvMessage.setText(feed.getMessage());
            tvCreateTime.setText(feed.getCreatedTime());
        }

        private String getDateTime(String strCreateTime) {

            return "";
        }
    }


}
