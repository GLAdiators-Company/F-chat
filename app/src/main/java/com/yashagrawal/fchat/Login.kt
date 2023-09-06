package com.yashagrawal.fchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.sign

class Login : AppCompatActivity() {

    private lateinit var edt_email: EditText
    private lateinit var edt_password: EditText
    private lateinit var btn_login: Button
    private lateinit var btn_signUp: Button

    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()

        edt_email = findViewById(R.id.email)
        edt_password = findViewById(R.id.password)
        btn_login = findViewById(R.id.login)
        btn_signUp = findViewById(R.id.signup)

        btn_signUp.setOnClickListener{
            var intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }
        btn_login.setOnClickListener{
            val email = edt_email.text.toString()
            val password = edt_password.text.toString()

            login(email,password)
        }
    }

    private fun login(email: String,password: String){
        /// Logic for logging user
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){task ->
            if (task.isSuccessful){
                // code to be execute if login successfully
                //logic to go to home activity
                val intent = Intent(this@Login,MainActivity::class.java)
                startActivity(intent)
                finish()

            }else{
                Toast.makeText(this@Login,"User doesn't exists",Toast.LENGTH_SHORT).show()
            }
        }
    }

}

