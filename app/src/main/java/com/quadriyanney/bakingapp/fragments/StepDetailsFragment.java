package com.quadriyanney.bakingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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

    public StepDetailsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        description = (TextView) view.findViewById(R.id.description);
        playerView = (SimpleExoPlayerView) view.findViewById(R.id.videoPlayer);

        if (!mThumbnailUrl.equals("")){
            thumbnail.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(mThumbnailUrl).into(thumbnail);
        }

        if (description != null){
            description.setText(mDescription);
        }

        if (!mVideoUrl.equals("")){
            playerView.setVisibility(View.VISIBLE);
            playVideo();
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

    public void getDetails(String description, String videoUrl, String thumbnailUrl){
        mDescription = description;
        mVideoUrl = videoUrl;
        mThumbnailUrl = thumbnailUrl;
    }
}
