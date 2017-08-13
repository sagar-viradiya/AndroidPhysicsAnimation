package com.example.sagar.physicsanimation.flinganimation


import android.graphics.Point
import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.FlingAnimation
import android.support.v4.app.Fragment
import android.view.*

import com.example.sagar.physicsanimation.R
import kotlinx.android.synthetic.main.fragment_fling_animation.*
import kotlin.properties.Delegates


/**
 * A simple [Fragment] subclass.
 * Use the [FlingAnimationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FlingAnimationFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = FlingAnimationFragment()

    }

    val screenSize by lazy(LazyThreadSafetyMode.NONE) {
        val size = Point()
        activity.windowManager.defaultDisplay.getSize(size)
        size
    }

    val flingAnimationX: FlingAnimation by lazy(LazyThreadSafetyMode.NONE) {
        FlingAnimation(android_bot, DynamicAnimation.X)
                .setMinValue(0f)
    }

    val flingAnimationY: FlingAnimation by lazy(LazyThreadSafetyMode.NONE) {
        FlingAnimation(android_bot, DynamicAnimation.Y)
                .setMinValue(0f)
    }

    var friction: Float by Delegates.observable(1f) {
        _, _, new ->
        flingAnimationX.friction = new
        flingAnimationY.friction = new
    }

    val gestureListener = object : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {

            flingAnimationX.setStartVelocity(velocityX)
            flingAnimationY.setStartVelocity(velocityY)

            flingAnimationX.start()
            flingAnimationY.start()

            return true
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_fling_animation, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        flingAnimationX.addEndListener { animation, canceled, value, velocity ->
            if(flingAnimationY.isRunning) flingAnimationY.cancel()
        }

        flingAnimationY.addEndListener { animation, canceled, value, velocity ->
            if(flingAnimationX.isRunning) flingAnimationX.cancel()
        }

        android_bot.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                flingAnimationX.setMaxValue((screenSize.x - android_bot.width).toFloat())
                flingAnimationY.setMaxValue((screenSize.y - android_bot.height).toFloat())
                android_bot.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })


        val gestureDetector = GestureDetector(context, gestureListener)

        android_bot.setOnTouchListener { _, motionEvent ->
            gestureDetector.onTouchEvent(motionEvent)
        }

    }


}
