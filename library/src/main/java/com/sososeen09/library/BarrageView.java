package com.sososeen09.library;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.sososeen09.library.Utils.getScreenWidth;
import static com.sososeen09.library.Utils.tintDrawable;

/**
 * Created by shiwei on 2017/8/2.
 */

public class BarrageView extends RelativeLayout {
    public static final String TAG = "BarrageView";
    private int DEFAULT_DURATION=3000;
    private int wholeScreenDuration;
    private Set<Integer> existMarginValues = new HashSet<>();
    private int linesCount;

    private int validHeightSpace;
    private int INTERVAL = 500;

    private Random random = new Random(System.currentTimeMillis());
    private int maxBarrageSize;
    private int textLeftPadding;
    private int textRightPadding;
    private int textTopPadding;
    private int textBottomPadding;
    private int maxTextSize;
    private int minTextSize;
    private int lineHeight;
    private int borderColor;
    private boolean random_color;
    private boolean allow_repeat;
    private final int DEFAULT_PADDING = 15;
    private final int DEFAULT_BARRAGESIZE = 10;
    private final int DEFAULT_MAXTEXTSIZE = 20;
    private final int DEFAULT_MINTEXTSIZE = 14;
    private final int DEFAULT_LINEHEIGHT = 24;
    private final int DEFAULT_BORDERCOLOR = 0xff000000;
    private final boolean DEFAULT_RANDOMCOLOR = false;
    private final boolean DEFAULT_ALLOWREPEAT = false;

    private List<Barrage> barrages = new ArrayList<>();
    private List<Barrage> cache = new ArrayList<>();
    private BarrageProvider barrageProvider;
    private BlockingQueue<TextView> textViewPools = new LinkedBlockingQueue<>();
    private BlockingQueue<TextView> bodarTextViewPools = new LinkedBlockingQueue<>();


    public BarrageView(Context context) {
        this(context, null);
    }

