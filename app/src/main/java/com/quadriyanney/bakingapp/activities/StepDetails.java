package com.quadriyanney.bakingapp.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.data.StepsInfo;
import com.quadriyanney.bakingapp.fragments.StepDetailsFragment;

import java.util.ArrayList;

public class StepDetails extends AppCompatActivity {

    ArrayList<StepsInfo> stepsList = new ArrayList<>();
    int position;
    Button nextButton, previousButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        nextButton = (Button) findViewById(R.id.nextButton);
        previousButton = (Button) findViewById(R.id.previousButton);

        if (getIntent().getExtras().getParcelableArrayList("steps_list") != null) {
            stepsList = getIntent().getExtras().getParcelableArrayList("steps_list");
        }

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position");
            positionChecker();
        } else {
            position = getIntent().getExtras().getInt("position", 0);
            positionChecker();
        }

        displayDetails();
    }

    public void displayDetails(){
        StepDetailsFragment fragment = new StepDetailsFragment();
        fragment.getDetails(stepsList.get(position).getDescription(),
                stepsList.get(position).getVideoUrl(),
                stepsList.get(position).getThumbnailUrl());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, "details").commit();
    }

    public void Navigate(View view) {
        if (view.getId() == R.id.previousButton) {
            position = position - 1;
            positionChecker();
            displayDetails();
        }
        else if (view.getId() == R.id.nextButton) {
            position = position + 1;
            positionChecker();
            displayDetails();
        }
    }

    public void positionChecker() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            nextButton.setVisibility(View.INVISIBLE);
            previousButton.setVisibility(View.INVISIBLE);
        } else {
            if (position == 0) {
                previousButton.setVisibility(View.INVISIBLE);
            } else if (position == (stepsList.size() - 1)) {
                nextButton.setVisibility(View.INVISIBLE);
            } else {
                previousButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
    }
}
