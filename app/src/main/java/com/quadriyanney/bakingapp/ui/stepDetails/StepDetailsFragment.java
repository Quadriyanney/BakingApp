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
import com.quadriyanney.bakingapp.App;
import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.data.model.Step;
import com.quadriyanney.bakingapp.util.Constants;
import com.quadriyanney.bakingapp.util.ImageUtil;

import javax.inject.Inject;

public class StepDetailsFragment extends Fragment implements StepDetailsView {

    public static final String TAG = StepDetailsFragment.class.getSimpleName();
    private static final String ARGUMENT_STEP = "ARGUMENT_STEP";
    private static final String PLAYER_POSITION = "PLAYER_POSITION";

    long currentPosition = 0;
    boolean isTwoPane;

    TextView tvDescription;
    SimpleExoPlayerView simpleExoPlayerView;
    SimpleExoPlayer simpleExoPlayer;
    ImageView ivThumbnail;

    @Inject
    ImageUtil imageUtil;
    @Inject
    StepDetailsPresenter stepDetailsPresenter;
    Step step;

    public StepDetailsFragment() { }

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
            currentPosition = savedInstanceState.getLong(PLAYER_POSITION, 0);
            step = savedInstanceState.getParcelable(Constants.EXTRA_STEP);
        } else {
            step = getArguments().getParcelable(ARGUMENT_STEP);
        }

        App.getDependencyComponent().inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);

        isTwoPane = getActivity().findViewById(R.id.detail_container) != null;

        simpleExoPlayerView = view.findViewById(R.id.videoPlayer);
        ivThumbnail = view.findViewById(R.id.ivThumbnail);
        tvDescription = view.findViewById(R.id.tvDescription);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stepDetailsPresenter.attachView(this);
    }

    @Override
    public void setUpView() {
        if (isTwoPane || getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (!TextUtils.isEmpty(step.getVideoURL())) {
                simpleExoPlayerView.setVisibility(View.VISIBLE);
                stepDetailsPresenter.playVideo();
            }

            if (!TextUtils.isEmpty(step.getThumbnailURL())) {
                imageUtil.loadImageAsBitmap(step.getThumbnailURL(), ivThumbnail);
                ivThumbnail.setVisibility(View.VISIBLE);
            }

            if (tvDescription != null) {
                tvDescription.setText(step.getDescription());
            }
        } else {
            if (!TextUtils.isEmpty(step.getVideoURL())) {
                simpleExoPlayerView.setVisibility(View.VISIBLE);
                stepDetailsPresenter.playVideo();
            } else {
                if (!TextUtils.isEmpty(step.getThumbnailURL())) {
                    imageUtil.loadImageAsBitmap(step.getThumbnailURL(), ivThumbnail);
                    ivThumbnail.setVisibility(View.VISIBLE);
                }

                if (tvDescription != null) {
                    tvDescription.setText(step.getDescription());
                }
            }
        }
    }

    @Override
    public void playStepVideo() {
        Handler handler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory trackSelection = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(trackSelection);

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        simpleExoPlayerView.setPlayer(simpleExoPlayer);

        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSource = new DefaultDataSourceFactory(getActivity(),
                Util.getUserAgent(getActivity(), getString(R.string.app_name)), defaultBandwidthMeter);

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource mediaSource = new ExtractorMediaSource(
                Uri.parse(step.getVideoURL()), dataSource, extractorsFactory, handler, null);

        simpleExoPlayer.prepare(mediaSource);

        if (currentPosition != 0) {
            simpleExoPlayer.seekTo(currentPosition);
        }
        simpleExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (simpleExoPlayer != null) {
            outState.putLong(PLAYER_POSITION, simpleExoPlayer.getCurrentPosition());
        }
        outState.putParcelable(Constants.EXTRA_STEP, step);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stepDetailsPresenter.releasePlayer();
        stepDetailsPresenter.detachView();
    }

}
