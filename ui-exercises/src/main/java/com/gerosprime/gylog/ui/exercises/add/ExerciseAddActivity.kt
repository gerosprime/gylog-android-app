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
import androidx.lifecycle.ViewModelProviders
import com.gerosprime.gylog.models.muscle.MuscleEnum
import com.gerosprime.gylog.ui.exercises.R
import com.gerosprime.gylog.ui.exercises.add.ExerciseAddActivity.Extras.RECORD_ID
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputLayout
import dagger.android.AndroidInjection
import javax.inject.Inject

class ExerciseAddActivity : AppCompatActivity() {

    lateinit var toolbar : MaterialToolbar

    @Inject
    lateinit var factory : ViewModelProvider.Factory
    lateinit var viewModel : ExerciseAddViewModel

    lateinit var nameTextInputLayout: TextInputLayout
    lateinit var descriptionTextInputLayout: TextInputLayout
    lateinit var directionTextInputLayout: TextInputLayout

    object Extras {
        const val RECORD_ID = "extra_exercise_record_id"
    }

    object Result {
        const val FLAG = "result_flag"
        const val INDEX = "result_index"
    }

    var muscleMaps : MutableMap<MuscleEnum, Chip> = mutableMapOf()
    var chipMuscleMaps : MutableMap<Chip, MuscleEnum> = mutableMapOf()

    lateinit var chestChip : Chip
    lateinit var chestUpperChip : Chip
    lateinit var chestLowerChip : Chip
    lateinit var tricepsChip : Chip
    lateinit var bicepsChip : Chip
    lateinit var upperBackChip : Chip
    lateinit var lowerBackChip : Chip
    lateinit var hamstringsChip : Chip
    lateinit var forearmsChip : Chip
    lateinit var shoulderSideChip : Chip
    lateinit var shoulderFrontChip : Chip
    lateinit var shoulderBackChip : Chip
    lateinit var trapsChip : Chip
    lateinit var absChip : Chip
    lateinit var quadsChip : Chip
    lateinit var calvesChip : Chip

