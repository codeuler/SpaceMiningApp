package com.example.spacemining

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.spacemining.databinding.ActivityMainBinding

/**
 * Actividad principal que gestiona la navegación entre fragmentos.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bindingActivity: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        // Obtiene el Contenedor de fragmentos
        val navHostController =
            supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        // Obtiene el NavController del contenedor de fragmentos
        navController = navHostController.navController
        // Configura la visibilidad de la toolbar en función del fragmento actual
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.entryFragment) {
                bindingActivity.toolbar.visibility = View.GONE
            } else {
                bindingActivity.toolbar.visibility = View.VISIBLE
            }
        }

        // Configura el clic del botón "backButton" para navegar hacia atrás
        bindingActivity.backButton.setOnClickListener {
            navController.navigateUp()
        }
    }
}
