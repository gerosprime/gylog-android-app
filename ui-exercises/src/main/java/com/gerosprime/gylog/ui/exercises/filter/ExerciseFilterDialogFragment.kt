package com.gerosprime.gylog.ui.exercises.filter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import androidx.fragment.app.DialogFragment
import com.gerosprime.gylog.models.muscle.MuscleEnum
import com.gerosprime.gylog.ui.exercises.R
import com.google.android.material.chip.Chip

class ExerciseFilterDialogFragment : DialogFragment() {

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

    private lateinit var buttonCancel : Button
    private lateinit var buttonConfirm : Button

    private val selectedMuscles : ArrayList<MuscleEnum> = arrayListOf()

    interface Listener {
        fun onFilterConfigured(selectedMuscles : ArrayList<MuscleEnum>)
    }

    lateinit var listener : Listener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Listener) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val inflated = inflater.inflate(R.layout.fragment_exercise_filter_dialog, container, false)

        buttonCancel = inflated.findViewById(R.id.fragment_exercise_filter_cancel)
        buttonCancel.setOnClickListener { dismiss() }

        buttonConfirm = inflated.findViewById(R.id.fragment_exercise_filter_confirm)
        buttonConfirm.setOnClickListener { filter() }

        chestChip = inflated.findViewById(R.id.fragment_exercise_filter_muscle_chest)
        chestChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.CHEST] = chestChip
        chipMuscleMaps[chestChip] = MuscleEnum.CHEST

        chestUpperChip = inflated.findViewById(R.id.fragment_exercise_filter_muscle_chest_upper)
        chestUpperChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.CHEST_UPPER] = chestUpperChip
        chipMuscleMaps[chestUpperChip] = MuscleEnum.CHEST_UPPER

        chestLowerChip = inflated.findViewById(R.id.fragment_exercise_filter_muscle_chest_lower)
        chestLowerChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.CHEST_LOWER] = chestLowerChip
        chipMuscleMaps[chestLowerChip] = MuscleEnum.CHEST_LOWER

        tricepsChip = inflated.findViewById(R.id.fragment_exercise_filter_muscle_triceps)
        tricepsChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.TRICEPS] = tricepsChip
        chipMuscleMaps[tricepsChip] = MuscleEnum.TRICEPS

        bicepsChip = inflated.findViewById(R.id.fragment_exercise_filter_muscle_biceps)
        bicepsChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.BICEPS] = bicepsChip
        chipMuscleMaps[bicepsChip] = MuscleEnum.BICEPS

        upperBackChip = inflated.findViewById(R.id.fragment_exercise_filter_muscle_back_upper)
        upperBackChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.BACK_UPPER] = upperBackChip
        chipMuscleMaps[upperBackChip] = MuscleEnum.BACK_UPPER

        lowerBackChip = inflated.findViewById(R.id.fragment_exercise_filter_muscle_back_lower)
        lowerBackChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.BACK_LOWER] = lowerBackChip
        chipMuscleMaps[lowerBackChip] = MuscleEnum.BACK_LOWER

        hamstringsChip = inflated.findViewById(R.id.fragment_exercise_filter_muscle_hamstring)
        hamstringsChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.HAMSTRINGS] = hamstringsChip
        chipMuscleMaps[hamstringsChip] = MuscleEnum.HAMSTRINGS

        forearmsChip = inflated.findViewById(R.id.fragment_exercise_filter_muscle_forearms)
        forearmsChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.FOREARMS] = forearmsChip
        chipMuscleMaps[forearmsChip] = MuscleEnum.FOREARMS

        shoulderSideChip = inflated.findViewById(R.id.fragment_exercise_filter_muscle_shoulder_side)
        shoulderSideChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.SHOULDER_SIDE] = shoulderSideChip
        chipMuscleMaps[shoulderSideChip] = MuscleEnum.SHOULDER_SIDE

        shoulderFrontChip = inflated.findViewById(R.id.fragment_exercise_filter_muscle_shoulder_front)
        shoulderFrontChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.SHOULDER_FRONT] = shoulderFrontChip
        chipMuscleMaps[shoulderFrontChip] = MuscleEnum.SHOULDER_FRONT

        shoulderBackChip = inflated.findViewById(R.id.fragment_exercise_filter_muscle_shoulder_back)
        shoulderBackChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.SHOULDER_BACK] = shoulderBackChip
        chipMuscleMaps[shoulderBackChip] = MuscleEnum.SHOULDER_BACK

        trapsChip = inflated.findViewById(R.id.fragment_exercise_filter_muscle_traps)
        trapsChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.TRAPS] = trapsChip
        chipMuscleMaps[trapsChip] = MuscleEnum.TRAPS

        absChip = inflated.findViewById(R.id.fragment_exercise_filter_muscle_abs)
        absChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.ABS] = absChip
        chipMuscleMaps[absChip] = MuscleEnum.ABS

        quadsChip = inflated.findViewById(R.id.fragment_exercise_filter_muscle_quads)
        quadsChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.QUADS] = quadsChip
        chipMuscleMaps[quadsChip] = MuscleEnum.QUADS

        calvesChip = inflated.findViewById(R.id.fragment_exercise_filter_muscle_calves)
        calvesChip.setOnCheckedChangeListener { compoundButton, _ ->
            run {
                muscleCheckEvent(compoundButton)
            }
        }
        muscleMaps[MuscleEnum.CALVES] = calvesChip
        chipMuscleMaps[calvesChip] = MuscleEnum.CALVES
        
        return inflated
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        for (chipMuscleMap in chipMuscleMaps.entries) {
            if (chipMuscleMap.key.isChecked) 
                selectedMuscles.add(chipMuscleMap.value)
        }
    }

    private fun filter() {
        listener.onFilterConfigured(selectedMuscles)
        dismiss()
    }

    private fun muscleCheckEvent(cmp : CompoundButton) {

        if (cmp.isChecked) {
            selectedMuscles.add(chipMuscleMaps[cmp]!!)
        } else {
            selectedMuscles.remove(chipMuscleMaps[cmp]!!)
        }

    }

}