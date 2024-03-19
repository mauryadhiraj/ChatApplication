package com.data.chatapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(
    val context:Context,    // variable named context of type Context & Context is used to provide information about the application
    val userlist:ArrayList<User> //userlist is  variable which is  type of Arraylist & ArrayList is a class in Kotlin
                                 // that represents a dynamic array, meaning its size can be dynamically adjusted.
                                 //<User> denotes that this ArrayList will contain elements of type User
): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val view :View= LayoutInflater.from(context).inflate(
            R.layout.user_layout,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        val currentUser=userlist[position]
        holder.textName.text=currentUser.name

        holder.itemView.setOnClickListener {
            val intent=Intent(context,ChatActivity::class.java)

            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    class UserViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){
        val textName= itemview.findViewById<TextView>(R.id.textName)
    }
}