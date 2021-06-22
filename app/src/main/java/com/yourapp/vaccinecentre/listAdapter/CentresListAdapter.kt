package com.yourapp.vaccinecentre.listAdapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.yourapp.vaccinecentre.R

class CentresListAdapter(private val context : Activity, private val hospitals : List<String>, private val vaccines : List<String>, private val minAge : List<Int>)
    : ArrayAdapter<String>(context, R.layout.centres_list, hospitals){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.centres_list, null, true)

        val hospitalsText = rowView.findViewById(R.id.hospitalNameTextView) as TextView
        val vaccinesText = rowView.findViewById(R.id.vaccineNameTextView) as TextView
        val availableText = rowView.findViewById(R.id.availTextView) as TextView

        hospitalsText.text = hospitals[position]
        vaccinesText.text = vaccines[position].toString()
        availableText.text = minAge[position].toString()

        return rowView
    }
}