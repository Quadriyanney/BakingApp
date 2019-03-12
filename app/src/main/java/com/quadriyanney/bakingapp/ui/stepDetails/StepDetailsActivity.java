package com.quadriyanney.bakingapp.ui.stepDetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.data.model.Step;
import com.quadriyanney.bakingapp.helper.Constants;

import java.util.List;

public class StepDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnNext, btnPrevious;
    List<Step> steps;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);

        btnPrevious = findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(this);

        steps = getIntent().getParcelableArrayListExtra(Constants.EXTRA_STEPS_LIST);

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt(Constants.EXTRA_POSITION);
        } else {
            position = getIntent().getIntExtra(Constants.EXTRA_POSITION, 0);
        }

        updateStepDetails();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                position++;
                break;
            case R.id.btnPrevious:
                position--;
                break;
        }

        updateStepDetails();
    }

    private void manageButtonVisibility() {
        btnPrevious.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
        btnNext.setVisibility(position == (steps.size() - 1) ? View.INVISIBLE : View.VISIBLE);
    }

    public void updateStepDetails() {
        manageButtonVisibility();

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
