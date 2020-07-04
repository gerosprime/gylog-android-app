package com.gerosprime.gylog.ui.programs.detail

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.programs.detail.LoadProgramFromCacheResult
import com.gerosprime.gylog.ui.programs.R
import com.gerosprime.gylog.ui.programs.add.ProgramsAddActivity
import com.gerosprime.gylog.ui.programs.databinding.ActivityProgramDetailBinding
import com.gerosprime.gylog.ui.programs.detail.ProgramDetailActivity.Extras.PROGRAM_RECORD_ID
import com.gerosprime.gylog.ui.programs.detail.ProgramDetailActivity.RequestCodes.PROGRAM_EDIT
import com.gerosprime.gylog.ui.programs.detail.ProgramDetailActivity.RequestCodes.WORKOUT_SESSION
import com.gerosprime.gylog.ui.programs.detail.adapter.ProgramDetailAdapter
import com.gerosprime.gylog.ui.workouts.detail.WorkoutDetailDialogFragment
import com.gerosprime.gylog.ui.workouts.session.WorkoutSessionActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import dagger.android.AndroidInjection
import javax.inject.Inject


class ProgramDetailActivity : AppCompatActivity(),
    WorkoutDetailDialogFragment.OnStartClickListener {

    object Extras {
        const val PROGRAM_INDEX = "extra_program_affected_index"
        const val PROGRAM_RECORD_ID = "extra_program_record_id"
    }

    object ResultExtras {
        const val PROGRAM_EDIT_MODE = "extra_program_edit_mode"
    }

    object Mode {
        const val INSERT = 0
        const val EDIT = 1
        const val REMOVE = 2
    }

    object RequestCodes {
        const val PROGRAM_EDIT = 1
        const val WORKOUT_SESSION = 2
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: ProgramDetailViewModel

    private lateinit var binding : ActivityProgramDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityProgramDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory)
            .get(DefaultProgramDetailViewModel::class.java)

        binding.let {
            setSupportActionBar(it.toolbar)

            val adapter = ProgramDetailAdapter(mutableListOf())
            it.activityProgramDetailWorkouts.adapter = adapter
            it.activityProgramDetailWorkouts.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)

            adapter.itemClickListener = object : OnItemClickListener<Long> {
                override fun onItemClicked(item: Long) {
                    showWorkoutDetailDialog(item)
                }
            }

        }

        viewModel.programEntityCacheLoadLiveData.observe(this, Observer { populateProgram(it) })
        viewModel.fetchStateLiveData.observe(this, Observer {})

        if (savedInstanceState == null) {

            if (TedPermission.isGranted(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                viewModel.loadProgramDetail(getProgramRecordId())
            } else {
                TedPermission.with(this)
                    .setRationaleMessage(R.string.read_permission_rationale)
                    .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .setPermissionListener(object : PermissionListener {
                        override fun onPermissionGranted() {
                            viewModel.loadProgramDetail(getProgramRecordId())
                        }
                        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                            viewModel.loadProgramDetail(getProgramRecordId())
                        }
                    }).check()
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_program_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.activity_program_detail_edit -> {
                val editProgramIntent = Intent(this, ProgramsAddActivity::class.java)
                editProgramIntent.putExtra(
                    ProgramsAddActivity.Extras.EXTRA_PROGRAM_RECORD_ID, getProgramRecordId())
                startActivityForResult(editProgramIntent, PROGRAM_EDIT)
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            PROGRAM_EDIT -> {
                if (resultCode == Activity.RESULT_OK) {
                    viewModel.loadProgramDetail(getProgramRecordId())
                    val returnData = Intent()
                    returnData.putExtra(ResultExtras.PROGRAM_EDIT_MODE,
                        data?.getIntExtra(ProgramsAddActivity.Result.EXTRA_PROGRAM_EDIT_MODE, -1))

                    val index = data?.getIntExtra(ProgramsAddActivity.Result.EXTRA_PROGRAM_AFFECTED_INDEX, -1)
                    returnData.putExtra(Extras.PROGRAM_INDEX, index)
                    setResult(Activity.RESULT_OK, returnData)
                }
            }

        }
    }

    private fun getProgramRecordId() : Long {
        return intent.getLongExtra(PROGRAM_RECORD_ID, -1)
    }

    private fun populateProgram(result : LoadProgramFromCacheResult) {

        binding.let {

            val entity = result.programEntity

            it.activityProgramDetailDescription.text = entity.description

            val adapter = it.activityProgramDetailWorkouts.adapter as ProgramDetailAdapter
            adapter.workouts = entity.workouts

            val programEntity = result.programEntity
            if (programEntity.imageUri.isNotEmpty())
                Glide.with(this).load(programEntity.imageUri)
                    .into(it.activityProgramDetailImage)

            it.toolbar.title = entity.name
            if (adapter.itemCount == 0) {
                it.activityProgramDetailEmpty.visibility = View.VISIBLE
                it.activityProgramDetailWorkouts.visibility = View.INVISIBLE
                it.activityProgramDetailWorkoutsLabel.visibility = View.INVISIBLE
            } else {
                it.activityProgramDetailEmpty.visibility = View.INVISIBLE
                it.activityProgramDetailWorkouts.visibility = View.VISIBLE
                it.activityProgramDetailWorkoutsLabel.visibility = View.VISIBLE
            }
        }

    }

    private fun showWorkoutDetailDialog(workoutRecordId : Long) {
        val dialog = WorkoutDetailDialogFragment.createInstance(workoutRecordId)
        dialog.show(supportFragmentManager, "")
    }

    override fun onStartClicked(workoutRecordId: Long) {
        val intentSession = Intent(this, WorkoutSessionActivity::class.java)
        intentSession.putExtra(WorkoutSessionActivity.Extras.WORKOUT_RECORD_ID, workoutRecordId)
        intentSession.putExtra(WorkoutSessionActivity.States.RESUME_WORKOUT, false)
        startActivityForResult(intentSession, WORKOUT_SESSION)
    }
}