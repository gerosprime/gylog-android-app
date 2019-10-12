package com.gerosprime.gylog.ui.programs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.base.utils.FetchStateUtils
import com.gerosprime.gylog.models.programs.ProgramEntity
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ProgramsDashboardFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory
    lateinit var viewModel: ProgramsDashboardViewModel

    lateinit var usersProgramRecyclerView : RecyclerView
    lateinit var builtInProgramRecyclerView : RecyclerView

    lateinit var mainContent : View
    lateinit var progressContent : View
    lateinit var errorContent : View

    var itemClickListener : OnItemClickListener<ProgramEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(DefaultProgramsDashboardViewModel::class.java)
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

        viewModel.loadUserPrograms()

    }

    private fun userProgramsLoaded(userPrograms : List<ProgramEntity>) {
        usersProgramRecyclerView.adapter = ProgramsAdapter(userPrograms)
    }

    private fun builtInProgramsLoaded(userPrograms : List<ProgramEntity>) {
        builtInProgramRecyclerView.adapter = ProgramsAdapter(userPrograms)
    }

    private fun fetchStateChanged(fetchState: FetchState) {
        FetchStateUtils.updateViewContentsByState(fetchState,
            mainContent, progressContent, errorContent)
    }

}