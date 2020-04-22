package com.example.androiddata.ui.splash

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.example.androiddata.PERMISSION_REQUEST_CODE
import com.example.androiddata.R
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
            displayMainFragment()
        } else {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE)
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displayMainFragment()
            } else {
                showToast()
            }
        }
    }

    private fun showToast() {
        Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_LONG).show()
    }

    private fun displayMainFragment () {
        //Get a handle to the NavigationController. Takes activity context and id of fragment
        //container view in the main_activity.xml layout
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host)
        //Navigate to the MainFragment
        //When user presses back button in MainFragment, we don't want them to go back into the
        //splash screen, but exit the app, so we are using a NavOptions argument in the navigate
        //function
        navController.navigate(
            R.id.action_nav_main,
            null,
            (NavOptions.Builder().setPopUpTo(R.id.splashFragment, true)).build())
    }

}
