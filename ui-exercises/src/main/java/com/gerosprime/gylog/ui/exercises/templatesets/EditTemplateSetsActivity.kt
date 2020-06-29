package com.gerosprime.gylog.ui.exercises.templatesets

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.base.utils.TimeFormatUtil
import com.gerosprime.gylog.models.exercises.templatesets.LoadTemplateSetsToCacheResult
import com.gerosprime.gylog.models.exercises.templatesets.add.CreateTemplateSetToCacheResult
import com.gerosprime.gylog.models.exercises.templatesets.commit.CommitTemplateSetsToWorkoutResult
import com.gerosprime.gylog.models.exercises.templatesets.remove.RemoveTemplateFromCacheResult
import com.gerosprime.gylog.ui.exercises.R
import com.gerosprime.gylog.ui.exercises.databinding.ActivityTemplateSetsBinding
import com.gerosprime.gylog.ui.exercises.templatesets.EditTemplateSetsActivity.RequestCodes.TEMPLATE_EDIT
import com.gerosprime.gylog.ui.exercises.templatesets.detail.TemplateSetEditActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class EditTemplateSetsActivity : AppCompatActivity() {



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

    private lateinit var binding : ActivityTemplateSetsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, factory)
            .get(DefaultEditTemplateSetsViewModel::class.java)

        binding = ActivityTemplateSetsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.activityTemplateSetAddSet.setOnClickListener {
            viewModel.addTemplate(getWorkoutIndex(), getExerciseIndex())
        }

        val adapter = EditTemplateSetsAdapter(mutableListOf())
        binding.activityExerciseAddExercises.adapter = adapter
        val intentSet = Intent(this, TemplateSetEditActivity::class.java)
        adapter.itemClickListener = object : OnItemClickListener<Int> {
            override fun onItemClicked(item: Int) {
                intentSet.putExtra(TemplateSetEditActivity.Extras.TEMPLATE_INDEX, item)
                intentSet.putExtra(TemplateSetEditActivity.Extras.WORKOUT_INDEX, getWorkoutIndex())
                intentSet.putExtra(TemplateSetEditActivity.Extras.EXERCISE_INDEX, getExerciseIndex())
                startActivityForResult(intentSet, TEMPLATE_EDIT)
            }
        }

        binding.activityExerciseAddExercises.addItemDecoration(DividerItemDecoration(this,
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

        ItemTouchHelper(callback).attachToRecyclerView(binding.activityExerciseAddExercises)

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
                    val adapter = binding.activityExerciseAddExercises.adapter
                            as EditTemplateSetsAdapter
                    adapter.notifyItemChanged(editSetPosition)

                }
            }

        }
    }

    private fun updateTotalInfos(totalWeight : Float, totalSeconds : Int, totalSets : Int) {
        binding.apply {
            activityTemplateSetTotalWeight.text = getString(R.string.template_set_weight_format, totalWeight, "KG")
            activityTemplateSetTotalRestTime.text = TimeFormatUtil.secondsToString(totalSeconds.toLong())
            activityTemplateSetTotalSets.text = getString(R.string.template_set_total_sets_format, totalSets)
        }
    }

    private fun templateSetRemoved(result: RemoveTemplateFromCacheResult) {

        val adapter = binding.activityExerciseAddExercises.adapter as EditTemplateSetsAdapter
        val removedIndex = result.templateRemovedIndex

        adapter.notifyItemRemoved(removedIndex)
        adapter.notifyItemRangeChanged(removedIndex, adapter.itemCount - removedIndex);

        binding.apply {
            activityTemplateSetEmpty.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
            activityExerciseAddExercises.visibility = if (adapter.itemCount == 0) View.INVISIBLE else View.VISIBLE
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

    private fun newTemplateSetAdded(result: CreateTemplateSetToCacheResult) {

        binding.apply {
            activityExerciseAddExercises.adapter!!
                .notifyItemRangeInserted(result.insertIndex, 1)
            activityTemplateSetEmpty.visibility = View.GONE
            activityExerciseAddExercises.visibility = View.VISIBLE

            updateTotalInfos(result.totalWeight, result.totalRest, result.totalSets)
        }

    }

    private fun getWorkoutIndex() : Int {
        return intent.getIntExtra(Extras.WORKOUT_INDEX, -1)
    }

    private fun getExerciseIndex() : Int {
        return intent.getIntExtra(Extras.EXERCISE_INDEX, -1)
    }

    private fun populateTemplateSets(result: LoadTemplateSetsToCacheResult) {


        binding.let {

            val adapter = it.activityExerciseAddExercises.adapter as EditTemplateSetsAdapter

            adapter.templates = result.copyTemplates
            adapter.notifyItemRangeChanged(0, result.copyTemplates.size)
            if (result.copyTemplates.isEmpty()) {
                it.activityTemplateSetEmpty.visibility = View.VISIBLE
                it.activityExerciseAddExercises.visibility = View.INVISIBLE
            } else {
                it.activityTemplateSetEmpty.visibility = View.GONE
                it.activityExerciseAddExercises.visibility = View.VISIBLE
            }

            it.collapsingToolbar.title = result.exerciseTemplateEntity.name
            it.toolbar.title = result.exerciseTemplateEntity.name

            updateTotalInfos(result.totalWeight, result.totalRestDuration, result.totalSets)

        }

    }



}