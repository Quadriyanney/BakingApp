package com.quadriyanney.bakingapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.fragments.StepDetailsFragment;

public class StepDetails extends AppCompatActivity {

    String description, videoUrl, thumbnailUrl;
    StepDetailsFragment fragment = new StepDetailsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        if (getIntent().getExtras().getString("description") != null){
            description = getIntent().getExtras().getString("description");
        }
        if (getIntent().getExtras().getString("vUrl") != null){
            videoUrl = getIntent().getExtras().getString("vUrl");
        }
        if (getIntent().getExtras().getString("tUrl") != null){
            thumbnailUrl = getIntent().getExtras().getString("tUrl");
        }

        displayDetails();
    }

    public void displayDetails(){
        fragment.getDetails(description, videoUrl, thumbnailUrl);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment, "stepFrag");
        transaction.commit();
    }
}
