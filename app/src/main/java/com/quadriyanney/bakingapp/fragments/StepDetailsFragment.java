package com.quadriyanney.bakingapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quadriyanney.bakingapp.R;


public class StepDetailsFragment extends Fragment {

    String mDescription, mVideoUrl, mThumbnailUrl;
    ImageView thumbnail;
    long currentPosition = 0;
    TextView description;


    public StepDetailsFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        description = (TextView) view.findViewById(R.id.description);

        return view;
    }

    public void getDetails(String description, String videoUrl, String thumbnailUrl){
        mDescription = description;
        mVideoUrl = videoUrl;
        mThumbnailUrl = thumbnailUrl;
    }

}
