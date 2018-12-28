package com.example.administrator.bottomguide.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.administrator.bottomguide.Model.Rank;
import com.example.administrator.bottomguide.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    private List<Rank> mrank;

    public RankAdapter(List<Rank> rankList) {
        mrank=rankList;
    }


    @NonNull
    @Override
    public RankAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rank_item,viewGroup,false);
        RankAdapter.ViewHolder viewHolder=new RankAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Rank rank=mrank.get(i);
        viewHolder.nub.setText(rank.getNub());
        viewHolder.rankicon.setImageResource(rank.getImage_src());
        viewHolder.user_id.setText(rank.getUserId());
        viewHolder.user_time.setText(rank.getUserTime());

    }

    @Override
    public int getItemCount() {
        return mrank.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nub;
        CircleImageView rankicon;
        TextView user_id;
        TextView user_time;
        public ViewHolder(@NonNull View view) {

            super(view);
            nub=(TextView)view.findViewById(R.id.nub);
            rankicon=(CircleImageView) view.findViewById(R.id.icon_image);
            user_id=(TextView) view.findViewById(R.id.user_id);
            user_time=(TextView) view.findViewById(R.id.user_time);

        }
    }
}
