package com.droidhelios.swipedrag.animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class SDAnimation {

    /**
     * @param view : where animation perform
     * @param speed : Best value is 100
     * @param xOffset : Best value is 5
     * @param yOffset : Best value is 5
     */
    public void makeMeShake(View view, int speed, int xOffset, int yOffset) {
        Animation anim = new TranslateAnimation(xOffset, xOffset, -yOffset, yOffset);
        anim.setDuration(speed);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        view.startAnimation(anim);
    }

//    public void makeMeShake(View view, int speed, int xOffset, int yOffset) {
//        yOffset = 5;
//        ObjectAnimator animator = ObjectAnimator.ofFloat(view,
//                View.TRANSLATION_Y,  yOffset,-yOffset);
//        animator.setDuration(100);
//        animator.setRepeatMode(ValueAnimator.REVERSE);
//        animator.setRepeatCount(Animation.INFINITE);
//        animator.setInterpolator(new CycleInterpolator(Animation.INFINITE));
//        animator.start();
//    }

}
