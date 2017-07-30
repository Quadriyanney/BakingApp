package com.quadriyanney.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.data.StepsInfo;

import java.util.List;

/**
 * Created by quadriy on 6/25/17.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder>{

    private List<StepsInfo> stepsList;
    private StepItemClickListener listener;

    public interface StepItemClickListener{
        void onStepItemClicked(int clicked);
    }

    public StepsAdapter(List<StepsInfo> stepsList, StepItemClickListener listener){
        this.stepsList = stepsList;
        this.listener = listener;
    }

    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsAdapter.ViewHolder holder, int position) {
        holder.stepName.setText(stepsList.get(position).getShortDescription());
        String video = stepsList.get(position).getVideoUrl();
        String image = stepsList.get(position).getThumbnailUrl();

        String s;
        String fixed = "Click to view details";

        if (TextUtils.isEmpty(video) && TextUtils.isEmpty(image)){
            holder.stepDetails.setText(fixed);
        } else if (!TextUtils.isEmpty(video) && TextUtils.isEmpty(image)){
            s = fixed + " and videos";
            holder.stepDetails.setText(s);
        } else if (TextUtils.isEmpty(video) && !TextUtils.isEmpty(image)){
            s = fixed + " and images";
            holder.stepDetails.setText(s);
        } else {
            s = fixed + ", images and videos";
            holder.stepDetails.setText(s);
        }
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView stepName, stepDetails;

        ViewHolder(View itemView) {
            super(itemView);
            stepName = (TextView) itemView.findViewById(R.id.step_name);
            stepDetails = (TextView) itemView.findViewById(R.id.details);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clicked = getAdapterPosition();
            listener.onStepItemClicked(clicked);
        }
    }
}
