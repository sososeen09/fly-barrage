package com.sososeen09.flybarrage.sample;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.sososeen09.library.Barrage;
import com.sososeen09.library.BarrageProvider;
import com.sososeen09.library.BarrageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements BarrageProvider {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barrageView = (BarrageView) findViewById(R.id.barrageView);
        barrageView.setBarrages(mBarrages);
//        barrageView.setBarrageProvider(this);

        List<Barrage> barrages = new ArrayList<>();
        for (String s : defaultMessage) {
            barrages.add(new Barrage(s, R.color.white, rainbow[random.nextInt(rainbow.length)]));
        }

        barrageView.setBarrages(barrages);
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = ContextCompat.getColor(MainActivity.this, rainbow[random.nextInt(rainbow.length)]);
                barrageView.addBarrage(new Barrage("111111111111", R.color.white, rainbow[random.nextInt(rainbow.length)]));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        barrageView.destroy();
    }

    @Override
    public TextView createBarrage(Barrage tb) {

        TextView textView = new TextView(this);
        Drawable drawable = textView.getContext().getResources().getDrawable(com.sososeen09.library.R.drawable.shape_bg_round);
        int color = ContextCompat.getColor(this, rainbow[random.nextInt(rainbow.length)]);
        textView.setBackgroundDrawable(tintDrawable(drawable, color));
//        textView.setPadding(textLeftPadding, textTopPadding, textRightPadding, textBottomPadding);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        textView.setTextColor(Color.WHITE);


        return textView;
    }

    private Drawable tintDrawable(Drawable drawable, int color) {
        ColorStateList colors = ColorStateList.valueOf(color);
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

}
