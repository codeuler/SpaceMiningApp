package com.example.spacemining

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.spacemining.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bindingActivity: ActivityMainBinding =
            DataBindingUtil.setContentView<ActivityMainBinding>(
                this, R.layout.activity_main
            )

        //Obtengo el Contenedor de fragmentos
        val navHostController =
            supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        //Obtengo el navcontroller del contenedor de fragmentos
        navController = navHostController.navController
        //Cada vez que se haga una transición de fragmentos se analiza si dicho fragmento es el entry
        //De ser así se desapareche la tool var, de no ser así se pone visible
        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id == R.id.entryFragment) {
                bindingActivity.toolbar.visibility = View.GONE
            } else {
                bindingActivity.toolbar.visibility = View.VISIBLE
            }
        }


        bindingActivity.backButton.setOnClickListener {
            navController.navigateUp()
        }
    }
}