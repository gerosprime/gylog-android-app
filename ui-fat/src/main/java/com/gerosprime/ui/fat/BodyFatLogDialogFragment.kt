package com.gerosprime.ui.fat

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.gerosprime.ui.fat.databinding.FragmentBodyFatLogDialogBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class BodyFatLogDialogFragment : DialogFragment() {


    object Extras {
        const val RECORD_ID = "extra_record_id"
        const val FAT = "extra_fat"
        const val DATE = "extra_date"
        const val NOTE = "extra_notes"
    }

    companion object {
        fun createInstance(recordId : Long?, fatPercentage : Float?,
                           date : Date, notes : String)
                : BodyFatLogDialogFragment {

            val arguments = Bundle()
            if (recordId != null)
                arguments.putLong(Extras.RECORD_ID, recordId)

            if (fatPercentage != null)
                arguments.putFloat(Extras.FAT, fatPercentage)

            arguments.putLong(Extras.DATE, date.time)
            arguments.putString(Extras.NOTE, notes)

            val fragment = BodyFatLogDialogFragment()
            fragment.arguments = arguments

            return fragment
        }
    }


    lateinit var listener : Listener

    private lateinit var dateFormat : DateFormat

    private lateinit var binding : FragmentBodyFatLogDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setTitle(R.string.log_fat)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBodyFatLogDialogBinding.inflate(inflater, container, false)
        val inflated =  inflater.inflate(R.layout.fragment_body_fat_log_dialog,
            container, false)

        binding.fragmentBodyFatLogDialogDate.setOnClickListener {
            showDateSelectorBuilder(getDate().time)
        }

        binding.fragmentBodyFatCancel.setOnClickListener {
            dismiss()
        }

        val createButton : Button = inflated.findViewById(R.id.fragment_body_fat_save)
        createButton.setOnClickListener {

            kotlin.run {
                val weight = binding.fragmentBodyFatLogFat.editText!!.text.toString()
                val notes = binding.fragmentBodyFatLogNotes.editText!!.text.toString()

                defineWorkout(getRecordId(), weight,
                    binding.fragmentBodyFatLogDate.editText!!.text.toString(), notes)
            }

        }

        return inflated
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dateFormat = SimpleDateFormat("MM/dd/yy", Locale.US)
        dateFormat.isLenient = false

        if (savedInstanceState == null) {
            val weight = getWeight()
            if (weight != null)
                binding.fragmentBodyFatLogFat.editText!!.setText(weight.toString())
            else
                binding.fragmentBodyFatLogFat.editText!!.setText("")

            val note = getNote()
            binding.fragmentBodyFatLogNotes.editText!!.setText(note)
            binding.fragmentBodyFatLogDate.editText!!.setText(dateFormat.format(getDate()))

        } else {
            savedInstanceState.getLong(Extras.DATE)
            binding.fragmentBodyFatLogDate.editText!!.setText(dateFormat.format(getDate()))

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // outState.putLong(Extras.DATE,
        //    dateFormat.parse(dateInputLayout.editText!!.text.toString()).time)
    }

    private fun showDateSelectorBuilder(dateMillis : Long) {

        val builder = MaterialDatePicker.Builder.datePicker()

        builder.setSelection(dateMillis)
        builder.setTitleText(R.string.body_fat_date_title)

        val buildDialog = builder.build()
        buildDialog.addOnPositiveButtonClickListener {

            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = buildDialog.selection!!
            binding.fragmentBodyFatLogDate.editText!!.setText(dateFormat.format(calendar.time))
        }

        buildDialog.show(fragmentManager!!, "")
    }


    private fun getWeight() : Float? {
        return if (arguments!!.containsKey(Extras.FAT))
            arguments!!.getFloat(Extras.FAT)
        else
            arguments!!.getFloat(Extras.FAT)

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


        if (weight.isBlank()) {
            binding.fragmentBodyFatLogDate.error = getString(R.string.body_fat_is_required)
            return
        }

        if (date.isBlank()) {
            binding.fragmentBodyFatLogDate.error = getString(R.string.body_fat_is_required)
            return
        }

        try {
            dateFormat.parse(date)
        } catch (e : ParseException) {
            binding.fragmentBodyFatLogDate.error = getString(R.string.date_is_invalid)
            return
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