package com.example.blood_donar

import android.annotation.SuppressLint
import android.content.Intent

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class DonarSearch : AppCompatActivity() {

    private lateinit var bloodTypeSelection: String
    private lateinit var rangeTypeSelection: String

    private lateinit var searchDonarBtn: Button

    @SuppressLint("MissingInflatedId")
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

        // code for making a dropdown option for blood type
        var rangeType = arrayOf("10","20","50","100")
        var rangeAutoTextview = findViewById<AutoCompleteTextView>(R.id.range)
        var rangeAdapter = ArrayAdapter(this,R.layout.item_type,rangeType)
        rangeAutoTextview.setAdapter(rangeAdapter)
        rangeTypeSelection=""
        // event to show the toast message of selected option when selecting the option
        rangeAutoTextview.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, rowId ->
                var select = parent.getItemAtPosition(position).toString()
                rangeTypeSelection = select
            }

        searchDonarBtn = findViewById(R.id.search)

        searchDonarBtn.setOnClickListener {
            if(bloodTypeSelection!="" && rangeTypeSelection!=""){
                startLocationActivity()
            }else{
                Toast.makeText(applicationContext, "Choose select both of the options ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startLocationActivity(){
        var intent: Intent = Intent(this, ShowLocation::class.java)
        intent.putExtra("Blood", bloodTypeSelection)
        intent.putExtra("Range", rangeTypeSelection)
        startActivity(intent)
    }

}