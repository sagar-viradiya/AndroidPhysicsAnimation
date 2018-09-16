@file:JvmName("Utils")

package com.example.sagar.physicsanimation

import android.support.animation.FloatPropertyCompat
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.view.View
import android.view.ViewTreeObserver

/**
 * Created by sagar on 07/08/17.
 */

inline fun <T: View> T.afterMeasured(crossinline func: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            func()
            viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    })
}

fun <K> K.springAnimationOf(property: FloatPropertyCompat<K>): SpringAnimation {
    return SpringAnimation(this, property)
}

fun <K> K.springAnimationWithSpringForceOf(property: FloatPropertyCompat<K>, finalPosition: Float): SpringAnimation {
    return SpringAnimation(this, property, finalPosition)
}

fun SpringAnimation.withSpringForceProperties(finalPosition: Float, stiffness: Float = SpringForce.STIFFNESS_MEDIUM,
                                    dampingRatio: Float = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY): SpringAnimation {
    return if (spring == null) {
        setSpring(SpringForce(finalPosition).apply {
            this.stiffness = stiffness
            this.dampingRatio= dampingRatio
        })
    } else {
        spring.finalPosition = finalPosition
        spring.stiffness = stiffness
        spring.dampingRatio = dampingRatio
        this
    }

}

fun SpringAnimation.withSpringForceProperties(stiffness: Float = SpringForce.STIFFNESS_MEDIUM,
                                              dampingRatio: Float = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY): SpringAnimation {
    return if (spring == null) {
        setSpring(SpringForce().apply {
            this.stiffness = stiffness
            this.dampingRatio= dampingRatio
        })
    } else {
        spring.stiffness = stiffness
        spring.dampingRatio = dampingRatio
        this
    }

}

fun SpringAnimation.updateSpringForceProperties(stiffness: Float = SpringForce.STIFFNESS_MEDIUM,
                                    dampingRatio: Float = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY): SpringAnimation {
    if (spring == null) {
        throw UnsupportedOperationException("Incomplete SpringAnimation: Spring force needs to be set.")
    }

    spring.stiffness = stiffness
    spring.dampingRatio = dampingRatio
    return this
}

