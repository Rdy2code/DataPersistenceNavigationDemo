package com.example.androiddata.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.androiddata.LOG_TAG
import com.example.androiddata.R
import com.example.androiddata.ui.shared.SharedViewModel

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Set the up arrow for navigation back to the main fragment
        //Copy and paste this code into the MainFragment.kt to turn off the arrow upon return
        //The up button is a reserved part of the options menu, so need to handle the touch event
        //in onOptionsItemSelected
        (requireActivity() as AppCompatActivity).run{
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        //Tell fragment to listen for touch events on the options menu
        setHasOptionsMenu(true)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host)
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        //Set up an Observer to the SharedViewModel LiveData object
        viewModel.selectedMonster.observe(viewLifecycleOwner, Observer {
            Log.i(LOG_TAG, "Selected Monster: ${it.monsterName}")
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            //For this to work, also include setHasOptionsMenu(true) in onCreateView
            navController.navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

}
