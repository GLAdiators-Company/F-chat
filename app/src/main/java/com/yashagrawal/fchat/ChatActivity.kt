package com.yashagrawal.fchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.yashagrawal.fchat.adapter.MessageAdapter

class ChatActivity : AppCompatActivity() {

    private lateinit var chat_recyclerView: RecyclerView
    private lateinit var msgBox : EditText
    private lateinit var sendBtn : ImageView
    private lateinit var messageAdapter : MessageAdapter
    private lateinit var messageList: ArrayList<MessageModel>
    private lateinit var mDbRef : DatabaseReference
    var receiverRoom : String? = null
    var senderRoom : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        mDbRef = FirebaseDatabase.getInstance().reference

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        supportActionBar?.title = name

        chat_recyclerView = findViewById(R.id.chatRecyclerView)
        msgBox = findViewById(R.id.messageBox)
        sendBtn = findViewById(R.id.send_btn)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)

        chat_recyclerView.layoutManager = LinearLayoutManager(this)
        chat_recyclerView.adapter = messageAdapter

        // Login for adding data to the recyclerView
        mDbRef.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()

                for(postSnapshot in snapshot.children){
                    val message = postSnapshot.getValue(MessageModel::class.java)
                    messageList.add(message!!)


                }
                messageAdapter.notifyDataSetChanged()
            }


            override fun onCancelled(error: DatabaseError) {
            }

        })


//        adding the messages to the database
        sendBtn.setOnClickListener{
            val message = msgBox.text.toString()
            val messageObject = MessageModel(message,senderUid)

            mDbRef.child("chats").child(senderRoom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                mDbRef.child("chats").child(receiverRoom!!).child("messages").push().setValue(messageObject)
            }


            msgBox.text = null
        }
    }
}