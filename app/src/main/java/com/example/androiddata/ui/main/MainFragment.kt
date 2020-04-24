package com.example.androiddata.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.androiddata.LOG_TAG
import com.example.androiddata.R
import com.example.androiddata.data.Monster
import com.example.androiddata.ui.shared.SharedViewModel
import com.example.androiddata.utilities.PrefsHelper

class MainFragment : Fragment(), MainRecyclerAdapter.MonsterItemListener {

    private lateinit var adapter: MainRecyclerAdapter
    private lateinit var viewModel: SharedViewModel
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

        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.main_fragment, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)

        //Set the layout according to the settings in the SharedPreferences file
        val layoutStyle = PrefsHelper.getItemType(requireContext())
        recyclerView.layoutManager =
            if (layoutStyle == "grid") {
                GridLayoutManager(requireContext(), 2)
            } else {
                LinearLayoutManager(requireContext())
            }

        //Supply id of fragment container in the main_activity layout
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host)
        swipeLayout = view.findViewById(R.id.swipeLayout)
        swipeLayout.setOnRefreshListener { viewModel.refreshLayout() }

        //Call the ViewModel and subscribe to the LiveData
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        viewModel.monsterData.observe(viewLifecycleOwner, Observer {
            adapter = MainRecyclerAdapter(requireContext(), it, this)
            recyclerView.adapter = adapter
            swipeLayout.isRefreshing = false
        })
        viewModel.activityTitle.observe(viewLifecycleOwner, Observer {
            //Set the title of the Activity to the value of the mutable live data object
            //that is passed into the observer function
            requireActivity().title = it
        })
        return view
    }

    override fun onMonsterItemClick(monster: Monster) {
        Log.i(LOG_TAG, "Selected monster: ${monster.monsterName}")

        //Store the monster item object that was clicked on in the SharedViewModel
        viewModel.selectedMonster.value = monster

        //navigate to the detail fragment
        navController.navigate(R.id.action_nav_detail)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_view_grid -> {
                //Write to the SharedPreferences
                PrefsHelper.setItemType(requireContext(), "grid")
                //Reset the RecyclerView Layout
                recyclerView.layoutManager =
                    GridLayoutManager(requireContext(), 2)
                //Rebuild the adapter with this new RecyclerView Layout
                recyclerView.adapter = adapter

            }
            R.id.action_view_list -> {
                PrefsHelper.setItemType(requireContext(), "list")
                recyclerView.layoutManager =
                    LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter
            }
            R.id.action_settings -> {
                navController.navigate(R.id.settingsActivity)
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateActivityTitle()
    }
}
