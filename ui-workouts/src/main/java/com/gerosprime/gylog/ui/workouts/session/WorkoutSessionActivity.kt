package com.gerosprime.gylog.ui.workouts.session

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.base.utils.TimeFormatUtil
import com.gerosprime.gylog.models.workouts.runningsession.create.WorkoutSessionCreationResult
import com.gerosprime.gylog.models.workouts.runningsession.discard.RunningWorkoutSessionDiscardResult
import com.gerosprime.gylog.models.workouts.runningsession.finalizer.FinalizedRunningSessionResult
import com.gerosprime.gylog.models.workouts.runningsession.load.WorkoutSessionCacheLoadResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.add.AddPerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.edit.EditPerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.RemoveWorkoutSessionSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.UnflagRemovePerformedSetResult
import com.gerosprime.gylog.ui.workouts.R
import com.gerosprime.gylog.ui.workouts.session.WorkoutSessionActivity.Extras.WORKOUT_RECORD_ID
import com.gerosprime.gylog.ui.workouts.session.WorkoutSessionActivity.States.RESUME_WORKOUT
import com.gerosprime.gylog.ui.workouts.session.adapters.exercises.*
import dagger.android.AndroidInjection
import javax.inject.Inject


class WorkoutSessionActivity : AppCompatActivity(),
    PerformedSetEditDialogFragment.SetEditListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: WorkoutSessionViewModel

    private lateinit var toolbar : Toolbar

    private lateinit var exercisesRecyclerView: RecyclerView

    private lateinit var timerContainer : View
    private lateinit var totalRestTimer : TextView
    private lateinit var runningRestTimer : TextView
    private lateinit var skipTimerButton : ImageButton
    private lateinit var addTimerButton : ImageButton
    private lateinit var minusTimerButton : ImageButton


    object States {
        const val RESUME_WORKOUT = "state_resume_workout"
    }

    object Extras {
        const val WORKOUT_RECORD_ID = "extra_workout_record_id"
    }

    // Adapter listener implementations
    private val setItemClick = object : OnItemClickListener<PerformedSetClick> {
        override fun onItemClicked(item: PerformedSetClick) {
            showEditSetDialog(item)
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
        finish()
    }

    private val observerFinalize = Observer<FinalizedRunningSessionResult> {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_session)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        exercisesRecyclerView = findViewById(R.id.activity_workout_session_exercises)

        timerContainer = findViewById(R.id.activity_workout_session_rest_container)
        totalRestTimer = findViewById(R.id.activity_workout_session_rest_title)
        runningRestTimer = findViewById(R.id.activity_workout_session_rest_running)
        skipTimerButton = findViewById(R.id.activity_workout_session_rest_timer_rest)
        skipTimerButton.setOnClickListener {
            skipRest()
        }
        addTimerButton = findViewById(R.id.activity_workout_session_rest_timer_add)
        minusTimerButton = findViewById(R.id.activity_workout_session_rest_timer_minus)

        viewModel = ViewModelProviders.of(this, factory)
            .get(DefaultWorkoutSessionViewModel::class.java)

        timerContainer.visibility =
            if (viewModel.isResting()) View.VISIBLE
            else View.GONE

        if (savedInstanceState == null) {

            if (shouldResumeWorkout()) {
                viewModel.workoutSessionLoadMLD.observe(this, Observer {
                    populateLoadedSession(it)
                })
                viewModel.resumeWorkoutSession(getWorkoutRecordID())
            } else {
                viewModel.workoutSessionCreateMLD.observe(this, Observer {
                    populateCreatedSession(it)
                })
                viewModel.createWorkoutSession(getWorkoutRecordID())
            }

        } else {
            viewModel.workoutSessionLoadMLD.observe(this, Observer {
                populateLoadedSession(it)
            })
            viewModel.resumeWorkoutSession(getWorkoutRecordID())
        }

        viewModel.addSetMutableLiveData
            .observe(this, observerAdd)

        viewModel.removeSetMutableLiveData
            .observe(this, observerRemove)

        viewModel.completeSetMutableLiveData.observe(this, observerEdit)

        viewModel.restTimerMLD.observe(this, observerRest)

        viewModel.unFlagRemoveSetMutableLiveData.observe(this, observerUnRemove)

        viewModel.discardSessionMLD.observe(this, observerDiscard)

        viewModel.finalizedSessionMLD.observe(this, observerFinalize)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            android.R.id.home -> {
                viewModel.discardWorkoutSession()
            }
            R.id.activity_workout_session_finish -> {
                viewModel.finishWorkoutSession()
            }
        }
        return true
    }

    private fun showWorkoutSessionFinalizeDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun exerciseSetUnRemoved(result: UnflagRemovePerformedSetResult?) {

        if (result == null)
            return

        val adapter = exercisesRecyclerView.adapter
        adapter!!.notifyItemChanged(result.exercisePerformedIndex, result)
    }

    private fun exerciseSetRemoved(result: RemoveWorkoutSessionSetResult?) {

        if (result == null)
            return

        val adapter = exercisesRecyclerView.adapter
        adapter!!.notifyItemChanged(result.exercisePerformedIndex, result)
    }

    private fun exerciseSetCompleted(result: EditPerformedSetResult?) {

        if (result == null)
            return

        val adapter = exercisesRecyclerView.adapter
        adapter!!.notifyItemChanged(result.exercisePerformedIndex, result)

        rest(result.performedSet.restTimeSeconds)

        // viewModel.completeSetMutableLiveData.value = null

    }

    private fun rest(duration : Int) {
        val timeString = TimeFormatUtil.secondsToString(duration.toLong())
        totalRestTimer.text = getString(R.string.time_rest_format, timeString)
        runningRestTimer.text = timeString
        timerContainer.visibility = View.VISIBLE

        viewModel.restTimerMLD.observe(this, observerRest)
        viewModel.restSet(duration)
    }

    private fun skipRest() {
        viewModel.cancelRest()
        totalRestTimer.text = ""
        runningRestTimer.text = ""
        timerContainer.visibility = View.GONE

        viewModel.restTimerMLD.removeObserver(observerRest)
        viewModel.restTimerMLD.value = null

    }

    private fun updateTimerLabel(time: String?) {

        if (time == null || time.isEmpty()) {
            timerContainer.visibility = View.GONE
        } else {
            runningRestTimer.text = time
        }

    }

    private fun exerciseSetAdded(result: AddPerformedSetResult?) {

        if (result == null)
            return

        val adapter = exercisesRecyclerView.adapter
        adapter!!.notifyItemChanged(result.exercisePerformedIndex, result)
    }

    private fun showEditSetDialog(item: PerformedSetClick) {
        val dialog = PerformedSetEditDialogFragment
            .createInstance(item.exerciseIndex, item.performedExercise.name,
                item.setIndex, item.performedSet)
        dialog.show(supportFragmentManager, "")
    }

    override fun onSetEdit(exerciseIndex: Int, setIndex: Int, weight: Float?, reps: Int?) {
        viewModel.editSet(exerciseIndex, setIndex, reps, weight)
    }

    private fun populateLoadedSession(result: WorkoutSessionCacheLoadResult) {
        val prePerformedExercises = result.prePerformedExercises

        exercisesRecyclerView.adapter =
            PerformedExerciseAdapter(prePerformedExercises,
            setItemClick, addClickListener, removeItemClick, unRemoveItemClick)
    }

    private fun populateCreatedSession(result: WorkoutSessionCreationResult) {
        val prePerformedExercises = result.prePerformedExercises
        exercisesRecyclerView.adapter = PerformedExerciseAdapter(prePerformedExercises,
            setItemClick, addClickListener, removeItemClick, unRemoveItemClick)
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

}