package com.gerosprime.gylog.ui.programs.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.programs.edit.load.EditProgramSetToCacheResult
import com.gerosprime.gylog.models.programs.save.SaveProgramDatabaseResult
import com.gerosprime.gylog.models.workouts.edit.add.WorkoutAddToCacheResult
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import com.gerosprime.gylog.ui.workouts.exercises.WorkoutExerciseEditActivity
import com.gerosprime.gylog.ui.exercises.templatesets.EditTemplateSetsActivity
import com.gerosprime.gylog.ui.programs.R
import com.gerosprime.gylog.ui.programs.add.ProgramsAddActivity.DialogTags.TAG_ADD_WORKOUT_DIALOG
import com.gerosprime.gylog.ui.programs.add.ProgramsAddActivity.Extras.EXTRA_PROGRAM_RECORD_ID
import com.gerosprime.gylog.ui.programs.add.ProgramsAddActivity.RequestCodes.TEMPLATE_SET_EDIT
import com.gerosprime.gylog.ui.programs.add.ProgramsAddActivity.RequestCodes.WORKOUT_EDIT
import com.gerosprime.gylog.ui.programs.add.ProgramsAddActivity.Result.EXTRA_PROGRAM_INSERT_INDEX
import com.gerosprime.gylog.ui.programs.add.exercises.ExerciseExecutionClicked
import com.gerosprime.gylog.ui.programs.add.workouts.ProgramWorkoutsAdapter
import com.gerosprime.gylog.ui.programs.workouts.AddWorkoutDialogFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.android.AndroidInjection
import javax.inject.Inject

class ProgramsAddActivity : AppCompatActivity(), AddWorkoutDialogFragment.Listener {


    private lateinit var nameEditText : TextInputLayout
    private lateinit var descriptionEditText : TextInputLayout

    @Inject
    lateinit var factory : ViewModelProvider.Factory
    private lateinit var viewModel : ProgramsAddViewModel

    private lateinit var workoutsRecyclerView: RecyclerView
    private lateinit var workoutAddButton : Button

    private lateinit var toolbar : Toolbar

    object Result {
        const val EXTRA_PROGRAM_INSERT_INDEX = "program_insert_index"
    }

    object Extras {
        const val EXTRA_PROGRAM_RECORD_ID = "extra_program_record_id"
    }

    private object DialogTags {
        var TAG_ADD_WORKOUT_DIALOG = "add_workout_dialog"
    }

    private object RequestCodes {
        const val WORKOUT_EDIT = 1
        const val TEMPLATE_SET_EDIT = 2
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
        viewModel.saveProgramResultMLD.observe(this, Observer { programSaved(it) })

        if (savedInstanceState == null)
            viewModel.loadProgramForEdit(getProgramRecordId())
    }

    override fun onBackPressed() {
        viewModel.clearAll()
        setResult(Activity.RESULT_CANCELED)
        super.onBackPressed()
    }

    private fun getProgramRecordId(): Long {
        return intent.getLongExtra(EXTRA_PROGRAM_RECORD_ID, -1)
    }

    private fun programSaved(result: SaveProgramDatabaseResult?) {

        val intentData = Intent()
        intentData.putExtra(EXTRA_PROGRAM_INSERT_INDEX, result!!.savedIndex)
        setResult(Activity.RESULT_OK, intentData)
        finish()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            WORKOUT_EDIT -> {
                if (resultCode == Activity.RESULT_OK) {
                    val workoutIndex = data!!.getIntExtra(
                        WorkoutExerciseEditActivity.EXTRAS.EXTRA_WORKOUT_INDEX,
                        -1
                    )

                    var adapter = workoutsRecyclerView.adapter as ProgramWorkoutsAdapter?
                    adapter!!.refreshWorkoutContent(workoutIndex)
                }
            }
            TEMPLATE_SET_EDIT -> {
                if (resultCode == Activity.RESULT_OK) {
                    val workoutIndex = data!!.getIntExtra(
                        EditTemplateSetsActivity.Extras.WORKOUT_INDEX,
                        -1
                    )

                    var adapter = workoutsRecyclerView.adapter as ProgramWorkoutsAdapter?
                    adapter!!.refreshWorkoutContent(workoutIndex)
                }
            }
        }
    }

    private fun newProgramLoaded(editProgramSetToCacheResult: EditProgramSetToCacheResult) {

        val adapter = ProgramWorkoutsAdapter(editProgramSetToCacheResult.workouts)
        adapter.exerciseWorkoutListener = object : OnItemClickListener<Int> {
            override fun onItemClicked(item: Int) {
                editWorkoutExercises(item)
            }
        }
        adapter.exerciseExecutionListener = object : OnItemClickListener<ExerciseExecutionClicked> {
            override fun onItemClicked(item: ExerciseExecutionClicked) {
                editExerciseExecution(item)
            }
        }
        workoutsRecyclerView.adapter = adapter

        val programEntity = editProgramSetToCacheResult.programEntity
        nameEditText.editText!!.setText(programEntity.name)
        descriptionEditText.editText!!.setText(programEntity.description)

    }

    private fun editWorkoutExercises(workoutIndex : Int) {

        val editExerciseIntent = Intent(this,
            WorkoutExerciseEditActivity::class.java)
        editExerciseIntent.putExtra(
            WorkoutExerciseEditActivity.EXTRAS.EXTRA_WORKOUT_INDEX,
            workoutIndex)

        startActivityForResult(editExerciseIntent, WORKOUT_EDIT)
    }

    private fun editExerciseExecution(item: ExerciseExecutionClicked) {
        val exerciseTemplateIntent = Intent(this,
            EditTemplateSetsActivity::class.java)
        exerciseTemplateIntent.putExtra(EditTemplateSetsActivity.Extras.WORKOUT_INDEX,
            item.workoutIndex)
        exerciseTemplateIntent.putExtra(EditTemplateSetsActivity.Extras.EXERCISE_INDEX,
            item.exerciseIndex)
        startActivityForResult(exerciseTemplateIntent, TEMPLATE_SET_EDIT)
    }

    private fun workoutAddedToCache(result: WorkoutAddToCacheResult) {

        // TODO Fix null pointer crash
        var adapter = workoutsRecyclerView.adapter as ProgramWorkoutsAdapter?
        adapter!!.notifyItemInserted(result.itemPosition)
        workoutsRecyclerView.smoothScrollToPosition(result.itemPosition)
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