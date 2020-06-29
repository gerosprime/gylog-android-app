package com.gerosprime.gylog.ui.exercises.templatesets.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gerosprime.gylog.base.utils.TimeFormatUtil
import com.gerosprime.gylog.models.exercises.templatesets.single.TemplateSetEditLoadResult
import com.gerosprime.gylog.models.exercises.templatesets.single.commit.TemplateSetCommitResult
import com.gerosprime.gylog.ui.exercises.R
import com.gerosprime.gylog.ui.exercises.databinding.ActivityTemplateSetEditBinding
import com.gerosprime.gylog.ui.exercises.templatesets.detail.TemplateSetEditActivity.Extras.EXERCISE_INDEX
import com.gerosprime.gylog.ui.exercises.templatesets.detail.TemplateSetEditActivity.Extras.TEMPLATE_INDEX
import com.gerosprime.gylog.ui.exercises.templatesets.detail.TemplateSetEditActivity.Extras.WORKOUT_INDEX
import dagger.android.AndroidInjection
import javax.inject.Inject

class TemplateSetEditActivity : AppCompatActivity() {

    object Extras {
        const val WORKOUT_INDEX = "extra_workout_index"
        const val EXERCISE_INDEX = "extra_exercise_index"
        const val TEMPLATE_INDEX = "extra_template_index"
    }


    @Inject
    lateinit var factory : ViewModelProvider.Factory
    lateinit var viewModel: TemplateSetEditViewModel

    private lateinit var binding : ActivityTemplateSetEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityTemplateSetEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory)
            .get(DefaultTemplateSetEditViewModel::class.java)


        binding.apply {

            setSupportActionBar(toolbar)

            activityTemplateSetFailure.setOnCheckedChangeListener { _, b ->
                run {
                    activityTemplateSetEditMinRep.isEnabled = !b
                    activityTemplateSetEditMaxRep.isEnabled = !b
                }
            }
        }



        viewModel.let {
            it.fetchStateLiveData.observe(this, Observer {  })
            it.loadTemplateSetLiveData.observe(this, Observer { populateTemplateSet(it) })
            it.commitLiveData.observe(this, Observer { templateSetCommitted(it) })
        }



        if (savedInstanceState == null)
            viewModel.loadTemplate(getWorkoutIndex(),
                getExerciseIndex(), getTemplateIndex())

    }

    private fun templateSetCommitted(result: TemplateSetCommitResult) {
        val intentData = Intent()
        intentData.putExtra(EXERCISE_INDEX, result.exerciseIndex)
        intentData.putExtra(WORKOUT_INDEX, result.workoutIndex)
        intentData.putExtra(TEMPLATE_INDEX, result.templateSetIndex)
        setResult(Activity.RESULT_OK, intentData)
        finish()
    }

    private fun commitTemplateSet() {


        binding.apply {
            if (!TimeFormatUtil.validateTimerFormat(
                activityTemplateSetEditRestDurationEdit.text.toString())) {
                activityTemplateSetEditRestDuration.error = getString(R.string.rest_time_format_helper_text)
                return
            }

            if (!activityTemplateSetFailure.isChecked
                && activityTemplateSetEditMinRepEdit.text.toString().isEmpty()) {
                activityTemplateSetEditMinRep.error = getString(R.string.min_rep_is_required)
                return
            }

            if (!activityTemplateSetFailure.isChecked
                && activityTemplateSetEditMaxRepEdit.text.toString().isBlank()) {
                activityTemplateSetEditMaxRep.error = getString(R.string.max_rep_is_required)
                return
            }

            val weight = if (!activityTemplateSetFailure.isChecked)
                activityTemplateSetEditWeightEdit.text.toString().toFloatOrNull() else null

            val minRep = if (!activityTemplateSetFailure.isChecked)
                activityTemplateSetEditMinRepEdit.text.toString().toIntOrNull() else null

            val maxRep = if (!activityTemplateSetFailure.isChecked)
                activityTemplateSetEditMaxRepEdit.text.toString().toIntOrNull() else null
            val restTime = TimeFormatUtil
                .stringTimeToSeconds(activityTemplateSetEditRestDurationEdit.text.toString())

            val upToFailure = false

            viewModel.commit(getWorkoutIndex(), getExerciseIndex(), getTemplateIndex(), minRep,
                maxRep, weight, 0f, 0, restTime
            )

        }





    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
            R.id.activity_template_set_edit_save -> {
                commitTemplateSet()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_template_set_edit_activity, menu)
        return true
    }

    private fun populateTemplateSet(result: TemplateSetEditLoadResult) {

        val entity = result.entity

        binding.apply {
            if (entity.weight != null) {
                activityTemplateSetEditWeightEdit.setText(
                    String.format(
                        "%.2f",
                        entity.weight
                    )
                )
            } else {
                activityTemplateSetEditWeightEdit.setText("")
            }

            if (entity.minReps != null) {
                activityTemplateSetEditMinRepEdit.setText(entity.minReps.toString())
            } else {
                activityTemplateSetEditMinRepEdit.setText("")
            }

            if (entity.reps != null) {
                activityTemplateSetEditMaxRepEdit.setText(entity.reps.toString())
            } else {
                activityTemplateSetEditMaxRepEdit.setText("")
            }

            activityTemplateSetEditRestDurationEdit
                .setText(TimeFormatUtil.secondsToString(entity.restTimeSeconds.toLong()))

        }


    }

    private fun getWorkoutIndex() : Int {
        return intent.getIntExtra(WORKOUT_INDEX, -1)
    }

    private fun getExerciseIndex() : Int {
        return intent.getIntExtra(EXERCISE_INDEX, -1)
    }

    private fun getTemplateIndex() : Int {
        return intent.getIntExtra(TEMPLATE_INDEX, -1)
    }

}