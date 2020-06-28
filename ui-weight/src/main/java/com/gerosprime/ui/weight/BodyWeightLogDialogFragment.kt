package com.gerosprime.ui.weight

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.gerosprime.ui.weight.databinding.FragmentBodyWeightLogDialogBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class BodyWeightLogDialogFragment : DialogFragment() {


    object Extras {
        const val RECORD_ID = "extra_record_id"
        const val WEIGHT = "extra_weight"
        const val DATE = "extra_date"
        const val NOTE = "extra_notes"
    }

    companion object {
        fun createInstance(recordId : Long?, weight : Float?,
                           date : Date, notes : String)
                : BodyWeightLogDialogFragment {

            val arguments = Bundle()
            if (recordId != null)
                arguments.putLong(Extras.RECORD_ID, recordId)

            if (weight != null)
                arguments.putFloat(Extras.WEIGHT, weight)

            arguments.putLong(Extras.DATE, date.time)
            arguments.putString(Extras.NOTE, notes)

            val fragment = BodyWeightLogDialogFragment()
            fragment.arguments = arguments

            return fragment
        }
    }


    lateinit var listener : Listener

    private lateinit var dateFormat : DateFormat

    private lateinit var binding : FragmentBodyWeightLogDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setTitle(R.string.log_weight)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBodyWeightLogDialogBinding.inflate(inflater, container, false)

        binding.run {
            fragmentBodyWeightLogDialogDate.setOnClickListener {
                showDateSelectorBuilder(getDate().time)
            }

            fragmentBodyWeightCancel.setOnClickListener {
                dismiss()
            }

            fragmentBodyWeightSave.setOnClickListener {
                kotlin.run {
                    val weight = fragmentBodyWeightLogWeightEdit.text.toString()
                    val notes = fragmentBodyWeightLogWeightNotes.text.toString()

                    defineWorkout(getRecordId(), weight,
                        fragmentBodyWeightLogDateEdit.text.toString(), notes)
                }
            }
        }

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dateFormat = SimpleDateFormat("MM/dd/yy", Locale.US)
        dateFormat.isLenient = false

        if (savedInstanceState == null) {

            binding.run {
                val weight = getWeight()
                if (weight != null)
                    fragmentBodyWeightLogWeightEdit.setText(weight.toString())
                else
                    fragmentBodyWeightLogWeightEdit.setText("")

                val note = getNote()
                fragmentBodyWeightLogWeightNotes.setText(note)
                fragmentBodyWeightLogDateEdit.setText(dateFormat.format(getDate()))
            }


        } else {
            savedInstanceState.getLong(Extras.DATE)
            binding.run {
                fragmentBodyWeightLogDateEdit.setText(dateFormat.format(getDate()))
            }
        }
    }

    private fun showDateSelectorBuilder(dateMillis : Long) {

        val builder = MaterialDatePicker.Builder.datePicker()

        builder.setSelection(dateMillis)
        builder.setTitleText(R.string.body_weight_date_title)

        val buildDialog = builder.build()
        buildDialog.addOnPositiveButtonClickListener {

            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = buildDialog.selection!!
            binding.fragmentBodyWeightLogDateEdit.setText(dateFormat.format(calendar.time))
        }

        buildDialog.show(childFragmentManager, "")
    }


    private fun getWeight() : Float? {
        return if (arguments!!.containsKey(Extras.WEIGHT))
            arguments!!.getFloat(Extras.WEIGHT)
        else
            arguments!!.getFloat(Extras.WEIGHT)

    }

    private fun getNote() : String {
        return arguments!!.getString(Extras.NOTE)!!
    }

    private fun getDate() : Date {
        return if (arguments!!.containsKey(Extras.DATE))
                Date(arguments!!.getLong(Extras.DATE))
            else Date()
    }


    private fun getRecordId() : Long? {
        return if (arguments!!.containsKey(Extras.RECORD_ID))
                arguments!!.getLong(Extras.RECORD_ID, -1)
            else null
    }

    private fun defineWorkout(recordId : Long?, weight : String,
                              date : String, notes : String) {

        binding.run {
            if (weight.isBlank()) {
                fragmentBodyWeightLogWeight.error = getString(R.string.body_weight_is_required)
                return
            }

            if (date.isBlank()) {
                fragmentBodyWeightLogDate.error = getString(R.string.body_weight_is_required)
                return
            }

            try {
                dateFormat.parse(date)
            } catch (e : ParseException) {
                fragmentBodyWeightLogDate.error = getString(R.string.date_is_invalid)
                return
            }
        }



        listener.bodyWeightSet(recordId, weight.toFloat(),
            dateFormat.parse(date), notes)

        dismiss()
    }

    interface Listener {
        fun bodyWeightSet(recordId : Long?, weight : Float,
                          date : Date, notes : String)
    }


}