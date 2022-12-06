package com.example.blood_donar

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.util.Patterns
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class DonarSignUp : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var address: EditText
    private lateinit var contact: EditText
    private lateinit var dob: Button
    private lateinit var bloodTypeSelection: String
    private lateinit var sexTypeSelection: String
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

        // selection for desired user date of birth
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
        bloodAutoTextview.onItemClickListener = OnItemClickListener { parent, view, position, rowId ->
            var select = parent.getItemAtPosition(position).toString()
            bloodTypeSelection =select
        }


        // code for making a dropdown option for sex type
        val sex = arrayOf("Male","Female","Other")
        val sexAutoTextView = findViewById<AutoCompleteTextView>(R.id.sex_type_select)
        var sexAdapter = ArrayAdapter(this,R.layout.item_type, sex)
        sexAutoTextView.setAdapter(sexAdapter)
        // event to show the toast message of selected option when selecting the option
        sexAutoTextView.onItemClickListener = OnItemClickListener { parent, view, position, rowId ->
            var select = parent.getItemAtPosition(position).toString()
            sexTypeSelection =select
        }

        signUpButton = findViewById(R.id.signUp)

        // implementing database
        var databaseHelper = DBHelper(applicationContext)
        var db = databaseHelper.writableDatabase

        // add event listener to store all the details to the database
        signUpButton.setOnClickListener {

            validFields = validateAllFields()

            if(validFields){
                val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                val age = currentYear - userAgeYear

                val latLong = getGeoCodeAddress(address?.text.toString())?.split("\n")
                val latitude = latLong?.get(0)
                val longitude = latLong?.get(1)

                // storing data to database
                var contentValues = ContentValues()
                contentValues.put("Name", name?.text.toString())
                contentValues.put("Email", email?.text.toString())
                contentValues.put("Latitude", latitude)
                contentValues.put("Longitude", longitude)
                contentValues.put("Contact", contact?.text.toString())
                contentValues.put("age", age)
                contentValues.put("BloodType", bloodTypeSelection)
                contentValues.put("SexType", sexTypeSelection)

                db.insert("Users",null,contentValues)

                Toast.makeText(applicationContext, "Registration Successful", Toast.LENGTH_SHORT).show()
                Thread.sleep(2000)

                var intent: Intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

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

        if(getGeoCodeAddress(address?.text.toString())==null){
            Toast.makeText(applicationContext, "Please type in the correct address", Toast.LENGTH_SHORT).show()
            return false
        }

        if(!android.util.Patterns.PHONE.matcher(contact?.text.toString().trim()).matches()){
            contact!!.error = "Invalid Contact Details"
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