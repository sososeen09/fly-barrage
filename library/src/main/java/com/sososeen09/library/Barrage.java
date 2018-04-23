package com.sososeen09.library;

import android.graphics.Color;
import android.support.annotation.ColorRes;

public class Barrage {

    private String content;
    private int textColorRes;
    private boolean showBorder;
    private int backGroundColorRes;

    public Barrage(String content) {
        this(content, R.color.black, false, Color.WHITE);
    }

    public Barrage(String content, int textColor) {
        this(content, textColor, false, Color.WHITE);
    }

    public Barrage(String content, boolean showBorder) {
        this(content, R.color.black, showBorder, Color.WHITE);
    }

    public Barrage(String content, int textColor, int backGroundColorRes) {
        this(content, textColor, false, backGroundColorRes);
    }

    private Barrage(String content, int textColor, boolean showBorder, int backGroundColorRes) {
        this.content = content;
        this.textColorRes = textColor;
        this.showBorder = showBorder;
        this.backGroundColorRes = backGroundColorRes;
    }

    public boolean isShowBorder() {
        return showBorder;
    }

    public void setShowBorder(boolean showBorder) {
        this.showBorder = showBorder;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTextColor() {
        return textColorRes;
    }

    public void setTextColor(@ColorRes int textColor) {
        this.textColorRes = textColor;
    }

    public int getBackGroundColorRes() {
        return backGroundColorRes;
    }

    public void setBackGroundColorRes(@ColorRes int backGroundColorRes) {
        this.backGroundColorRes = backGroundColorRes;
    }
}
