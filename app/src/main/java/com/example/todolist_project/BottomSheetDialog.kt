package com.example.todolist_project

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.Visibility
import com.example.todolist_project.databinding.BottomsheetdialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialog : BottomSheetDialogFragment() {
    private var _binding: BottomsheetdialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomSheetDialogClickListener: BottomSheetDialogClickListener

    interface BottomSheetDialogClickListener {
        fun onButton()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):
            View? {

        _binding = BottomsheetdialogBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.timeGoal.setOnClickListener(){



        }
        binding.timeSave.setOnClickListener() {

            save()
            binding.timeButonText.text = binding.timeButonText.toString()
binding.timeGoal
            //데이터 저장

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            bottomSheetDialogClickListener = context as BottomSheetDialogClickListener
        } catch (e: Exception) {
            Log.d(TAG, "Error")
        }
    }

    fun save() {
        binding.timeModify.visibility = View.VISIBLE
        binding.timeDelete.visibility = View.GONE
        binding.timeGoal.isEnabled = isHidden
        binding.timeButonText.isEnabled = isHidden


    }
}

