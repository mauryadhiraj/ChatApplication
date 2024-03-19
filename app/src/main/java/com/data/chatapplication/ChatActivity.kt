    package com.data.chatapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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

    class ChatActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox:EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList:ArrayList<Message>
    private lateinit var dbRef:DatabaseReference

        var receiveRoom:String?=null
        var senderRoom:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name=intent.getStringExtra("name")
        val receiveruid=intent.getStringExtra("uid")
        val senderUid=FirebaseAuth.getInstance().currentUser?.uid
        dbRef=FirebaseDatabase.getInstance().getReference()

        senderRoom=receiveruid+senderUid
        receiveRoom=senderUid+receiveruid

        supportActionBar?.title=name

        chatRecyclerView=findViewById(R.id.chatRecyclerView)
        messageBox=findViewById(R.id.messageBox)
        sendButton=findViewById(R.id.sendButton)
        messageList=ArrayList()
        messageAdapter=MessageAdapter(this,messageList)

        chatRecyclerView.layoutManager= LinearLayoutManager(this)
        chatRecyclerView.adapter= messageAdapter

        //logic for adding data to recyclerView
        dbRef.child("chats").child(senderRoom!!).child("message").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                messageList.clear()
                for(postSnapshot in snapshot.children){
                    val message= postSnapshot.getValue(Message::class.java)
                    messageList.add(message!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        //add message to database
        sendButton.setOnClickListener {
            val message=messageBox.text.toString()
            val messageObject=Message(message,senderUid)

            dbRef.child("chats").child(senderRoom!!).child("message").push().setValue(messageObject).addOnSuccessListener {
            dbRef.child("chats").child(receiveRoom!!).child("message").push().setValue(messageObject)
            }
            messageBox.setText("")
        }

    }
}