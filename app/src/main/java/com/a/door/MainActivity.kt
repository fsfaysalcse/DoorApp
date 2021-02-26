package com.a.door

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val on1= findViewById(R.id.on) as Button
        val off1= findViewById(R.id.off) as Button
        on1.setOnClickListener {

            if(isConnected()) {
                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("Door_Status")

                myRef.setValue(1)

                val toast = Toast.makeText(applicationContext, "Door is Looked", Toast.LENGTH_SHORT)
                toast.show()

            }
            else{
                val toast = Toast.makeText(applicationContext, "No Internet Connection", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
        off1.setOnClickListener {
            if(isConnected()) {

                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("Door_Status")

                myRef.setValue(0)

                val toast = Toast.makeText(applicationContext, "Door is UnLooked", Toast.LENGTH_SHORT)
                toast.show()
            }
            else{
                val toast = Toast.makeText(applicationContext, "No Internet Connection", Toast.LENGTH_SHORT)
                toast.show()
            }


        }
    }
    fun isConnected(): Boolean {
        var connected = false
        try {
            val cm =
                    applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nInfo = cm.activeNetworkInfo
            connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected
            return connected
        } catch (e: Exception) {
            Log.e("Connectivity Exception", e.message)
        }
        return connected
    }
}
