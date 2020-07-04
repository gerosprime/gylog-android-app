package com.gerosprime.gylog.ui.workouts.session

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.base.utils.TimeFormatUtil
import com.gerosprime.gylog.models.workouts.runningsession.create.WorkoutSessionCreationResult
import com.gerosprime.gylog.models.workouts.runningsession.discard.RunningWorkoutSessionDiscardResult
import com.gerosprime.gylog.models.workouts.runningsession.finalizer.FinalizedRunningSessionResult
import com.gerosprime.gylog.models.workouts.runningsession.load.WorkoutSessionCacheLoadResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.add.AddPerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.edit.ClearPerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.edit.EditPerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.RemoveWorkoutSessionSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.UnflagRemovePerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.save.WorkoutSessionSaveResult
import com.gerosprime.gylog.ui.workouts.R
import com.gerosprime.gylog.ui.workouts.databinding.ActivityWorkoutSessionBinding
import com.gerosprime.gylog.ui.workouts.history.WorkoutExerciseHistoryFragment
import com.gerosprime.gylog.ui.workouts.session.WorkoutSessionActivity.DialogTag.SESSION_INFO
import com.gerosprime.gylog.ui.workouts.session.WorkoutSessionActivity.Extras.WORKOUT_RECORD_ID
import com.gerosprime.gylog.ui.workouts.session.WorkoutSessionActivity.States.RESUME_WORKOUT
import com.gerosprime.gylog.ui.workouts.session.adapters.exercises.*
import com.gerosprime.gylog.ui.workouts.session.info.SessionInfoDialogFragment
import dagger.android.AndroidInjection
import java.util.*
import javax.inject.Inject


