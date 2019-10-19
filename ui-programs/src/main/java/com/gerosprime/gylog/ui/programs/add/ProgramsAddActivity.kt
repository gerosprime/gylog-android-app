package com.gerosprime.gylog.ui.programs.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.models.exercises.ExerciseTemplatesAddToCacheResult
import com.gerosprime.gylog.models.programs.NewProgramSetToCacheResult
import com.gerosprime.gylog.models.workouts.WorkoutAddToCacheResult
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import com.gerosprime.gylog.ui.programs.R
import com.gerosprime.gylog.ui.programs.add.ProgramsAddActivity.DialogTags.TAG_ADD_WORKOUT_DIALOG
import com.gerosprime.gylog.ui.programs.add.workouts.ProgramWorkoutsAdapter
import com.gerosprime.gylog.ui.programs.workouts.AddWorkoutDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import dagger.android.AndroidInjection
import javax.inject.Inject

class ProgramsAddActivity : AppCompatActivity(), AddWorkoutDialogFragment.Listener {


    private lateinit var nameEditText : TextInputLayout
    private lateinit var descriptionEditText : TextInputLayout

    @Inject
    lateinit var factory : ViewModelProvider.Factory
    lateinit var viewModel : ProgramsAddViewModel

    private lateinit var workoutsRecyclerView: RecyclerView
    private lateinit var workoutAddButton : Button

    private lateinit var toolbar : Toolbar

    private object DialogTags {
        var TAG_ADD_WORKOUT_DIALOG = "add_workout_dialog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory)
            .get(DefaultProgramsAddViewModel::class.java)

        setContentView(R.layout.activity_add_programs)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        nameEditText = findViewById(R.id.fragment_add_programs_name_layout)
        descriptionEditText = findViewById(R.id.fragment_add_programs_description_layout)
        workoutsRecyclerView = findViewById(R.id.fragment_add_programs_workouts)
        workoutAddButton = findViewById(R.id.fragment_add_programs_add_workout)
        workoutAddButton.setOnClickListener { showCreateWorkoutDialog() }

        viewModel.programSetToCacheResultMLD.observe(this, Observer { newProgramLoaded(it) })
        viewModel.workoutAddToCacheResultMLD.observe(this,
            Observer { workoutAddedToCache(it) })
        viewModel.exerciseTemplateAddResultMTD.observe(this,
            Observer { exerciseAddedToWorkout(it) })

        if (savedInstanceState == null)
            viewModel.loadNewProgram()
    }

    private fun newProgramLoaded(newProgramSetToCacheResult: NewProgramSetToCacheResult) {
        workoutsRecyclerView.adapter = ProgramWorkoutsAdapter(newProgramSetToCacheResult.workouts)
    }

    private fun workoutAddedToCache(result: WorkoutAddToCacheResult) {
        var adapter = workoutsRecyclerView.adapter as ProgramWorkoutsAdapter?
        adapter!!.notifyItemInserted(result.itemPosition)
        workoutsRecyclerView.scrollToPosition(result.itemPosition)
    }

    private fun exerciseAddedToWorkout(result : ExerciseTemplatesAddToCacheResult) {

    }

    override fun workoutNameDescriptionDefined(newWorkout: WorkoutEntity) {
        viewModel.addWorkoutToCache(newWorkout.name, newWorkout.description)
    }

    private fun showEditWorkoutDialog(workoutListIndex : Int) {

    }

    private fun showCreateWorkoutDialog() {
        var workoutCreateDialog = AddWorkoutDialogFragment()
        workoutCreateDialog.show(supportFragmentManager, TAG_ADD_WORKOUT_DIALOG)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_add_program, menu)
        return true
    }

    private fun saveProgram() {

        val name = nameEditText.editText?.text.toString()
        val description = descriptionEditText.editText?.text.toString()
        if (name.isNotEmpty()) {
            viewModel.saveProgramToDB(name, description)
        } else {
            nameEditText.error = getString(R.string.program_name_is_required)
        }



    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> {
                viewModel.clearAll()
                finish()
            }
            R.id.activity_add_program_save -> saveProgram()
        }

        return true
    }

}