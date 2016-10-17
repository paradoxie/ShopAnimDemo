package com.paradoxie.shopanimlibrary;/**
 * Created by xiehehe on 2016/10/17.
 */

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

/**
 * User: xiehehe
 * Date: 2016-10-17
 * Time: 22:02
 * FIXME
 */
public class AniManager {
    AnimListener mListener;
    private ViewGroup anim_mask_layout;// 动画层
    private long time = 800;

    public void setOnAnimListener(AnimListener listener) {
        mListener = listener;
    }


    private ViewGroup createAnimLayout(Activity mainActivity) {
        ViewGroup rootView = (ViewGroup) mainActivity.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(mainActivity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        int id = Integer.MAX_VALUE;
        animLayout.setId(id);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final ViewGroup vg, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    public void setAnim(Activity mainActivity, final View v, int[] start_location, int[] end_location) {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout(mainActivity);
        anim_mask_layout.addView(v);// 把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, v, start_location);

        //终点位置
        int endX = end_location[0] - start_location[0] + 20;
        int endY = end_location[1] - start_location[1];// 动画位移的y坐标
        TranslateAnimation translateAnimationX = new TranslateAnimation(0, endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);
        translateAnimationX.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(time);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
                mListener.setAnimBegin(AniManager.this);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画的结束调用的方法
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
                mListener.setAnimEnd(AniManager.this);
            }
        });
    }

    //自定义时间接口
    public long setTime(long l) {
        time = l;
        return time;
    }

    //回调监听
   public interface AnimListener {

        void setAnimBegin(AniManager a);

        void setAnimEnd(AniManager a);

    }
}
