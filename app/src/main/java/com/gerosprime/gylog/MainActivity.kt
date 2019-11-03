package com.gerosprime.gylog

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gerosprime.gylog.MainActivity.RequestCodes.PROGRAM_EDIT
import com.gerosprime.gylog.ui.programs.ProgramsDashboardFragment
import com.gerosprime.gylog.ui.programs.add.ProgramsAddActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {


    lateinit var floatingActionButton: FloatingActionButton

    lateinit var toolbar : Toolbar


    object RequestCodes {
        const val PROGRAM_EDIT = 32
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        floatingActionButton = findViewById(R.id.activity_main_floating_action)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_programs, R.id.navigation_exercises
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            run {
                navigationItemSelected(destination)
            }
        }
        // navView.setOnNavigationItemSelectedListener { navigationItemSelected(it) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            PROGRAM_EDIT -> {

                if (resultCode == Activity.RESULT_OK) {
                    val insertIndex =
                        data!!.getIntExtra(ProgramsAddActivity.Result.EXTRA_PROGRAM_INSERT_INDEX, 0)

                    val findFragmentById =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                    val fragment
                            = findFragmentById!!.childFragmentManager.fragments[0]
                            as ProgramsDashboardFragment
                    fragment.notifyItemInserted(insertIndex)
                }

            }
        }

    }

    private fun navigationItemSelected(destination: NavDestination) : Boolean {
        when (destination.id) {
            R.id.navigation_programs -> programsNavigationSelected()
            R.id.navigation_home -> homeNavigationSelected()
            R.id.navigation_exercises -> exerciseNavigationSelected()
        }
        return true
    }

    private fun exerciseNavigationSelected() {

        if (floatingActionButton.isOrWillBeShown)
            floatingActionButton.hide()

        floatingActionButton.show()

        floatingActionButton.setOnClickListener {
        }

    }

    private fun homeNavigationSelected() {
        if (floatingActionButton.isOrWillBeShown)
            floatingActionButton.hide()

        floatingActionButton.setOnClickListener {

        }
    }

    private fun programsNavigationSelected() {

        if (floatingActionButton.isOrWillBeShown)
            floatingActionButton.hide()

        floatingActionButton.show()

        floatingActionButton.setOnClickListener {
            startActivityForResult(Intent(this, ProgramsAddActivity::class.java),
                PROGRAM_EDIT)
        }
    }

}
