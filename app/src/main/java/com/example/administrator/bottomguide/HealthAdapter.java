package com.example.administrator.bottomguide;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class HealthAdapter extends RecyclerView.Adapter<HealthAdapter.ViewHolder>{
    private List<Health> mHealth;

    public HealthAdapter(List<Health> healthList) {
        mHealth=healthList;
    }


    @NonNull
    @Override
    public HealthAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.heart_item,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HealthAdapter.ViewHolder viewHolder, int i) {
     Health health=mHealth.get(i);
     viewHolder.healthicon.setImageResource(health.getImageId());
     viewHolder.healthdata.setText(health.getHealthdata());
     viewHolder.health_info.setText(health.getHealthinfo());

    }

    @Override
    public int getItemCount() {
        return mHealth.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView healthicon;
        TextView  health_info;
        TextView  healthdata;
        public ViewHolder(@NonNull View view) {

            super(view);
            healthicon=(ImageView) view.findViewById(R.id.health_image);
            health_info=(TextView) view.findViewById(R.id.healthinfo);
            healthdata=(TextView) view.findViewById(R.id.healthdata);

        }
    }
}
