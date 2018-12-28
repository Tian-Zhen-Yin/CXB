package com.example.administrator.bottomguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.bottomguide.Adapter.RankAdapter;
import com.example.administrator.bottomguide.Model.Rank;

import java.util.ArrayList;
import java.util.List;

public class RankActivity extends AppCompatActivity {
   /* public ArrayList<Integer> data_source=new ArrayList<>( );
    private List<Health> healthList=new ArrayList<>();*/
   public List<Rank> rankList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        initRank();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
       RankAdapter adapter=new RankAdapter(rankList);
        recyclerView.setAdapter(adapter);

    }

    private void initRank() {
        for(int i=1;i<=3;i++) {
            rankList.add(new Rank("2",R.drawable.touxiang01,"Lucky","1.5"));
            rankList.add(new Rank("3",R.drawable.touxiang02,"Lucky","1.2"));
            rankList.add(new Rank("4",R.drawable.touxiang03,"Lucky","1.0"));
            rankList.add(new Rank("5",R.drawable.touxiang04,"Lucky","0.9"));
            rankList.add(new Rank("6",R.drawable.touxiang05,"Lucky","0.1"));
        }
    }
}
