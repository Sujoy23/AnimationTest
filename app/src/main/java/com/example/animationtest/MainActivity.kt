package com.example.animationtest

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.animationtest.application.appConstants
import com.example.animationtest.databinding.ActivityMainBinding
import com.example.animationtest.ui.home.ImageMoveInterface
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(), ImageMoveInterface {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: BottomNavigationView
    private var navHeight:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.customAppBar)

        // Set the custom Toolbar as the ActionBar
        setSupportActionBar(toolbar)

        supportActionBar?.title = appConstants.appName

        navView = binding.navView
        navHeight = navView.layoutParams.height

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun hideNavHeight() {
        slideDown(navView)
    }

    override fun resetNavHeight() {
        slideUp(navView)
    }

    private fun slideUp(view: View) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0f,  // fromXDelta
            0f,  // toXDelta
            view.height.toFloat(),  // fromYDelta
            0f
        ) // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    private fun slideDown(view: View) {
        val animate = TranslateAnimation(
            0f,  // fromXDelta
            0f,  // toXDelta
            0f,  // fromYDelta
            view.height.toFloat()
        ) // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }


}