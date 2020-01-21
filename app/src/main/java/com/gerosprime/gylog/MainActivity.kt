package com.gerosprime.gylog

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gerosprime.gylog.MainActivity.RequestCodes.EXERCISE_DETAIL
import com.gerosprime.gylog.MainActivity.RequestCodes.EXERCISE_EDIT
import com.gerosprime.gylog.MainActivity.RequestCodes.PROGRAM_DETAIL
import com.gerosprime.gylog.MainActivity.RequestCodes.PROGRAM_EDIT
import com.gerosprime.gylog.models.exercises.ExerciseDatabaseSaveResult
import com.gerosprime.gylog.ui.exercises.add.ExerciseAddActivity
import com.gerosprime.gylog.ui.exercises.dashboard.DashboardExercisesFragment
import com.gerosprime.gylog.ui.exercises.dashboard.ExerciseItemClick
import com.gerosprime.gylog.ui.exercises.detail.ExerciseDetailActivity
import com.gerosprime.gylog.ui.programs.ProgramsDashboardFragment
import com.gerosprime.gylog.ui.programs.add.ProgramsAddActivity
import com.gerosprime.gylog.ui.programs.detail.ProgramDetailActivity
import com.gerosprime.gylog.ui.settings.SettingsActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), DashboardExercisesFragment.Listener {


    lateinit var floatingActionButton: FloatingActionButton

    lateinit var toolbar : Toolbar


    object RequestCodes {
        const val PROGRAM_DETAIL = 31
        const val PROGRAM_EDIT = 32
        const val EXERCISE_EDIT = 33
        const val EXERCISE_DETAIL = 34
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
                R.id.navigation_programs, R.id.navigation_you, R.id.navigation_exercises
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.main_activity_menu_settings ->
                startActivity(Intent(this, SettingsActivity::class.java))
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            PROGRAM_DETAIL -> {
                if (resultCode == Activity.RESULT_OK) {
                    val insertIndex =
                        data!!.getIntExtra(ProgramDetailActivity.Extras.PROGRAM_INDEX, 0)

                    val findFragmentById =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                    val fragment
                            = findFragmentById!!.childFragmentManager.fragments[0]
                            as ProgramsDashboardFragment

                    when (val mode = data.getIntExtra(ProgramDetailActivity.ResultExtras.PROGRAM_EDIT_MODE, -1)) {
                        ProgramsAddActivity.Mode.EDIT -> {
                            fragment.notifyItemUpdated(insertIndex)
                        }
                        ProgramsAddActivity.Mode.INSERT -> {
                            fragment.notifyItemInserted(insertIndex)
                        }
                        else -> {
                            throw IllegalArgumentException("Invalid mode: $mode")
                        }
                    }
                }
            }

            PROGRAM_EDIT -> {

                if (resultCode == Activity.RESULT_OK) {
                    val insertIndex =
                        data!!.getIntExtra(ProgramsAddActivity.Result.EXTRA_PROGRAM_AFFECTED_INDEX, 0)

                    val findFragmentById =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                    val fragment
                            = findFragmentById!!.childFragmentManager.fragments[0]
                            as ProgramsDashboardFragment

                    when (val mode = data.getIntExtra(ProgramsAddActivity.Result.EXTRA_PROGRAM_EDIT_MODE, -1)) {
                        ProgramsAddActivity.Mode.EDIT -> {
                            fragment.notifyItemUpdated(insertIndex)
                        }
                        ProgramsAddActivity.Mode.INSERT -> {
                            fragment.notifyItemInserted(insertIndex)
                        }
                        else -> {
                            throw IllegalArgumentException("Invalid mode: $mode")
                        }
                    }
                }

            }

            EXERCISE_DETAIL -> {
                if (resultCode == Activity.RESULT_OK) {
                    val index = data!!.getIntExtra(ExerciseAddActivity.Result.INDEX, 0)


                    val flag = data.getIntExtra(ExerciseAddActivity.Result.FLAG, -1)
                    when (flag) {
                        ExerciseDatabaseSaveResult.Flag.NEW -> {

                            val findFragmentById =
                                supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                            val fragment
                                    = findFragmentById!!.childFragmentManager.fragments[0]
                                    as DashboardExercisesFragment

                            fragment.notifyItemUpdated(index)

                        }

                        ExerciseDatabaseSaveResult.Flag.UPDATED -> {

                            val findFragmentById =
                                supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                            val fragment
                                    = findFragmentById!!.childFragmentManager.fragments[0]
                                    as DashboardExercisesFragment

                            fragment.notifyItemUpdated(index)

                        }

                    }

                }
            }

            EXERCISE_EDIT -> {
                if (resultCode == Activity.RESULT_OK) {
                    val index = data!!.getIntExtra(ExerciseAddActivity.Result.INDEX, 0)


                    val flag = data.getIntExtra(ExerciseAddActivity.Result.FLAG, -1)
                    when (flag) {
                        ExerciseDatabaseSaveResult.Flag.NEW -> {

                            val findFragmentById =
                                supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                            val fragment
                                    = findFragmentById!!.childFragmentManager.fragments[0]
                                    as DashboardExercisesFragment

                            fragment.notifyItemInserted(index)

                        }

                        ExerciseDatabaseSaveResult.Flag.UPDATED -> {

                            val findFragmentById =
                                supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                            val fragment
                                    = findFragmentById!!.childFragmentManager.fragments[0]
                                    as DashboardExercisesFragment

                            fragment.notifyItemUpdated(index)

                        }

                    }

                }
            }
        }

    }

    private fun navigationItemSelected(destination: NavDestination) : Boolean {
        when (destination.id) {
            R.id.navigation_you -> youNavigationSelected()
            R.id.navigation_programs -> programsNavigationSelected()
            R.id.navigation_home -> homeNavigationSelected()
            R.id.navigation_exercises -> exerciseNavigationSelected()
        }
        return true
    }

    private fun youNavigationSelected() {
        if (floatingActionButton.isOrWillBeShown)
            floatingActionButton.hide()

        floatingActionButton.setOnClickListener {

        }
    }

    private fun exerciseNavigationSelected() {

        if (floatingActionButton.isOrWillBeShown)
            floatingActionButton.hide()

        floatingActionButton.show()

        floatingActionButton.setOnClickListener {
            startActivityForResult(Intent(this, ExerciseAddActivity::class.java),
                EXERCISE_EDIT)
        }

    }

    private fun showExerciseDetail(id : Long) {
        val intentExercise = Intent(this, ExerciseDetailActivity::class.java)
        intentExercise.putExtra(ExerciseDetailActivity.Extras.EXERCISE_ID, id)
        startActivityForResult(intentExercise,
            EXERCISE_DETAIL
        )
    }

    override fun onExerciseItemClick(exerciseItem: ExerciseItemClick) {
        showExerciseDetail(exerciseItem.entity.recordId!!)
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
