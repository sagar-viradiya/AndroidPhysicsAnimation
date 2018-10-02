package com.example.sagar.physicsanimation.flinganimation


import android.graphics.Point
import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.FlingAnimation
import android.support.v4.app.Fragment
import android.view.*

import com.example.sagar.physicsanimation.R
import com.example.sagar.physicsanimation.utils.afterMeasured
import com.example.sagar.physicsanimation.utils.flingAnimationOf
import kotlinx.android.synthetic.main.fragment_fling_animation.*
import kotlin.properties.Delegates


/**
 * A simple [Fragment] subclass.
 */
class FlingAnimationFragment : Fragment() {

    private val screenSize by lazy(LazyThreadSafetyMode.NONE) {
        val size = Point()
        requireActivity().windowManager.defaultDisplay.getSize(size)
        size
    }

    val flingAnimationX: FlingAnimation by lazy(LazyThreadSafetyMode.NONE) {
        android_bot.flingAnimationOf(DynamicAnimation.X)
    }

    val flingAnimationY: FlingAnimation by lazy(LazyThreadSafetyMode.NONE) {
        android_bot.flingAnimationOf(DynamicAnimation.Y)
    }

    var friction: Float by Delegates.observable(1f) {
        _, _, new ->
        flingAnimationX.friction = new
        flingAnimationY.friction = new
    }

    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fling_animation, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        flingAnimationX.addEndListener { animation, canceled, value, velocity ->
            if(flingAnimationY.isRunning) flingAnimationY.cancel()
        }

        flingAnimationY.addEndListener { animation, canceled, value, velocity ->
            if(flingAnimationX.isRunning) flingAnimationX.cancel()
        }

        android_bot.afterMeasured {
            flingAnimationX.setMinValue(0f)
                    .setMaxValue((screenSize.x - width).toFloat())
                    .setFriction(2.2f)
            flingAnimationY.setMinValue(0f)
                    .setMaxValue((screenSize.y - height).toFloat())
                    .setFriction(2.2f)
        }


        val gestureDetector = GestureDetector(context, gestureListener)

        android_bot.setOnTouchListener { _, motionEvent ->
            gestureDetector.onTouchEvent(motionEvent)
        }

    }


}
