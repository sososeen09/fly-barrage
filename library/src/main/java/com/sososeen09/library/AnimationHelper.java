package com.sososeen09.library;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class AnimationHelper {
    public static Animation createTranslateAnim(Context context, int fromX, int toX) {
        TranslateAnimation tlAnim = new TranslateAnimation(fromX, toX, 0, 0);
        long duration = (long) (Math.abs(toX - fromX) * 1.0f / Utils.getScreenWidth(context) * 3000);
        tlAnim.setDuration(duration);
        tlAnim.setInterpolator(new DecelerateAccelerateInterpolator());
        tlAnim.setFillAfter(true);
        return tlAnim;
    }

    public static Animator createTranslateXAnimator(Context context, View target, int fromX, int toX) {
        ObjectAnimator ranslationX = ObjectAnimator.ofFloat(target, "translationX", fromX, toX);
        long duration = (long) (Math.abs(toX - fromX) * 1.0f / Utils.getScreenWidth(context) * 3000);
        ranslationX.setDuration(duration);
        ranslationX.setInterpolator(new DecelerateAccelerateInterpolator());

        return ranslationX;
    }

}