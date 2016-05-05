package at.acid.conquer.animation;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import at.acid.conquer.R;

/**
 * Created by Trey
 * Created on 29.04.2016
 */
public class TrackingInfoAnimator{
    private static final LinearLayout.LayoutParams mShrinkInvisible = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 5f);
    private static final LinearLayout.LayoutParams mExtendInvisible = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 8f);

    private final Context mAppContext;
    private final FloatingActionButton mFAB;
    private final LinearLayout mLayoutShort;
    private final LinearLayout mLayoutLong;
    private final TextView mTVInvisible;


    public TrackingInfoAnimator(Context context, FloatingActionButton fab, LinearLayout layoutShort, LinearLayout layoutLong, TextView invisibleTV){
        this.mAppContext = context;
        this.mFAB = fab;
        this.mLayoutShort = layoutShort;
        this.mLayoutLong = layoutLong;
        this.mTVInvisible = invisibleTV;
    }


//    public void extendShortInfo(){
//
//        mFAB.hide();
//        mTVInvisible.setLayoutParams(mShrinkInvisible);
//
//        Animation bottomUp = AnimationUtils.loadAnimation(mAppContext, R.anim.bottom_up);
//        LinearLayout.LayoutParams mShrinkInvisible = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 5f);
//        mLayoutShort.setLayoutParams(mShrinkInvisible);
//        mLayoutInfo.setVisibility(View.VISIBLE);
//
//        bottomUp.setAnimationListener(new Animation.AnimationListener(){
//            @Override
//            public void onAnimationStart(Animation animation){}
//
//            @Override
//            public void onAnimationEnd(Animation animation){
//                 ObjectAnimator rotate = ObjectAnimator.ofFloat(mFAB, "rotation", -180f);
//                rotate.start();
//
//                mFAB.show();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation){}
//        });
//
//        mLayoutShort.startAnimation(bottomUp);
//    }
//
//    public void shrinkShortInfo(){
//
//        mFAB.hide();
//        mTVInvisible.setLayoutParams(mExtendInvisible);
//
//        Animation bottomUp = AnimationUtils.loadAnimation(mAppContext, R.anim.top_down);
//
//
//
//        bottomUp.setAnimationListener(new Animation.AnimationListener(){
//            @Override
//            public void onAnimationStart(Animation animation){}
//
//            @Override
//            public void onAnimationEnd(Animation animation){
//                ObjectAnimator rotate = ObjectAnimator.ofFloat(mFAB, "rotation", 0f);
//                rotate.start();
//
//                LinearLayout.LayoutParams mShrinkInvisible = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 2f);
//                mLayoutShort.setLayoutParams(mShrinkInvisible);
//
//                mFAB.show();
//                mLayoutInfo.setVisibility(View.GONE);
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation){}
//        });
//
//        mLayoutShort.startAnimation(bottomUp);
//    }


//    public void extendTrackingInfo(){
//        mFAB.hide();
//        mTVInvisible.setLayoutParams(mShrinkInvisible);
//
//        Animation bottomUp = AnimationUtils.loadAnimation(mAppContext, R.anim.bottom_up);
//        bottomUp.setAnimationListener(new Animation.AnimationListener(){
//            @Override
//            public void onAnimationStart(Animation animation){}
//
//            @Override
//            public void onAnimationEnd(Animation animation){
//                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mFAB.getLayoutParams();
//                params.setAnchorId(R.id.ll_trackinginfo_long);
//
//                ObjectAnimator rotate = ObjectAnimator.ofFloat(mFAB, "rotation", -180f);
//                rotate.start();
//
//                mFAB.show();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation){}
//        });
//
//        mLayoutLong.startAnimation(bottomUp);
//        mLayoutShort.setVisibility(View.GONE);
//        mLayoutLong.setVisibility(View.VISIBLE);
//    }
//
//
//    public void shrinkTrackingInfo(){
//        mFAB.hide();
//        mTVInvisible.setLayoutParams(mExtendInvisible);
//
//        Animation topDown = AnimationUtils.loadAnimation(mAppContext, R.anim.top_down);
//        topDown.setAnimationListener(new Animation.AnimationListener(){
//            @Override
//            public void onAnimationStart(Animation animation){}
//
//            @Override
//            public void onAnimationEnd(Animation animation){
//                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mFAB.getLayoutParams();
//                params.setAnchorId(R.id.ll_trackinginfo_short);
//
//                ObjectAnimator rotate = ObjectAnimator.ofFloat(mFAB, "rotation", 0f);
//                rotate.start();
//
//                mFAB.show();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation){}
//        });
//
//        mLayoutLong.startAnimation(topDown);
//        mLayoutLong.setVisibility(View.GONE);
//        mLayoutShort.setVisibility(View.VISIBLE);
//    }

}
