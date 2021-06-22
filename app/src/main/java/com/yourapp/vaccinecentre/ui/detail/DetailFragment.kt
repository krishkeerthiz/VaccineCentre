package com.yourapp.vaccinecentre.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.yourapp.vaccinecentre.R
import com.yourapp.vaccinecentre.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private lateinit var binding : FragmentDetailBinding

    val args : DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)

        val session = args.session

        binding.dateTextView.text = session.date
        binding.hospitalTextView.text = session.name
        binding.addressTextView.text = session.address
        binding.vaccineTextView.text = session.vaccine
        binding.ageTextView.text = session.minAgeLimit.toString() + "+"
        binding.feeTypeTextView.text = session.feeType
        binding.feeTextView.text = "₹"+session.fee
        binding.availableTextView.text = session.availableCapacity.toString()
        binding.dose1TextView.text = session.availableCapacityDose1.toString()
        binding.dose2TextView.text = session.availableCapacityDose2.toString()
        binding.slotTextView.text = session.slots.toString()

    }
}