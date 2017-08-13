package com.example.sagar.physicsanimation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.example.sagar.physicsanimation.chainanimation.ChainAnimationFragment
import com.example.sagar.physicsanimation.flinganimation.FlingAnimationFragment
import com.example.sagar.physicsanimation.springanimation.SpringAnimationFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        var SELECTED_FRAGMENT_INDEX = 0
        const val KEY_SELECTED_INDEX = "selected_index"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation_view.setNavigationItemSelectedListener(this)
        setupToolbar()
        setupNavigationDrawer(toolbar)

        savedInstanceState?.apply {
            navigation_view.menu.getItem(this.getInt(KEY_SELECTED_INDEX)).isChecked = true
            return
        }

        supportFragmentManager.beginTransaction().add(R.id.fragment_container,
                FlingAnimationFragment.newInstance()).commit()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putInt(KEY_SELECTED_INDEX, SELECTED_FRAGMENT_INDEX)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {

            R.id.nav_item_fling -> {

                if(!item.isChecked) {
                    item.isChecked = true
                    SELECTED_FRAGMENT_INDEX = 0
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                            FlingAnimationFragment.newInstance()).commit()
                }
                drawer_layout.closeDrawer(GravityCompat.START)
                return true
            }

            R.id.nav_item_spring -> {

                if(!item.isChecked) {
                    item.isChecked = true
                    SELECTED_FRAGMENT_INDEX = 1
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                            SpringAnimationFragment.newInstance()).commit()
                }
                drawer_layout.closeDrawer(GravityCompat.START)
                return true

            }

            R.id.nav_item_chain -> {
                if(!item.isChecked) {
                    item.isChecked = true
                    SELECTED_FRAGMENT_INDEX = 2
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                            ChainAnimationFragment.newInstance()).commit()
                }
                drawer_layout.closeDrawer(GravityCompat.START)
                return true

            }

            else -> return true

        }
    }

    private fun setupToolbar() {

        toolbar.title = ""
        setSupportActionBar(toolbar)

    }

    fun setupNavigationDrawer(toolbar: Toolbar) {
        val actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.open_drawer, R.string.close_drawer) {

            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
            }

            override fun onDrawerClosed(drawerView: View?) {
                super.onDrawerClosed(drawerView)
            }
        }

        drawer_layout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }
}
