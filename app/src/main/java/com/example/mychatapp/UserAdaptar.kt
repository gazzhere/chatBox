package com.example.mychatapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.core.Context

class UserAdaptar(val context: MainActivity, val userlist:ArrayList<user>) :
    RecyclerView.Adapter<UserAdaptar.Userviewholder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Userviewholder {
      val view:View=LayoutInflater.from(parent.context).inflate(R.layout.user_layout,parent,false)
        return Userviewholder(view)
    }

    override fun onBindViewHolder(holder: Userviewholder, position: Int) {
       val currentuser=userlist[position]
        holder.textName.text=currentuser.name
        holder.itemView.setOnClickListener{
            val intent=Intent(context,chatActivity::class.java)
            intent.putExtra("name",currentuser.name)
            intent.putExtra("uid",currentuser.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    class  Userviewholder(itemView: View) :RecyclerView.ViewHolder(itemView){
       val textName=itemView.findViewById<TextView>(R.id.txt_name)
    }
}