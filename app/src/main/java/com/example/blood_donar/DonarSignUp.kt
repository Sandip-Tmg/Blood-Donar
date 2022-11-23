package com.example.blood_donar

import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class DonarSignUp : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donar_signup)

        // code for making a dropdown option for blood type
        val bloodType = arrayOf("A+","A-","B+","B-","O+","O-","AB+","AB-")
        val bloodAutoTextview = findViewById<AutoCompleteTextView>(R.id.blood_type_select)
        val bloodAdapter = ArrayAdapter(this,R.layout.item_type,bloodType)
        bloodAutoTextview.setAdapter(bloodAdapter)

        // code for making a dropdown option for sex type
        val sex = arrayOf("Male","Female","Other")
        val sexAutoTextView = findViewById<AutoCompleteTextView>(R.id.sex_type_select)
        var sexAdapter = ArrayAdapter(this,R.layout.item_type, sex)
        sexAutoTextView.setAdapter(sexAdapter)

        // event to show the toast message of selected option when selecting the option
        bloodAutoTextview.onItemClickListener = OnItemClickListener { parent, view, position, rowId ->
            val selection = parent.getItemAtPosition(position) as String
            Toast.makeText(applicationContext, "Type: $selection", Toast.LENGTH_SHORT).show()
        }

        // event to show the toast message of selected option when selecting the option
        sexAutoTextView.onItemClickListener = OnItemClickListener { parent, view, position, rowId ->
            val selection = parent.getItemAtPosition(position) as String
            Toast.makeText(applicationContext, "Type: $selection", Toast.LENGTH_SHORT).show()
        }



    }
}