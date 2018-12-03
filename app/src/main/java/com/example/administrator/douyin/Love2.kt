package com.example.administrator.douyin

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import java.util.*


/**
 * information：仿抖音点赞功能
 */

class Love2(context: Context) : RelativeLayout(context) {
    var mContext: Context? = null
    //动画中随机❤的旋转角度
    var num = floatArrayOf(-35f, -25f, 0f, 25f, 35f)

    //用来判断是否是连续的点击事件
    private val mHits = LongArray(3)

    constructor(context: Context, attrs: AttributeSet) : this(context) {
        mContext = context
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        System.arraycopy(mHits, 1, mHits, 0, mHits.size - 1)
        mHits[mHits.size - 1] = SystemClock.uptimeMillis()

        //用这个来判断是否是3击事件，判断数组中pos=2的点击事件的时间与数组中pos=0的点击事件的时间差值是否小于500，若是小于500认为是3击事件，这时需要绘制爱心图片
        if (mHits[0] >= (SystemClock.uptimeMillis() - 500)) {
            //点击是触发心形的图片add到整个view中，然后执行动画

            //有连续触摸的时候，创建一个展示心形的图片
            var iv: ImageView = ImageView(mContext)

            //设置展示的位置，需要在手指触摸的位置上方，即触摸点是心形的右下角的位置
            var lp: LayoutParams = LayoutParams(300, 300)
            lp.leftMargin = (event?.x!! - 150F).toInt()
            lp.topMargin = (event?.y!! - 300F).toInt()
            //设置图片资源
            iv.setImageDrawable(resources.getDrawable(R.mipmap.icon_home_like_after))
            iv.layoutParams = lp

            //把IV添加到父布局当中
            addView(iv)

            //设置控件的动画
            var animatorSet: AnimatorSet = AnimatorSet()
            animatorSet.play(
                    //缩放动画，X轴2倍缩小至0.9倍
                    scaleAni(iv, "scaleX", 2f, 0.9f, 100, 0))
                    //缩放动画，Y轴2倍缩放至0.9倍
                    .with(scaleAni(iv, "scaleY", 2f, 0.9f, 100, 0))
                    //旋转动画，随机旋转角
                    .with(rotation(iv, 0, 0, num[Random().nextInt(4)]))
                    //渐变透明动画，透明度从0-1
                    .with(alphaAni(iv, 0F, 1F, 100, 0))
                    //缩放动画，X轴0.9倍缩小至
                    .with(scaleAni(iv, "scaleX", 0.9f, 1F, 50, 150))
                    //缩放动画，Y轴0.9倍缩放至
                    .with(scaleAni(iv, "scaleY", 0.9f, 1F, 50, 150))
                    //位移动画，Y轴从0上移至600
                    .with(translationY(iv, 0F, -600F, 800, 400))
                    //透明动画，从1-0
                    .with(alphaAni(iv, 1F, 0F, 300, 400))
                    //缩放动画，X轴1至3倍
                    .with(scaleAni(iv, "scaleX", 1F, 3f, 700, 400))
                    //缩放动画，Y轴1至3倍
                    .with(scaleAni(iv, "scaleY", 1F, 3f, 700, 400))


            //开始动画
            animatorSet.start()
            //设置动画结束监听
            animatorSet.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)

                    //当动画结束以后，需要把控件从父布局移除
                    removeViewInLayout(iv)
                }
            })

        }

        return super.onTouchEvent(event)

    }


    fun scaleAni(view: View, propertyName: String, from: Float, to: Float, time: Long, delayTime: Long): ObjectAnimator {
        val ani: ObjectAnimator = ObjectAnimator.ofFloat(view, propertyName, from, to)
        ani.interpolator = LinearInterpolator()
        ani.startDelay = delayTime
        ani.duration = time
        return ani
    }

    fun translationX(view: View, from: Float, to: Float, time: Long, delayTime: Long): ObjectAnimator {
        val ani: ObjectAnimator = ObjectAnimator.ofFloat(view, "translationX", from, to)
        ani.interpolator = LinearInterpolator()
        ani.startDelay = delayTime
        ani.duration = time
        return ani
    }

    fun translationY(view: View, from: Float, to: Float, time: Long, delayTime: Long): ObjectAnimator {
        val ani: ObjectAnimator = ObjectAnimator.ofFloat(view, "translationY", from, to)
        ani.interpolator = LinearInterpolator()
        ani.startDelay = delayTime
        ani.duration = time
        return ani
    }

    fun alphaAni(view: View, from: Float, to: Float, time: Long, delayTime: Long): ObjectAnimator {
        val ani = ObjectAnimator.ofFloat(view, "alpha", from, to)
        ani.interpolator = LinearInterpolator()
        ani.startDelay = delayTime
        ani.duration = time
        return ani
    }

    fun rotation(view: View, time: Long, delayTime: Long, vararg values: Float): ObjectAnimator {
        val ani = ObjectAnimator.ofFloat(view, "rotation", *values)
        ani.duration = time
        ani.startDelay = delayTime
        ani.interpolator = TimeInterpolator { input -> input }
        return ani
    }

}