package com.example.sagar.physicsanimation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        setupNavigationDrawer(toolbar)
    }

    private fun setupToolbar() {
        toolbar.title = ""
        setSupportActionBar(toolbar)

    }

    private fun setupNavigationDrawer(toolbar: Toolbar) {
        val actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.open_drawer, R.string.close_drawer){}
        drawer_layout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        val navController = Navigation.findNavController(this, R.id.physics_animation_nav_fragment)
        navigation_view.setupWithNavController(navController)
    }
}
