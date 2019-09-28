package dev.keepcoding.dtacfacebookfeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.keepcoding.dtacfacebookfeed.adapter.FeedRecyclerViewAdapter;
import dev.keepcoding.dtacfacebookfeed.model.ServerResponse;
import dev.keepcoding.dtacfacebookfeed.model.Feed;

public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private AccessTokenTracker accessTokenTracker;

    private static final String TAG = "MainActivity";

    private RecyclerView rvFeed;
    private ProgressBar loading;
    private FeedRecyclerViewAdapter mFeedRecyclerViewAdapter;

    private List<Feed> mFeeds = new ArrayList<>();

    private ServerResponse mServerResponse;

    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialView
        rvFeed = findViewById(R.id.rv_feed);
        loading = findViewById(R.id.pb_loading);

        mFeedRecyclerViewAdapter = new FeedRecyclerViewAdapter(getApplicationContext(), mFeeds);
        rvFeed.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvFeed.addItemDecoration(new DividerItemDecoration(getApplicationContext(), RecyclerView.VERTICAL));
        rvFeed.setItemAnimator(new DefaultItemAnimator());
        rvFeed.setAdapter(mFeedRecyclerViewAdapter);

        rvFeed.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading && !mFeeds.isEmpty()) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mFeeds.size() - 1) {
                        //bottom of list
                        loadMore();
                        isLoading = true;
                        loading.setVisibility(View.VISIBLE);
                    }
                }
            }
        });


        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile", "user_posts"));

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.i(TAG, "onSuccess: ");

                AccessToken accessToken = loginResult.getAccessToken();
                Log.i(TAG, "accessToken: " + accessToken.getToken());


                /* make the API call */
                GraphRequest request = new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me/feed",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                /* handle the result */
                                Log.i(TAG, "response: " + response.toString());

                                Gson gson = new Gson();
                                mServerResponse = gson.fromJson(response.getRawResponse(), ServerResponse.class);
                                mFeeds = mServerResponse.getFeeds();

                                mFeedRecyclerViewAdapter.updateFeeds(mFeeds);

                                loading.setVisibility(View.GONE);
                            }
                        }
                );
                request.executeAsync();


            }

            @Override
            public void onCancel() {
                // App code
                Log.i(TAG, "onCancel: ");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.i(TAG, "onError: " + exception.toString());
            }
        });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    //write your code here what to do when user logout
                    mFeeds.clear();
                    mFeedRecyclerViewAdapter.updateFeeds(mFeeds);
                }
            }
        };
    }


    private void loadMore() {

        if(mServerResponse == null){
            return;
        }

        Uri uri = Uri.parse(mServerResponse.getPaging().getNext());
        String query = uri.getEncodedQuery();


        /* make the API call */
        GraphRequest request = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "me/feed?"+query
                ,
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        /* handle the result */
                        Log.i(TAG, "response: " + response.toString());

                        Gson gson = new Gson();
                        ServerResponse data = gson.fromJson(response.getRawResponse(), ServerResponse.class);


                        if(data != null){
                            if (mFeeds.addAll(data.getFeeds())) {
                                mServerResponse = data;
                            }
                        }


                        isLoading = false;

                        loading.setVisibility(View.GONE);
                    }
                }
        );
        request.executeAsync();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
