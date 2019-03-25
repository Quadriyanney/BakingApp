package com.quadriyanney.bakingapp.ui.stepDetails;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.data.model.Step;
import com.quadriyanney.bakingapp.util.Constants;

import java.util.List;

public class StepDetailsActivity extends AppCompatActivity {

    ImageButton btnNext, btnPrevious;

    int position;
    List<Step> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(view -> {
            position++;
            updateStepDetails();
        });

        btnPrevious = findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(view -> {
            position--;
            updateStepDetails();
        });

        steps = getIntent().getParcelableArrayListExtra(Constants.EXTRA_STEPS_LIST);

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt(Constants.EXTRA_POSITION);
        } else {
            position = getIntent().getIntExtra(Constants.EXTRA_POSITION, 0);
        }

        updateStepDetails();
    }

    public void updateStepDetails() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            btnNext.setVisibility(View.INVISIBLE);
            btnPrevious.setVisibility(View.INVISIBLE);
        } else {
            btnPrevious.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
            btnNext.setVisibility(position == (steps.size() - 1) ? View.INVISIBLE : View.VISIBLE);
        }

        StepDetailsFragment fragment = StepDetailsFragment.newInstance(steps.get(position));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, StepDetailsFragment.TAG)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.EXTRA_POSITION, position);
    }
}
