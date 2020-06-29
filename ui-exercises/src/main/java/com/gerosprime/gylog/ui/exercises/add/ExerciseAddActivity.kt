package com.gerosprime.gylog.ui.exercises.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gerosprime.gylog.models.exercises.ExerciseDatabaseSaveResult
import com.gerosprime.gylog.models.exercises.LoadedSingleExerciseResult
import com.gerosprime.gylog.models.muscle.MuscleEnum
import com.gerosprime.gylog.ui.exercises.R
import com.gerosprime.gylog.ui.exercises.add.ExerciseAddActivity.Extras.RECORD_ID
import com.gerosprime.gylog.ui.exercises.databinding.ActivityExerciseAddBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.chip.Chip
import dagger.android.AndroidInjection
import javax.inject.Inject

class ExerciseAddActivity : AppCompatActivity() {

    lateinit var toolbar : MaterialToolbar

    @Inject
    lateinit var factory : ViewModelProvider.Factory
    private lateinit var viewModel : ExerciseAddViewModel


    object Extras {
        const val RECORD_ID = "extra_exercise_record_id"
    }

    object Result {
        const val FLAG = "result_flag"
        const val INDEX = "result_index"
    }

    private var muscleMaps : MutableMap<MuscleEnum, Chip> = mutableMapOf()
    private var chipMuscleMaps : MutableMap<Chip, MuscleEnum> = mutableMapOf()

    private lateinit var binding : ActivityExerciseAddBinding

    private lateinit var selectedMuscles : ArrayList<MuscleEnum>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory)
            .get(DefaultExerciseAddViewModel::class.java)

        binding = ActivityExerciseAddBinding.inflate(layoutInflater)

        setContentView(binding.root)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        viewModel.saveResultLiveData.observe(this, Observer { exerciseSaved(it) })

        viewModel.loadResultLiveData.observe(this, Observer { exerciseLoaded(it) })


        binding.apply {
            muscleMaps[MuscleEnum.CHEST] = activityExerciseAddMuscleChest
            chipMuscleMaps[activityExerciseAddMuscleChest] = MuscleEnum.CHEST

            muscleMaps[MuscleEnum.CHEST_UPPER] = activityExerciseAddMuscleChestUpper
            chipMuscleMaps[activityExerciseAddMuscleChestUpper] = MuscleEnum.CHEST_UPPER


            muscleMaps[MuscleEnum.CHEST_LOWER] = activityExerciseAddMuscleChestLower
            chipMuscleMaps[activityExerciseAddMuscleChestLower] = MuscleEnum.CHEST_LOWER

            muscleMaps[MuscleEnum.TRICEPS] = activityExerciseAddMuscleTriceps
            chipMuscleMaps[activityExerciseAddMuscleTriceps] = MuscleEnum.TRICEPS


            muscleMaps[MuscleEnum.BICEPS] = activityExerciseAddMuscleBiceps
            chipMuscleMaps[activityExerciseAddMuscleBiceps] = MuscleEnum.BICEPS


            muscleMaps[MuscleEnum.BACK_UPPER] = activityExerciseAddMuscleBackUpper
            chipMuscleMaps[activityExerciseAddMuscleBackUpper] = MuscleEnum.BACK_UPPER


            muscleMaps[MuscleEnum.BACK_LOWER] = activityExerciseAddMuscleBackLower
            chipMuscleMaps[activityExerciseAddMuscleBackLower] = MuscleEnum.BACK_LOWER


            muscleMaps[MuscleEnum.HAMSTRINGS] = activityExerciseAddMuscleHamstring
            chipMuscleMaps[activityExerciseAddMuscleHamstring] = MuscleEnum.HAMSTRINGS


            muscleMaps[MuscleEnum.FOREARMS] = activityExerciseAddMuscleForearms
            chipMuscleMaps[activityExerciseAddMuscleForearms] = MuscleEnum.FOREARMS


            muscleMaps[MuscleEnum.SHOULDER_SIDE] = activityExerciseAddMuscleShoulderSide
            chipMuscleMaps[activityExerciseAddMuscleShoulderSide] = MuscleEnum.SHOULDER_SIDE


            muscleMaps[MuscleEnum.SHOULDER_FRONT] = activityExerciseAddMuscleShoulderFront
            chipMuscleMaps[activityExerciseAddMuscleShoulderFront] = MuscleEnum.SHOULDER_FRONT

            muscleMaps[MuscleEnum.SHOULDER_BACK] = activityExerciseAddMuscleShoulderBack
            chipMuscleMaps[activityExerciseAddMuscleShoulderBack] = MuscleEnum.SHOULDER_BACK


            muscleMaps[MuscleEnum.TRAPS] = activityExerciseAddMuscleTraps
            chipMuscleMaps[activityExerciseAddMuscleTraps] = MuscleEnum.TRAPS

            muscleMaps[MuscleEnum.ABS] = activityExerciseAddMuscleAbs
            chipMuscleMaps[activityExerciseAddMuscleAbs] = MuscleEnum.ABS

            muscleMaps[MuscleEnum.QUADS] = activityExerciseAddMuscleQuads
            chipMuscleMaps[activityExerciseAddMuscleQuads] = MuscleEnum.QUADS

            muscleMaps[MuscleEnum.CALVES] = activityExerciseAddMuscleCalves
            chipMuscleMaps[activityExerciseAddMuscleCalves] = MuscleEnum.CALVES

        }

        if (savedInstanceState == null) {
            viewModel.loadExercise(getExerciseId())
        }

    }

    private fun muscleCheckEvent(cmp : CompoundButton) {

        if (cmp.isChecked) {
            selectedMuscles.add(chipMuscleMaps[cmp]!!)
        } else {
            selectedMuscles.remove(chipMuscleMaps[cmp]!!)
        }

    }

    private fun exerciseLoaded(result : LoadedSingleExerciseResult) {

        binding.apply {
            activityExerciseAddEditName.setText(result.name)
            activityExerciseAddEditDescription.setText(result.description)
            activityExerciseAddEditDirections.setText(result.directions)

            selectedMuscles = result.muscles
            for (selectedMuscle in selectedMuscles) {

                val checkBox = muscleMaps[selectedMuscle]
                checkBox?.isChecked = true

            }

            for (value in muscleMaps.values) {
                value.setOnCheckedChangeListener { compoundButton, _ ->
                    run {
                        muscleCheckEvent(compoundButton)
                    }
                }
            }
        }

    }

    private fun exerciseSaved(result : ExerciseDatabaseSaveResult) {
        val dataIntent = Intent()
        dataIntent.putExtra(Result.FLAG, result.flag)
        dataIntent.putExtra(Result.INDEX, result.itemIndex)
        setResult(Activity.RESULT_OK, dataIntent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_exercises_add_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
            R.id.activity_workout_exercise_edit_save -> {
                saveExercise()
            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun saveExercise() {

        binding.apply {
            val name = activityExerciseAddEditName.text.toString()

            if (name.isBlank()) {
                activityExerciseAddEditName.error = getString(R.string.exercise_add_name_is_required)
                return
            }

            val description = activityExerciseAddEditDescription.text.toString()
            val instruction = activityExerciseAddEditDirections.text.toString()

            // val muscles : ArrayList<MuscleEnum> =  arrayListOf()

            viewModel.saveExercise(getExerciseId(), name, description,
                instruction, selectedMuscles)
        }

    }

    private fun getExerciseId() : Long? {
        return if (intent.hasExtra(RECORD_ID)) {
            intent.getLongExtra(RECORD_ID, -1)
        } else {
            null
        }
    }

}