package com.gerosprime.gylog.ui.exercises.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.models.workouts.edit.WorkoutExerciseEditLoadResult
import com.gerosprime.gylog.models.workouts.edit.WorkoutExerciseSetCacheResult
import com.gerosprime.gylog.ui.exercises.R
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_exercise_add.*
import javax.inject.Inject


class WorkoutExerciseEditActivity : AppCompatActivity() {

    object EXTRAS {
        const val EXTRA_WORKOUT_INDEX = "extra_workout_index"
        val EXTRA_RESULT_START_INDEX = "extra_result_start_index"
        val EXTRA_RESULT_END_INDEX = "extra_result_end_index"
    }

    @Inject
    lateinit var factory : ViewModelProvider.Factory
    private lateinit var viewModel : WorkoutExerciseEditViewModel

    lateinit var exercisesRecyclerView: RecyclerView
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory)
            .get(DefaultWorkoutExerciseEditViewModel::class.java)

        setContentView(R.layout.activity_exercise_add)
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
        setSupportActionBar(toolbar)


        exercisesRecyclerView = findViewById(R.id.activity_exercise_add_exercises)
        exercisesRecyclerView.addItemDecoration(DividerItemDecoration(this,
            DividerItemDecoration.VERTICAL))

        viewModel.fetchStateMutableLiveData.observe(this,
            Observer { fetchStateChanged(it) })
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
        menuInflater.inflate(R.menu.menu_exercises_add_activity, menu)
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
        val adapter = WorkoutExerciseEditAdapter(result.workoutExercises, result.exercises)
        adapter.imageClickListener = object : OnItemClickListener<ExerciseEntity> {
            override fun onItemClicked(item: ExerciseEntity) {
                exerciseImageClicked(item)
            }
        }
        exercisesRecyclerView.adapter = adapter
    }

    private fun exerciseImageClicked(entity: ExerciseEntity) {
        // Open details
    }

    private fun fetchStateChanged(fetchState: FetchState) {

    }

    private fun exercisesSetToWorkout(result : WorkoutExerciseSetCacheResult) {
        val intentData = Intent()
        intentData.putExtra(EXTRAS.EXTRA_WORKOUT_INDEX, getWorkoutIndex())
        setResult(Activity.RESULT_OK, intentData)
        finish()
    }

}