package com.mindnotix.youthhub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.jaedongchicken.ytplayer.JLog;
import com.jaedongchicken.ytplayer.YoutubePlayerView;
import com.jaedongchicken.ytplayer.model.PlaybackQuality;
import com.jaedongchicken.ytplayer.model.YTParams;

/**
 * Created by Admin on 2/22/2018.
 */

public class YoutubeVideoActivity extends BaseActivity {


    private static final String TAG = YoutubeVideoActivity.class.getSimpleName();
    String youtube_url;
    private YoutubePlayerView youtubePlayerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video);

        if (getIntent() != null) {
            youtube_url = getIntent().getStringExtra("youtube_url");
        }

        // get id from XML
        youtubePlayerView = (YoutubePlayerView) findViewById(R.id.youtubePlayerView);

        // make auto height of youtube. if you want to use 'wrap_content'
        youtubePlayerView.setAutoPlayerHeight(this);

        // Control values : see more # https://developers.google.com/youtube/player_parameters?hl=en
        YTParams params = new YTParams();
        // params.setControls(0);
        // params.setAutoplay(1);
        params.setVolume(100);
        params.setPlaybackQuality(PlaybackQuality.small);

        String youtubeID = getYoutubeID(youtube_url);

        Log.d(TAG, "onCreate: youtubeID "+youtubeID);


        youtubePlayerView.initializeWithCustomURL(youtubeID, params, new YoutubePlayerView.YouTubeListener() {

            @Override
            public void onReady() {
                // when player is ready.
                JLog.i("onReady()");
            }

            @Override
            public void onStateChange(YoutubePlayerView.STATE state) {
                /**
                 * YoutubePlayerView.STATE
                 *
                 * UNSTARTED, ENDED, PLAYING, PAUSED, BUFFERING, CUED, NONE
                 *
                 */

                JLog.i("onStateChange(" + state + ")");
            }

            @Override
            public void onPlaybackQualityChange(String arg) {
                String message = "onPlaybackQualityChange(" + arg + ")";
                Toast.makeText(YoutubeVideoActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPlaybackRateChange(String arg) {
                String message = "onPlaybackRateChange(" + arg + ")";
                Toast.makeText(YoutubeVideoActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String arg) {
                String message = "onError(" + arg + ")";
                Toast.makeText(YoutubeVideoActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onApiChange(String arg) {
                String message = "onApiChange(" + arg + ")";
                Toast.makeText(YoutubeVideoActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCurrentSecond(double second) {
                //   currentSec.setText(String.valueOf(second));
                Log.d(TAG, "onCurrentSecond: " + second);
            }

            @Override
            public void onDuration(double duration) {
                String message = "onDuration(" + duration + ")";
                JLog.i(message);
            }

            @Override
            public void logs(String log) {
                // javascript debug log. you don't need to use it.
                JLog.d(log);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        // pause video when on the background mode.
        youtubePlayerView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // this is optional but you need.
        youtubePlayerView.destroy();
    }
}
