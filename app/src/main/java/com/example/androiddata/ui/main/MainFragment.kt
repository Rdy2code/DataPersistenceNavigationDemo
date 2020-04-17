package com.example.androiddata.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.androiddata.LOG_TAG
import com.example.androiddata.R
import com.example.androiddata.data.Monster
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment(), MainRecyclerAdapter.MonsterItemListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        //Turn off back arrow
        (requireActivity() as AppCompatActivity).run{
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        val view = inflater.inflate(R.layout.main_fragment, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host)
        swipeLayout = view.findViewById(R.id.swipeLayout)
        swipeLayout.setOnRefreshListener { viewModel.refreshLayout() }

        //Call the ViewModel and subscribe to the LiveData
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.monsterData.observe(viewLifecycleOwner, Observer {
            val adapter = MainRecyclerAdapter(requireContext(), it, this)
            recyclerView.adapter = adapter
            swipeLayout.isRefreshing = false
        })
        return view
    }

    override fun onMonsterItemClick(monster: Monster) {
        Log.i(LOG_TAG, "Selected monster: ${monster.monsterName}")
        navController.navigate(R.id.action_nav_detail)
    }
}
