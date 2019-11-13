package com.gerosprime.gylog.ui.workouts.session

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.gerosprime.gylog.models.exercises.performedsets.PerformedSetEntity
import com.gerosprime.gylog.ui.workouts.R
import com.gerosprime.gylog.ui.workouts.session.PerformedSetEditDialogFragment.Extras.EXERCISE_INDEX
import com.gerosprime.gylog.ui.workouts.session.PerformedSetEditDialogFragment.Extras.EXERCISE_NAME
import com.gerosprime.gylog.ui.workouts.session.PerformedSetEditDialogFragment.Extras.EXERCISE_SET_INDEX
import com.gerosprime.gylog.ui.workouts.session.PerformedSetEditDialogFragment.Extras.INITIAL_REPS
import com.gerosprime.gylog.ui.workouts.session.PerformedSetEditDialogFragment.Extras.INITIAL_WEIGHT
import com.gerosprime.gylog.ui.workouts.session.PerformedSetEditDialogFragment.Extras.PREVIOUS_REPS
import com.gerosprime.gylog.ui.workouts.session.PerformedSetEditDialogFragment.Extras.PREVIOUS_WEIGHT
import com.gerosprime.gylog.ui.workouts.session.PerformedSetEditDialogFragment.Extras.REPS
import com.gerosprime.gylog.ui.workouts.session.PerformedSetEditDialogFragment.Extras.WEIGHT
import com.google.android.material.textfield.TextInputLayout


class PerformedSetEditDialogFragment : DialogFragment() {

    private object Extras {
        const val EXERCISE_INDEX = "extra_exercise_index"
        const val EXERCISE_NAME = "extra_exercise_name"
        const val EXERCISE_SET_INDEX = "extra_exercise_set_index"
        const val REPS = "extra_reps"
        const val WEIGHT = "extra_weight"
        const val INITIAL_REPS = "extra_initial_reps"
        const val INITIAL_WEIGHT = "extra_initial_weight"
        const val PREVIOUS_REPS = "extra_previous_reps"
        const val PREVIOUS_WEIGHT = "extra_previous_weight"
    }

    companion object Factory {
        fun createInstance(exerciseIndex : Int,
                           exerciseName : String,
                           setIndex : Int,
                           performedSetEntity: PerformedSetEntity)
                : PerformedSetEditDialogFragment {

            val reps: Int? = performedSetEntity.reps
            val weight = performedSetEntity.weight
            val initialReps = performedSetEntity.initialReps
            val initialWeight = performedSetEntity.initialWeight
            val previousReps = performedSetEntity.previousReps
            val previousWeight = performedSetEntity.previousWeight

            val arguments = Bundle()
            arguments.putInt(EXERCISE_INDEX, exerciseIndex)
            arguments.putInt(EXERCISE_SET_INDEX, setIndex)

            if (reps != null)
                arguments.putInt(REPS, reps)
            if (weight != null)
                arguments.putFloat(WEIGHT, weight)

            arguments.putString(EXERCISE_NAME, exerciseName)

            if (initialReps != null)
                arguments.putInt(INITIAL_REPS, initialReps)
            if (initialWeight != null)
                arguments.putFloat(INITIAL_WEIGHT, initialWeight)

            if (previousReps != null)
                arguments.putInt(PREVIOUS_REPS, previousReps)
            if (previousWeight != null)
                arguments.putFloat(PREVIOUS_WEIGHT, previousWeight)

            val instance = PerformedSetEditDialogFragment()
            instance.arguments = arguments

            return instance
        }
    }

    interface SetEditListener {
        fun onSetEdit(exerciseIndex : Int,
                      setIndex : Int,
                      weight : Float?, reps : Int?)
    }

    private lateinit var titleTextView: TextView
    private lateinit var exerciseNameTextView: TextView
    private lateinit var subTitleTextView: TextView

    private lateinit var weightTextInputLayout : TextInputLayout
    private lateinit var repsTextInputLayout : TextInputLayout

    private lateinit var listener : SetEditListener

    private lateinit var setButton : Button
    private lateinit var cancelButton : Button


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SetEditListener)
            listener = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val inflated = inflater.inflate(R.layout.fragment_performed_set_edit,
            container, false)

        weightTextInputLayout = inflated.findViewById(R.id.fragment_performed_set_edit_weight)
        repsTextInputLayout = inflated.findViewById(R.id.fragment_performed_set_edit_reps)

        titleTextView = inflated.findViewById(R.id.fragment_performed_set_dialog_title)
        exerciseNameTextView = inflated
            .findViewById(R.id.fragment_performed_set_dialog_exercisename)
        subTitleTextView = inflated.findViewById(R.id.fragment_performed_set_dialog_subtitle)

        setButton = inflated.findViewById(R.id.fragment_performed_set_edit_dialog_set)
        setButton.setOnClickListener {
            edit()
        }

        cancelButton = inflated.findViewById(R.id.fragment_performed_set_edit_dialog_cancel)
        cancelButton.setOnClickListener { dismiss() }

        return inflated
    }

    private fun edit() {

        var weight : Float? = null
        if (weightTextInputLayout.editText!!.text.isNotEmpty())
            weight = weightTextInputLayout.editText!!.text.toString().toFloat()

        var reps : Int? = null
        if (repsTextInputLayout.editText!!.text.isNotEmpty())
            reps = repsTextInputLayout.editText!!.text.toString().toInt()

        listener.onSetEdit(getExerciseIndex(), getSetIndex(),
            weight, reps)

        dismiss()
    }

    private fun getExerciseIndex() : Int {
        return arguments!!.getInt(EXERCISE_INDEX)
    }

    private fun getSetIndex() : Int {
        return arguments!!.getInt(EXERCISE_SET_INDEX)
    }

    private fun getWeight() : Float? {
        return arguments!!.getFloat(WEIGHT)
    }

    private fun getReps() : Int? {
        return arguments!!.getInt(REPS)
    }

    private fun getPreviousWeight() : Float? {
        return arguments!!.getFloat(PREVIOUS_WEIGHT)
    }

    private fun getPreviousReps() : Int? {
        return arguments!!.getInt(PREVIOUS_REPS)
    }

    private fun getInitialWeight() : Float? {
        return arguments!!.getFloat(INITIAL_WEIGHT)
    }

    private fun getInitialReps() : Int? {
        return arguments!!.getInt(INITIAL_REPS)
    }

    private fun getExerciseName() : String? {
        return arguments!!.getString(EXERCISE_NAME)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleTextView.text = resources.getString(R.string.workout_session_set_number_format,
            getSetIndex() + 1)
        exerciseNameTextView.text = getExerciseName()


        if (getWeight() != null) {
            weightTextInputLayout.editText!!.setText(getWeight().toString())
        }
        if (getReps() != null) {
            repsTextInputLayout.editText!!.setText(getReps().toString())
        }

    }

}