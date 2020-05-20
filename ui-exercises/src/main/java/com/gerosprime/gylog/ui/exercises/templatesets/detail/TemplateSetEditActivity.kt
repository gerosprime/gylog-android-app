package com.gerosprime.gylog.ui.exercises.templatesets.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gerosprime.gylog.base.utils.TimeFormatUtil
import com.gerosprime.gylog.models.exercises.templatesets.single.TemplateSetEditLoadResult
import com.gerosprime.gylog.models.exercises.templatesets.single.commit.TemplateSetCommitResult
import com.gerosprime.gylog.ui.exercises.R
import com.gerosprime.gylog.ui.exercises.templatesets.detail.TemplateSetEditActivity.Extras.EXERCISE_INDEX
import com.gerosprime.gylog.ui.exercises.templatesets.detail.TemplateSetEditActivity.Extras.TEMPLATE_INDEX
import com.gerosprime.gylog.ui.exercises.templatesets.detail.TemplateSetEditActivity.Extras.WORKOUT_INDEX
import com.google.android.material.textfield.TextInputLayout
import dagger.android.AndroidInjection
import javax.inject.Inject

class TemplateSetEditActivity : AppCompatActivity() {

    object Extras {
        const val WORKOUT_INDEX = "extra_workout_index"
        const val EXERCISE_INDEX = "extra_exercise_index"
        const val TEMPLATE_INDEX = "extra_template_index"
    }

    object Tags {
        const val REST_DIALOG = "tag_rest_dialog"
    }

    @Inject
    lateinit var factory : ViewModelProvider.Factory
    lateinit var viewModel: TemplateSetEditViewModel

    private lateinit var toolbar: Toolbar


    private lateinit var weightTextInputLayout: TextInputLayout
    private lateinit var minRepTextInputLayout: TextInputLayout
    private lateinit var maxRepTextInputLayout: TextInputLayout
    private lateinit var restTimeTextInputLayout: TextInputLayout

    private lateinit var failureCheckBox : CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_template_set_edit)

        viewModel = ViewModelProvider(this, factory)
            .get(DefaultTemplateSetEditViewModel::class.java)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        weightTextInputLayout = findViewById(R.id.activity_template_set_edit_weight)
        minRepTextInputLayout = findViewById(R.id.activity_template_set_edit_min_rep)
        maxRepTextInputLayout = findViewById(R.id.activity_template_set_edit_max_rep)
        restTimeTextInputLayout = findViewById(R.id.activity_template_set_edit_rest_duration)

        failureCheckBox = findViewById(R.id.activity_template_set_failure)
        failureCheckBox.setOnCheckedChangeListener { _, b ->
            run {
                minRepTextInputLayout.isEnabled = !b
                maxRepTextInputLayout.isEnabled = !b
            }
        }


        viewModel.fetchStateMLD.observe(this, Observer {  })
        viewModel.loadTemplateSetMLD.observe(this, Observer { populateTemplateSet(it) })
        viewModel.commitMLD.observe(this, Observer { templateSetCommitted(it) })

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

        if (!TimeFormatUtil.validateTimerFormat(
                restTimeTextInputLayout.editText!!.text.toString())) {
            restTimeTextInputLayout.error = getString(R.string.rest_time_format_helper_text)
            return
        }

//        if (weightTextInputLayout.editText!!.text.isBlank()) {
//            weightTextInputLayout.error = getString(R.string.weight_is_required)
//            return
//        }

         if (!failureCheckBox.isChecked && minRepTextInputLayout.editText!!.text.isBlank()) {
            minRepTextInputLayout.editText!!.error = getString(R.string.min_rep_is_required)
            return
         }

        if (!failureCheckBox.isChecked && maxRepTextInputLayout.editText!!.text.isBlank()) {
            maxRepTextInputLayout.editText!!.error = getString(R.string.max_rep_is_required)
            return
        }

        val weight = if (!failureCheckBox.isChecked)
            weightTextInputLayout.editText!!.text.toString().toFloatOrNull() else null
        val minRep = if (!failureCheckBox.isChecked)
            minRepTextInputLayout.editText!!.text.toString().toIntOrNull() else null
        val maxRep = if (!failureCheckBox.isChecked)
            maxRepTextInputLayout.editText!!.text.toString().toIntOrNull() else null
        val restTime = TimeFormatUtil
            .stringTimeToSeconds(restTimeTextInputLayout.editText!!.text.toString())

        val upToFailure = false

        viewModel.commit(getWorkoutIndex(), getExerciseIndex(), getTemplateIndex(), minRep,
            maxRep, weight, 0f, 0, restTime
            )

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

    private fun populateTemplateSet(result: TemplateSetEditLoadResult?) {

        val entity = result!!.entity

        if (entity.weight != null) {
            weightTextInputLayout.editText!!.setText(
                String.format(
                    "%.2f",
                    entity.weight
                )
            )
        } else {
            weightTextInputLayout.editText!!.setText("")
        }

        if (entity.minReps != null) {
            minRepTextInputLayout.editText!!.setText(entity.minReps.toString())
        } else {
            minRepTextInputLayout.editText!!.setText("")
        }

        if (entity.reps != null) {
            maxRepTextInputLayout.editText!!.setText(entity.reps.toString())
        } else {
            maxRepTextInputLayout.editText!!.setText("")
        }

        restTimeTextInputLayout.editText!!
            .setText(TimeFormatUtil.secondsToString(entity.restTimeSeconds.toLong()))
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