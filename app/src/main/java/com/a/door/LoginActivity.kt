package com.a.door

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.a.door.Constants
import com.a.door.MainActivity
import com.a.door.R
import com.a.door.SharedPref


class LoginActivity : AppCompatActivity() {

    lateinit var edit_username : EditText
    lateinit var edit_password : EditText
    lateinit var loginBtn : Button
    lateinit var showPassword : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        edit_username = findViewById(R.id.edit_username)
        edit_password = findViewById(R.id.edit_password)
        loginBtn = findViewById(R.id.loginBtn)
        showPassword = findViewById(R.id.showPassword)

        val status = SharedPref.getKey(this, Constants.LOGIN_STATUS) // Getting login status from shared preference

        if (status == "1"){ // if status value is 0 then user already logged in
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }


        //login button action if user press the button then those code will be execute
        loginBtn.setOnClickListener {
            val username = edit_username.text.toString().trim()
            val password = edit_password.text.toString().trim()

            if (username == "" || username == null){
                edit_username.setError("Required field is empty !")
                return@setOnClickListener
            }

            if (password == "" || password == null){
                edit_password.setError("Required field is empty !")
                return@setOnClickListener
            }

           // static password stored in Constant.kt file

            if (username == Constants.USER_NAME && password == Constants.PASSWORD){ // if username and password is correct then open main activity
                Toast.makeText(applicationContext, "Login Success !", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                SharedPref.putKey(applicationContext, Constants.LOGIN_STATUS, "1")
            }else{ // else show this error
                SharedPref.putKey(applicationContext, Constants.LOGIN_STATUS, "0")
                Toast.makeText(applicationContext, "Authentication Failed !", Toast.LENGTH_SHORT).show()
            }
        }

        showPassword.setOnTouchListener(OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> edit_password.setInputType(InputType.TYPE_CLASS_TEXT)
                MotionEvent.ACTION_UP -> edit_password.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            }
            true
        })


    }
}