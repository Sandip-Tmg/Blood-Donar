package com.example.blood_donar

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SearchDonar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_donar)

        // code for making a dropdown option for blood type
        val bloodType = arrayOf("A+","A-","B+","B-","O+","O-","AB+","AB-")
        val bloodAutoTextview = findViewById<AutoCompleteTextView>(R.id.blood_type_select)
        val bloodAdapter = ArrayAdapter(this,R.layout.item_type,bloodType)
        bloodAutoTextview.setAdapter(bloodAdapter)

        // code for making a dropdown option for sex type
        val range = arrayOf("10km","20km","50km","100km")
        val rangeAutoTextView = findViewById<AutoCompleteTextView>(R.id.range_type_select)
        var rangeAdapter = ArrayAdapter(this,R.layout.item_type, range)
        rangeAutoTextView.setAdapter(rangeAdapter)

        // event to show the toast message of selected option when selecting the option
        bloodAutoTextview.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, rowId ->
                val selection = parent.getItemAtPosition(position) as String
                Toast.makeText(applicationContext, "Type: $selection", Toast.LENGTH_SHORT).show()
            }

        // event to show the toast message of selected option when selecting the option
        rangeAutoTextView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, rowId ->
                val selection = parent.getItemAtPosition(position) as String
                Toast.makeText(applicationContext, "Type: $selection", Toast.LENGTH_SHORT).show()
            }


    }
}