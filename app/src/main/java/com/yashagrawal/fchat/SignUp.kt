package com.yashagrawal.fchat
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.yashagrawal.fchat.UserModel.User

class SignUp : AppCompatActivity() {
    private lateinit var edt_name : EditText
    private lateinit var edt_email: EditText
    private lateinit var edt_password: EditText
    private lateinit var btn_signUp: Button

    private lateinit var mDbRef : DatabaseReference
    private lateinit var mAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()
        edt_name = findViewById(R.id.name)
        edt_email = findViewById(R.id.email)
        edt_password = findViewById(R.id.password)
        btn_signUp = findViewById(R.id.signup)

        mAuth = FirebaseAuth.getInstance()

        btn_signUp.setOnClickListener{
            val name = edt_name.text.toString()
            val email = edt_email.text.toString()
            val password = edt_password.text.toString()
            signUp(name,email,password)
        }
    }

    private fun signUp(name : String,email:String,password:String){
        /// Logic for creating user
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){task ->
            if (task.isSuccessful){
//                logic to go to home activity
                addUserToDataBase(name,email,mAuth.currentUser?.uid!!)
                val intent = Intent(this@SignUp,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this@SignUp,"Some error occurred",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun addUserToDataBase(name: String,email: String,uid : String){
        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child("user").child(uid).setValue(User(name,email,uid))
    }
}