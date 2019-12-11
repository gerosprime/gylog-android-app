package com.gerosprime.gylog.ui.exercises.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.models.exercises.LoadedSingleExerciseResult
import com.gerosprime.gylog.ui.exercises.R
import com.gerosprime.gylog.ui.exercises.add.ExerciseAddActivity
import com.gerosprime.gylog.ui.exercises.dashboard.TargetMusclesAdapter
import com.gerosprime.gylog.ui.exercises.detail.ExerciseDetailActivity.Extras.EXERCISE_ID
import com.gerosprime.gylog.ui.exercises.detail.ExerciseDetailActivity.RequestCodes.EXERCISE_EDIT
import com.google.android.material.appbar.MaterialToolbar
import dagger.android.AndroidInjection
import javax.inject.Inject

class ExerciseDetailActivity : AppCompatActivity() {

    object Extras {
        const val EXERCISE_ID = "extra_exercise_id"
    }

    object Result {
        const val FLAG = "result_flag"
        const val INDEX = "result_index"
    }

    object State {
        const val FLAG = "state_flag"
        const val INDEX = "state_index"
    }

    object RequestCodes {
        const val EXERCISE_EDIT = 1
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: ExerciseDetailViewModel

    private lateinit var toolbar : MaterialToolbar

    private lateinit var description : TextView
    private lateinit var directions : TextView
    private lateinit var musclesRecyclerView: RecyclerView

    private var flag : Int? = null
    private var index: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory)
            .get(DefaultExerciseDetailViewModel::class.java)

        setContentView(R.layout.activity_exercise_detail)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        description = findViewById(R.id.activity_exercise_detail_description)
        directions = findViewById(R.id.activity_exercise_detail_directions)
        musclesRecyclerView = findViewById(R.id.activity_exercise_detail_muscles)
        musclesRecyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.loadedExerciseResultMLD.observe(this,
            Observer {
                populateExerciseDetail(it)
            })

        if(savedInstanceState == null)
            viewModel.load(getExerciseID())
        else {

            if (savedInstanceState.containsKey(State.FLAG))
                flag = savedInstanceState.getInt(State.FLAG)

            if (savedInstanceState.containsKey(State.INDEX))
                index = savedInstanceState.getInt(State.INDEX)

        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (index != null)
            outState.putInt(State.INDEX, index!!)

        if (flag != null)
            outState.putInt(State.FLAG, flag!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_exercise_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        if (index == null && flag == null)
            setResult(Activity.RESULT_CANCELED)
        else {
            val data = Intent()
            data.putExtra(Result.FLAG, flag)
            data.putExtra(Result.INDEX, index)
            setResult(Activity.RESULT_OK, data)
        }
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> {
                if (index == null && flag == null)
                    setResult(Activity.RESULT_CANCELED)
                else {
                    val data = Intent()
                    data.putExtra(Result.FLAG, flag)
                    data.putExtra(Result.INDEX, index)
                    setResult(Activity.RESULT_OK, data)
                }
                finish()
            }
            R.id.activity_exercise_detail_edit -> {
                val intentEdit = Intent(this,
                    ExerciseAddActivity::class.java)
                intentEdit.putExtra(ExerciseAddActivity.Extras.RECORD_ID,
                    getExerciseID())
                startActivityForResult(intentEdit, EXERCISE_EDIT)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun populateExerciseDetail(it: LoadedSingleExerciseResult) {
        description.text = it.description
        directions.text = it.directions
        supportActionBar?.title = it.name
        musclesRecyclerView.adapter = TargetMusclesAdapter(it.muscles)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            EXERCISE_EDIT -> {
                if (resultCode == Activity.RESULT_OK) {
                    index = data?.getIntExtra(ExerciseAddActivity.Result.INDEX, -1)
                    flag = data?.getIntExtra(ExerciseAddActivity.Result.FLAG, -1)
                    viewModel.load(getExerciseID())
                }

            }
        }
    }

    private fun getExerciseID() : Long {
        return intent.getLongExtra(EXERCISE_ID, -1)
    }

}