    lateinit var selectedMuscles : ArrayList<MuscleEnum>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory)
            .get(DefaultExerciseAddViewModel::class.java)

        setContentView(R.layout.activity_exercise_add)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        viewModel.saveResultMLD.observe(this, Observer {
            val dataIntent = Intent()
            dataIntent.putExtra(Result.FLAG, it.flag)
            dataIntent.putExtra(Result.INDEX, it.itemIndex)
            setResult(Activity.RESULT_OK, dataIntent)
            finish()
        })
        viewModel.loadResultMLD.observe(this, Observer {

            nameTextInputLayout.editText?.setText(it?.name)
            descriptionTextInputLayout.editText?.setText(it?.description)
            directionTextInputLayout.editText?.setText(it?.directions)
            nameTextInputLayout.editText?.setText(it?.name)

            selectedMuscles = it.muscles
            for (selectedMuscle in selectedMuscles) {
                muscleMaps[selectedMuscle]?.isChecked = true
            }

        })

        nameTextInputLayout = findViewById(R.id.activity_exercise_add_layout_name)
        descriptionTextInputLayout = findViewById(R.id.activity_exercise_add_layout_description)
        directionTextInputLayout = findViewById(R.id.activity_exercise_add_layout_directions)

        chestChip = findViewById(R.id.activity_exercise_add_muscle_chest)
        chestChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.CHEST] = chestChip
        chipMuscleMaps[chestChip] = MuscleEnum.CHEST

        chestUpperChip = findViewById(R.id.activity_exercise_add_muscle_chest_upper)
        chestUpperChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.CHEST_UPPER] = chestUpperChip
        chipMuscleMaps[chestUpperChip] = MuscleEnum.CHEST_UPPER

        chestLowerChip = findViewById(R.id.activity_exercise_add_muscle_chest_lower)
        chestLowerChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.CHEST_LOWER] = chestLowerChip
        chipMuscleMaps[chestLowerChip] = MuscleEnum.CHEST_LOWER

        tricepsChip = findViewById(R.id.activity_exercise_add_muscle_triceps)
        tricepsChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.TRICEPS] = tricepsChip
        chipMuscleMaps[tricepsChip] = MuscleEnum.TRICEPS

        bicepsChip = findViewById(R.id.activity_exercise_add_muscle_biceps)
        bicepsChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.BICEPS] = bicepsChip
        chipMuscleMaps[bicepsChip] = MuscleEnum.BICEPS

        upperBackChip = findViewById(R.id.activity_exercise_add_muscle_back_upper)
        upperBackChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.BACK_UPPER] = upperBackChip
        chipMuscleMaps[upperBackChip] = MuscleEnum.BACK_UPPER

        lowerBackChip = findViewById(R.id.activity_exercise_add_muscle_back_lower)
        lowerBackChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.BACK_LOWER] = lowerBackChip
        chipMuscleMaps[lowerBackChip] = MuscleEnum.BACK_LOWER

        hamstringsChip = findViewById(R.id.activity_exercise_add_muscle_hamstring)
        hamstringsChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.HAMSTRINGS] = hamstringsChip
        chipMuscleMaps[hamstringsChip] = MuscleEnum.HAMSTRINGS

        forearmsChip = findViewById(R.id.activity_exercise_add_muscle_forearms)
        forearmsChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.FOREARMS] = forearmsChip
        chipMuscleMaps[forearmsChip] = MuscleEnum.FOREARMS

        shoulderSideChip = findViewById(R.id.activity_exercise_add_muscle_shoulder_side)
        shoulderSideChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.SHOULDER_SIDE] = shoulderSideChip
        chipMuscleMaps[shoulderSideChip] = MuscleEnum.SHOULDER_SIDE

        shoulderFrontChip = findViewById(R.id.activity_exercise_add_muscle_shoulder_front)
        shoulderFrontChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.SHOULDER_FRONT] = shoulderFrontChip
        chipMuscleMaps[shoulderFrontChip] = MuscleEnum.SHOULDER_FRONT

        shoulderBackChip = findViewById(R.id.activity_exercise_add_muscle_shoulder_back)
        shoulderBackChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.SHOULDER_BACK] = shoulderBackChip
        chipMuscleMaps[shoulderBackChip] = MuscleEnum.SHOULDER_BACK

        trapsChip = findViewById(R.id.activity_exercise_add_muscle_traps)
        trapsChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.TRAPS] = trapsChip
        chipMuscleMaps[trapsChip] = MuscleEnum.TRAPS

        absChip = findViewById(R.id.activity_exercise_add_muscle_abs)
        absChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.ABS] = absChip
        chipMuscleMaps[absChip] = MuscleEnum.ABS

        quadsChip = findViewById(R.id.activity_exercise_add_muscle_quads)
        quadsChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.QUADS] = quadsChip
        chipMuscleMaps[quadsChip] = MuscleEnum.QUADS

        calvesChip = findViewById(R.id.activity_exercise_add_muscle_calves)
        calvesChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.CALVES] = calvesChip
        chipMuscleMaps[calvesChip] = MuscleEnum.CALVES


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

        val name = nameTextInputLayout.editText?.text.toString()

        if (name.isNullOrBlank()) {
            nameTextInputLayout.error = getString(R.string.exercise_add_name_is_required)
            return
        }

        val description = descriptionTextInputLayout.editText?.text.toString()
        val instruction = directionTextInputLayout.editText?.text.toString()

        val muscles : ArrayList<MuscleEnum> =  arrayListOf()

        viewModel.saveExercise(getExerciseId(), name, description,
            instruction, muscles)
    }

    private fun getExerciseId() : Long? {
        return if (intent.hasExtra(RECORD_ID)) {
            intent.getLongExtra(RECORD_ID, -1)
        } else {
            null
        }
    }

}