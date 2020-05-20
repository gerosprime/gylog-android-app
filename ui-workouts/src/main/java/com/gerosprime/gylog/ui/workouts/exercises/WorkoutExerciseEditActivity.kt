package com.gerosprime.gylog.ui.workouts.exercises

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.models.muscle.MuscleEnum
import com.gerosprime.gylog.models.workouts.edit.commit.WorkoutExerciseSetCacheResult
import com.gerosprime.gylog.models.workouts.edit.load.WorkoutExerciseEditLoadResult
import com.gerosprime.gylog.ui.exercises.filter.ExerciseFilterDialogFragment
import com.gerosprime.gylog.ui.workouts.R
import dagger.android.AndroidInjection
import javax.inject.Inject


class WorkoutExerciseEditActivity : AppCompatActivity(),
    ExerciseFilterDialogFragment.Listener {

    object EXTRAS {
        const val EXTRA_WORKOUT_INDEX = "extra_workout_index"
    }

    object DialogTags {
        const val FILTER_DIALOG = "dialog_filter_dialog"
    }

    @Inject
    lateinit var factory : ViewModelProvider.Factory
    private lateinit var viewModel : WorkoutExerciseEditViewModel

    lateinit var exercisesRecyclerView: RecyclerView
    lateinit var toolbar: Toolbar

    private lateinit var buttonFilter : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, factory)
            .get(DefaultWorkoutExerciseEditViewModel::class.java)

        setContentView(R.layout.activity_workout_exercise_add)
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
        setSupportActionBar(toolbar)

        buttonFilter = findViewById(R.id.activity_workout_exercise_filter)
        buttonFilter.setOnClickListener { showExerciseFilterDialog() }

        exercisesRecyclerView = findViewById(R.id.activity_exercise_add_exercises)
        exercisesRecyclerView.addItemDecoration(DividerItemDecoration(this,
            DividerItemDecoration.VERTICAL))

        viewModel.exercisesMutableLiveData.observe(this,
            Observer { populateExercises(it) })
        viewModel.exerciseCacheMutableLiveData.observe(this,
            Observer { exercisesSetToWorkout(it) })

        if (savedInstanceState == null)
            viewModel.loadExercises(getWorkoutIndex())
    }

    private fun getWorkoutIndex() : Int {
        return intent.extras?.getInt(EXTRAS.EXTRA_WORKOUT_INDEX) as Int
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_workout_exercises_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            android.R.id.home -> {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
            R.id.activity_workout_exercise_edit_save -> {

                val adapter = exercisesRecyclerView.adapter as WorkoutExerciseEditAdapter
                viewModel.addExercisesIntoWorkout(getWorkoutIndex(),
                    adapter.selectedItems())
            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun populateExercises(result : WorkoutExerciseEditLoadResult) {
        val adapter = WorkoutExerciseEditAdapter(
            result.workoutExercisesMap,
            result.exercises
        )

        exercisesRecyclerView.adapter = adapter
    }

    private fun exercisesSetToWorkout(result : WorkoutExerciseSetCacheResult) {
        val intentData = Intent()
        intentData.putExtra(EXTRAS.EXTRA_WORKOUT_INDEX, getWorkoutIndex())
        setResult(Activity.RESULT_OK, intentData)
        finish()
    }

    private fun showExerciseFilterDialog() {
        val filter = ExerciseFilterDialogFragment()
        filter.show(supportFragmentManager, DialogTags.FILTER_DIALOG)
    }

    override fun onFilterConfigured(selectedMuscles: ArrayList<MuscleEnum>) {
        viewModel.filterExercises(getWorkoutIndex(), selectedMuscles)
    }
}