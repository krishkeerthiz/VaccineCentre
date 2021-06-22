package com.yourapp.vaccinecentre.ui.home

import android.app.DatePickerDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.yourapp.vaccinecentre.R
import com.yourapp.vaccinecentre.databinding.FragmentHomeBinding
import com.yourapp.vaccinecentre.listAdapter.CentresListAdapter
import com.yourapp.vaccinecentre.viewmodel.SessionsViewModel
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val viewModel : SessionsViewModel by lazy{
        ViewModelProvider(requireActivity()).get(SessionsViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!isOnline(requireActivity()))
            Toast.makeText(requireActivity(), getString(R.string.check_internet), Toast.LENGTH_LONG).show()
        else
            Toast.makeText(requireActivity(), "Today's Date : " + viewModel.sDate, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        var adapter : CentresListAdapter
        var centresListView = binding.centresListView

        binding.getButton.setOnClickListener {
            viewModel.getVaccineCentres(binding.editTextPincode.text.toString(), viewModel.sDate)
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken,0)
        }

        binding.datePicker.setOnClickListener{
            val calender = Calendar.getInstance()
            val year = calender.get(Calendar.YEAR)
            val month = calender.get(Calendar.MONTH)
            val day = calender.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireActivity(), DatePickerDialog.OnDateSetListener{ view, year, monthOfYear, dayOfMonth ->
                viewModel.sDate = stringDate(dayOfMonth, monthOfYear+1, year)
                //Toast.makeText(requireActivity(), viewModel.sDate, Toast.LENGTH_SHORT).show()
            } , year, month, day)

            datePickerDialog.show()
        }


      //  binding.centresListView.emptyView = binding.emptyTextView

        viewModel.sessionsModel.observe(requireActivity(), androidx.lifecycle.Observer {

            val hospitals = mutableListOf<String>()
            val vaccines = mutableListOf<String>()
            val available = mutableListOf<Int>()

            for(session in it.sessions!!){
                hospitals.add(session.name)
                vaccines.add(session.vaccine)
                available.add(session.availableCapacity)
            }

            adapter = CentresListAdapter(requireActivity(), hospitals, vaccines, available)
            centresListView.adapter = adapter

            centresListView.setOnItemClickListener { parent, listView, position, id ->
                val session = it.sessions!![position]
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(session)
                view.findNavController().navigate(action)
            }

            if(it.sessions?.size ?: 0 == 0)
                Toast.makeText(requireActivity(), getString(R.string.empty_message), Toast.LENGTH_SHORT).show()
            
        })

        // Get button visibility
        val watcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                binding.getButton.isEnabled = (binding.editTextPincode.text.toString().length == 6)
            }
        }

        binding.editTextPincode.addTextChangedListener(watcher)
    }

    private fun stringDate(dayOfMonth: Int, monthOfYear: Int, year: Int): String = "$dayOfMonth-$monthOfYear-$year"

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    //Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    //Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    //Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

}