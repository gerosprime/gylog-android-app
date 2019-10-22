package com.gerosprime.gylog.ui.programs.workouts

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import com.gerosprime.gylog.ui.programs.R
import com.google.android.material.textfield.TextInputLayout

class AddWorkoutDialogFragment : DialogFragment() {

    lateinit var nameEditTextLayout : TextInputLayout
    lateinit var descriptionEditTextLayout : TextInputLayout

    private var listener : Listener? = null

    lateinit var cancelButton : Button
    lateinit var createButton : Button

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is Listener)
            listener = context
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(context!!)
            .setTitle(R.string.add_workout)
            .setPositiveButton("Create") { dialogInterface, i -> }
            .setNegativeButton("Cancel") { dialogInterface, i -> }
            .setView(R.layout.fragment_add_workout_dialog)
            .create()

    }

    override fun onStart() {
        super.onStart()

        val alertDialog = dialog as AlertDialog

        val createButton : Button? = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
        // val cancelButton : Button? = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)

        val nameInputLayout : TextInputLayout?
                =  alertDialog.findViewById(R.id.fragment_add_workout_dialog_name_layout)
        val descriptionInputLayout : TextInputLayout?
                =  alertDialog.findViewById(R.id.fragment_add_workout_dialog_description_layout)
        createButton!!.setOnClickListener {

            kotlin.run {

                val name = nameInputLayout?.editText!!.text.toString()
                val desc = descriptionInputLayout?.editText!!.text.toString()
                if (name.isEmpty()) {
                    nameInputLayout.error = context!!.getString(R.string.workout_name_is_required)
                } else {
                    defineWorkout(name, desc)
                    dismiss()
                }

            }

        }
    }

    private fun defineWorkout(name : String, description : String) {
        listener!!.workoutNameDescriptionDefined(WorkoutEntity(name = name,
            description = description, exercises = arrayListOf()))
    }

    interface Listener {
        fun workoutNameDescriptionDefined(newWorkout : WorkoutEntity)
    }

}