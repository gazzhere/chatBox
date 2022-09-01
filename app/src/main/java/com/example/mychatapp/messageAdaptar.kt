package com.example.mychatapp

import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.core.Context

class messageAdaptar(val context: chatActivity, val messageList:ArrayList<message>)
    :RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    val ITEM_RECIEVED=1;
    val ITEM_SENT=2;


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
   if(viewType==1){
       val view:View= LayoutInflater.from(parent.context).inflate(R.layout.recieve,parent,false)
       return RecieveViewHolder(view)
       }else{
       val view:View=LayoutInflater.from(parent.context).inflate(R.layout.sent,parent,false)
       return SentViewHolder(view)
       }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage=messageList[position]

        if(holder.javaClass==SentViewHolder::class.java){
            val viewholder=holder as SentViewHolder
            holder.sentMessage.text=currentMessage.message
        }else{
            val recieveHolder=holder as RecieveViewHolder
            holder.recievMessage.text=currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage=messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RECIEVED
        }
    }

    override fun getItemCount(): Int {
  return messageList.size
    }
    class  SentViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
       var sentMessage=itemView.findViewById<TextView>(R.id.txtSentmessage)
    }
    class  RecieveViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val recievMessage=itemView.findViewById<TextView>(R.id.txtRecievemessage)
    }
}