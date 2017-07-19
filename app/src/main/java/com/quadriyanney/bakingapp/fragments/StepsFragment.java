package com.quadriyanney.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.adapters.StepsAdapter;
import com.quadriyanney.bakingapp.data.StepsInfo;

import java.util.ArrayList;

/**
 * Created by quadriy on 6/21/17.
 */

public class StepsFragment extends Fragment implements StepsAdapter.StepItemClickListener {

    ArrayList<StepsInfo> mStepsList = new ArrayList<>();
    StepsAdapter stepsAdapter;
    RecyclerView recyclerView;
    ClickListener listener;

    public StepsFragment () {}

    public interface ClickListener {
        void onStepClicked(int position);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            mStepsList = savedInstanceState.getParcelableArrayList("list");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.steps_layout, container, false);

        stepsAdapter = new StepsAdapter(mStepsList, this);
        recyclerView = (RecyclerView) view.findViewById(R.id.stepsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(stepsAdapter);

        return view;
    }

    public void getStepsList(ArrayList<StepsInfo> stepsList){
        mStepsList = stepsList;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list", mStepsList);
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
