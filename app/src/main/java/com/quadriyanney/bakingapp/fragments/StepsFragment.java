package com.quadriyanney.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.activities.RecipeDetails;
import com.quadriyanney.bakingapp.adapters.StepsAdapter;
import com.quadriyanney.bakingapp.data.StepsInfo;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quadriy on 6/21/17.
 */

public class StepsFragment extends Fragment implements StepsAdapter.StepItemClickListener {

    List<StepsInfo> stepsList = new ArrayList<>();
    StepsAdapter stepsAdapter;
    RecyclerView recyclerView;
    String mStepsList, mShortDescription, mDescription, mVideoUrl, mThumbnailUrl;
    JSONArray jsonSteps;
    int counter = 0;
    RecipeDetails recipeDetails;

    public StepsFragment () {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.steps_layout, container, false);

        setAttributes();

        stepsAdapter = new StepsAdapter(stepsList, this);
        recyclerView = (RecyclerView) view.findViewById(R.id.stepsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(stepsAdapter);

        return view;
    }

    public void setAttributes(){
        try {
            jsonSteps = new JSONArray(mStepsList);

            while (counter < jsonSteps.length()){
                mShortDescription = jsonSteps.getJSONObject(counter).getString("shortDescription");
                mDescription = jsonSteps.getJSONObject(counter).getString("description");
                mVideoUrl = jsonSteps.getJSONObject(counter).getString("videoURL");
                mThumbnailUrl = jsonSteps.getJSONObject(counter).getString("thumbnailURL");

                stepsList.add(new StepsInfo(mShortDescription, mDescription, mVideoUrl, mThumbnailUrl));
                counter++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStepItemClicked(int clicked) {
//        recipeDetails.launchDetailActivity(stepsList.get(clicked).getDescription(),
//                stepsList.get(clicked).getVideoUrl(),
//                stepsList.get(clicked).getThumbnailUrl());

        Log.d("text", stepsList.get(clicked).getDescription() + stepsList.get(clicked).getVideoUrl()
        + stepsList.get(clicked).getThumbnailUrl())
    }

    public void getStepsList(String stepsList){
        mStepsList = stepsList;
    }
}
