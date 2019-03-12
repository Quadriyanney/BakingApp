package com.quadriyanney.bakingapp.ui.stepDetails;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import com.quadriyanney.bakingapp.data.model.Step;
import com.quadriyanney.bakingapp.helper.Constants;

public class StepDetailsFragment extends Fragment {

    public static final String TAG = StepDetailsFragment.class.getSimpleName();
    private static final String ARGUMENT_STEP = "ARGUMENT_STEP";

    Step step;
    ImageView thumbnail;
    long currentPosition = 0;
    TextView description;
    SimpleExoPlayerView playerView;
    SimpleExoPlayer player;
    boolean isTwoPane;

    public StepDetailsFragment() {
    }

    public static StepDetailsFragment newInstance(Step step) {
        StepDetailsFragment fragment = new StepDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_STEP, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getLong("player_position", 0);
            step = savedInstanceState.getParcelable(Constants.EXTRA_STEP);
        } else {
            step = getArguments().getParcelable(ARGUMENT_STEP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        if (getActivity().findViewById(R.id.detail_container) != null) isTwoPane = true;
        playerView = view.findViewById(R.id.videoPlayer);
        thumbnail = view.findViewById(R.id.ivThumbnail);
        description = view.findViewById(R.id.tvDescription);

        if (isTwoPane || getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (!TextUtils.isEmpty(step.getVideoURL())) {
                playerView.setVisibility(View.VISIBLE);
                playVideo();
            }
            if (!TextUtils.isEmpty(step.getThumbnailURL())) {
                Glide.with(getContext()).asBitmap().load(step.getThumbnailURL()).into(thumbnail);
                thumbnail.setVisibility(View.VISIBLE);
            }
            if (description != null) description.setText(step.getDescription());

        } else {
            if (!TextUtils.isEmpty(step.getVideoURL())) {
                playerView.setVisibility(View.VISIBLE);
                playVideo();
            } else {
                if (!TextUtils.isEmpty(step.getThumbnailURL())) {
                    Glide.with(getActivity()).asBitmap().load(step.getThumbnailURL()).into(thumbnail);
                    thumbnail.setVisibility(View.VISIBLE);
                }
                if (description != null) description.setText(step.getDescription());
            }

        }
        return view;
    }

    private void playVideo() {
        if (player == null) {
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
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(step.getVideoURL()), dataSource,
                    factory, handler, null);

            player.prepare(mediaSource);

            if (currentPosition != 0) player.seekTo(currentPosition);
            player.setPlayWhenReady(true);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (player != null) {
            outState.putLong("player_position", player.getCurrentPosition());
        }
        outState.putParcelable(Constants.EXTRA_STEP, step);
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

    public void releasePlayer() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }
}
