package com.quadriyanney.bakingapp.ui.stepDetails;

import com.quadriyanney.bakingapp.ui.base.BasePresenter;

import javax.inject.Inject;

public class StepDetailsPresenter implements BasePresenter<StepDetailsView> {

    private StepDetailsView stepDetailsView;

    @Inject
    public StepDetailsPresenter() { }

    @Override
    public void attachView(StepDetailsView stepDetailsView) {
        this.stepDetailsView = stepDetailsView;
        this.stepDetailsView.setUpView();
    }

    void playVideo() {
        stepDetailsView.playStepVideo();
    }

    void releasePlayer() {
        stepDetailsView.releasePlayer();
    }

    @Override
    public void detachView() {
        this.stepDetailsView = null;
    }
}
