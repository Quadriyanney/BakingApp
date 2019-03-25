package com.quadriyanney.bakingapp.ui.stepsList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.data.model.Step;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private ArrayList<Step> steps;
    private StepItemClickListener listener;

    StepsAdapter(ArrayList<Step> steps, StepItemClickListener listener) {
        this.steps = steps;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                      int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_step, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.ViewHolder holder, int position) {
        holder.bind(steps.get(position));
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvStepName, tvStepDetails;

        ViewHolder(View itemView) {
            super(itemView);
            tvStepName = itemView.findViewById(R.id.tvStepDescription);
            tvStepDetails = itemView.findViewById(R.id.tvClickToViewDetails);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onStepItemClicked(getAdapterPosition());
        }

        void bind(Step step) {
            tvStepName.setText(step.getShortDescription());

            String details;
            String detailsPrefix = "Click to view details";
            String videoURL = step.getVideoURL();
            String thumbnailURL = step.getThumbnailURL();

            if (TextUtils.isEmpty(videoURL) && TextUtils.isEmpty(thumbnailURL)) {
                tvStepDetails.setText(detailsPrefix);
            } else {
                if (!TextUtils.isEmpty(videoURL) && TextUtils.isEmpty(thumbnailURL)) {
                    details = detailsPrefix + " and videos";
                } else if (TextUtils.isEmpty(videoURL) && !TextUtils.isEmpty(thumbnailURL)) {
                    details = detailsPrefix + " and images";
                } else {
                    details = detailsPrefix + ", images and videos";
                }
                tvStepDetails.setText(details);
            }
        }
    }

    public interface StepItemClickListener {
        void onStepItemClicked(int position);
    }

}
