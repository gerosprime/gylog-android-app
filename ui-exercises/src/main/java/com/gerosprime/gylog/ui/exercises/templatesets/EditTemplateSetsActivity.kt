package com.gerosprime.gylog.ui.exercises.templatesets

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.base.utils.TimeFormatUtil
import com.gerosprime.gylog.models.exercises.templatesets.LoadTemplateSetsToCacheResult
import com.gerosprime.gylog.models.exercises.templatesets.add.CreateTemplateSetToCacheResult
import com.gerosprime.gylog.models.exercises.templatesets.commit.CommitTemplateSetsToWorkoutResult
import com.gerosprime.gylog.models.exercises.templatesets.remove.RemoveTemplateFromCacheResult
import com.gerosprime.gylog.ui.exercises.R
import com.gerosprime.gylog.ui.exercises.templatesets.EditTemplateSetsActivity.RequestCodes.TEMPLATE_EDIT
import com.gerosprime.gylog.ui.exercises.templatesets.detail.TemplateSetEditActivity
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import dagger.android.AndroidInjection
import javax.inject.Inject

class EditTemplateSetsActivity : AppCompatActivity() {


    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private lateinit var toolbar: Toolbar
    private lateinit var addSetButton : ExtendedFloatingActionButton

    private lateinit var templateSetsRecyclerView : RecyclerView
    private lateinit var emptyTextView: TextView

    private lateinit var totalContainer : View
    private lateinit var totalWeightTextView : TextView
    private lateinit var totalSetsTextView : TextView
    private lateinit var totalRestTimeTextView : TextView

    @Inject
    lateinit var factory : ViewModelProvider.Factory
    private lateinit var viewModel: EditTemplateSetsViewModel

    object RequestCodes {
        const val TEMPLATE_EDIT = 12
    }

    object Extras {
        const val WORKOUT_INDEX = "extra_workout_index"
        const val EXERCISE_INDEX = "extra_exercise_index"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_template_sets)
        toolbar = findViewById(R.id.toolbar)
        collapsingToolbar = findViewById(R.id.collapsingToolbar)
        setSupportActionBar(toolbar)

        addSetButton = findViewById(R.id.activity_template_set_add_set)
        addSetButton.setOnClickListener { viewModel.addTemplate(getWorkoutIndex(),
            getExerciseIndex()) }

        emptyTextView = findViewById(R.id.activity_template_set_empty)

        templateSetsRecyclerView = findViewById(R.id.activity_exercise_add_exercises)
        templateSetsRecyclerView.addItemDecoration(DividerItemDecoration(this,
            DividerItemDecoration.VERTICAL))

        // TODO Clean code

        val callback = TemplateItemSwipeCallback(this)
        callback.setListener { source ->
            run {
                val vHolder = source as EditTemplateSetsViewHolder

                viewModel.removeTemplate(
                    vHolder.templatePosition,
                    getWorkoutIndex(), getExerciseIndex()
                )
            }
        }

        ItemTouchHelper(callback).attachToRecyclerView(templateSetsRecyclerView)

        totalContainer = findViewById(R.id.activity_template_sets_total_container)
        totalWeightTextView = findViewById(R.id.activity_template_set_total_weight)
        totalSetsTextView = findViewById(R.id.activity_template_set_total_sets)
        totalRestTimeTextView = findViewById(R.id.activity_template_set_total_rest_time)


        viewModel = ViewModelProviders.of(this, factory)
            .get(DefaultEditTemplateSetsViewModel::class.java)

        viewModel.loadTemplateSetsMutableLiveData.observe(this,
            Observer { populateTemplateSets(it) })
        viewModel.addTemplateResultMutableLiveData.observe(this,
            Observer { newTemplateSetAdded(it) })
        viewModel.commitTemplateResultMutableLiveData.observe(this,
            Observer { templateSetCommitted(it) })
        viewModel.removeTemplateResultMutableLiveData.observe(this,
            Observer { templateSetRemoved(it) })

