package at.acid.conquer;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;

/**
 * Created by Trey
 * Created on 26.04.2016
 */
public class TranslationAnimator{

    public static void translateAndScaleToBottomRight(View view){
        float scaleFactor = 0.65f;

        View parent = (View) view.getParent();
        int parentWidth = parent.getMeasuredWidth();
        int parentHeight = parent.getMeasuredHeight();

        int[] currentLocationBottomRight = new int[2];
        currentLocationBottomRight[0] = view.getRight();
        currentLocationBottomRight[1] = view.getBottom();

        float deltaX = parentWidth - currentLocationBottomRight[0] - 10f + view.getMeasuredWidth() / 4 * scaleFactor;
        float deltaY = parentHeight - currentLocationBottomRight[1] - 10f + view.getMeasuredHeight() / 4 * scaleFactor;

        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "translationX", 0f, deltaX);
        ObjectAnimator animY = ObjectAnimator.ofFloat(view, "translationY", 0f, deltaY);
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", scaleFactor);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", scaleFactor);

        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.setDuration(1500);
        animSetXY.playTogether(animX, animY, scaleDownX, scaleDownY);
        animSetXY.start();

        view.setBackgroundResource(R.drawable.animate_button_to_stop);
        AnimationDrawable playStopAnimation = (AnimationDrawable) view.getBackground();
        playStopAnimation.start();
    }

    public static void translateAndScaleCenter(View view){
        float scaleFactor = 1.0f;

        int[] currentLocationTopLeft = new int[2];
        currentLocationTopLeft[0] = view.getLeft();
        currentLocationTopLeft[1] = view.getTop();

        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "translationX", currentLocationTopLeft[0], 0);
        ObjectAnimator animY = ObjectAnimator.ofFloat(view, "translationY", currentLocationTopLeft[1], 0);
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", scaleFactor);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", scaleFactor);

        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animX, animY, scaleDownX, scaleDownY);
        animSetXY.start();

        view.setBackgroundResource(R.drawable.animate_button_to_play);
        AnimationDrawable playStopAnimation = (AnimationDrawable) view.getBackground();
        playStopAnimation.start();
    }
}