    public BarrageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarrageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BarrageView, 0, 0);
        try {
            textLeftPadding = typedArray.getDimensionPixelSize(R.styleable.BarrageView_text_left_padding, DEFAULT_PADDING);
            textRightPadding = typedArray.getDimensionPixelSize(R.styleable.BarrageView_text_right_padding, DEFAULT_PADDING);
            textTopPadding = typedArray.getDimensionPixelSize(R.styleable.BarrageView_text_top_padding, DEFAULT_PADDING);
            textBottomPadding = typedArray.getDimensionPixelSize(R.styleable.BarrageView_text_bottom_padding, DEFAULT_PADDING);
            maxBarrageSize = typedArray.getInt(R.styleable.BarrageView_size, DEFAULT_BARRAGESIZE);
            maxTextSize = typedArray.getDimensionPixelSize(R.styleable.BarrageView_max_text_size, DEFAULT_MAXTEXTSIZE);
            minTextSize = typedArray.getDimensionPixelSize(R.styleable.BarrageView_min_text_size, DEFAULT_MINTEXTSIZE);
            lineHeight = typedArray.getDimensionPixelSize(R.styleable.BarrageView_line_height, Utils.dp2px(context, DEFAULT_LINEHEIGHT));
            borderColor = typedArray.getColor(R.styleable.BarrageView_border_color, DEFAULT_BORDERCOLOR);
            random_color = typedArray.getBoolean(R.styleable.BarrageView_random_color, DEFAULT_RANDOMCOLOR);
            allow_repeat = typedArray.getBoolean(R.styleable.BarrageView_allow_repeat, DEFAULT_ALLOWREPEAT);
            wholeScreenDuration = typedArray.getInt(R.styleable.BarrageView_duration, DEFAULT_DURATION);
            if (Utils.px2sp(context, lineHeight) < maxTextSize) {
                maxTextSize = Utils.px2sp(context, lineHeight);
            }
        } finally {
            typedArray.recycle();
        }
    }

    public void setBarrages(List<Barrage> list) {
        if (!list.isEmpty()) {
            barrages.clear();
            barrages.addAll(list);
            existMarginValues.clear();
            post(mRunnable);
        }
    }

    @NonNull
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mHandler.sendEmptyMessageDelayed(0, INTERVAL);
        }
    };

    public void addBarrage(Barrage tb) {
        barrages.add(tb);
        if (allow_repeat) {
            cache.add(tb);
        }
        showBarrage(tb);
        if (!mHandler.hasMessages(0)) {
            mHandler.sendEmptyMessageDelayed(0, INTERVAL);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (barrages.isEmpty()) {
                return;
            }
            checkBarrage();
            sendEmptyMessageDelayed(0, INTERVAL);
        }
    };

    public void checkBarrage() {
        Barrage barrage = barrages.get(random.nextInt(barrages.size()));
        if (allow_repeat) {
            if (cache.contains(barrage)) {
                return;
            }
            cache.add(barrage);
        }
        showBarrage(barrage);
    }

    private void showBarrage(final Barrage tb) {
        if (linesCount != 0 && getChildCount() >= linesCount) {
            return;
        }
        if (getChildCount() >= maxBarrageSize) {
            return;
        }
        TextView textView;

        //先从缓存中获取
        if (tb.isShowBorder()) {
            textView = bodarTextViewPools.poll();
        } else {
            textView = textViewPools.poll();
        }

        if (textView == null) {
            if (barrageProvider == null) {
                textView = tb.isShowBorder() ? new BorderTextView(getContext(), borderColor) : new TextView(getContext());
                Drawable drawable = textView.getContext().getResources().getDrawable(R.drawable.shape_bg_round);
                textView.setBackgroundDrawable(tintDrawable(drawable, ContextCompat.getColor(getContext(), tb.getBackGroundColorRes())));
                textView.setPadding(textLeftPadding, textTopPadding, textRightPadding, textBottomPadding);

                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (minTextSize + (maxTextSize - minTextSize) * Math.random()));
                textView.setTextColor(random_color ? Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)) : ContextCompat.getColor(getContext(), tb.getTextColor()));

            } else {
                textView = barrageProvider.createBarrage(tb);
            }
            Log.e(TAG, "createBarrage: ");
        }

        textView.setText(tb.getContent());

        int leftMargin = getRight() - getLeft() - getPaddingLeft();
        int verticalMargin = getRandomTopMargin();
        textView.setTag(verticalMargin);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams
                .WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.topMargin = verticalMargin;
        textView.setLayoutParams(params);
        final TextView finalTextView = textView;

        Animator translateXAnimator = AnimationHelper.createTranslateXAnimator(getContext(), textView, leftMargin, -getScreenWidth(getContext()), wholeScreenDuration);
        translateXAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i(TAG, "onAnimationStart: ");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG, "onAnimationEnd: ");
                updateCached(tb, finalTextView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.i(TAG, "onAnimationCancel: ");
                updateCached(tb, finalTextView);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        translateXAnimator.start();
        addView(textView);
    }

    private void updateCached(Barrage tb, TextView finalTextView) {
        if (allow_repeat) {
            cache.remove(tb);
        }
        removeView(finalTextView);

        //加入缓存
        if (finalTextView instanceof BorderTextView) {
            bodarTextViewPools.offer(finalTextView);
        } else {
            textViewPools.offer(finalTextView);
        }
        int verticalMargin = (int) finalTextView.getTag();
        existMarginValues.remove(verticalMargin);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

    }

    private int getRandomTopMargin() {
        if (validHeightSpace == 0) {
            validHeightSpace = getBottom() - getTop() - getPaddingTop() - getPaddingBottom();
        }
        if (linesCount == 0) {
            linesCount = validHeightSpace / lineHeight;
            if (linesCount == 0) {
                throw new RuntimeException("Not enough space to show text.");
            }
        }
        while (true) {
            int randomIndex = random.nextInt(linesCount);
            int marginValue = randomIndex * (validHeightSpace / linesCount);
            if (!existMarginValues.contains(marginValue)) {
                existMarginValues.add(marginValue);
                return marginValue;
            }
        }
    }

    public void destroy() {
        release();
    }

    public void pause() {
        mHandler.removeCallbacksAndMessages(null);
    }

    public void resume() {
        post(mRunnable);
    }

    private void release() {
        mHandler.removeCallbacksAndMessages(null);
        barrages.clear();
        cache.clear();
        existMarginValues.clear();
    }

    public void setBarrageProvider(BarrageProvider barrageProvider) {
        this.barrageProvider = barrageProvider;
    }
}
