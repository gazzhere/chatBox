package com.example.mychatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*

class chatActivity : AppCompatActivity() {
    private  var mDBref= FirebaseDatabase.getInstance().getReference()
    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox:EditText
    private lateinit var sendbutton:ImageView
    private lateinit var messageAdaptar: messageAdaptar
    private lateinit var messageList:ArrayList<message>



    var reciverRoom:String?=null;
    var senderRoom:String?=null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name=intent.getStringExtra("name")
        val recieverUid=intent.getStringExtra("uid")
        val senderUid=FirebaseAuth.getInstance().currentUser?.uid
        mDBref=FirebaseDatabase.getInstance().getReference()
        senderRoom=recieverUid+senderUid
        reciverRoom=senderUid+recieverUid

        supportActionBar?.title=name
        messageRecyclerView=findViewById(R.id.chatRecyclerView)
        messageBox=findViewById(R.id.messageBox)
        sendbutton=findViewById(R.id.sentButton)
        messageList= ArrayList()
        messageAdaptar= messageAdaptar(this,messageList)
        chatRecyclerView.layoutManager=LinearLayoutManager(this)
        chatRecyclerView.adapter=messageAdaptar
//        logic for showing message in user db
mDBref.child("chats").child(senderRoom!!).child("messages")
    .addValueEventListener(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
             for(postSnapshot in snapshot.children){
                 val message=postSnapshot.getValue(message::class.java)
                 messageList.add(message!!)
             }
                messageAdaptar.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        
//        adding message to datat base


        sendbutton.setOnClickListener{
            val message=messageBox.text.toString()
            val messageObject=message(message,senderUid)
            mDBref.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDBref.child("chats").child(reciverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messageBox.setText("")
        }
    }
}