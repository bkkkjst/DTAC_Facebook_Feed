package dev.keepcoding.dtacfacebookfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.keepcoding.dtacfacebookfeed.adapter.FeedRecyclerViewAdapter;
import dev.keepcoding.dtacfacebookfeed.model.Data;
import dev.keepcoding.dtacfacebookfeed.model.Feed;

public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton loginButton;

    private static final String TAG = "MainActivity";

    private RecyclerView rvFeed;
    private FeedRecyclerViewAdapter mFeedRecyclerViewAdapter;

    private List<Feed> mFeeds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialView
        rvFeed = findViewById(R.id.rv_feed);

        mFeedRecyclerViewAdapter = new FeedRecyclerViewAdapter(getApplicationContext(), mFeeds);
        rvFeed.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvFeed.addItemDecoration(new DividerItemDecoration(getApplicationContext(), RecyclerView.VERTICAL));
        rvFeed.setItemAnimator(new DefaultItemAnimator());
        rvFeed.setAdapter(mFeedRecyclerViewAdapter);


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
                Log.i(TAG, "accessToken: "+accessToken.getToken());


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
                                Data data = gson.fromJson(response.getRawResponse(), Data.class);

                                mFeedRecyclerViewAdapter.updateFeeds(data.getFeeds());
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
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