        if (savedInstanceState == null)
            viewModel.loadTemplateSets(getWorkoutIndex(), getExerciseIndex())

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            TEMPLATE_EDIT -> {
                if (resultCode == Activity.RESULT_OK) {

                    val editSetPosition = data!!
                        .getIntExtra(TemplateSetEditActivity.Extras.TEMPLATE_INDEX, -1)
                    val adapter = templateSetsRecyclerView.adapter as EditTemplateSetsAdapter
                    adapter.notifyItemChanged(editSetPosition)

                }
            }

        }
    }

    private fun updateTotalInfos(totalWeight : Float, totalSeconds : Int, totalSets : Int) {
        totalWeightTextView.text = getString(R.string.template_set_weight_format, totalWeight, "KG")
        totalRestTimeTextView.text = TimeFormatUtil.secondsToString(totalSeconds.toLong())
        totalSetsTextView.text = getString(R.string.template_set_total_sets_format, totalSets)
    }

    private fun templateSetRemoved(result: RemoveTemplateFromCacheResult?) {

        val adapter = templateSetsRecyclerView.adapter as EditTemplateSetsAdapter
        val removedIndex = result!!.templateRemovedIndex


        adapter.notifyItemRemoved(removedIndex)
        adapter.notifyItemRangeChanged(removedIndex, adapter.itemCount - removedIndex);

        if (adapter.itemCount == 0) {
            emptyTextView.visibility = View.VISIBLE
            templateSetsRecyclerView.visibility = View.INVISIBLE
        } else {
            emptyTextView.visibility = View.GONE
            templateSetsRecyclerView.visibility = View.VISIBLE
        }

        updateTotalInfos(result.totalWeight, result.totalRestDuration, result.totalSets)

    }

    private fun templateSetCommitted(result: CommitTemplateSetsToWorkoutResult?) {
        val data = Intent()
        data.putExtra(Extras.EXERCISE_INDEX, result!!.exerciseIndex)
        data.putExtra(Extras.WORKOUT_INDEX, result.workoutIndex)

        setResult(Activity.RESULT_OK, data)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.activity_edit_template_sets_save -> {
                viewModel.commitTemplates(getWorkoutIndex(),
                    getExerciseIndex())
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit_template_sets_activity, menu)
        return true
    }

    private fun newTemplateSetAdded(result: CreateTemplateSetToCacheResult?) {
        templateSetsRecyclerView.adapter!!
            .notifyItemRangeInserted(result!!.insertIndex, 1)

        emptyTextView.visibility = View.GONE
        templateSetsRecyclerView.visibility = View.VISIBLE

        updateTotalInfos(result.totalWeight, result.totalRest, result.totalSets)
    }

    private fun getWorkoutIndex() : Int {
        return intent.getIntExtra(Extras.WORKOUT_INDEX, -1)
    }

    private fun getExerciseIndex() : Int {
        return intent.getIntExtra(Extras.EXERCISE_INDEX, -1)
    }

    private fun populateTemplateSets(result: LoadTemplateSetsToCacheResult) {

        val adapter = EditTemplateSetsAdapter(result.copyTemplates)
        val intentSet = Intent(this, TemplateSetEditActivity::class.java)
        adapter.itemClickListener = object : OnItemClickListener<Int> {
            override fun onItemClicked(item: Int) {
                intentSet.putExtra(TemplateSetEditActivity.Extras.TEMPLATE_INDEX, item)
                intentSet.putExtra(TemplateSetEditActivity.Extras.WORKOUT_INDEX, getWorkoutIndex())
                intentSet.putExtra(TemplateSetEditActivity.Extras.EXERCISE_INDEX, getExerciseIndex())
                startActivityForResult(intentSet, TEMPLATE_EDIT)
            }
        }
        templateSetsRecyclerView.adapter = adapter

        if (result.copyTemplates.isEmpty()) {
            emptyTextView.visibility = View.VISIBLE
            templateSetsRecyclerView.visibility = View.INVISIBLE
        } else {
            emptyTextView.visibility = View.GONE
            templateSetsRecyclerView.visibility = View.VISIBLE
        }


        collapsingToolbar.title = result.exerciseTemplateEntity.name
        toolbar.title = result.exerciseTemplateEntity.name

        updateTotalInfos(result.totalWeight, result.totalRestDuration, result.totalSets)
    }



}