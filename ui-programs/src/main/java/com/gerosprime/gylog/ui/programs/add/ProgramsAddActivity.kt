package com.gerosprime.gylog.ui.programs.add

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.programs.edit.load.EditProgramSetToCacheResult
import com.gerosprime.gylog.models.programs.save.SaveProgramDatabaseResult
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import com.gerosprime.gylog.models.workouts.edit.add.WorkoutAddToCacheResult
import com.gerosprime.gylog.ui.exercises.templatesets.EditTemplateSetsActivity
import com.gerosprime.gylog.ui.programs.R
import com.gerosprime.gylog.ui.programs.add.ProgramsAddActivity.DialogTags.TAG_ADD_WORKOUT_DIALOG
import com.gerosprime.gylog.ui.programs.add.ProgramsAddActivity.Extras.EXTRA_PROGRAM_RECORD_ID
import com.gerosprime.gylog.ui.programs.add.ProgramsAddActivity.RequestCodes.IMAGE_PICKER
import com.gerosprime.gylog.ui.programs.add.ProgramsAddActivity.RequestCodes.TEMPLATE_SET_EDIT
import com.gerosprime.gylog.ui.programs.add.ProgramsAddActivity.RequestCodes.WORKOUT_EDIT
import com.gerosprime.gylog.ui.programs.add.ProgramsAddActivity.Result.EXTRA_PROGRAM_AFFECTED_INDEX
import com.gerosprime.gylog.ui.programs.add.ProgramsAddActivity.Result.EXTRA_PROGRAM_EDIT_MODE
import com.gerosprime.gylog.ui.programs.add.exercises.ExerciseExecutionClicked
import com.gerosprime.gylog.ui.programs.add.workouts.ProgramWorkoutsAdapter
import com.gerosprime.gylog.ui.programs.databinding.ActivityAddProgramsBinding
import com.gerosprime.gylog.ui.programs.workouts.AddWorkoutDialogFragment
import com.gerosprime.gylog.ui.workouts.exercises.WorkoutExerciseEditActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import dagger.android.AndroidInjection
import javax.inject.Inject

class ProgramsAddActivity : AppCompatActivity(), AddWorkoutDialogFragment.Listener {

    @Inject
    lateinit var factory : ViewModelProvider.Factory
    private lateinit var viewModel : ProgramsAddViewModel

    private lateinit var binding : ActivityAddProgramsBinding

    private var pictureUri : Uri? = null
    private var mode : Int = -1

    object Mode {
        const val INSERT = 0
        const val EDIT = 1
        const val REMOVE = 2
    }

    object Result {
        const val EXTRA_PROGRAM_AFFECTED_INDEX = "program_insert_index"
        const val EXTRA_PROGRAM_EDIT_MODE = "program_edit_mode"
    }

    object Extras {
        const val EXTRA_PROGRAM_RECORD_ID = "extra_program_record_id"
    }

    object State {
        const val STATE_PICTURE_URI = "state_picture_uri"
    }

    private object DialogTags {
        var TAG_ADD_WORKOUT_DIALOG = "add_workout_dialog"
    }

    private object RequestCodes {
        const val WORKOUT_EDIT = 1
        const val TEMPLATE_SET_EDIT = 2
        const val IMAGE_PICKER = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, factory)
            .get(DefaultProgramsAddViewModel::class.java)

        binding = ActivityAddProgramsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        binding.apply {
            val adapter = ProgramWorkoutsAdapter(listOf())
            fragmentAddProgramsWorkouts.adapter = adapter

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

            fragmentAddProgramsAddWorkout.setOnClickListener { showCreateWorkoutDialog() }
        }

        viewModel.programSetToCacheResultMLD.observe(this, Observer { newProgramLoaded(it) })
        viewModel.workoutAddToCacheResultMLD.observe(this, Observer { workoutAddedToCache(it) })
        viewModel.saveProgramResultMLD.observe(this, Observer { programSaved(it) })

        if (savedInstanceState == null)

