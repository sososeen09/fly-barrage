package com.sososeen09.flybarrage.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sososeen09.library.Barrage;
import com.sososeen09.library.BarrageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecyclerViewActivity extends AppCompatActivity {

    private BarrageView barrageView;
    private List<Barrage> mBarrages = new ArrayList<>();
    private Random random = new Random();
    //用户头像颜色
    public static final int[] rainbow = new int[]{
            R.color.colorAccent,
            R.color.colorPrimary,
            R.color.colorPrimaryDark
    };

    String[] defaultMessage = {
            "defaultMessage1",
            "defaultMessage2",
            "defaultMessage3",
            "defaultMessage4",
            "defaultMessage5",
            "defaultMessage6",
            "defaultMessage7",
            "defaultMessage8",
            "defaultMessage9",
            "defaultMessage10"
    };


    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        rv = findViewById(R.id.rv);

        rv.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("aa" + i);
        }
        final BarragesAdapter barragesAdapter = new BarragesAdapter(this, data);

        rv.setAdapter(barragesAdapter);


        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barragesAdapter.notifyDataSetChanged();
            }
        });
    }

    public void needRefreshDanmaku(BarrageView barrageView) {
        final List<Barrage> barrages = new ArrayList<>();
        for (String s : defaultMessage) {
            barrages.add(new Barrage(s, R.color.white, rainbow[random.nextInt(rainbow.length)]));
        }

        barrageView.setBarrages(barrages);
    }
}
