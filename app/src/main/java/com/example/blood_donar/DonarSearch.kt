package com.example.blood_donar

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class DonarSearch : AppCompatActivity() {

    private lateinit var bloodTypeSelection: String

    private lateinit var searchDonarBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_donar)

        // code for making a dropdown option for blood type
        var bloodType = arrayOf("A+","A-","B+","B-","O+","O-","AB+","AB-")
        var bloodAutoTextview = findViewById<AutoCompleteTextView>(R.id.blood_type_select)
        var bloodAdapter = ArrayAdapter(this,R.layout.item_type,bloodType)
        bloodAutoTextview.setAdapter(bloodAdapter)
        bloodTypeSelection=""
        // event to show the toast message of selected option when selecting the option
        bloodAutoTextview.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, rowId ->
                var select = parent.getItemAtPosition(position).toString()
                bloodTypeSelection = select
            }

        searchDonarBtn = findViewById(R.id.search)

        searchDonarBtn.setOnClickListener {
            if(bloodTypeSelection!=""){
                startLocationActivity()
            }else{
                Toast.makeText(applicationContext, "Choose in the blood type ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startLocationActivity(){
        var intent: Intent = Intent(this, ShowLocation::class.java)
        startActivity(intent)
    }

}