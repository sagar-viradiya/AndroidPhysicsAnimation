package com.example.sagar.physicsanimation.chainanimation


import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.FloatPropertyCompat
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

import com.example.sagar.physicsanimation.R
import kotlinx.android.synthetic.main.fragment_chain_animation.*


/**
 * A simple [Fragment] subclass.
 * Use the [ChainAnimationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChainAnimationFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() = ChainAnimationFragment()

    }

    val firstSpringAnimationX by lazy(LazyThreadSafetyMode.NONE) {
        createSpringAnimation(android_bot1, DynamicAnimation.TRANSLATION_X)
    }

    val firstSpringAnimationY by lazy(LazyThreadSafetyMode.NONE) {
        createSpringAnimation(android_bot1, DynamicAnimation.TRANSLATION_Y)
    }

    val secondSpringAnimationX by lazy(LazyThreadSafetyMode.NONE) {
        createSpringAnimation(android_bot2, DynamicAnimation.TRANSLATION_X)
    }

    val secondSpringAnimationY by lazy(LazyThreadSafetyMode.NONE) {
        createSpringAnimation(android_bot2, DynamicAnimation.TRANSLATION_Y)
    }

    var lastX: Float = 0f
    var lastY: Float = 0f

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_chain_animation, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnTouchListener()

    }

    private fun setupOnTouchListener() {

        firstSpringAnimationX.addUpdateListener { _, value, _ ->
            secondSpringAnimationX.animateToFinalPosition(value)
        }

        firstSpringAnimationY.addUpdateListener { _, value, _ ->
            secondSpringAnimationY.animateToFinalPosition(value)
        }

        android_bot.setOnTouchListener { view, motionEvent ->

            if(motionEvent.action == MotionEvent.ACTION_MOVE) {

                val deltaX = motionEvent.rawX - lastX
                val deltaY = motionEvent.rawY - lastY

                view.translationX = deltaX + view.translationX
                view.translationY = deltaY + view.translationY

                firstSpringAnimationX.animateToFinalPosition(view.translationX)
                firstSpringAnimationY.animateToFinalPosition(view.translationY)

            }

            lastX = motionEvent.rawX
            lastY = motionEvent.rawY

            return@setOnTouchListener true
        }
    }

    private fun <K> createSpringAnimation(view: K, property: FloatPropertyCompat<K>) : SpringAnimation {
        return SpringAnimation(view, property).setSpring(SpringForce())
    }


}
