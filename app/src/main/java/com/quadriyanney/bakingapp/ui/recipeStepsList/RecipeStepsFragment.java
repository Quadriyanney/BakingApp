package com.quadriyanney.bakingapp.ui.recipeStepsList;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.data.model.Step;

import java.util.ArrayList;


public class RecipeStepsFragment extends Fragment implements RecipeStepsAdapter.StepItemClickListener {

    ArrayList<Step> steps;
    RecipeStepsAdapter recipeStepsAdapter;
    RecyclerView recyclerView;
    ClickListener listener;

    public RecipeStepsFragment() { }

    public interface ClickListener {
        void onStepClicked(int position);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            this.steps = savedInstanceState.getParcelableArrayList("list");
        }
    }

    public static RecipeStepsFragment newInstance(ArrayList<Step> steps) {
        RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
        recipeStepsFragment.setSteps(steps);
        return recipeStepsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.steps_layout, container, false);

        recipeStepsAdapter = new RecipeStepsAdapter(steps, this);
        recyclerView = view.findViewById(R.id.stepsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recipeStepsAdapter);

        return view;
    }

    public void setSteps(ArrayList<Step> steps){
        this.steps = steps;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list", steps);
    }

    @Override
    public void onStepItemClicked(int clicked) {
        listener.onStepClicked(clicked);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ClickListener");
        }
    }
}
