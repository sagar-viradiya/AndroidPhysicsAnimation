package com.example.sagar.physicsanimation.utils

import android.support.animation.FlingAnimation
import android.support.animation.FloatPropertyCompat
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.view.View

fun <K: View> K.flingAnimationOf(property: FloatPropertyCompat<K>): FlingAnimation {
    return FlingAnimation(this, property)
}

fun <K: View> K.springAnimationOf(property: FloatPropertyCompat<K>): SpringAnimation {
    return SpringAnimation(this, property)
}

fun <K: View> K.springAnimationOf(property: FloatPropertyCompat<K>, finalPosition: Float): SpringAnimation {
    return SpringAnimation(this, property, finalPosition)
}

inline fun <K: View> K.springAnimationOf(property: FloatPropertyCompat<K>,
                                         func: SpringForce.() -> Unit): SpringAnimation {
    val springAnimation = SpringAnimation(this, property)
    val springForce = SpringForce()
    springForce.func()
    springAnimation.spring = springForce
    return springAnimation
}

inline fun <K: View> K.springAnimationOf(property: FloatPropertyCompat<K>, finalPosition: Float,
                                         func: SpringForce.() -> Unit): SpringAnimation {

    val springAnimation = SpringAnimation(this, property, finalPosition)
    springAnimation.spring.func()
    return springAnimation
}

inline fun SpringAnimation.withSpringForceProperties(func: SpringForce.() -> Unit): SpringAnimation {
    if (spring == null) {
        spring = SpringForce()
    }
    spring.func()
    return this
}