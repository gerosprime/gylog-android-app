package com.gerosprime.gylog.ui.programs

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.base.utils.FetchStateUtils
import com.gerosprime.gylog.models.programs.ProgramEntity
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

    private lateinit var mainContent : View
    private lateinit var progressContent : View
    private lateinit var errorContent : View

    private var itemClickListener : OnItemClickListener<ProgramEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(DefaultProgramsDashboardViewModel::class.java)
    }

    fun notifyItemInserted(insertIndex : Int) {
        val adapter = usersProgramRecyclerView.adapter

        adapter!!.notifyItemInserted(insertIndex)
        usersProgramRecyclerView.smoothScrollToPosition(0)
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

        mainContent = inflated.findViewById(R.id.fragment_dashboard_programs_content)
        progressContent = inflated.findViewById(R.id.fragment_dashboard_programs_progress)
        errorContent = inflated.findViewById(R.id.fragment_dashboard_programs_error_content)


        usersProgramRecyclerView = mainContent.findViewById(R.id.fragment_dashboard_userprograms)
        usersProgramRecyclerView.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        builtInProgramRecyclerView = mainContent.findViewById(R.id.fragment_dashboard_builtin_programs)
        builtInProgramRecyclerView.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        return inflated
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.fetchStateLiveData.observe(this, Observer { fetchStateChanged(it) })
        viewModel.userProgramListLiveData.observe(this, Observer { userProgramsLoaded(it) })
        viewModel.builtInProgramsLiveData.observe(this, Observer { builtInProgramsLoaded(it) })


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
            viewModel.loadUserPrograms()

    }

    private fun userProgramsLoaded(userPrograms : List<ProgramEntity>) {
        val adapter = ProgramsAdapter(userPrograms)
        adapter.clickListener = object : OnItemClickListener<ProgramEntity> {
            override fun onItemClicked(item: ProgramEntity) {
                openProgramDetail(item)
            }
        }
        usersProgramRecyclerView.adapter = adapter
    }

    private fun openProgramDetail(programEntity: ProgramEntity) {
        val detailIntent = Intent(activity, ProgramDetailActivity::class.java)
        detailIntent.putExtra(ProgramDetailActivity.Extras.PROGRAM_RECORD_ID,
            programEntity.recordId)
        startActivity(detailIntent)
    }

    private fun builtInProgramsLoaded(userPrograms : List<ProgramEntity>) {

        val adapter = ProgramsAdapter(userPrograms)
        adapter.clickListener = object : OnItemClickListener<ProgramEntity> {

            override fun onItemClicked(item: ProgramEntity) {

            }
        }
        builtInProgramRecyclerView.adapter = adapter
    }

    private fun fetchStateChanged(fetchState: FetchState) {
        FetchStateUtils.updateViewContentsByState(fetchState,
            mainContent, progressContent, errorContent)
    }

}