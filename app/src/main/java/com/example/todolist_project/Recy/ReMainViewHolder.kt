package com.example.todolist_project.Recy

import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_project.databinding.RecyclerviewMainItemBinding
import kotlinx.android.synthetic.main.recyclerview_main_item.view.*

class ReMainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    // 전역 변수로 바인딩 객체 선언
    private lateinit var binding: RecyclerviewMainItemBinding
    // 뷰들 가져오기


    private val main_goal_text = itemView.re_main_time_text
    private val main_time_text = itemView.goal_text
    private var main_day_text = itemView.re_main_day_text


    fun bind(mainItem: ReMainViewModel) {
        Log.d(TAG, "ReMainViewHolder - bind() called")

        main_goal_text.text = mainItem.goal_text_room.toString()

        main_time_text.text = mainItem.re_main_time_text_room.toString()
        main_day_text.text = mainItem.re_main_day_text_room.toString()


    }








}

