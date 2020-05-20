package com.gerosprime.gylog.ui.programs

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.base.utils.FetchStateUtils
import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.ui.programs.ProgramsDashboardFragment.RequestCodes.PROGRAM_DETAIL
import com.gerosprime.gylog.ui.programs.databinding.FragmentDashboardProgramsBinding
import com.gerosprime.gylog.ui.programs.detail.ProgramDetailActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ProgramsDashboardFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory
    private lateinit var viewModel: ProgramsDashboardViewModel

    private lateinit var usersProgramRecyclerView : RecyclerView
    private lateinit var builtInProgramRecyclerView : RecyclerView


    private lateinit var programsBinding: FragmentDashboardProgramsBinding

    private lateinit var mainContent : View
    private lateinit var progressContent : View
    private lateinit var errorContent : View

    private var itemClickListener : OnItemClickListener<ProgramEntity>? = null

    object RequestCodes {
        const val PROGRAM_DETAIL = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)
        .get(DefaultProgramsDashboardViewModel::class.java)
    }

    fun notifyItemInserted(insertIndex : Int) {
        val adapter = usersProgramRecyclerView.adapter

        adapter!!.notifyItemInserted(insertIndex)
        usersProgramRecyclerView.smoothScrollToPosition(insertIndex)
    }

    fun notifyItemUpdated(updateIndex : Int) {
        val adapter = usersProgramRecyclerView.adapter

        adapter!!.notifyItemChanged(updateIndex)
        usersProgramRecyclerView.smoothScrollToPosition(updateIndex)
    }

    override fun onDestroy() {
        itemClickListener = null
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var inflated : View = inflater.inflate(R.layout.fragment_dashboard_programs,
            container, false)

        programsBinding = FragmentDashboardProgramsBinding.bind(inflated)

        programsBinding.fragmentDashboardUserprograms.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        programsBinding.fragmentDashboardBuiltinPrograms.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        return inflated

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.fetchStateLiveData.observe(viewLifecycleOwner, Observer { fetchStateChanged(it) })
        viewModel.userProgramListLiveData.observe(viewLifecycleOwner, Observer { userProgramsLoaded(it) })
        viewModel.builtInProgramsLiveData.observe(viewLifecycleOwner, Observer { builtInProgramsLoaded(it) })


        if (savedInstanceState == null) {
            if (TedPermission.isGranted(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                viewModel.loadUserPrograms()
            } else {
                TedPermission.with(context)
                    .setRationaleMessage(R.string.read_permission_rationale)
                    .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .setPermissionListener(object : PermissionListener {
                        override fun onPermissionGranted() {
                            viewModel.loadUserPrograms()
                        }
                        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                            viewModel.loadUserPrograms()
                        }
                    }).check()

            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            PROGRAM_DETAIL -> {
                if (resultCode == Activity.RESULT_OK) {
                    val insertIndex =
                        data!!.getIntExtra(ProgramDetailActivity.Extras.PROGRAM_INDEX, -1)

                    when (val mode = data.getIntExtra(ProgramDetailActivity.ResultExtras.PROGRAM_EDIT_MODE, -1)) {
                        ProgramDetailActivity.Mode.EDIT -> {
                            notifyItemUpdated(insertIndex)
                        }
                        ProgramDetailActivity.Mode.INSERT -> {
                            notifyItemInserted(insertIndex)
                        }
                        else -> {
                            throw IllegalArgumentException("Invalid mode: $mode")
                        }
                    }
                }
            }
        }

    }

    private fun userProgramsLoaded(userPrograms : List<ProgramEntity>) {
        val adapter = ProgramsAdapter(userPrograms)
        adapter.clickListener = object : OnItemClickListener<ProgramItemClick> {
            override fun onItemClicked(item: ProgramItemClick) {
                openProgramDetail(item.index, item.entity)
            }
        }
        usersProgramRecyclerView.adapter = adapter
    }

    private fun openProgramDetail(index : Int, programEntity: ProgramEntity) {
        val detailIntent = Intent(activity, ProgramDetailActivity::class.java)
        detailIntent.putExtra(ProgramDetailActivity.Extras.PROGRAM_RECORD_ID,
            programEntity.recordId)
        detailIntent.putExtra(ProgramDetailActivity.Extras.PROGRAM_INDEX,
            index)
        startActivityForResult(detailIntent, PROGRAM_DETAIL)
    }

    private fun builtInProgramsLoaded(userPrograms : List<ProgramEntity>) {

        val adapter = ProgramsAdapter(userPrograms)
        adapter.clickListener = object : OnItemClickListener<ProgramItemClick> {

            override fun onItemClicked(item: ProgramItemClick) {

            }
        }
        builtInProgramRecyclerView.adapter = adapter
    }

    private fun fetchStateChanged(fetchState: FetchState) {
        FetchStateUtils.updateViewContentsByState(fetchState,
            mainContent, progressContent, errorContent)
    }

}