class WorkoutSessionActivity : AppCompatActivity(),
    PerformedSetEditDialogFragment.SetEditListener, SessionInfoDialogFragment.ConfirmClickListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: WorkoutSessionViewModel

    private lateinit var binding : ActivityWorkoutSessionBinding

    object DialogTag {
        const val SESSION_INFO = "dialog_session_info"
    }

    object States {
        const val RESUME_WORKOUT = "state_resume_workout"
    }

    object Extras {
        const val WORKOUT_RECORD_ID = "extra_workout_record_id"
    }

    // Adapter listener implementations

    private val exerciseClickListener = object : OnItemClickListener<WorkoutExerciseClick> {
        override fun onItemClicked(item: WorkoutExerciseClick) {
            val historyFragment =
                WorkoutExerciseHistoryFragment.createInstance(getWorkoutRecordID(),
                    item.exerciseId)
            historyFragment.show(supportFragmentManager, "")
        }
    }

    private val setItemClick = object : OnItemClickListener<PerformedSetClick> {
        override fun onItemClicked(item: PerformedSetClick) {
            if (!item.performedSet.flagRemoved) {
                showEditSetDialog(item)
            } else {
                viewModel.unRemoveTemplateSet(item.exerciseIndex, item.setIndex)
            }
        }
    }

    private val addClickListener = object : OnItemClickListener<AddPerformSetClick> {
        override fun onItemClicked(item: AddPerformSetClick) {
            viewModel.addSet(item.exerciseIndex)
        }
    }

    private val removeItemClick = object : OnItemClickListener<RemovePerformSetClick> {
        override fun onItemClicked(item: RemovePerformSetClick) {
            viewModel.removeSet(item.exerciseIndex, item.setIndex)
        }
    }

    private val unRemoveItemClick = object : OnItemClickListener<UnRemovePerformSetClick> {
        override fun onItemClicked(item: UnRemovePerformSetClick) {
            viewModel.unRemoveTemplateSet(item.exerciseIndex, item.setIndex)
        }
    }

    private val observerAdd = Observer<AddPerformedSetResult> {
        exerciseSetAdded(it)
    }

    private val observerEdit = Observer<EditPerformedSetResult> {
        exerciseSetCompleted(it)
    }

    private val observerClear = Observer<ClearPerformedSetResult> {
        exerciseSetClear(it)
    }

    private val observerUnRemove = Observer<UnflagRemovePerformedSetResult> {
        exerciseSetUnRemoved(it)
    }

    private val observerRemove = Observer<RemoveWorkoutSessionSetResult> {
        exerciseSetRemoved(it)
    }

    private val observerRest = Observer<String> {
        updateTimerLabel(it)
    }

    private val observerDiscard = Observer<RunningWorkoutSessionDiscardResult> {
        setResult(Activity.RESULT_CANCELED)

        val serviceIntent = Intent(this, WorkoutSessionService::class.java)
        stopService(serviceIntent)

        finish()
    }

    private val observerFinalize = Observer<FinalizedRunningSessionResult> {
        setResult(Activity.RESULT_OK)
        finish()
    }

    private val observerSave = Observer<WorkoutSessionSaveResult> {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutSessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.activityWorkoutSessionRestTimerRest.setOnClickListener {
            skipRest()
        }

        viewModel = ViewModelProvider(this, factory)
            .get(DefaultWorkoutSessionViewModel::class.java)

        binding.activityWorkoutSessionExercises.adapter = PerformedExerciseAdapter(
            listOf(),
            exerciseClickListener, setItemClick,
            addClickListener, removeItemClick, unRemoveItemClick)

        showRestimerIfTimerRunning()

        if (savedInstanceState == null) {

            if (shouldResumeWorkout()) {
                viewModel.workoutSessionLoadLiveData.observe(this, Observer {
                    populateLoadedSession(it)
                })
                viewModel.resumeWorkoutSession(getWorkoutRecordID())
            } else {
                viewModel.workoutSessionCreateLiveData.observe(this, Observer {
                    populateCreatedSession(it)
                })
                viewModel.createWorkoutSession(getWorkoutRecordID())

                startWorkoutService(getWorkoutRecordID())
            }

        } else {
            viewModel.workoutSessionLoadLiveData.observe(this, Observer {
                populateLoadedSession(it)
            })
            viewModel.resumeWorkoutSession(getWorkoutRecordID())
        }

        viewModel.addSetLiveData
            .observe(this, observerAdd)

        viewModel.removeSetLiveData
            .observe(this, observerRemove)

        viewModel.completeSetLiveData.observe(this, observerEdit)
        viewModel.clearSetLiveData.observe(this, observerClear)

        viewModel.restTimerLiveData.observe(this, observerRest)

        viewModel.unFlagRemoveSetLiveData.observe(this, observerUnRemove)

        viewModel.discardSessionLiveData.observe(this, observerDiscard)

        viewModel.finalizedSessionLiveData.observe(this, observerFinalize)
        viewModel.savedSessionLiveData.observe(this, observerSave)

    }

    private fun showRestimerIfTimerRunning() {
        when (viewModel.isResting()) {
            true -> {
                binding.activityWorkoutSessionTimerContainer.visibility = View.VISIBLE
                updateTimerLabel(viewModel.restTimerLiveData.value!!)
            }
            else -> {
                binding.activityWorkoutSessionTimerContainer.visibility = View.GONE
                updateTimerLabel("")
            }
        }
    }

    private fun startWorkoutService(workoutId : Long) {
        val serviceIntent = Intent(this, WorkoutSessionService::class.java)
        serviceIntent.putExtra(WorkoutSessionService.Extras.COMMAND, WorkoutSessionService.CommandValues.NORMAL_START)
        serviceIntent.putExtra(WorkoutSessionService.Extras.COMMAND_ARGUMENT, workoutId)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
    }

    private fun startWorkoutServiceTimer(restDuration : Int) {
        val serviceIntent = Intent(this, WorkoutSessionService::class.java)
        serviceIntent.putExtra(WorkoutSessionService.Extras.COMMAND, WorkoutSessionService.CommandValues.REST)
        serviceIntent.putExtra(WorkoutSessionService.Extras.COMMAND_ARGUMENT, restDuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            android.R.id.home -> {
                viewModel.discardWorkoutSession()
            }
            R.id.activity_workout_session_finish -> {

                showWorkoutSessionFinalizeDialog()
                // viewModel.finishWorkoutSession()
            }
            R.id.activity_workout_session_info -> {
                showSessionInfoDialog()
            }
        }
        return true
    }

    private fun showSessionInfoDialog() {
        SessionInfoDialogFragment.createInstance(false)
            .show(supportFragmentManager, SESSION_INFO)
    }

    private fun showWorkoutSessionFinalizeDialog() {
        SessionInfoDialogFragment.createInstance(true)
            .show(supportFragmentManager, SESSION_INFO)
    }

    private fun exerciseSetUnRemoved(result: UnflagRemovePerformedSetResult) {

        val adapter = binding.activityWorkoutSessionExercises.adapter
        adapter?.notifyItemChanged(result.exercisePerformedIndex, result)
    }

    private fun exerciseSetRemoved(result: RemoveWorkoutSessionSetResult) {

        val adapter = binding.activityWorkoutSessionExercises.adapter
        adapter!!.notifyItemChanged(result.exercisePerformedIndex, result)
    }

    private fun exerciseSetClear(result: ClearPerformedSetResult) {

        val adapter = binding.activityWorkoutSessionExercises.adapter
        adapter?.notifyItemChanged(result.exercisePerformedIndex, result)

    }

    private fun exerciseSetCompleted(result: EditPerformedSetResult) {

        val adapter = binding.activityWorkoutSessionExercises.adapter
        adapter?.notifyItemChanged(result.exercisePerformedIndex, result)

        startWorkoutServiceTimer(result.performedSet.restTimeSeconds)
        rest(result.performedSet.restTimeSeconds)

        // viewModel.completeSetMutableLiveData.value = null

    }

    private fun rest(duration : Int) {

        val timeString = TimeFormatUtil.secondsToString(duration.toLong())

        binding.activityWorkoutSessionRestTitle.text = getString(R.string.time_rest_format, timeString)
        binding.activityWorkoutSessionRestRunning.text = timeString
        binding.activityWorkoutSessionTimerContainer.visibility = View.VISIBLE

        viewModel.restTimerLiveData.observe(this, observerRest)
        viewModel.restSet(duration)
    }

    private fun skipRest() {
        viewModel.cancelRest()
        binding.activityWorkoutSessionRestTitle.text = ""
        binding.activityWorkoutSessionRestRunning.text = ""
        binding.activityWorkoutSessionTimerContainer.visibility = View.GONE

        viewModel.restTimerLiveData.removeObserver(observerRest)
    }

    private fun updateTimerLabel(time: String) {

        if (time.isEmpty()) {
            binding.activityWorkoutSessionTimerContainer.visibility = View.GONE
        } else {
            binding.activityWorkoutSessionRestRunning.text = time
        }

    }

    private fun exerciseSetAdded(result: AddPerformedSetResult) {

        val adapter = binding.activityWorkoutSessionExercises.adapter
        adapter?.notifyItemChanged(result.exercisePerformedIndex, result)
    }

    private fun showEditSetDialog(item: PerformedSetClick) {
        val dialog = PerformedSetEditDialogFragment
            .createInstance(item.exerciseIndex, item.performedExercise.name,
                item.setIndex, item.performedSet)
        dialog.show(supportFragmentManager, "")
    }

    override fun onSetEdit(exerciseIndex: Int, setIndex: Int, weight: Float?,
                           reps: Int?, datePerformed : Date?) {
        viewModel.editSet(exerciseIndex, setIndex, reps, weight, datePerformed)
    }

    private fun populateLoadedSession(result: WorkoutSessionCacheLoadResult) {
        val prePerformedExercises = result.prePerformedExercises

        val adapter = binding.activityWorkoutSessionExercises.adapter
                as PerformedExerciseAdapter
        adapter.performedExercises = prePerformedExercises
        adapter.notifyItemRangeChanged(0, prePerformedExercises.size)
    }

    private fun populateCreatedSession(result: WorkoutSessionCreationResult) {
        val prePerformedExercises = result.prePerformedExercises

        val adapter = binding.activityWorkoutSessionExercises.adapter
                as PerformedExerciseAdapter
        adapter.performedExercises = prePerformedExercises
        adapter.notifyItemMoved(0, prePerformedExercises.size)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(WORKOUT_RECORD_ID, getWorkoutRecordID())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_workout_session, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun getStateWorkoutRecordID(savedInstanceState: Bundle?)
            = savedInstanceState!!.getLong(WORKOUT_RECORD_ID, -1)

    private fun getWorkoutRecordID() : Long
            = intent.getLongExtra(WORKOUT_RECORD_ID, -1)

    private fun shouldResumeWorkout() : Boolean
            = intent.getBooleanExtra(RESUME_WORKOUT, false)

    override fun onBackPressed() {
        viewModel.discardWorkoutSession()
    }

    override fun onConfirmed(source: SessionInfoDialogFragment) {
        viewModel.finishWorkoutSession()
    }
}