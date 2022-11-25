package com.example.blood_donar

import android.app.DatePickerDialog
import android.location.Geocoder
import android.os.Bundle
import android.util.Patterns
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class DonarSignUp : AppCompatActivity() {

    var name: EditText?=null
    var email: EditText?=null
    var address: EditText?=null
    var contact: EditText?=null
    private lateinit var dob: Button
    var bloodTypeSelection: String?=null
    var sexTypeSelection: String?=null
    var userAgeYear = Calendar.getInstance().get(Calendar.YEAR)
    private lateinit var signUpButton: Button
    var validFields =false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donar_signup)

        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        address = findViewById(R.id.address)
        contact = findViewById(R.id.contact)
        dob = findViewById(R.id.dateOfBirth)

        val calender = Calendar.getInstance()
        var year = calender.get(Calendar.YEAR)
        var month = calender.get(Calendar.MONTH)
        var day = calender.get(Calendar.DAY_OF_MONTH)

        dob.setOnClickListener {
            val datePick = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, myear, mmonth, mday ->
                userAgeYear = myear
                year = myear
                month = mmonth
                day = mday
                dob.text = "Date of Birth: $myear/$mmonth/$mday"
            },year,month,day).show()
        }

        // code for making a dropdown option for blood type
        var bloodType = arrayOf("A+","A-","B+","B-","O+","O-","AB+","AB-")
        var bloodAutoTextview = findViewById<AutoCompleteTextView>(R.id.blood_type_select)
        var bloodAdapter = ArrayAdapter(this,R.layout.item_type,bloodType)
        bloodAutoTextview.setAdapter(bloodAdapter)
        // event to show the toast message of selected option when selecting the option
        var bloodTypeSelection = ""
        bloodAutoTextview.onItemClickListener = OnItemClickListener { parent, view, position, rowId ->
            var select = parent.getItemAtPosition(position).toString()
            bloodTypeSelection += select
        }

        // code for making a dropdown option for sex type
        val sex = arrayOf("Male","Female","Other")
        val sexAutoTextView = findViewById<AutoCompleteTextView>(R.id.sex_type_select)
        var sexAdapter = ArrayAdapter(this,R.layout.item_type, sex)
        sexAutoTextView.setAdapter(sexAdapter)
        // event to show the toast message of selected option when selecting the option
        var sexTypeSelection=""
        sexAutoTextView.onItemClickListener = OnItemClickListener { parent, view, position, rowId ->
            var select = parent.getItemAtPosition(position).toString()
            sexTypeSelection +=select
        }


        signUpButton = findViewById(R.id.signUp)
        signUpButton.setOnClickListener {
            validFields = validateAllFields()

            if(validFields){
                // store data to database

            }
        }

    }

    // function to return the latitude and longitude of the address
    private fun getGeoCodeAddress(address: String): String? {
        var city: String = address
        return try{
            var geoCode= Geocoder(this, Locale.getDefault())
            var addresses = geoCode.getFromLocationName("$city",1)
            var latitude = addresses[0].latitude
            var longitude = addresses[0].longitude
            "$latitude \n $longitude"
        }catch(e: Exception){
            return null
        }
    }

    // function to validate all the fields and return if all the fields are valid
    private fun validateAllFields(): Boolean{
        if(name!!.length()==0){
            name!!.error = "Name is required"
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email?.text).matches()){
            email!!.error = "Invalid Email Address"
            return false
        }
        if(!android.util.Patterns.PHONE.matcher(contact?.text.toString().trim()).matches()){
            contact!!.error = "Invalid Contact Details"
            return false
        }

        if(getGeoCodeAddress(address?.text.toString())==null){
            Toast.makeText(applicationContext, "Please type in the correct address", Toast.LENGTH_SHORT).show()
            return false
        }

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val age = currentYear - userAgeYear
        if(age<18 || age>60){
            Toast.makeText(applicationContext, "You are unable to donate blood", Toast.LENGTH_SHORT).show()
            return false
        }



        return true
    }

}