            if (TedPermission.isGranted(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                viewModel.loadProgramForEdit(getProgramRecordId())
            } else {
                TedPermission.with(this)
                    .setRationaleMessage(R.string.read_permission_rationale)
                    .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .setPermissionListener(object : PermissionListener {
                        override fun onPermissionGranted() {
                            viewModel.loadProgramForEdit(getProgramRecordId())
                        }

                        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                            viewModel.loadProgramForEdit(getProgramRecordId())
                        }
                    }).check()
            }

        else {
            if (savedInstanceState.containsKey(State.STATE_PICTURE_URI)) {
                pictureUri = savedInstanceState.getParcelable(State.STATE_PICTURE_URI)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (pictureUri != null) {
            outState.putParcelable(State.STATE_PICTURE_URI, pictureUri)
        }
    }

    private fun pickPicture() {
        val intentPick = Intent(Intent.ACTION_PICK)
        intentPick.type = "image/*"
        startActivityForResult(intentPick, IMAGE_PICKER)
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
        intentData.putExtra(EXTRA_PROGRAM_AFFECTED_INDEX, result!!.savedIndex)
        intentData.putExtra(EXTRA_PROGRAM_EDIT_MODE, mode)
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

                    val adapter = binding.fragmentAddProgramsWorkouts.adapter as ProgramWorkoutsAdapter
                    adapter.refreshWorkoutContent(workoutIndex)
                }
            }
            TEMPLATE_SET_EDIT -> {
                if (resultCode == Activity.RESULT_OK) {
                    val workoutIndex = data!!.getIntExtra(
                        EditTemplateSetsActivity.Extras.WORKOUT_INDEX,
                        -1
                    )

                    val adapter = binding.fragmentAddProgramsWorkouts.adapter
                            as ProgramWorkoutsAdapter
                    adapter.refreshWorkoutContent(workoutIndex)
                }
            }
            IMAGE_PICKER -> {
                if (resultCode == Activity.RESULT_OK) {
                    pictureUri = data!!.data!!
                    // Glide.with(this).load(pictureUri).into(binding.imageProgram)
                }
            }
        }
    }

    private fun newProgramLoaded(editProgramSetToCacheResult: EditProgramSetToCacheResult) {

        mode = editProgramSetToCacheResult.mode

        val adapter = binding.fragmentAddProgramsWorkouts.adapter
                as ProgramWorkoutsAdapter
        adapter.items = editProgramSetToCacheResult.workouts
        adapter.notifyItemRangeChanged(0, adapter.itemCount)

        val programEntity = editProgramSetToCacheResult.programEntity

        binding.apply {
            fragmentAddProgramsNameEdittext.setText(programEntity.name)
            fragmentAddProgramsDescriptionEdittext.setText(programEntity.description)
        }

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

        binding.apply {
            val adapter = fragmentAddProgramsWorkouts.adapter as ProgramWorkoutsAdapter
            adapter.notifyItemInserted(result.itemPosition)
            fragmentAddProgramsWorkouts.smoothScrollToPosition(result.itemPosition)
        }
    }

    override fun workoutNameDescriptionDefined(newWorkout: WorkoutEntity) {
        viewModel.addWorkoutToCache(newWorkout.name, newWorkout.description)
    }

    private fun showEditWorkoutDialog(workoutListIndex : Int) {

    }

    private fun showCreateWorkoutDialog() {
        val workoutCreateDialog = AddWorkoutDialogFragment()
        workoutCreateDialog.show(supportFragmentManager, TAG_ADD_WORKOUT_DIALOG)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_add_program, menu)
        return true
    }

    private fun saveProgram() {

        binding.apply {

            val name = fragmentAddProgramsNameEdittext.text.toString()
            val description = fragmentAddProgramsDescriptionEdittext.text.toString()
            if (name.isNotEmpty()) {
                viewModel.saveProgramToDB(name, description, pictureUri.toString())
            } else {
                fragmentAddProgramsNameLayout.error = getString(R.string.program_name_is_required)
            }
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