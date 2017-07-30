package com.quadriyanney.bakingapp.fragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.quadriyanney.bakingapp.R;

public class StepDetailsFragment extends Fragment {

    String mDescription, mVideoUrl, mThumbnailUrl;
    ImageView thumbnail;
    long currentPosition = 0;
    TextView description;
    SimpleExoPlayerView playerView;
    SimpleExoPlayer player;
    boolean isTwoPane;

    public StepDetailsFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            currentPosition = savedInstanceState.getLong("player_position", 0);
            mDescription = savedInstanceState.getString("desc");
            mVideoUrl = savedInstanceState.getString("vid");
            mThumbnailUrl = savedInstanceState.getString("thumb");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        if (getActivity().findViewById(R.id.detail_container) != null) isTwoPane = true;
        playerView = (SimpleExoPlayerView) view.findViewById(R.id.videoPlayer);
        thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        description = (TextView) view.findViewById(R.id.description);

        if (isTwoPane || getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            if (!TextUtils.isEmpty(mVideoUrl)){
                playerView.setVisibility(View.VISIBLE);
                playVideo();
            }
            if (!TextUtils.isEmpty(mThumbnailUrl)){
                Glide.with(getContext()).asBitmap().load(mThumbnailUrl).into(thumbnail);
                thumbnail.setVisibility(View.VISIBLE);
            }
            if (description != null) description.setText(mDescription);

        } else {
            if (!TextUtils.isEmpty(mVideoUrl)){
                playerView.setVisibility(View.VISIBLE);
                playVideo();
            } else {
                if (!TextUtils.isEmpty(mThumbnailUrl)){
                    Glide.with(getActivity()).asBitmap().load(mThumbnailUrl).into(thumbnail);
                    thumbnail.setVisibility(View.VISIBLE);
                }
                if (description != null) description.setText(mDescription);
            }

        }
        return view;
    }

    private void playVideo() {
        Handler handler = new Handler();
        BandwidthMeter meter = new DefaultBandwidthMeter();
        TrackSelection.Factory selection = new AdaptiveTrackSelection.Factory(meter);
        TrackSelector selector = new DefaultTrackSelector(selection);

        player = ExoPlayerFactory.newSimpleInstance(getActivity(), selector);
        playerView.setPlayer(player);

        DefaultBandwidthMeter widthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSource = new DefaultDataSourceFactory(getActivity(),
                Util.getUserAgent(getActivity(), "BakingApp"), widthMeter);

        ExtractorsFactory factory = new DefaultExtractorsFactory();
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(mVideoUrl), dataSource,
                factory, handler, null);

        player.prepare(mediaSource);

        if (currentPosition != 0) player.seekTo(currentPosition);
        player.setPlayWhenReady(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (player != null) {
            outState.putLong("player_position", player.getCurrentPosition());
        }
        outState.putString("desc", mDescription);
        outState.putString("vid", mVideoUrl);
        outState.putString("thumb", mThumbnailUrl);
    }

    public void getDetails(String description, String videoUrl, String thumbnailUrl){
        mDescription = description;
        mVideoUrl = videoUrl;
        mThumbnailUrl = thumbnailUrl;
    }

    @Override
    public void onResume() {
        super.onResume();
            playVideo();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    public void releasePlayer(){
        if (player != null) {
            player.stop();
            player.release();
        }
    }
}
