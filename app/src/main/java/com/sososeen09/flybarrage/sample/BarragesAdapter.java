package com.sososeen09.flybarrage.sample;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sososeen09.library.BarrageView;

import java.util.List;

/**
 * Created by yunlong.su on 2018/4/27.
 */

public class BarragesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private final RecyclerViewActivity recyclerViewActivity;

    public BarragesAdapter(RecyclerViewActivity recyclerViewActivity, @Nullable List<String> data) {
        super(R.layout.item_home_lesson_living, data);
        this.recyclerViewActivity = recyclerViewActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        BarrageView barrageView = helper.getView(R.id.barrageView);
        setBarrageView(barrageView);
    }

    private void setBarrageView(BarrageView barrageView) {
        barrageView.removeAllViews();
        barrageView.destroy();
        recyclerViewActivity.needRefreshDanmaku(barrageView);
    }
}
