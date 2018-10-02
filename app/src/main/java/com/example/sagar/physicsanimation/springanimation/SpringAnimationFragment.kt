package com.example.sagar.physicsanimation.springanimation


import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

import com.example.sagar.physicsanimation.R
import com.example.sagar.physicsanimation.utils.springAnimationOf
import kotlinx.android.synthetic.main.fragment_spring_animation.*


/**
 * A simple [Fragment] subclass.
 */
class SpringAnimationFragment : Fragment() {

    private val springForce: SpringForce by lazy(LazyThreadSafetyMode.NONE) {
        SpringForce(0f).apply {
            stiffness = SpringForce.STIFFNESS_MEDIUM
            dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        }
    }

    private val springAnimationTranslationX: SpringAnimation by lazy(LazyThreadSafetyMode.NONE) {
        android_bot.springAnimationOf(DynamicAnimation.TRANSLATION_X).setSpring(springForce)
    }

    private val springAnimationTranslationY: SpringAnimation by lazy(LazyThreadSafetyMode.NONE) {
        android_bot.springAnimationOf(DynamicAnimation.TRANSLATION_Y).setSpring(springForce)
    }

    var xDiffInTouchPointAndViewTopLeftCorner: Float = -1f
    var yDiffInTouchPointAndViewTopLeftCorner: Float = -1f


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spring_animation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupTouchListener()
    }

    private fun setupSpringForce(stiffness: Float = SpringForce.STIFFNESS_MEDIUM,
                                 dampingRatio: Float = SpringForce.DAMPING_RATIO_HIGH_BOUNCY) {
        springForce.stiffness = stiffness
        springForce.dampingRatio = dampingRatio
    }

    private fun setupTouchListener() {

        android_bot.setOnTouchListener { view, motionEvent ->

            when(motionEvent?.action) {

                MotionEvent.ACTION_DOWN -> {
                    xDiffInTouchPointAndViewTopLeftCorner = motionEvent.rawX - view.x
                    yDiffInTouchPointAndViewTopLeftCorner = motionEvent.rawY - view.y

                    springAnimationTranslationX.cancel()
                    springAnimationTranslationY.cancel()
                }

                MotionEvent.ACTION_MOVE -> {
                    android_bot.x = motionEvent.rawX - xDiffInTouchPointAndViewTopLeftCorner
                    android_bot.y = motionEvent.rawY - yDiffInTouchPointAndViewTopLeftCorner
                }

                MotionEvent.ACTION_UP -> {
                    springAnimationTranslationX.start()
                    springAnimationTranslationY.start()
                }
            }

            true
        }
    }
}
