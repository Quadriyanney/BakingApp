package com.quadriyanney.bakingapp.ui.stepsList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.data.model.Step;
import com.quadriyanney.bakingapp.util.Constants;
import com.quadriyanney.bakingapp.ui.stepDetails.StepDetailsActivity;

import java.util.ArrayList;

import static com.quadriyanney.bakingapp.util.Constants.EXTRA_STEPS_LIST;

public class StepsListFragment extends Fragment implements StepsAdapter.StepItemClickListener {

    private static final String ARGUMENT_STEPS_LIST = "ARGUMENT_STEPS_LIST";

    RecyclerView recyclerView;

    ArrayList<Step> steps;
    StepsAdapter stepsAdapter;

    public StepsListFragment() { }

    public static StepsListFragment newInstance(ArrayList<Step> steps) {
        StepsListFragment stepsListFragment = new StepsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_STEPS_LIST, steps);
        stepsListFragment.setArguments(args);
        return stepsListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            steps = savedInstanceState.getParcelableArrayList(EXTRA_STEPS_LIST);
        } else {
            steps = getArguments().getParcelableArrayList(ARGUMENT_STEPS_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.steps_layout, container, false);

        stepsAdapter = new StepsAdapter(steps, this);

        recyclerView = view.findViewById(R.id.rvSteps);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(stepsAdapter);

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STEPS_LIST, steps);
    }

    @Override
    public void onStepItemClicked(int position) {
        Intent intent = new Intent(getContext(), StepDetailsActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_STEPS_LIST, steps);
        intent.putExtra(Constants.EXTRA_POSITION, position);
        startActivity(intent);
    }

}
