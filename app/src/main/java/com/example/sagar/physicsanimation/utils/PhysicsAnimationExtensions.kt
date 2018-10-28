package com.example.sagar.physicsanimation.utils

import android.support.animation.FlingAnimation
import android.support.animation.FloatPropertyCompat
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.view.View

fun <K : View> K.flingAnimationOf(property: FloatPropertyCompat<K>): FlingAnimation {
    return FlingAnimation(this, property)
}

fun <K : View> K.springAnimationOf(
        property: FloatPropertyCompat<K>,
        finalPosition: Float = Float.NaN
): SpringAnimation {
    return if (finalPosition.isNaN()) {
        SpringAnimation(this, property)
    } else {
        SpringAnimation(this, property, finalPosition)
    }
}

inline fun SpringAnimation.withSpringForceProperties(func: SpringForce.() -> Unit): SpringAnimation {
    if (spring == null) {
        spring = SpringForce()
    }
    spring.func()
    return this
}