package com.example.todolist_project.Recy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_project.R

class ReMainViewAdapter(
        mainList: ArrayList<ReMainViewModel>
)

    : RecyclerView.Adapter<ReMainViewHolder>() {

    private var mainList = ArrayList<ReMainViewModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReMainViewHolder {

        return ReMainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_main_item,parent,false))
    }

    override fun onBindViewHolder(holder: ReMainViewHolder, position: Int) {
        holder.bind(mainList[position])



    }

    override fun getItemCount(): Int {
        return mainList.size

    }


}