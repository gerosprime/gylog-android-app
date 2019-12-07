package com.gerosprime.gylog.ui.workouts.session.info

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.gerosprime.gylog.models.workouts.runningsession.load.WorkoutSessionInfoLoadResult
import com.gerosprime.gylog.ui.workouts.R
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SessionInfoDialogFragment : DialogFragment() {

    private lateinit var titleTextView : TextView
    private lateinit var subTitleTextView : TextView

    private lateinit var nameTextView : TextView
    private lateinit var durationTextView : TextView
    private lateinit var setsTextView : TextView
    private lateinit var weightTextView : TextView

    private lateinit var dismissButton : Button
    private lateinit var cancelButton : Button
    private lateinit var confirmButton : Button

    @Inject
    lateinit var factory : ViewModelProvider.Factory
    private lateinit var viewmodel : SessionInfoViewModel

    private lateinit var listener : ConfirmClickListener

    object Extras {
        const val CONFIRM_MODE = "extra_confirm_mode"
    }

    companion object {
        fun createInstance(confirmMode : Boolean) : SessionInfoDialogFragment {
            val arguments = Bundle()
            arguments.putBoolean(Extras.CONFIRM_MODE, confirmMode)

            val instance = SessionInfoDialogFragment()
            instance.arguments = arguments

            return instance
        }
    }

    interface ConfirmClickListener {
        fun onConfirmed(source : SessionInfoDialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ConfirmClickListener)
            listener = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewmodel = ViewModelProviders.of(this, factory)
            .get(DefaultSessionInfoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val inflated = inflater.inflate(R.layout.fragment_session_info_dialog, container, false)

        titleTextView = inflated.findViewById(R.id.fragment_session_info_dialog_title)
        subTitleTextView = inflated.findViewById(R.id.fragment_session_info_dialog_subtitle)

        nameTextView = inflated.findViewById(R.id.fragment_session_info_name_value)
        durationTextView = inflated.findViewById(R.id.fragment_session_info_duration_value)
        setsTextView = inflated.findViewById(R.id.fragment_session_info_sets_value)
        weightTextView = inflated.findViewById(R.id.fragment_session_info_total_weight_value)

        cancelButton = inflated.findViewById(R.id.fragment_session_info_dialog_cancel)
        cancelButton.setOnClickListener {
            viewmodel.stopSessionDurationStopwatch()
            dismiss()
        }
        confirmButton = inflated.findViewById(R.id.fragment_session_info_dialog_confirm)
        confirmButton.setOnClickListener {
            listener.onConfirmed(this)
        }
        dismissButton = inflated.findViewById(R.id.fragment_session_info_dialog_dismiss)
        dismissButton.setOnClickListener {
            viewmodel.stopSessionDurationStopwatch()
            dismiss()
        }

        if (isConfirmMode()) {
            subTitleTextView.text = getString(R.string.please_confirm_to_finish_workout)
            cancelButton.visibility = View.VISIBLE
            confirmButton.visibility = View.VISIBLE
            dismissButton.visibility = View.GONE
        } else {
            cancelButton.visibility = View.GONE
            confirmButton.visibility = View.GONE
            dismissButton.visibility = View.VISIBLE
        }

        return inflated
    }

    fun isConfirmMode() : Boolean {
        return arguments!!.getBoolean(Extras.CONFIRM_MODE, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewmodel.sessionInfoStopwatchMLD.observe(this,  Observer {
            durationTextView.text = it.toTimeString()
        })
        viewmodel.sessionInfoMLD.observe(this, Observer {

            populateSessionInfo(it)
            if (savedInstanceState == null)
                viewmodel.startSessionDurationStopwatch(it.started)
        })

        if (savedInstanceState == null)
            viewmodel.loadInfo()

    }

    @SuppressLint("SetTextI18n")
    private fun populateSessionInfo(result : WorkoutSessionInfoLoadResult) {
        titleTextView.text = result.workout.name
        // nameTextView.text = result.workout.name
        setsTextView.text = "${result.setsPerformed} / ${result.setsTotal}"
        weightTextView.text = String.format("%.2f", result.totalWeight)
    }

}